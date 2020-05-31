package com.myproject.myweatherapp.DTO;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherInfo implements Parcelable {

    private double temperature;
    private double windchill;
    private String description;
    private String timezome;
    private String icon;

    public WeatherInfo (){}

    protected WeatherInfo(Parcel in) {
        temperature = in.readDouble();
        windchill = in.readDouble();
        description = in.readString();
        timezome = in.readString();
        icon = in.readString();
    }

    public static final Creator<WeatherInfo> CREATOR = new Creator<WeatherInfo>() {
        @Override
        public WeatherInfo createFromParcel(Parcel in) {
            return new WeatherInfo(in);
        }

        @Override
        public WeatherInfo[] newArray(int size) {
            return new WeatherInfo[size];
        }
    };

    public WeatherInfo(double temperature,double windchill,String description,String timezome,String icon) {
        this.temperature = temperature;
        this.windchill=windchill;
        this.description=description;
        this.timezome=timezome;
        this.icon=icon;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindchill() {
        return windchill;
    }

    public void setWindchill(double windchill) {
        this.windchill = windchill;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getTimezome() {
        return timezome;
    }

    public void setTimezome(String timezome) {
        this.timezome = timezome;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(temperature);
        dest.writeDouble(windchill);
        dest.writeString(description);
        dest.writeString(timezome);
        dest.writeString(icon);
    }
}
