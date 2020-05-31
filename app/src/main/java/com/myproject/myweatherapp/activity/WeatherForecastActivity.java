package com.myproject.myweatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import com.myproject.myweatherapp.R;
import com.myproject.myweatherapp.adapter.RecyclerViewAdapter;
import com.myproject.myweatherapp.DTO.WeatherInfo;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherForecastActivity extends AppCompatActivity {

    public static ArrayList<WeatherInfo> weatherInfoArrayList = new ArrayList<>();
    public static RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(weatherInfoArrayList);
    public static int WEATHER_DETAIL = 9999;

    // 리사이클러뷰
    RecyclerView recyclerView = null;
    private String TAG="WeatherForecastActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        recyclerView = findViewById(R.id.recyclerView);

        Log.d(TAG,"onCreate is called");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(recyclerViewAdapter);

        String city=getIntent().getStringExtra("City");
        //키보드를 숨긴다.
        /*InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getRootView().getWindowToken(), 0);*/
        api_key(city);

        setItemClickListener();
    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause is called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume is called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //날씨 리스트 삭제
        weatherInfoArrayList.clear();
        Log.d(TAG,"onDestroy is called");
    }

    //리싸이클러뷰 아이템 클릭 리스너 -> 상세 페이지로 이동
    private void setItemClickListener() {

        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                WeatherInfo weatherInfo = weatherInfoArrayList.get(position);


                //del 2020.03.23    Schedule schedule = arrayListOtherSchedule.get(position);
                Intent intent = new Intent(WeatherForecastActivity.this, ViewDetailsActivity.class);
                intent.putExtra("CorrespondingWeather", weatherInfo);
                //intent.putExtra("itemPosition", position);
                startActivity(intent);

            }
        });

    }

    private void api_key(final String City) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/forecast?q=" + City + "&appid=&units=metric")
                .get()
                .build();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Response response = client.newCall(request).execute();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseData = response.body().string();
                    try {
                        JSONObject json = new JSONObject(responseData);

                        JSONArray array = json.getJSONArray("list");
                        System.out.println(array.length());
                        for(int i=0; i<array.length(); i++){
                            WeatherInfo weatherInfo = new WeatherInfo();
                            JSONObject jsonObject = array.getJSONObject(i);
                            String timezone = jsonObject.getString("dt_txt");
                            weatherInfo.setTimezome(timezone);
                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            weatherInfo.setTemperature(jsonObjectMain.getInt("temp"));
                            weatherInfo.setWindchill(jsonObjectMain.getInt("feels_like"));
                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            weatherInfo.setDescription(jsonObjectWeather.getString("description"));
                            String icons = jsonObjectWeather.getString("icon");
                            weatherInfo.setIcon(icons);

                            weatherInfoArrayList.add(weatherInfo);

                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                recyclerViewAdapter.notifyDataSetChanged();
                            }
                        });

                        //JSONObject object = array.getJSONObject(0);



                        /*String description = object.getString("description");
                        String icons = object.getString("icon");

                        JSONObject temp1 = json.getJSONObject("main");
                        Double Temperature = temp1.getDouble("temp");
                        Double Windchill = temp1.getDouble("feels_like");

                        setText(view_city, City);

                        String temps = Math.round(Temperature) + " °C";
                        setText(view_temp, temps);
                        String windchill_temps= Math.round(Windchill)+" °C";
                        setText(view_windchill,windchill_temps);
                        setText(view_desc, description);
                        setImage(view_weather, icons);*/

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
