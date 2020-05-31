package com.example.user.bmicalculator;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText etForgotUsername;
    Button btnResetPassword;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etForgotUsername = findViewById(R.id.etForgotUsername);
        btnResetPassword=findViewById(R.id.btnResetPassword);
        firebaseAuth = FirebaseAuth.getInstance();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un = etForgotUsername.getText().toString();
                firebaseAuth.sendPasswordResetEmail(un).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful())
                       {
                           Toast.makeText(ForgotPasswordActivity.this, "check email", Toast.LENGTH_SHORT).show();

                           Intent i = new Intent(ForgotPasswordActivity.this,MainActivity.class);
                           startActivity(i);
                           Toast.makeText(ForgotPasswordActivity.this, "main activity", Toast.LENGTH_SHORT).show();
                       }
                       else
                       {
                           Toast.makeText(ForgotPasswordActivity.this, "issue", Toast.LENGTH_SHORT).show();
                       }
                    }
                });
            }
        });
    }
}
