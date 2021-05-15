package com.example.mycalendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {
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
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Mapping();
        getCurrentWeatherData("Quebec");
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
    }

    private void getCurrentWeatherData(String city)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(WeatherActivity.this);

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
                    String updatedAtText1 = "Updated At: "+ new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt1*1000));
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
                    status.setText(weatherDescription1);
                    windSpeed.setText(windSpeed1);
                    pressure.setText(pressure1);
                    humidity.setText(humidity1);
                    sunriseTime.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise1*1000)));
                    sunsetTime.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset1*1000)));

                    Picasso.with(WeatherActivity.this).load("https://openweathermap.org/img/wn/"+icon+".png").into(img);
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

    private void Mapping()
    {
        address = (TextView) findViewById(R.id.address);
        updateAt = (TextView)findViewById(R.id.updateAt);
        status = (TextView) findViewById(R.id.status);
        temperature = (TextView) findViewById(R.id.temperature);
        minTemperature = (TextView)findViewById(R.id.minTemperature);
        maxTemperature = (TextView)findViewById(R.id.maxTemperature);
        sunriseTime = (TextView)findViewById(R.id.sunriseTime);
        sunsetTime = (TextView)findViewById(R.id.sunsetTime);
        windSpeed = (TextView)findViewById(R.id.windSpeed);
        pressure = (TextView)findViewById(R.id.pressure);
        humidity = (TextView)findViewById(R.id.humidity);
        searchBTN = (Button)findViewById(R.id.search_button);
        img = (ImageView)findViewById(R.id.statusIcon);
        findTXT = (EditText)findViewById(R.id.findCityTxt);

    }
}