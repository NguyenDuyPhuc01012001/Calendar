package com.example.mycalendar.presenter;

import com.example.mycalendar.model.DateTimeInfo;

public interface DayCalendarInterface {
    void LoadData(DateTimeInfo dateTimeInfo);
    void LoadMaxim(String maxim);
    void SetBTNVisibility(boolean isShow);
}
