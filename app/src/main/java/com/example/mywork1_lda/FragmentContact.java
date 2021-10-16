package com.example.mywork1_lda;

import android.content.Context;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentContact extends Fragment {
    private RecyclerView recyclerView;
    private List<Map<String, Object>> data;
    private Context context;
    private AdapterOfMessAndCon myAdapter;


    public FragmentContact() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_contact, container, false);
        recyclerView = view.findViewById(R.id.RVcontact);
        context = this.getActivity();
        data = new ArrayList<Map<String, Object>>();

        String[] name = new String[]{"lda", "chf", "wy", "zh"};
        String[] message = new String[]{"我好帅","傻逼", "哲学家", "睿智"};
        int[] photo = new int[]{R.drawable.person1, R.drawable.person2, R.drawable.person3, R.drawable.person4};
        String[] key = new String[]{"联系人", "消息", "头像"};
        for (int i = 0; i < 4; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put(key[0], name[i]);
            item.put(key[1], "");
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
        return view;

    }
}