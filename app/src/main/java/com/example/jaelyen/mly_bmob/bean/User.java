package com.example.jaelyen.mly_bmob.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator
 * Project: MLY_Bmob
 * Date: 2016/10/26 0026
 * Time: 11:30.
 */

public class User extends BmobUser {
    private BmobFile icon;

    public BmobFile getIcon() {
        return icon;
    }

    public void setIcon(BmobFile icon) {
        this.icon = icon;
    }
}
