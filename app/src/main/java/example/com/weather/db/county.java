package example.com.weather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 14064 on 2017/4/23.
 */

public class County extends DataSupport {
    private int id;
    private String cityName;//城市名字
    private String weatherId;//对应天气的Id
    private int cityId;//城市ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }



}
