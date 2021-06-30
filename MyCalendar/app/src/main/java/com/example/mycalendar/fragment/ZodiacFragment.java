package com.example.mycalendar.fragment;

import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mycalendar.R;
import com.mannan.translateapi.Language;
import com.mannan.translateapi.TranslateAPI;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class ZodiacFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner zodiacName;
    String zodiacInEnglish;
    TextView todayTextView;
    TextView zodiacRange;
    Button confirmButton;
    private String TAG = "ZodiacFragment";
    ImageView zodiacImg;

    TextView colorSuitTV;
    TextView zodiacSuitTV;
    TextView descriptionTV;
    TextView luckyNumberTV;
    TextView luckyTimeTV;
    TextView moodTV;
    JSONObject allZodiacData;
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
                GetZodiacAstrology();
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
        colorSuitTV = view.findViewById(R.id.colorSuitTV);
        zodiacSuitTV = view.findViewById(R.id.compatZodiacTV);
        descriptionTV = view.findViewById(R.id.descriptionZodiacTV);
        luckyNumberTV = view.findViewById(R.id.luckyNumberTV);
        luckyTimeTV = view.findViewById(R.id.luckyTimeTV);
        moodTV=  view.findViewById(R.id.moodTV);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String zodiacInVietnamese = parent.getItemAtPosition(position).toString();
            TranslateAPI translateAPI = new TranslateAPI(Language.VIETNAMESE, Language.ENGLISH, zodiacInVietnamese);
            translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
                @Override
                public void onSuccess(String translatedText) {
                    zodiacInEnglish = translatedText;
                    if(zodiacInEnglish.equals("Scorpion"))
                    {
                        zodiacInEnglish = "Scorpio";
                    }
                    if(zodiacInEnglish.equals("Lion"))
                    {
                        zodiacInEnglish = "Leo";
                    }
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
        GetZodiacAstrology();
        SetImageZodiac(zodiacInEnglish);
        SetZodiacDateRange(zodiacInEnglish);
    }

    private void SetChoices(Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.zodiac_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void GetZodiacAstrology() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://sameer-kumar-aztro-v1.p.rapidapi.com/?sign="+zodiacInEnglish+"&day=today")
                        .post(new RequestBody() {
                            @Nullable
                            @Override
                            public MediaType contentType() {
                                return null;
                            }

                            @Override
                            public void writeTo(@NotNull BufferedSink bufferedSink) throws IOException {

                            }
                        })
                        .addHeader("x-rapidapi-key", "f5a4c5c119msh71384052eab298fp1b44a4jsnba3e50d75874")
                        .addHeader("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    allZodiacData = new JSONObject(response.body().string());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "run: "+allZodiacData.toString());
                            try {
                                TranslateToColorVietnamese(allZodiacData.getString("color"));
                                TranslateToDescriptionVietnamese(allZodiacData.getString("description"));
                                TranslateToCompatibilityVietnamese(allZodiacData.getString("compatibility"));
                                luckyNumberTV.setText("Số may mắn: "+allZodiacData.getString("lucky_number"));
                                luckyTimeTV.setText("Giờ hoàng đạo: "+allZodiacData.getString("lucky_time"));
                                TranslateToMoodVietnamese(allZodiacData.getString("mood"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });

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
        if (zodiacName.equals("Leo")) {
            zodiacImg.setImageResource(R.drawable.zodiac_leo);
        }
        if (zodiacName.equals("Virgo")) {
            zodiacImg.setImageResource(R.drawable.zodiac_virgo);
        }
        if (zodiacName.equals("Libra")) {
            zodiacImg.setImageResource(R.drawable.zodiac_libra);
        }
        if (zodiacName.equals("Scorpio")) {
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
        } else if (zodiacName.equals("Leo")) {
            zodiacRange.setText("23/7 - 22/8");
        } else if (zodiacName.equals("Virgo")) {
            zodiacRange.setText("23/8 - 22/9");
        } else if (zodiacName.equals("Libra")) {
            zodiacRange.setText("23/9 - 22/10");
        } else if (zodiacName.equals("Scorpio")) {
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

    private void TranslateToDescriptionVietnamese(String description) {

        TranslateAPI translateAPI = new TranslateAPI(
                Language.AUTO_DETECT,
                Language.VIETNAMESE,
                description);
        translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
            @Override
            public void onSuccess(String translatedText) {
                Log.d("translated", translatedText);
                descriptionTV.setText("Mô tả ngày của bạn: " + translatedText);
            }

            @Override
            public void onFailure(String ErrorText) {

            }
        });
    }
    private void TranslateToColorVietnamese(String color) {

        TranslateAPI translateAPI = new TranslateAPI(
                Language.AUTO_DETECT,
                Language.VIETNAMESE,
                color);
        translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
            @Override
            public void onSuccess(String translatedText) {
                Log.d("translated", translatedText);
                colorSuitTV.setText("Màu may mắn: " + translatedText);
            }

            @Override
            public void onFailure(String ErrorText) {

            }
        });
    }
    private void TranslateToCompatibilityVietnamese(String compatibility) {

        TranslateAPI translateAPI = new TranslateAPI(
                Language.AUTO_DETECT,
                Language.VIETNAMESE,
                compatibility);
        translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
            @Override
            public void onSuccess(String translatedText) {
                Log.d("translated", translatedText);
                if(translatedText.equals("Ung thư"))
                {
                    translatedText = "Cự Giải";
                }
                zodiacSuitTV.setText("Cung hoàng đạo thích hợp: " + translatedText);
            }

            @Override
            public void onFailure(String ErrorText) {

            }
        });
    }
    private void TranslateToMoodVietnamese(String mood) {

        TranslateAPI translateAPI = new TranslateAPI(
                Language.AUTO_DETECT,
                Language.VIETNAMESE,
                mood);
        translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
            @Override
            public void onSuccess(String translatedText) {
                Log.d("translated", translatedText);
                moodTV.setText("Tâm trạng: " + translatedText);
            }

            @Override
            public void onFailure(String ErrorText) {

            }
        });
    }
}