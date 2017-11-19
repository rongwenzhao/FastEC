package com.nicro.latte.net.download;

import android.os.AsyncTask;

import com.nicro.latte.net.RestCreator;
import com.nicro.latte.net.callback.IError;
import com.nicro.latte.net.callback.IFailure;
import com.nicro.latte.net.callback.IRequest;
import com.nicro.latte.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rongwenzhao on 2017/11/20.
 */

public class DownloadHandler {
    private final String URL;
    private final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    //下载文件需要的变量
    private final String DOWNLOAD_DIR;//下载文件的存放目录
    private final String EXTENSION;//文件后缀名
    private final String NAME;//下载后的文件名

    public DownloadHandler(String url,
                           IRequest request,
                           ISuccess success,
                           IFailure failure,
                           IError error,
                           String download_dir,
                           String extension,
                           String name) {
        URL = url;
        REQUEST = request;
        SUCCESS = success;
        FAILURE = failure;
        ERROR = error;
        DOWNLOAD_DIR = download_dir;
        EXTENSION = extension;
        NAME = name;
    }

    public final void handleDownload() {
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        RestCreator.getRestService().download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            final ResponseBody body = response.body();
                            final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                            //task在线程池中执行，而task.execute()是在队列中执行
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, body, NAME);

                            //注意，在调用回调之前，做个task是否已经已经被取消的判断，
                            // 否则，可能在回调中处理时，而文件还没有下载完整
                            if (task.isCancelled()) {
                                if (REQUEST != null) {
                                    REQUEST.onRequestEnd();
                                }
                            }
                        } else {
                            if (ERROR != null) {
                                ERROR.onError(response.code(), response.message());
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null) {
                            FAILURE.onFailure();
                        }

                    }
                });
    }
}
