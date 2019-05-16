package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main2Activity extends AppCompatActivity implements Runnable{
    EditText rmb;
    TextView show;
    Handler handler;
    private  String updateDate="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rmb = findViewById(R.id.rmb);
        show = findViewById(R.id.showOut);
//获取sp里面保存的数据
        SharedPreferences sharedPreferences= getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        dollarRate =sharedPreferences.getFloat("dollar_rate",0.0f);
        euroRate =sharedPreferences.getFloat("euro_rate",0.0f);
        wonRate =sharedPreferences.getFloat("won_rate",0.0f);
        updateDate=sharedPreferences.getString("update_date","");

        //获取当前系统时间
       Date today= Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr =sdf.format(today);

        Log.i(TAG,"onCreat: sp dollarRate="+dollarRate);
        Log.i(TAG,"onCreat: sp euroRate="+euroRate);
        Log.i(TAG,"onCreat: sp wonRate="+wonRate);
        Log.i(TAG,"onCreat: sp updateDate="+updateDate);
        Log.i(TAG,"onCreat:todayStr="+todayStr);

        //判断时间
        if(!todayStr.equals(updateDate)){
            Log.i(TAG, "onCreate: 需要更新");
            //开启子线程
            Thread t=new Thread(this);
            t.start();
        }else{
            Log.i(TAG, "onCreate: 不需要更新");
        }

//开启子线程
        Thread t=new Thread(this);
        t.start();

       handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==5){
                    Bundle bdl= (Bundle) msg.obj;
                    dollarRate=bdl.getFloat("dollar-rate");
                    euroRate=bdl.getFloat("euro-rate");
                    wonRate=bdl.getFloat("won-rate");
                    Log.i(TAG, "handleMessage: dollar"+dollarRate);
                    Log.i(TAG, "handleMessage: euro"+euroRate);
                    Log.i(TAG, "handleMessage: won"+wonRate);

                    //保存更新日期
                    SharedPreferences sharedPreferences= getSharedPreferences("myrate", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putFloat("dollar_rate",dollarRate);
                    editor.putFloat("euro_rate",euroRate);
                    editor.putFloat("won_rate",wonRate);
                    editor.putString("update_date",todayStr);
                    editor.apply();

                    Toast.makeText(Main2Activity.this, "汇率已更新", Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };


    }
    private float dollarRate=(1/6.7f);
    private float euroRate=(1/11f);
    private float wonRate=500;
    private final String TAG="rate";
    public void onclick(View btn){


        String str = rmb.getText().toString();
        float r = 0 ;
        if( str.length()>0){
            r= Float.parseFloat(str);
        }
        else{
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
        }
        float val = 0.0f ;
        if(btn.getId()==R.id.btn_dollar){
            val = r * dollarRate ;
        }
        else if(btn.getId()==R.id.btn_euro){
            val = r * euroRate ;
        }
        else if(btn.getId()==R.id.btn_won){
            val = r * wonRate ;
        }
        String val1 = String.valueOf(val);
        show.setText(val1);
Log.i(TAG,"onClick:r="+r);
    }
public void openConfig(){
    Intent config = new Intent(this,ConfigActivity.class);
   config.putExtra("dollar_rate_key",dollarRate);
    config.putExtra("euro_rate_key",euroRate);
    config.putExtra("won_rate_key",wonRate);

    Log.i(TAG,"openOne:dollar_rate_key="+dollarRate);
    Log.i(TAG,"openOne:euro_rate_key="+euroRate);
    Log.i(TAG,"openOne:won_rate_key="+wonRate);
    //startActivity(config);
    startActivityForResult(config,1);

}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_set){
            openConfig();
        }else if(item.getItemId()==R.id.open_list){

            //打开列表窗口

            Intent list = new Intent(this,MyList2Activity.class);

            startActivity(list);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==2){
            /* bdl.putFloat("key_dollar",newDollar);
        bdl.putFloat("key_euro",newEuro);
        bdl.putFloat("key_won",newWon);*/
            Bundle bundle = data.getExtras();
            dollarRate= bundle.getFloat("key_dollar",0.1f);
            euroRate= bundle.getFloat("key_euro",0.1f);
            wonRate= bundle.getFloat("key_won",0.1f);
            Log.i(TAG,"onActivityResult:dollarRate="+dollarRate);
            Log.i(TAG,"onActivityResult:euroRate="+euroRate);
            Log.i(TAG,"onActivityResult:wonRate="+wonRate);
            //将新设置的汇率保存到sp里
            SharedPreferences sharedPreferences= getSharedPreferences("myrate", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putFloat("dollar_rate",dollarRate);
            editor.putFloat("euro_rate",euroRate);
            editor.putFloat("won_rate",wonRate);
editor.commit();
            Log.i(TAG,"onActivityResult:数据已保存到sharedPreferences");

        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void run() {
        Log.i(TAG,"run:run().......");


            try {
                Thread.sleep( 2000);
            } catch (InterruptedException e) {
                e.printStackTrace();

        }
        //用于保存获取的汇率
        Bundle bundle;

        //获取网络数据
//        URL url= null;
////        try {
////            url = new URL("www.usd-cny.com/bankofchina.htm");
////            HttpURLConnection http= (HttpURLConnection) url.openConnection();
////            InputStream in = http.getInputStream();
////
////            String html=inputStream2string(in);
////            Log.i(TAG,"run:html="+html);
////            Document doc=Jsoup.parse(html);
////
////        } catch (MalformedURLException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }

        bundle= getFromBOC();


    }
    /*
    从bankofchina获取数据
     */

    private Bundle getFromBOC() {
        Bundle bundle= new Bundle();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.boc.cn/sourcedb/whpj/").get();
            //doc=Jsoup.parse(html);
            Log.i(TAG,"run:"+ doc.title());
          Elements tables = doc.getElementsByTag("table");

//
//          for (Element table:tables){
//              Log.e(TAG, "run: table["+i+"]="+table);
//              i++;
//          }

          Element table2 = tables.get(1);
          //  Log.i(TAG, "run: table6 "+table6);
            //获取TD中的数据
            Elements   tds=table2.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=8){
                Element td1= tds.get(i);
                Element td2= tds.get(i+5);
                Log.i(TAG, "run: "+td1 .text()+"==>"+td2 .text());
                String str1=td1.text();
                String val=td2.text();


                if("美元".equals(str1)){
                    bundle.putFloat("dollar-rate",100f/Float.parseFloat(val));

                }else if("欧元".equals(str1)){
                    bundle.putFloat("euro-rate",100f/Float.parseFloat(val));

                }else if("韩国元".equals(str1)){
                    bundle.putFloat("won-rate",100f/Float.parseFloat(val));

                }

                //bundle中保存所获取的汇率

                //获取message对象，用于返回主线程
                Message msg=handler.obtainMessage();
              //  msg.what=5;
              //  msg.obj="Hello from run()";
                msg.obj=bundle;
                handler.sendMessage(msg);
            }
            for(Element td:tds){
                Log.i(TAG, "run: td="+td);
                Log.i(TAG, "run: text="+td.text());
                Log.i(TAG, "run: html="+td.html());


            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return bundle;
    }


    private Bundle getFromUsdCny() {
        Bundle bundle= new Bundle();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            //doc=Jsoup.parse(html);
            Log.i(TAG,"run:"+ doc.title());
            Elements tables = doc.getElementsByTag("table");

//
//          for (Element table:tables){
//              Log.e(TAG, "run: table["+i+"]="+table);
//              i++;
//          }

            Element table6 = tables.get(5);
            //  Log.i(TAG, "run: table6 "+table6);
            //获取TD中的数据
            Elements   tds=table6.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=8){
                Element td1= tds.get(i);
                Element td2= tds.get(i+5);
                Log.i(TAG, "run: "+td1 .text()+"==>"+td2 .text());
                String str1=td1.text();
                String val=td2.text();


                if("美元".equals(str1)){
                    bundle.putFloat("dollar-rate",100f/Float.parseFloat(val));

                }else if("欧元".equals(str1)){
                    bundle.putFloat("euro-rate",100f/Float.parseFloat(val));

                }else if("韩国元".equals(str1)){
                    bundle.putFloat("won-rate",100f/Float.parseFloat(val));

                }

                //bundle中保存所获取的汇率

                //获取message对象，用于返回主线程
                Message msg=handler.obtainMessage();
                //  msg.what=5;
                //  msg.obj="Hello from run()";
                msg.obj=bundle;
                handler.sendMessage(msg);
            }
            for(Element td:tds){
                Log.i(TAG, "run: td="+td);
                Log.i(TAG, "run: text="+td.text());
                Log.i(TAG, "run: html="+td.html());


            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return bundle;
    }

    private String inputStream2string(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        for (; ; ) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }
}