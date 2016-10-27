package com.example.jaelyen.mly_bmob.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator
 * Project: MLY_Bmob
 * Date: 2016/10/26 0026
 * Time: 16:21.
 */

public class MyNote extends BmobObject {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
