package com.nicro.latte.net;

import com.nicro.latte.app.ConfigKeys;
import com.nicro.latte.app.Latte;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by rongwenzhao on 2017/11/19.
 * 返回RestService实现类的创建者，单例实现
 */

public class RestCreator {

    /**
     * 提供全局params实现
     */
    private static final class ParamsHolder {
        private static final WeakHashMap<String, Object> params = new WeakHashMap<>();
    }

    public static WeakHashMap<String, Object> getParams() {
        return ParamsHolder.params;
    }

    /**
     * 4、提供获取RestService的接口
     *
     * @return
     */
    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    /**
     * 2、编写构建Retrofit代码
     * 静态内部类实现单例
     */
    private static final class RetrofitHolder {
        private static final String BASE_URL = (String) Latte.getConfiguration(ConfigKeys.API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())//转换工厂，来自com.squareup.retrofit2:converter-scalars
                .build();
    }

    /**
     * 1、编写创建okhttpClient代码
     */
    private static final class OKHttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = (ArrayList<Interceptor>) Latte.getConfiguration(ConfigKeys.INTERCEPTOR);

        private static OkHttpClient.Builder addInterceptor() {
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()) {
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * 3、创建RestService
     */
    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }
}
