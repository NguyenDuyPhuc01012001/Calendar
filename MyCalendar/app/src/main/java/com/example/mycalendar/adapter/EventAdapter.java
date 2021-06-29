package com.example.mycalendar.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycalendar.R;
import com.example.mycalendar.model.EventInfo;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder > {

    private final ArrayList<EventInfo> eventInfoArrayList;
    private OnItemListener onItemListener;
    public EventAdapter(ArrayList<EventInfo> eventInfoArrayList, OnItemListener onItemListener) {
        this.eventInfoArrayList = eventInfoArrayList;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_cell,parent,false);
        return new ViewHolder(view,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull  EventAdapter.ViewHolder holder, int position) {
        holder.title.setText(eventInfoArrayList.get(position).getTitle());
        if(eventInfoArrayList.get(position).getType() == 1)
        {
            holder.time.setText(String.valueOf(eventInfoArrayList.get(position).getStartHour() + " : " + eventInfoArrayList.get(position).getStartMinute() + " - " + eventInfoArrayList.get(position).getEndHour() + " : " + eventInfoArrayList.get(position).getEndMinute() ));
        }
        else if(eventInfoArrayList.get(position).getType() == 2)
        {
            holder.time.setText("Cả ngày");
            holder.title.setTextColor(Color.parseColor("#FF0000"));
            holder.time.setTextColor(Color.parseColor("#FF0000"));
        }
    }


    @Override
    public int getItemCount() {
        return eventInfoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView time;
        private OnItemListener onItemListener;

        public ViewHolder(View itemView, OnItemListener onItemListener)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.titleTV);
            time = (TextView) itemView.findViewById(R.id.timeTV);
            this.onItemListener =onItemListener;
            itemView.setOnClickListener(this);

        }
        @Nullable
        @Override
        public void onClick(View v) {
            onItemListener.onEventClick(getAdapterPosition(), eventInfoArrayList.get(getAdapterPosition()).getType());
        }
    }
    public interface OnItemListener
    {
        void onEventClick(int position, int type);
    }
}
