package com.test.viewdraghelper.attach;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.test.viewdraghelper.R;

public class AttachActivity extends AppCompatActivity implements View.OnClickListener{

    private AttachLayout alTest;
    private Button btnTest1;
    private Button btnTest2;
    private Button btnTest3;
    private Button btnTest4;
    private Button btnTest5;

    private RelativeLayout musicFlagLayout;
    private ConstraintLayout musicBoxLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attach);
        alTest = findViewById(R.id.al_test);
        btnTest1 = findViewById(R.id.btn_test1);
        btnTest2 = findViewById(R.id.btn_test2);
        btnTest3 = findViewById(R.id.btn_test3);
        btnTest4 = findViewById(R.id.btn_test4);
        btnTest5 = findViewById(R.id.btn_test5);
        btnTest1.setOnClickListener(this);
        btnTest2.setOnClickListener(this);
        btnTest3.setOnClickListener(this);
        btnTest4.setOnClickListener(this);
        btnTest5.setOnClickListener(this);

        musicFlagLayout = findViewById(R.id.music_flag_layout);
        musicBoxLayout = findViewById(R.id.music_box_layout);

        musicFlagLayout.setOnClickListener(this);
        musicBoxLayout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_test1) {
            alTest.setTest1();
        } else if(v.getId() == R.id.btn_test2) {
            alTest.setTest2();
        } else if(v.getId() == R.id.btn_test3) {
            alTest.setTest3();
        } else if(v.getId() == R.id.btn_test4) {
            Toast.makeText(this, "test4", Toast.LENGTH_SHORT).show();
        } else if(v.getId() == R.id.btn_test5) {
            Toast.makeText(this, "test5", Toast.LENGTH_SHORT).show();
        } else if(v.getId() == R.id.music_flag_layout) {
            showMusicLayout();
        } else if(v.getId() == R.id.music_box_layout) {
            hideMusicLayout();
        }
    }

    /**
     * 显示音乐详情
     */
    public void showMusicLayout() {
        alTest.showMusic();
    }

    /**
     * 隐藏音乐详情
     */
    public void hideMusicLayout() {
        alTest.hideMusic();
    }

}
