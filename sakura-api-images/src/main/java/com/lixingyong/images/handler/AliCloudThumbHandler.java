package com.lixingyong.images.handler;

import com.lixingyong.common.utils.HttpRequestUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author LIlGG
 * @since 2022-11-13
 */
public class AliCloudThumbHandler implements ThumbHandler {

    @Override
    public ThumbBuild createBuild(String strUrl) {
        URI url = null;
        try {
            url = new URI(strUrl);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("待处理图像链接非常规 URI ");
        }
        return new AliCloudThumbBuild(url);
    }

    static class AliCloudThumbBuild implements ThumbBuild {

        private static final Integer MIN_RESIZE = 0;

        private static final Integer MAX_RESIZE = 4096;

        private final Map<String, String> builder = new HashMap<>();

        private final URI url;

        public AliCloudThumbBuild(URI url) {
            this.url = url;
        }

        @Override
        public ThumbBuild format(String type) {
            return this;
        }

        @Override
        public ThumbBuild quality(Float quality) {
            return this;
        }

        @Override
        public ThumbBuild resize(Integer width, Integer height) {
            List<String> params = new ArrayList<>();
            if (Objects.nonNull(width)) {
                params.add("w_" + Math.min(Math.max(width, MIN_RESIZE), MAX_RESIZE));
            }
            if (Objects.nonNull(height)) {
                params.add("h_" + Math.min(Math.max(height, MIN_RESIZE), MAX_RESIZE));
            }
            if (!params.isEmpty()) {
                builder.put("resize", String.join(",", params));
            }
            return this;
        }

        @Override
        public String build() {
            List<String> list = builder
                .entrySet()
                .stream()
                .map(et -> et.getKey() + "," + et.getValue())
                .collect(Collectors.toList());
            String query = "image_process=" + String.join("/", list);
            return HttpRequestUtil.addUriQuery(this.url, query).toString();
        }
    }
}
