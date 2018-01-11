package com.example.prabhdeep.currencyconvertor;

/**
 * Created by Prabhdeep on 11-Jan-18.
 */

public class WeatherModel {
    private double wind;
    private double pressure;
    private double Temp;
    private double humidity;

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemp() {
        return Temp;
    }

    public void setTemp(double temp) {
        Temp = temp;
    }

    private String description;
    private String city;
    private String country;

    public double getWind() {
        return wind;
    }

    public void setWind(double minTemp) {
        this.wind = minTemp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
