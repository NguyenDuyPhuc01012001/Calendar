package com.example.mycalendar.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mycalendar.activity.AddEvent;
import com.example.mycalendar.dialog.BottomDialog;
import com.example.mycalendar.ChinaCalendar;
import com.example.mycalendar.dialog.EventDialog;
import com.example.mycalendar.OnSwipeTouchListener;
import com.example.mycalendar.activity.OnlineEvent;
import com.example.mycalendar.R;
import com.example.mycalendar.adapter.CalendarAdapter;
import com.example.mycalendar.adapter.EventAdapter;
import com.example.mycalendar.database.EventDatabase;
import com.example.mycalendar.database.EventDatabaseOpenHelper;
import com.example.mycalendar.model.EventInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

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
    private FirebaseAuth auth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance("https://ascendant-nova-318320-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private LocalDate selectedDate;
    private ProgressBar progressBar;
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

        listEvent.clear();
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


        for(EventInfo eventInfo : listEvent)
        {
            Log.i("all the title",eventInfo.getTitle().toString());
        }

        EventAdapter eventAdapter = new EventAdapter(listEvent, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        eventRecyclerView.setLayoutManager(layoutManager);
        eventRecyclerView.setAdapter(eventAdapter);
        try {
            progressBar.setVisibility(View.VISIBLE);
            eventRecyclerView.setVisibility(View.INVISIBLE);
            String UserId =  auth.getCurrentUser().getUid();
            Query query = db.getReference(UserId + "/Events/").orderByChild("date").equalTo(day + "_" + selectedDate.getMonthValue() + "_" + selectedDate.getYear());
            query.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
                        for(DataSnapshot snapshot1 : snapshot.getChildren())
                        {
                            EventInfo eventInfo = snapshot1.getValue(EventInfo.class);
                            listEvent.add(eventInfo);
                            Log.i("event tag",eventInfo.getId().toString());
                        }
                        eventAdapter.notifyDataSetChanged();

                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    eventRecyclerView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    progressBar.setVisibility(View.INVISIBLE);
                    eventRecyclerView.setVisibility(View.VISIBLE);
                }

            });
        }
        catch (Exception e)
        {
            Log.i("login",e.getMessage());
            progressBar.setVisibility(View.INVISIBLE);
            eventRecyclerView.setVisibility(View.VISIBLE);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initWidgets(View v) {
        calendarRecyclerView = v.findViewById(R.id.calendarRecyclerView);
        tvMonthYearText = v.findViewById(R.id.monthYearTV);
        eventRecyclerView = v.findViewById(R.id.eventRecyclerView);
        tvEventIn = v.findViewById(R.id.eventIn);
        addEvent = v.findViewById(R.id.addEventBtn);
        btnToday=v.findViewById(R.id.btnToday);
        auth = FirebaseAuth.getInstance();
        progressBar = v.findViewById(R.id.monthPB);
        tvEventIn.setText("Sự kiện trong ngày " + selectedDate.getDayOfMonth() + " " + monthYearFromDate(selectedDate));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() {
        boolean isNewMonth=false;
        if(Integer.valueOf(tvMonthYearText.getText().toString().split(" ")[1])!=selectedDate.getMonthValue())
            isNewMonth=true;

        daysInMonth = daysInMonthArray(selectedDate);
        ArrayList<String> daysLunar = daysLunarArray(selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, daysLunar, selectedDate, tvEventIn, this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        if(isNewMonth){
            calendarRecyclerView.scheduleLayoutAnimation();
            tvMonthYearText.setText("Tháng "+selectedDate.getMonthValue()+" - "+selectedDate.getYear());
        }

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
    public void onItemLongClick(int position, String dayText) {
        LocalDateTime localDateTime = selectedDate.atStartOfDay();
        DayDetailFragment dayDetailFragment = new DayDetailFragment(localDateTime);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.month_calendar_fragment, dayDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onEventClick(int position,int type) {
        Check = 1;
        if (type == 1)
        {
            Intent detailIntent = new Intent(getActivity(), AddEvent.class);
            detailIntent.putExtra("position",position);
            startActivityForResult(detailIntent,10001);
        }
        else if(type == 3)
        {
            Intent onlineEvent = new Intent(getActivity(), OnlineEvent.class);
            onlineEvent.putExtra("position",position);
            startActivityForResult(onlineEvent,10001);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void LoadNewDate(LocalDateTime selectedDate) {
        this.selectedDate=selectedDate.toLocalDate();
        loadData();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("requestresult",requestCode + "-" + resultCode);
        setEventView(selectedDate.getDayOfMonth());
    }

}