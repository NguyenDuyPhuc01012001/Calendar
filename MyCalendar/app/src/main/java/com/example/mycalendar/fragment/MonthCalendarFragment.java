package com.example.mycalendar.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;

import com.example.mycalendar.AddEvent;
import com.example.mycalendar.ChinaCalendar;
import com.example.mycalendar.EventDialog;
import com.example.mycalendar.R;
import com.example.mycalendar.adapter.CalendarAdapter;
import com.example.mycalendar.adapter.EventAdapter;
import com.example.mycalendar.database.EventDatabase;
import com.example.mycalendar.model.EventInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class MonthCalendarFragment extends Fragment implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private RecyclerView eventRecyclerView;
    private LocalDate selectedDate;
    private Button nextMonth;
    private Button previousMonth;
    private FloatingActionButton addEvent;
    public static ArrayList<EventInfo> listEvent = new ArrayList<EventInfo>();
    private TextView eventIn;
    public static int Check = 0;

    public MonthCalendarFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_month_calendar, container, false);
        selectedDate = LocalDate.now();
        initWidgets(v);
        setMonthView();
        setEventView(selectedDate.getDayOfMonth());
        setOnCLick();
        return v;
    }

    private void setOnCLick() {
        nextMonth.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                nextMonthAction(v);
            }
        });

        previousMonth.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                previousMonthAction(v);
            }
        });
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventDialog eventDialog = new EventDialog();
                eventDialog.show(getActivity().getSupportFragmentManager(), "Thêm sự kiện");
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setEventView(int day)
    {
        EventDatabase eventDatabase = new EventDatabase(getActivity());
        listEvent = (ArrayList<EventInfo>) eventDatabase.getEventday(day,selectedDate.getMonthValue(),selectedDate.getYear());
        EventAdapter eventAdapter = new EventAdapter(listEvent);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        eventRecyclerView.setLayoutManager(layoutManager);
        eventRecyclerView.setAdapter(eventAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initWidgets(View v) {
        calendarRecyclerView = v.findViewById(R.id.calendarRecyclerView);
        monthYearText = v.findViewById(R.id.monthYearTV);
        eventRecyclerView = v.findViewById(R.id.eventRecyclerView);
        nextMonth=v.findViewById(R.id.nextMonth);
        previousMonth=v.findViewById(R.id.previousMonth);
        eventIn = v.findViewById(R.id.eventIn);
        addEvent = v.findViewById(R.id.addEventBtn);
        eventIn.setText("Sự kiện trong ngày " + selectedDate.getDayOfMonth() + " " + monthYearFromDate(selectedDate));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        ArrayList<String> daysLunar = daysLunarArray(selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, daysLunar, selectedDate, eventIn, this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysLunarArray(LocalDate date) {
        ArrayList<String> daysLunar = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        LocalDate monthPrevious=date.minusMonths(1);
        LocalDate monthNext=date.plusMonths(1);
        int daysInPreviousMonth = YearMonth.from(monthPrevious).lengthOfMonth();
        int day,month,year;
        for(int i = 2; i <= 43; i++)
        {
            if(i <= dayOfWeek )
            {
                day=daysInPreviousMonth+i - dayOfWeek;
                month=monthPrevious.getMonthValue();
                year=monthPrevious.getYear();
                ChinaCalendar chinaCalendar=new ChinaCalendar();
                String dateLunar = chinaCalendar.Solar2Lunar(day,month,year);
                daysLunar.add(dateLunar);
            }
            else if(i > daysInMonth + dayOfWeek)
            {
                day=i - daysInMonth- dayOfWeek;
                month=monthNext.getMonthValue();
                year=monthNext.getYear();
                ChinaCalendar chinaCalendar=new ChinaCalendar();
                String dateLunar = chinaCalendar.Solar2Lunar(day,month,year);
                daysLunar.add(dateLunar);
            }
            else {
                day=i - dayOfWeek;
                month=selectedDate.getMonthValue();
                year=selectedDate.getYear();
                ChinaCalendar chinaCalendar=new ChinaCalendar();
                String dateLunar = chinaCalendar.Solar2Lunar(day,month,year);
                daysLunar.add(dateLunar);
            }
        }
        return daysLunar;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        LocalDate monthPrevious=date.minusMonths(1);
        LocalDate monthNext=date.plusMonths(1);
        int daysInPreviousMonth = YearMonth.from(monthPrevious).lengthOfMonth();
        String day,month,year;
        for(int i = 2; i <= 43; i++)
        {
            if(i <= dayOfWeek )
            {
                day=String.valueOf(daysInPreviousMonth+i - dayOfWeek);
                month=String.valueOf(monthPrevious.getMonthValue());
                year=String.valueOf(monthPrevious.getYear());
                daysInMonthArray.add(day+"/"+month+"/"+year);
            }
            else if(i > daysInMonth + dayOfWeek)
            {
                day=String.valueOf(i - daysInMonth- dayOfWeek);
                month=String.valueOf(monthNext.getMonthValue());
                year=String.valueOf(monthNext.getYear());
                daysInMonthArray.add(day+"/"+month+"/"+year);
            }
            else {
                day=String.valueOf(i - dayOfWeek);
                month=String.valueOf(date.getMonthValue());
                year=String.valueOf(date.getYear());
                daysInMonthArray.add(day+"/"+month+"/"+year);
            }

        }
        return  daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("vn"));
        return date.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
        eventIn.setText("Sự kiện trong ngày " + 1 + " " + monthYearFromDate(selectedDate));
        setEventView(Integer.valueOf(1));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
        eventIn.setText("Sự kiện trong ngày " + 1 + " " + monthYearFromDate(selectedDate));
        setEventView(Integer.valueOf(1));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText) {
        eventIn.setText("Sự kiện trong ngày " + dayText + " " + monthYearFromDate(selectedDate));
        setEventView(Integer.valueOf(dayText));
    }
}