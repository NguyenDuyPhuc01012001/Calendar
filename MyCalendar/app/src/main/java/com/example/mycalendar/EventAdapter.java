package com.example.mycalendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.EventListener;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder > {

    private final ArrayList<EventInfo> eventInfoArrayList;
    MyClickListener listener;
    public EventAdapter(ArrayList<EventInfo> eventInfoArrayList, MyClickListener listener) {
        this.eventInfoArrayList = eventInfoArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_cell,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  EventAdapter.ViewHolder holder, int position) {
        holder.title.setText(eventInfoArrayList.get(position).getTitle());
        holder.hourStart.setText(String.valueOf(eventInfoArrayList.get(position).getStartHour()));
        holder.minuteStart.setText(String.valueOf(eventInfoArrayList.get(position).getStartMinute()));
        holder.hourtEnd.setText(String.valueOf(eventInfoArrayList.get(position).getEndHour()));
        holder.minuteEnd.setText(String.valueOf(eventInfoArrayList.get(position).getEndMinute()));
        holder.event_cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //listener.onEventClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return eventInfoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout event_cell;
        private TextView title;
        private TextView hourStart;
        private TextView minuteStart;
        private TextView hourtEnd;
        private TextView minuteEnd;

        public ViewHolder(View itemView)
        {
            super(itemView);
            this.event_cell = (LinearLayout) itemView.findViewById(R.id.event_cell);
            title = (TextView) itemView.findViewById(R.id.titleTV);
            hourStart = (TextView) itemView.findViewById(R.id.starthTV);
            minuteStart = (TextView) itemView.findViewById(R.id.startmTV);
            hourtEnd = (TextView) itemView.findViewById(R.id.endhTV);
            minuteEnd = (TextView) itemView.findViewById(R.id.endmTV);
        }
        @Nullable
        @Override
        public void onClick(View v) {
        }
    }
}
