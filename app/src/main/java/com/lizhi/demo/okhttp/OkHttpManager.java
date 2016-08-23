package com.lizhi.demo.okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.lizhi.demo.okhttp.request.BaseRequest;
import com.lizhi.demo.okhttp.request.UrlEnum;
import com.lizhi.demo.utils.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2016/3/24.
 */
public class OkHttpManager {


    public volatile static OkHttpManager manager;
    OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    public static final MediaType POST_TYPE = MediaType.parse("application/x-www-form-urlencoded");


    private OkHttpManager() {
        mOkHttpClient = new OkHttpClient();
        //cookie enabled
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public static OkHttpManager getManager() {
        if (manager == null) {
            synchronized (OkHttpManager.class) {
                if (manager == null) {
                    manager = new OkHttpManager();
                }

            }
        }
        return manager;
    }

    public static final String NET_NO_MSG = "没有网络连接";
    public static final String NET_ERR_MSG = "网络错误";
    public static final String DOWN_SUCCESS = "下载成功";

    /**
     * get 或者 post 提交数据
     *
     * @param baseRequest
     * @param onHttpCallBack
     */
    public Call httpData(final BaseRequest baseRequest, final OnHttpCallBack onHttpCallBack) {
        Call call = null;
        try {
            /******一种写法**这个方法开启了线程可以在住线程里直接调用*******/
            call = mOkHttpClient.newCall(createRequest(baseRequest));
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (onHttpCallBack != null) {
                        //没网
                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                onHttpCallBack.onFail(NET_NO_MSG, Constant.NO_NET_CODE);
                            }
                        });
                    }
                }

                @SuppressWarnings({"unchecked", "varargs"})
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String jsonStr = response.body().string();
                    if (onHttpCallBack != null) {
                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                if (response.isSuccessful()) {
                                    String contentJson = getContentJson(jsonStr);
                                    BaseResponse baseResponse = (BaseResponse) baseRequest.baseParser.parser(contentJson);
                                    if (Constant.NET_OK_CODE == baseResponse.businessCode) {
                                        onHttpCallBack.onSuccess(baseRequest.baseParser.parser(contentJson), jsonStr, baseResponse.businessCode);
                                    } else {
                                        onHttpCallBack.onFail(baseResponse.message, baseResponse.businessCode);
                                    }
                                } else {
                                    onHttpCallBack.onFail(NET_ERR_MSG, response.code());
                                }
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return call;
    }


    public final String CONTENT_KEY = "content";

    public String getContentJson(String allJson) {
        LogUtil.log("-返回->" + allJson);
        String content = "";
        JSONObject jsonObject = JSONObject.parseObject(allJson);
        content = jsonObject.getString(CONTENT_KEY);
        return content;
    }


    /**
     * 下载数据
     *
     * @param baseRequest
     * @param onHttpCallBack
     */
    public Call httpDownload(final BaseRequest baseRequest, final OnHttpCallBack onHttpCallBack) {
        Call call = mOkHttpClient.newCall(createRequest(baseRequest));
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (onHttpCallBack != null) {
                    //没网
                    mDelivery.post(new Runnable() {
                        @Override
                        public void run() {
                            onHttpCallBack.onFail(NET_NO_MSG, Constant.NO_NET_CODE);
                        }
                    });
                }
            }
            @SuppressWarnings({"unchecked", "varargs"})
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (onHttpCallBack != null) {
                    final byte data[] = response.body().bytes();
                    mDelivery.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful()) {
                                onHttpCallBack.onSuccess(data, DOWN_SUCCESS, response.code());
                            } else {
                                onHttpCallBack.onFail(NET_ERR_MSG, response.code());
                            }
                        }
                    });
                }
            }
        });
        return call;
    }


    /**
     * 同步请求数据
     */
    @SuppressWarnings({"unchecked", "varargs"})
    public void syncHttpData(final BaseRequest baseRequest, final OnHttpCallBack onHttpCallBack) {
        try {
            Response response = mOkHttpClient.newCall(createRequest(baseRequest)).execute();
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                final String jsonStr = responseBody.string();
                if (onHttpCallBack != null) {
                    onHttpCallBack.onSuccess(baseRequest.baseParser.parser(jsonStr), jsonStr, response.code());
                }
            } else {
                if (onHttpCallBack != null) {
                    LogUtil.log("--错误Code-->" + response.code());
                    onHttpCallBack.onFail(null, response.code());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 生成 okHttp 需要的request
     *
     * @param baseRequest
     * @return
     */
    public Request createRequest(BaseRequest baseRequest) {
        String urlString = "";
        Request request = null;
        if (UrlEnum.DOWN_IMG_URL.url.equals(baseRequest.urlEnum.url)) {
            urlString = baseRequest.urlEnum.url;
        } else {
            urlString = Constant.HOST_URL + baseRequest.urlEnum.url;
        }
        if (UrlEnum.RequestMethod.GET.equals(baseRequest.urlEnum.requestMethod)) {
            //GET
            Map<String, Object> params = baseRequest.params;
            if (params != null) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("?");
                stringBuffer.append(changeParams2String(params));
                urlString = urlString + stringBuffer;
            }
            LogUtil.log("--GET-请求地址->" + urlString);
            request = new Request.Builder().url(urlString).build();
        } else if (UrlEnum.RequestMethod.POST.equals(baseRequest.urlEnum.requestMethod)) {
            //POST
            LogUtil.log("--POST-请求地址->" + urlString);
            Map<String, Object> params = baseRequest.params;
            RequestBody body = null;
            if (params != null) {
                String postStr = changeParams2String(params).toString();
                body = RequestBody.create(POST_TYPE, postStr);
            } else {
                body = RequestBody.create(POST_TYPE, "");
            }
            request = new Request.Builder()
                    .url(urlString)
                    .post(body)
                    .build();
        }
        return request;
    }


    public StringBuffer changeParams2String(Map<String, Object> params) {
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object values = entry.getValue();
            LogUtil.log("参数：" + key + "值：" + values);
            if (values != null) {
                stringBuffer.append(key + "=" + values).append("&");
            }
        }
        if (stringBuffer.length() >= 1) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        }
        return stringBuffer;
    }


    public interface OnHttpCallBack<T> {
        /**
         * @param t       返回泛型
         * @param jsonStr 返回json数据
         * @param code    返回状态码
         */
        @SuppressWarnings({"unchecked", "varargs"})
        public void onSuccess(T t, String jsonStr, int code);

        /**
         * @param errMsg 错误信息
         * @param code   错误码
         */
        public void onFail(String errMsg, int code);
    }


    public String changeInputstream2String(InputStream inputStream) {
        String s = null;
        if (inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            try {
                while ((s = reader.readLine()) != null) {
                    LogUtil.log("------readLine------->");
                    builder.append(s + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return s;
    }

}
