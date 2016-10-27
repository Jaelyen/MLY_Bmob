package com.example.jaelyen.mly_bmob;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaelyen.mly_bmob.bean.MyNote;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class NoteActivity extends AppCompatActivity {

    private NoteAdapter noteAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        initData();
    }
    //初始化数据
    private void initData() {
        BmobQuery<MyNote> query = new BmobQuery<>();
        query.findObjects(new FindListener<MyNote>() {
            @Override
            public void done(List<MyNote> list, BmobException e) {
                if (e == null) {
                    noteAdapter = new NoteAdapter(NoteActivity.this,list);
                    listView.setAdapter(noteAdapter);
                }else {
                    Toast.makeText(NoteActivity.this, "啥也没有/(ㄒoㄒ)/~~", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }
    //选项菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addNote:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class NoteAdapter extends BaseAdapter{

        private Context context;
        private List<MyNote> notes;

        public NoteAdapter(Context context, List<MyNote> notes) {
            this.context = context;
            this.notes = notes;
        }

        @Override
        public int getCount() {
            return notes.size();
        }

        @Override
        public Object getItem(int position) {
            return notes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            VieWHolder vieWHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.note_list_item,null);
                vieWHolder.textView_content = (TextView) convertView.findViewById(R.id.textView_content);
                vieWHolder.textView_date = (TextView) convertView.findViewById(R.id.textView_date);
                convertView.setTag(vieWHolder);
            }else {
                vieWHolder = (VieWHolder) convertView.getTag();
            }
            MyNote note = notes.get(position);
            vieWHolder.textView_content.setText(note.getContent());
            vieWHolder.textView_date.setText(note.getCreatedAt());
            return convertView;
        }
        static class VieWHolder{
            TextView textView_content;
            TextView textView_date;
        }
    }
}
