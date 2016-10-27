package com.example.jaelyen.mly_bmob;

import android.app.Application;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Created by Administrator
 * Project: MLY_Bmob
 * Date: 2016/10/26 0026
 * Time: 10:09.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //第一：默认初始化
        //Bmob.initialize(this,"");
        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        .setApplicationId("940b857c411444694f2253fd7da3c9a5")
        ////请求超时时间（单位为秒）：默认15s
        .setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        .setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        .setFileExpiration(2500)
        .build();
        Bmob.initialize(config);
    }
}
