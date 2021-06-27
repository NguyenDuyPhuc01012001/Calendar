package com.example.mycalendar.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mycalendar.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrayerContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrayerContentFragment extends Fragment {

    TextView textView;
    ImageButton zoomIn;
    ImageButton zoomOut;
    float scale = 20;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prayer_content, container, false);

        textView = view.findViewById(R.id.textview);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setText(getArguments().getString("key"));

        zoomOut = view.findViewById(R.id.zoomOut);  //thu nhỏ
        zoomIn = view.findViewById(R.id.zoomIn);    //phóng to
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scale -= 2;
                textView.setTextSize(scale);
            }
        });
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scale += 2;
                textView.setTextSize(scale);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}