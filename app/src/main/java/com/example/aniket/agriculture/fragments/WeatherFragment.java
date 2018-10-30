package com.example.aniket.agriculture.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aniket.agriculture.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherFragment extends android.support.v4.app.Fragment {

    TextView dateTextView;
    TextView placeTextView;
    TextView temperatureTextView;
    ImageView weatherImageView;
    TextView descTextView;
    TextView humidityTextView;
    TextView windTextView;
    LinearLayout weatherLayout;

    int[] weatherBackground = {R.drawable.sunny,R.drawable.background_weather};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        findWeather();
        return inflater.inflate(R.layout.fragment_weather,container,false);
    }

    public void findWeather(){
        String url = "https://api.openweathermap.org/data/2.5/weather?q=pune,india&appid=8a97caffdb8d3d687b8c8f457d83ca24&units=Imperial";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
        try {

            JSONObject jsonObject = response.getJSONObject("main");
            JSONObject jsonObject1 = response.getJSONObject("wind");
            JSONArray jsonArray = response.getJSONArray("weather");
            JSONObject object = jsonArray.getJSONObject(0);
            String desc = object.getString("description");
            String temperature = String.valueOf(jsonObject.getDouble("temp"));
            String placeName = response.getString("name");
            String humidity = String.valueOf(jsonObject.getInt("humidity"));
            String windSpeed = String.valueOf(jsonObject1.getInt("speed"));

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = sdf.format(new Date());

            weatherLayout = (LinearLayout)getView().findViewById(R.id.weatherLayout);

            dateTextView = (TextView)getView().findViewById(R.id.dateTextView);
            placeTextView = (TextView)getView().findViewById(R.id.placeTextView);
            temperatureTextView = (TextView)getView().findViewById(R.id.temperatureTextView);
            weatherImageView = (ImageView)getView().findViewById(R.id.weatherImageView);
            descTextView = (TextView)getView().findViewById(R.id.descTextView);
            humidityTextView = (TextView)getView().findViewById(R.id.humidityTextView);
            windTextView = (TextView)getView().findViewById(R.id.windTextView);

            placeTextView.setText(placeName);

            humidityTextView.setText("Humidity: "+humidity);

            windTextView.setText("Wind Speed: "+windSpeed+" km/h");

            descTextView.setText(desc);

            double temperatureInteger = Double.parseDouble(temperature);
            double centigrade = (temperatureInteger - 32)/1.8000;
            centigrade = Math.round(centigrade);
            int centi = (int)centigrade;

            if(centi > 32){
                weatherImageView.setImageResource(R.drawable.sun1);
            }else if(25 < centi && centi < 30){
                weatherImageView.setImageResource(R.drawable.cloudysunny);
            }else if(centi < 25){
                weatherImageView.setImageResource(R.drawable.cloudwithrain);
            }

            temperatureTextView.setText(String.valueOf(centi));

            dateTextView.setText(formattedDate);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }
    );

    RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
    requestQueue.add(request);

    }
}
