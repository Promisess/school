package com.jxyy.school.school;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jxyy.school.school.fragment.resultFragment;
import com.jxyy.school.school.fragment.resumesFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 98398 on 2017/6/26.
 */

public class MainActivity extends FragmentActivity {

    private int MenuPosition = 1;
    private  List<Map<String,String>> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);
        initview();
        update();
    }

    private void initview() {
        TextView MenuName = (TextView) findViewById(R.id.menuname);
//        MenuName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SlidingPaneLayout slidingPaneLayout = (SlidingPaneLayout) findViewById(R.id.sliding);
//                boolean isopen = slidingPaneLayout.isOpen();
//                if(isopen){
//                    slidingPaneLayout.closePane();
//                }else {
//                    slidingPaneLayout.openPane();
//                }
//            }
//        });


        SharedPreferences sp = getSharedPreferences("result", MODE_PRIVATE);
        MenuName.setText(sp.getString("name","未命名"));
        ListView menuListView = (ListView) findViewById(R.id.menulist);
        list = new ArrayList<Map<String,String>>();
        Map map3 = new HashMap();
        map3.put("select","个人资料");

        list.add(map3);
        Map map = new HashMap();
        map.put("select","成绩查询");

        list.add(map);
        Map map2 = new HashMap();
        map2.put("select","退出");
        list.add(map2);
        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(),list,R.layout.menulist,
                new String[]{"select"},
                new int[]{R.id.T1});
        menuListView.setAdapter(simpleAdapter);
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuPosition = position;
                update();
            }
        });
    }
    private void update(){
        TextView tx = (TextView) findViewById(R.id.title);
        switch (MenuPosition){
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.display,new resumesFragment()).commit();

                tx.setText(list.get(MenuPosition).get("select"));

                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.display,new resultFragment()).commit();

                tx.setText(list.get(MenuPosition).get("select"));
                break;
            case 2:
                SharedPreferences sp = getSharedPreferences("result", MODE_PRIVATE);
                sp.edit().putBoolean("iszddl",false).commit();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                tx.setText(list.get(MenuPosition).get("select"));
                break;

        }
    }
}
