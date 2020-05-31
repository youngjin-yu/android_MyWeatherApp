package com.myproject.myweatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myproject.myweatherapp.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ParticulateActivity extends AppCompatActivity {

    private Spinner spinnerCity;
    private ArrayList<String> arrayListCity;
    private ArrayList<String> arrayListStation;
    private ArrayAdapter<String> arrayAdapterCity;
    private ArrayAdapter<String> arrayAdapterStation;
    private Boolean isCitySelected;
    private EditText search_sido;

    private String selectedCity;
    private TextView textViewOutput;
    public static Set<String> setSidoName = new HashSet<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particulate);
        textViewOutput=findViewById(R.id.textViewOutput);
        spinnerCity = (Spinner)findViewById(R.id.spinnerCity);
        search_sido=findViewById(R.id.search_sido);
        //spinner=findViewById(R.id.spinner);

        isCitySelected=false;
        arrayListCity = new ArrayList<>();
        arrayListStation = new ArrayList<>();

        arrayListCity.add("서울");
        arrayListCity.add("부산");
        arrayListCity.add("대구");
        arrayListCity.add("인천");
        arrayListCity.add("광주");
        arrayListCity.add("대전");
        arrayListCity.add("울산");
        arrayListCity.add("경기");
        arrayListCity.add("강원");
        arrayListCity.add("충북");
        arrayListCity.add("충남");
        arrayListCity.add("전북");
        arrayListCity.add("전남");
        arrayListCity.add("경북");
        arrayListCity.add("경남");
        arrayListCity.add("제주");
        arrayListCity.add("세종");

        arrayAdapterCity = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayListCity);

        arrayAdapterStation = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                arrayListStation);


        spinnerCity.setAdapter(arrayAdapterCity);


        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                isCitySelected=true;

                Toast.makeText(getApplicationContext(),arrayListCity.get(i)+"이(가) 선택되었습니다.",
                        Toast.LENGTH_SHORT).show();
                selectedCity = arrayListCity.get(i);
                //시도 이름 (서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)
                /*api_key_test(arrayListCity.get(i));


                Iterator<String> it = setSidoName.iterator(); // Iterator(반복자) 생성
                while (it.hasNext()) { // hasNext() : 데이터가 있으면 true 없으면 false
                    arrayListStation.add(it.next()); // next() : 다음 데이터 리턴
                }
                arrayAdapterStation.notifyDataSetChanged();*/
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isCitySelected=false;
    }



    private void api_key(String sidoName) {
        OkHttpClient client = new OkHttpClient();

        //시도 이름 (서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)
        Request request = new Request.Builder()
                .url("http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?" +
                        "sidoName="+sidoName+"&searchCondition=DAILY&pageNo=1&numOfRows=100&" +
                        "ServiceKey=%secret%secret%%" +
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
                        setSidoName.clear();
                        JSONObject json = new JSONObject(responseData);

                        JSONArray array = json.getJSONArray("list");
                        System.out.println(array.length());
                        for(int i=0; i<array.length(); i++){

                            JSONObject jsonObject = array.getJSONObject(i);
                            String cityName = jsonObject.getString("cityName");
                            setSidoName.add(cityName);

                        }
                        System.out.println(setSidoName.size());
                        Iterator<String> iterator = setSidoName.iterator();
                        String str = "";
                        while (iterator.hasNext()){
                            //System.out.println(iterator.next());
                            if(str.equals("")){
                                str= iterator.next();
                                continue;
                            }
                            str= str+" , "+iterator.next();

                        }
                        final String finalStr = str;
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                textViewOutput.setText(finalStr);
                                // Stuff that updates the UI

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

    public void onClickSeoul(View view) {
        api_key("서울");
    }

    public void onClickBusan(View view) {
        api_key("부산");
    }

    public void onClickDaegu(View view) {
        api_key("대구");
    }

    public void onClickIncheon(View view) {
        api_key("인천");
    }

    public void onClickGwangju(View view) {
        api_key("광주");
    }

    public void onClickDaejeon(View view) {
        api_key("대전");
    }

    public void onClickOwlsan(View view) {
        api_key("울산");
    }

    public void onClickGyenggi(View view) {
        api_key("경기");
    }

    public void onClickGangwon(View view) {
        api_key("강원");
    }

    public void onClickChongbuk(View view) {
        api_key("충북");
    }

    public void onClickChongnam(View view) {
        api_key("충남");
    }

    public void onClickJeonbuk(View view) {
        api_key("전북");
    }

    public void onClickJeonnam(View view) {
        api_key("전남");
    }

    public void onClickGyengbuk(View view) {
        api_key("경북");
    }

    public void onClickGyengnam(View view) {
        api_key("경남");
    }

    public void onClickJeju(View view) {
        api_key("제주");
    }

    public void onClickSejong(View view) {
        api_key("세종");
    }

    public void onClickSearch(View view) {
        if(search_sido.equals("")){
            Toast.makeText(getApplicationContext(),"시(도)를 입력하여주세요.",
                    Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(ParticulateActivity.this, PreviousDataOfParticulateActivity.class);
        intent.putExtra("SelectedCity", selectedCity);
        intent.putExtra("SidoName", search_sido.getText().toString());
        startActivity(intent);
    }
}
