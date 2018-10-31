package com.example.regextest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTestActivity extends AppCompatActivity {

    private static final String TAG = "RegexTestActivity";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regex_test);
        mTextView = findViewById(R.id.tv1);

        testSplit();
        testReplace();
        testMatch();
    }

    public void testSplit() {
        String string = "zhang5555wangpppppplioooooliu";
        String regex = "(.)\\1+";
        /*
         * 任意字符用.匹配
         * (.)把点变为组，其标号为1，引用为\1,反义后为\\1,
         * 叠词至少重复一次
         * */
        String[] strings = string.split(regex);
        for (String s : strings) {
            Log.d(TAG, "叠词拆分: " + s);
            String s1 = mTextView.getText() + ";" + s;
            mTextView.setText(s1);
            Log.d(TAG, "叠词拆分: " + s1);// regex;zhang;wang;li
        }
    }

    public void testReplace() {
        String string = "zhang5555wangpppppplioooooliu";
        String regex = "(.)\\1+";
        String reg = "$1";
        /*
         * 把叠词变成非叠词
         * */
        Log.d(TAG, "testReplace: " + reg);// $1
        String result = string.replaceAll(regex, reg);
        Log.d(TAG, "testReplace: " + reg);// $1
        Log.d(TAG, "testReplace: " + result);// zhang5wangplioliu

        Log.e(TAG, "****************号码隐藏中间四位*************************");
        string = "13800001111";
        regex = "(\\d{3})\\d{4}(\\d{4})";
        reg = "$1****$2";
        result = string.replaceAll(regex, reg);
        Log.d(TAG, "testReplace: " + result);// 138****1111
    }

    public void testMatch() {
        Log.e(TAG, "testMatch: ****************获取是三个字母的单词*************************");
        String string = "ni hao a, ming tian you kong mei?";
        // String reg = "[a-z]{3}";// min tia
        String reg = "\\b[a-z]{3}\\b";// 两边单词边界
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String group = matcher.group();
            Log.d(TAG, "testMatch: " + group);
            Log.d(TAG, "testMatch: " + matcher.start() + ":" + matcher.end());
        }
    }
}
