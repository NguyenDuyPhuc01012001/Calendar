package com.example.mycalendar.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mycalendar.R;
import com.mannan.translateapi.Language;
import com.mannan.translateapi.TranslateAPI;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherFragment extends Fragment {
    private String TAG = "WeatherFragment";
    String city;
    String API = "5a0aeaee604f73e90337988b11cf7ae0";
    String degreeUnit = "metric";
    String url;
    private TextView address;
    private TextView updateAt;
    private TextView status;
    private TextView temperature;
    private TextView minTemperature;
    private TextView maxTemperature;
    private TextView sunriseTime;
    private TextView sunsetTime;
    private TextView windSpeed;
    private TextView pressure;
    private TextView humidity;
    private ImageView img;
    private Button searchBTN;
    private EditText findTXT;
    private String weatherDes;
    private ProgressBar progressBar;
    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_weather, container, false);
        Mapping(v);
        getCurrentWeatherData("SaiGon");
        searchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city;
                if(findTXT.equals(""))
                {
                    city="Hue";
                }
                else
                {
                    city =findTXT.getText().toString();
                }
                getCurrentWeatherData(city);
            }
        });
        return v;
    }
    private void getCurrentWeatherData(String city)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&units="+degreeUnit+"&appid="+API;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response);
                    JSONObject main1 = jsonObject1.getJSONObject("main");
                    JSONObject sys1 = jsonObject1.getJSONObject("sys");
                    JSONObject wind1 = jsonObject1.getJSONObject("wind");
                    JSONObject weather1 = jsonObject1.getJSONArray("weather").getJSONObject(0);
                    Long updatedAt1 = jsonObject1.getLong("dt");
                    String updatedAtText1 = "Cập nhật lúc: "+ new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt1*1000));
                    String temp1 = main1.getString("temp");
                    Double a = Double.valueOf(temp1);
                    temp1= String.valueOf(a.intValue());
                    String tempMin1  = main1.getString("temp_min");

                    Double b = Double.valueOf(tempMin1);
                    tempMin1=String.valueOf(b.intValue());

                    String tempMax1  = main1.getString("temp_max");
                    Double c = Double.valueOf(tempMax1);
                    tempMax1=String.valueOf(c.intValue());
                    String pressure1 = main1.getString("pressure");

                    String humidity1 = main1.getString("humidity");
                    Long sunrise1 = sys1.getLong("sunrise");
                    Long sunset1 = sys1.getLong("sunset");
                    String windSpeed1 = wind1.getString("speed");

                    String weatherDescription1 = weather1.getString("description");
                    TranslateAPI translateAPI = new TranslateAPI(Language.AUTO_DETECT,Language.VIETNAMESE,weatherDescription1);
                    translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
                        @Override
                        public void onSuccess(String translatedText) {
                            weatherDes = translatedText;
                            Log.d(TAG, "onSuccess: "+weatherDes+" "+translatedText);
                        }

                        @Override
                        public void onFailure(String ErrorText) {
                            weatherDes = weatherDescription1;
                        }
                    });
                    String address1 = jsonObject1.getString("name")+", "+sys1.getString("country");
                    String icon =weather1.getString("icon");
                    Log.d("picture code: ", icon);
                    Log.d("ketqua", jsonObject1.toString());
                    address.setText(address1);
                    updateAt.setText(updatedAtText1);
                    String temp = temp1 +"°C";
                    String tempMin = "Min "+tempMin1+"°C";
                    String tempMax = "Max "+tempMax1+"°C";
                    temperature.setText(temp);
                    minTemperature.setText(tempMin);
                    maxTemperature.setText(tempMax);
                    status.setText(weatherDes);
                    windSpeed.setText(windSpeed1);
                    pressure.setText(pressure1);
                    humidity.setText(humidity1);
                    sunriseTime.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise1*1000)));
                    sunsetTime.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset1*1000)));

                    Picasso.with(getActivity()).load("https://openweathermap.org/img/wn/"+icon+".png").into(img);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("MYAPP", "unexpected JSON exception", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
    private JSONObject getWeatherData(String response) throws JSONException {
        return new JSONObject(response);
    }

    private void Mapping(View v)
    {
        address = (TextView) v.findViewById(R.id.address);
        updateAt = (TextView)v.findViewById(R.id.updateAt);
        status = (TextView) v.findViewById(R.id.status);
        temperature = (TextView) v.findViewById(R.id.temperature);
        minTemperature = (TextView)v.findViewById(R.id.minTemperature);
        maxTemperature = (TextView)v.findViewById(R.id.maxTemperature);
        sunriseTime = (TextView)v.findViewById(R.id.sunriseTime);
        sunsetTime = (TextView)v.findViewById(R.id.sunsetTime);
        windSpeed = (TextView)v.findViewById(R.id.windSpeed);
        pressure = (TextView)v.findViewById(R.id.pressure);
        humidity = (TextView)v.findViewById(R.id.humidity);
        searchBTN = (Button)v.findViewById(R.id.search_button);
        img = (ImageView)v.findViewById(R.id.statusIcon);
        findTXT = (EditText)v.findViewById(R.id.findCityTxt);
    }
}