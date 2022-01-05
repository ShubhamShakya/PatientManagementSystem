package com.example.pms_patientmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    Button doctorButton,patientButton;

    private TextView mLoginTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doctorButton=findViewById(R.id.buttonRegisterDoctor);
        patientButton=findViewById(R.id.buttonRegisterPatient);
        mLoginTV=(TextView) findViewById(R.id.tvRegisterLogin);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbarMainAcitivity);
        setSupportActionBar(toolbar);
        TextView mTitle=findViewById(R.id.toolbarTVMainActivity);
        mTitle.setText("PatientManagementSystem");
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        new Thread(new Runnable() {
            @Override
            public void run() {
                mLoginTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this,LogIn.class));
                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                doctorButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick (View v){
                        startActivity(new Intent(MainActivity.this, DoctorRegister.class));
                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                patientButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(),PatientRegister.class));
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main_activity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.itemContactUs){
            startActivity(new Intent(getApplicationContext(),ContactUs.class));
            Toast.makeText(getApplicationContext(),"Contact Us",Toast.LENGTH_SHORT).show();
        }else if(id==R.id.itemAboutUs){

            startActivity(new Intent(getApplicationContext(),AboutUs.class));
            Toast.makeText(getApplicationContext(),"About Us",Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}

