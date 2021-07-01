package com.example.mycalendar.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mycalendar.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrayerContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrayerContentFragment extends Fragment {

    TextView textView;
    ScaleGestureDetector scaleGestureDetector;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PrayerContentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PrayerContentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrayerContentFragment newInstance(String param1, String param2) {
        PrayerContentFragment fragment = new PrayerContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        scaleGestureDetector = new ScaleGestureDetector(this.getActivity().getApplicationContext(), new PinchToZoomGestureListener());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prayer_content, container, false);

        textView = view.findViewById(R.id.textview);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setText(getArguments().getString("key"));

        // zoom bằng cử chỉ 2 ngón tay
        textView.setOnTouchListener(new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                scaleGestureDetector.onTouchEvent(event);
                return v.performClick();

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public class PinchToZoomGestureListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener
    {
        @Override
        public boolean onScale(ScaleGestureDetector detector)
        {
            float size = textView.getTextSize();

            float factor = detector.getScaleFactor();

            float product = size * factor;
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, product);

            size = textView.getTextSize();
            return true;
        }
    }
}