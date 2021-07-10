package com.example.mycalendar.presenter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.appota.lunarcore.LunarCoreHelper;
import com.example.mycalendar.ChinaCalendar;
import com.example.mycalendar.database.EventDatabase;
import com.example.mycalendar.database.EventDatabaseOpenHelper;
import com.example.mycalendar.model.DateTimeInfo;
import com.example.mycalendar.model.EventInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DayDetailPresenter {
    DayDetailInterface dayDetailInterface;

    private static String[] star = {"Giác", "Cang", "Đê", "Phòng", "Tâm", "Vĩ", "Cơ",
            "Đẩu", "Ngưu", "Nữ", "Hư", "Nguy", "Thất", "Bích",
            "Khuê", "Lâu", "Vị", "Mão", "Tất", "Chủy", "Sâm",
            "Tỉnh", "Quỷ", "Liễu", "Tinh", "Trương", "Dực", "Chẩn"};

    public DayDetailPresenter(DayDetailInterface dayDetailInterface) {
        this.dayDetailInterface = dayDetailInterface;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getData(LocalDateTime selectedDate)
    {
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
        Log.i("DateLunar",dateLunar);
        dateTimeInfo.setDayOfMonthLunar(Integer.parseInt(dateLunar.split("/")[0]));
        dateTimeInfo.setMonthLunar(Integer.parseInt(dateLunar.split("/")[1]));
        dateTimeInfo.setYearLunar(Integer.parseInt(dateLunar.split("/")[2]));

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

        dayDetailInterface.LoadData(dateTimeInfo);

    }
    public static String GetUnZodiacTime(int mDay, int mMonth, int mYear)
    {
        String dayChi = LunarCoreHelper.getChiDayLunar(mDay,mMonth,mYear), saveTmp = "";

        if (dayChi == "Dần" || dayChi =="Thân")
            saveTmp = "Dần (3h - 5h), Mão (5h - 7h), Ngọ (11h - 13h), Thân (15h -17h), Dậu (17h - 19h), Hợi (21h - 23h)";
        if (dayChi == "Mão"|| dayChi == "Dậu")
            saveTmp = "Sửu (1h - 3h), Thìn (7h - 9h), Tỵ (9h - 11h), Thân (15h - 17h), Tuất (19h - 21h), Hợi (21h - 23h)";
        if (dayChi == "Thìn" || dayChi == "Tuất")
            saveTmp = "Tý (23h - 1h), Sửu (1h - 3h), Mão (5h - 7h), Ngọ (11h - 13h), Mùi (13h - 15h), Tuất (19h - 21h)";
        if (dayChi == "Tỵ" || dayChi == "Hợi")
            saveTmp = "Tý (23h - 1h), Dần (3h - 5h), Mão (5h - 7h), Tỵ (9h - 11h), Thân (15h - 17h), Dậu (17h - 19h)";
        if (dayChi == "Tý" || dayChi == "Ngọ")
            saveTmp = "Dần (3h - 5h), Thìn (7h - 9h), Tỵ (9h - 11h), Mùi (13h - 15h), Tuất (19h - 21h), Hợi (21h - 23h)";
        if (dayChi == "Sửu" || dayChi == "Mùi")
            saveTmp = "Tý (23h - 1h), Sửu (1h - 3h), Thìn (7h - 9h), Ngọ (11h - 13h), Mùi (13h - 25h), Dậu (17h -19h)";
        return saveTmp;
    }
    public static String GetZodiacTime(int mDay, int mMonth, int mYear)
    {
        String dayChi = LunarCoreHelper.getChiDayLunar(mDay,mMonth,mYear), saveTmp = "";

        if (dayChi == "Dần" || dayChi =="Thân")
            saveTmp = "Tý (23h -1h),Sửu (1h - 3h),Thìn (7h - 9h),Tỵ (9h - 11h),Mùi (13h - 15h),Tuất (19h - 21h)";
        if (dayChi == "Mão"|| dayChi == "Dậu")
            saveTmp = "Tý (23h - 1h),Dần (3h - 5h),Mão (5h - 7h),Ngọ (11h - 13h),Mùi (13h - 15h),Dậu (17h - 19h)";
        if (dayChi == "Thìn" || dayChi == "Tuất")
            saveTmp = "Dần (3h - 5h),Thìn (7h - 9h),Tỵ (9h - 11h),Thân (15h - 17h),Dậu (17h - 19h),Hợi (21h - 23h)";
        if (dayChi == "Tỵ" || dayChi == "Hợi")
            saveTmp = "Sửu (1h - 3h),Thìn (7h - 9h),Ngọ (11h - 13h),Mùi (13h - 15h),Tuất (19h - 21h),Hợi (21h - 23h)";
        if (dayChi == "Tý" || dayChi == "Ngọ")
            saveTmp = "Tý (23h - 1h),Sửu (1h - 3h),Mão (5h - 7h),Ngọ (11h - 13h),Thân (15h - 17h),Dậu (17h - 19h)";
        if (dayChi == "Sửu" || dayChi == "Mùi")
            saveTmp = "Dần (3h - 5h),Mão (5h - 7h),Tỵ (9h - 11h),Thân (15h - 17h),Tuất (19h - 21h),Hợi (21h - 23h)";
        return saveTmp;
    }
    public static String GetHyThan(int mDay,int mMonth,int mYear)
    {
        String s = "";
        String sCan = LunarCoreHelper.getCanDayLunar(mDay,mMonth,mYear);
        switch (sCan)
        {
            case "Giáp":
                s = "Đông Bắc";
                break;
            case "Ất":
                s = "Tây Bắc";
                break;
            case "Bính":
                s = "Tây Nam";
                break;
            case "Đinh":
                s = "chính Nam";
                break;
            case "Mậu":
                s = "Đông Nam";
                break;
            case "Kỷ":
                s = "Đông Bắc";
                break;
            case "Canh":
                s = "Tây Bắc";
                break;
            case "Tân":
                s = "Tây Nam";
                break;
            case "Nhâm":
                s = "chính Nam";
                break;
            case "Quý":
                s = "Đông Nam";
                break;
            default:
                break;
        }
        return s;
    }
    public static String GetTaiThan(int mDay, int mMonth, int mYear)
    {
        String s = "";
        String sCan = LunarCoreHelper.getCanDayLunar(mDay,mMonth,mYear);
        switch (sCan)
        {
            case "Giáp":
                s = "Đông Nam";
                break;
            case "Ất":
                s = "Đông Nam";
                break;
            case "Bính":
                s = "Đông";
                break;
            case "Đinh":
                s = "Đông";
                break;
            case "Mậu":
                s = "Bắc";
                break;
            case "Kỷ":
                s = "Nam";
                break;
            case "Canh":
                s = "Tây Nam";
                break;
            case "Tân":
                s = "Tây Nam";
                break;
            case "Nhâm":
                s = "Tây";
                break;
            case "Quý":
                s = "Tây Bắc";
                break;
            default:
                break;
        }

        return s;
    }
    public static String GetHacThan(int mDay,int mMonth,int mYear)
    {
        String s = "";
        String sday = LunarCoreHelper.getCanDayLunar(mDay,mMonth,mYear) + " " + LunarCoreHelper.getChiDayLunar(mDay,mMonth,mYear) ;
        switch (sday)
        {
            case "Kỷ Dậu":
            case "Canh Tuất":
            case "Tân Hợi":
            case "Nhâm Tý":
            case "Quý Sửu":
            case "Giáp Dần":
                s = "Đông Bắc";
                break;
            case "Ất Mão":
            case "Bính Thìn":
            case "Đinh Tỵ":
            case "Mậu Ngọ":
            case "Kỷ Mùi":
                s = "Đông";
                break;
            case "Canh Thân":
            case "Tân Dậu":
            case "Nhâm Tuất":
            case "Quý Hợi":
            case "Giáp Tý":
            case "Ất Sửu":
                s = "Đông Nam";
                break;
            case "Bính Dần":
            case "Đinh Mão":
            case "Mậu Thìn":
            case "Kỷ Tỵ":
            case "Canh Ngọ":
                s = "Nam";
                break;
            case "Tân Mùi":
            case "Nhâm Thân":
            case "Quý Dậu":
            case "Giáp Tuất":
            case "Ất Hợi":
            case "Bính Tý":
                s = "Tây Nam";
                break;
            case "Đinh Sửu":
            case "Mậu Dần":
            case "Kỷ Mão":
            case "Canh Thìn":
            case "Tân Tỵ":
                s = "Tây";
                break;
            case "Nhâm Ngọ":
            case "Quý Mùi":
            case "Giáp Thân":
            case "Ất Dậu":
            case "Bính Tuất":
            case "Đinh Hợi":
                s = "Tây Bắc";
                break;
            case "Mậu Tý":
            case "Kỷ Sửu":
            case "Canh Dần":
            case "Tân Mão":
            case "Nhâm Thìn":
                s = "Bắc";
                break;
            default:
                s = "Vô";
                break;
        }

        return s;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static int GetStar28(LocalDateTime selectedDate)
    {
        LocalDateTime date = LocalDateTime.parse("1997-01-01T00:00:00.000");
        long iStar = 13;
        ChinaCalendar chinaCalendar = new ChinaCalendar(selectedDate.getDayOfMonth(),selectedDate.getMonth().getValue(),selectedDate.getYear(),7);
        ChinaCalendar chinaCalendar1970 = new ChinaCalendar(1,1,1970,7);
        long numDays = jdFromDate(selectedDate.getDayOfMonth(),selectedDate.getMonth().getValue(),selectedDate.getYear()) - jdFromDate(1,1,1997);
//                chinaCalendar.convertToJuliusDay() - chinaCalendar1970.convertToJuliusDay();
        Log.i("numDays",String.valueOf(numDays));
        if (selectedDate.isBefore(date))
            iStar = (iStar - numDays % 28 + 28) % 28;
        else
        {
            if (selectedDate.isAfter(date))
                iStar = (iStar + numDays % 28) % 28;
        }
        return (int) iStar;
    }
    public static void appendColoredText(TextView tv, String text, int color) {
        int start = tv.getText().length();
        tv.append(text);
        int end = tv.getText().length();
        Spannable spannableText = (Spannable) tv.getText();
        spannableText.setSpan(new ForegroundColorSpan(color), start, end, 0);
        tv.append("\n");
    }

    public void displayOutput(TextView output,Context context, int star)
    {
        output.setText("");
        output.setMaxLines(20000);
        //File sdcard = Environment.getExternalStorageDirectory();
        try{
            InputStream inputStream = context.getAssets().open("NhiThapBatTu/"+ star + ".txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = br.readLine()) != null) {
                if(line.length() != 0)
                {
                    if (line.substring(0,1).equals("~")) {
                        appendColoredText(output, line, Color.BLUE);}
//                    } else if (line.substring(0,1).equals("-")) {
//                        appendColoredText(output, "Nên làm:", Color.RED);
//                    } else if (line.substring(0,1).equals("-")) {
//                        appendColoredText(output, "Kỵ làm:", Color.RED);
//                    }else if (line.substring(0,1).equals("-")) {
//                        appendColoredText(output, "Ngoại lệ:", Color.RED);}
                    else if (line.substring(0,1).equals("*")) {
                        appendColoredText(output, line, Color.RED);}
                    else if (line.substring(0,1).equals(".")) {
                        appendColoredText(output, line, Color.RED);}
                    else {
                        appendColoredText(output, line, Color.BLACK);
                    }
                }
            }
        }
        catch (IOException e) {
            Toast.makeText(context,"Error reading file!" + e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
    private static int jdFromDate(int dd, int mm, int yy) {
        int a = (14 - mm) / 12;
        int y = yy + 4800 - a;
        int m = mm + 12 * a - 3;
        int jd = dd + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400
                - 32045;
        if (jd < 2299161) {
            jd = dd + (153 * m + 2) / 5 + 365 * y + y / 4 - 32083;
        }
        // jd = jd - 1721425;
        return jd;
    }
}
