package com.example.mycalendar;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.appota.lunarcore.LunarCoreHelper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/* Source: https://breakdownblogs.wordpress.com/2015/07/09/convert-solar-to-lunar-calendar-android/ */
/**
 * Created by dat on 09/07/2015.
 */
public class ChinaCalendar {
    final float PI = 3.14f;
    private int mDay;
    private int mMonth;
    private int mYear;
    private int mTimeZone = 8;
    private long mJulius;

    public int mLunarDay;
    public int mLunarYear;
    public int mLunarMonth;

    String[] can = new String[] {"Giáp", "Ất","Bính","Đinh","Mậu","Kỉ","Canh","Tân","Nhâm","Quí"};

    public ChinaCalendar(){
    }

    public ChinaCalendar(int day,int month,int year,int timeZone){
        mDay = day;
        mMonth = month;
        mYear = year;
        mTimeZone = timeZone;
        Date date = new Date();
    }

    public long convertToJuliusDay(){
        float a = (14 - mMonth) / 12;
        float y = mYear+4800-a;
        float m = mMonth+12*a-3;
        long jd = (long)(mDay + (153*m+2)/5 + 365*y + y/4 - y/100 + y/400 - 32045);
        return jd;
    }

    public int[] convertSolar2Lunar(int dd, int mm, int yy, int timeZone)
    {
        long dayNumber = jdFromDate(dd, mm, yy);
        long k = INT((dayNumber - 2415021.076998695) / 29.530588853);
        long monthStart = getNewMoonDay(k + 1, timeZone);
        if (monthStart > dayNumber)
        {
            monthStart = getNewMoonDay(k, timeZone);
        }
        long a11 = getLunarMonth11(yy, timeZone);
        long b11 = a11;
        int lunarYear;
        if (a11 >= monthStart)
        {
            lunarYear = yy;
            a11 = getLunarMonth11(yy - 1, timeZone);
        }
        else
        {
            lunarYear = yy + 1;
            b11 = getLunarMonth11(yy + 1, timeZone);
        }
        long lunarDay = dayNumber - monthStart + 1;
        long diff = INT((monthStart - a11) / 29);
        int lunarLeap = 0;
        long lunarMonth = diff + 11;
        if (b11 - a11 > 365)
        {
            int leapMonthDiff = getLeapMonthOffset(a11, timeZone);
            if (diff >= leapMonthDiff)
            {
                lunarMonth = diff + 10;
                if (diff == leapMonthDiff)
                {
                    lunarLeap = 1;
                }
            }
        }
        if (lunarMonth > 12)
        {
            lunarMonth = lunarMonth - 12;
        }
        if (lunarMonth >= 11 && diff < 4)
        {
            lunarYear -= 1;
        }
        return new int[] { (int)lunarDay, (int)lunarMonth, (int)lunarYear, lunarLeap };
    }

    private long INT(double d)
    {
        return (long)Math.floor(d);
    }

    private long jdFromDate(int dd, int mm, int yy) {
        long a = INT((14 - mm) / 12);
        long y = yy + 4800 - a;
        long m = mm + 12 * a - 3;
        long jd = dd + INT((153 * m + 2) / 5) + 365 * y + INT(y / 4) - INT(y / 100) + INT(y / 400) - 32045;
        if (jd < 2299161)
        {
            jd = dd + INT((153 * m + 2) / 5) + 365 * y + INT(y / 4) - 32083;
        }
        return jd;
    }

    private long getNewMoonDay(long k, long timeZone)
    {
        double T = k / 1236.85; // Time in Julian centuries from 1900 January 0.5
        double T2 = T * T;
        double T3 = T2 * T;
        double dr = Math.PI / 180;
        double Jd1 = 2415020.75933 + 29.53058868 * k + 0.0001178 * T2 - 0.000000155 * T3;
        Jd1 = Jd1 + 0.00033 * Math.sin((166.56 + 132.87 * T - 0.009173 * T2) * dr); // Mean new moon
        double M = 359.2242 + 29.10535608 * k - 0.0000333 * T2 - 0.00000347 * T3; // Sun's mean anomaly
        double Mpr = 306.0253 + 385.81691806 * k + 0.0107306 * T2 + 0.00001236 * T3; // Moon's mean anomaly
        double F = 21.2964 + 390.67050646 * k - 0.0016528 * T2 - 0.00000239 * T3; // Moon's argument of latitude
        double C1 = (0.1734 - 0.000393 * T) * Math.sin(M * dr) + 0.0021 * Math.sin(2 * dr * M);
        C1 = C1 - 0.4068 * Math.sin(Mpr * dr) + 0.0161 * Math.sin(dr * 2 * Mpr);
        C1 = C1 - 0.0004 * Math.sin(dr * 3 * Mpr);
        C1 = C1 + 0.0104 * Math.sin(dr * 2 * F) - 0.0051 * Math.sin(dr * (M + Mpr));
        C1 = C1 - 0.0074 * Math.sin(dr * (M - Mpr)) + 0.0004 * Math.sin(dr * (2 * F + M));
        C1 = C1 - 0.0004 * Math.sin(dr * (2 * F - M)) - 0.0006 * Math.sin(dr * (2 * F + Mpr));
        C1 = C1 + 0.0010 * Math.sin(dr * (2 * F - Mpr)) + 0.0005 * Math.sin(dr * (2 * Mpr + M));
        double deltat = 0;
        if (T < -11)
        {
            deltat = 0.001 + 0.000839 * T + 0.0002261 * T2 - 0.00000845 * T3 - 0.000000081 * T * T3;
        }
        else
        {
            deltat = -0.000278 + 0.000265 * T + 0.000262 * T2;
        };
        double JdNew = Jd1 + C1 - deltat;
        return INT(JdNew + 0.5 + (double)((double)timeZone / 24));
    }

    private long getSunLongitude(long jdn, int timeZone)
    {
        double T = (jdn - 2451545.5 - timeZone / 24) / 36525; // Time in Julian centuries from 2000-01-01 12:00:00 GMT
        double T2 = T * T;
        double dr = Math.PI / 180; // degree to radian
        double M = 357.52910 + 35999.05030 * T - 0.0001559 * T2 - 0.00000048 * T * T2; // mean anomaly, degree
        double L0 = 280.46645 + 36000.76983 * T + 0.0003032 * T2; // mean longitude, degree
        double DL = (1.914600 - 0.004817 * T - 0.000014 * T2) * Math.sin(dr * M);
        DL = DL + (0.019993 - 0.000101 * T) * Math.sin(dr * 2 * M) + 0.000290 * Math.sin(dr * 3 * M);
        double L = L0 + DL; // true longitude, degree
        // obtain apparent longitude by correcting for nutation and aberration
        double omega = 125.04 - 1934.136 * T;
        L = L - 0.00569 - 0.00478 * Math.sin(omega * dr);
        L = L * dr;
        L = L - Math.PI * 2 * (INT(L / (Math.PI * 2))); // Normalize to (0, 2*PI)
        return INT(L / Math.PI * 6);
    }

    private long getLunarMonth11(int yy, int timeZone)
    {
        long off = jdFromDate(31, 12, yy) - 2415021;
        long k = INT(off / 29.530588853);
        long nm = getNewMoonDay(k, timeZone);
        long sunLong = getSunLongitude(nm, timeZone); // sun longitude at local midnight
        if (sunLong >= 9)
        {
            nm = getNewMoonDay(k - 1, timeZone);
        }
        return nm;
    }

    private int getLeapMonthOffset(long a11, int timeZone)
    {
        long k = INT((a11 - 2415021.076998695) / 29.530588853 + 0.5);
        long last = 0;
        int i = 1; // We start with the month following lunar month 11
        long arc = getSunLongitude(getNewMoonDay(k + i, timeZone), timeZone);
        do
        {
            last = arc;
            i = i + 1;
            arc = getSunLongitude(getNewMoonDay(k + i, timeZone), timeZone);
        } while (arc != last && i < 14);
        return i - 1;
    }

    public Date ConVertToLunar() {
//        int k, dayNumber, monthStart, a11, b11, lunarDay, lunarMonth, lunarYear, lunarLeap;
//        dayNumber = jdFromDate(mDay, mMonth, mYear);
//        k = (int)((dayNumber - 2415021.076998695) / 29.530588853);
//        monthStart = getNewMoonDay(k + 1, mTimeZone);
//        if (monthStart > dayNumber) {
//            monthStart = getNewMoonDay(k, mTimeZone);
//        }
//        a11 = getLunarMonth11(mYear, mTimeZone);
//        b11 = a11;
//        if (a11 >= monthStart) {
//            lunarYear = mYear;
//            a11 = getLunarMonth11(mYear - 1, mTimeZone);
//        } else {
//            lunarYear = mYear + 1;
//            b11 = getLunarMonth11(mYear + 1, mTimeZone);
//        }
//        lunarDay = dayNumber - monthStart + 1;
//        int diff = (int)((monthStart - a11) / 29);
//        lunarLeap = 0;
//        lunarMonth = diff + 11;
//        if (b11 - a11 > 365) {
//            int leapMonthDiff = getLeapMonthOffset(a11, mTimeZone);
//            if (diff >= leapMonthDiff) {
//                lunarMonth = diff + 10;
//                if (diff == leapMonthDiff) {
//                    lunarLeap = 1;
//                }
//            }
//        }
//        if (lunarMonth > 12) {
//            lunarMonth = lunarMonth - 12;
//        }
//        if (lunarMonth >= 11 && diff < 4) {
//            lunarYear -= 1;
//        }
        int[] lunarDay = LunarCoreHelper.convertSolar2Lunar(mDay, mMonth, mYear, 7);
        Log.d("Lunar date", "Lunar date:\n" +
                "Day: " + lunarDay[0] + "\n" +
                "Month: " + lunarDay[1] + "\n" +
                "Year: " + lunarDay[2] + "\n" +
                "Leap Month: " + (lunarDay[3]==1 ? "Yes" : "No"));

        //Update day
        int lunar_day = lunarDay[0];
        int lunar_month = lunarDay[1];
        int lunar_year = lunarDay[2];

        mLunarDay = lunar_day;
        mLunarMonth= lunar_month;
        mLunarYear = lunar_year;
        Date date = new Date(lunar_year - 1900,lunar_month-1,lunar_day);
        return date;
    }

    public String getLunarDate(){
        return LunarCoreHelper.getCanDayLunar(mDay, mMonth, mYear) + " "
                + LunarCoreHelper.getChiDayLunar(mDay, mMonth, mYear);
    }

    public String getLunarMonth(){
        String[] chi = new String[] {"Dần","Mão","Thìn","Tị","Ngọ","Mùi","Thân","Dậu","Tuất","Hợi","Tý","Sửu"};
        int mod = (mLunarYear*12+ mLunarMonth+3)%10;

        return can[mod]+" "+chi[mLunarMonth-1];
    }

    public String getLunarYear(){
        String[] chi = new String[] {"Tý","Sửu","Dần","Mão","Thìn","Tị","Ngọ","Mùi","Thân","Dậu","Tuất","Hợi"};

        return can[(mYear+6)%10] +" "+chi[(mYear+8)%12];
    }

    /*----------------------------------------------------------------------------*/
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String Solar2Lunar(int day, int month, int year) {
        int[] lunarDay = convertSolar2Lunar(day,month,year, 7);
        Log.d("Lunar date", "Lunar date:\n" +
                "Day: " + lunarDay[0] + "\n" +
                "Month: " + lunarDay[1] + "\n" +
                "Year: " + lunarDay[2] + "\n" +
                "Leap Month: " + (lunarDay[3]==1 ? "Yes" : "No"));

        //Update day
        int lunar_day = lunarDay[0];
        int lunar_month = lunarDay[1];
        int lunar_year = lunarDay[2];

        String lunarDate;
        if (lunar_day<10)
            lunarDate="0"+String.valueOf(lunar_day);
        else
            lunarDate=String.valueOf(lunar_day);
        lunarDate+="/";
        if (lunar_month<10)
            lunarDate+="0"+String.valueOf(lunar_month);
        else
            lunarDate+=String.valueOf(lunar_month);
        lunarDate += "/" +String.valueOf(lunar_year);
        Log.i("lunarDate",lunarDate);
        return lunarDate;
    }

    public String Time2TimeLunar(String strHour) {
        int[][] matrix = {{0, 2, 4, 6, 8},
                {1, 3, 5, 7, 9},
                {2, 4, 6, 8, 0},
                {3, 5, 7, 9, 1},
                {4, 6, 8, 0, 2},
                {5, 7, 9, 1, 3},
                {6, 8, 0, 2, 4},
                {7, 9, 1, 3, 5},
                {8, 0, 2, 4, 6},
                {9, 1, 3, 5, 7},
                {0, 2, 4, 6, 8},
                {1, 3, 5, 7, 9}};
        long juliusDay = convertToJuliusDay();
        String[] chi = new String[]{"Tý", "Sửu", "Dần", "Mão", "Thìn", "Tị", "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi"};
        int iHour = Integer.parseInt(strHour);
        return can[matrix[((iHour + 1) / 2) % 12][(int) (((juliusDay + 9) % 10) % 5)]] + " " + chi[((iHour + 1) / 2) % 12];
    }

    public boolean IsGoodTime(String strHour) {
        String dayChi = getLunarDate().split(" ")[1];
        String timeChi = Time2TimeLunar(strHour).split(" ")[1];

        if (dayChi.compareToIgnoreCase("dần") == 0 || dayChi.compareToIgnoreCase("thân") == 0)
            if (timeChi.compareToIgnoreCase("tý") == 0 || timeChi.compareToIgnoreCase("sửu") == 0 ||
                    timeChi.compareToIgnoreCase("thìn") == 0 || timeChi.compareToIgnoreCase("Tị") == 0 ||
                    timeChi.compareToIgnoreCase("mùi") == 0 || timeChi.compareToIgnoreCase("tuất") == 0)
                return true;
        if (dayChi.compareToIgnoreCase("mão") == 0 || dayChi.compareToIgnoreCase("dậu") == 0)
            if (timeChi.compareToIgnoreCase("tý") == 0 || timeChi.compareToIgnoreCase("dần") == 0 ||
                    timeChi.compareToIgnoreCase("mão") == 0 || timeChi.compareToIgnoreCase("mùi") == 0 ||
                    timeChi.compareToIgnoreCase("ngọ") == 0 || timeChi.compareToIgnoreCase("dậu") == 0)
                return true;
        if (dayChi.compareToIgnoreCase("thìn") == 0 || dayChi.compareToIgnoreCase("tuất") == 0)
            if (timeChi.compareToIgnoreCase("dần") == 0 || timeChi.compareToIgnoreCase("Tị") == 0 ||
                    timeChi.compareToIgnoreCase("thìn") == 0 || timeChi.compareToIgnoreCase("thân") == 0 ||
                    timeChi.compareToIgnoreCase("hợi") == 0 || timeChi.compareToIgnoreCase("dậu") == 0)
                return true;
        if (dayChi.compareToIgnoreCase("Tị") == 0 || dayChi.compareToIgnoreCase("hợi") == 0)
            if (timeChi.compareToIgnoreCase("ngọ") == 0 || timeChi.compareToIgnoreCase("sửu") == 0 ||
                    timeChi.compareToIgnoreCase("thìn") == 0 || timeChi.compareToIgnoreCase("mùi") == 0 ||
                    timeChi.compareToIgnoreCase("tuất") == 0 || timeChi.compareToIgnoreCase("hợi") == 0)
                return true;
        if (dayChi.compareToIgnoreCase("tý") == 0 || dayChi.compareToIgnoreCase("ngọ") == 0)
            if (timeChi.compareToIgnoreCase("tý") == 0 || timeChi.compareToIgnoreCase("sửu") == 0 ||
                    timeChi.compareToIgnoreCase("mão") == 0 || timeChi.compareToIgnoreCase("ngọ") == 0 ||
                    timeChi.compareToIgnoreCase("thân") == 0 || timeChi.compareToIgnoreCase("dậu") == 0)
                return true;
        if (dayChi.compareToIgnoreCase("sửu") == 0 || dayChi.compareToIgnoreCase("mùi") == 0)
            if (timeChi.compareToIgnoreCase("dần") == 0 || timeChi.compareToIgnoreCase("Tị") == 0 ||
                    timeChi.compareToIgnoreCase("mão") == 0 || timeChi.compareToIgnoreCase("tuất") == 0 ||
                    timeChi.compareToIgnoreCase("thân") == 0 || timeChi.compareToIgnoreCase("hợi") == 0)
                return true;
        return false;
    }

    public String IsZodiacDay(int lunnarmonth) {
        // Trả về 1 là ngày hoàng đạo
        // Trả về 0 là ngày bình thường
        // Trả về -1 là ngày hắc đạo
//        String dayChi = getLunarDate().split(" ")[1];
//        if (lunnarmonth == 1 || lunnarmonth == 7) {
//            if (dayChi.equals("Tý") || dayChi.equals("Sửu") || dayChi.equals("Tị") || dayChi.equals("Mùi"))
//                return 1;
//            if (dayChi.equals("Ngọ") || dayChi.equals("Mão") || dayChi.equals("Hợi") || dayChi.equals("Dậu"))
//                return -1;
//            return 0;
//        } else if (lunnarmonth == 2 || lunnarmonth == 8) {
//            if (dayChi.equals("Dần") || dayChi.equals("Mão") || dayChi.equals("Mùi") || dayChi.equals("Dậu"))
//                return 1;
//            if (dayChi.equals("Thân") || dayChi.equals("Tị") || dayChi.equals("Sửu") || dayChi.equals("Hợi"))
//                return -1;
//            return 0;
//        } else if (lunnarmonth == 3 || lunnarmonth == 9) {
//            if (dayChi.equals("Thìn") || dayChi.equals("Tị") || dayChi.equals("Dậu") || dayChi.equals("Hợi"))
//                return 1;
//            if (dayChi.equals("Tuất") || dayChi.equals("Mão") || dayChi.equals("Mùi") || dayChi.equals("Sửu"))
//                return -1;
//            return 0;
//        } else if (lunnarmonth == 4 || lunnarmonth == 10) {
//            if (dayChi.equals("Ngọ") || dayChi.equals("Mùi") || dayChi.equals("Sửu") || dayChi.equals("Hợi"))
//                return 1;
//            if (dayChi.equals("Tý") || dayChi.equals("Mão") || dayChi.equals("Tị") || dayChi.equals("Dậu"))
//                return -1;
//            return 0;
//        } else if (lunnarmonth == 5 || lunnarmonth == 11) {
//            if (dayChi.equals("Thân") || dayChi.equals("Sửu") || dayChi.equals("Dậu") || dayChi.equals("Mão"))
//                return 1;
//            if (dayChi.equals("Dần") || dayChi.equals("Hợi") || dayChi.equals("Mùi") || dayChi.equals("Tị"))
//                return -1;
//            return 0;
//        } else {
//            if (dayChi.equals("Tuất") || dayChi.equals("Hợi") || dayChi.equals("Tị") || dayChi.equals("Mão"))
//                return 1;
//            if (dayChi.equals("Thìn") || dayChi.equals("Sửu") || dayChi.equals("Mùi") || dayChi.equals("Dậu"))
//                return -1;
//            return 0;
//        }
        String chiDay = LunarCoreHelper.getChiDayLunar(mDay, mMonth, mYear);
        Log.d("Good/Bad Day", "Good/Bad Day: " + LunarCoreHelper.rateDay(chiDay, lunnarmonth));
        return LunarCoreHelper.rateDay(chiDay, lunnarmonth);
    }


}