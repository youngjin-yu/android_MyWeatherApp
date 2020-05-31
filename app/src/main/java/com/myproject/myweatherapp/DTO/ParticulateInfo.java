package com.myproject.myweatherapp.DTO;

import android.os.Parcel;
import android.os.Parcelable;

public class ParticulateInfo implements Parcelable {
    //일산화탄소 농도
    private double coValue;
    //이산화질소 농도
    private double no2Value;
    //오존 농도 o3Value
    private double o3Value;
    //아황산가스 농도 so2Value
    private double so2Value;
    //pm25value 미세먼지(PM2.5) 농도
    private double pm25Value;
    //데이터의 시간
    private String dataTime;

    public ParticulateInfo(){}

    public ParticulateInfo(double coValue, double no2Value, double o3Value, double so2Value, double pm25Value, String dataTime) {
        this.coValue = coValue;
        this.no2Value = no2Value;
        this.o3Value = o3Value;
        this.so2Value = so2Value;
        this.pm25Value = pm25Value;
        this.dataTime = dataTime;
    }

    protected ParticulateInfo(Parcel in) {
        coValue = in.readDouble();
        no2Value = in.readDouble();
        o3Value = in.readDouble();
        so2Value = in.readDouble();
        pm25Value = in.readDouble();
        dataTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(coValue);
        dest.writeDouble(no2Value);
        dest.writeDouble(o3Value);
        dest.writeDouble(so2Value);
        dest.writeDouble(pm25Value);
        dest.writeString(dataTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ParticulateInfo> CREATOR = new Creator<ParticulateInfo>() {
        @Override
        public ParticulateInfo createFromParcel(Parcel in) {
            return new ParticulateInfo(in);
        }

        @Override
        public ParticulateInfo[] newArray(int size) {
            return new ParticulateInfo[size];
        }
    };

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public double getCoValue() {
        return coValue;
    }

    public void setCoValue(double coValue) {
        this.coValue = coValue;
    }

    public double getNo2Value() {
        return no2Value;
    }

    public void setNo2Value(double no2Value) {
        this.no2Value = no2Value;
    }

    public double getO3Value() {
        return o3Value;
    }

    public void setO3Value(double o3Value) {
        this.o3Value = o3Value;
    }

    public double getSo2Value() {
        return so2Value;
    }

    public void setSo2Value(double so2Value) {
        this.so2Value = so2Value;
    }

    public double getPm25Value() {
        return pm25Value;
    }

    public void setPm25Value(double pm25Value) {
        this.pm25Value = pm25Value;
    }
}
