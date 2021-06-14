package com.example.mycalendar.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycalendar.R;
import com.example.mycalendar.adapter.EventAdapter;
import com.example.mycalendar.adapter.NewFragmentAdapter;

import java.util.ArrayList;

public class AstrologyFragment extends Fragment {
    private String[] titleOfFragment;
    private int[] idPictureOfFragment;
    private RecyclerView astrologyFragment;

    public AstrologyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleOfFragment = getResources().getStringArray(R.array.contentPageAstrology);
        idPictureOfFragment = new int[]{
                R.drawable.feng_shui_2021, R.drawable.feng_shui_life,
                R.drawable.feng_shui_star, R.drawable.feng_shui_east,
                R.drawable.feng_shui_good_day, R.drawable.feng_shui,
                R.drawable.feng_shui_astrology, R.drawable.feng_shui_home,
                R.drawable.feng_shui_good_teller};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_astrology, container, false);
        astrologyFragment = v.findViewById(R.id.astrologyView);

        if (titleOfFragment.length == idPictureOfFragment.length) {
            NewFragmentAdapter eventAdapter = new NewFragmentAdapter(titleOfFragment, idPictureOfFragment,getContext(),3);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            astrologyFragment.setLayoutManager(layoutManager);
            astrologyFragment.setAdapter(eventAdapter);
        }

        return v;
    }
}