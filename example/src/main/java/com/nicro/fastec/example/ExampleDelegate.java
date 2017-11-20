package com.nicro.fastec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.nicro.latte.app.Latte;
import com.nicro.latte.delegates.LatteDelegate;
import com.nicro.latte.net.RestClient;
import com.nicro.latte.net.callback.IError;
import com.nicro.latte.net.callback.IFailure;
import com.nicro.latte.net.callback.IRequest;
import com.nicro.latte.net.callback.ISuccess;

import java.util.WeakHashMap;

/**
 * Created by rongwenzhao on 2017/11/18.
 */

public class ExampleDelegate extends LatteDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        restClientTest();
    }

    private void restClientTest() {
        RestClient restClient = RestClient.builder()
                .url("http://127.0.0.1/index")
                .loader(getContext())
                .raw("")
                .params("", "")
                .params(new WeakHashMap<String, Object>())
                .onRequest(new IRequest() {
                    @Override
                    public void onRequestStart() {

                    }

                    @Override
                    public void onRequestEnd() {

                    }
                })
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(Latte.getApplicationContext(), " " + response, Toast.LENGTH_SHORT).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .build();
        restClient.get();
    }
}
