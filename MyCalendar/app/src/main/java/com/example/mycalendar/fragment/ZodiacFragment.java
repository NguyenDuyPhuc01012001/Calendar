package com.example.mycalendar.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mycalendar.R;
import com.mannan.translateapi.Language;
import com.mannan.translateapi.TranslateAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ZodiacFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner zodiacName;
    TextView todayTextView;
    TextView zodiacRange;
    Button confirmButton;
    TextView description;
    String zodiacInEnglish;
    ImageView zodiacImg;
    String url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_zodiac, container, false);
        Mapping(view);
        SetChoices(zodiacName);
        todayTextView.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetZodiacAstrology(zodiacInEnglish);
            }
        });
        return view;
    }

    private void Mapping(View view) {
        todayTextView = view.findViewById(R.id.todayTextview);
        zodiacImg = view.findViewById(R.id.zodiacIcon);
        zodiacName = view.findViewById(R.id.zodiacName);
        zodiacName.setOnItemSelectedListener(this);
        zodiacRange = view.findViewById(R.id.zodiacDateRange);
        confirmButton = view.findViewById(R.id.get_astrology_button);
        description = view.findViewById(R.id.descriptionTextview);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String zodiacInVietnamese = parent.getItemAtPosition(position).toString();
        TranslateAPI translateAPI = new TranslateAPI(Language.VIETNAMESE, Language.ENGLISH, zodiacInVietnamese);
        translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
            @Override
            public void onSuccess(String translatedText) {
                zodiacInEnglish = translatedText;
                Log.d("zodiac name = ", zodiacInEnglish);
                SetImageZodiac(zodiacInEnglish);
                SetZodiacDateRange(zodiacInEnglish);
            }

            @Override
            public void onFailure(String ErrorText) {
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        zodiacInEnglish = "Aries";
        GetZodiacAstrology(zodiacInEnglish);
        SetImageZodiac(zodiacInEnglish);
        SetZodiacDateRange(zodiacInEnglish);
    }

    private void SetChoices(Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.zodiac_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void GetZodiacAstrology(String zodiacName) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        if (zodiacName.equals("Lion")) {
            url = "https://horoscope-api.herokuapp.com/horoscope/today/Leo";
        } else if (zodiacName.equals("Scorpion")) {
            url = "https://horoscope-api.herokuapp.com/horoscope/today/Scorpio";
        } else
            url = "https://horoscope-api.herokuapp.com/horoscope/today/" + zodiacName;
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String day = jsonObject.getString("date");
                    String horoscope = jsonObject.getString("horoscope");

                    String[] getSplitDay = day.split("-");
                    todayTextView.setText(getSplitDay[2] + "-" + getSplitDay[1] + "-" + getSplitDay[0]);

                    TranslateToVietnamese(horoscope);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void SetImageZodiac(String zodiacName) {
        if (zodiacName.equals("Aries")) {
            zodiacImg.setImageResource(R.drawable.zodiac_aries);
        }
        if (zodiacName.equals("Taurus")) {
            zodiacImg.setImageResource(R.drawable.zodiac_taurus);
        }
        if (zodiacName.equals("Gemini")) {
            zodiacImg.setImageResource(R.drawable.zodiac_gemini);
        }
        if (zodiacName.equals("Cancer")) {
            zodiacImg.setImageResource(R.drawable.zodiac_cancer);
        }
        if (zodiacName.equals("Lion")) {
            zodiacImg.setImageResource(R.drawable.zodiac_leo);
        }
        if (zodiacName.equals("Virgo")) {
            zodiacImg.setImageResource(R.drawable.zodiac_virgo);
        }
        if (zodiacName.equals("Libra")) {
            zodiacImg.setImageResource(R.drawable.zodiac_libra);
        }
        if (zodiacName.equals("Scorpion")) {
            zodiacImg.setImageResource(R.drawable.zodiac_scorpio);
        }
        if (zodiacName.equals("Sagittarius")) {
            zodiacImg.setImageResource(R.drawable.zodiac_sagittarius);
        }
        if (zodiacName.equals("Capricorn")) {
            zodiacImg.setImageResource(R.drawable.zodiac_capricorn);
        }
        if (zodiacName.equals("Aquarius")) {
            zodiacImg.setImageResource(R.drawable.zodiac_aquarium);
        }
        if (zodiacName.equals("Pisces")) {
            zodiacImg.setImageResource(R.drawable.zodiac_pisces);
        }
    }

    private void SetZodiacDateRange(String zodiacName) {
        if (zodiacName.equals("Aries")) {
            zodiacRange.setText("21/3 - 20/4");
        } else if (zodiacName.equals("Taurus")) {
            zodiacRange.setText("21/4 - 20/5");
        } else if (zodiacName.equals("Gemini")) {
            zodiacRange.setText("21/5 - 20/6");
        } else if (zodiacName.equals("Cancer")) {
            zodiacRange.setText("21/6 - 22/7");
        } else if (zodiacName.equals("Lion")) {
            zodiacRange.setText("23/7 - 22/8");
        } else if (zodiacName.equals("Virgo")) {
            zodiacRange.setText("23/8 - 22/9");
        } else if (zodiacName.equals("Libra")) {
            zodiacRange.setText("23/9 - 22/10");
        } else if (zodiacName.equals("Scorpion")) {
            zodiacRange.setText("23/10 - 22/11");
        } else if (zodiacName.equals("Sagittarius")) {
            zodiacRange.setText("23/11 - 21/12");
        } else if (zodiacName.equals("Capricorn")) {
            zodiacRange.setText("22/12 - 19/1");
        } else if (zodiacName.equals("Aquarius")) {
            zodiacRange.setText("20/1 - 19/2");
        } else if (zodiacName.equals("Pisces")) {
            zodiacRange.setText("20/2 - 20/3");
        } else {
            zodiacRange.setText("error");
        }
    }

    private void TranslateToVietnamese(String horoscope) {

        TranslateAPI translateAPI = new TranslateAPI(
                Language.AUTO_DETECT,
                Language.VIETNAMESE,
                horoscope);
        translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
            @Override
            public void onSuccess(String translatedText) {
                Log.d("translated", translatedText);
                description.setText("Mô tả ngày của bạn: " + translatedText);
            }

            @Override
            public void onFailure(String ErrorText) {

            }
        });
    }
}