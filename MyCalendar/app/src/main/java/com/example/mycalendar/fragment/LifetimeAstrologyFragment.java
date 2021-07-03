package com.example.mycalendar.fragment;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mycalendar.R;
import com.example.mycalendar.handler.GetAstrologyHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class LifetimeAstrologyFragment extends Fragment {

    private String TAG = "LìfetimeAstrologyFragment";
    List<ImageView>listZodiacIcon;
    private final int zodiacCount = 12;
    ImageView lifeTimeZodiacImg;
    Spinner yearPicker;
    Spinner sexPicker;
    TextView lifeTimeZodiacDescription;
    String sex = "nam";
    String folderName = "lifetime";
    int birthYear;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lifetime_astrology, container,false);
        Mapping(view);
        SetSpinnerValue();
        yearPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                birthYear = Integer.parseInt(parent.getItemAtPosition(position).toString());
                LoadZodiacDescription(birthYear,sex);
                LoadZodiacImage(birthYear);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sexPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex = parent.getItemAtPosition(position).toString();
                if(sex.equals("Nam"))
                {
                    sex = "nam";
                }
                if(sex.equals("Nữ"))
                {
                    sex= "nu";
                }
                LoadZodiacDescription(birthYear,sex);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return  view;
    }


    private void SetSpinnerValue() {
        SetYearPickerValue();
        SetSexPickerValue();
    }

    private void SetYearPickerValue() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.year_picker_array,
                R.layout.spinner_picker_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        yearPicker.setAdapter(adapter);
    }

    private void SetSexPickerValue() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.sex_picker_array,
                R.layout.spinner_picker_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sexPicker.setAdapter(adapter);
    }

    private void Mapping(View view ) {
        yearPicker = view.findViewById(R.id.yearPicker);
        sexPicker = view.findViewById(R.id.sexPicker);
        lifeTimeZodiacImg = view.findViewById(R.id.lifeTimeEasternZodiacImage);
        lifeTimeZodiacDescription = view.findViewById(R.id.lifeTimeEasternZodiacDescriptionTV);
    }
    private void LoadZodiacDescription(int birthYear,String sex)
    {
        GetAstrologyHandler getAstrologyHandler = new GetAstrologyHandler();
        String fileName =getAstrologyHandler.GetFileName(birthYear)+"_"+sex+".txt";
        String fileLocation = folderName+"/"+fileName;
        Log.d(TAG, "LoadZodiacDescription: "+fileLocation);
        try {
            InputStream inputStream = getContext().getAssets().open(fileLocation);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String data = "";
            if(inputStream!=null)
            {
                while((data=bufferedReader.readLine())!=null)
                {
                    stringBuffer.append(data+"\n");
                }
                lifeTimeZodiacDescription.setText(stringBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void LoadZodiacImage(int birthYear) {
        //con giáp sắp xếp theo thứ tự:
        //Thân-Dậu-Tuất-Hợi-Tý-Sửu-Dần-Mão-Thìn-Tỵ-Ngọ-Mùi
        int imageCode = birthYear % zodiacCount;
        if(imageCode == 0)
        {
            lifeTimeZodiacImg.setImageResource(R.drawable.than);
        }
        if(imageCode == 1)
        {
            lifeTimeZodiacImg.setImageResource(R.drawable.dau);
        }
        if(imageCode == 2)
        {
            lifeTimeZodiacImg.setImageResource(R.drawable.tuat);
        }
        if(imageCode == 3)
        {
            lifeTimeZodiacImg.setImageResource(R.drawable.hoi);
        }
        if(imageCode == 4)
        {
            lifeTimeZodiacImg.setImageResource(R.drawable.ty);
        }
        if(imageCode == 5)
        {
            lifeTimeZodiacImg.setImageResource(R.drawable.suu);
        }
        if(imageCode == 6)
        {
            lifeTimeZodiacImg.setImageResource(R.drawable.dan);
        }
        if(imageCode == 7)
        {
            lifeTimeZodiacImg.setImageResource(R.drawable.mao);
        }
        if(imageCode == 8)
        {
            lifeTimeZodiacImg.setImageResource(R.drawable.thin);
        }
        if(imageCode == 9)
        {
            lifeTimeZodiacImg.setImageResource(R.drawable.ty);
        }
        if(imageCode == 10)
        {
            lifeTimeZodiacImg.setImageResource(R.drawable.ngo);
        }
        if(imageCode == 11)
        {
            lifeTimeZodiacImg.setImageResource(R.drawable.mui);
        }
    }
}
