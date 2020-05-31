package com.myproject.myweatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.myweatherapp.DTO.WeatherInfo;
import com.myproject.myweatherapp.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<WeatherInfo> myArrayList;

    /*//RecyclerView에서 유저가 선택한 라디오 버튼의 위치
    public static int recyclerViewRadioBtnPosition = -1;*/

    //private int lastSelectedPosition = -1;

    //아이템 클릭 리스너 변수
    private OnItemClickListener onItemClickListener;

    //아이템 클릭 인터페이스
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    //아이템 클릭 리스너 설정
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    //생성자에서 데이터 리스트 객체를 전달받는다.
    public RecyclerViewAdapter(ArrayList<WeatherInfo> myWeatherInfoList) {
        this.myArrayList = myWeatherInfoList;
    }


    //item view를 위한 ViewHolder 객체를 생성하여 return
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //item 1개 view
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other_schedule, parent, false);
        Context context = parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.item_weather, parent, false);
        RecyclerViewAdapter.ViewHolder viewHolder = new RecyclerViewAdapter.ViewHolder(view);

        return viewHolder;
    }

    //ArrayList에서 position에 해당하는 데이터를 ViewHolder의 item view에 표시
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder, final int position) {

        WeatherInfo weatherInfo = myArrayList.get(position);
        holder.imageView.setImageResource(R.drawable.weather);
        if ((weatherInfo != null)) {
            holder.timezone.setText(weatherInfo.getTimezome());
            holder.temperature.setText(String.valueOf(weatherInfo.getTemperature()));

            holder.windchill.setText(String.valueOf(weatherInfo.getWindchill()));
            holder.description.setText(String.valueOf(weatherInfo.getDescription()));


            //2020.03.23 수정
            if (((weatherInfo.getIcon()) != "")) {
                //File file = new File(schedule.getImageFilePath());
                //파일 경로에 실제로 파일이 존재 한다면, 그 일정에 해당하는 사진파일을 불러들이고
                /*if(file.exists() && (!file.isDirectory())){

                }else{
                    //파일 경로에 실제로 파일이 존재 하지 않는다면, 그 일정에는 default 달력 사진을 넣는다.
                    holder.uploadImage.setImageResource(R.drawable.ic_calendar);
                }*/
                //Bitmap myBitmap = BitmapFactory.decodeFile((schedule.getImageFilePath()));
                if((weatherInfo.getIcon()).equals("01d")||(weatherInfo.getIcon()).equals("01n")){
                    holder.imageView.setImageResource(R.drawable.d01d);
                }else if((weatherInfo.getIcon()).equals("02d")||(weatherInfo.getIcon()).equals("02n")){
                    holder.imageView.setImageResource(R.drawable.d02d);
                }else if((weatherInfo.getIcon()).equals("03d")||(weatherInfo.getIcon()).equals("03n")){
                    holder.imageView.setImageResource(R.drawable.d03d);
                }else if((weatherInfo.getIcon()).equals("04d")||(weatherInfo.getIcon()).equals("04n")){
                    holder.imageView.setImageResource(R.drawable.d04d);
                }else if((weatherInfo.getIcon()).equals("09d")||(weatherInfo.getIcon()).equals("09n")){
                    holder.imageView.setImageResource(R.drawable.d09d);
                }else if((weatherInfo.getIcon()).equals("10d")||(weatherInfo.getIcon()).equals("10n")){
                    holder.imageView.setImageResource(R.drawable.d10d);
                }else if((weatherInfo.getIcon()).equals("11d")||(weatherInfo.getIcon()).equals("11n")){
                    holder.imageView.setImageResource(R.drawable.d11d);
                }else if((weatherInfo.getIcon()).equals("13d")||(weatherInfo.getIcon()).equals("13n")){
                    holder.imageView.setImageResource(R.drawable.d13d);
                }else{
                    holder.imageView.setImageResource(R.drawable.weather);
                }


            }
        }
    }



    //ArrayList의 size(데이터 개수)를 return
    @Override
    public int getItemCount() {
        return myArrayList.size();
    }

    //item view를 저장하는 ViewHolder 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView timezone;
        TextView temperature;
        TextView windchill;
        TextView description;
        ImageView imageView;


        //view 객체에 대한 참조
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timezone = itemView.findViewById(R.id.timezone);
            temperature = itemView.findViewById(R.id.temperature);
            windchill = itemView.findViewById(R.id.windchill);

            description = itemView.findViewById(R.id.description);
            imageView=itemView.findViewById(R.id.weatherImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    // notifyDataSetChanged()에 의해 리사이클러뷰가 아이템뷰를 갱신하는 과정에서,
                    // 뷰홀더가 참조하는 아이템이 어댑터에서 삭제되면 getAdapterPosition() 메서드는 NO_POSITION을 리턴하기 때문입니다.
                    if (pos != RecyclerView.NO_POSITION) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }
}
