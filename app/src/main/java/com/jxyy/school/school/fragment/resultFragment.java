package com.jxyy.school.school.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.jxyy.school.school.MainActivity;
import com.jxyy.school.school.R;
import com.jxyy.school.school.util.loginHttp;

import java.util.List;
import java.util.Map;

/**
 * Created by 98398 on 2017/6/26.
 */

public class resultFragment extends Fragment {

    private String[] buf = new String[]{"2016-2017","2015-2016","2014-2015","2013-2014","2012-2013","2011-2012"};
    private int position1 = 0;
    private int position2 = 0;
    private ListView resultlist;
    private TextView s;
    private ProgressBar progress;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resultfragment,container,false);

        initview(view);
        return view;
    }

    Handler mhandle = new Handler(){
      public void handleMessage(Message msg){

          switch (msg.what){
              case 0x01:

                  List<Map<String,String>> handlerlist = (List<Map<String,String>>) msg.obj;

                  if (handlerlist.size()>1){
                      SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),handlerlist,R.layout.listview,
                              new String[]{"3","6","7","8"},
                              new int[]{R.id.t1,R.id.t2,R.id.t3,R.id.t4});
                      resultlist.setAdapter(simpleAdapter);
                  }else{
                      resultlist.setAdapter(null);
                      s.setText("没有查询到成绩");
                  }
                  progress.setVisibility(View.GONE);

                  break;
          }
      }
    };

    private void initview(View view) {

        progress = (ProgressBar) view.findViewById(R.id.progress);
        resultlist = (ListView) view.findViewById(R.id.resultlist);
       s = (TextView) view.findViewById(R.id.empty_tv);

        resultlist.setEmptyView(s);

        Spinner spinner1 = (Spinner) view.findViewById(R.id.xnspinner);
        Spinner spinner2 = (Spinner) view.findViewById(R.id.xqspinner);

        SpinnerAdapter spinnerAdapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                buf);
        spinner1.setAdapter(spinnerAdapter1);
        SpinnerAdapter spinnerAdapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                new String[]{"1","2"});
        spinner2.setAdapter(spinnerAdapter2);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                position1 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                position2 = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button cxbt = (Button) view.findViewById(R.id.cxbt);
        cxbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("result", Context.MODE_PRIVATE);
                String xh = sp.getString("username","");
                String name = sp.getString("name","");
                new loginHttp().getresult(buf[position1],position2==0?"1":"2",name,xh,mhandle);
                progress.setVisibility(View.VISIBLE);
            }
        });
    }
}
