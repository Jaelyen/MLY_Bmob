package com.example.jaelyen.mly_bmob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaelyen.mly_bmob.bean.User;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText editText_username;
    private EditText editText_validCode;
    private EditText editText_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editText_username = (EditText) findViewById(R.id.editText_username);
        editText_validCode = (EditText) findViewById(R.id.editText_validCode);
        editText_password = (EditText) findViewById(R.id.editText_password);
    }

    //获取验证码
    public void getValidCodeClick(View view) {
        String username = editText_username.toString();
        if (TextUtils.isEmpty(username)) {
            toast("用户名不能为空");
            return;
        }
        requestValidCode(username);
    }

    //发送请求验证码
    private void requestValidCode(String username) {
        BmobSMS.requestSMSCode(username, "磨砺营", new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e != null) {
                    toast("获取验证码失败，请重新获取-" + integer);
                }
            }
        });
    }

    //登录
    public void loginClick(View view) {
        User currentUser = BmobUser.getCurrentUser(User.class);
        if (currentUser != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    //注册
    public void registerClick(View view) {
        String validCode = editText_validCode.getText().toString();
        final String username = editText_username.getText().toString();
        final String password = editText_password.getText().toString();
        if (TextUtils.isEmpty(validCode)) {
            toast("请输入验证码");
            return;
        }
        BmobSMS.verifySmsCode(username, validCode, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //开始注册
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null) {
                                toast("注册成功");
                            }
                        }
                    });
                }
            }
        });
    }

    //封装toast()方法
    private void toast(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
}
