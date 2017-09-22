package com.mydroid.testapplication;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mydroid.testapplication.data.AppDatabase;
import com.mydroid.testapplication.data.Contract;

import java.io.FileInputStream;

public class FetchData extends Service {
    public FetchData() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        TextView textView; //textview
        AppDatabase mAppdatase = new AppDatabase(this);

        SQLiteDatabase dbRead = mAppdatase.getReadableDatabase();
        String selecQuery = "SELECT  * FROM " + Contract.Accele.TABLE_NAME;
        Cursor cursor = dbRead.rawQuery(selecQuery,null);

        if(cursor.moveToFirst()){
            do {
                String str = cursor.getInt(0)+ "    "+ cursor.getString(1) +
                        "   " + cursor.getString(2)+ "  "+ cursor.getFloat(3) +"   " +
                        cursor.getFloat(4) +  " "  + cursor.getFloat(5) ;
                Log.e("database: ", str);
            }while(cursor.moveToNext());
        }
        cursor.close();
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
//            textView.setText(temp);
//            //string temp contains all the data of the file.
//            fin.close();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
