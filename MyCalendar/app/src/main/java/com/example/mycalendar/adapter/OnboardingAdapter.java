package com.example.mycalendar.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycalendar.R;
import com.example.mycalendar.model.OnboardingItem;

import java.util.List;

public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private List<OnboardingItem> onboardingItems;

    public OnboardingAdapter(List<OnboardingItem> onboardingItems) {
        this.onboardingItems = onboardingItems;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new OnboardingViewHolder(
            LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_guide,parent,false)
    );
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
    holder.setOnboardingData(onboardingItems.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingItems.size();
    }

    class OnboardingViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageOnboarding;

        OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageOnboarding = imageOnboarding;
            imageOnboarding = itemView.findViewById(R.id.imageOnboarding);
        }


        void setOnboardingData(OnboardingItem onboardingItem)
        {
            imageOnboarding.setImageResource(onboardingItem.getImage());
        }
    }
}
