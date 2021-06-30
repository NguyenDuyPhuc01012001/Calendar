package com.example.mycalendar.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mycalendar.AddEvent;
import com.example.mycalendar.BottomDialog;
import com.example.mycalendar.ChinaCalendar;
import com.example.mycalendar.EventDialog;
import com.example.mycalendar.OnSwipeTouchListener;
import com.example.mycalendar.R;
import com.example.mycalendar.adapter.CalendarAdapter;
import com.example.mycalendar.adapter.EventAdapter;
import com.example.mycalendar.database.EventDatabase;
import com.example.mycalendar.database.EventDatabaseOpenHelper;
import com.example.mycalendar.model.EventInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MonthCalendarFragment extends Fragment implements CalendarAdapter.OnItemListener, EventAdapter.OnItemListener,BottomDialog.OnSelected {
    private TextView tvMonthYearText;
    private TextView tvEventIn;
    private RecyclerView calendarRecyclerView;
    private RecyclerView eventRecyclerView;
    private FloatingActionButton addEvent;
    private ImageButton btnToday;

    private LocalDate selectedDate;
    public static ArrayList<EventInfo> listEvent = new ArrayList<EventInfo>();
    private ArrayList<String> daysInMonth=null;
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
        setEvent();
        loadData();
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadData() {
        setMonthView();
        setEventView(selectedDate.getDayOfMonth());
        tvEventIn.setText("Sự kiện trong ngày " + selectedDate.getDayOfMonth() + " tháng "+selectedDate.getMonthValue()+" "+selectedDate.getYear());
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setEvent() {
        calendarRecyclerView.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeRight() {
                previousMonthAction();
            }

            public void onSwipeLeft() {
                nextMonthAction();
            }
        });

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventDialog eventDialog = new EventDialog();
                eventDialog.show(getActivity().getSupportFragmentManager(), "Thêm sự kiện");
            }
        });

        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDate=LocalDate.now();
                loadData();
            }
        });

        tvMonthYearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });
    }

    private void ShowDialog() {
        FragmentManager mine = getActivity().getSupportFragmentManager();
        BottomDialog dialogFragment = new BottomDialog(this);
        dialogFragment.show(mine, BottomDialog.TAG);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setEventView(int day)
    {
        EventDatabase eventDatabase = new EventDatabase(getActivity());
        listEvent = (ArrayList<EventInfo>) eventDatabase.getEventday(day,selectedDate.getMonthValue(),selectedDate.getYear());

        EventDatabaseOpenHelper eventDatabaseOpenHelper = new EventDatabaseOpenHelper(getContext());
        eventDatabaseOpenHelper.CreateDatabase();
        eventDatabaseOpenHelper.openDatabase();
        eventDatabaseOpenHelper.getEventday(day,selectedDate.getMonthValue(),listEvent,true);
        ChinaCalendar chinaCalendar = new ChinaCalendar(day, selectedDate.getMonthValue(), selectedDate.getYear(), 7);
        Date date = chinaCalendar.ConVertToLunar();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        eventDatabaseOpenHelper.getEventday(localDate.getDayOfMonth(),localDate.getMonthValue(),listEvent,false);

        EventAdapter eventAdapter = new EventAdapter(listEvent, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        eventRecyclerView.setLayoutManager(layoutManager);
        eventRecyclerView.setAdapter(eventAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initWidgets(View v) {
        calendarRecyclerView = v.findViewById(R.id.calendarRecyclerView);
        tvMonthYearText = v.findViewById(R.id.monthYearTV);
        eventRecyclerView = v.findViewById(R.id.eventRecyclerView);
        tvEventIn = v.findViewById(R.id.eventIn);
        addEvent = v.findViewById(R.id.addEventBtn);
        btnToday=v.findViewById(R.id.btnToday);
        tvEventIn.setText("Sự kiện trong ngày " + selectedDate.getDayOfMonth() + " " + monthYearFromDate(selectedDate));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        tvMonthYearText.setText("Tháng "+selectedDate.getMonthValue()+" - "+selectedDate.getYear());
        daysInMonth = daysInMonthArray(selectedDate);
        ArrayList<String> daysLunar = daysLunarArray(selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, daysLunar, selectedDate, tvEventIn, this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        calendarRecyclerView.scheduleLayoutAnimation();

        if(selectedDate.isEqual(LocalDate.now()))
            btnToday.setVisibility(View.GONE);
        else
            btnToday.setVisibility(View.VISIBLE);
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
    public void previousMonthAction() {
        selectedDate = selectedDate.withDayOfMonth(1).minusMonths(1);
        loadData();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction() {
        selectedDate = selectedDate.withDayOfMonth(1).plusMonths(1);
        loadData();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText) {
        String day=daysInMonth.get(position);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        selectedDate = LocalDate.parse(day, formatter);
        loadData();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onEventClick(int position,int type) {
        if (type == 1)
        {
            Intent detailIntent = new Intent(getActivity(), AddEvent.class);
            detailIntent.putExtra("position",position);
            startActivity(detailIntent);
            Check = 1;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void LoadNewDate(LocalDateTime selectedDate) {
        this.selectedDate=selectedDate.toLocalDate();
        loadData();
    }
}