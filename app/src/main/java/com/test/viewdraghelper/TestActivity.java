package com.test.viewdraghelper;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {

//    private String test = "123,223,2234,43222,533333,6323423,7999999,800030,9293983,10222222";
    private String test = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }

    public void onTest(View v) {
//        List<String> strs = new ArrayList<>();
//        String str1 = "请稍后";
//        String str2 = "请稍后";
//        String str3 = "请稍后2";
//        String str4 = "请稍后3";
//        strs.add(str1);
//        strs.add(str2);
//        strs.add(str3);
//        strs.add(str4);
//        strs.remove("请稍后");
//        strs.remove("请稍后3");
////        strs.remove("请稍后2");
//        System.out.println("===============> str1 == \"请稍后\"： " +  (str1 == "请稍后"));
//        System.out.println("===============> strs: " +  strs .toString());

//        System.out.println("===============> indexOf: " +  test.indexOf(","));
//        System.out.println("===============> lastIndexOf: " +  test.lastIndexOf(","));
        addShowChatFreeId(11111);
    }

    private int count = 10;

    public void addShowChatFreeId(int uid) {
        String ids = test;
        if(ids != null) {
            if(!ids.contains(Integer.toString(uid))) {
                String[] idArr = ids.split(",");
                if(idArr != null && idArr.length >= count) { // 如果当前已经存储了20个主播id，需要把最前面的id删除掉，然后再添加
                    String str = ids.substring(0, ids.indexOf(",") + 1);
                    ids = ids.replace(str, "");
                }

                test = ids.concat(ids.length() == 0 ? Integer.toString(uid) : "," + uid);
                System.out.println("===============> ids: " + ids);
            }
        }
    }

}
