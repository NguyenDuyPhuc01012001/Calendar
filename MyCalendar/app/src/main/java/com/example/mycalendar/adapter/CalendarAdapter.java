package com.example.mycalendar.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycalendar.ChinaCalendar;
import com.example.mycalendar.R;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final ArrayList<String> daysOfMonth;
    private final ArrayList<String> daysLunar;
    private final LocalDate selectedDate;
    private final TextView eventIn;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<String> daysOfMonth, ArrayList<String> daysLunar, LocalDate selectedDate, TextView eventIn, OnItemListener onItemListener) {
        this.daysOfMonth = daysOfMonth;
        this.daysLunar = daysLunar;
        this.selectedDate = selectedDate;
        this.eventIn = eventIn;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        CalendarViewHolder viewHolder = new CalendarViewHolder(view,onItemListener);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, final int position) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String today = LocalDate.now().format(formatter);
        String dayOfMonth = daysOfMonth.get(position);
        int[] sunday = {6, 13, 20, 27, 34, 41};
        FrameLayout layout = holder.background;
        TextView tvDaySolar = holder.dayOfMonth;
        TextView tvDayLunar = holder.dayLunar;
        String dayLunar;
        if (daysLunar.get(position).startsWith("01"))
            dayLunar = daysLunar.get(position);
        else
            dayLunar = daysLunar.get(position).substring(0, 2);

        if (IntStream.of(sunday).anyMatch(x -> x == position)) {
            layout.setBackgroundResource(R.drawable.sunday_card);
        }
        if (today.equalsIgnoreCase(dayOfMonth)) {
            layout.setBackgroundResource(R.drawable.today_card);
        }
        if (dayLunar.startsWith("01") || dayLunar.startsWith("15")) {
            tvDayLunar.setTextColor(Color.parseColor("#FF0000"));
        }
        if (!isInMonth(dayOfMonth))
            tvDaySolar.setTextColor(Color.parseColor("#a9a9a9"));

        int monthLunar=Integer.parseInt(daysLunar.get(position).substring(3, 5));
        if(IsGoodOrBadDay(dayOfMonth,monthLunar)==1){
            holder.imgGoodOrBadDay.setVisibility(View.VISIBLE);
            holder.imgGoodOrBadDay.setImageResource(R.mipmap.yin_yang_red);
        }
        if(IsGoodOrBadDay(dayOfMonth,monthLunar)==0)
            holder.imgGoodOrBadDay.setVisibility(View.GONE);
        if(IsGoodOrBadDay(dayOfMonth,monthLunar)==-1){
            holder.imgGoodOrBadDay.setVisibility(View.VISIBLE);
            holder.imgGoodOrBadDay.setImageResource(R.mipmap.yin_yang_black);
        }

        holder.dayOfMonth.setText(dayOfMonth.split("/")[0]);
        holder.dayLunar.setText(dayLunar);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private int IsGoodOrBadDay(String dayOfMonth,int LunarMonth) {
        ChinaCalendar chinaCalendar=null;
        if(isInMonth(dayOfMonth)){
            LocalDate date=selectedDate;
            int day=Integer.parseInt(dayOfMonth.split("/")[0]);
            int month=date.getMonthValue()+1;
            int year=date.getYear();
            chinaCalendar=new ChinaCalendar(day,month,year,7);
        }
        if(isInPreviousMonth(dayOfMonth)){
            LocalDate date=selectedDate.withDayOfMonth(1).minusDays(1);
            int day=Integer.parseInt(dayOfMonth.split("/")[0]);
            int month=date.getMonthValue()+1;
            int year=date.getYear();
            chinaCalendar=new ChinaCalendar(day,month,year,7);
        }
        if(isInNextMonth(dayOfMonth)){
            LocalDate date=selectedDate.withDayOfMonth(1).plusMonths(1);
            int day=Integer.parseInt(dayOfMonth.split("/")[0]);
            int month=date.getMonthValue()+1;
            int year=date.getYear();
            chinaCalendar=new ChinaCalendar(day,month,year,7);
        }
        assert chinaCalendar != null;
        chinaCalendar.ConVertToLunar();
        return chinaCalendar.IsZodiacDay(LunarMonth);
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, String dayText);
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isInMonth(String day) {
        YearMonth yearMonth = YearMonth.from(selectedDate);
        LocalDate start = selectedDate.withDayOfMonth(1).minusDays(1);
        LocalDate end = selectedDate.withDayOfMonth(1).plusMonths(1);

        //DateTimeFormatterBuilder fmb = new DateTimeFormatterBuilder();
        //fmb.parseCaseInsensitive();
        //fmb.append(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate ldtDay = LocalDate.parse(day, formatter);

        return (ldtDay.isAfter(start) && ldtDay.isBefore(end));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isInPreviousMonth(String day) {
        YearMonth yearMonth = YearMonth.from(selectedDate);
        LocalDate start = selectedDate.withDayOfMonth(1);

        //DateTimeFormatterBuilder fmb = new DateTimeFormatterBuilder();
        //fmb.parseCaseInsensitive();
        //fmb.append(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate ldtDay = LocalDate.parse(day, formatter);

        return ldtDay.isBefore(start);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isInNextMonth(String day) {
        YearMonth yearMonth = YearMonth.from(selectedDate);
        LocalDate end = selectedDate.withDayOfMonth(1).plusMonths(1).minusDays(1);

        //DateTimeFormatterBuilder fmb = new DateTimeFormatterBuilder();
        //fmb.parseCaseInsensitive();
        //fmb.append(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate ldtDay = LocalDate.parse(day, formatter);

        return ldtDay.isAfter(end);
    }
}