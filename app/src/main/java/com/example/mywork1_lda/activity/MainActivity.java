package com.example.mywork1_lda.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mywork1_lda.R;
import com.example.mywork1_lda.fragment.FragmentContact;
import com.example.mywork1_lda.fragment.FragmentExplore;
import com.example.mywork1_lda.fragment.FragmentMe;
import com.example.mywork1_lda.fragment.FragmentMessage;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Fragment messageFragment = new FragmentMessage();
    private Fragment contactFragment = new FragmentContact();
    private Fragment exploreFragment = new FragmentExplore();
    private Fragment meFragment = new FragmentMe();
    private FragmentManager fragmentManager;
    private LinearLayout linearLayoutMessage;
    private LinearLayout linearLayoutContact;
    private LinearLayout linearLayoutExplore;
    private LinearLayout linearLayoutMe;
    // 界面顶栏的文本
    private TextView topText;
    // 底栏的图片和文本
    private TextView messageText;
    private TextView contactText;
    private TextView exploreText;
    private TextView meText;
    private ImageView messageImage;
    private ImageView contactImage;
    private ImageView exploreImage;
    private ImageView meImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayoutMessage = findViewById(R.id.LinearLayoutMessage);
        linearLayoutContact = findViewById(R.id.LinearLayoutContact);
        linearLayoutExplore = findViewById(R.id.LinearLayoutExplore);
        linearLayoutMe = findViewById(R.id.LinearLayoutMe);
        topText=findViewById(R.id.topText);
        messageText = findViewById(R.id.messageText);
        contactText = findViewById(R.id.contactText);
        exploreText = findViewById(R.id.exploreText);
        meText = findViewById(R.id.meText);
        messageImage = findViewById(R.id.messageImage);
        contactImage = findViewById(R.id.contactImage);
        exploreImage = findViewById(R.id.exploreImage);
        meImage = findViewById(R.id.meImage);
        linearLayoutMessage.setOnClickListener(this);
        linearLayoutContact.setOnClickListener(this);
        linearLayoutExplore.setOnClickListener(this);
        linearLayoutMe.setOnClickListener(this);

       initFragmentManager();
    }


    public void initFragmentManager(){
        // android而不是androidx
        fragmentManager = getFragmentManager();
        // transaction是事务管理方式
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.message_recyclerView, messageFragment);
        transaction.add(R.id.message_recyclerView, contactFragment);
        transaction.add(R.id.message_recyclerView, exploreFragment);
        transaction.add(R.id.message_recyclerView, meFragment);
        transaction.commit();  // 添加要先提交
        hideFragmentAll(transaction);
        // 加载四个fragment之后默认显示第一个界面
        showFragmentOne(messageFragment);
        topText.setText(R.string.message);
        messageText.setTextColor(Color.GREEN);
        messageImage.setColorFilter(Color.GREEN);

    }


    public void hideFragmentAll(FragmentTransaction transaction){

        transaction.hide(messageFragment);
        transaction.hide(contactFragment);
        transaction.hide(exploreFragment);
        transaction.hide(meFragment);
    }


    public void showFragmentOne(Fragment oneFragment){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragmentAll(transaction);
        transaction.show(oneFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case R.id.LinearLayoutMessage:
            showFragmentOne(messageFragment);
            topText.setText(R.string.message);
            setColorDefault();
            messageText.setTextColor(Color.GREEN);
            messageImage.setColorFilter(Color.GREEN);
            break;
        case R.id.LinearLayoutContact:
            showFragmentOne(contactFragment);
            topText.setText(R.string.contact);
            setColorDefault();
            contactText.setTextColor(Color.GREEN);
            contactImage.setColorFilter(Color.GREEN);
            break;
        case R.id.LinearLayoutExplore:
            showFragmentOne(exploreFragment);
            topText.setText(R.string.explore);
            setColorDefault();
            exploreText.setTextColor(Color.GREEN);
            exploreImage.setColorFilter(Color.GREEN);
            break;
        case R.id.LinearLayoutMe:
            showFragmentOne(meFragment);
            topText.setText(R.string.me);
            setColorDefault();
            meText.setTextColor(Color.GREEN);
            meImage.setColorFilter(Color.GREEN);
            break;
        default:
            break;
    }
    }
    public void setColorDefault(){
        messageImage.setColorFilter(0);
        messageText.setTextColor(Color.BLACK);
        contactImage.setColorFilter(0);
        contactText.setTextColor(Color.BLACK);
        exploreImage.setColorFilter(0);
        exploreText.setTextColor(Color.BLACK);
        meImage.setColorFilter(0);
        meText.setTextColor(Color.BLACK);
    }

}