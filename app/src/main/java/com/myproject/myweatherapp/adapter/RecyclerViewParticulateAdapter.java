package com.myproject.myweatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myproject.myweatherapp.DTO.ParticulateInfo;
import com.myproject.myweatherapp.R;

import java.util.ArrayList;

public class RecyclerViewParticulateAdapter extends RecyclerView.Adapter<RecyclerViewParticulateAdapter.ViewHolder> {

    private ArrayList<ParticulateInfo> myArrayList;

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
    public RecyclerViewParticulateAdapter(ArrayList<ParticulateInfo> myParticulateInfoList) {
        this.myArrayList = myParticulateInfoList;
    }


    //item view를 위한 ViewHolder 객체를 생성하여 return
    @NonNull
    @Override
    public RecyclerViewParticulateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //item 1개 view
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_other_schedule, parent, false);
        Context context = parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.item_particulate, parent, false);
        RecyclerViewParticulateAdapter.ViewHolder viewHolder = new RecyclerViewParticulateAdapter.ViewHolder(view);

        return viewHolder;
    }

    //ArrayList에서 position에 해당하는 데이터를 ViewHolder의 item view에 표시
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewParticulateAdapter.ViewHolder holder, final int position) {

        ParticulateInfo particulateInfo = myArrayList.get(position);
        //holder.imageView.setImageResource(R.drawable.weather);
        if ((particulateInfo != null)) {
            holder.dataTime.setText(particulateInfo.getDataTime());
            holder.pm25Value.setText(String.valueOf(particulateInfo.getPm25Value()));
            holder.coValue.setText(String.valueOf(particulateInfo.getCoValue()));
            holder.no2Value.setText(String.valueOf(particulateInfo.getNo2Value()));
            holder.o3Value.setText(String.valueOf(particulateInfo.getO3Value()));
            holder.so2Value.setText(String.valueOf(particulateInfo.getSo2Value()));

            if(((particulateInfo.getPm25Value())>=0)&&((particulateInfo.getPm25Value())<=15)){
                holder.imageView.setImageResource(R.drawable.good);
            }else if(((particulateInfo.getPm25Value())>15)&&((particulateInfo.getPm25Value())<=25)) {
                holder.imageView.setImageResource(R.drawable.soso);
            }else if(((particulateInfo.getPm25Value())>25)&&((particulateInfo.getPm25Value())<=50)) {
                holder.imageView.setImageResource(R.drawable.bad);
            }else if(((particulateInfo.getPm25Value())>50)) {
                holder.imageView.setImageResource(R.drawable.verybad);
            }
            /*//2020.03.23 수정
            if (((weatherInfo.getIcon()) != "")) {
                //File file = new File(schedule.getImageFilePath());
                //파일 경로에 실제로 파일이 존재 한다면, 그 일정에 해당하는 사진파일을 불러들이고
                *//*if(file.exists() && (!file.isDirectory())){

                }else{
                    //파일 경로에 실제로 파일이 존재 하지 않는다면, 그 일정에는 default 달력 사진을 넣는다.
                    holder.uploadImage.setImageResource(R.drawable.ic_calendar);
                }*//*
                //Bitmap myBitmap = BitmapFactory.decodeFile((schedule.getImageFilePath()));

            }*/
        }
    }



    //ArrayList의 size(데이터 개수)를 return
    @Override
    public int getItemCount() {
        return myArrayList.size();
    }

    //item view를 저장하는 ViewHolder 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dataTime;
        TextView coValue;
        TextView no2Value;
        TextView o3Value;
        TextView so2Value;
        TextView pm25Value;
        ImageView imageView;


        //view 객체에 대한 참조
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dataTime = itemView.findViewById(R.id.dataTime);
            pm25Value = itemView.findViewById(R.id.pm25Value);
            coValue = itemView.findViewById(R.id.coValue);

            no2Value = itemView.findViewById(R.id.no2Value);
            o3Value=itemView.findViewById(R.id.o3Value);
            so2Value = itemView.findViewById(R.id.so2Value);
            imageView=itemView.findViewById(R.id.particulateImage);
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
