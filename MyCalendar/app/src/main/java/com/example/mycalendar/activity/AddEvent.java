package com.example.mycalendar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mycalendar.R;
import com.example.mycalendar.database.EventDatabase;
import com.example.mycalendar.fragment.MonthCalendarFragment;
import com.example.mycalendar.model.EventInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddEvent extends AppCompatActivity {

    int id = -1;
    int hourStart = 0;
    int minuteStart = 0;
    int hourEnd = 0;
    int minuteEnd = 0;
    TextView TitleText;
    Calendar calendar = Calendar.getInstance();
    int year1 = calendar.get(Calendar.YEAR);
    int month1 = calendar.get(Calendar.MONTH) + 1;
    int day1 = calendar.get(Calendar.DAY_OF_MONTH);
    String Title;
    Button DeleteBtn;
    Button Save1Btn;
    Button Save2Btn;
    Switch alldaySW;
    TextView Date;
    TextView startTime;
    TextView endTime;
    EditText content;
    LinearLayout startlayout;
    LinearLayout endlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        init();
        getID();
        alldaySW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true)
                {
                    startlayout.setVisibility(View.GONE);
                    endlayout.setVisibility(View.GONE);
                }
                else
                {
                    startlayout.setVisibility(View.VISIBLE);
                    endlayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void getID() {
        EventInfo eventInfo;
        Intent preIntent = getIntent();
        if(MonthCalendarFragment.Check == 1)
        {
            int position = preIntent.getIntExtra("position", 0);
            eventInfo = MonthCalendarFragment.listEvent.get(position);
            id = Integer.parseInt(eventInfo.getId());
            showResult(eventInfo.getTitle(),eventInfo.getDay(),eventInfo.getMonth(),eventInfo.getYear(),eventInfo.getStartHour(),eventInfo.getStartMinute(),eventInfo.getEndHour(),eventInfo.getEndMinute(),eventInfo.isAllDay());
        }
        else if(MonthCalendarFragment.Check == 0)
        {
            id = preIntent.getIntExtra("id",-1);
        }
    }

    private void init() {
        Date = (TextView) findViewById(R.id.datepickerTV);
        DeleteBtn = (Button) findViewById(R.id.deleteBtn);
        Save1Btn = (Button) findViewById(R.id.save1Btn);
        Save2Btn = (Button) findViewById(R.id.save2Btn);
        TitleText = (TextView) findViewById(R.id.titleTV);
        alldaySW = (Switch) findViewById(R.id.AllDaySW);
        startTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);
        startlayout = findViewById(R.id.startLL);
        endlayout = findViewById(R.id.endLL);
        content = findViewById(R.id.contentET);
        String date = day1 + "/" + month1 + "/" + year1;
        Date.setText(date);
        getID();
        if(id != -1 )
        {
            DeleteBtn.setVisibility(View.VISIBLE);
            Save1Btn.setVisibility(View.INVISIBLE);
            Save2Btn.setVisibility(View.VISIBLE);
            TitleText.setText("Sửa sự kiện");
        }
        else
        {
            DeleteBtn.setVisibility(View.INVISIBLE);
            Save1Btn.setVisibility(View.VISIBLE);
            Save2Btn.setVisibility(View.INVISIBLE);
            TitleText.setText("Thêm sự kiện");
        }
    }

    public void startOnClick(View view) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddEvent.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hourStart =hourOfDay;
                        minuteStart=minute;
                        String time = hourStart + ":" + minuteStart;
                        SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                        try{
                            Date date =f24Hours.parse(time);
                            startTime.setText(f24Hours.format(date));
                            if(hourStart > hourEnd || (hourStart == hourEnd && minuteStart >minuteEnd))
                            {
                                if(hourStart == 23)
                                {
                                    hourEnd = 23;
                                    minuteEnd = 59;
                                }
                                else
                                {
                                    hourEnd = hourStart + 1;
                                }
                                time = hourEnd + ":" + minuteEnd;
                                Date date2 =f24Hours.parse(time);
                                endTime.setText(f24Hours.format(date2));
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, 24,0,true);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(hourStart,minuteStart);
        timePickerDialog.show();
    }

    public void endOnClick(View view) {
        TextView startTime = (TextView) findViewById(R.id.startTime);
        TextView endTime = (TextView) findViewById(R.id.endTime);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddEvent.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hourEnd =hourOfDay;
                        minuteEnd=minute;
                        String time = hourEnd + ":" + minuteEnd;
                        SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                        try{
                            Date date =f24Hours.parse(time);

                            endTime.setText(f24Hours.format(date));
                            if(hourStart > hourEnd || (hourStart == hourEnd && minuteStart >minuteEnd))
                            {
                                if(hourEnd == 0)
                                {
                                    hourStart = 0;
                                    minuteStart = 0;
                                }
                                else
                                {
                                    hourStart = hourEnd - 1;
                                }
                                time = hourStart + ":" + minuteStart;
                                Date date2 =f24Hours.parse(time);
                                startTime.setText(f24Hours.format(date2));
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, 24,0,true);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(hourEnd,minuteEnd);
        timePickerDialog.show();
    }

    public void dateOnClick(View view) {
        TextView Date = (TextView) findViewById(R.id.datepickerTV);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddEvent.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                day1 = dayOfMonth;
                month1 = month+1;
                year1 = year;
                String date = day1 + "/" + month1 + "/" + year1;
                Date.setText(date);
            }
        }, year1, month1, day1);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.updateDate(year1,month1-1,day1);
        datePickerDialog.show();
    }

    public void saveOnClick(View view) {
        Title = content.getText().toString();
        EventInfo eventInfo;
        EventDatabase eventDatabase = new EventDatabase(AddEvent.this);

        ArrayList<EventInfo> listEvent = new ArrayList<EventInfo>();
        listEvent = (ArrayList<EventInfo>) eventDatabase.CheckID(id);
        if(listEvent.size() == 1)
        {

            String str_id = String.valueOf(id);
            eventInfo = new EventInfo(str_id, Title,day1,month1,year1,hourStart,minuteStart,hourEnd,minuteEnd, "", 1, alldaySW.isChecked());
            eventDatabase.EditEvent(eventInfo);
            Toast.makeText(AddEvent.this,"Sửa thành công!",Toast.LENGTH_SHORT).show();
        }
        else
        {
            try {
                if(Title.isEmpty())
                {
                    Toast.makeText(AddEvent.this,"Nội dung không được trống",Toast.LENGTH_SHORT).show();
                }
                else {
                    eventInfo = new EventInfo("-1",Title,day1,month1,year1,hourStart,minuteStart,hourEnd,minuteEnd, "", 1, alldaySW.isChecked());
                    eventDatabase.addOne(eventInfo);
                    Toast.makeText(AddEvent.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                Toast.makeText(AddEvent.this,"Lỗi không xác định",Toast.LENGTH_SHORT).show();
            }
        }
        setResult(Activity.RESULT_OK);
        finish();
    }

    public void showResult(String Title, int day,int month, int year, int hourstart,int minutestart, int hourend,int minuteend, boolean allday)
    {
        SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");

        day1 = day;
        month1 = month;
        year1 = year;
        hourStart = hourstart;
        minuteStart = minutestart;
        hourEnd = hourend;
        minuteEnd = minuteend;

        content.setText(Title);
        alldaySW.setChecked(allday);
        if(allday == true)
        {
            startlayout.setVisibility(View.GONE);
            endlayout.setVisibility(View.GONE);
        }
        else
        {
            startlayout.setVisibility(View.VISIBLE);
            endlayout.setVisibility(View.VISIBLE);
        }

        Date.setText(day + "/" + month + "/" + year);
        String time = hourStart + ":" + minuteStart;
        Date date2;
        try {
            date2 = f24Hours.parse(time);
            startTime.setText(f24Hours.format(date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        time = hourEnd + ":" + minuteEnd;
        Date date3 = null;
        try {
            date3 = f24Hours.parse(time);
            endTime.setText(f24Hours.format(date3));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void deleteOnClick(View view) {
        EventDatabase eventDatabase = new EventDatabase(AddEvent.this);
        eventDatabase.deleteDatabase(id);
        Toast.makeText(AddEvent.this,"Xóa thành công!",Toast.LENGTH_SHORT).show();
        setResult(Activity.RESULT_OK);
        finish();
    }
}