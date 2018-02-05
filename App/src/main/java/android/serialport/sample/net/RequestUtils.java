package android.serialport.sample.net;


import android.serialport.sample.util.LogUtil;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


/**
 * 服务端请求工具
 */
public class RequestUtils {

    /**
     * @param url           请求地址
     * @param requestParams 请求参数
     * @param callBack      回调
     */
    public static <T> void sendGetRequest(String url, final RequestParams requestParams, @NonNull ResponseCallBack<T> callBack) {
        sendRequest(HttpMethod.GET, url, requestParams, callBack);
    }

    public static <T> void sendPostRequest(String url, final RequestParams requestParams, @NonNull ResponseCallBack<T> callBack) {
        sendRequest(HttpMethod.POST, url, requestParams, callBack);
    }

    public static <T> void sendRequest(HttpMethod method, String url, final RequestParams requestParams,
                                       @NonNull final ResponseCallBack<T> callBack) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

//        url = "https://api.netease.im/nimserver" + url;     //加上域名部分
//        url = "https://api.tudouni.doubozhibo.com" + url;     //加上域名部分

        requestParams.setUri(url);
        requestParams.setConnectTimeout(3 * 1000);
//        requestParams.setCacheMaxAge(1000);
//        requestParams.setCacheSize(1024 * 1024);
        requestParams.setReadTimeout(3 * 1000);
        requestParams.setMaxRetryCount(2);
        requestParams.setCharset("utf-8");

        LogUtil.d("RequestUtils", "request : " + requestParams.toString());
        x.http().request(method, requestParams, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {

                LogUtil.d("RequestUtils", "response: " + result);
                try {
                    T data = callBack.parse(result);
                    callBack.onSuccess(data);
                } catch (ServiceException e) {
                    callBack.onFailure(e);
                } catch (Throwable e) {
                    //这里先捕获了异常，防止被xutils捕获然后走onError回调
//                    Toast.makeText(App.sCurrActivity, "系统繁忙", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    callBack.onFailure(new ServiceException("解析失败"));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof ServiceException) {
                    //checknetwork();
                    callBack.onFailure((ServiceException) ex);
                } else if (ex instanceof ConnectException || ex instanceof SocketException) {
                    callBack.onFailure(new ServiceException("连接异常，请检查网络"));
                } else if (ex instanceof UnknownHostException || ex instanceof SocketTimeoutException
                        || ex instanceof IOException) {
                    callBack.onFailure(new ServiceException("网络连接失败，请重试"));
                } else {
                    callBack.onFailure(new ServiceException(ex.getMessage()));
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onFailure(new ServiceException(cex.getMessage()));
            }

            @Override
            public void onFinished() {
            }
        });


    }

}
