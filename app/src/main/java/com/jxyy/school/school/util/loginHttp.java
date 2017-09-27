package com.jxyy.school.school.util;

import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 98398 on 2017/6/26.
 */

public class loginHttp {

    private String username = "";
    private String password = "";
    private Handler mhandle;

    private String loginUrl = "http://jw.jxyyxy.com/default2.aspx";
    private static Cookie myCookie;
    private boolean islogin = false;

    public void login(String username, String password, Handler mhandle){
        this.username = username;
        this.password = password;
        this.mhandle = mhandle;


        new Thread(){
            public void run(){
                loginpost();
            }
        }.start();
    }

    private void loginpost(){
        HttpPost httprequest = new HttpPost(loginUrl);

        List<NameValuePair> list = new ArrayList<NameValuePair>();


        list.add(new BasicNameValuePair("TextBox1",username));
        list.add(new BasicNameValuePair("TextBox2",password));
        list.add(new BasicNameValuePair("RadioButtonList1","ѧ��"));
        list.add(new BasicNameValuePair("Button1",""));
        list.add(new BasicNameValuePair("__VIEWSTATE","dDwyOTIzOTAzMDY7Oz7gTm7Lj6715kvFkfvLWBEaHDN6dA=="));

        try {
            httprequest.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httprequest);

            if(response.getStatusLine().getStatusCode()==200){
                System.out.println("提交登录信息成功");
                List<Cookie> cookies = ((AbstractHttpClient)client).getCookieStore().getCookies();
                myCookie = cookies.get(0);
                BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"GB2312"));

                String Html = "";
                StringBuffer sb = new StringBuffer();

                String s = "";
                while ((s = br.readLine())!=null){
                    sb.append(s);
//                    System.out.println(s);
                }
                Html = sb.toString();
                System.out.println(Html);
                if(Html.indexOf("同学")>0){
                    System.out.println("欢迎");
                    islogin = true;
                    String name = Html.substring(Html.indexOf("xhxm")+6+12,Html.indexOf("同学"));
                    Message msg = mhandle.obtainMessage();
                    msg.what = 0x01;
                    msg.obj = name;
                    mhandle.sendMessage(msg);
                }else{
                    System.out.println("sb"+username+password);

                    Message msg = mhandle.obtainMessage();
                    msg.what = 0x02;
                    mhandle.sendMessage(msg);
                    islogin = false;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cookie getMyCookie(){
        return myCookie;
    }

    public void getresumes(final String xh,final String name, final Handler mhandler){

        new Thread(){
            @Override
            public void run() {

                System.out.println(xh+"++++++++++++++++++++++++++++++++++++++++");

                HttpPost httprequest = new HttpPost("http://jw.jxyyxy.com/xscj_gc.aspx?xh="+xh+"&xm="+name+"&gnmkdm=N121605");

                List<NameValuePair> list = new ArrayList<NameValuePair>();
//                list.add(new BasicNameValuePair("Button1","%B0%B4%D1%A7%C6%DA%B2%E9%D1%AF"));
//                list.add(new BasicNameValuePair("ddlXN",sn));
//                list.add(new BasicNameValuePair("ddlXQ",sq));
//                list.add(new BasicNameValuePair("__VIEWSTATE","dDwtMTA1MTQ3MjIyMjt0PHA8bDx4aDtzZmRjYms7ZHlieXNjajt6eGNqY3h4czs+O2w8MjAxNTEzMTg3OTtcZTtcZTswOz4+O2w8aTwxPjs\n" +
//                        "+O2w8dDw7bDxpPDE+O2k8Mz47aTw1PjtpPDc+O2k8OT47aTwxMT47aTwxMz47aTwxNT47aTwyND47aTwyNT47aTwyNj47aTwzMz47aTwzNT47aTwzNz47aTwzOT47aTw0MT47aTw0Mz47aTw0NT47aTw1NT47aTw2MD47PjtsPHQ8cDxwPGw8VGV4dDs\n" +
//                        "+O2w85a2m5Y+377yaMjAxNTEzMTg3OTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85aeT5ZCN77ya6YKT5paH5a6jOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlrabpmaLvvJrnlLXlrZDkv6Hmga\n" +
//                        "/lt6XnqIvns7s7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOS4k+S4mu+8mjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w86L2v5Lu25oqA5pyvOz4\n" +
//                        "+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzooYzmlL/nj63vvJoxNei9r+S7tjAyOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwyMDE1NzIyNDs\n" +
//                        "+Pjs+Ozs+O3Q8dDw7dDxpPDE4PjtAPFxlOzIwMDEtMjAwMjsyMDAyLTIwMDM7MjAwMy0yMDA0OzIwMDQtMjAwNTsyMDA1LTIwMDY\n" +
//                        "7MjAwNi0yMDA3OzIwMDctMjAwODsyMDA4LTIwMDk7MjAwOS0yMDEwOzIwMTAtMjAxMTsyMDExLTIwMTI7MjAxMi0yMDEzOzIwMTM\n" +
//                        "tMjAxNDsyMDE0LTIwMTU7MjAxNS0yMDE2OzIwMTYtMjAxNzsyMDE3LTIwMTg7PjtAPFxlOzIwMDEtMjAwMjsyMDAyLTIwMDM7MjA\n" +
//                        "wMy0yMDA0OzIwMDQtMjAwNTsyMDA1LTIwMDY7MjAwNi0yMDA3OzIwMDctMjAwODsyMDA4LTIwMDk7MjAwOS0yMDEwOzIwMTAtMjA\n" +
//                        "xMTsyMDExLTIwMTI7MjAxMi0yMDEzOzIwMTMtMjAxNDsyMDE0LTIwMTU7MjAxNS0yMDE2OzIwMTYtMjAxNzsyMDE3LTIwMTg7Pj47Pjs7Pjt0PHA8O3A8bDxvbmNsaWNrOz47bDx3aW5kb3cucHJpbnQoKVw7Oz4\n" +
//                        "+Pjs7Pjt0PHA8O3A8bDxvbmNsaWNrOz47bDx3aW5kb3cuY2xvc2UoKVw7Oz4+Pjs7Pjt0PHA8cDxsPFZpc2libGU7PjtsPG88dD47Pj47Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs\n" +
//                        "+Ozs+O3Q8QDA8Ozs7Ozs7Ozs7Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs+Ozs+O3Q8QDA8Ozs7Ozs7Ozs7Oz47Oz47dDxAMDxwPHA8bDxWaXNpYmxlOz47bDxvPGY\n" +
//                        "+Oz4+Oz47Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Ozs7Ozs7Ozs+Ozs+O3Q8cDxwPGw8VGV4dDs\n" +
//                        "+O2w8emY7Pj47Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs+Ozs+Oz4+Oz4+Oz6RPg+4kRkDhnC7tRoxTVoVH1od0w=="));
//


                httprequest.addHeader("Host", "jw.jxyyxy.com");
                httprequest.addHeader("Referer", "http://jw.jxyyxy.com/xs_main.aspx?xh="+xh);

                try {
                    httprequest.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
                    HttpClient client = new DefaultHttpClient();
                    ((AbstractHttpClient)client).getCookieStore().addCookie(getMyCookie());
                    HttpResponse response = client.execute(httprequest);
                    if (response.getStatusLine().getStatusCode()==200){
                        System.out.println("查询资料信息提交");

                        HttpEntity entity = response.getEntity();
                        InputStream is = entity.getContent();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is, "GB2312"));
                        //是读取要改编码的源，源的格式是GB2312的，安源格式读进来，然后再对源码转换成想要的编码就行

                        String data = "";
                        StringBuffer sb = new StringBuffer();
                        while ((data = br.readLine()) != null) {
                            sb.append(data);
                            System.out.println(data);
                        }
                        String Html = sb.toString();
                        Html = Html.replace(" ","");
                        String Table =  Html;
                        String bf[] = Table.substring(Table.indexOf("id=\"Label3\""),Table.indexOf("id=\"Label1\"")).split("</span>");

                        String resumes[] = new String[6];
                        for (int i=0;i<6;i++){
                            resumes[i] = bf[i].substring(bf[i].indexOf("Labe")+8);
                            System.out.println(resumes[i]);
                        }

                        resumes[3] = resumes[3]+resumes[4];
                        resumes[4] = resumes[5];
                        resumes[5] = null;
                        Message msg = mhandler.obtainMessage();
                        msg.obj = resumes;
                        mhandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public void getresult(final String sn, final String sq, final String name,final String xh, final Handler mhandler) {


        new Thread(){
            public void run(){
                System.out.println(xh+"++++++++++++++++++++++++++++++++++++++++"+name);

                HttpPost httprequest = new HttpPost("http://jw.jxyyxy.com/xscj_gc.aspx?xh="+xh+"&xm="+name+"&gnmkdm=N121605");

                List<NameValuePair> list = new ArrayList<NameValuePair>();
                list.add(new BasicNameValuePair("Button1","按学期查询"));
                list.add(new BasicNameValuePair("ddlXN",sn));
                list.add(new BasicNameValuePair("ddlXQ",sq));
                               list.add(new BasicNameValuePair("__VIEWSTATE","dDwtMTA1MTQ3MjIyMjt0PHA8bDx4aDtzZmRjYms7ZHlieXNjajt6eGNqY3h4czs+O2w8MjAxNTEzMTg3OTtcZTtcZTswOz4+O2w8aTwxPjs\n" +
                        "+O2w8dDw7bDxpPDE+O2k8Mz47aTw1PjtpPDc+O2k8OT47aTwxMT47aTwxMz47aTwxNT47aTwyND47aTwyNT47aTwyNj47aTwzMz47aTwzNT47aTwzNz47aTwzOT47aTw0MT47aTw0Mz47aTw0NT47aTw1NT47aTw2MD47PjtsPHQ8cDxwPGw8VGV4dDs\n" +
                        "+O2w85a2m5Y+377yaMjAxNTEzMTg3OTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w85aeT5ZCN77ya6YKT5paH5a6jOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlrabpmaLvvJrnlLXlrZDkv6Hmga\n" +
                        "/lt6XnqIvns7s7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOS4k+S4mu+8mjs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w86L2v5Lu25oqA5pyvOz4\n" +
                        "+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzooYzmlL/nj63vvJoxNei9r+S7tjAyOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDwyMDE1NzIyNDs\n" +
                        "+Pjs+Ozs+O3Q8dDw7dDxpPDE4PjtAPFxlOzIwMDEtMjAwMjsyMDAyLTIwMDM7MjAwMy0yMDA0OzIwMDQtMjAwNTsyMDA1LTIwMDY\n" +
                        "7MjAwNi0yMDA3OzIwMDctMjAwODsyMDA4LTIwMDk7MjAwOS0yMDEwOzIwMTAtMjAxMTsyMDExLTIwMTI7MjAxMi0yMDEzOzIwMTM\n" +
                        "tMjAxNDsyMDE0LTIwMTU7MjAxNS0yMDE2OzIwMTYtMjAxNzsyMDE3LTIwMTg7PjtAPFxlOzIwMDEtMjAwMjsyMDAyLTIwMDM7MjA\n" +
                        "wMy0yMDA0OzIwMDQtMjAwNTsyMDA1LTIwMDY7MjAwNi0yMDA3OzIwMDctMjAwODsyMDA4LTIwMDk7MjAwOS0yMDEwOzIwMTAtMjA\n" +
                        "xMTsyMDExLTIwMTI7MjAxMi0yMDEzOzIwMTMtMjAxNDsyMDE0LTIwMTU7MjAxNS0yMDE2OzIwMTYtMjAxNzsyMDE3LTIwMTg7Pj47Pjs7Pjt0PHA8O3A8bDxvbmNsaWNrOz47bDx3aW5kb3cucHJpbnQoKVw7Oz4\n" +
                        "+Pjs7Pjt0PHA8O3A8bDxvbmNsaWNrOz47bDx3aW5kb3cuY2xvc2UoKVw7Oz4+Pjs7Pjt0PHA8cDxsPFZpc2libGU7PjtsPG88dD47Pj47Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs\n" +
                        "+Ozs+O3Q8QDA8Ozs7Ozs7Ozs7Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs+Ozs+O3Q8QDA8Ozs7Ozs7Ozs7Oz47Oz47dDxAMDxwPHA8bDxWaXNpYmxlOz47bDxvPGY\n" +
                        "+Oz4+Oz47Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPHA8cDxsPFZpc2libGU7PjtsPG88Zj47Pj47Pjs7Ozs7Ozs7Ozs+Ozs+O3Q8cDxwPGw8VGV4dDs\n" +
                        "+O2w8emY7Pj47Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs+Ozs+Oz4+Oz4+Oz6RPg+4kRkDhnC7tRoxTVoVH1od0w=="));



                httprequest.addHeader("Host", "jw.jxyyxy.com");
                httprequest.addHeader("Referer", "http://jw.jxyyxy.com/xscj_gc.aspx?xh="+xh+"&xm="+"邓文宣"+"&gnmkdm=N121605");

                try {
                    httprequest.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
                    HttpClient client = new DefaultHttpClient();
                    ((AbstractHttpClient)client).getCookieStore().addCookie(getMyCookie());
                    HttpResponse response = client.execute(httprequest);
                    if (response.getStatusLine().getStatusCode()==200){
                        System.out.println("查询成绩信息提交");





                        HttpEntity entity = response.getEntity();
                        InputStream is = entity.getContent();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is, "GB2312"));
                        //是读取要改编码的源，源的格式是GB2312的，安源格式读进来，然后再对源码转换成想要的编码就行

                        String result = "";
                        String data = "";
                        StringBuffer sb = new StringBuffer();
                        while ((data = br.readLine()) != null) {
                            sb.append(data);
                    System.out.println(data);
                        }
                        String Html = sb.toString();
                        Html = Html.replace(" ","");
                        String Table =  Html.substring(Html.indexOf("<table"),Html.indexOf("</table>")+8);  //此时result中就是我们成绩的HTML的源代码了

                        String trs[] = (Table.substring(Table.indexOf("<tr"),Table.lastIndexOf("</tr>"))).split("</tr>");
                        List<Map<String,String>> list1 = new ArrayList<Map<String,String>>();
                        for(String tr:trs){
                            Map map = new HashMap();
                            String tds[] = (tr.substring(tr.indexOf("<td>"),tr.lastIndexOf("</td>"))).split("</td>");
                            int i=0;
                            for(String s:tds){
                                System.out.println(s);
                                map.put(""+i,s.replace("<td>",""));
                                i++;
                            }
                            list1.add(map);

                        }

                        Message msg = mhandler.obtainMessage();
                        msg.what = 0x01;
                        msg.obj = list1;
                        mhandler.sendMessage(msg);
                        System.out.println(Html);













                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }
}
