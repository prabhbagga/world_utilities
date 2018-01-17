package com.example.prabhdeep.currencyconvertor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class time extends AppCompatActivity {
    Thread myThread = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        Runnable runnable = new CountDownRunner();
        myThread = new Thread(runnable);

        Button btn = findViewById(R.id.btnTime);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myThread.start();
            }
        });



    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    TextView txtCurrentTime =findViewById(R.id.lbltime);
                    TextView txtCurrentDate = findViewById(R.id.lbldate);
                    SimpleDateFormat sdf= new SimpleDateFormat("hh:mm a");
                    SimpleDateFormat sdf2 =new SimpleDateFormat("dd.MM.yyyy");
                    Calendar c = Calendar.getInstance();
                    String curTime = sdf.format(c.getTime());
                    String cuDate =sdf2.format(c.getTime());
                    txtCurrentTime.setText(curTime);
                    txtCurrentDate.setText(cuDate);

                } catch (Exception e) {
                }
            }
        });
    }


    class CountDownRunner implements Runnable {
        // @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                }
            }
        }
    }
}
