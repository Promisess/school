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
import android.widget.TextView;

import com.jxyy.school.school.R;
import com.jxyy.school.school.util.loginHttp;

/**
 * Created by 98398 on 2017/9/1.
 */

public class resumesFragment extends Fragment {

    private TextView resumesText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.resumes,container,false);
        initview(view);
        return view;
    }

    Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            StringBuffer sb = new StringBuffer();
            String buffer[] = (String[]) msg.obj;
            for (String b:buffer){
                if (b!=null)
                sb.append(b+"\n");
            }
            resumesText.setText(sb.toString());
        }
    };
    private void initview(View view) {

        SharedPreferences sp = getActivity().getSharedPreferences("result", Context.MODE_PRIVATE);
        String xh = sp.getString("username","");
        String name = sp.getString("name","");
        new loginHttp().getresumes(xh,name,mhandler);

        resumesText = (TextView) view.findViewById(R.id.resumes);
    }
}
