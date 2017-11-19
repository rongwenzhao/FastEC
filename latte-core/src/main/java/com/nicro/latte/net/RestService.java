package com.nicro.latte.net;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by rongwenzhao on 2017/11/19.
 * 此处**Service其实是对Rest API的一个映射关系，
 * 在实际开发中，我们可以定义：public interface RestService，里面包含post ,get 方法。
 */

public interface RestService {

    /**
     * get请求,其中QueryMap就是键值对，在get请求中，会把键值对里面的参数自动拼接到url里面(与请求浏览器效果一样)。
     *
     * @param url
     * @param params
     * @return
     */
    @GET
    Call<String> get(@Url String url, @QueryMap Map<String, Object> params);

    /**
     * post请求。@FormUrlEncoded注解用来发送表单数据；可以使用@Field注解添加表单项；@FieldMap表单数据集。
     *
     * @param url
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap Map<String, Object> params);

    /**
     * post一个原始数据，指字符串
     *
     * @param url
     * @param body
     * @return
     */
    @POST
    Call<String> postRaw(@Url String url, @Body RequestBody body);

    /**
     * put请求，类似post
     *
     * @param url
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST
    Call<String> put(@Url String url, @FieldMap Map<String, Object> params);

    /**
     * put一个原始数据，指字符串
     *
     * @param url
     * @param body
     * @return
     */
    @POST
    Call<String> putRaw(@Url String url, @Body RequestBody body);

    /**
     * 请求删除数据(没用过)
     *
     * @param url
     * @param params
     * @return
     */
    @DELETE
    Call<String> delete(@Url String url, @QueryMap Map<String, Object> params);

    /**
     * 下载请求，Retrofit默认是直接把文件下载到内存，加@Streaming注解，
     * 可以以文件流方式一块块写到本地，需要放在单独线程异步处理。
     *
     * @param url
     * @param params
     * @return
     */
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String url, @QueryMap Map<String, Object> params);

    /**
     * 注：@Multipart发送Multipart数据,使用@Part注解定义要发送的每个文件
     *
     * @param url
     * @param file
     * @return
     */
    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);

}
