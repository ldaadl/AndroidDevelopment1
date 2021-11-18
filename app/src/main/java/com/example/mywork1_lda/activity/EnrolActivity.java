package com.example.mywork1_lda.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mywork1_lda.R;
import com.example.mywork1_lda.other.OperateOfAccount;

import java.util.regex.Pattern;

public class EnrolActivity extends AppCompatActivity {
    private OperateOfAccount operateOfAccount;
    private EditText editTextAccount;
    private EditText editTextPassword;
    private EditText editAge;
    private Button buttonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrol);
        operateOfAccount = new OperateOfAccount(this);
        editTextAccount = findViewById(R.id.editSureAccount);
        editTextPassword = findViewById(R.id.editSurePassword);
        editAge = findViewById(R.id.editSureAge);
        buttonRegister=findViewById(R.id.buttonSureEnrol);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String age = editAge.getText().toString().trim();
                if (!Pattern.matches("^([1-9][2-9])|([2-9][0-9])$",age))
                {
                    Toast.makeText(getApplicationContext(), "age是找回密码的重要凭据，请按要求填写", Toast.LENGTH_SHORT).show();
                }else
                {
                    String account = editTextAccount.getText().toString().trim();
                    if(account.equals(null))
                        account="";
                    String pass = editTextPassword.getText().toString().trim();
                    if(pass.equals(null))
                        pass="";

                    int result = operateOfAccount.enrol(account,pass,Integer.parseInt(age));
                    SystemClock.sleep(2000);
                    switch (result) {
                        case 0:
                            Toast.makeText(getApplicationContext(), "账户已经存在", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(getApplicationContext(), "账户或密码格式错误", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                            finish();
                    }
                }
            }
        });
    }
}