package com.example.mycalendar.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mycalendar.activity.LoginEvent;
import com.example.mycalendar.R;
import com.example.mycalendar.activity.AddEvent;
import com.example.mycalendar.fragment.MonthCalendarFragment;

import org.jetbrains.annotations.NotNull;

public class EventDialog extends AppCompatDialogFragment {
    private Button localBtn;
    private Button OnlineBtn;
    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_event,null);
        builder.setView(view)
                .setTitle("Thêm sự kiện")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        localBtn = view.findViewById(R.id.AddLocal);
        OnlineBtn = view.findViewById(R.id.AddOnline);
        localBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEvent.class);
                intent.putExtra("id",-1);
                MonthCalendarFragment.Check = 0;
                startActivityForResult(intent,10001);
            }
        });
        OnlineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginEvent.class);
                MonthCalendarFragment.Check = 0;
                startActivityForResult(intent,10001);
            }
        });
        return builder.create();
    }
}
