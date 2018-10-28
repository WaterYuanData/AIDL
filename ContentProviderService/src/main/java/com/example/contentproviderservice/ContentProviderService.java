package com.example.contentproviderservice;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class ContentProviderService extends ContentProvider {
    private static final String TAG = "ContentProviderService";
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DBOpenHelper mDbOpenHelper;
    public static String databaseName = "databaseName";
    public static String tableName = "tableName";

    //为了方便直接使用UriMatcher,这里addURI,下面再调用Matcher进行匹配
    static {
        matcher.addURI("com.example.contentproviderservice", databaseName, 1);
    }

    public ContentProviderService() {
        Log.d(TAG, "ContentProviderService: ");
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate: ");
        mDbOpenHelper = new DBOpenHelper(getContext(), databaseName + ".db", null, 1);
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
//        throw new UnsupportedOperationException("Not yet implemented");
        Log.d(TAG, "insert: ");
        switch (matcher.match(uri)) {
            //把数据库打开放到里面是想证明uri匹配完成
            case 1:
                SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
                long rowId = db.insert(tableName, null, values);
                if (rowId > 0) {
                    Log.d(TAG, "insert: " + values.getAsString("name"));
                    //在前面已有的Uri后面追加ID
                    Uri nameUri = ContentUris.withAppendedId(uri, rowId);
                    //通知数据已经发生改变
                    getContext().getContentResolver().notifyChange(nameUri, null);
                    return nameUri;
                }
        }
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query:   =========");
        switch (matcher.match(uri)) {
            //把数据库打开放到里面是想证明uri匹配完成
            case 1:
                SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
                Cursor cursor = db.query(tableName, null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    String _id = cursor.getString(0);
                    String name = cursor.getString(1);
                    Log.d(TAG, "query: _id=" + _id);
                    Log.d(TAG, "query: name=" + name);
                }
//                cursor.close();
                return cursor;
        }
        return null;
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
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}


