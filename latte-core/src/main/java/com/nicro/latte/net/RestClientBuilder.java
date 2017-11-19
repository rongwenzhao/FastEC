package com.nicro.latte.net;

import android.content.Context;

import com.nicro.latte.net.callback.IError;
import com.nicro.latte.net.callback.IFailure;
import com.nicro.latte.net.callback.IRequest;
import com.nicro.latte.net.callback.ISuccess;
import com.nicro.latte.ui.LoaderStyle;

import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by rongwenzhao on 2017/11/19.
 * 构造RestClient对象
 */

public class RestClientBuilder {

    private String mUrl = null;
    private WeakHashMap<String, Object> mParams = RestCreator.getParams();
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IFailure mIFailure = null;
    private IError mIError = null;
    private RequestBody mBody = null;
    private LoaderStyle mLoaderStyle = null;
    private Context mContext = null;

    /**
     * default访问权限，支持同类与同包中访问
     * (此处，只支持同包的RestClient来创建，包外的，不能创建)
     */
    RestClientBuilder() {
    }

    /**
     * 下面代码，对RestClient对象的各个属性进行赋值，builder模式构造
     */
    public final RestClientBuilder url(String url) {
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        this.mParams = params;
        return this;
    }

    /**
     * 因为传递的参数是允许丢失的，所以用WeakHashMap是合适的
     *
     * @param key
     * @param value
     * @return
     */
    public final RestClientBuilder params(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    /**
     * 传入原始数据，即字符串
     *
     * @param raw
     * @return
     */
    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    /**
     * 初始化加载框的样式
     *
     * @param context
     * @param loaderStyle
     * @return
     */
    public final RestClientBuilder loader(Context context, LoaderStyle loaderStyle) {
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
        return this;
    }

    /**
     * 使用默认加载框的样式
     *
     * @param context
     * @return
     */
    public final RestClientBuilder loader(Context context) {
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    /**
     * public RestClient(String URL,
     * WeakHashMap<String, Object> PARAMS,
     * IRequest REQUEST,
     * ISuccess SUCCESS,
     * IFailure FAILURE,
     * IError ERROR,
     * RequestBody BODY) {
     *
     * @return
     */

    public final RestClient build() {
        return new RestClient(mUrl, mParams,
                mIRequest, mISuccess,
                mIFailure, mIError,
                mBody, mLoaderStyle, mContext);
    }
}
