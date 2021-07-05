package com.example.mycalendar.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycalendar.AlertDirectedDialog;
import com.example.mycalendar.R;
import com.example.mycalendar.fragment.Astrology2021Fragment;
import com.example.mycalendar.fragment.ChangeDateFragment;
import com.example.mycalendar.fragment.CompassFragment;
import com.example.mycalendar.fragment.DreamInterpretationFragment;
import com.example.mycalendar.fragment.HistoryEventFragment;
import com.example.mycalendar.fragment.LifetimeAstrologyFragment;
import com.example.mycalendar.fragment.MoreFragment;
import com.example.mycalendar.fragment.PrayerFragment;
import com.example.mycalendar.fragment.WeatherFragment;
import com.example.mycalendar.fragment.ZodiacFragment;

import org.jetbrains.annotations.NotNull;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class NewFragmentAdapter extends RecyclerView.Adapter<NewFragmentAdapter.NewFragmentViewHolder>{
    private String TAG = "NewFragmentAdapter";
    private final String[] titleOfFragment;
    private final int[] idPictureOfFragment;

    public NewFragmentAdapter(String[] titleOfFragment, int[] idPictureOfFragment, Context context, int tabPosition) {
        this.titleOfFragment = titleOfFragment;
        this.idPictureOfFragment = idPictureOfFragment;
        this.context = context;
        this.tabPosition = tabPosition;
    }

    private Context context;

    public NewFragmentAdapter(String[] titleOfFragment, int[] idPictureOfFragment, int tabPosition) {
        this.titleOfFragment = titleOfFragment;
        this.idPictureOfFragment = idPictureOfFragment;
        this.tabPosition = tabPosition;     //3 is tab Zodiac; 4 is tab More
    }

    private int tabPosition;

    public NewFragmentAdapter(String[] titleOfFragment, int[] idPictureOfFragment) {
        this.titleOfFragment = titleOfFragment;
        this.idPictureOfFragment = idPictureOfFragment;
    }

    @NonNull
    @NotNull
    @Override
    public NewFragmentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.new_fragment_cell, parent, false);
        NewFragmentViewHolder viewHolder = new NewFragmentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NewFragmentAdapter.NewFragmentViewHolder holder, int position) {
        int id_img=idPictureOfFragment[position];
        String title = titleOfFragment[position];

        holder.img.setImageResource(id_img);
        holder.title.setText(title);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int adapterPosition) {
                switch (tabPosition){
                    case 3:
                        LoadFragmentInAstrology(position);
                        break;
                    case 4:
                        LoadFragmentInMore(position);
                        break;
                }
            }
        });
    }

    private void LoadFragmentInMore(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new WeatherFragment();
                LoadFragment(R.id.moreContainer,fragment);
                break;
            case 1:
                fragment = new PrayerFragment();
                LoadFragment(R.id.moreContainer,fragment);
                break;
            case 2:
                fragment = new CompassFragment();
                LoadFragment(R.id.moreContainer,fragment);
                break;
            case 3:
                fragment = new ChangeDateFragment();
                LoadFragment(R.id.moreContainer,fragment);
                break;
            case 4:
                break;
            case 5:
                fragment = new HistoryEventFragment();
                LoadFragment(R.id.moreContainer,fragment);
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                LoadDialog();
                break;
            case 9:
                break;
        }
    }

    private void LoadFragmentInAstrology(int position) {
        Fragment fragment;
        switch (position){
            case 0:
                fragment = new Astrology2021Fragment();
                LoadFragment(R.id.astrologyContainer,fragment);
                break;
            case 1:
                fragment = new LifetimeAstrologyFragment();
                LoadFragment(R.id.astrologyContainer,fragment);
                break;
            case 2:
                fragment = new DreamInterpretationFragment();
                LoadFragment(R.id.astrologyContainer,fragment);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                fragment = new ZodiacFragment();
                LoadFragment(R.id.astrologyContainer,fragment);
                break;
            case 7:
                break;
            case 8:
                break;
        }
    }

    private void LoadFragment(int container, Fragment fragment) {
        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        fragmentTransaction.add(container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void LoadDialog()
    {
        FragmentManager mine = ((AppCompatActivity)context).getSupportFragmentManager();
        AlertDirectedDialog alertDirectedDialog = new AlertDirectedDialog();
        alertDirectedDialog.show(mine,"dialog");
        
    }
    @Override
    public int getItemCount() {
        return titleOfFragment.length;
    }

    public class NewFragmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CardView new_fragment_cell;
        private ImageView img;
        private TextView title;
        private ItemClickListener itemClickListener;
        public NewFragmentViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            new_fragment_cell=itemView.findViewById(R.id.newFragmentCell);
            img=itemView.findViewById(R.id.imgIconOfFragment);
            title=itemView.findViewById(R.id.titleOfFragment);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition());
        }

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }
    }

    private interface ItemClickListener{
        void onClick(View v, int adapterPosition);
    }
}
