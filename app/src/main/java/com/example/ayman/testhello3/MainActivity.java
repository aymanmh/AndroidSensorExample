package com.example.ayman.testhello3;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private MyReceiver myReceiver;
    private IntentFilter mIntentFilter;

    public static final String TAG = MainActivity.class.getSimpleName();
    private TextView textView;
    private EditText editText;

    private Timer t;
    private int TimeCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    Log.d(TAG, "in oncreate()");
        textView = (TextView)findViewById(R.id.text_view);
        editText = (EditText)findViewById(R.id.myTextBox);

        Button myEnterButton = (Button) findViewById(R.id.myBtn1);
        myEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dotheClick(v);
            }
        });

      //  final Intent intent = new Intent(this,SimpleService.class);
       // startService(intent);

      //  MyIntentService.startActionLogMe(this,"firt log from onCreate");

        //final Intent intent = new Intent( MyReceiver.ACTION_MY_DATA);
        //sendBroadcast(intent);
      //  LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

        myReceiver = new MyReceiver();
        mIntentFilter = new IntentFilter(MyReceiver.ACTION_MY_DATA);
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                runOnUiThread(new Runnable() {
                    public void run() {
                        textView.setText(String.valueOf(TimeCounter)); // you can set it to a textView to show it to the user to see the time passing while he is writing.
                        TimeCounter++;
                    }
                });

            }
        }, 1000, 1000); // 1000 means start from 1 sec, and the second 1000 is do the loop each 1 sec.

    }





    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "ONPAAAAAuSe");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
    }

    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,mIntentFilter);
    }
    public void dotheClick(View v) {
        t.cancel();//stopping the timer when ready to stop.
        MyIntentService.startActionLogMe(this,"clickedButton");
return;
      //  String t = editText.getText().toString();
      //  textView.setText(String.format("ok, %s !", t));
    }
}
