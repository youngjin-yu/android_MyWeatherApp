package com.myproject.myweatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.myproject.myweatherapp.DTO.CoronaVirusInfo;
import com.myproject.myweatherapp.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ViewDetailsCoronaVirusActivity extends AppCompatActivity {

    private String TAG = "ViewDetailsCovidVirusActivity";
    private TextView total_cases;
    private TextView new_cases;
    private TextView total_deaths;
    private TextView new_deaths;
    private TextView total_recovered;
    private TextView updated_dates;
    private EditText search_coronaVirusStatusByCountry;
    private ArrayList<String> arrayListCountry;
    private TextView textViewOutputOfCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details_corona_virus);
        total_cases =findViewById(R.id.total_cases);
        new_cases = findViewById(R.id.new_cases);
        total_deaths =findViewById(R.id.total_deaths);
        new_deaths =findViewById(R.id.new_deaths);
        total_recovered =findViewById(R.id.total_recovered);
        updated_dates = findViewById(R.id.updated_dates);
        search_coronaVirusStatusByCountry =findViewById(R.id.search_coronaVirusStatusByCountry);
        textViewOutputOfCountry =findViewById(R.id.textViewOutputOfCountry);
        arrayListCountry = new ArrayList<>();

        api_key();

        /*Runnable parsingRunnable = new ParsingThread();
        Thread parsingThread = new Thread(parsingRunnable);
        parsingThread.start();*/

    }

    private void searchCountry() {
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

                        arrayListCountry.clear();
                        JSONArray jsonArray = new JSONArray(responseData);
                        String str = "";
                        for(int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String country = jsonObject.getString("country");
                            arrayListCountry.add(country);
                        }
                        for(int i=0; i<arrayListCountry.size(); i++){
                            str= str + arrayListCountry.get(i) +"\r\n";
                        }

                        final String finalStr = str;
                        runOnUiThread(new Runnable() {
                            public void run() {
                                textViewOutputOfCountry.setText(finalStr);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void api_key() {
        OkHttpClient client = new OkHttpClient();

        //
        Request request = new Request.Builder()
                .url("https://corona.lmao.ninja/v2/all")
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
                        JSONObject jsonObject = new JSONObject(responseData);


                        long last_update = jsonObject.getLong("updated");
                        Date updatedDate = new Date(last_update);
                        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

                        final String stringOfLastUpdated = transFormat.format(updatedDate);


                        final int cases = jsonObject.getInt("cases");
                        final int todayCases = jsonObject.getInt("todayCases");
                        final int deaths = jsonObject.getInt("deaths");
                        final int todayDeaths = jsonObject.getInt("todayDeaths");
                        final int recovered = jsonObject.getInt("recovered");

                        runOnUiThread(new Runnable() {
                            public void run() {
                                total_cases.setText(String.valueOf(cases));
                                new_cases.setText(String.valueOf(todayCases));
                                total_deaths.setText(String.valueOf(deaths));
                                new_deaths.setText(String.valueOf(todayDeaths));
                                total_recovered.setText(String.valueOf(recovered));
                                updated_dates.setText(stringOfLastUpdated);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClickSearchCoronaVirusStatus(View view) {
        Intent intent = new Intent(ViewDetailsCoronaVirusActivity.this, ViewDetailsCoronaVirusByCountryActivity.class);
        intent.putExtra("Country", search_coronaVirusStatusByCountry.getText().toString());
        startActivity(intent);
    }

    public void onClickSearchCountry(View view) {
        searchCountry();
    }
}
