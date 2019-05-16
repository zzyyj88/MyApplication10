package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView)findViewById(R.id.out);


        EditText inp = (EditText)findViewById(R.id.input);
        String str = inp.getText().toString();

        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(this);
        setContentView(R.layout.activity_main)       ;

    }

    @Override
    public void onClick(View v) {
        Log.i("click","onClick...");
        TextView output=findViewById(R.id.out);
        EditText input =(EditText)findViewById(R.id.input);
        String in = input.getText().toString();
        float f=0 ;
        float c;
        String a = String.valueOf(f);
        c = Math.round(((f*1.8f+32)*10)/10);
        output.setText(c+"C");

    }
}