package com.example.amos.travelapp.Pages;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amos.travelapp.R;

import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class Currency extends AppCompatActivity {
    EditText inText;
    TextView outText;
    Spinner spinnerIn;
    Spinner spinnerOut;
    StringBuffer stringBuffer;
    private static final String[]currency = {
            "SGD","AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL", "BSD", "BTC", "BTN", "BWP", "BYR", "BZD", "CAD", "CDF", "CHF", "CLF", "CLP", "CNY", "COP", "CRC", "CUC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EEK", "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "GBP", "GEL", "GGP", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS", "IMP", "INR", "IQD", "IRR", "ISK", "JEP", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KMF", "KPW", "KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LTL", "LVL", "LYD", "MAD", "MDL", "MGA", "MKD", "MMK", "MNT", "MOP", "MRO", "MTL", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLL", "SOS", "SRD", "STD", "SVC", "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TWD", "TZS", "UAH", "UGX", "USD", "UYU", "UZS", "VEF", "VND", "VUV", "WST", "XAF", "XAG", "XAU", "XCD", "XDR", "XOF","XPD", "XPF", "XPT", "YER", "ZAR", "ZMK", "ZMW", "ZWL"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Currency Converter");
        outText = (TextView)findViewById(R.id.outputtext);
        inText = (EditText)findViewById(R.id.inputtext);
        spinnerIn = (Spinner)findViewById(R.id.spinnerIN);
        spinnerOut = (Spinner)findViewById(R.id.spinnerOUT);
        Button btn = (Button)findViewById(R.id.calculatebtn);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Currency.this,
                android.R.layout.simple_spinner_item, currency);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIn.setAdapter(adapter);
        spinnerOut.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetWebPageTask().execute("https://openexchangerates.org/api/latest.json?app_id=56740999d7864866a34fa3259ef852f8");

            }
        });
    }
    private String getWebsite(String address) { //Open website
        stringBuffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            URL url = new URL(address);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
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
    public class GetWebPageTask extends AsyncTask<String,Void,String>{ //Get JSON information
        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(),"LOADING...",Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("OnPostExecute", result);
            super.onPostExecute(result);
            try{
                JSONObject jsonObj = new JSONObject(String.valueOf(stringBuffer));
                JSONObject RatesObject = jsonObj.getJSONObject("rates");
                Double rateIn = RatesObject.getDouble(spinnerIn.getSelectedItem().toString());
                Double rateOut = RatesObject.getDouble(spinnerOut.getSelectedItem().toString());
                Double valueIn = Double.parseDouble(inText.getText().toString());
                Double valueOut = (valueIn/rateIn)*rateOut;
                String out = String.valueOf(valueOut);
                outText.setText(out);
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