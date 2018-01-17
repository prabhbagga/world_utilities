package com.example.prabhdeep.currencyconvertor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void Currencyconverter(View view)
    {
        Intent i =new Intent(this,CurrencyConverter.class);
        startActivity(i);
    }
    public void weather(View view)
    {
        Intent i =new Intent(this,Weather.class);
        startActivity(i);
    }
    public void translate(View view)
    {
        Intent i =new Intent(this,translator.class);
        startActivity(i);
    }
    public void worldclock(View view)
    {
        Intent i =new Intent(this,time.class);
        startActivity(i);
    }
}
