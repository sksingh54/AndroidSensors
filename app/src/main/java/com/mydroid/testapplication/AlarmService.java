package com.mydroid.testapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.SystemClock;
import android.widget.Toast;

public class AlarmService extends Service {
    public AlarmService() {
    }
    AlarmManager alarmMgr;
    CountDownTimer Ctimer;
    PendingIntent alarmIntent;
    PackageManager pm;
    ComponentName receiver;
    long minterval = 5000; //5 sec interval
    private String action;
    Intent intentSensor;

//    private final BroadcastReceiver myreceiver = new BroadcastReceiver() {
//
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            action = intent.getAction();
//            if (action.equals(AlarmManager.ACTION_NEXT_ALARM_CLOCK_CHANGED)){
//                context.startService(new Intent(context, ServiceToSchedule.class));
//            }
//        }
//
//    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Alarm to trigger Sensor Service at some interval


//        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent Alarmintent = new Intent(this, SensorAlarmTrigger.class);
//        alarmIntent = PendingIntent.getBroadcast(this, 3333, Alarmintent, 0);
//        receiver = new ComponentName(this, SensorAlarmTrigger.class);
//        pm = this.getPackageManager();
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);
//        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime() + minterval,
//                500, alarmIntent);
        intentSensor = new Intent(this,SensorService.class);

        Ctimer = new CountDownTimer(30*60000, 5000) {

            public void onTick(long millisUntilFinished) {
                startService(intentSensor);
            }

            public void onFinish() {

            }
        }.start();

        Toast.makeText(this,"Alarm Set",Toast.LENGTH_SHORT).show();

        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy(){
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                PackageManager.DONT_KILL_APP);
        Ctimer.cancel();
        stopService(intentSensor);
//        Toast.makeText(this, "SensorService Stopped", Toast.LENGTH_SHORT).show();
    }
}
