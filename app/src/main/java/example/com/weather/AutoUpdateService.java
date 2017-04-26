package example.com.weather;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.io.IOException;

import example.com.weather.gson.HeWeather;
import example.com.weather.util.HttpUtil;
import example.com.weather.util.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {
    public AutoUpdateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updatePicture();
        updateWeather();
        AlarmManager manager=(AlarmManager)getSystemService(ALARM_SERVICE);
        int time=8*60*60*1000;
        long triggerAtTime= SystemClock.elapsedRealtime()+time;
        Intent i=new Intent(this,AutoUpdateService.class);
        PendingIntent pi=PendingIntent.getService(this,0,i,0);
        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent, flags, startId);
    }
    /*
    更新天气信息
     */
    private void updateWeather(){
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String weather=preferences.getString("weather",null);
        if(weather!=null){
            final HeWeather heWeather= Utility.handleWeatherResponse(weather);
            String id=heWeather.basic.getId();
            String address="http://guolin.tech/api/weather?cityid=" +id+
                    "&key=40b03cdd88434177b8869c29e92204d4";
            HttpUtil.sendOkHttpRequest(address, new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                            String text=response.body().string();
                    HeWeather heWeather1=Utility.handleWeatherResponse(text);
                    if(heWeather!=null && heWeather.status.equals("ok")){
                        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("weather",text);
                        editor.apply();
                    }
                }
            });
        }
    }
    private void updatePicture(){
        String address="http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(address, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String text=response.body().string();
                    SharedPreferences.Editor editor=PreferenceManager.
                            getDefaultSharedPreferences(AutoUpdateService.this).edit();
                    editor.putString("bing_pic",text);
                    editor.apply();

            }
        });
    }
}
