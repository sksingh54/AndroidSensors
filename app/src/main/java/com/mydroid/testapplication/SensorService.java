package com.mydroid.testapplication;

import android.app.IntentService;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.mydroid.testapplication.data.AppDatabase;
import com.mydroid.testapplication.data.Contract;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class SensorService extends Service implements SensorEventListener {
    public SensorService() {
        super();
    }

    GPSTracker gps;
    SensorManager mAcceleromanager,mGyromanager,mMagnetomanager;
    LocationManager mLocationManager;
    Sensor gyro;
    Sensor accelero;
    Sensor magneto;
    private AppDatabase mAppDatabase;
    private SQLiteDatabase db;
    private SQLiteDatabase dbRead;
    private ContentValues values;
    private Calendar cal;
    String currentLocation;
    String oldTime;
    String magnetoData;
    float x_axisM,y_axisM,z_axisM,x_axisA,y_axisA,z_axisA,x_axisG,y_axisG,z_axisG;


//    PowerManager powerManager;
//    PowerManager.WakeLock wakeLock;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "SensorService Started", Toast.LENGTH_SHORT).show();
        oldTime = cal.getInstance().getTime().toString();

        // wakelock
//        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
//        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
//                "MyWakelockTag");
//        wakeLock.acquire();

        //create loation object
//        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,this);
        // Creating sensors object
        mAcceleromanager = (SensorManager) getSystemService(SENSOR_SERVICE);      //sensor manager instance
        mGyromanager = (SensorManager) getSystemService(SENSOR_SERVICE);      //sensor manager instance
        mMagnetomanager = (SensorManager) getSystemService(SENSOR_SERVICE);      //sensor manager instance
        accelero = mAcceleromanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);     // getting acceletometer
        gyro = mGyromanager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);                 //getting gyroscope
        magneto = mMagnetomanager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
//        List<Sensor> list = msensormanager.getSensorList(Sensor.TYPE_ACCELEROMETER);
//        Log.e("Sensor LISTS:", list.toString());
//        System.out.println("helllllllloooooooooooooo");
        mGyromanager.registerListener(this, gyro,SensorManager.SENSOR_DELAY_FASTEST);
        mAcceleromanager.registerListener(this, accelero,SensorManager.SENSOR_DELAY_NORMAL);
        mMagnetomanager.registerListener(this, magneto,SensorManager.SENSOR_DELAY_NORMAL);

        // create databases

        mAppDatabase = new AppDatabase(this);
        db = mAppDatabase.getWritableDatabase();
        values = new ContentValues();
        dbRead = mAppDatabase.getReadableDatabase();

        gps = new GPSTracker(this); // get user defined gps service

        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        String currentTime = cal.getInstance().getTime().toString();
        currentLocation = "Lat = " + gps.getLatitude() + " Lon = " + gps.getLongitude();
//        Log.e("Sleep", "sleeping 5 sec");
        Sensor sensor = event.sensor;


            String[] oldtok_1 = oldTime.split("\\s");
            String[] oldtok_2 = oldtok_1[3].split(":");
            String[] currenttok_1 = currentTime.split("\\s");
            String[] currenttok_2 = currenttok_1[3].split(":");


            if ((Math.abs(Integer.parseInt(oldtok_2[2]) - Integer.parseInt(currenttok_2[2])) >= 1)) { //get sensor values every 5 seconds

                    if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {

                        float x_axisG = event.values[0];
                        float y_axisG = event.values[1];
                        float z_axisG = event.values[2];

                        Log.e("x_axisG = ", Float.toString(x_axisG));
                        Log.e("y_axisG = ", Float.toString(y_axisG));
                        Log.e("z_axisG = ", Float.toString(z_axisG));

//                        String gyroData = "\n---time = " + cal.getInstance().getTime().toString() + "/nlocation: " + currentLocation +
//                                "\nx_axisGyro = " + Float.toString(x_axisG) + "\ny_axisGyro = " + Float.toString(y_axisG) +
//                                "\nz_axisGyro = " + Float.toString(z_axisG);
//                        appendLog(gyroData);
                    }

                if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    float x_axisA = event.values[0];
                    float y_axisA = event.values[1];
                    float z_axisA = event.values[2];

                    Log.e("x_axisA = ", Float.toString(x_axisA));
                    Log.e("y_axisA = ", Float.toString(y_axisA));
                    Log.e("z_axisA = ", Float.toString(z_axisA));

//                    if (z_axisA > -11.0 && z_axisA < -9.0) {
//                        Log.e("x_axisA = ", Float.toString(x_axisA));
//                        Log.e("y_axisA = ", Float.toString(y_axisA));
//                        Log.e("z_axisA = ", Float.toString(z_axisA));
//                    }

//                    String acceleroData = "\n---time = " + cal.getInstance().getTime().toString() + "\nlocation: " + currentLocation +
//                            "\nx_axisAccelero = " + Float.toString(x_axisA) + "\ny_axisAccelero = " + Float.toString(y_axisA) +
//                            "\nz_axisAcceleto = " + Float.toString(z_axisA);
//                    appendLog(acceleroData);

//                    values.put(Contract.Accele.TIME, cal.getInstance().getTime().toString());
//                    values.put(Contract.Accele.LOCATION, currentLocation);
//                    values.put(Contract.Accele.ACCEL_COLUMN_NAME_X, x_axisA);
//                    values.put(Contract.Accele.ACCEL_COLUMN_NAME_Y, y_axisA);
//                    values.put(Contract.Accele.ACCEL_COLUMN_NAME_Z, z_axisA);
//                    db.insert(
//                            Contract.Accele.TABLE_NAME,
//                            null,
//                            values);


                }



                if(sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){

                    float x_axisM = event.values[0];
                    float y_axisM = event.values[1];
                    float z_axisM = event.values[2];

                    Log.e("x_axisM = ", Float.toString(x_axisM));
                    Log.e("y_axisM= ", Float.toString(y_axisM));
                    Log.e("z_axisM = ", Float.toString(z_axisM));

//                    String magnetoData = "\n---time = " + cal.getInstance().getTime().toString() + "\nlocation: " + currentLocation +
//                            "\nx_axisMagneto = " + Float.toString(x_axisM) + "\ny_axisMagneto = " + Float.toString(y_axisM) +
//                            "\nz_axisMagneto = " + Float.toString(z_axisM);
//                    appendLog(magnetoData);
                    magnetoData = "\n---time = " + cal.getInstance().getTime().toString() + "\nlocation: " + currentLocation +
                            "\nx_axisMagneto = " + Float.toString(x_axisA) +
                            "\ny_axisMagneto = " + Float.toString(y_axisA) +
                            "\nz_axisMagneto = " + Float.toString(z_axisA) +
                            "\nx_axisMagneto = " + Float.toString(x_axisG) +
                            "\ny_axisMagneto = " + Float.toString(y_axisG) +
                            "\nz_axisMagneto = " + Float.toString(z_axisG) +
                            "\nx_axisMagneto = " + Float.toString(x_axisM) +
                            "\ny_axisMagneto = " + Float.toString(y_axisM) +
                            "\nz_axisMagneto = " + Float.toString(z_axisM);

                }

              oldTime = cal.getInstance().getTime().toString();

                appendLog(magnetoData);

            }
//        stopSelf();

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onCreate(){

    }

//    @Override
//    protected void onHandleIntent(Intent intent) {
//        Bundle extras = intent.getExtras();
//        // Do the work that requires your app to keep the CPU running.
//        // ...
//        // Release the wake lock provided by the WakefulBroadcastReceiver.
//        MyWakefulReceiver.completeWakefulIntent(intent);
//    }


    public void appendLog(String text)
    {
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput("sensorFile.txt", Context.MODE_APPEND);
            outputStream.write(text.getBytes());
//            Log.e("inside try",outputStream.toString());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        FileInputStream fin;
//        try{
//            fin = openFileInput("sensorFile.txt");
//            int c;
//            String temp="";
//            while( (c = fin.read()) != -1){
//                temp = temp + Character.toString((char)c);
//
//            }
//            Log.e("FILE READ",temp);
//            //string temp contains all the data of the file.
//            fin.close();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
    }


    @Override
    public void onDestroy(){
        mAcceleromanager.unregisterListener(this);
        mGyromanager.unregisterListener(this);
        mMagnetomanager.unregisterListener(this);
//        db.close();
//        wakeLock.release();
        Toast.makeText(this, "SensorService Stopped", Toast.LENGTH_SHORT).show();
    }

}





