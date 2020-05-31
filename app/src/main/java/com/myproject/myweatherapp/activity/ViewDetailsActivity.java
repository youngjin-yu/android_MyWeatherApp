package com.myproject.myweatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.myproject.myweatherapp.R;
import com.myproject.myweatherapp.DTO.WeatherInfo;

public class ViewDetailsActivity extends AppCompatActivity {


    private TextView timezone, temperature,windchill,description;
    private ImageView imageView;
    private WeatherInfo weatherInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

        setComp();
        getData();
        setWeather();
    }

    //기본 컴포넌트 셋팅
    public void setComp() {
        timezone = findViewById(R.id.details_timezone);
        temperature = findViewById(R.id.details_temperature);
        windchill = findViewById(R.id.details_windchill);
        description = findViewById(R.id.details_description);
        imageView =findViewById(R.id.detail_imageView);
    }

    //이전 액티비티에서 받아오는 인텐트
    public void getData() {
        weatherInfo = (WeatherInfo) (getIntent().getParcelableExtra("CorrespondingWeather"));
    }

    //이전 액티비티에서 받아오는 인텐트에서 정보를 확인하여 뉴스표시
    public void setWeather() {
        if(this.weatherInfo != null) {

            timezone.setText(weatherInfo.getTimezome());
            temperature.setText(String.valueOf(weatherInfo.getTemperature()));
            windchill.setText(String.valueOf(weatherInfo.getWindchill()));
            description.setText(weatherInfo.getDescription());

            if((weatherInfo.getIcon()).equals("01d")||(weatherInfo.getIcon()).equals("01n")){
                imageView.setImageResource(R.drawable.d01d);
            }else if((weatherInfo.getIcon()).equals("02d")||(weatherInfo.getIcon()).equals("02n")){
                imageView.setImageResource(R.drawable.d02d);
            }else if((weatherInfo.getIcon()).equals("03d")||(weatherInfo.getIcon()).equals("03n")){
                imageView.setImageResource(R.drawable.d03d);
            }else if((weatherInfo.getIcon()).equals("04d")||(weatherInfo.getIcon()).equals("04n")){
                imageView.setImageResource(R.drawable.d04d);
            }else if((weatherInfo.getIcon()).equals("09d")||(weatherInfo.getIcon()).equals("09n")){
                imageView.setImageResource(R.drawable.d09d);
            }else if((weatherInfo.getIcon()).equals("10d")||(weatherInfo.getIcon()).equals("10n")){
                imageView.setImageResource(R.drawable.d10d);
            }else if((weatherInfo.getIcon()).equals("11d")||(weatherInfo.getIcon()).equals("11n")){
                imageView.setImageResource(R.drawable.d11d);
            }else if((weatherInfo.getIcon()).equals("13d")||(weatherInfo.getIcon()).equals("13n")){
                imageView.setImageResource(R.drawable.d13d);
            }else{
                imageView.setImageResource(R.drawable.weather);
            }

        }
    }
}
