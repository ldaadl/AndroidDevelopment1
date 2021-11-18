package com.example.mywork1_lda.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mywork1_lda.R;
import com.example.mywork1_lda.other.OperateOfAccount;

public class LogActivity extends AppCompatActivity {
    private EditText editTextAccount;
    private EditText editTextPassword;
    private Button buttonRegister;
    private Button buttonLog;
    private Button buttonFind;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    // 账户操作的对象
    private OperateOfAccount operateOfAccount;
    // 复选框对象
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        editTextAccount = findViewById(R.id.editTextAccount);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonEnrol);
        buttonLog = findViewById(R.id.buttonLog);
        buttonFind = findViewById(R.id.buttonForgetPass);
        sp = getSharedPreferences("log",0);
        editor = sp.edit();
        operateOfAccount = new OperateOfAccount(this);
        checkBox = findViewById(R.id.remPass);

        // 根据sp文件中对于复选框状态的记录给复选框赋值
        if(sp.getString("choose","NO").equals("YES"))
            checkBox.setChecked(true);

        // 打开界面如果复选框够勾选记住密码，账户密码将被自动加载
        if(checkBox.isChecked()){
            // 读取sp文件中存储的账户和密码
            editTextAccount.setText(sp.getString("account",null));
            editTextPassword.setText(sp.getString("password",null));
        }

        // 登录按钮的登录行为,跳转到注册
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EnrolActivity.class);
                startActivity(intent);
               /* String account = editTextAccount.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                if (!account.equals("") && !password.equals("")&&!account.equals("null")) {
                    editor.putString(account,password);
                    editor.commit();
                }else
                {
                    Toast.makeText(getApplicationContext(), "密码或帐号不能为空，帐号不能为null", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = editTextAccount.getText().toString().trim();
                if(account.equals(null))
                    account="";
                String pass = editTextPassword.getText().toString().trim();
                if(pass.equals(null))
                    pass="";
                int result = operateOfAccount.log(account,pass);
                switch (result){
                    case 0:
                        Toast.makeText(getApplicationContext(), "账户不存在", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                        // 保存帐号密码
                        if (checkBox.isChecked()){
                            editor.putString("account",account);
                            editor.putString("password",pass);
                            editor.putString("choose","YES");
                            editor.commit();
                        }else {
                            editor.putString("choose","NO");
                            editor.commit();
                        }
                        Intent intent=new Intent();

                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.setClass(getApplicationContext(),MainActivity.class);

                        startActivity(intent);
                       /* Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);*/
                }
               /* String account = editTextAccount.getText().toString().trim();
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
                }*/
            }
        });
        buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindPassActivity.class);
                startActivity(intent);
            }
        });
    }
}