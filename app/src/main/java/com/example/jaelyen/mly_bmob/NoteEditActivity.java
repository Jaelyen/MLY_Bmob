package com.example.jaelyen.mly_bmob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.EditText;

public class NoteEditActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        editText = (EditText) findViewById(R.id.editText_addNote);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            saveNote();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void saveNote() {
        String content = editText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            return;
        }else {

        }
    }
}
