package com.example.mywork1_lda.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LogDAO {
    private MySQLiteOpenHelper mySQLiteOpenHelper;
    private static final String databaseName = "userData";
    private static final String tableName ="user";
    private SQLiteDatabase sqLiteDatabase;
    public LogDAO(Context context){
        this.mySQLiteOpenHelper = new MySQLiteOpenHelper(context, databaseName,null,1);
        this.sqLiteDatabase = mySQLiteOpenHelper.getWritableDatabase();
        this.create();
    }

    private void create(){  // 创建数据表，数据库的名称作为表名
        String sql = "create table if not exists "+ tableName +"(account varchar(12) not null primary key, password varchar(12) not null,name varchar(15) not null, age int not null)";  // sql语句，如果不存在数据库，创建它
        this.sqLiteDatabase.execSQL(sql);
    }
    public void insert(ContentValues contentValues){
        sqLiteDatabase.insert(tableName,null,contentValues);
        Log.d("sql","dao insert");
    }

    public void update(ContentValues contentValues, String account){
        this.sqLiteDatabase.update(tableName,contentValues,"account=?",new String[]{account});
    }


    public void delete(String account){
        sqLiteDatabase.delete(tableName,"account=?",new String[]{account});
    }

    public boolean selectAccountAndPass(String account, String pass){
        Cursor cursor=sqLiteDatabase.query(tableName,new String[]{"*"},"account=? and password=?",new String[]{account,pass},null,null,null);
        while (cursor.moveToNext()){
            return true;
        }
        return false;
    }

    public boolean selectAccount(String account){
        Cursor cursor = sqLiteDatabase.query(tableName,new String[]{"*"},"account=?",new String[]{account},null,null,null);
        while (cursor.moveToNext()){
            return true;
        }
        return false;
    }

    public boolean selectAllExcludePass(String account,String name, int age){
        Cursor cursor = sqLiteDatabase.query(tableName,new String[]{"*"},"account=? and name=? and age=?",new String[]{account,name,Integer.toString(age)},null,null,null);
        while (cursor.moveToNext()){
            return true;
        }
        return false;
    }


    public String getDatabaseName(){
        return databaseName;
    }

    @SuppressLint("Range")
    public String select(String columnName, String account){
        Cursor cursor = sqLiteDatabase.query(tableName,new String[]{columnName},"account=?",new String[]{account},null,null,null);
        while (cursor.moveToNext()){
            return cursor.getString(cursor.getColumnIndex(columnName));
        }
        return  null;
    }
}
