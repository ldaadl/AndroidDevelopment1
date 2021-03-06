package com.example.mywork1_lda.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mywork1_lda.R;
import com.example.mywork1_lda.activity.BaiduMapActivity;
import com.example.mywork1_lda.activity.BingPaperActivity;

public class FragmentExplore extends Fragment {
    private LinearLayout baiduLinearLayout;
    private LinearLayout bingLinearLayout;

    public FragmentExplore() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_explore, container, false);

        baiduLinearLayout = view.findViewById(R.id.baiduMapLayout);
        bingLinearLayout = view.findViewById(R.id.bingPaperLayout);

        // 点击百度地图功能，跳转页面
        baiduLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BaiduMapActivity.class);
                startActivity(intent);
            }
        });

        bingLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BingPaperActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}