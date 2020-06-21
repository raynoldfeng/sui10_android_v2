package com.sui10.commonlib.network.manager;


import com.sui10.commonlib.BuildConfig;
import com.sui10.commonlib.base.config.BuildHelper;
import com.sui10.commonlib.network.interceptor.HostInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static RetrofitManager mInstance;
    private Retrofit mRetrofitObj = null;
    private final static Object mRetrofitLock = new Object();


    public static RetrofitManager getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitManager();
                }
            }
        }
        return mInstance;
    }

    private  Retrofit getRetrofit(){
        if(mRetrofitObj == null){
            synchronized (mRetrofitLock){
                if(mRetrofitObj == null){
                    OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        clientBuilder.addInterceptor(httpLoggingInterceptor);
                    }

                    if (BuildHelper.isTest()) {
                        clientBuilder.addInterceptor(new HostInterceptor());
                    }

                    clientBuilder.addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Interceptor.Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder()
                                    .removeHeader("User-Agent")
                                    .addHeader("user_agent", "android@0.0.1@123@official@dasdsa-sadasd-asd")
                                    .build();
                            return chain.proceed(request);
                        }
                    });
                    clientBuilder.connectTimeout(15, TimeUnit.SECONDS);
                    clientBuilder.readTimeout(30, TimeUnit.SECONDS);
                    clientBuilder.writeTimeout(15, TimeUnit.SECONDS);

                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();

                    mRetrofitObj = new Retrofit.Builder().client(clientBuilder.build())
                            .baseUrl("https://api.github.com/")
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }

        }

        return mRetrofitObj;
    }

    public <T> T get(Class<T> tClass) {
        return getRetrofit().create(tClass);
    }

//    private static SSLSocketFactory sslSocketFactory(X509TrustManager x509Manager) {
//
//        try {
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null, new TrustManager[]{x509Manager}, new SecureRandom());
//            return sslContext.getSocketFactory();
//        } catch (NoSuchAlgorithmException | KeyManagementException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public void clearResource() {
        mRetrofitObj = null;
        mInstance = null;
    }


}
