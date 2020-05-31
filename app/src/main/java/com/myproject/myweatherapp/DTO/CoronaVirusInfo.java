package com.myproject.myweatherapp.DTO;

import android.os.Parcel;
import android.os.Parcelable;

public class CoronaVirusInfo implements Parcelable {

    private long updated;
    private int cases;
    private int todayCases;
    private int deaths;
    private int todayDeaths;
    private int recovered;
    private String country;


    public CoronaVirusInfo() {
    }

    public CoronaVirusInfo(long updated, int cases, int todayCases, int deaths, int todayDeaths, int recovered, String country) {
        this.updated = updated;
        this.cases = cases;
        this.todayCases = todayCases;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.country = country;
    }

    protected CoronaVirusInfo(Parcel in) {
        updated = in.readLong();
        cases = in.readInt();
        todayCases = in.readInt();
        deaths = in.readInt();
        todayDeaths = in.readInt();
        recovered = in.readInt();
        country = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(updated);
        dest.writeInt(cases);
        dest.writeInt(todayCases);
        dest.writeInt(deaths);
        dest.writeInt(todayDeaths);
        dest.writeInt(recovered);
        dest.writeString(country);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CoronaVirusInfo> CREATOR = new Creator<CoronaVirusInfo>() {
        @Override
        public CoronaVirusInfo createFromParcel(Parcel in) {
            return new CoronaVirusInfo(in);
        }

        @Override
        public CoronaVirusInfo[] newArray(int size) {
            return new CoronaVirusInfo[size];
        }
    };

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public int getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(int todayCases) {
        this.todayCases = todayCases;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(int todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
