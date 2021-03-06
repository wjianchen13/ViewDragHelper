package com.test.viewdraghelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.test.viewdraghelper.drag.DragActivity;
import com.test.viewdraghelper.expand.ExpandActivity;
import com.test.viewdraghelper.vdh.VDHBlogActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTest(View v) {
        startActivity(new Intent(this, VDHBlogActivity.class));
    }

    public void onDrag(View v) {
        startActivity(new Intent(this, DragActivity.class));
    }

    public void onTest3(View v) {
        startActivity(new Intent(this, TestActivity.class));
    }

    public void onExpand(View v) {
        startActivity(new Intent(this, ExpandActivity.class));
    }
}
