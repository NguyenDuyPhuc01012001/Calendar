package com.example.mycalendar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener, EventAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private RecyclerView eventRecyclerView;
    private LocalDate selectedDate;
    public static ArrayList<EventInfo> listEvent = new ArrayList<EventInfo>();
    private TextView eventIn;
    public static int Check = 0;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectedDate = LocalDate.now();
        initWidgets();
        setMonthView();
        setEventView(selectedDate.getDayOfMonth());
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setEventView(int day)
    {
        EventDatabase eventDatabase = new EventDatabase(MainActivity.this);
        listEvent = (ArrayList<EventInfo>) eventDatabase.getEventday(day,selectedDate.getMonthValue(),selectedDate.getYear());
        EventAdapter eventAdapter = new EventAdapter(listEvent,this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),1);
        eventRecyclerView.setLayoutManager(layoutManager);
        eventRecyclerView.setAdapter(eventAdapter);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventRecyclerView = findViewById(R.id.eventRecyclerView);
        eventIn = (TextView) findViewById(R.id.eventIn);
        eventIn.setText("Event in " + selectedDate.getDayOfMonth() + " " + monthYearFromDate(selectedDate));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        ArrayList<String> daysLunar = daysLunarArray(selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, daysLunar, this, selectedDate);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
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
    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
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
    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("vn"));
        return date.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
        eventIn.setText("Event in " + 1 + " " + monthYearFromDate(selectedDate));
        setEventView(Integer.valueOf(1));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
        eventIn.setText("Event in " + 1 + " " + monthYearFromDate(selectedDate));
        setEventView(Integer.valueOf(1));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, String dayText)
    {
        if(!dayText.equals(""))
        {
            eventIn.setText("Event in " + dayText + " " + monthYearFromDate(selectedDate));
            setEventView(Integer.valueOf(dayText));
        }
    }

    public void AddAction(View view) {
        Intent intent = new Intent(this, AddEvent.class);
        intent.putExtra("id",-1);
        startActivity(intent);
        Check = 0;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void TodayBtn(View view) {
        selectedDate = LocalDate.now();
        setMonthView();
    }

    @Override
    public void onEventClick(int position) {
        Intent detailIntent = new Intent(this,AddEvent.class);
        detailIntent.putExtra("position",position);
        startActivity(detailIntent);
        Check = 1;
    }
}