package com.sui10.commonlib.network.interceptor;

import com.sui10.commonlib.base.config.TestEnvironment;
import com.sui10.commonlib.base.constants.HostConstant;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HostInterceptor implements Interceptor {
    private static HashMap<String, String> hostMap;
    private static HashMap<String, String> schemeMap;

    public HostInterceptor() {
        initHostMap();
    }

    private void initHostMap() {
        if (hostMap == null && schemeMap == null) {
            hostMap = new HashMap<>();
            schemeMap = new HashMap<>();
            Field[] fields = HostConstant.class.getDeclaredFields();
            if (fields == null) {
                return;
            }
            for (Field field : fields) {
                try {
                    Object o = field.get(HostConstant.class);
                    if (o instanceof String) {
                        Annotation annotation = field.getAnnotation(TestEnvironment.class);
                        if (annotation != null) {
                            String prod = (String) o;
                            String test = ((TestEnvironment) annotation).value();
                            HttpUrl prodUrl = HttpUrl.parse(prod);
                            HttpUrl testUrl = HttpUrl.parse(test);
                            if (prodUrl != null && testUrl != null) {
                                hostMap.put(prodUrl.host(), testUrl.host());
                                schemeMap.put(testUrl.host(), testUrl.scheme());
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        HttpUrl url = chain.request().url();
        String oldHost = url.host();
        if (hostMap.containsKey(oldHost)) {
            HttpUrl.Builder urlBuilder = url.newBuilder();
            String newHost = hostMap.get(oldHost);
            urlBuilder.host(newHost).scheme(schemeMap.get(newHost));
            requestBuilder.url(urlBuilder.build());
            return chain.proceed(requestBuilder.build());
        }
        return chain.proceed(chain.request());
    }
}

