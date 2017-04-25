package example.com.weather.gson;

/**
 * Created by 14064 on 2017/4/23.
 */

public class Aqi {
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City city;
    public  static class City{
        private int aqi;
        private int co;
        private String no2;
        private int pm10;
        private int pm25;
        private String qlty;
        private int so2;

        public int getAqi() {
            return aqi;
        }

        public void setAqi(int api) {
            this.aqi = api;
        }

        public int getCo() {
            return co;
        }

        public void setCo(int co) {
            this.co = co;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public int getPm10() {
            return pm10;
        }

        public void setPm10(int pm10) {
            this.pm10 = pm10;
        }

        public int getPm25() {
            return pm25;
        }

        public void setPm25(int pm25) {
            this.pm25 = pm25;
        }

        public String getQlty() {
            return qlty;
        }

        public void setQlty(String qlty) {
            this.qlty = qlty;
        }

        public int getSo2() {
            return so2;
        }

        public void setSo2(int so2) {
            this.so2 = so2;
        }
    }
}
