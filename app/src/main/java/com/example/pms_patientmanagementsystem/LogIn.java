package com.example.pms_patientmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity {
    private Button mLoginButtonDoctor;
    private Button mLoginButtonPatient;

    private TextView mRegisterTV;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mLoginButtonDoctor=(Button)findViewById(R.id.buttonLoginDoctor);
        mLoginButtonPatient=(Button)findViewById(R.id.buttonLoginPatient);
        mRegisterTV=(TextView) findViewById(R.id.tvLoginRegister);

        mLoginButtonDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,DoctorLogin.class));
            }
        });

        mLoginButtonPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,PatientLogin.class));
            }
        });

        mRegisterTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}
