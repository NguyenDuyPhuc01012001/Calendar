package com.example.mycalendar.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.mycalendar.ChinaCalendar;
import com.example.mycalendar.database.MaximDatabase;
import com.example.mycalendar.model.DateTimeInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DayCalendarPresenter {
    DayCalendarInterface mDCInterface;

    public DayCalendarPresenter(DayCalendarInterface mDCInterface) {
        this.mDCInterface = mDCInterface;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void GetData(LocalDateTime selectedDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE,dd/MM/yyyy,HH", new Locale("vn"));
        String date_time = selectedDate.format(formatter);
        DateTimeInfo dateTimeInfo =new DateTimeInfo();

        String dayOfWeek=date_time.split(",")[0];
        String date=date_time.split(",")[1];
        String hour =date_time.split(",")[2];

        int day=Integer.parseInt(date.split("/")[0]);
        int month=Integer.parseInt(date.split("/")[1]);
        int year=Integer.parseInt(date.split("/")[2]);

        ChinaCalendar chinaCalendar=new ChinaCalendar(day,month,year,7);
        chinaCalendar.ConVertToLunar();

        String dateLunar = chinaCalendar.Solar2Lunar(day,month,year);
        dateTimeInfo.setDayOfMonthLunar(Integer.parseInt(dateLunar.split("/")[0]));
        dateTimeInfo.setMonthLunar(Integer.parseInt(dateLunar.split("/")[1]));

        String timeLunar=chinaCalendar.Time2TimeLunar(hour);
        dateTimeInfo.setTimeLunar(timeLunar);

        dateTimeInfo.setStrDayOfMonthLunar(chinaCalendar.getLunarDate());
        dateTimeInfo.setStrMonthLunar(chinaCalendar.getLunarMonth());
        dateTimeInfo.setStrYearLunar(chinaCalendar.getLunarYear());

        dateTimeInfo.setDayOfWeek(dayOfWeek);
        dateTimeInfo.setDayOfMonthSolar(day);
        dateTimeInfo.setMonthSolar(month);
        dateTimeInfo.setYearSolar(year);

        dateTimeInfo.setIsGoodTime(chinaCalendar.IsGoodTime(hour));
        dateTimeInfo.setIsGoodDay(chinaCalendar.IsZodiacDay(dateTimeInfo.getMonthLunar()));

        mDCInterface.LoadData(dateTimeInfo);
    }

    public void GetMaxim(MaximDatabase db) {
        if(db.count()==0)
            db.insertListMaxim();
        mDCInterface.LoadMaxim(db.getMaxim());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ShowBTN(LocalDateTime selectedDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("vn"));
        String date = selectedDate.format(formatter);
        String dateNow= LocalDate.now().format(formatter);
        mDCInterface.SetBTNVisibility(date.equals(dateNow));
    }
}
