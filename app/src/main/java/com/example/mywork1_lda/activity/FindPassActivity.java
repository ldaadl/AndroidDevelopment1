package com.example.mywork1_lda.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mywork1_lda.R;
import com.example.mywork1_lda.other.OperateOfAccount;

import java.util.regex.Pattern;

public class FindPassActivity extends AppCompatActivity {
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private Button button;
    private OperateOfAccount operateOfAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pass);
        editText1 = findViewById(R.id.findAccount);
        editText2 = findViewById(R.id.findName);
        editText3 = findViewById(R.id.findAge);
        editText4 = findViewById(R.id.findPass);
        button = findViewById(R.id.buttonFindPass);
        operateOfAccount = new OperateOfAccount(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = editText1.getText().toString().trim();
                if(account.equals(null))
                    account="";
                String name = editText2.getText().toString().trim();
                if(name.equals(null))
                    name="";
                String age = editText3.getText().toString().trim();
                if(age.equals(null)||!Pattern.matches("^([1-9][2-9])|([2-9][0-9])$",age))
                    age="0";
                String pass = editText4.getText().toString().trim();
                if(pass.equals(null))
                    pass="";
                int result = operateOfAccount.findPass(account, name, Integer.parseInt(age), pass);
                switch (result) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "输入信息错误", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "新密码格式错误", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                }
            }
        });
    }
}