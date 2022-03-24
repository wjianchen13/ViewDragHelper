package com.test.viewdraghelper.vdh;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.test.viewdraghelper.R;


public class VDHBlogActivity extends AppCompatActivity {

    private LinearLayout llytTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vdhblog);
        llytTest = findViewById(R.id.llyt_test);
    }

    public void onClick(View v) {
        Toast.makeText(this, "click button", Toast.LENGTH_SHORT).show();
    }

    public void onTest1(View v) {
        Toast.makeText(this, "click button", Toast.LENGTH_SHORT).show();
        llytTest.setVisibility(View.GONE);
    }

    public void onTest2(View v) {
        Toast.makeText(this, "click button", Toast.LENGTH_SHORT).show();
        llytTest.setVisibility(View.VISIBLE);
    }
}
