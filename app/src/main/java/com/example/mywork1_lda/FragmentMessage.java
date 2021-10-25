package com.example.mywork1_lda;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.app.Fragment;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentMessage extends Fragment {
    private SwipeRecyclerView recyclerView;
    private List<Map<String, Object>> data;
    private Context context;
    private AdapterOfMessAndCon myAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    public FragmentMessage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        recyclerView = view.findViewById(R.id.RVmessage);
        Log.d("mmm", String.valueOf(R.id.RVmessage));
        context = this.getActivity();
        data = new ArrayList<Map<String, Object>>();

        String[] name = new String[]{"lda", "chf", "wy", "zh"};
        String[] message = new String[]{"我好帅","智力低下", "哲学家", "睿智"};
        int[] photo = new int[]{R.drawable.person1, R.drawable.person2, R.drawable.person3, R.drawable.person4};
        String[] key = new String[]{"联系人", "消息", "头像"};
        for (int i = 0; i < 4; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put(key[0], name[i]);
            item.put(key[1], message[i]);
            item.put(key[2], photo[i]);
            data.add(item);
        }
        myAdapter = new AdapterOfMessAndCon(data, context, R.layout.item_message, key);
        // GridLayoutManager可以设置行数
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        // Horizontal是水平布局
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        // 在recyclerView上渲染它的内容
        recyclerView.setAdapter(myAdapter);




        // 设置下拉监听
        mSwipeRefreshLayout = view.findViewById(R.id.swiperefreshlayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TextView text = view.findViewById(R.id.message);
                if (text.getText() == "消息刷新了") {
                    Toast.makeText(mSwipeRefreshLayout.getContext(),"消息已是最新",Toast.LENGTH_LONG).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                } else {
                    TextView textView = view.findViewById(R.id.message);
                    /*AdapterOfMessAndCon newMyAdapter = new AdapterOfMessAndCon(newData, context, R.layout.item_message, key);
                    recyclerView.setAdapter(newMyAdapter);*/
                    // 刷新数据
                    for (int i = 0; i < 4; i++) {
                        data.get(i).put(key[1], "消息刷新了");
                    }
                    // 针对数据的刷新作出反应
                    myAdapter.notifyDataSetChanged();
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        // 设置删除监听
        // 针对整个recyclerView设置监听
        // 为recyclerView提供监听到的对象和检测到监听后的具体解决方法
        // 点击到那一个item也是有recyclerView进行判断
        recyclerView.setRightClickListener(new SwipeRecyclerView.OnRightClickListener() {
            @Override
            public void onRightClick(int position, String id) {
                TextView text = view.findViewById(R.id.message);

                data.remove(position);
                //    myAdapter.notifyItemRemoved(position);
                myAdapter.notifyDataSetChanged();
                Toast.makeText(context, " 正在删除 ", Toast.LENGTH_SHORT).show();
            }
        });

        // 设置点击监听
        // 这里有疑问，重写的onClick的上下文是当前环境的吗？
        recyclerView.setOnClickListener(new SwipeRecyclerView.OnClickListener() {
            @Override
            public void onClick(int position, String id) {
                Intent intent = new Intent(context,MainActivity2.class);  // ****
                intent.putExtra(key[0],data.get(position).get(key[0]).toString());
                intent.putExtra(key[1],data.get(position).get(key[1]).toString());
                intent.putExtra(key[2],Integer.parseInt(data.get(position).get(key[2]).toString()));
                Toast.makeText(context,"跳转页面",Toast.LENGTH_LONG).show();
                // SystemClock.sleep(1000);
                startActivityForResult(intent, 0);
            }
        });
        
        return view;
    }
}

