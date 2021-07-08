package com.example.mycalendar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mycalendar.model.EventInfo;

import java.util.ArrayList;
import java.util.List;

public class EventDatabase extends SQLiteOpenHelper {

    public static final String EVENT_TABLE = "EVENT_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_DAY = "DAY";
    public static final String COLUMN_MONTH = "MONTH";
    public static final String COLUMN_YEAR = "YEAR";
    public static final String COLUMN_START_HOUR = "STARTHOUR";
    public static final String COLUMN_END_HOUR = "ENDHOUR";
    public static final String COLUMN_START_MINUTE = "STARTMINUTE";
    public static final String COLUMN_END_MINUTE = "ENDMINUTE";
    public static final String COLUMN_TITLE = "TITLE";
    public static final String COLUMN_ALL_DAY = "ALLDAY";
//

    public EventDatabase(@Nullable Context context) {
        super(context, "event.db", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + EVENT_TABLE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_DAY + " INT,"
                + COLUMN_MONTH + " INT,"
                + COLUMN_YEAR + " INT,"
                + COLUMN_START_HOUR + " INT,"
                + COLUMN_END_HOUR + " INT,"
                + COLUMN_START_MINUTE + " INT,"
                + COLUMN_END_MINUTE + " INT,"
                + COLUMN_ALL_DAY + " BOOLEAN)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public void addOne(EventInfo eventInfo)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,eventInfo.getTitle());
        cv.put(COLUMN_DAY,eventInfo.getDay());
        cv.put(COLUMN_MONTH,eventInfo.getMonth());
        cv.put(COLUMN_YEAR,eventInfo.getYear());
        cv.put(COLUMN_START_HOUR,eventInfo.getStartHour());
        cv.put(COLUMN_START_MINUTE,eventInfo.getStartMinute());
        cv.put(COLUMN_END_HOUR,eventInfo.getEndHour());
        cv.put(COLUMN_END_MINUTE,eventInfo.getEndMinute());
        cv.put(COLUMN_ALL_DAY, eventInfo.isAllDay());
        db.insert(EVENT_TABLE,null,cv);
    }

    public List<EventInfo> getEventday(int day, int month,int year){
        List<EventInfo> returnList = new ArrayList<>();
//
        String queryString = "SELECT * FROM " + EVENT_TABLE + " WHERE DAY = " + day + " AND MONTH = "
                + month + " AND YEAR = " + year;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst())
        {
            do{
                int ID = cursor.getInt(0);
                String title = cursor.getString(1);
                int Day = cursor.getInt(2);
                int Month = cursor.getInt(3);
                int Year = cursor.getInt(4);
                int startHour = cursor.getInt(5);
                int endHour = cursor.getInt(6);
                int startMinute = cursor.getInt(7);
                int endMinute = cursor.getInt(8);
                boolean isAllDay = cursor.getInt(9) > 0;
                String str_id = String.valueOf(ID);
                EventInfo eventInfo = new EventInfo(str_id,title,Day,Month,Year,startHour,startMinute,endHour,endMinute, "", 1, isAllDay);
                returnList.add(eventInfo);
            }while(cursor.moveToNext());
        }
        else
        {

        }

        cursor.close();
        db.close();
        return returnList;
    }
    public void EditEvent(EventInfo eventInfo)
    {
        int id = Integer.parseInt(eventInfo.getId());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,eventInfo.getTitle());
        cv.put(COLUMN_DAY,eventInfo.getDay());
        cv.put(COLUMN_MONTH,eventInfo.getMonth());
        cv.put(COLUMN_YEAR,eventInfo.getYear());
        cv.put(COLUMN_START_HOUR,eventInfo.getStartHour());
        cv.put(COLUMN_START_MINUTE,eventInfo.getStartMinute());
        cv.put(COLUMN_END_HOUR,eventInfo.getEndHour());
        cv.put(COLUMN_END_MINUTE,eventInfo.getEndMinute());
        cv.put(COLUMN_ALL_DAY,eventInfo.isAllDay());
        db.update(EVENT_TABLE,cv,"ID = " + id, null);
    }
    public List<EventInfo>  CheckID(int ID){
        List<EventInfo> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + EVENT_TABLE + " WHERE ID = " + ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst())
        {
            do{
                String title = cursor.getString(1);
                int Day = cursor.getInt(2);
                int Month = cursor.getInt(3);
                int Year = cursor.getInt(4);
                int startHour = cursor.getInt(5);
                int endHour = cursor.getInt(6);
                int startMinute = cursor.getInt(7);
                int endMinute = cursor.getInt(8);
                boolean isAllDay = cursor.getInt(9) > 0;
                String str_id = String.valueOf(ID);
                EventInfo eventInfo = new EventInfo(str_id,title,Day,Month,Year,startHour,startMinute,endHour,endMinute, "", 1, isAllDay);
                returnList.add(eventInfo);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }
    public void deleteDatabase(int ID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EVENT_TABLE,"ID = " + ID,null);
    }

}
