package com.example.prabhdeep.currencyconvertor;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.example.prabhdeep.currencyconvertor.Consts.Australian_dollar;
import static com.example.prabhdeep.currencyconvertor.Consts.Brazilian_real;
import static com.example.prabhdeep.currencyconvertor.Consts.Bulgarian_iev;
import static com.example.prabhdeep.currencyconvertor.Consts.Canadian_dollar;
import static com.example.prabhdeep.currencyconvertor.Consts.Chinese_yuan_renminbi;
import static com.example.prabhdeep.currencyconvertor.Consts.Croatian_kuna;
import static com.example.prabhdeep.currencyconvertor.Consts.Czech_koruna;
import static com.example.prabhdeep.currencyconvertor.Consts.Danish_krone;
import static com.example.prabhdeep.currencyconvertor.Consts.Euro;
import static com.example.prabhdeep.currencyconvertor.Consts.Hong_Kong_dollar;
import static com.example.prabhdeep.currencyconvertor.Consts.Hungarian_forint;
import static com.example.prabhdeep.currencyconvertor.Consts.Indian_rupee;
import static com.example.prabhdeep.currencyconvertor.Consts.Indonesian_rupiah;
import static com.example.prabhdeep.currencyconvertor.Consts.Israeli_shekel;
import static com.example.prabhdeep.currencyconvertor.Consts.Japanese_yen;
import static com.example.prabhdeep.currencyconvertor.Consts.Malaysian_ringgit;
import static com.example.prabhdeep.currencyconvertor.Consts.Mexican_peso;
import static com.example.prabhdeep.currencyconvertor.Consts.New_Zealand_dollar;
import static com.example.prabhdeep.currencyconvertor.Consts.Norwegian_krone;
import static com.example.prabhdeep.currencyconvertor.Consts.Philippine_piso;
import static com.example.prabhdeep.currencyconvertor.Consts.Polish_zloty;
import static com.example.prabhdeep.currencyconvertor.Consts.Pound_sterling;
import static com.example.prabhdeep.currencyconvertor.Consts.Romanian_leu;
import static com.example.prabhdeep.currencyconvertor.Consts.Russian_rouble;
import static com.example.prabhdeep.currencyconvertor.Consts.Singapore_dollar;
import static com.example.prabhdeep.currencyconvertor.Consts.South_African_rand;
import static com.example.prabhdeep.currencyconvertor.Consts.South_Korean_won;
import static com.example.prabhdeep.currencyconvertor.Consts.Swedish_krona;
import static com.example.prabhdeep.currencyconvertor.Consts.Swiss_franc;
import static com.example.prabhdeep.currencyconvertor.Consts.Thai_baht;
import static com.example.prabhdeep.currencyconvertor.Consts.Turkish_lira;
import static com.example.prabhdeep.currencyconvertor.Consts.US_dollar;

public class CurrencyConverter extends AppCompatActivity {
    TextView tvRate;
    String CURRENCY_1;
    String CURRENCY_2;
    Double Value;
    public static final String[] currencies ={"Select Currency","US Dollars","Japanese Yen","Bulgarian Iev","Czech Koruna","Euro","Danish Krone","Pound Sterling",
            "Hungarian Forint","Polish Zloty","Romaninan Leu","Swedish Krona","Swiss Franc","Norwegian Krone","Croatian Kuna","Russian Rouble",
            "Turkish Lira","Australian Dollar","Brazilian Real","Canadian Dollar","Chinese Yuan Renminbi","Hong Kong Dollar","Indonesian Rupiah",
            "Israeli Shekel","Indian Rupee","South Korean Won","Mexican Peso","Malaysian Ringgit","New Zealand Dollar","Philippine Piso","Singapore Dollar",
            "Thai Baht","South African Rand"};
    public static final String[] symbols={"",US_dollar,Japanese_yen,Bulgarian_iev,Czech_koruna,Euro,Danish_krone,Pound_sterling,Hungarian_forint,Polish_zloty,
            Romanian_leu,Swedish_krona,Swiss_franc,Norwegian_krone,Croatian_kuna,Russian_rouble,Turkish_lira,Australian_dollar,Brazilian_real,Canadian_dollar,
            Chinese_yuan_renminbi,Hong_Kong_dollar,Indonesian_rupiah,Israeli_shekel,Indian_rupee,South_Korean_won,Mexican_peso,Malaysian_ringgit,New_Zealand_dollar,
   Philippine_piso,Singapore_dollar,Thai_baht,South_African_rand };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currencyconverter);
        tvRate=findViewById(R.id.tvRate);
        Spinner spinner1=findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,currencies);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CURRENCY_1=symbols[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        Spinner spinner2=findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            CURRENCY_2=symbols[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Button btnConvert= findViewById(R.id.btnConvert);
        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etValue=findViewById(R.id.etValue);
                Value = Double.valueOf(etValue.getText().toString());
                new DownloadExchangeRate().execute();
            }
        });
    }


class DownloadExchangeRate extends AsyncTask<Void, Void, Double> {

    @Override
    protected Double doInBackground(Void... voids) {

       double s1=0.0;
        try {
            URL url = new URL("https://api.fixer.io/latest?base="+CURRENCY_1+"&symbols="+CURRENCY_2);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String buf = br.readLine();
            while (buf != null) {
                sb.append(buf);
                buf = br.readLine();
            }
            String data = sb.toString();
            JSONObject er = new JSONObject(data);
            s1 = er.getJSONObject("rates").getDouble(CURRENCY_2);
            Log.d("123", "doInBackground: "+s1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s1;


    }

    @Override
    protected void onPostExecute(Double s) {
        super.onPostExecute(s);
        Double ans= s*Value;
        Log.d("456", "onPostExecute: "+Value);
        String ans1 = Double.toString(ans);
        Log.d("456", "onPostExecute: "+ans);
        tvRate.setText(ans1);

    }
}
}