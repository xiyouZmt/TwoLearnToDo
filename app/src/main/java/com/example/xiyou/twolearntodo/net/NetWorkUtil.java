package com.example.xiyou.twolearntodo.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

/**
 * Created by maple on 2016/9/10.
 */
public class NetWorkUtil {

    //判断网络状态
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isAvailable();
    }


    public static String pingHost(){
        //这里设置ping命令检查网络是否连通，我们
        String str = "www.baidu.com";
        String resault="";
        try {
            // TODO: Hardcoded for now, make it UI configurable
            Process p = Runtime.getRuntime().exec("ping -c 1 -w 100 " +str);
            int status = p.waitFor();
            if (status == 0) {
                //  mTextView.setText("success") ;
                resault="success";
            }
            else
            {
                resault="faild";
                //  mTextView.setText("fail");
            }
        } catch (IOException e) {
            //  mTextView.setText("Fail: IOException"+"\n");
        } catch (InterruptedException e) {
            //  mTextView.setText("Fail: InterruptedException"+"\n");
        }

        return resault;
    }


}
