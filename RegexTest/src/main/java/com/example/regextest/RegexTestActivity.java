package com.example.regextest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class RegexTestActivity extends AppCompatActivity {

    private static final String TAG = "RegexTestActivity";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regex_test);
        mTextView = findViewById(R.id.tv1);
        testSplit();
    }

    public void testSplit() {
        String string = "a   b   c";
        // 切割时正则表达式的使用
        // String[] strings = string.split(" ");// regex;a;;;b;;;c
        String[] strings = string.split(" +");// regex;a;b;c
        for (String s : strings) {
            String s1 = mTextView.getText() + ";" + s;
            Log.d(TAG, "onCreate: " + s1);
            mTextView.setText(s1);
        }
    }
}
