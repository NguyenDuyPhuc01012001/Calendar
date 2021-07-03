package com.example.mycalendar.handler;

import android.util.Log;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

public class GetAstrologyHandler {
    private String TAG = "LifeTimeAstrologyHandler";
    private final int zodiacCount = 12;
    public String GetFileName(int birthYear)
    {
        Log.d(TAG, "GetFileName: "+GetCanChi(birthYear)+GetZodiac(birthYear));
        return GetCanChi(birthYear)+"_"+GetZodiac(birthYear);
    }
    private String GetCanChi(int birthYear)
    {
        int lastNumber = birthYear % 10;
        String canchi = "";
        switch (lastNumber)
        {
            case 0:
                canchi = "canh";
                break;
            case 1:
                canchi = "tan";
                break;
            case 2:
                canchi = "nham";
                break;
            case 3:
                canchi = "quy";
                break;
            case 4:
                canchi = "giap";
                break;
            case 5:
                canchi = "at";
                break;
            case 6:
                canchi = "binh";
                break;
            case 7:
                canchi = "dinh";
                break;
            case 8:
                canchi = "mau";
                break;
            case 9:
                canchi = "ky";
                break;
        }
        return canchi;
    }
    private String GetZodiac(int birthYear)
    {
        int zodiacCode = birthYear % zodiacCount;
        Log.d(TAG, "GetZodiac: "+zodiacCode);
        String zodiacName = "";
        switch (zodiacCode)
        {
            case 0:
                zodiacName = "than";
                break;
            case 1:
                zodiacName = "dau";
                break;
            case 2:
                zodiacName = "tuat";
                break;
            case 3:
                zodiacName = "hoi";
                break;
            case 4:
                zodiacName = "ty";
                break;
            case 5:
                zodiacName = "suu";
                break;
            case 6:
                zodiacName = "dan";
                break;
            case 7:
                zodiacName = "mao";
                break;
            case 8:
                zodiacName = "thin";
                break;
            case 9:
                zodiacName = "ty";
                break;
            case 10:
                zodiacName = "ngo";
                break;
            case 11:
                zodiacName = "mui";
                break;
        }
        return zodiacName;
    }
}
