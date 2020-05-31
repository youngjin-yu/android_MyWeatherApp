package com.myproject.myweatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import com.myproject.myweatherapp.DTO.CoronaVirusInfo;
import com.myproject.myweatherapp.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ViewDetailsCoronaVirusByCountryActivity extends AppCompatActivity {

    private String receivedCountry;
    private String TAG = "ViewDetailsCoronaVirusByCountryActivity";
    private TextView total_cases_by_country;
    private TextView new_cases_by_country;
    private TextView total_deaths_by_country;
    private TextView new_deaths_by_country;
    private TextView total_recovered_by_country;
    private TextView updated_dates_by_country;
    private TextView countryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details_corona_virus_by_country);
        total_cases_by_country =findViewById(R.id.total_cases_by_country);
        new_cases_by_country = findViewById(R.id.new_cases_by_country);
        total_deaths_by_country =findViewById(R.id.total_deaths_by_country);
        new_deaths_by_country =findViewById(R.id.new_deaths_by_country);
        total_recovered_by_country =findViewById(R.id.total_recovered_by_country);
        updated_dates_by_country = findViewById(R.id.updated_dates_by_country);
        countryName=findViewById(R.id.countryName);
        getData();

        viewDetailsCoronaVirusStatusByCountry();
    }

    private void viewDetailsCoronaVirusStatusByCountry(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://corona.lmao.ninja/v2/countries?sort=country")
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
                        Log.d(TAG, "OkHttpConnection okay");
                        CoronaVirusInfo coronaVirusInfo = new CoronaVirusInfo();
                        JSONArray jsonArray = new JSONArray(responseData);
                        String str = "";
                        for(int i=0; i<jsonArray.length(); i++){
                            final JSONObject jsonObject = jsonArray.getJSONObject(i);
                            final String country = jsonObject.getString("country");
                            if(receivedCountry.equals(country)){


                                long last_update = jsonObject.getLong("updated");
                                Date updatedDate = new Date(last_update);
                                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                                final String stringOfLastUpdated = transFormat.format(updatedDate);


                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        try {
                                            countryName.setText(country);
                                            total_cases_by_country.setText(String.valueOf(jsonObject.getInt("cases")));
                                            new_cases_by_country.setText(String.valueOf(jsonObject.getInt("todayCases")));
                                            total_deaths_by_country.setText(String.valueOf(jsonObject.getInt("deaths")));
                                            new_deaths_by_country.setText(String.valueOf(jsonObject.getInt("todayDeaths")));
                                            total_recovered_by_country.setText(String.valueOf(jsonObject.getInt("recovered")));
                                            updated_dates_by_country.setText(stringOfLastUpdated);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //이전 액티비티에서 받아오는 인텐트
    private void getData() {

        receivedCountry = (getIntent().getStringExtra("Country"));

    }
}
