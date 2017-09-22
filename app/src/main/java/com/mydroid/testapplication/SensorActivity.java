package com.mydroid.testapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mydroid.testapplication.data.AppDatabase;
import com.mydroid.testapplication.data.Contract;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;


public class SensorActivity extends Activity {


    Button button; //start/stop button
    TextView textView; //textview
    EditText source,dest,sadhan,weather;
    GPSTracker gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        Context context = getApplicationContext();
        Log.e("HELLO","hello");

        //screen wake
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        textView = (TextView) findViewById(R.id.text);
        source = (EditText) findViewById(R.id.locFrom);
        dest = (EditText) findViewById(R.id.locTo);
        sadhan = (EditText) findViewById(R.id.How);
        weather = (EditText) findViewById(R.id.Weather);

        gps = new GPSTracker(getApplicationContext());


        button = (Button) findViewById(R.id.Startbutton);



        if(isMyServiceRunning(SensorService.class)){
            button.setText("Stop");
        }
        else {
            button.setText("Start");
        }

        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();


            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + " Long: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
            Toast.makeText(getApplicationContext(),"Cant start location", Toast.LENGTH_LONG).show();
        }

        readFile();

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sensor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    protected void onStop()
    {

        super.onStop();
    }

//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//        // TODO Auto-generated method stub
//
//    }

/*    public void onSensorChanged(SensorEvent event){
        Sensor sensor = event.sensor;
        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x_axisA = event.values[0];
            float y_axisA = event.values[1];
            float z_axisA = event.values[2];
            if (z_axisA > -11.0 && z_axisA < -9.0){
                Log.e("x_axisA = ", Float.toString(x_axisA));
                Log.e("y_axisA = ", Float.toString(y_axisA));
                Log.e("z_axisA = ", Float.toString(z_axisA));
            }

//            values.put(Contract.Accele.ACCEL_COLUMN_NAME_X,x_axisA);
//            values.put(Contract.Accele.ACCEL_COLUMN_NAME_Y,y_axisA);
//            values.put(Contract.Accele.ACCEL_COLUMN_NAME_Z,z_axisA);
//            db.insert(
//                    Contract.Accele.TABLE_NAME,
//                    null,
//                    values);

            TextView textView = (TextView) findViewById(R.id.text);
            CharSequence str = textView.getText();
            textView.setText(str +"\n" + " x_axis = " + Float.toString(x_axisA)+ "\n" + " y_axis = " + Float.toString(y_axisA) +"\n" +
                    " z_axis = " + Float.toString(z_axisA) +"\n");


        }

        if(sensor.getType() == Sensor.TYPE_GYROSCOPE){
            if(event.values[0] == 0){
                Log.e("No gyroscope x", "value is 0");
            }
            if(event.values[1] == 0){
                Log.e("No gyroscope y", "value is 0");
            }
            if(event.values[2] == 0){
                Log.e("No gyroscope z", "value is 0");
            }
            float x_axis = event.values[0];
            float y_axis = event.values[1];
            float z_axis = event.values[2];

            Log.e("x_axis = ", Float.toString(x_axis));
            Log.e("y_axis = ", Float.toString(y_axis));
            Log.e("z_axis = ", Float.toString(z_axis));

//            TextView textView = (TextView) findViewById(R.id.text);
//            CharSequence str = textView.getText();
//            textView.setText(str +"\n" + " x_axis = " + Float.toString(x_axis)+ "\n" + " y_axis = " + Float.toString(x_axis) +"\n" +
//                    " z_axis = " + Float.toString(x_axis) +"\n");

        }


    } */

    public void startStop(View view){
        Intent start = new Intent(this, SensorService.class); // SensorService Intent
        if(isMyServiceRunning(SensorService.class)){
            stopService(start);
            button.setText("Start");
        }
        else {
            if (source.getText().toString().equals("")&& dest.getText().toString().equals("")
                    && sadhan.getText().toString().equals("") && weather.getText().toString().equals("")){
                Toast.makeText(this,"Fill the details first",Toast.LENGTH_LONG).show();
            }
            else{
                addDetails("\n***Details: Source: "+source.getText()+"\t Dest: "+dest.getText()+"\t Using: "+sadhan.getText()
                        +"\t Weather: "+weather.getText());
                startService(start);
                button.setText("Stop");
            }

        }

    }

    public void addDetails(String text)
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

    }


    public void readFile(){
        FileInputStream fin;
        try{
            fin = openFileInput("sensorFile.txt");
            int c;
            String temp="";
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);

            }
            Log.i("FILE READ",temp);
            textView.setText(temp);
            //string temp contains all the data of the file.
            fin.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
