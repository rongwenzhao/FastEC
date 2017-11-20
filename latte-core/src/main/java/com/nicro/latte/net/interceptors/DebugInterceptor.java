package com.nicro.latte.net.interceptors;

import android.support.annotation.RawRes;

import com.nicro.latte.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by rongwenzhao on 2017/11/20.
 */

public class DebugInterceptor extends BaseInterceptor {

    /**
     * 需要拦截的url
     */
    private final String DEBUG_URL;
    /**
     * 本项目使用的服务器返回的json，都是拦截器处理之后，从应用raw目录下的文件获取的；
     * 本屬性对应raw下的文件的raw ID，R资源里面
     */
    private final int DEBUG_RAW_ID;

    public DebugInterceptor(String debug_url, int debug_raw_id) {
        DEBUG_URL = debug_url;
        DEBUG_RAW_ID = debug_raw_id;
    }

    /**
     * 模拟生成网络返回请求
     *
     * @param chain
     * @param json
     * @return
     */
    private Response getResponse(Chain chain, String json) {
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type", "application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"), json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    private Response debugResponse(Chain chain, @RawRes int rawId) {
        final String json = FileUtil.getRawFile(rawId);
        return getResponse(chain, json);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String url = chain.request().url().toString();
        if (url.contains(DEBUG_URL)) {
            return debugResponse(chain, DEBUG_RAW_ID);
        } else {
            return chain.proceed(chain.request());
        }
    }
}
