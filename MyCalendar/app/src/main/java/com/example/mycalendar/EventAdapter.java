package com.example.mycalendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder > {

    private ArrayList<EventInfo> eventInfoArrayList;
    private OnItemListener onItemListener;


    public EventAdapter(ArrayList<EventInfo> eventInfoArrayList, OnItemListener onItemListener) {
        this.eventInfoArrayList = eventInfoArrayList;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_cell,parent,false);

        return new ViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull  EventAdapter.ViewHolder holder, int position) {
        holder.title.setText(eventInfoArrayList.get(position).getTitle());
        holder.hourStart.setText(String.valueOf(eventInfoArrayList.get(position).getStartHour()));
        holder.minuteStart.setText(String.valueOf(eventInfoArrayList.get(position).getStartMinute()));
        holder.hourtEnd.setText(String.valueOf(eventInfoArrayList.get(position).getEndHour()));
        holder.minuteEnd.setText(String.valueOf(eventInfoArrayList.get(position).getEndMinute()));
    }


    @Override
    public int getItemCount() {
        return eventInfoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView title;
        private TextView hourStart;
        private TextView minuteStart;
        private TextView hourtEnd;
        private TextView minuteEnd;
        private OnItemListener onItemListener;

        public ViewHolder(View itemView, OnItemListener onItemListener)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleTV);
            hourStart = (TextView) itemView.findViewById(R.id.starthTV);
            minuteStart = (TextView) itemView.findViewById(R.id.startmTV);
            hourtEnd = (TextView) itemView.findViewById(R.id.endhTV);
            minuteEnd = (TextView) itemView.findViewById(R.id.endmTV);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }
        @Nullable
        @Override
        public void onClick(View v) {
            onItemListener.onEventClick(getAdapterPosition());
        }
    }

    public interface OnItemListener
    {
        void onEventClick(int position);
    }
}
