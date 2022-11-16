package com.lixingyong.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * 这是一个基于 JDK11 的 HttpClient 所构建的简易工具类。此类基于复用 HttpClient 的理念设计，
 * 并增加默认了全局默认 Headers，减少不必要的设置，简化网络请求。
 *
 * <p>
 * 推荐用户使用有参构造函数 {@link #HttpRequestUtil(HttpClient)} 创建此类的对象，若采用无参
 * 构造函数，将基于 {@link HttpClient#newHttpClient()} 构建一个全局默认的 HttpClient。运行
 * 期间可通过 Get 以及 Set 操作重设 HttpClient
 * </p>
 *
 * <p>
 * 为方便用户请求，此类采用 {@link RequestItem} 重新封装了 {@code HttpRequest} 的参数并进行
 * 了一部分处理。例如增加了默认参数 {@link #defaultHeaders}，封装发送请求方法
 * {@link #send(RequestItem, HttpResponse.BodyHandler)}。为方便用户自定义，在运行期间可以使用
 * {@link #getRequest(RequestItem)} 来获取构造过程中的 HttpRequest。
 * </p>
 *
 * @see HttpClient
 * @see HttpRequest
 * @see RequestItem
 *
 * @author LIlGG
 * @since 2022-09-27
 */
public class HttpRequestUtil {

    private final static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    private final static int DEFAULT_REQUEST_TIMEOUT = 30;

    private HttpClient httpClient;

    private Map<String, String> defaultHeaders;

    private final ObjectMapper objectMapper;

    public HttpRequestUtil() {
        this(HttpClient.newHttpClient());
    }

    public HttpRequestUtil(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.defaultHeaders = new HashMap<>();
        this.objectMapper = new ObjectMapper();
    }

    public HttpRequest.Builder getRequestBuild(RequestItem requestItem) {
        HttpRequest.Builder builder = HttpRequest.newBuilder(requestItem.uri());
        if (requestItem.formMap().isEmpty()) {
            builder.GET(); // HttpRequest 不允许 POST 之类的请求实体为空，因此转为 GET
        } else {
            if ("GET".equals(requestItem.method())) {
                String query = HttpRequestUtil.getQueryByParamMap(requestItem.formMap());
                builder.uri(HttpRequestUtil.addUriQuery(requestItem.uri(), query));
                builder.GET();
            } else {
                String requestBody;
                try {
                    requestBody = objectMapper
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(requestItem.formMap());
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("请求参数转换 JSON 失败", e);
                }
                builder.method(requestItem.method(), HttpRequest.BodyPublishers.ofString(requestBody));
            }
        }
        String[] arr = HttpRequestUtil.mapToArrayHeader(mergeDefaultHeader(requestItem.headers()));
        if (Objects.nonNull(arr)) {
            builder.headers(arr);
        }
        builder.timeout(
            Objects.isNull(requestItem.duration())
                ? Duration.ofSeconds(DEFAULT_REQUEST_TIMEOUT) : requestItem.duration()
        );
        return builder;
    }

    public static URI addUriQuery(URI uri, String query) {
        if (!StringUtils.hasLength(query)) {
            return uri;
        }
        String uriStr = uri.toString();
        if (uriStr.contains("?")) {
            return URI.create(uriStr + "&" + query);
        }
        return URI.create(uriStr + "?" + query);
    }

    public HttpRequest getRequest(RequestItem requestItem) {
        return getRequestBuild(requestItem).build();
    }

    public <T> HttpResponse<T> send(RequestItem requestItem,
                                    HttpResponse.BodyHandler<T> responseBodyHandler)
        throws IOException, InterruptedException {
        HttpRequest request = getRequest(requestItem);
        return httpClient.send(request, responseBodyHandler);
    }

    public <T> CompletableFuture<HttpResponse<T>> sendAsync(RequestItem requestItem,
                                                            HttpResponse.BodyHandler<T> responseBodyHandler) {
        HttpRequest request = getRequest(requestItem);
        return httpClient.sendAsync(request, responseBodyHandler)
            .handle(((httpResponse, throwable) -> {
                if (throwable != null) {
                    logger.error("CompletableFuture 异常, httpResponse -> " + httpResponse, throwable);
                    throw new RuntimeException("CompletableFuture 处理异常 -> " + throwable.getMessage());
                }
                if (httpResponse.statusCode() < 200 || httpResponse.statusCode() > 299) {
                    logger.error("http 状态码异常,httpResponse -> " + httpResponse);
                    throw new RuntimeException("网络请求失败, httpResponse -> " + httpResponse);
                }
                return httpResponse;
            }));
    }

    /**
     * 合并公共 Headers 数据
     * @param additionalHeaders 用户传入的额外 headers 数据
     * @return 合并之后 Headers 后的 copy Map
     */
    private Map<String, String> mergeDefaultHeader(Map<String, String> additionalHeaders) {
        Map<String, String> headers = new HashMap<>();
        headers.putAll(this.defaultHeaders);
        headers.putAll(additionalHeaders);
        return headers;
    }

    @Nullable
    public static String[] mapToArrayHeader(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        String[] arr = new String[map.size() * 2];
        for (int i = 0; i < map.size(); i += 2) {
            Map.Entry<String, String> entry = iterator.next();
            arr[i] = entry.getKey();
            arr[i + 1] = entry.getValue();
        }
        return arr;
    }

    /**
     * 将 Map 参数转换为 GET 类型的查询语句，类似于：
     * <pre>
     *   key1=v1&amp;key2=&amp;key3=v3
     * </pre>
     *
     * @param params 参数 Map
     * @return 查询字符串，查询不到返回空串
     */
    @NonNull
    public static String getQueryByParamMap(Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        List<String> queryList = new ArrayList<>(params.size());
        params.forEach((key, value) -> queryList.add(key + "=" + value));
        return String.join("&", queryList);
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Map<String, String> getDefaultHeaders() {
        return defaultHeaders;
    }

    public void setDefaultHeaders(Map<String, String> defaultHeaders) {
        this.defaultHeaders = defaultHeaders;
    }

    public void addDefaultHeader(String key, String value) {
        this.defaultHeaders.put(key, value);
    }
}
