package com.example.mycalendar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.mycalendar.R;
import com.example.mycalendar.fragment.AstrologyFragment;
import com.example.mycalendar.fragment.DayCalendarFragment;
import com.example.mycalendar.fragment.DayDetailFragment;
import com.example.mycalendar.fragment.MonthCalendarFragment;
import com.example.mycalendar.fragment.MoreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.tab_day_calendar:
                    fragment = new DayCalendarFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.tab_monh_calendar:
                    fragment = new MonthCalendarFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.tab_astrology:
                    fragment = new AstrologyFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.tab_more:
                    fragment = new MoreFragment();
                    loadFragment(fragment);
                    return true;
            }
            return true;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.fragment_container, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        requestCode &= 0xffff;
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}