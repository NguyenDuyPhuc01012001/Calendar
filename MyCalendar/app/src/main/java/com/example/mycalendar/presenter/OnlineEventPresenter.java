package com.example.mycalendar.presenter;

import java.text.SimpleDateFormat;

public class OnlineEventPresenter {
    OnlineEventInterface onlineEventInterface;

    public OnlineEventPresenter(OnlineEventInterface onlineEventInterface) {
        this.onlineEventInterface = onlineEventInterface;
    }
    public void isAllDay(boolean allday){
        if(allday == true)
        {
            onlineEventInterface.AllDayTrue();
        }
        else if(allday == false)
        {
            onlineEventInterface.AllDayFalse();
        }
    }
    public void CheckSave(int check)
    {
        if(check == 0)
        {
            onlineEventInterface.CheckSave1();
        }
        else
        {
            onlineEventInterface.CheckSave2();
        }
    }
}
