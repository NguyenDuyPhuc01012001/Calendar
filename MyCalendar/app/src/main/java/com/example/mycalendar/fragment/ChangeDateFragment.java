package com.example.mycalendar.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;

import com.appota.lunarcore.LunarCoreHelper;
import com.example.mycalendar.R;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChangeDateFragment extends Fragment {
    private static final String TAG="CHANGE_DATE_FRAGMENT";
    private int lunarLeapMonth;
    private int lunarYear=1,lunarMonth=1,lunarDay=1;

    DatePicker dpSolarDay,dpLunarDay;
    Button btnShowDayDetail;
    CheckBox ckbIsLeapMonth;

    public ChangeDateFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_change_date, container, false);
        Init(view);
        SetEvent();
        return view;
    }

    private void Init(View view) {
        dpSolarDay=view.findViewById(R.id.dpSolarDay);
        dpLunarDay=view.findViewById(R.id.dpLunarDay);
        btnShowDayDetail=view.findViewById(R.id.btnShowDayDetail);
        ckbIsLeapMonth=view.findViewById(R.id.ckbIsLeap);
        ckbIsLeapMonth.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void SetEvent() {
        dpSolarDay.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int[] lunarDay = LunarCoreHelper.convertSolar2Lunar(dayOfMonth, monthOfYear+1, year, 7);
                Log.d(TAG, "Lunar date:\n" +
                        "Day: " + lunarDay[0] + "\n" +
                        "Month: " + lunarDay[1] + "\n" +
                        "Year: " + lunarDay[2] + "\n" +
                        "Leap Month: " + (lunarDay[3]==1 ? "Yes" : "No"));

                //Update day
                int lunar_day = lunarDay[0];
                int lunar_month = lunarDay[1]-1;
                int lunar_year = lunarDay[2];
                dpLunarDay.updateDate(lunar_year, lunar_month,lunar_day);

                //Update leap month
                if(year%19==0||year%19==3||year%19==6||year%19==9||year%19== 11||year%19== 14||year%19== 17)
                    ckbIsLeapMonth.setVisibility(View.VISIBLE);
                else
                    ckbIsLeapMonth.setVisibility(View.GONE);
                if(lunarDay[3]==1)
                    ckbIsLeapMonth.setChecked(true);
                else
                    ckbIsLeapMonth.setChecked(false);
            }
        });

        dpLunarDay.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                lunarYear=year;
                lunarMonth=monthOfYear+1;
                lunarDay=dayOfMonth;
                Lunar2Solar(lunarYear, lunarMonth, lunarDay);
            }
        });

        ckbIsLeapMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Lunar2Solar(lunarYear, lunarMonth, lunarDay);
            }
        });

        btnShowDayDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year=dpSolarDay.getYear();
                int month=dpSolarDay.getMonth()+1;
                int day=dpSolarDay.getDayOfMonth();

                String date=String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
                LocalDate dateTime = LocalDate.parse(date, formatter);
                LocalDateTime localDateTime = dateTime.atStartOfDay();

                DayDetailFragment dayDetailFragment = new DayDetailFragment(localDateTime);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.changeDateFragment, dayDetailFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
//                btnShowDayDetail.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void Lunar2Solar(int year, int monthOfYear, int dayOfMonth){
        if(year%19==0||year%19==3||year%19==6||year%19==9||year%19== 11||year%19== 14||year%19== 17)
            ckbIsLeapMonth.setVisibility(View.VISIBLE);
        else
            ckbIsLeapMonth.setVisibility(View.GONE);
        if(ckbIsLeapMonth.isChecked())
            lunarLeapMonth=1;
        else
            lunarLeapMonth=0;

        int[] solarDay = LunarCoreHelper.convertLunar2Solar(dayOfMonth, monthOfYear, year, lunarLeapMonth, 7);
        if(solarDay[0]==0||solarDay[1]==0||solarDay[2]==0){
            ckbIsLeapMonth.setChecked(false);
            return;
        }

        Log.d(TAG, "Solar date:\n" +
                "Day: " + solarDay[0] + "\n" +
                "Month: " + solarDay[1] + "\n" +
                "Year: " + solarDay[2] + "\n");
        int solar_day = solarDay[0];
        int solar_month = solarDay[1]-1;
        int solar_year = solarDay[2];
        dpSolarDay.updateDate(solar_year, solar_month, solar_day);
    }
}