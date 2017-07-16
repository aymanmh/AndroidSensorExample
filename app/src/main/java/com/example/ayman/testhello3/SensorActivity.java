package com.example.ayman.testhello3;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.security.Timestamp;
import java.util.Date;
import java.util.Set;

import static android.app.PendingIntent.getActivity;

public class SensorActivity extends Activity implements SensorEventListener {

    private static final String TAG = "MySensorActivity";
    private static final int CHEK_INTERVAL = 600;
    private static final int REQUEST_ENABLE_BT = 1853;
    private Sensor mLightSensor;
    private Sensor mAccelorometer;
    private Sensor mMagnetometer;

    private long mLastTimeStamp = 0;
    private int turnsCount = 0;
    private View mView;
    private TextView mTextView;
    private SensorManager mSensorManager ;
    private MyReceiver myReceiverHandle= null;
    private float[] mGeomagneticValues = null;
    public static float[] mAccelerometerValues = null;

    private BluetoothAdapter mBluetoothAdapter;


    public SensorActivity getObject()
    {
        return SensorActivity.this;
    }
    //Create broadcast object
    BroadcastReceiver mybroadcast = new BroadcastReceiver() {

        //When Event is published, onReceive method is called
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            Log.d("[BroadcastReceiver]", "MyReceiver");

            if(intent.getAction().equals(Intent.ACTION_SCREEN_ON))
            {
                Log.d("[BroadcastReceiver]", "Screen ON");
                if(mAccelorometer != null)
                {
                   mSensorManager.registerListener(getObject(),mAccelorometer,SensorManager.SENSOR_DELAY_FASTEST );
                }

                if(mMagnetometer != null)
                {
                    mSensorManager.registerListener(getObject(),mMagnetometer,SensorManager.SENSOR_DELAY_FASTEST );
                }
            }
            else if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
                Log.d("[BroadcastReceiver]", "Screen OFF");
                if(mAccelorometer != null)
                {
                    mSensorManager.unregisterListener(getObject(), mAccelorometer);
                }

                if(mMagnetometer != null)
                {
                    mSensorManager.unregisterListener(getObject(), mMagnetometer);
                }
            }

        }
    };

    public void registerSensorListner()
    {
        if(mAccelorometer != null)
        {
            mSensorManager.registerListener(getObject(),mAccelorometer,SensorManager.SENSOR_DELAY_FASTEST );
        }

        if(mMagnetometer != null)
        {
            mSensorManager.registerListener(getObject(),mMagnetometer,SensorManager.SENSOR_DELAY_FASTEST );
        }
    }

    public void unRegisterSensorListner()
    {
        if(mAccelorometer != null)
        {
            mSensorManager.unregisterListener(getObject(), mAccelorometer);
        }

        if(mMagnetometer != null)
        {
            mSensorManager.unregisterListener(getObject(), mMagnetometer);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        mTextView = (TextView)findViewById(R.id.lightValueText);

        mView = (View)findViewById(R.id.sensorView);
        mSensorManager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        myReceiverHandle = new MyReceiver();
        myReceiverHandle.setMainActivityHandler(this);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Log.d(TAG,"NOO Bluetooth");
        }
        else
        {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            // If there are paired devices
            if (pairedDevices.size() > 0) {
                // Loop through paired devices
                for (BluetoothDevice device : pairedDevices) {
                    Log.d(TAG, device.getName() + " - " + device.getAddress());
                }
            }
            else
                Log.d(TAG,"No paired devices found");
        }
        registerReceiver(myReceiverHandle, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(myReceiverHandle, new IntentFilter(Intent.ACTION_SCREEN_OFF));

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAccelorometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if(mAccelorometer == null)
            Log.d(TAG,"ACCELEROMETER Sensor Not Available");
        else if (mMagnetometer == null)
            Log.d(TAG,"MAGNETIC FIELD Sensor Not Available");
        else
        {
            //sensorManager.registerListener(this,mAccelorometer,SensorManager.SENSOR_DELAY_UI);
            mSensorManager.registerListener(this,mAccelorometer,SensorManager.SENSOR_DELAY_FASTEST );
            mSensorManager.registerListener(this,mMagnetometer,SensorManager.SENSOR_DELAY_FASTEST );

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

/*        SensorManager sensorManager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        mLightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(mLightSensor == null)
            Log.d(TAG,"Light Sensor Not Available");
        else
        {
            sensorManager.registerListener(this,mLightSensor,SensorManager.SENSOR_DELAY_UI);
        }

        mAccelorometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(mAccelorometer == null)
            Log.d(TAG,"ACCELEROMETER Sensor Not Available");
        else
        {
            //sensorManager.registerListener(this,mAccelorometer,SensorManager.SENSOR_DELAY_UI);
            sensorManager.registerListener(this,mAccelorometer,SensorManager.SENSOR_DELAY_UI,REPORT_LATENCY);

        }*/
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mAccelorometer != null)
        {
            mSensorManager.unregisterListener(this, mAccelorometer);
        }
        if(mMagnetometer != null)
        {
            mSensorManager.unregisterListener(this, mMagnetometer);
        }
        unregisterReceiver(myReceiverHandle );
    }

    @Override
    protected void onPause() {
        super.onPause();
/*        SensorManager sensorManager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);

        if(mLightSensor != null)
        {
            sensorManager.unregisterListener(this, mLightSensor);
        }

        if(mAccelorometer != null)
        {
            sensorManager.unregisterListener(this, mAccelorometer);
        }*/
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        double azimuth = 0;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mGeomagneticValues = event.values;
        }
        if (event.sensor == mAccelorometer)
        {
            mAccelerometerValues = event.values;


            float R[] = new float[9];
            float I[] = new float[9];
            if(mAccelerometerValues != null && mGeomagneticValues!= null) {
                boolean success = SensorManager.getRotationMatrix(R, I, mAccelerometerValues, mGeomagneticValues);

                if (success) {
                    float orientation[] = new float[3];
                    SensorManager.getOrientation(R, orientation);
                    // at this point, orientation contains the azimuth(direction), pitch and roll values.
                    azimuth = 180 * orientation[0] / Math.PI;

                }
            }
           // Log.d(TAG,"Accelerometer values:"+event.values[0]+" - "+event.values[1]+"-"+event.values[2]);
            float xValue = event.values[0];
//            int red = (int) (255*sensorReading);
//
//            sensorReading = Math.abs(Math.min(event.values[1], 1));
//            int green = (int) (255*sensorReading);
//
//            sensorReading = Math.abs(Math.min(event.values[2], 1));
//            int blue = (int) (255*sensorReading);

            long currentTimestamp = (new Date()).getTime()
                    + (event.timestamp - System.nanoTime()) / 1000000L;

            if(currentTimestamp - mLastTimeStamp > CHEK_INTERVAL)
            {
                mLastTimeStamp = currentTimestamp;
                //Log.d(TAG, "valid timestamp check "+ mLastTimeStamp);

                if (xValue > 7) {
                    turnsCount = turnsCount+1;
                    Log.d(TAG, "TURNED To the Left "+ turnsCount);
                    Log.d(TAG, "Azimuth: " + String.valueOf(azimuth));

                } else if (xValue < -7) {
                    turnsCount = turnsCount+1;
                    Log.d(TAG, "TURNED To the right " + turnsCount);
                    Log.d(TAG, "Azimuth: " + String.valueOf(azimuth));

                }
            }
            //mView.setBackgroundColor(Color.rgb(red,green,blue));

            //Log.d(TAG,"New Color:"+Color.rgb(red,green,blue));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
