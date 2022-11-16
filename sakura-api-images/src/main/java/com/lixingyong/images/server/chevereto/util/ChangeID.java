package com.lixingyong.images.server.chevereto.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.Assert;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * chevereto 加解密工具类
 *
 * @author LIlGG
 * @since 2022-10-18
 */
public class ChangeID {
    private final String index;
    public static final int ID_PADDING = 5000;

    public final static char[] DICTIONARIES =
        new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t',
            'u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F',
            'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    public ChangeID(String cryptSalt) {
        Assert.notNull(cryptSalt, "请检查配置 cryptSalt");
        char[] i = new char[DICTIONARIES.length];
        System.arraycopy(DICTIONARIES, 0, i, 0, DICTIONARIES.length);
        String passhash = DigestUtils.sha256Hex(cryptSalt);
        passhash = (passhash.length() < DICTIONARIES.length)
            ?  DigestUtils.sha256Hex(cryptSalt) : passhash;
        char[] p = passhash.substring(0, i.length).toCharArray();
        sortDic(p, i);
        this.index = new String(i);
    }

    /**
     * 将字符串解码为 ID
     * @param in
     * @return
     */
    public long decodeID(String in) {
        int base = index.length();
        long out = 0;
        int len = in.length() - 1;
        for (int t = 0; t <= len; ++t) {
            long bcpow = (long) Math.pow(base, len - t);
            out = out + index.indexOf(in.charAt(t)) * bcpow;
        }
        if (ID_PADDING > 0) {
            out = out / ID_PADDING;
        }
        return out;
    }

    public String encodeID(long in) {
        int base = index.length();
        StringBuilder out = new StringBuilder();
        if(ID_PADDING > 0) {
            in = in * ID_PADDING;
        }
        for(double t = Math.floor( Math.log(in) / Math.log(base)); t >= 0; --t) {
            double bcp = Math.pow(base, t);
            double a = Math.floor((double) in / bcp) % base;
            out.append(index.charAt((int) a));
            in = (long) (in - (a * bcp));
        }
        return out.toString();
    }

    /**
     * 基于字典的排序
     * 两个数组，第二个数组将基于第一个数组的排序方式进行排序
     * 要求：1. 两个数组必须一样长 2. 第二个数组不能有相同的项
     */
    public static void sortDic(char[] c, char[] i) {
        if(c.length != i.length) {
            throw new IllegalArgumentException("数组长度不一致");
        }

        HashMap<Character, Character> map = new HashMap<>();
        for(int n = 0; n < c.length; ++n) {
            map.put(i[n], c[n]);
        }

        // 对 map 根据 value 进行重排
        List<Map.Entry<Character,Character>> list = sortByValueFloatDesc(map);
        if(list.size() < c.length) {
            throw new ArithmeticException("运算出错");
        }

        for(int n = 0; n < list.size(); ++n) {
            Map.Entry<Character,Character> entry = list.get(n);
            i[n] = entry.getKey();
            c[n] = entry.getValue();
        }
    }

    /**
     * 将集合按照降序排列
     * @param nowPartTwoData
     * @return
     */
    private static List<Map.Entry<Character,Character>> sortByValueFloatDesc(
        Map<Character, Character> nowPartTwoData
    ){
        //这里将map.entrySet()转换成list
        List<Map.Entry<Character,Character>> list = new ArrayList<>(nowPartTwoData.entrySet());
        //降序排序
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        return list;
    }
}
