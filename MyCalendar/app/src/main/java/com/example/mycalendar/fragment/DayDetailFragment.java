package com.example.mycalendar.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycalendar.R;
import com.example.mycalendar.model.DateTimeInfo;
import com.example.mycalendar.presenter.DayDetailInterface;
import com.example.mycalendar.presenter.DayDetailPresenter;

import java.time.LocalDateTime;

public class DayDetailFragment extends Fragment implements DayDetailInterface {

    private LocalDateTime selectedDate;
    private TextView dateSelectTV,numberDayTV,numberMonthTV,numberYearTV,stringDayTV,stringMonthTV,stringYearTV;
    private TextView ngayDaoTV;
    private Button nextDay, previousDay;
    private ImageView ngayDaoImage;
    DayDetailPresenter ddPresenter;
    public DayDetailFragment() {
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
        View v = inflater.inflate(R.layout.fragment_day_detail, container, false);
        init(v);
        ddPresenter.getData(selectedDate);
        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDate = selectedDate.plusDays(1);
                ddPresenter.getData(selectedDate);
            }
        });
        previousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDate = selectedDate.minusDays(1);
                ddPresenter.getData(selectedDate);
            }
        });
        return v;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void init(View v)
    {
        selectedDate = LocalDateTime.now();
        dateSelectTV = v.findViewById(R.id.monthYearTV);
        numberDayTV = v.findViewById(R.id.dayNumberTV);
        numberMonthTV = v.findViewById(R.id.monthNumberTV);
        numberYearTV = v.findViewById(R.id.yearNumberTV);
        stringDayTV = v.findViewById(R.id.dayStringTV);
        stringMonthTV = v.findViewById(R.id.monthStringTV);
        stringYearTV = v.findViewById(R.id.yearStringTV);
        ngayDaoTV = v.findViewById(R.id.NgayDaoTV);
        ngayDaoImage = v.findViewById(R.id.NgayDaoImage);
        nextDay = v.findViewById(R.id.NextDay);
        previousDay = v.findViewById(R.id.previousDay);
        ddPresenter = new DayDetailPresenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void LoadData(DateTimeInfo dateTimeInfo) {
        dateSelectTV.setText(selectedDate.getDayOfMonth() + "-" + selectedDate.getMonth().getValue() + "-" + selectedDate.getYear());
        numberDayTV.setText(Integer.valueOf(dateTimeInfo.getDayOfMonthLunar()).toString());
        numberMonthTV.setText(String.valueOf(dateTimeInfo.getMonthLunar()));
        numberYearTV.setText(String.valueOf(dateTimeInfo.getYearLunar()));
        stringDayTV.setText(String.valueOf(dateTimeInfo.getStrDayOfMonthLunar()));
        stringMonthTV.setText(String.valueOf(dateTimeInfo.getStrMonthLunar()));
        stringYearTV.setText(String.valueOf(dateTimeInfo.getStrYearLunar()));

        if(dateTimeInfo.getIsGoodDay().equals("Good"))
        {
            ngayDaoImage.setVisibility(View.VISIBLE);
            ngayDaoImage.setImageResource(R.mipmap.yin_yang_red);
            ngayDaoTV.setText("Hoàng đạo");
            ngayDaoTV.setVisibility(View.VISIBLE);
            ngayDaoTV.setTextColor(Color.RED);
        }
        else if(dateTimeInfo.getIsGoodDay().equals("Normal"))
        {
            ngayDaoImage.setVisibility(View.GONE);
            ngayDaoTV.setVisibility(View.GONE);
        }

        else if(dateTimeInfo.getIsGoodDay().equals("Bad"))
        {
            ngayDaoImage.setVisibility(View.VISIBLE);
            ngayDaoImage.setImageResource(R.mipmap.yin_yang_black);
            ngayDaoTV.setVisibility(View.VISIBLE);
            ngayDaoTV.setText("Hắc đạo");
            ngayDaoTV.setTextColor(Color.BLACK);
        }
    }
}