 package com.example.mycalendar;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.IntStream;

 public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final ArrayList<String> daysOfMonth;
    private final ArrayList<String> daysLunar;
    private final OnItemListener onItemListener;
    private LocalDate selectedDate;

    public CalendarAdapter(ArrayList<String> daysOfMonth, ArrayList<String> daysLunar, OnItemListener onItemListener, LocalDate selectedDate)
    {
        this.daysOfMonth = daysOfMonth;
        this.daysLunar = daysLunar;
        this.onItemListener = onItemListener;
        this.selectedDate = selectedDate;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        //layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, final int position)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String today=LocalDate.now().format(formatter);
        String dayOfMonth=daysOfMonth.get(position);
        int[] sunday={6,13,20,27,34,41};
        FrameLayout layout=holder.background;
        TextView tvDaySolar=holder.dayOfMonth;
        TextView tvDayLunar=holder.dayLunar;
        String dayLunar;
        if(daysLunar.get(position).startsWith("01"))
            dayLunar=daysLunar.get(position);
        else
            dayLunar=daysLunar.get(position).substring(0,2);

        if(IntStream.of(sunday).anyMatch(x -> x == position)){
            layout.setBackgroundResource(R.drawable.sunday_card);
        }
        if(today.equalsIgnoreCase(dayOfMonth)){
            layout.setBackgroundResource(R.drawable.today_card);
        }
        if(dayLunar.startsWith("01")||dayLunar.startsWith("15")){
            tvDayLunar.setTextColor(R.color.red);
        }
        if(!isInMonth(dayOfMonth))
            tvDaySolar.setTextColor(R.color.gray);

        holder.dayOfMonth.setText(dayOfMonth.split("/")[0]);
        holder.dayLunar.setText(dayLunar);
    }

    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, String dayText);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isInMonth(String day){
        YearMonth yearMonth = YearMonth.from(selectedDate);
        LocalDate start = selectedDate.withDayOfMonth(1).minusDays(1);
        LocalDate end = selectedDate.withDayOfMonth(1).plusMonths(1);

        //DateTimeFormatterBuilder fmb = new DateTimeFormatterBuilder();
        //fmb.parseCaseInsensitive();
        //fmb.append(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate ldtDay = LocalDate.parse(day, formatter);

        return (ldtDay.isAfter(start)&&ldtDay.isBefore(end));
    }
}
