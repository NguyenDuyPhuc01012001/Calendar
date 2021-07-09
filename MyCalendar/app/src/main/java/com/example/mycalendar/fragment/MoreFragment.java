package com.example.mycalendar.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mycalendar.R;
import com.example.mycalendar.adapter.NewFragmentAdapter;

public class MoreFragment extends Fragment {
    private String[] titleOfFragment;
    private int[] idPictureOfFragment;
    private RecyclerView moreFragment;

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleOfFragment = getResources().getStringArray(R.array.contentPageMore);
        idPictureOfFragment = new int[]{
                R.drawable.more_weather, R.drawable.more_book,
                R.drawable.more_compass, R.drawable.more_change_day,
                R.drawable.more_history_event , R.drawable.more_guide,
                R.drawable.more_rate, R.drawable.more_share};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_more, container, false);
        moreFragment = v.findViewById(R.id.moreView);

        if (titleOfFragment.length == idPictureOfFragment.length) {
            NewFragmentAdapter eventAdapter = new NewFragmentAdapter(titleOfFragment, idPictureOfFragment,getContext(),4);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            moreFragment.setLayoutManager(layoutManager);
            moreFragment.setAdapter(eventAdapter);
        }
        return v;
    }
}