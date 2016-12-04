package com.example.xiyou.twolearntodo.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.xiyou.twolearntodo.model.RequestModel;

/**
 * Created by maple on 2016/7/30.
 */
public class VolleyErrorHelper {

    public static RequestModel<String> getMessage(Object error, Context context) {
        RequestModel<String> errorInfo = new RequestModel<>();
        errorInfo.setData(null);
        if (error instanceof TimeoutError) {
            errorInfo.setMsg("网络超时,请刷新重试");
            errorInfo.setStatus(-300);

        } else if (isServerProblem(error)) {
            errorInfo.setMsg(handleServerError(error, context));
            errorInfo.setStatus(-200);
        } else if (isNetworkProblem(error)) {
            errorInfo.setMsg("网络连接失败");
            errorInfo.setStatus(-100);

        }
        return errorInfo;
    }


    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError)
                || (error instanceof NoConnectionError);
    }


    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError)
                || (error instanceof AuthFailureError);
    }


    private static String handleServerError(Object err, Context context) {
        VolleyError error = (VolleyError) err;

        NetworkResponse response = error.networkResponse;

        if (response != null) {
            if (200 != response.statusCode) {
                return "服务器没有响应";
            }
        }

        return "服务器异常";
    }
}
