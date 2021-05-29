package com.example.mycalendar;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public CardView calendar_cell;
    public final TextView dayOfMonth;
    public final TextView dayLunar;
    public final FrameLayout background;

    public CalendarViewHolder(@NonNull View itemView)
    {
        super(itemView);
        this.calendar_cell=itemView.findViewById(R.id.calendar_cell);
        this.dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.dayLunar = itemView.findViewById(R.id.cellLunarDayText);
        this.background= itemView.findViewById(R.id.background);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }


}
