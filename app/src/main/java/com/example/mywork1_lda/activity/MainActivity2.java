package com.example.mywork1_lda.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mywork1_lda.R;
import com.example.mywork1_lda.adapter.AdapterOfMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    private TextView textView;
    private RecyclerView recyclerView;
    private List<Map<String, Object>> message_logging;
    private AdapterOfMessage myAdapter;
    private ImageView button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.topWho);

        Log.d("life", "2:onCreate");

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        String name = data.getString("联系人");
        textView.setText(name); textView.setText(name);
        String message = data.getString("消息");
        int photo = data.getInt("头像");
        Log.d("message",name+message+photo);

        recyclerView = findViewById(R.id.message_recyclerView);
        message_logging = new ArrayList<Map<String, Object>>();

        // 绑定2条消息
        String[] key = new String[]{"联系人", "消息", "头像"};
        Map<String, Object> item1 = new HashMap<String, Object>();
        item1.put(key[0],name);
        item1.put(key[1],message);
        item1.put(key[2],photo);

        Map<String, Object> item2 = new HashMap<String, Object>();
        item2.put(key[0],"");
        item2.put(key[1],"你好吗");
        item2.put(key[2],R.drawable.me);
        message_logging.add(item2);
        message_logging.add(item1);

        myAdapter = new AdapterOfMessage(message_logging, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);


        // 监听返回
        button = findViewById(R.id.message_return);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        // life可以在logcat中搜索该标签
        Log.d("life","2:onStart");
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d("life","2:onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("life","2:onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("life","2:onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("life","2:onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("life","2:onDestroy");
    }

    @Override
    public void finish() {
        super.finish();
    }
}