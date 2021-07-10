package com.example.mycalendar.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycalendar.dialog.BottomDialog;
import com.example.mycalendar.ChinaCalendar;
import com.example.mycalendar.R;
import com.example.mycalendar.adapter.EventAdapter;
import com.example.mycalendar.database.EventDatabase;
import com.example.mycalendar.database.EventDatabaseOpenHelper;
import com.example.mycalendar.model.DateTimeInfo;
import com.example.mycalendar.model.EventInfo;
import com.example.mycalendar.presenter.DayDetailInterface;
import com.example.mycalendar.presenter.DayDetailPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class DayDetailFragment extends Fragment implements DayDetailInterface,EventAdapter.OnItemListener,BottomDialog.OnSelected {

    private LocalDateTime selectedDate;
    private TextView dateSelectTV,numberDayTV,numberMonthTV,numberYearTV,stringDayTV,stringMonthTV,stringYearTV;
    private TextView ngayDaoTV,gioHoangDaoTV,gioHacDaoTV,xuatHanhTV,NhiThapTV,dateViewTV;
    private Button nextDay, previousDay;
    private ImageView ngayDaoImage;
    private RecyclerView eventRV;
    DayDetailPresenter ddPresenter;
    public static ArrayList<EventInfo> listEvent = new ArrayList<EventInfo>();
    private FirebaseAuth auth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance("https://ascendant-nova-318320-default-rtdb.asia-southeast1.firebasedatabase.app/");

    public DayDetailFragment(LocalDateTime selectedDate) {
        this.selectedDate=selectedDate;
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
        setEventView(selectedDate.getDayOfMonth());
        setEvent();
        ddPresenter.displayOutput(NhiThapTV,getActivity(),ddPresenter.GetStar28(selectedDate));
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void init(View v)
    {
//        selectedDate = LocalDateTime.now();
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
        eventRV = v.findViewById(R.id.eventRecyclerView);
        gioHoangDaoTV = v.findViewById(R.id.GioHoangDaoTV);
        gioHacDaoTV = v.findViewById(R.id.GioHacDaoTV);
        xuatHanhTV = v.findViewById(R.id.XuatHanhTV);
        NhiThapTV = v.findViewById(R.id.ThapNhiTV);
        dateViewTV = v.findViewById(R.id.monthYearTV);
        auth = FirebaseAuth.getInstance();
        ddPresenter = new DayDetailPresenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setEvent() {
        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDate = selectedDate.plusDays(1);
                ddPresenter.getData(selectedDate);
                setEventView(selectedDate.getDayOfMonth());
                ddPresenter.displayOutput(NhiThapTV,getActivity(),ddPresenter.GetStar28(selectedDate));
            }
        });

        previousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDate = selectedDate.minusDays(1);
                ddPresenter.getData(selectedDate);
                setEventView(selectedDate.getDayOfMonth());
                ddPresenter.displayOutput(NhiThapTV,getActivity(),ddPresenter.GetStar28(selectedDate));
            }
        });
        dateViewTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setEventView(int day)
    {
        listEvent.clear();
        eventRV.setVisibility(View.INVISIBLE);
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
        eventRV.setLayoutManager(layoutManager);
        eventRV.setAdapter(eventAdapter);
        try {
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
                    eventRV.setVisibility(View.VISIBLE);
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    eventRV.setVisibility(View.VISIBLE);
                }

            });
        }
        catch (Exception e)
        {
            Log.i("login","does not have account here!");
            eventRV.setVisibility(View.VISIBLE);
        }



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
            ngayDaoTV.setTextColor(Color.BLUE);
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
        gioHoangDaoTV.setText(DayDetailPresenter.GetZodiacTime(selectedDate.getDayOfMonth(),selectedDate.getMonthValue(),selectedDate.getYear()));
        gioHacDaoTV.setText(DayDetailPresenter.GetUnZodiacTime(selectedDate.getDayOfMonth(),selectedDate.getMonthValue(),selectedDate.getYear()));
        xuatHanhTV.setText("Đi về hướng " + DayDetailPresenter.GetTaiThan(selectedDate.getDayOfMonth(),selectedDate.getMonthValue(),selectedDate.getYear()) + " để nghênh tiếp Tài Thần \n" +
                "Đi về hướng " + DayDetailPresenter.GetHyThan(selectedDate.getDayOfMonth(),selectedDate.getMonthValue(),selectedDate.getYear()) + " để đón tiếp Hỷ Thần \n" +
                "Tránh đi về hướng " + DayDetailPresenter.GetHacThan(selectedDate.getDayOfMonth(),selectedDate.getMonthValue(),selectedDate.getYear()) + " để tránh Hắc Thần");
    }

    @Override
    public void onEventClick(int position, int type) {

    }

    private void ShowDialog() {
        FragmentManager mine = getActivity().getSupportFragmentManager();
        BottomDialog dialogFragment = new BottomDialog(this);
        dialogFragment.show(mine, BottomDialog.TAG);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void LoadNewDate(LocalDateTime selectedDate) {
        this.selectedDate=selectedDate;
        ddPresenter.getData(selectedDate);
        setEventView(selectedDate.getDayOfMonth());
        ddPresenter.displayOutput(NhiThapTV,getActivity(),ddPresenter.GetStar28(selectedDate));
    }
}