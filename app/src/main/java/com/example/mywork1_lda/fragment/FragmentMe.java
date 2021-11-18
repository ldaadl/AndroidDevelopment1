package com.example.mywork1_lda.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;

import android.os.ConditionVariable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mywork1_lda.R;
import com.example.mywork1_lda.activity.ChangePassActivity;
import com.example.mywork1_lda.other.OperateOfAccount;


public class FragmentMe extends Fragment {
    private Button buttonChangePass;
    private Button buttonChangeName;
    private Button buttonDeleteAccount;
    private Button buttonLogout;
    private OperateOfAccount operateOfAccount;
    private Context context;
    private TextView textView;


    public FragmentMe() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        buttonChangeName = view.findViewById(R.id.buttonChangeName);
        buttonChangePass = view.findViewById(R.id.buttonChangePass);
        buttonDeleteAccount = view.findViewById(R.id.buttonDeleteAccount);
        buttonLogout = view.findViewById(R.id.buttonLogout);
        context = this.getActivity();
        operateOfAccount = new OperateOfAccount(context);
        textView = view.findViewById(R.id.textViewMyName);

        textView.setText("你好"+operateOfAccount.getName());

        buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operateOfAccount.deleteAccount();
                getActivity().finish();
            }
        });
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        buttonChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangePassActivity.class);
                getActivity().startActivity(intent);
            }
        });
        buttonChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("请输入新的用户名（以字母开头，可以包括数字，长度不小于5,不大于15：）");
                EditText editText = new EditText(getActivity());
                builder.setView(editText);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = editText.getText().toString().trim();
                        if(operateOfAccount.changeName(name)) {
                            Toast.makeText(getActivity(), "更改成功", Toast.LENGTH_SHORT).show();
                            textView.setText("你好"+operateOfAccount.getName());
                        }
                        else
                            Toast.makeText(getActivity(), "用户名非法", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();
            }
        });
        return  view;
    }
}