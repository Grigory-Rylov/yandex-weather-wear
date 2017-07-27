package com.grishberg.androidweatherprovider;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by grishberg on 21.07.17.
 */
public class WeatherResponse implements Parcelable {
    private final float currentTemperature;
    private final int weatherIconIndex;
    private final WeatherRequestParams.RequestType currentWeatherType;

    public WeatherResponse(float currentTemperature,
                           int weatherIcon,
                           WeatherRequestParams.RequestType currentWeatherType) {
        this.currentTemperature = currentTemperature;
        this.weatherIconIndex = weatherIcon;
        this.currentWeatherType = currentWeatherType;
    }

    public float getCurrentTemperature() {
        return currentTemperature;
    }

    public int getWeatherIconIndex() {
        return weatherIconIndex;
    }

    public WeatherRequestParams.RequestType getCurrentWeatherType() {
        return currentWeatherType;
    }

    public byte[] toBytes() {
        Parcel parcel = Parcel.obtain();
        writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    public static WeatherResponse weatherResponseFromBytes(byte[] bytes) {
        Parcel parcel = unmarshall(bytes);
        WeatherResponse result = CREATOR.createFromParcel(parcel);
        parcel.recycle();
        return result;
    }

    private static Parcel unmarshall(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0); // This is extremely important!
        return parcel;
    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.currentTemperature);
        dest.writeInt(this.weatherIconIndex);
        dest.writeInt(this.currentWeatherType == null ? -1 : this.currentWeatherType.ordinal());
    }

    private WeatherResponse(Parcel in) {
        this.currentTemperature = in.readFloat();
        this.weatherIconIndex = in.readInt();
        int tmpCurrentWeatherType = in.readInt();
        this.currentWeatherType = tmpCurrentWeatherType == -1 ? null : WeatherRequestParams.RequestType.values()[tmpCurrentWeatherType];
    }

    public static final Creator<WeatherResponse> CREATOR = new Creator<WeatherResponse>() {
        @Override
        public WeatherResponse createFromParcel(Parcel source) {
            return new WeatherResponse(source);
        }

        @Override
        public WeatherResponse[] newArray(int size) {
            return new WeatherResponse[size];
        }
    };
}
