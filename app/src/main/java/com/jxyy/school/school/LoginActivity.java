package com.jxyy.school.school;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.jxyy.school.school.util.loginHttp;

public class LoginActivity extends AppCompatActivity {

    private EditText usered;
    private EditText pwded;
    private CheckBox jzmmcb;
    private CheckBox zddlcb;
    private Button loginbt;

    private String username;
    private String password;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loginlayout);
        initview();
    }

    private SharedPreferences sp;
    Handler mhandler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0x01:

                    sp.edit().putString("username", username)
                            .putString("name", (String) msg.obj)
                            .putString("password", password)
                            .putBoolean("isjzmm", jzmmcb.isChecked())
                            .putBoolean("iszddl", zddlcb.isChecked())
                            .commit();
                    Toast.makeText(LoginActivity.this, "欢迎"+sp.getString("name",""), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 0x02:

                    Toast.makeText(LoginActivity.this, "学号或密码错误", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    private void initview() {
        sp = getSharedPreferences("result", MODE_PRIVATE);


        usered = (EditText) findViewById(R.id.xhed);
        pwded = (EditText) findViewById(R.id.pwded);
        jzmmcb = (CheckBox) findViewById(R.id.jzpswcb);
        zddlcb = (CheckBox) findViewById(R.id.zdcb);
        loginbt = (Button) findViewById(R.id.loginbt);

        boolean iszddl = sp.getBoolean("iszddl",false);
        boolean isjzmm = sp.getBoolean("isjzmm",false);

        if (iszddl){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (isjzmm){
            usered.setText(sp.getString("username",""));
            pwded.setText(sp.getString("password",""));
            jzmmcb.setChecked(true);
        }

        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usered.getText().toString();
                password = pwded.getText().toString();

                progressDialog = ProgressDialog.show(LoginActivity.this,"提示","正在登录");
                new loginHttp().login(username,password,mhandler);
//                progressDialog.show();
            }
        });
    }
}
