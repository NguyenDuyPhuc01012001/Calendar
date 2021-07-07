package com.example.mycalendar.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.example.mycalendar.model.EventInfo;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class EventDatabaseOpenHelper extends SQLiteOpenHelper {

    public static String DB_PATH = "/databases/";

    public static int dbVersion = 1;

    public static String DB_NAME = "ImportantEvent.db";

    private SQLiteDatabase myDatabase;

    private Context mContext;

    public EventDatabaseOpenHelper(Context context) {
        super (context, DB_NAME, null, dbVersion);

        if(Build.VERSION.SDK_INT>=17)
        {
            DB_PATH = context.getApplicationInfo().dataDir+"/databases/";
        }
        else
        {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }

        mContext = context;
    }

    @Override
    public synchronized void close() {
        if(myDatabase!=null)
            myDatabase.close();
        super.close();
    }

    public boolean checkDatabase()
    {
        SQLiteDatabase TempDB = null;
        try{
            String path = DB_PATH + DB_NAME;
            SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READWRITE);
        } catch (Exception e) {
            this.getReadableDatabase();
        }
        if(TempDB!=null)
            TempDB.close();
        return TempDB!=null?true:false;
    }

    public void copyDatabase(){
        try{
            InputStream io = mContext.getAssets().open(DB_NAME);

            String outfilename = DB_PATH + DB_NAME;

            OutputStream outputStream = new FileOutputStream(outfilename);
            int length;

            byte[] buffer = new byte[1024];
            while((length = io.read(buffer))>0)
            {
                outputStream.write(buffer,0,length);
            }
            io.close();
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openDatabase(){
        String path = DB_PATH+DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public void CreateDatabase(){
        boolean isDBExist = checkDatabase();
        if(isDBExist)
        {}
        else
        {
            this.getReadableDatabase();
            try{
                copyDatabase();
            }
            catch (Exception ex){}
        }
    }

    public void getEventday(int day, int month, List<EventInfo> eventInfos, boolean isSolar){
        String queryString = "";
        if(isSolar == true)
        {
            queryString  = "SELECT * FROM SolarEvent WHERE Day = " + day + " AND Month = "
                    + month;
        }
        else
        {
            queryString  = "SELECT * FROM LunarEvent WHERE Day = " + day + " AND Month = "
                    + month;
        }


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst())
        {
            do{
                int ID = cursor.getInt(0);
                String title = cursor.getString(1);
                int Day = cursor.getInt(2);
                int Month = cursor.getInt(3);
                String str_id = String.valueOf(ID);
                EventInfo eventInfo = new EventInfo(str_id,title,Day,Month,0,0,0,0,0, "", 2, true);
                eventInfos.add(eventInfo);
            }while(cursor.moveToNext());
        }
        else
        {

        }

        cursor.close();
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
