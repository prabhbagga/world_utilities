package com.example.prabhdeep.currencyconvertor;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.prabhdeep.currencyconvertor.WeatherModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Weather extends AppCompatActivity {
    String CITY;
    TextView tvTemp;
    TextView tvHumidity;
    TextView tvPressure;
    TextView tvWind;
    TextView tvDescription;
    TextView tvLoc;
    Button btn;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CITY="delhi";
        setContentView(R.layout.activity_weather);
        tvTemp=findViewById(R.id.textView);
        tvHumidity=findViewById(R.id.textView6);
        tvWind=findViewById(R.id.textView3);
        tvPressure=findViewById(R.id.textView2);
        tvDescription=findViewById(R.id.textView4);
        tvLoc=findViewById(R.id.textView5);
        btn =findViewById(R.id.button);
        et=findViewById(R.id.etCity);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CITY=et.getText().toString();
                new DownloadWeather().execute();

            }
        });
        et.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    btn.performClick();
                    return true;
                }
                return false;
            }
        });

    }

    class DownloadWeather extends AsyncTask<Void,Void,WeatherModel>
    {
        @Override
        protected WeatherModel doInBackground(Void... voids) {
            WeatherModel weather = new WeatherModel();

            try {
                URL url=new URL("http://api.openweathermap.org/data/2.5/weather?q="+CITY+"&APPID=b32199da00a6b922e4747f076971dd45&units=metric");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                BufferedReader br= new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String buf=br.readLine();
                while(buf!=null){
                    sb.append(buf);
                    buf=br.readLine();
                }
                String data=sb.toString();
                JSONObject jObj= new JSONObject(data);

                JSONArray jArr=jObj.getJSONArray("weather");

                JSONObject jObj2 = jArr.getJSONObject(0);

                String description =jObj2.getString("main");
                Double temp = jObj.getJSONObject("main").getDouble("temp");
                Double wind=(jObj.getJSONObject("wind").getDouble("speed"));
                Double humidity= jObj.getJSONObject("main").getDouble("humidity");
                Double pressure= jObj.getJSONObject("main").getDouble("pressure");
                String country = jObj.getJSONObject("sys").getString("country");
                Log.d("123", "doInBackground: "+description);
                String city  = jObj.getString("name");
                weather.setCity(city);
                weather.setCountry(country);
                weather.setDescription(description);
                weather.setTemp(temp);
                weather.setWind(wind);
                weather.setPressure(pressure);
                weather.setHumidity(humidity);
                String city1 = weather.getCity();
                Log.d("123", "city "+city1);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;
        }

        @Override
        protected void onPostExecute(WeatherModel weatherModel) {
           tvTemp.setText(Double.toString(weatherModel.getTemp())+"°C");
           tvPressure.setText("Pressure:"+Double.toString(weatherModel.getPressure())+" mPa ");
            tvHumidity.setText("Humidity:"+Double.toString(weatherModel.getHumidity())+" % ");
            tvWind.setText("Wind:"+Double.toString(weatherModel.getWind())+" m/s ");
            tvLoc.setText(weatherModel.getCity()+","+weatherModel.getCountry());
            tvDescription.setText(weatherModel.getDescription());
            super.onPostExecute(weatherModel);
        }
    }
}
