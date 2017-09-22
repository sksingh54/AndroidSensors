package com.mydroid.testapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class SensorAlarmTrigger extends BroadcastReceiver {
    public SensorAlarmTrigger() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.e("Alarmtrigeer","Started");
        context.startService(new Intent(context, SensorService.class));
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
