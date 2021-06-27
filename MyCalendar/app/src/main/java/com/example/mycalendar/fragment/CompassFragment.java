package com.example.mycalendar.fragment;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycalendar.R;

import static android.content.Context.SENSOR_SERVICE;

public class CompassFragment extends Fragment implements SensorEventListener {

    // define the display assembly compass picture
    private ImageView image;

    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;

    TextView tvHeading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_compass, container, false);
        // our compass image
        image = view.findViewById(R.id.imageViewCompass);

        // TextView that will tell the user what degree is he heading
        tvHeading = view.findViewById(R.id.tvHeading);

        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        // get the angle around the z-axis rotated
        float degree = Math.round(event.values[0]);
        String strDirect=null;

        if(degree<=20)
            strDirect="Bắc";
        else if(degree<=60)
        strDirect="Đông Bắc";
        else if(degree<=100)
            strDirect="Đông";
        else if(degree<=140)
            strDirect="Đông Nam";
        else if(degree<=180)
            strDirect="Nam";
        else if(degree<=220)
            strDirect="Tây Nam";
        else if(degree<=260)
            strDirect="Tây";
        else if(degree<=300)
            strDirect="Tây Bắc";
        else if(degree<=360)
            strDirect="Bắc";

        tvHeading.setText(Float.toString(degree) + "\u00B0"+strDirect);

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(10);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
}