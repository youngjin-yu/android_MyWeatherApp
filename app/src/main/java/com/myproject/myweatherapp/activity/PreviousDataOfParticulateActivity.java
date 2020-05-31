package com.myproject.myweatherapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.myweatherapp.DTO.ParticulateInfo;
import com.myproject.myweatherapp.R;
import com.myproject.myweatherapp.adapter.RecyclerViewParticulateAdapter;

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

public class PreviousDataOfParticulateActivity extends AppCompatActivity {

    public static ArrayList<ParticulateInfo> particulateInfoArrayList = new ArrayList<>();
    public static RecyclerViewParticulateAdapter recyclerViewParticulateAdapter = new RecyclerViewParticulateAdapter(particulateInfoArrayList);
    public static int PARTICULATE_DETAIL = 8888;
    private String selectedCity;
    private String sidoName;
    private ParticulateInfo particulateInfo;

    // 리사이클러뷰
    RecyclerView recyclerView = null;
    private String TAG="PreviousDataOfParticulateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_data_particulate);
        recyclerView = findViewById(R.id.recyclerViewParticulate);

        Log.d(TAG,"onCreate is called");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(recyclerViewParticulateAdapter);

        getData();

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
        particulateInfoArrayList.clear();
        Log.d(TAG,"onDestroy is called");
    }

    //리싸이클러뷰 아이템 클릭 리스너 -> 상세 페이지로 이동
    private void setItemClickListener() {

        recyclerViewParticulateAdapter.setOnItemClickListener(new RecyclerViewParticulateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                ParticulateInfo particulateInfo = particulateInfoArrayList.get(position);

                //del 2020.03.23    Schedule schedule = arrayListOtherSchedule.get(position);
                Intent intent = new Intent(PreviousDataOfParticulateActivity.this, ViewDetailsParticulateActivity.class);
                intent.putExtra("CorrespondingParticulate", particulateInfo);
                //intent.putExtra("itemPosition", position);
                startActivity(intent);

            }
        });

    }

    //이전 액티비티에서 받아오는 인텐트
    private void getData() {
        selectedCity = (getIntent().getStringExtra("SelectedCity"));
        sidoName = (getIntent().getStringExtra("SidoName"));
        api_key(selectedCity,sidoName);
    }

    private void api_key(String selectedCity, final String sidoName) {
        OkHttpClient client = new OkHttpClient();

        //시도 이름 (서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)
        Request request = new Request.Builder()
                .url("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?" +
                        "sidoName="+selectedCity+"&searchCondition=DAILY&pageNo=1&numOfRows=600&" +
                        "ServiceKey=%%%%" +
                        "&ver=1.3&_returnType=json")
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
                        Log.d(TAG,"OkHttpConnection okay");
                        JSONObject json = new JSONObject(responseData);

                        JSONArray array = json.getJSONArray("list");
                        System.out.println(array.length());
                        for(int i=0; i<array.length(); i++){

                            JSONObject jsonObject = array.getJSONObject(i);
                            String cityName = jsonObject.getString("cityName");
                            particulateInfo = new ParticulateInfo();
                            if(sidoName.equals(cityName)){
                                particulateInfo.setCoValue(jsonObject.getDouble("coValue"));
                                particulateInfo.setDataTime(jsonObject.getString("dataTime"));
                                particulateInfo.setNo2Value(jsonObject.getDouble("no2Value"));
                                particulateInfo.setO3Value(jsonObject.getDouble("o3Value"));
                                particulateInfo.setSo2Value(jsonObject.getDouble("so2Value"));
                                particulateInfo.setPm25Value(jsonObject.getDouble("pm25Value"));

                                particulateInfoArrayList.add(particulateInfo);
                                Log.d(TAG,particulateInfo.getDataTime());
                            }
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    recyclerViewParticulateAdapter.notifyDataSetChanged();
                                }
                            });

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


}
