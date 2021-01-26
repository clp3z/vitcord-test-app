package com.clp3z.vitcord.mvp.base.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.clp3z.vitcord.mvp.base.global.Constants.RETROFIT_LOG_LEVEL;

/**
 * Created by Clelia López on 6/16/18
 */
public class Client {

    /**
     * Attributes
     */
    private static final String host = "https://interview-api-instance.herokuapp.com/";
    private static RestAPIServer restAPIServer = null;
    private static Retrofit retrofit;
    private static boolean hasHostChanged = false;


    private Client(Retrofit retrofit) {
        restAPIServer = retrofit.create(RestAPIServer.class);
    }

    public static void changeBaseHost(String newHost) {
        hasHostChanged = true;
        retrofit = new Retrofit.Builder()
            .baseUrl(newHost)
            .client(getOkHttpBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    public static void returnToBaseHost() {
        hasHostChanged = false;
    }

    private static OkHttpClient.Builder getOkHttpBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(getInterceptor());
        return builder;
    }

    private static HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(RETROFIT_LOG_LEVEL);
        return loggingInterceptor;
    }

    private static Retrofit getDefaultRetrofitBuilder () {
        return new Retrofit.Builder()
            .baseUrl(host)
            .client(getOkHttpBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    static RestAPIServer getRestAPIService() {
        if (restAPIServer == null || !hasHostChanged)
            new Client(getDefaultRetrofitBuilder());
        return restAPIServer;
    }

    public static RestAPIServer getTestAPIService() {
        if (restAPIServer == null || hasHostChanged)
            new Client(retrofit);
        return restAPIServer;
    }
}
