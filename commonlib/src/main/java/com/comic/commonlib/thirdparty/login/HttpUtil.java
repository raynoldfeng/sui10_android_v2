package com.comic.commonlib.thirdparty.login;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    //get request
    public static void GetHttpRequest(String url, HashMap<String, String> params, okhttp3.Callback callback) {
        String address = url + AssemblyParams(params).toString();
        OkHttpClient client = new OkHttpClient();
        client.newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    //组装参数
    static StringBuilder AssemblyParams(final HashMap<String, String> params) {
        StringBuilder assmbly = new StringBuilder();
        assmbly.append("?");
        Iterator iter =  params.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>)iter.next();
            String key = (String)entry.getKey();
            String value = (String)entry.getValue();
            assmbly.append(key + "=" + value);
            if (iter.hasNext()) {
                assmbly.append("&");
            }
        }
        return assmbly;
    }
}
