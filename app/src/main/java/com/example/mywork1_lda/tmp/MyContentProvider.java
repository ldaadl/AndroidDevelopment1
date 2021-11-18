package com.example.mywork1_lda.tmp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.mywork1_lda.DAO.LogDAO;


// ContentProvider实际上是一个应用的数据和库其他应用共享的接口
public class MyContentProvider extends ContentProvider {
    private LogDAO dao;
    private static final UriMatcher uriMatcher;  // 修改数据库后的反馈
    private static final String AUTHORITY = "lda.testContentProvider";
    private static Uri NOTIFY_URI = Uri.parse("content://"+AUTHORITY+"/person");
    private static final int MATCH_CODE=100;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"person",MATCH_CODE);
    }
    public MyContentProvider(){

    }

    public MyContentProvider(LogDAO dao) {
        this.dao = dao;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        if(uriMatcher.match(uri)==MATCH_CODE){
            dao.insert(values);
            notifychange();
        }
        return null;
    }

    private void notifychange() {
        getContext().getContentResolver().notifyChange(NOTIFY_URI,null);
    }

    private void Match() {

    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}