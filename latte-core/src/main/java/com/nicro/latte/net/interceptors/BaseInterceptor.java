package com.nicro.latte.net.interceptors;

import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * Created by rongwenzhao on 2017/11/20.
 */

public abstract class BaseInterceptor implements Interceptor {

    /**
     * BaseInterceptor中提供从get，post请求中获取参数的方法,
     * getUrlParameters从get请求中获取；
     */

    protected LinkedHashMap<String, String> getUrlParameters(Chain chain) {
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        final HttpUrl url = chain.request().url();
        int size = url.querySize();//返回全部参数数目
        for (int i = 0; i < size; i++) {
            params.put(url.queryParameterName(i), url.queryParameterValue(i));
        }

        return params;
    }

    protected String getUrlParameters(Chain chain, String key) {
        Request request = chain.request();
        return request.url().queryParameter(key);
    }

    /**
     * 从post请求体中获取请求参数
     *
     * @param chain
     * @return
     */
    protected LinkedHashMap<String, String> getBodyParameters(Chain chain) {
        final FormBody formBody = (FormBody) chain.request().body();
        final LinkedHashMap<String, String> params = new LinkedHashMap<>();
        int size = formBody.size();
        for (int i = 0; i < size; i++) {
            params.put(formBody.name(i), formBody.value(i));
        }
        return params;

    }

    /**
     * 根据key，从formbody中获取参数value
     *
     * @param chain
     * @param key
     * @return
     */
    protected String getBodyParameters(Chain chain, String key) {
        return getBodyParameters(chain).get(key);
    }

}
