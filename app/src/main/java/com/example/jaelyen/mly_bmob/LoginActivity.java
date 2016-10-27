package com.example.jaelyen.mly_bmob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaelyen.mly_bmob.bean.User;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    private EditText editText_username2;
    private EditText editText_password2;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText_username2 = (EditText) findViewById(R.id.editText_username2);
        editText_password2 = (EditText) findViewById(R.id.editText_password2);
    }
    public void loginClick(View view){
        String username = editText_username2.getText().toString();
        String password = editText_password2.getText().toString();
        if (TextUtils.isEmpty(username)) {
            toast("用户名或手机号不能为空");
            return;
        }else if (TextUtils.isEmpty(password)) {
            toast("请输入密码");
            return;
        }
        User user = new User();
        user.setUsername(username);
        Log.i(TAG, "loginClick: ");
        user.setPassword(password);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    toast("登录成功");
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    private void toast(String info){
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
}
