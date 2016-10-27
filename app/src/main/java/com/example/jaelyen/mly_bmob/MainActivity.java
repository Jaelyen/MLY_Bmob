package com.example.jaelyen.mly_bmob;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jaelyen.mly_bmob.bean.Person;
import com.example.jaelyen.mly_bmob.bean.User;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class MainActivity extends AppCompatActivity {

    private static final int GET_IMAGE_CODE = 0X1;
    private static final String TAG = "MainActivity";
    private ImageView imageView_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView_icon = (ImageView) findViewById(R.id.imageView);
        //loadUserInfo();
    }

    //添加数据
    public void addClick(View view) {
        Person p1 = new Person();
        p1.setName("jaelyen");
        p1.setAddress("北京昌平");
        //异步过程
        p1.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    toast("数据添加成功" + s);
                } else {
                    toast("数据添加失败-" + e.getMessage());
                }
            }
        });
    }

    //更新数据
    public void updateClick(View view) {
        final Person p2 = new Person();
        p2.setName("Mike");
        p2.setAddress("北京朝阳");
        p2.update("e448494b15", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    toast("更新成功" + p2.getUpdatedAt());
                } else {
                    toast("更新失败-" + e.getMessage());
                }
            }
        });
    }

    //删除数据
    public void deleteClick(View view) {
        final Person p3 = new Person();
        p3.setObjectId("c47c39c3d8");
        p3.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    toast("删除成功" + p3.getUpdatedAt());
                } else {
                    toast("删除失败" + e.getMessage());
                }
            }
        });
    }

    //查询数据
    public void queryClick(View view) {
        BmobQuery<Person> bmobQuery = new BmobQuery<>();
        //查询一个对象
        bmobQuery.getObject("e448494b15", new QueryListener<Person>() {
            @Override
            public void done(Person person, BmobException e) {
                if (e == null) {
                    toast("查找数据成功" + person);
                } else {
                    toast("查找数据失败-" + e.getMessage());
                }
            }
        });
    }

    //查询所有数据
    public void queryAllClick(View view) {
        BmobQuery<Person> bmobQuery = new BmobQuery<>();
        //查询所有对象
        bmobQuery.findObjects(new FindListener<Person>() {
            @Override
            public void done(List<Person> list, BmobException e) {
                toast(list.toString());
            }
        });
    }

    //分页查询
    public void queryPerPageClick(View view){
        final BmobQuery<Person> bmobQuery = new BmobQuery<>();
        bmobQuery.setSkip(0);
        bmobQuery.setLimit(5);
        //降序查询
        bmobQuery.order("-createdAt").findObjects(new FindListener<Person>() {
            @Override
            public void done(List<Person> list, BmobException e) {
                for (Person person:list) {
                    System.out.println(person);
                }
            }
        });
    }
    //修改密码
    public void updatePasswordClick(View view){
        User user = (User) BmobUser.getCurrentUser();
        BmobUser.updateCurrentUserPassword("123456", "654321", new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    toast("更新密码成功");
                }else {
                    toast("更新密码失败");
                }
            }
        });
    }
    //更新上传头像
    public void uploadIconCLick(View view){
        //打开相册
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,GET_IMAGE_CODE);
    }
    //退出当前账号
    public void exitClick(View view){
        BmobUser.logOut();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
    //便签
    public void myNoteClick(View view){
        startActivity(new Intent(this,NoteActivity.class));
    }
    //获取图片详细信息
    private void loadUserInfo() {
        User currentUser = BmobUser.getCurrentUser(User.class);
        BmobFile icon = currentUser.getIcon();
        //Log.i(TAG, "loadUserInfo: "+icon.getFileUrl());
        VolleyUtils.getInstance(this).loadImage(icon.getFileUrl(),72,72,imageView_icon);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case RESULT_OK:
                if (requestCode == GET_IMAGE_CODE) {
                    Uri uri = data.getData();
                    Log.i(TAG, "onActivityResult: "+uri);
                    Cursor c = getContentResolver().query(uri,new String[]{MediaStore.Images.Thumbnails.DATA},null,null,null,null);
                    if (c!=null && c.moveToFirst()) {
                        String imageUri = c.getString(0);
                        Log.i(TAG, "onActivityResult: "+imageUri);
                        uploadIcon(imageUri);
                    }
                    c.close();
                }
                break;
        }
    }
    //上传图片
    private void uploadIcon(String imageUri) {
        final BmobFile file = new BmobFile(new File(imageUri));
        file.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                User currentUser = BmobUser.getCurrentUser(User.class);
                currentUser.setIcon(file);
                currentUser.update(currentUser.getObjectId(),new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            toast("头像更新成功");
                        }else {
                            toast("头像更新失败");
                        }
                    }
                });
            }
        });
    }

    //封装toast()方法
    private void toast(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
}
