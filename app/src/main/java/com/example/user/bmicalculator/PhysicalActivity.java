package com.example.user.bmicalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PhysicalActivity extends AppCompatActivity {

    TextView tvWelcome,fTemp;
    EditText etWeight;
    Spinner spnFeet,spnInch;
    Button btnCalculate,btnHistory;
    SharedPreferences sp,sp2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical);
        tvWelcome = findViewById(R.id.tvWelcome);
        spnFeet = findViewById(R.id.spnFeet);
        spnInch = findViewById(R.id.spnInch);
        etWeight = findViewById(R.id.etWeight);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnHistory = findViewById(R.id.btnHistory);
        fTemp = findViewById(R.id.fTemp);


        final ArrayList<String> F = new ArrayList<>();
        F.add("0"); F.add("1"); F.add("2"); F.add("3"); F.add("4");
        F.add("5"); F.add("6"); F.add("7"); F.add("8"); F.add("9");

        ArrayAdapter arrayAdapter1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,F);
        spnFeet.setAdapter(arrayAdapter1);

        final ArrayList<String> I = new ArrayList<>();
        I.add("0"); I.add("1"); I.add("2"); I.add("3"); I.add("4");
        I.add("5"); I.add("6"); I.add("7"); I.add("8"); I.add("9");

        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,I);
        spnInch.setAdapter(arrayAdapter2);




        sp2 = getSharedPreferences("f2",MODE_PRIVATE);




        tvWelcome = findViewById(R.id.tvWelcome);
        sp = getSharedPreferences("f1",MODE_PRIVATE);
        String name = sp.getString("n","");

        tvWelcome.setText("Welcome "+ name);

        String loc = "MUMBAI";
        MeraTask t1 = new MeraTask();
        String w1 = "http://api.openweathermap.org/data/2.5/weather?units=metric";
        String w2  = "&q="+loc;
        String w3 = "&appid=c6e315d09197cec231495138183954bd";

        String w = w1+w2+w3;

        t1.execute(w);



        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id1 = spnFeet.getSelectedItemPosition();
                String feet = F.get(id1);
                int Feet = Integer.parseInt(feet);

                int id2 = spnInch.getSelectedItemPosition();
                String inch = F.get(id2);
                int Inch = Integer.parseInt(inch);

                float h = (Feet*10+Inch)/10;

                float height = (float) (h/3.28);
                float weight = Float.parseFloat(etWeight.getText().toString());

                Float BMI = weight/(height*height);

                SharedPreferences.Editor editor = sp2.edit();
                editor.putFloat("bmi",BMI);
                editor.commit();

                Intent i = new Intent(PhysicalActivity.this,BMIResultActivity.class);
                startActivity(i);

            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(PhysicalActivity.this,FHistoryActivity.class));
                Intent k = new Intent(PhysicalActivity.this,FHistoryActivity.class);
                startActivity(k);
            }
        });
        }


        class MeraTask extends AsyncTask<String,Void,Double>
        {
            double temp;


            @Override
            protected Double doInBackground(String... strings) {
                String json="", line = "";
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.connect();

                    InputStreamReader isr = new InputStreamReader(con.getInputStream());
                    BufferedReader br = new BufferedReader(isr);

                    while ((line = br.readLine())!=null){
                        json = json + line + "\n";
                    }

                    JSONObject o = new JSONObject(json);
                    JSONObject p = o.getJSONObject("main");
                    temp = p.getDouble("temp");
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                return temp;
            }

            @Override
            protected void onPostExecute(Double aDouble) {
                super.onPostExecute(aDouble);
                fTemp.setText("temperature = "+aDouble);
            }
        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.website)
        {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://"+"www.google.com"));
            startActivity(i);
        }

        if(item.getItemId()==R.id.about)
        {
            Toast toast = Toast.makeText(this, "made by Amita Kashid", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
            toast.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
