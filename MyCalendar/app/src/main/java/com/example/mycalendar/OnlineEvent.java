package com.example.mycalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mycalendar.model.EventInfo;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OnlineEvent extends AppCompatActivity {

    private TextView Name;
    private String UserId;
    private String UserEmail;
    private EditText Content;
    private LinearLayout startLL;
    private LinearLayout endLL;
    private TextView Date,timeStart,timeEnd;
    private Switch isAllDay;
    private ProgressBar progressBar;
    private FirebaseDatabase db = FirebaseDatabase.getInstance("https://ascendant-nova-318320-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private DatabaseReference reference;
    Calendar calendar = Calendar.getInstance();
    int year1 = calendar.get(Calendar.YEAR);
    int month1 = calendar.get(Calendar.MONTH) + 1;
    int day1 = calendar.get(Calendar.DAY_OF_MONTH);
    int hourStart = 0;
    int minuteStart = 0;
    int hourEnd = 0;
    int minuteEnd = 0;
    List<EventInfo> eventInfoList;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_event);
        Name = findViewById(R.id.userName);
        UserId =  auth.getCurrentUser().getUid();
        UserEmail = auth.getCurrentUser().getEmail();
        Date = findViewById(R.id.DateStart);
        Content = findViewById(R.id.ContentET);
        timeStart = findViewById(R.id.TimeStart);
        timeEnd = findViewById(R.id.TimeEnd);
        isAllDay = findViewById(R.id.AllDaySW);
        progressBar = findViewById(R.id.OnlineProgress);
        startLL = findViewById(R.id.timeStartLL);
        endLL = findViewById(R.id.timeEndLL);
        reference = db.getReference().child(UserId).child("Events");
        Date.setText(day1 + "/" + month1 + "/" + year1 + " >");
        if(user.getDisplayName() != "")
        {
            Name.setText(user.getDisplayName());
        }
        else {
            DocumentReference UserRef = firestore.document("users/" + UserId);
            UserRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        Name.setText(documentSnapshot.getString("Name"));
                    } else {
                        Toast.makeText(OnlineEvent.this, "the user does not have name", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        isAllDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    startLL.setVisibility(View.GONE);
                    endLL.setVisibility(View.GONE);
                }
                else
                {
                    startLL.setVisibility(View.VISIBLE);
                    endLL.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void LogOutOnClick(View view) {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        finish();
    }

    public void Save1OnClick(View view) {
        progressBar.setVisibility(View.VISIBLE);
        String id = reference.push().getKey();
        EventInfo eventInfo;
        if(isAllDay.isChecked() == true)
        {
            eventInfo = new EventInfo(id,Content.getText().toString(),day1,month1,year1,0,0,0,0, day1+"_"+month1 + "_" + year1, 3,true);
        }
        else
        {
            eventInfo = new EventInfo(id,Content.getText().toString(),day1,month1,year1,hourStart,minuteStart,hourEnd,minuteEnd, day1+"_"+month1 + "_" + year1, 3,false);
        }
        reference.child(id).setValue(eventInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(OnlineEvent.this,"Thêm thành công!",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    finish();
                }
            }
        });
    }

    public void dateOnClick(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(OnlineEvent.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                day1 = dayOfMonth;
                month1 = month+1;
                year1 = year;
                String date = day1 + "/" + month1 + "/" + year1 + " >";
                Date.setText(date);
            }
        }, year1, month1, day1);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.updateDate(year1,month1-1,day1);
        datePickerDialog.show();
    }

    public void timeStartOnClick(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(OnlineEvent.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hourStart =hourOfDay;
                        minuteStart=minute;
                        String time = hourStart + ":" + minuteStart + " >";
                        SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                        try{
                            java.util.Date date =f24Hours.parse(time);
                            timeStart.setText(f24Hours.format(date));
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
                                time = hourEnd + ":" + minuteEnd + " >";
                                Date date2 =f24Hours.parse(time);
                                timeEnd.setText(f24Hours.format(date2));
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

    public void TimeEndOnClick(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(OnlineEvent.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hourEnd =hourOfDay;
                        minuteEnd=minute;
                        String time = hourEnd + ":" + minuteEnd + " >";
                        SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                        try{
                            Date date =f24Hours.parse(time);

                            timeEnd.setText(f24Hours.format(date));
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
                                time = hourStart + ":" + minuteStart + " >";
                                Date date2 =f24Hours.parse(time);
                                timeStart.setText(f24Hours.format(date2));
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

}