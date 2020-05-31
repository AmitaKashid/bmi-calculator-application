package com.example.user.bmicalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BMIResultActivity extends AppCompatActivity {

    TextView tvResult,tvfUnderWeight,tvUnderWeight,tvNormal,tvOverWeight,tvObese;
    Button btnBack,btnShare,btnSave;
    SharedPreferences sp2,sp1;

    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiresult);

        tvResult = findViewById(R.id.tvResult);
        tvfUnderWeight = findViewById(R.id.tvfUnderWeight);
        tvUnderWeight = findViewById(R.id.tvUnderWeight);
        tvNormal = findViewById(R.id.tvNormal);
        tvOverWeight=findViewById(R.id.tvOverWeight);
        tvObese = findViewById(R.id.tvObese);
        btnBack = findViewById(R.id.btnBack);
        btnShare = findViewById(R.id.btnShare);
        btnSave = findViewById(R.id.btnSave);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("info");


        sp2 = getSharedPreferences("f2",MODE_PRIVATE);
        final float BMI = sp2.getFloat("bmi",0);

        sp1 = getSharedPreferences("f1",MODE_PRIVATE);
        final String Name = sp1.getString("n","");
        final int Age = Integer.parseInt(sp1.getString("a",""));
        final Long Phone = Long.parseLong(sp1.getString("p",""));
        final String Gender = sp1.getString("g","");

        String res = "";


        if(BMI<17.0)
        {
            tvResult.setText("Your BMI is "+BMI+" and you are severely underweight.");
            tvfUnderWeight.setTextColor(Color.parseColor("#ff0000"));
            res = "underweight";
        }
        else if (17.0<=BMI & BMI <= 18.4 )
        {
            tvResult.setText("Your BMI is "+BMI+" and you are underweight.");
            tvUnderWeight.setTextColor(Color.parseColor("#ff0000"));
            res = "underweight";
        }
        else if (18.5<=BMI & BMI <= 24.9 )
        {
            tvResult.setText("Your BMI is "+BMI+" and you are normal.");
            tvNormal.setTextColor(Color.parseColor("#ff0000"));
            res = "normal";
        }
        else if (25.0<=BMI & BMI <= 29.9 )
        {
            tvResult.setText("Your BMI is "+BMI+" and you are overweight.");
            tvOverWeight.setTextColor(Color.parseColor("#ff0000"));
            res = "overweight";
        }
        else if ( BMI>29.9  ) {
            tvResult.setText("Your BMI is " + BMI + " and you are obese.");
            tvObese.setTextColor(Color.parseColor("#ff0000"));
            res = "obese";
        }



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BMIResultActivity.this,PhysicalActivity.class);
                startActivity(i);
            }
        });

        final String finalRes = res;
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent a = new Intent(Intent.ACTION_SEND);
                a.setType("text/plain");
                a.putExtra(Intent.EXTRA_TEXT,"Name - "+Name+"\n"+"Age -"+Age+"\n"+"Phone -"+Phone+"\n"+"Sex -"+Gender+"\n"+"BMI = "+BMI+"\n"+"you are "+ finalRes);
                startActivity(a);

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                Float bmi = BMI;
                String result = finalRes;

                Toast.makeText(BMIResultActivity.this, "date="+date+" bmi = "+bmi+" result = "+result, Toast.LENGTH_SHORT).show();

                Info f = new Info(date,result,bmi);
                myRef.push().setValue(f);
                Toast.makeText(BMIResultActivity.this, "record added", Toast.LENGTH_SHORT).show();


            }
        });

    }
}
