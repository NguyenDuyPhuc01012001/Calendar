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

        if (dayChi == "D???n" || dayChi =="Th??n")
            saveTmp = "D???n (3h - 5h), M??o (5h - 7h), Ng??? (11h - 13h), Th??n (15h -17h), D???u (17h - 19h), H???i (21h - 23h)";
        if (dayChi == "M??o"|| dayChi == "D???u")
            saveTmp = "S???u (1h - 3h), Th??n (7h - 9h), T??? (9h - 11h), Th??n (15h - 17h), Tu???t (19h - 21h), H???i (21h - 23h)";
        if (dayChi == "Th??n" || dayChi == "Tu???t")
            saveTmp = "T?? (23h - 1h), S???u (1h - 3h), M??o (5h - 7h), Ng??? (11h - 13h), M??i (13h - 15h), Tu???t (19h - 21h)";
        if (dayChi == "T???" || dayChi == "H???i")
            saveTmp = "T?? (23h - 1h), D???n (3h - 5h), M??o (5h - 7h), T??? (9h - 11h), Th??n (15h - 17h), D???u (17h - 19h)";
        if (dayChi == "T??" || dayChi == "Ng???")
            saveTmp = "D???n (3h - 5h), Th??n (7h - 9h), T??? (9h - 11h), M??i (13h - 15h), Tu???t (19h - 21h), H???i (21h - 23h)";
        if (dayChi == "S???u" || dayChi == "M??i")
            saveTmp = "T?? (23h - 1h), S???u (1h - 3h), Th??n (7h - 9h), Ng??? (11h - 13h), M??i (13h - 25h), D???u (17h -19h)";
        return saveTmp;
    }
    public static String GetZodiacTime(int mDay, int mMonth, int mYear)
    {
        String dayChi = LunarCoreHelper.getChiDayLunar(mDay,mMonth,mYear), saveTmp = "";

        if (dayChi == "D???n" || dayChi =="Th??n")
            saveTmp = "T?? (23h -1h),S???u (1h - 3h),Th??n (7h - 9h),T??? (9h - 11h),M??i (13h - 15h),Tu???t (19h - 21h)";
        if (dayChi == "M??o"|| dayChi == "D???u")
            saveTmp = "T?? (23h - 1h),D???n (3h - 5h),M??o (5h - 7h),Ng??? (11h - 13h),M??i (13h - 15h),D???u (17h - 19h)";
        if (dayChi == "Th??n" || dayChi == "Tu???t")
            saveTmp = "D???n (3h - 5h),Th??n (7h - 9h),T??? (9h - 11h),Th??n (15h - 17h),D???u (17h - 19h),H???i (21h - 23h)";
        if (dayChi == "T???" || dayChi == "H???i")
            saveTmp = "S???u (1h - 3h),Th??n (7h - 9h),Ng??? (11h - 13h),M??i (13h - 15h),Tu???t (19h - 21h),H???i (21h - 23h)";
        if (dayChi == "T??" || dayChi == "Ng???")
            saveTmp = "T?? (23h - 1h),S???u (1h - 3h),M??o (5h - 7h),Ng??? (11h - 13h),Th??n (15h - 17h),D???u (17h - 19h)";
        if (dayChi == "S???u" || dayChi == "M??i")
            saveTmp = "D???n (3h - 5h),M??o (5h - 7h),T??? (9h - 11h),Th??n (15h - 17h),Tu???t (19h - 21h),H???i (21h - 23h)";
        return saveTmp;
    }
    public static String GetHyThan(int mDay,int mMonth,int mYear)
    {
        String s = "";
        String sCan = LunarCoreHelper.getCanDayLunar(mDay,mMonth,mYear);
        switch (sCan)
        {
            case "Gi??p":
                s = "????ng B???c";
                break;
            case "???t":
                s = "T??y B???c";
                break;
            case "B??nh":
                s = "T??y Nam";
                break;
            case "??inh":
                s = "ch??nh Nam";
                break;
            case "M???u":
                s = "????ng Nam";
                break;
            case "K???":
                s = "????ng B???c";
                break;
            case "Canh":
                s = "T??y B???c";
                break;
            case "T??n":
                s = "T??y Nam";
                break;
            case "Nh??m":
                s = "ch??nh Nam";
                break;
            case "Qu??":
                s = "????ng Nam";
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
            case "Gi??p":
                s = "????ng Nam";
                break;
            case "???t":
                s = "????ng Nam";
                break;
            case "B??nh":
                s = "????ng";
                break;
            case "??inh":
                s = "????ng";
                break;
            case "M???u":
                s = "B???c";
                break;
            case "K???":
                s = "Nam";
                break;
            case "Canh":
                s = "T??y Nam";
                break;
            case "T??n":
                s = "T??y Nam";
                break;
            case "Nh??m":
                s = "T??y";
                break;
            case "Qu??":
                s = "T??y B???c";
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
            case "K??? D???u":
            case "Canh Tu???t":
            case "T??n H???i":
            case "Nh??m T??":
            case "Qu?? S???u":
            case "Gi??p D???n":
                s = "????ng B???c";
                break;
            case "???t M??o":
            case "B??nh Th??n":
            case "??inh T???":
            case "M???u Ng???":
            case "K??? M??i":
                s = "????ng";
                break;
            case "Canh Th??n":
            case "T??n D???u":
            case "Nh??m Tu???t":
            case "Qu?? H???i":
            case "Gi??p T??":
            case "???t S???u":
                s = "????ng Nam";
                break;
            case "B??nh D???n":
            case "??inh M??o":
            case "M???u Th??n":
            case "K??? T???":
            case "Canh Ng???":
                s = "Nam";
                break;
            case "T??n M??i":
            case "Nh??m Th??n":
            case "Qu?? D???u":
            case "Gi??p Tu???t":
            case "???t H???i":
            case "B??nh T??":
                s = "T??y Nam";
                break;
            case "??inh S???u":
            case "M???u D???n":
            case "K??? M??o":
            case "Canh Th??n":
            case "T??n T???":
                s = "T??y";
                break;
            case "Nh??m Ng???":
            case "Qu?? M??i":
            case "Gi??p Th??n":
            case "???t D???u":
            case "B??nh Tu???t":
            case "??inh H???i":
                s = "T??y B???c";
                break;
            case "M???u T??":
            case "K??? S???u":
            case "Canh D???n":
            case "T??n M??o":
            case "Nh??m Th??n":
                s = "B???c";
                break;
            default:
                s = "V??";
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
//                        appendColoredText(output, "N??n l??m:", Color.RED);
//                    } else if (line.substring(0,1).equals("-")) {
//                        appendColoredText(output, "K??? l??m:", Color.RED);
//                    }else if (line.substring(0,1).equals("-")) {
//                        appendColoredText(output, "Ngo???i l???:", Color.RED);}
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
