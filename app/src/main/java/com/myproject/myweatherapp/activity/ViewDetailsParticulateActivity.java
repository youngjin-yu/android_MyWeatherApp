package com.myproject.myweatherapp.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.myproject.myweatherapp.DTO.ParticulateInfo;
import com.myproject.myweatherapp.R;

public class ViewDetailsParticulateActivity extends AppCompatActivity {


    private TextView details_dataTime;
    private TextView details_pm25Value;
    private TextView details_coValue;
    private TextView details_no2Value;
    private TextView details_o3Value;
    private TextView details_so2Value;
    private ImageView imageView;
    private ParticulateInfo particulateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_particulate_details);

        setComp();
        getData();
        setParticulate();
    }

    //기본 컴포넌트 셋팅
    public void setComp() {
        details_dataTime = findViewById(R.id.details_dataTime);
        details_pm25Value = findViewById(R.id.details_pm25Value);
        details_coValue = findViewById(R.id.details_coValue);
        details_no2Value = findViewById(R.id.details_no2Value);
        details_o3Value =findViewById(R.id.details_o3Value);
        details_so2Value=findViewById(R.id.details_so2Value);
        imageView=findViewById(R.id.details_particulateImage);
    }

    //이전 액티비티에서 받아오는 인텐트
    public void getData() {
        particulateInfo = (ParticulateInfo) (getIntent().getParcelableExtra("CorrespondingParticulate"));
    }

    //이전 액티비티에서 받아오는 인텐트에서 정보를 확인하여 표시
    public void setParticulate() {
        if(this.particulateInfo != null) {

            details_dataTime.setText(particulateInfo.getDataTime());
            details_pm25Value.setText(String.valueOf(particulateInfo.getPm25Value()));
            details_coValue.setText(String.valueOf(particulateInfo.getCoValue()));
            details_no2Value.setText(String.valueOf(particulateInfo.getNo2Value()));
            details_o3Value.setText(String.valueOf(particulateInfo.getO3Value()));
            details_so2Value.setText(String.valueOf(particulateInfo.getSo2Value()));

            if(((particulateInfo.getPm25Value())>=0)&&((particulateInfo.getPm25Value())<=15)){
                imageView.setImageResource(R.drawable.good);
            }else if(((particulateInfo.getPm25Value())>15)&&((particulateInfo.getPm25Value())<=25)) {
                imageView.setImageResource(R.drawable.soso);
            }else if(((particulateInfo.getPm25Value())>25)&&((particulateInfo.getPm25Value())<=50)) {
                imageView.setImageResource(R.drawable.bad);
            }else if(((particulateInfo.getPm25Value())>50)) {
                imageView.setImageResource(R.drawable.verybad);
            }
        }
    }
}
