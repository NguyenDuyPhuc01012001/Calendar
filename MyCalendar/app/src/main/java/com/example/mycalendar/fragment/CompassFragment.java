package com.example.mycalendar.fragment;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycalendar.R;

import static android.content.Context.SENSOR_SERVICE;

public class CompassFragment extends Fragment {
    // define the display assembly compass picture
    private Integer ListCompass[] = {R.drawable.compass_macdinh, R.drawable.compass_24son,
            R.drawable.compass_60mach, R.drawable.compass_72long,
            R.drawable.compass_thiendianhan};
    private Integer counter = 0;

    TextView tvHuong;
    TextView tvToa;
    TextView tvNameCompass;
    Button btnPreviousCompass;
    Button btnNextCompass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compass, container, false);
        init(view);
        setEvent();
        return view;
    }

    private void init(View view) {
        // our compass image
        LoadFragment(ListCompass[0], false);
        // TextView that will tell the user what degree is he heading
        tvHuong = view.findViewById(R.id.tvHuong);
        // TextView that will tell the user what degree is he backing
        tvToa = view.findViewById(R.id.tvToa);
        tvNameCompass = view.findViewById(R.id.tvNameCompass);

        btnNextCompass = view.findViewById(R.id.nextCompass);
        btnPreviousCompass = view.findViewById(R.id.previousCompass);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setEvent() {
        btnPreviousCompass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadCompass(false);
            }
        });

        btnNextCompass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadCompass(true);
            }
        });
    }

    private void LoadCompass(boolean isPlusCompass) {
        if (isPlusCompass)
            counter++;
        else
            counter--;

        if (counter == 5) {
            counter = 0;
            LoadFragment(ListCompass[counter], isPlusCompass);
        } else if (counter < 0) {
            counter = 4;
            LoadFragment(ListCompass[counter], isPlusCompass);
        } else
            LoadFragment(ListCompass[counter], isPlusCompass);
        tvNameCompass.setText(getResources().getStringArray(R.array.compass)[counter]);
    }

    private void LoadFragment(int image, boolean isPlusCompass) {
//        ImgCompassFragment fragment=(ImgCompassFragment) getChildFragmentManager().findFragmentById(R.id.frImgCompass);
//        fragment.setImageCompass(image);
        ImgCompassFragment fragment = new ImgCompassFragment(image);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (isPlusCompass)
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        else
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.add(R.id.frImgCompass, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void setText(String huong, String toa) {
        tvHuong.setText("Hướng: " + huong);
        tvToa.setText("Tọa: " + toa);
    }
}