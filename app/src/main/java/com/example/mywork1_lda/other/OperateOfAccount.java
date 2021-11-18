package com.example.mywork1_lda.other;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.mywork1_lda.DAO.LogDAO;

import java.util.regex.Pattern;

/**
 * 这个类主要是关于帐号相关的操作
 * 实现登录、注销、删除帐号、找回帐号、更改姓名和密码等功能
 * 内部调用DAO修改数据库，方法返回的是操作状态，UI界面的修改需要调用者实现
 * */
public class OperateOfAccount {
    private LogDAO logDAO;
    private static boolean isLogging=false;  // 根据isLogging可以决定外部的按钮能否点击
    private static String account;
    private static String pass;
    private static String name;
   /* private  static OperateOfAccount operateOfAccount;*/


    public OperateOfAccount(Context context){

        logDAO = new LogDAO(context);
    }

    /*public static OperateOfAccount getOperateOfAccount(Context context){
        if(operateOfAccount==null)
            operateOfAccount = new OperateOfAccount(context);
        return operateOfAccount;
    }*/

    // 获取登录登录状态
    public boolean getIsLogging(){
        return isLogging;
    }
    // 查询账户是否注册
    private boolean selectAccount(String account){
        return logDAO.selectAccount(account);
    }
    /**
     * 登录
     * @return 0为账户不存在，1为密码错误，2为登录成功
     * */
    public int log(String account,String pass){
        if(!selectAccount(account))
            return 0;
        else {
            isLogging = logDAO.selectAccountAndPass(account,pass);
            if(!isLogging)
                return 1;
            else {
                this.account = account;
                this.pass = pass;
                this.name = logDAO.select("name",account);
                return 2;
            }
        }
    }
    // 判断账户是否合法
    private boolean judgeAccount(String account){
        if(Pattern.matches("^[0-9]{8,12}$",account))
            return true;
        else
            return false;
    }
    // 判断用户名是否合法
    private boolean judgeName(String name){
        if(Pattern.matches("^[a-zA-Z][a-zA-Z0-9]{4,14}$",name))
            return true;
        else
            return false;
    }
    // 判断密码是否合法
    private boolean judgePass(String pass){
        if(Pattern.matches("^(?![0-9]+$)(?![a-zA-Z]+$)(?![+\\-*/]+$)[0-9A-Za-z+\\-*/_]{8,12}$",pass))
            return true;
        else
            return false;
    }
    /**
     * 注册
     * @param account 帐号长度不得小于8长于12,纯数字
     * @param pass 密码长度不的小于8长于12,必须为数字、字母、+ - * / _等字符中至少两种的组合
     * @return 0为账户已经存在，1为账户或者密码不符合要求，2注册成功
     * */
    public int enrol(String account,String pass, int age){
        if(selectAccount(account))
            return 0;
        else{
            if(judgeAccount(account)&&judgePass(pass)){
                ContentValues contentValues = new ContentValues();
                contentValues.put("account",account);
                contentValues.put("password",pass);
                // 生成默认用户名
                contentValues.put("name","User"+account);
                contentValues.put("age",age);
                logDAO.insert(contentValues);
                Log.d("sql","enrol insert");
                return 2;
            }
            else
                return 1;
        }
    }

    // 登出
    public void logout(){
        isLogging = false;
    }

    /**
     * 改名
     * @param name 以字母开头，长度大于5小于15
     * @return false是名字不符合要求
     * */
    public boolean changeName(String name){
        if(judgeName(name)){
            this.name = name;
            ContentValues contentValues = new ContentValues();
            contentValues.put("name",name);
            logDAO.update(contentValues,this.account);
            return true;
        }else
            return false;
    }

    /**
     * 改密码,必须重新输入账户密码
     * @return 0密码账户错误，1密码格式错误，2成功
     * */
    public int changePass(String account, String oldPass, String newPass){
        if(logDAO.selectAccountAndPass(account,oldPass)){
            if(judgePass(newPass)){
                ContentValues contentValues = new ContentValues();
                contentValues.put("password",newPass);
                logDAO.update(contentValues,account);
                return 2;
            }
            else
                return 1;
        }else
            return 0;
    }


    // 删除账户
    public void deleteAccount(){
        logout();
        logDAO.delete(this.account);
    }


    /**
     * 找回密码
     * @return 0输入信息错误，1新密码格式错误，2修改成功
     * */
    public int findPass(String account,String name,int age,String newPass){

        if(logDAO.selectAllExcludePass(account,name,age)) {
            if(judgePass(newPass)){
                ContentValues contentValues = new ContentValues();
                contentValues.put("password",newPass);
                logDAO.update(contentValues,account);
                return 2;
            }else
                return 1;
        }else
            return 0;

    }

    public String getName(){
        return name;
    }

    public String getAccount(){
        return account;
    }
}
