package com.example.amos.travelapp.Pages;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amos.travelapp.R;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Weather extends AppCompatActivity {
    TextView weathertext;
    TextView humiditytext;
    TextView temperaturetext;
    TextView windtext;
    StringBuffer stringBuffer;
    ImageView weatherimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Weather");
        weathertext = (TextView)findViewById(R.id.weatherdetail);
        humiditytext = (TextView)findViewById(R.id.Humidity);
        temperaturetext = (TextView)findViewById(R.id.Temperature);
        windtext = (TextView)findViewById(R.id.Wind);
//        Button btn = (Button)findViewById(R.id.Weatherbutton);
        new GetWebPageTask().execute("http://api.openweathermap.org/data/2.5/weather?q=Singapore&appid=7dd572bdb53b94386c2ba158d91d0bb7");
        weatherimage = (ImageView)findViewById(R.id.weatherimage);


//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new GetWebPageTask().execute("http://api.openweathermap.org/data/2.5/weather?q=Singapore&appid=7dd572bdb53b94386c2ba158d91d0bb7");
//            }
//        });
    }
    private String getWebsite(String address) { //Open the website URL
        stringBuffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            URL url = new URL(address);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuffer.toString();
    }
    public class GetWebPageTask extends AsyncTask<String,Void,String>{ //Get JSON website information
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("OnPostExecute", result);
            super.onPostExecute(result);
            try{
                JSONObject jsonObj = new JSONObject(String.valueOf(stringBuffer));
                JSONArray jsonArray = jsonObj.getJSONArray("weather");
                JSONObject weatherObject = jsonArray.getJSONObject(0);
                String weather = weatherObject.getString("main");
                String description = weatherObject.getString("description");
                weathertext.setText(weather+" ("+description+")"); //Set Weather detail
                JSONObject mainObject = jsonObj.getJSONObject("main");
                Double temperature = (double)Math.round((mainObject.getDouble("temp") - 273.15)*100d)/100d; //Convert kelvin to °C
                temperaturetext.setText(temperature.toString()+"°C"); //Set Temperature
                String humidity = mainObject.getString("humidity");
                humiditytext.setText(humidity+"%");  //Set Humidity
                JSONObject windObject = jsonObj.getJSONObject("wind");
                Double windspeed = (double)Math.round((windObject.getDouble("speed")*3.6)*100d)/100d; //Convert m/s to Km/h
                windtext.setText(windspeed.toString()+"km/h");



                String icon = weatherObject.getString("icon");
                switch (icon){ //Change weather icon
                    case "01d":
                        weatherimage.setImageResource(R.drawable.a01d);
                        break;
                    case "01n":
                        weatherimage.setImageResource(R.drawable.a01n);
                        break;
                    case "02d":
                        weatherimage.setImageResource(R.drawable.a02d);
                        break;
                    case "02n":
                        weatherimage.setImageResource(R.drawable.a02n);
                        break;
                    case "03d":
                        weatherimage.setImageResource(R.drawable.a03d);
                        break;
                    case "03n":
                        weatherimage.setImageResource(R.drawable.a03n);
                        break;
                    case "04d":
                        weatherimage.setImageResource(R.drawable.a04d);
                        break;
                    case "04n":
                        weatherimage.setImageResource(R.drawable.a04n);
                        break;
                    case "09d":
                        weatherimage.setImageResource(R.drawable.a09d);
                        break;
                    case "09n":
                        weatherimage.setImageResource(R.drawable.a09n);
                        break;
                    case "10d":
                        weatherimage.setImageResource(R.drawable.a10d);
                        break;
                    case "10n":
                        weatherimage.setImageResource(R.drawable.a10n);
                        break;
                    case "11d":
                        weatherimage.setImageResource(R.drawable.a11d);
                        break;
                    case "11n":
                        weatherimage.setImageResource(R.drawable.a11n);
                        break;
                    case "13d":
                        weatherimage.setImageResource(R.drawable.a13d);
                        break;
                    case "13n":
                        weatherimage.setImageResource(R.drawable.a13n);
                        break;
                    case "50d":
                        weatherimage.setImageResource(R.drawable.a50d);
                        break;
                    case "50n":
                        weatherimage.setImageResource(R.drawable.a50n);
                        break;
                }


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplication(),"Requires Internet Connection",Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... url) {
            return getWebsite(url[0]);
        }

    }
    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;

    }

}
