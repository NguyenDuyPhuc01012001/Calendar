package com.example.mycalendar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycalendar.R;
import com.example.mycalendar.handler.GetAstrologyHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Astrology2021Fragment extends Fragment {
    Spinner astrology2021YearPicker;
    Spinner astrology2021SexPicker;
    ImageView astrology2021ZodiacImage;
    TextView astrology2021Description;
    int zodiacCount = 12;
    int birthYear;
    String sex;
    String folderName = "astrology2021";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_astrology_2021,container,false);
        Mapping(view);
        SetSpinnerValue();
        astrology2021YearPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        astrology2021SexPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sexValue = parent.getItemAtPosition(position).toString();
                if(sexValue.equals("Nam"))
                {
                    sex = "nam";
                }
                else
                {
                    sex = "nu";
                }
                LoadZodiacDescription(birthYear,sex);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;

    }


    private void LoadZodiacDescription(int birthYear, String sex) {
        GetAstrologyHandler getAstrologyHandler = new GetAstrologyHandler();
        String fileName = getAstrologyHandler.GetFileName(birthYear)+"_"+sex+".txt";
        String fileLocation = folderName+"/"+fileName;
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
                astrology2021Description.setText(stringBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void SetSpinnerValue() {
        SetYearPickerValue();
        SetSexPickerValue();
    }

    private void SetSexPickerValue() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.sex_picker_array,R.layout.spinner_picker_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        astrology2021SexPicker.setAdapter(adapter);
    }

    private void SetYearPickerValue() {
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.year_picker_array,R.layout.spinner_picker_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        astrology2021YearPicker.setAdapter(adapter);
    }

    private void Mapping(View view) {
        astrology2021YearPicker = view.findViewById(R.id.astrology2021YearPicker);
        astrology2021SexPicker = view.findViewById(R.id.astrology2021SexPicker);
        astrology2021Description = view.findViewById(R.id.astrology2021Description);
        astrology2021ZodiacImage = view.findViewById(R.id.zodiacImage);

    }
    private void LoadZodiacImage(int birthYear) {
        //con giáp sắp xếp theo thứ tự:
        //Thân-Dậu-Tuất-Hợi-Tý-Sửu-Dần-Mão-Thìn-Tỵ-Ngọ-Mùi
        int imageCode = birthYear % zodiacCount;
        if(imageCode == 0)
        {
            astrology2021ZodiacImage.setImageResource(R.drawable.than);
        }
        if(imageCode == 1)
        {
            astrology2021ZodiacImage.setImageResource(R.drawable.dau);
        }
        if(imageCode == 2)
        {
            astrology2021ZodiacImage.setImageResource(R.drawable.tuat);
        }
        if(imageCode == 3)
        {
            astrology2021ZodiacImage.setImageResource(R.drawable.hoi);
        }
        if(imageCode == 4)
        {
            astrology2021ZodiacImage.setImageResource(R.drawable.ty);
        }
        if(imageCode == 5)
        {
            astrology2021ZodiacImage.setImageResource(R.drawable.suu);
        }
        if(imageCode == 6)
        {
            astrology2021ZodiacImage.setImageResource(R.drawable.dan);
        }
        if(imageCode == 7)
        {
            astrology2021ZodiacImage.setImageResource(R.drawable.mao);
        }
        if(imageCode == 8)
        {
            astrology2021ZodiacImage.setImageResource(R.drawable.thin);
        }
        if(imageCode == 9)
        {
            astrology2021ZodiacImage.setImageResource(R.drawable.ty);
        }
        if(imageCode == 10)
        {
            astrology2021ZodiacImage.setImageResource(R.drawable.ngo);
        }
        if(imageCode == 11)
        {
            astrology2021ZodiacImage.setImageResource(R.drawable.mui);
        }
    }
}
