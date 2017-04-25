package example.com.weather.gson;

import java.util.List;

/**
 * Created by 14064 on 2017/4/23.
 */

public class HeWeather {
    public Aqi aqi;
    public Basic basic;
    public List<Daily_Forecast> daily_forecast;
    public List<Hourly_Forecast> hourly_forecast;
    public Hourly_Forecast now;
    public String status;
    public  Suggestion suggestion;

    public Aqi getAqi() {
        return aqi;
    }

    public void setAqi(Aqi api) {
        this.aqi = api;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public List<Daily_Forecast> getDaily_forecast() {
        return daily_forecast;
    }

    public void setDaily_forecasts(List<Daily_Forecast> daily_forecast) {
        this.daily_forecast = daily_forecast;
    }

    public List<Hourly_Forecast> getHourly_forecast() {
        return hourly_forecast;
    }

    public void setHourly_forecasts(List<Hourly_Forecast> hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }

    public Hourly_Forecast getNow() {
        return now;
    }

    public void setNow(Hourly_Forecast now) {
        this.now = now;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }
}
