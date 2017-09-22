package com.mydroid.testapplication.data;

import android.provider.BaseColumns;

/**
 * Created by sksingh on 21-Aug-17.
 */
public class Contract {

    public Contract(){}

    public static final class Accele implements BaseColumns {        //inner class to implement basecolums to create schema of contract

        public static final String TABLE_NAME = "Accelerometer";       //table to enter data
        public static final String ACCEL_COLUMN_NAME_X = "Xaxis";   //COLUMN 1 to entry modes/constant values
        public static final String ACCEL_COLUMN_NAME_Y = "Yaxis"; //column 2 to enter timings of modes executed
        public static final String ACCEL_COLUMN_NAME_Z = "Zaxis"; //column 2 to enter timings of modes executed
        public static final String TIME = "timing";
        public static final String LOCATION = "location";

    }

    public static final class Gyro implements BaseColumns {        //inner class to implement basecolums to create schema of contract

        public static final String TABLE_NAME = "Gyroscope";       //table to enter data
        public static final String GYRO_COLUMN_NAME_X = "X-axis";   //COLUMN 1 to entry modes/constant values
        public static final String GYRO_COLUMN_NAME_Y = "Y-axis"; //column 2 to enter timings of modes executed
        public static final String GYRO_COLUMN_NAME_Z = "Z-axis"; //column 2 to enter timings of modes executed

    }

    public static final class Magneto implements BaseColumns {        //inner class to implement basecolums to create schema of contract

        public static final String TABLE_NAME = "Magnetometer";       //table to enter data
        public static final String MAG_COLUMN_NAME_X = "X-axis";   //COLUMN 1 to entry modes/constant values
        public static final String MAG_COLUMN_NAME_Y = "Y-axis"; //column 2 to enter timings of modes executed
        public static final String MAG_COLUMN_NAME_Z = "Z-axis"; //column 2 to enter timings of modes executed

    }

}
