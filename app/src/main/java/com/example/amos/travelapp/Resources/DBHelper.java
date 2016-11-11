package com.example.amos.travelapp.Resources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.amos.travelapp.Resources.TableInfo;

/**
 * Created by Amos on 30/10/15.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="ATTRACTIONS.db";
    private static final int DATABASE_VERSION=1;
    private static final String QUERY_PT= //Create Table for Public Transport
            "CREATE TABLE " + TableInfo.info.TABLE_NAME_PT + "("
                    + "LOCATION" + " TEXT,"
                    + TableInfo.info.MBS + " TEXT,"
                    + TableInfo.info.SF + " TEXT,"
                    + TableInfo.info.VC + " TEXT,"
                    + TableInfo.info.RWS + " TEXT,"
                    + TableInfo.info.BTRT + " TEXT,"
                    + TableInfo.info.ZOO + " TEXT);";

    private static final String QUERY_TAXI= //Create Table for Taxi
            "CREATE TABLE " + TableInfo.info.TABLE_NAME_Taxi + "("
                    + "LOCATION" + " TEXT,"
                    + TableInfo.info.MBS + " TEXT,"
                    + TableInfo.info.SF + " TEXT,"
                    + TableInfo.info.VC + " TEXT,"
                    + TableInfo.info.RWS + " TEXT,"
                    + TableInfo.info.BTRT + " TEXT,"
                    + TableInfo.info.ZOO + " TEXT);";
    private static final String QUERY_FOOT= //Create Table for Foot
            "CREATE TABLE " + TableInfo.info.TABLE_NAME_Foot + "("
                    + "LOCATION" + " TEXT,"
                    + TableInfo.info.MBS + " INTEGER,"
                    + TableInfo.info.SF + " INTEGER,"
                    + TableInfo.info.VC + " INTEGER,"
                    + TableInfo.info.RWS + " INTEGER,"
                    + TableInfo.info.BTRT + " INTEGER,"
                    + TableInfo.info.ZOO + " INTEGER);";


    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_PT);
        db.execSQL(QUERY_TAXI);
        db.execSQL(QUERY_FOOT);

        //Adding details to PT table
        String PT_add_1 = "INSERT INTO "+TableInfo.info.TABLE_NAME_PT+ "("       //Add  MBS details into PT table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Marina Bay Sands','0-0','0.83-17','1.18-26','4.03-35','0.88-19','1.96-84')"; //amount($)-time(mins)
        String PT_add_2 = "INSERT INTO "+TableInfo.info.TABLE_NAME_PT+ "("       //Add  MBS details into PT table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Singapore Flyer','0.83-17','0-0','1.26-31','4.03-38','0.98-24','1.89-85')"; //amount($)-time(mins)
        String PT_add_3 = "INSERT INTO "+TableInfo.info.TABLE_NAME_PT+ "("       //Add  MBS details into PT table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Vivo City','1.18-24','1.26-29','0-0','2.00-10','0.98-18','1.99-85')"; //amount($)-time(mins)
        String PT_add_4 = "INSERT INTO "+TableInfo.info.TABLE_NAME_PT+ "("       //Add  MBS details into PT table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Resorts World Sentosa','1.18-33','1.26-38','0.00-10','0-0','0.98-27','1.99-92')"; //amount($)-time(mins)
        String PT_add_5 = "INSERT INTO "+TableInfo.info.TABLE_NAME_PT+ "("       //Add  MBS details into PT table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Buddha Tooth Relic Temple','0.88-18','0.98-23','0.98-19','3.98-28','0-0','1.91-83')"; //amount($)-time(mins)
        String PT_add_6 = "INSERT INTO "+TableInfo.info.TABLE_NAME_PT+ "("       //Add  MBS details into PT table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Zoo','1.88-86','1.96-87','2.11-86','4.99-96','1.91-84','0-0')"; //amount($)-time(mins)

        //Adding details to Taxi table
        String Taxi_add_1 = "INSERT INTO "+TableInfo.info.TABLE_NAME_Taxi+ "("       //Add  MBS details into Taxi table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Marina Bay Sands','0-0','3.22-3','6.96-14','8.50-19','4.98-8','18.40-30')"; //amount($)-time(mins)
        String Taxi_add_2 = "INSERT INTO "+TableInfo.info.TABLE_NAME_Taxi+ "("       //Add  MBS details into Taxi table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Singapore Flyer','4.32-6','0-0','7.84-13','9.38-18','4.76-8','18.18-29')"; //amount($)-time(mins)
        String Taxi_add_3 = "INSERT INTO "+TableInfo.info.TABLE_NAME_Taxi+ "("       //Add  MBS details into Taxi table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Vivo City','8.30-12','7.96-14','0-0','4.54-9','6.42-11','22.58-31')"; //amount($)-time(mins)
        String Taxi_add_4 = "INSERT INTO "+TableInfo.info.TABLE_NAME_Taxi+ "("       //Add  MBS details into Taxi table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Resorts World Sentosa','8.74-13','8.40-14','3.22-4','0-0','6.64-12','22.80-32')"; //amount($)-time(mins)
        String Taxi_add_5 = "INSERT INTO "+TableInfo.info.TABLE_NAME_Taxi+ "("       //Add  MBS details into Taxi table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Buddha Tooth Relic Temple','5.32-7','4.76-8','4.98-9','6.52-14','0-0','18.40-30')"; //amount($)-time(mins)
        String Taxi_add_6 = "INSERT INTO "+TableInfo.info.TABLE_NAME_Taxi+ "("       //Add  MBS details into Taxi table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Zoo','22.48-32','19.40-29','21.48-32','23.68-36','21.60-30','0-0')"; //amount($)-time(mins)

        //Adding details to Foot table
        String Foot_add_1 = "INSERT INTO "+TableInfo.info.TABLE_NAME_Foot+ "("       //Add  MBS details into Foot table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Marina Bay Sands',0,14,69,76,28,269)"; //time(mins)
        String Foot_add_2 = "INSERT INTO "+TableInfo.info.TABLE_NAME_Foot+ "("       //Add  MBS details into Foot table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Singapore Flyer',14,0,81,88,39,264)"; //time(mins)
        String Foot_add_3 = "INSERT INTO "+TableInfo.info.TABLE_NAME_Foot+ "("       //Add  MBS details into Foot table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Vivo City',69,81,0,12,47,270)"; //time(mins)
        String Foot_add_4 = "INSERT INTO "+TableInfo.info.TABLE_NAME_Foot+ "("       //Add  MBS details into Foot table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Resorts World Sentosa',76,88,12,0,55,285)"; //time(mins)
        String Foot_add_5 = "INSERT INTO "+TableInfo.info.TABLE_NAME_Foot+ "("       //Add  MBS details into Foot table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Buddha Tooth Relic Temple',28,39,47,55,0,264)"; //time(mins)
        String Foot_add_6 = "INSERT INTO "+TableInfo.info.TABLE_NAME_Foot+ "("       //Add  MBS details into Foot table
                + "LOCATION," + TableInfo.info.MBS+"," + TableInfo.info.SF+"," + TableInfo.info.VC+"," + TableInfo.info.RWS+"," + TableInfo.info.BTRT+"," + TableInfo.info.ZOO+")"
                + "VALUES('Zoo',269,264,270,285,264,0)"; //time(mins)

        db.execSQL(PT_add_1);
        db.execSQL(PT_add_2);
        db.execSQL(PT_add_3);
        db.execSQL(PT_add_4);
        db.execSQL(PT_add_5);
        db.execSQL(PT_add_6);

        db.execSQL(Taxi_add_1);
        db.execSQL(Taxi_add_2);
        db.execSQL(Taxi_add_3);
        db.execSQL(Taxi_add_4);
        db.execSQL(Taxi_add_5);
        db.execSQL(Taxi_add_6);

        db.execSQL(Foot_add_1);
        db.execSQL(Foot_add_2);
        db.execSQL(Foot_add_3);
        db.execSQL(Foot_add_4);
        db.execSQL(Foot_add_5);
        db.execSQL(Foot_add_6);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
