package com.example.contentproviderclient;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ContentProviderClientActivity extends AppCompatActivity {

    private static final String TAG = "ContentProviderClientAc";
    private TextView mTextView;
    private TextView mTextView2;
    Uri mParse_Uri = Uri.parse("content://com.example.contentproviderclient/" + ContentProviderClient.databaseName);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_client);

        initView();
    }

    public void initView() {
        mTextView = findViewById(R.id.tv1);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                ContentValues contentValues = new ContentValues();
                contentValues.put("_id", "13");
                contentValues.put("name", "ran3");
                getContentResolver().insert(mParse_Uri, contentValues);
            }
        });
        mTextView2 = findViewById(R.id.tv2);
        mTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ---------");
                Cursor cursor = getContentResolver().query(mParse_Uri, null, null, null, null);
                while (cursor.moveToNext()) {
                    String _id = cursor.getString(0);
                    String name = cursor.getString(1);
                    Log.d(ContentProviderClientActivity.this.getClass().getName(), "onClick: _id=" + _id);
                    Log.d(TAG, "onClick: name=" + name);
                }
                cursor.close();
            }
        });
    }
}
