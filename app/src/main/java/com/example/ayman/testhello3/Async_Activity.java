package com.example.ayman.testhello3;

import android.database.DatabaseUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.text.format.DateUtils;
import android.widget.TextView;

public class Async_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_);

        final TextView textView = (TextView) findViewById(R.id.loadingTextV);
        startLoading();

    }

    public   class sampleTask extends AsyncTask<Void,Integer,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            final TextView textView = (TextView)findViewById(R.id.loadingTextV);
            textView.setText("doneeeeee");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Thread.sleep(DateUtils.SECOND_IN_MILLIS*20);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "nein";
            }
            return "ok";
        }
    }

    public void onComplete(String myString)
    {
        final TextView textView = (TextView)findViewById(R.id.loadingTextV);
        textView.setText(myString);
    }

    public void startLoading()
    {
        new Async_Activity.sampleTask().execute();
    }

}

