package com.example.mycalendar.dialog;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.mycalendar.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class BottomDialog extends BottomSheetDialogFragment {
    public static final String TAG="BottomDialog";
    public static final int REQUEST_CODE=1;

    private DatePicker datePicker;
    private Button btnToday,btnSelect;

    public interface OnSelected{
        void LoadNewDate(LocalDateTime selectedDate);
    }

    public BottomDialog(OnSelected mOnSelected) {
        this.mOnSelected = mOnSelected;
    }

    public OnSelected mOnSelected;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.dialog_bottom, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull @org.jetbrains.annotations.NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
Init();
setOnClick();

    }

    private void setOnClick() {
        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = LocalDate.now().getDayOfMonth();
                int month = LocalDate.now().getMonthValue()-1;
                int year = LocalDate.now().getYear();

                datePicker.updateDate(year,month,day);
            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                LocalDateTime dateTime=getDateTimeFromDatePicker(datePicker);
                mOnSelected.LoadNewDate(dateTime);
                getDialog().dismiss();
            }
        });
    }

    private void Init() {
        datePicker = getView().findViewById(R.id.datePicker);
        btnToday=getView().findViewById(R.id.btnToday);
        btnSelect=getView().findViewById(R.id.btnSelect);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime getDateTimeFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth()+1;
        int year = datePicker.getYear();
        int hour= LocalTime.now().getHour();
        int minute= LocalTime.now().getMinute();

        LocalDateTime selectedDate = LocalDateTime.of(year, month,day,hour,minute);

        return selectedDate;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            if(context instanceof OnSelected)
                mOnSelected=(OnSelected) context;
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage() );
        }
    }
}