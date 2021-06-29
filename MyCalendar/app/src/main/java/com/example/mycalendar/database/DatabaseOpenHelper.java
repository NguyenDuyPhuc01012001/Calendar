package com.example.mycalendar.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static String DB_PATH = "/databases/";

    public static int dbVersion = 1;

    public static String DB_NAME = "TodayInHistory.db";

    private SQLiteDatabase myDatabase;

    private Context mContext;

    public DatabaseOpenHelper(Context context) {
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

    public List<String> VIETQUERYHISTORY(String day, String month)
    {
        List<String> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        int dayint = Integer.parseInt(day);
        int monthint = Integer.parseInt(month);

        if(dayint > 9 && monthint>9)
        {
            c= db.rawQuery("SELECT NameEvent FROM EventCalendar WHERE strftime('%d',DateEvent) = '" + day + "' and strftime('%m',DateEvent) = '" + month + "'",new String[]{});
        }
        else if(dayint > 9 && monthint <=9)
        {
            c= db.rawQuery("SELECT NameEvent FROM EventCalendar WHERE strftime('%d',DateEvent) = '" + day + "' and strftime('%m',DateEvent) = '0" + month + "'",new String[]{});
        }
        else if(dayint <= 9 && monthint > 9)
        {
            c= db.rawQuery("SELECT NameEvent FROM EventCalendar WHERE strftime('%d',DateEvent) = '0" + day + "' and strftime('%m',DateEvent) = '" + month + "'",new String[]{});
        }
        else
        {
            c= db.rawQuery("SELECT NameEvent FROM EventCalendar WHERE strftime('%d',DateEvent) = '0" + day + "' and strftime('%m',DateEvent) = '0" + month + "'",new String[]{});
        }
        while(c.moveToNext()){
            String History = c.getString(0);
            returnList.add(History);
        }
        c.close();
        db.close();
        return returnList;
    }

    public List<String> WORLDQUERYHISTORY(String day, String month)
    {
        List<String> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        int dayint = Integer.parseInt(day);
        int monthint = Integer.parseInt(month);

        if(dayint > 9 && monthint>9)
        {
            c= db.rawQuery("SELECT NameEvent FROM EventWorldCalendar WHERE strftime('%d',DateEvent) = '" + day + "' and strftime('%m',DateEvent) = '" + month + "'",new String[]{});
        }
        else if(dayint > 9 && monthint <=9)
        {
            c= db.rawQuery("SELECT NameEvent FROM EventWorldCalendar WHERE strftime('%d',DateEvent) = '" + day + "' and strftime('%m',DateEvent) = '0" + month + "'",new String[]{});
        }
        else if(dayint <= 9 && monthint > 9)
        {
            c= db.rawQuery("SELECT NameEvent FROM EventWorldCalendar WHERE strftime('%d',DateEvent) = '0" + day + "' and strftime('%m',DateEvent) = '" + month + "'",new String[]{});
        }
        else
        {
            c= db.rawQuery("SELECT NameEvent FROM EventWorldCalendar WHERE strftime('%d',DateEvent) = '0" + day + "' and strftime('%m',DateEvent) = '0" + month + "'",new String[]{});
        }
        while(c.moveToNext()){
            String History = c.getString(0);
            returnList.add(History);
        }
        c.close();
        db.close();
        return returnList;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
