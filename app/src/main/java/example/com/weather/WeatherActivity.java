package example.com.weather;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.IOException;

import example.com.weather.gson.Daily_Forecast;
import example.com.weather.gson.HeWeather;
import example.com.weather.util.HttpUtil;
import example.com.weather.util.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    private Button navButton;
    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportsText;
    private ImageView imageView;
    public SwipeRefreshLayout refreshLayout;
    private String WeatherId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        navButton=(Button)findViewById(R.id.nav_button);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
       imageView=(ImageView)findViewById(R.id.bing_pic_img);
        titleCity=(TextView)findViewById(R.id.title_city);
        titleUpdateTime=(TextView)findViewById(R.id.tille_update_time);
        degreeText=(TextView)findViewById(R.id.degree_text);
        weatherInfoText=(TextView)findViewById(R.id.weather_info_text);
        aqiText=(TextView)findViewById(R.id.api_text);
        pm25Text=(TextView)findViewById(R.id.pm25_text);
        comfortText=(TextView)findViewById(R.id.comfort_text);
        carWashText=(TextView)findViewById(R.id.car_wash_text);
        sportsText=(TextView)findViewById(R.id.sport_text);
        weatherLayout=(ScrollView)findViewById(R.id.weahter_layout);
        forecastLayout=(LinearLayout)findViewById(R.id.forecast_layout);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
        String weatherString=preferences.getString("weather",null);
        Log.d("QQ","城市"+weatherString);
        String bingPic=preferences.getString("bing_pic",null);

        if(bingPic!=null){
            Glide.with(this).load(bingPic).into(imageView);
        }else {
            loadBasicPic();
        }
        if(weatherString != null){
            HeWeather heWeather= Utility.handleWeatherResponse(weatherString);
            WeatherId=heWeather.basic.getId();
            showWeatherInfo(heWeather);
        }else {
            WeatherId=getIntent().getStringExtra("weather_id");
            String cityId=getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(cityId);
        }
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(WeatherId);
                Log.d("QQ","城市的id"+WeatherId);
            }
        });
    }
    public void requestWeather(final String cityId){
        String address="http://guolin.tech/api/weather?cityid=" +cityId+
                "&key=40b03cdd88434177b8869c29e92204d4";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
           @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText=response.body().string();
               final HeWeather heWeather=Utility.handleWeatherResponse(responseText);
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       if(heWeather!=null && "ok".equals(heWeather.getStatus())){
                           SharedPreferences.Editor editor=PreferenceManager.
                                   getDefaultSharedPreferences(WeatherActivity.this).edit();
                           editor.putString("weather",responseText);
                           editor.apply();
                           WeatherId=heWeather.basic.getId();
                           showWeatherInfo(heWeather);
                       }else {
                           Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                       }
                       refreshLayout.setRefreshing(false);
                   }
               });

            }
            @Override
            public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(WeatherActivity.this,"获取天气信息失败",Toast.LENGTH_SHORT).show();
                            refreshLayout.setRefreshing(false);
                        }
                    });
            }
        });
        loadBasicPic();
    }
    private void showWeatherInfo(HeWeather heWeather){
        String cityName=heWeather.basic.city;
        String updateTime=heWeather.basic.update.loc;
        String degree=heWeather.now.tmp+"℃";
        String weatherInfo=heWeather.now.cond.getTxt();
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for(Daily_Forecast forecast:heWeather.getDaily_forecast()){
            View view= LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false);
            TextView dateText=(TextView)view.findViewById(R.id.date_text);
            TextView infoText=(TextView)view.findViewById(R.id.info_text);
            TextView maxText=(TextView)view.findViewById(R.id.max_text);
            TextView minText=(TextView)view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.cond.getTxt_d());
            maxText.setText(forecast.tmp.getMax());
            minText.setText(forecast.tmp.getMin());
            forecastLayout.addView(view);

        }
        String comfort="舒适度:"+heWeather.suggestion.comf.getTxt();
        String sports="运动指数:"+heWeather.suggestion.sport.getTxt();
        String carWash="洗车指数:"+heWeather.suggestion.cw.getTxt();
        String aqi=heWeather.aqi.city.getAqi()+"";
        String pm25=heWeather.aqi.city.getPm25()+"";
        comfortText.setText(comfort);
        sportsText.setText(sports);
        carWashText.setText(carWash);
        aqiText.setText(aqi);
        pm25Text.setText(pm25);
        weatherLayout.setVisibility(View.VISIBLE);
    }
    private void loadBasicPic(){
        String address="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    final String responseText=response.body().string();
                SharedPreferences.Editor editor=PreferenceManager.
                        getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic",responseText);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(responseText).into(imageView);
                    }
                });
            }
        });
    }

}
