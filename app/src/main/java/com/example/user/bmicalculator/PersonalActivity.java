package com.example.user.bmicalculator;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PersonalActivity extends AppCompatActivity
{

    EditText etName,etAge,etPhone;
    Button btnNext,btnLogOut;
    Spinner spnGender;
    FirebaseAuth firebaseAuth;
    TextView tvResult1,tvULoc;
    FirebaseUser user;
    SharedPreferences sp;
    String tf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etPhone = findViewById(R.id.etPhone);
        btnNext = findViewById(R.id.btnNext);
        btnLogOut = findViewById(R.id.btnLogOut);
        spnGender = findViewById(R.id.spnGender);
        tvResult1 = findViewById(R.id.tvResult1);
        tvULoc = findViewById(R.id.tvULoc);




        firebaseAuth = FirebaseAuth.getInstance();

        final ArrayList<String> s = new ArrayList<>();
        s.add("Female");
        s.add("Male");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,s);
        spnGender.setAdapter(arrayAdapter);

        sp = getSharedPreferences("f1",MODE_PRIVATE);




        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String age = etAge.getText().toString();
                String phone = etPhone.getText().toString();


                int id = spnGender.getSelectedItemPosition();
                String gender = s.get(id);

                if(name.length()==0)
                {
                    etName.setError("name is empty");
                    etName.requestFocus();
                    return;
                }

                if(age.length() == 0)
                {
                    etAge.setError("age is empty");
                    etAge.requestFocus();
                    return;
                }
                if(phone.length()==0)
                {
                    etPhone.setError("phone is empty");
                    etPhone.requestFocus();
                    return;
                }
                if(phone.length()<=9)
                {
                    etPhone.setError("invalid phone number");
                    etPhone.requestFocus();
                    return;
                }
                if(gender.length()==0)
                {
                    Toast toast = Toast.makeText(PersonalActivity.this, "please select the gender", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    toast.show();
                    spnGender.requestFocus();
                    return;
                }

                String loc = "MUMBAI";
                MeraTask t1 = new MeraTask();
                String w1 = "http://api.openweathermap.org/data/2.5/weather?units=metric";
                String w2  = "&q="+loc;
                String w3 = "&appid=c6e315d09197cec231495138183954bd";

                String w = w1+w2+w3;

                t1.execute(w);




                SharedPreferences.Editor editor = sp.edit();
                editor.putString("n",name);
                editor.putString("a",age);
                editor.putString("p",phone);
                editor.putString("g",gender);
                editor.commit();

                Intent i = new Intent(PersonalActivity.this,PhysicalActivity.class);
                startActivity(i);
                Toast.makeText(PersonalActivity.this, "physical", Toast.LENGTH_SHORT).show();

            }
        });


        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent i = new Intent(PersonalActivity.this,MainActivity.class);
                startActivity(i);
                finish();
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
            tvULoc.setText("temperature :"+aDouble);
            //tvResult1.setText("temperature :"+aDouble);
            //tf = String.valueOf(aDouble);
        }
    }




}
