package com.example.regextest;

import android.Manifest;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.TreeSet;
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
        testIP();
        readFile();
    }

    private void readFile() {
        String externalStorageState = Environment.getExternalStorageState();
        Log.d(TAG, "readFile: externalStorageState=" + externalStorageState);
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        Log.d(TAG, "readFile: externalStoragePublicDirectory=" + externalStoragePublicDirectory);
//        try {
//            // 读电脑主机文件
//            BufferedReader bufferedReader = new BufferedReader(
//                    new FileReader("D:\\MyCode\\MyGitHub\\AIDL\\README.md"));
//            String s;
//            while ((s = bufferedReader.readLine()) != null) {
//                Log.d(TAG, "readFile: " + s);
//            }
//            // 读网络文件
//            URL url = new URL("www.baidu.com");
//            bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void testIP() {
        String string = "192.168.1.2    3.3.3.3   05.06.07.125 16.15.186.158";
        // 补两个0
        String regex = "(\\d{1,3})";
        String reg = "00$1";
        String result = string.replaceAll(regex, reg);
        Log.d(TAG, "testIP: 补0 result=" + result);
        // 保留3位 ？是一次或者零次，*是零次或者多次
        regex = "0*(\\d{3})";
        reg = "$1";
        result = result.replaceAll(regex, reg);
        Log.d(TAG, "testIP: 3位 result=" + result);
        // 按照空格拆分
        regex = " +";
        String[] strings = result.split(regex);
        // 按照字符串排序
        TreeSet<String> treeSet = new TreeSet<String>();
        for (String st : strings) {
            treeSet.add(st);
        }
        for (String st : treeSet) {
            // 去掉添加的零
            String s = st.replaceAll("0*(\\d{1,3})", "$1");
            Log.d(TAG, "testIP: 排序 st=" + s);
        }
    }

    /*
     * 按照叠词进行拆分
     * 任意字符用.匹配
     * ()变为组，其标号从1开始，引用为\1,反义后为\\1,
     * +叠词至少重复一次
     * */
    public void testSplit() {
        String string = "zhang5555wangpppppplioooooliu";
        String regex = "(.)\\1+";
        String[] strings = string.split(regex);
        for (String s : strings) {
            Log.d(TAG, "叠词拆分: " + s);
            String s1 = mTextView.getText() + ";" + s;
            mTextView.setText(s1);
            Log.d(TAG, "叠词拆分: " + s1);// regex;zhang;wang;li
        }
    }

    /*
     * 替换
     * 把叠词变成非叠词
     * */
    public void testReplace() {
        String string = "zhang5555wangpppppplioooooliu";
        String regex = "(.)\\1+";
        String reg = "$1";
        Log.d(TAG, "testReplace: " + reg);// $1
        String result = string.replaceAll(regex, reg);
        Log.d(TAG, "testReplace: " + reg);// $1
        Log.d(TAG, "testReplace: " + result);// zhang5wangplioliu

        Log.e(TAG, "**********隐藏号码中间四位************");
        string = "13800001111";
        regex = "(\\d{3})\\d{4}(\\d{4})";
        reg = "$1****$2";
        result = string.replaceAll(regex, reg);
        Log.d(TAG, "testReplace: " + result);// 138****1111
    }

    /*
     * 获取
     * \b是单词边界，\\b转义
     * */
    public void testMatch() {
        Log.e(TAG, "testMatch: **********获取只有三个字母的单词*********");
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
