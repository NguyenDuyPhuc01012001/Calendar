package com.example.mycalendar.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mycalendar.R;
import com.example.mycalendar.adapter.OnboardingAdapter;
import com.example.mycalendar.model.OnboardingItem;

import java.util.ArrayList;
import java.util.List;

public class GuideFragment extends Fragment {
    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private Button btnGuideAction;
    private ViewPager2 onboardingViewPager;

    public GuideFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_guide, container, false);
        Init(v);
        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);
        setEvent();
        return v;
    }

    private void Init(View v) {
        layoutOnboardingIndicators = v.findViewById(R.id.layoutOnboardIndicator);
        btnGuideAction = v.findViewById(R.id.buttonOnboard);
        onboardingViewPager = v.findViewById(R.id.onboardViewPager);
        setupOnboardingItems();
        onboardingViewPager.setAdapter(onboardingAdapter);
    }

    private void setEvent() {
        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });
        btnGuideAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onboardingViewPager.getCurrentItem()+1 < onboardingAdapter.getItemCount()){
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem()+1);
                }else{
                    getActivity().onBackPressed();
                }
            }
        });
    }

    private void setupOnboardingItems(){
        List<OnboardingItem> onboardingItems = new ArrayList<>();
        //1
        OnboardingItem itemFeature1 = new OnboardingItem();
        itemFeature1.setImage(R.drawable.guide_daycalendar);
        //2
        OnboardingItem itemFeature2 = new OnboardingItem();
        itemFeature2.setImage(R.drawable.guide_monthcalendar);
        //3
        OnboardingItem itemFeature3 = new OnboardingItem();
        itemFeature3.setImage(R.drawable.guide_addevent);
        //4
        OnboardingItem itemFeature4 = new OnboardingItem();
        itemFeature4.setImage(R.drawable.guide_astrology2021);
        //5
        OnboardingItem itemFeature5 = new OnboardingItem();
        itemFeature5.setImage(R.drawable.guide_astrologylife);
        //6
//        OnboardingItem itemFeature6 = new OnboardingItem();
//        itemFeature6.setImage(R.drawable.guide_dream);
        //7
        OnboardingItem itemFeature7 = new OnboardingItem();
        itemFeature7.setImage(R.drawable.guide_zodiac);
        //8
        OnboardingItem itemFeature8 = new OnboardingItem();
        itemFeature8.setImage(R.drawable.guide_weather);
        //9
        OnboardingItem itemFeature9 = new OnboardingItem();
        itemFeature9.setImage(R.drawable.guide_changeday);
        //add item
        onboardingItems.add(itemFeature1);
        onboardingItems.add(itemFeature2);
        onboardingItems.add(itemFeature3);
        onboardingItems.add(itemFeature4);
        onboardingItems.add(itemFeature5);
//        onboardingItems.add(itemFeature6);
        onboardingItems.add(itemFeature7);
        onboardingItems.add(itemFeature8);
        onboardingItems.add(itemFeature9);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);

    }

    private void setupOnboardingIndicators(){
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0 ,8,0);
        for(int i = 0;i < indicators.length;i++){
            indicators[i]=new ImageView(getContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getContext(),
                    R.drawable.onboard_indicate_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }

    private  void  setCurrentOnboardingIndicator( int index){
        int childCount = layoutOnboardingIndicators.getChildCount();
        for ( int i=0 ; i<childCount ; i++){
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if (i==index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getContext(), R.drawable.onboard_indicate_active)
                );
            }else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getContext(), R.drawable.onboard_indicate_inactive)
                );
            }
        }
        if (index==onboardingAdapter.getItemCount()-1){
            btnGuideAction.setText("Xong");
        }else {
            btnGuideAction.setText("Tiếp");
        }
    }
}