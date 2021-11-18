package com.example.mywork1_lda.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mywork1_lda.R;
import com.example.mywork1_lda.other.OperateOfAccount;

public class ChangePassActivity extends AppCompatActivity {
    private EditText account;
    private EditText oldPass;
    private EditText newPass;
    private Button button;
    private OperateOfAccount operateOfAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        account = findViewById(R.id.changeAccount);
        oldPass = findViewById(R.id.oldPass);
        newPass = findViewById(R.id.newPass);
        button = findViewById(R.id.sureChangePass);
        operateOfAccount = new OperateOfAccount(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = operateOfAccount.changePass(account.getText().toString().trim(),oldPass.getText().toString().trim(),
                        newPass.getText().toString().trim());
                switch(result){
                    case 0:
                        Toast.makeText(getApplicationContext(), "账户或密码错误", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "密码格式错误", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                        finish();
                }
            }
        });
    }
}