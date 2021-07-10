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

import com.example.mycalendar.R;

import static android.content.Context.SENSOR_SERVICE;

public class ImgCompassFragment extends Fragment implements SensorEventListener {

    // define the display assembly compass picture
    private ImageView image;
    private int typeCompass;
    View view;
    // record the compass picture angle turned
    private float currentDegree = 0f;

    // device sensor manager
    private SensorManager mSensorManager;

    public ImgCompassFragment() {
        // Required empty public constructor
    }

    public ImgCompassFragment(int typeCompass) {
        this.typeCompass=typeCompass;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_img_compass, container, false);
        image = view.findViewById(R.id.imgCompass);
        image.setImageResource(typeCompass);
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
        float hDegree = Math.round(event.values[0]);
        float tDegree=hDegree+180>360?hDegree+180-360:hDegree+180;
        String strHuongDirect =null;
        String strToaDirect =null;

        if(hDegree <=22)
            strHuongDirect ="Bắc";
        else if(hDegree <=67)
            strHuongDirect ="Đông Bắc";
        else if(hDegree <=112)
            strHuongDirect ="Đông";
        else if(hDegree <=157)
            strHuongDirect ="Đông Nam";
        else if(hDegree <=202)
            strHuongDirect ="Nam";
        else if(hDegree <=247)
            strHuongDirect ="Tây Nam";
        else if(hDegree <=292)
            strHuongDirect ="Tây";
        else if(hDegree <=337)
            strHuongDirect ="Tây Bắc";
        else if(hDegree <=360)
            strHuongDirect ="Bắc";
        strHuongDirect=Float.toString(hDegree)+ "\u00B0" + strHuongDirect;

        if(tDegree <=22)
            strToaDirect ="Bắc";
        else if(tDegree <=67)
            strToaDirect ="Đông Bắc";
        else if(tDegree <=112)
            strToaDirect ="Đông";
        else if(tDegree <=157)
            strToaDirect ="Đông Nam";
        else if(tDegree <=202)
            strToaDirect ="Nam";
        else if(tDegree <=247)
            strToaDirect ="Tây Nam";
        else if(tDegree <=292)
            strToaDirect ="Tây";
        else if(tDegree <=337)
            strToaDirect ="Tây Bắc";
        else if(tDegree <=360)
            strToaDirect ="Bắc";
        strToaDirect=Float.toString(tDegree)+ "\u00B0" + strToaDirect;

        CompassFragment obj= (CompassFragment) getParentFragment();// getActivity().getSupportFragmentManager().findFragmentByTag("Compass fragment");
        obj.setText(strHuongDirect,strToaDirect);
        // create a rotation animation (reverse turn hDegree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -hDegree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(10);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
        currentDegree = -hDegree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
}