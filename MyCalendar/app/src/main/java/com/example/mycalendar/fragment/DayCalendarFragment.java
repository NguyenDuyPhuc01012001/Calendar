package com.example.mycalendar.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.mycalendar.dialog.BottomDialog;
import com.example.mycalendar.OnSwipeTouchListener;
import com.example.mycalendar.R;
import com.example.mycalendar.database.MaximDatabase;
import com.example.mycalendar.model.DateTimeInfo;
import com.example.mycalendar.presenter.DayCalendarInterface;
import com.example.mycalendar.presenter.DayCalendarPresenter;

import java.time.LocalDateTime;

public class DayCalendarFragment extends Fragment implements DayCalendarInterface ,BottomDialog.OnSelected{
    public static final String TAG="DayCalendarFragment";

    private LocalDateTime selectedDate = null;
    private DayCalendarPresenter mDCPresenter;
    private RelativeLayout containerLayout;
    private ImageButton btnToday;

    private TextView tvMonthYearSolar,tvDayOfWeek,tvDayOfMonth;
    private TextView tvDayLunar,tvMonthLunar;
    private TextView tvStrDayLunar,tvStrMonthLunar,tvStrYearLunar,tvStrTimeLunar;
    private TextView tvMaxim;

    private ImageView imgIsGoodDay,imgIsGoodTime;
    private ImageSwitcher imageSwitcher;
    private Integer ListWallpaper[]={R.drawable.wallpaper_pic1,R.drawable.wallpaper_pic2,
            R.drawable.wallpaper_pic3,R.drawable.wallpaper_pic4,
            R.drawable.wallpaper_pic5,R.drawable.wallpaper_pic6,
            R.drawable.wallpaper_pic7,R.drawable.wallpaper_pic8,
            R.drawable.wallpaper_pic9,R.drawable.wallpaper_pic10,
            R.drawable.wallpaper_pic11,R.drawable.wallpaper_pic12,
            R.drawable.wallpaper_pic13};
    private Integer lengthListWallpaper=ListWallpaper.length;
    private Integer counter=0;

    private MaximDatabase db;

    public DayCalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_day_calendar, container, false);
        Init(v);
        setEvent();
        LoadData(selectedDate);
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Init(View v) {
        selectedDate = LocalDateTime.now();
        containerLayout = v.findViewById(R.id.dayCalendarContainer);
        tvMonthYearSolar=v.findViewById(R.id.tvMonthYearSolar);
        tvDayOfWeek=v.findViewById(R.id.tvDayOfWeek);
        tvDayOfMonth=v.findViewById(R.id.tvDayOfMonthSolar);
        tvDayLunar=v.findViewById(R.id.tvDayOfMonthLunar) ;
        tvMonthLunar=v.findViewById(R.id.tvMonthLunar);
        tvStrDayLunar=v.findViewById(R.id.tvStrDayLunar);
        tvStrMonthLunar=v.findViewById(R.id.tvStrMonthLunar);
        tvStrYearLunar=v.findViewById(R.id.tvStrYearLunar);
        tvStrTimeLunar=v.findViewById(R.id.tvTimeLunar);
        tvMaxim=v.findViewById(R.id.tvMaxim);
        imageSwitcher=v.findViewById(R.id.imgWallpaper);
        btnToday=v.findViewById(R.id.btnToday);
        imgIsGoodDay=v.findViewById(R.id.imgIsGoodDay);
        imgIsGoodTime=v.findViewById(R.id.imgIsGoodTime);
        db=new MaximDatabase(getContext());
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setEvent() {
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView=new ImageView(getActivity());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageResource(R.drawable.wallpaper_pic1);
                return imageView;
            }
        });

        containerLayout.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeRight() {
                Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
                Animation out = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);

                imageSwitcher.setOutAnimation(out);
                imageSwitcher.setInAnimation(in);

                LoadWallpaper(false);
                selectedDate = selectedDate.minusDays(1);
                LoadData(selectedDate);
            }

            public void onSwipeLeft() {
                Animation in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
                Animation out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left);

                imageSwitcher.setOutAnimation(out);
                imageSwitcher.setInAnimation(in);

                LoadWallpaper(true);
                selectedDate = selectedDate.plusDays(1);
                LoadData(selectedDate);
            }

            public void onClick(){
                DayDetailFragment dayDetailFragment = new DayDetailFragment(selectedDate);
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.dayCalendarContainer, dayDetailFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadWallpaper(true);
                selectedDate=LocalDateTime.now();
                LoadData(selectedDate);
            }
        });

        tvMonthYearSolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();
            }
        });
    }

    private void ShowDialog() {
        FragmentManager mine = getActivity().getSupportFragmentManager();
        BottomDialog dialogFragment = new BottomDialog(this);
        dialogFragment.show(mine, BottomDialog.TAG);
    }

    private void LoadWallpaper(boolean isPlusDay) {
        if(isPlusDay)
            counter++;
        else
            counter--;

        if(counter==lengthListWallpaper){
            counter=0;
            imageSwitcher.setImageResource(ListWallpaper[counter]);
        }
        else if(counter<0){
            counter=lengthListWallpaper;
            imageSwitcher.setImageResource(ListWallpaper[counter]);
        }
        else
            imageSwitcher.setImageResource(ListWallpaper[counter]);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void LoadData(LocalDateTime selectedDate) {
        mDCPresenter = new DayCalendarPresenter(this);

        mDCPresenter.GetData(selectedDate);
        mDCPresenter.GetMaxim(db);
        mDCPresenter.ShowBTN(selectedDate);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void LoadData(DateTimeInfo dateTimeInfo) {
        String monthYear="Tháng "+dateTimeInfo.getMonthSolar()+" - "+dateTimeInfo.getYearSolar();
        tvMonthYearSolar.setText(monthYear);
        tvDayOfMonth.setText(Integer.valueOf(dateTimeInfo.getDayOfMonthSolar()).toString());
        tvDayOfWeek.setText(dateTimeInfo.getDayOfWeek().toUpperCase());
        tvDayLunar.setText(Integer.valueOf(dateTimeInfo.getDayOfMonthLunar()).toString());
        tvMonthLunar.setText("Tháng "+dateTimeInfo.getMonthLunar());
        tvStrDayLunar.setText("Ng. "+dateTimeInfo.getStrDayOfMonthLunar());
        tvStrMonthLunar.setText("Th. "+dateTimeInfo.getStrMonthLunar());
        tvStrYearLunar.setText("Năm "+dateTimeInfo.getStrYearLunar());
        tvStrTimeLunar.setText(dateTimeInfo.getTimeLunar());

        if(dateTimeInfo.getIsGoodDay().equals("Good")){
            imgIsGoodDay.setVisibility(View.VISIBLE);
            imgIsGoodDay.setImageResource(R.mipmap.yin_yang_red);
        }
        if(dateTimeInfo.getIsGoodDay().equals("Normal"))
            imgIsGoodDay.setVisibility(View.GONE);
        if(dateTimeInfo.getIsGoodDay().equals("Bad")){
            imgIsGoodDay.setVisibility(View.VISIBLE);
            imgIsGoodDay.setImageResource(R.mipmap.yin_yang_black);
        }
        if(dateTimeInfo.getIsGoodTime())
            imgIsGoodTime.setImageResource(R.mipmap.yin_yang_red);
        else
            imgIsGoodTime.setImageResource(R.mipmap.yin_yang_black);
    }

    @Override
    public void LoadMaxim(String maxim) {
        tvMaxim.setText(maxim);
    }

    @Override
    public void SetBTNVisibility(boolean isToday) {
        if(isToday)
            btnToday.setVisibility(View.INVISIBLE);
        else
            btnToday.setVisibility(View.VISIBLE);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void LoadNewDate(LocalDateTime selectedDate){
        this.selectedDate=selectedDate;
        LoadData(this.selectedDate);
    }
}