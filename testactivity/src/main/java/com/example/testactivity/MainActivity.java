package com.example.testactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    /*
2019-04-20 15:30:14.194 28642-28642/com.example.testactivity D/MainActivity: onPause:
2019-04-20 15:30:14.210 28642-28642/com.example.testactivity D/MainActivity: onStop:
2019-04-20 15:30:14.211 28642-28642/com.example.testactivity D/MainActivity: onSaveInstanceState:
2019-04-20 15:30:14.213 28642-28642/com.example.testactivity D/MainActivity: onDestroy:
2019-04-20 15:30:14.263 28642-28642/com.example.testactivity D/MainActivity: onCreate:
2019-04-20 15:30:14.301 28642-28642/com.example.testactivity D/MainActivity: onStart:
2019-04-20 15:30:14.302 28642-28642/com.example.testactivity D/MainActivity: onRestoreInstanceState:
2019-04-20 15:30:14.303 28642-28642/com.example.testactivity D/MainActivity: onResume:
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: ");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent: ");
        super.onNewIntent(intent);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: ");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
