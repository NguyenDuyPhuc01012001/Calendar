package com.example.mycalendar.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_event,parent,false);
        return new ViewHolder(view,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull  EventAdapter.ViewHolder holder, int position) {
        holder.title.setText(eventInfoArrayList.get(position).getTitle());
        if(eventInfoArrayList.get(position).getType() == 1)
        {
            if(eventInfoArrayList.get(position).isAllDay() == true)
            {
                holder.time.setText("Cả ngày");
            }
            else
            {
                holder.time.setText(String.valueOf(eventInfoArrayList.get(position).getStartHour() + " : " + eventInfoArrayList.get(position).getStartMinute() + " - " + eventInfoArrayList.get(position).getEndHour() + " : " + eventInfoArrayList.get(position).getEndMinute()));
            }

            holder.iconEvent.setImageResource(R.drawable.ic_phone);
        }
        else if(eventInfoArrayList.get(position).getType() == 2)
        {
            holder.time.setText("Cả ngày");
            holder.iconEvent.setImageResource(R.drawable.lotus);
        }
        else
        {
            if(eventInfoArrayList.get(position).isAllDay() == true)
            {
                holder.time.setText("Cả ngày");

            }
            else
            {
                Log.i("Event title",holder.title.getText().toString());
                holder.time.setText(String.valueOf(eventInfoArrayList.get(position).getStartHour() + " : " + eventInfoArrayList.get(position).getStartMinute() + " - " + eventInfoArrayList.get(position).getEndHour() + " : " + eventInfoArrayList.get(position).getEndMinute() ));
            }
            holder.iconEvent.setImageResource(R.drawable.ic_wifi);
        }
    }


    @Override
    public int getItemCount() {
        return eventInfoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView time;
        private ImageView iconEvent;
        private OnItemListener onItemListener;

        public ViewHolder(View itemView, OnItemListener onItemListener)
        {
            super(itemView);
            iconEvent = itemView.findViewById(R.id.eventIcon);
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
