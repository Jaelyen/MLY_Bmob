package com.example.jaelyen.mly_bmob.bean;


import com.example.jaelyen.mly_bmob.bean.BaseBean;

/**
 * Created by Administrator
 * Project: MLY_Bmob
 * Date: 2016/10/26 0026
 * Time: 10:16.
 */

public class Person extends BaseBean {
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
