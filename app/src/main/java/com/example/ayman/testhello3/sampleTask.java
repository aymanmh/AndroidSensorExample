package com.example.ayman.testhello3;

import android.os.AsyncTask;
import android.text.format.DateUtils;

public class sampleTask extends AsyncTask<Void,Integer,String>
{
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
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
