package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main33);
        Button  button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.editText);
                String str = editText.getText().toString();
                int F;
                F= Integer.valueOf(str)+1;
                editText.setText(F+"");
            }
        })
        ;
        Button  button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.editText);
                String str = editText.getText().toString();
                int F;
                F= Integer.valueOf(str)+2;
                editText.setText(F+"");
            }
        })
        ;
        Button  button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.editText);
                String str = editText.getText().toString();
                int F;
                F= Integer.valueOf(str)+3;
                editText.setText(F+"");
            }
        })
        ;
        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.editText);
                editText.setText("0");
            }
        })
        ;
        Button  buttona = findViewById(R.id.buttona);
        buttona.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText editTexta = findViewById(R.id.editTexta);
                String stra = editTexta.getText().toString();
                int Fa;
                Fa= Integer.valueOf(stra)+1;
                editTexta.setText(Fa+"");
            }
        })
        ;
        Button  button2a = findViewById(R.id.button2a);
        button2a.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText editTexta = findViewById(R.id.editTexta);
                String stra = editTexta.getText().toString();
                int Fa;
                Fa= Integer.valueOf(stra)+2;
                editTexta.setText(Fa+"");
            }
        })
        ;
        Button  button3a = findViewById(R.id.button3a);
        button3a.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText editTexta = findViewById(R.id.editTexta);
                String stra = editTexta.getText().toString();
                int Fa;
                Fa= Integer.valueOf(stra)+3;
                editTexta.setText(Fa+"");
            }
        })
        ;
        Button button4a = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.editText);
                EditText editTexta = findViewById(R.id.editTexta);
                editText.setText("0");
                editTexta.setText("0");
            }
        })
        ;

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) {
        super.onSaveInstanceState(outState);
        EditText editText = findViewById(R.id.editText);
        String scorea = editText.getText().toString();
        EditText editTexta = findViewById(R.id.editTexta);
        String scoreb = editTexta.getText().toString();
        outState.putString("team_ascore",scorea);
        outState.putString("team_bscore",scoreb);
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState ) {
        super.onRestoreInstanceState(savedInstanceState);
        String scorea = savedInstanceState.getString("team_ascore");
        String scoreb = savedInstanceState.getString("team_bscore");
        EditText editText = findViewById(R.id.editText);
        editText.setText(scorea);
        EditText editTexta = findViewById(R.id.editTexta);
        editTexta.setText(scoreb);
    }
}