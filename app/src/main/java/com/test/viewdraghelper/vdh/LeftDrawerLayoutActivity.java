package com.test.viewdraghelper.vdh;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.test.viewdraghelper.R;


public class LeftDrawerLayoutActivity extends AppCompatActivity
{

    private LeftMenuFragment mMenuFragment;
    private LeftDrawerLayout mLeftDrawerLayout ;
    private TextView mContentTv ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_drawer_layout);

        mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.id_drawerlayout);
        mContentTv = (TextView) findViewById(R.id.id_content_tv);

        FragmentManager fm = getSupportFragmentManager();
        mMenuFragment = (LeftMenuFragment) fm.findFragmentById(R.id.id_container_menu);
        if (mMenuFragment == null)
        {
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment = new LeftMenuFragment()).commit();
        }

        mMenuFragment.setOnMenuItemSelectedListener(new LeftMenuFragment.OnMenuItemSelectedListener()
        {
            @Override
            public void menuItemSelected(String title)
            {
                mLeftDrawerLayout.closeDrawer();
                mContentTv.setText(title);
            }
        });

    }


    
}
