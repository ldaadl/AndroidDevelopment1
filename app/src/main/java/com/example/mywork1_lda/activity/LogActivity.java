package com.example.mywork1_lda.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mywork1_lda.R;

public class LogActivity extends AppCompatActivity {
    private EditText editTextAccount;
    private EditText editTextPassword;
    private Button buttonRegister;
    private Button buttonLog;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        editTextAccount = findViewById(R.id.editTextAccount);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLog = findViewById(R.id.buttonLog);
        sp = getSharedPreferences("log",0);
        editor = sp.edit();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = editTextAccount.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                if (!account.equals("") && !password.equals("")&&!account.equals("null")) {
                    editor.putString(account,password);
                    editor.commit();
                }else
                {
                    Toast.makeText(getApplicationContext(), "密码或帐号不能为空，帐号不能为null", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = editTextAccount.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String get =  sp.getString(account, "null");
                if(get.equals("null")){
                    Toast.makeText(getApplicationContext(), "帐号不存在", Toast.LENGTH_SHORT).show();
                }else if(password.equals(get)){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                   startActivity(intent);
                }else
                {
                    Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}