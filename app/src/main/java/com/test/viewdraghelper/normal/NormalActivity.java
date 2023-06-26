package com.test.viewdraghelper.normal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.test.viewdraghelper.R;

/**
 * 常规拖动View的方式
 */
public class NormalActivity extends AppCompatActivity {

    private Button btnTest;
    private View clTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
//        btnTest = findViewById(R.id.btn_test);
//        DragViewUtil.registerDragAction(btnTest);
        clTest = findViewById(R.id.cl_test);
        DragViewUtil.registerDragAction(clTest);
    }

    public void onTest(View v) {
        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
    }

}
