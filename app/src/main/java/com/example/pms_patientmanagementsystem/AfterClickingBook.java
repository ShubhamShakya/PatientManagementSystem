package com.example.pms_patientmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import devs.mulham.horizontalcalendar.utils.Utils;

public class AfterClickingBook extends AppCompatActivity  {
    TextView m9t012,m1to3,m4to7,m8to11;
    TextInputLayout mDisease;
    Button mConfirmAppointment;
    ProgressBar mProgressBar;

    String string9to12,string1to3,string4to7,string8to11;
    String patientEmailForDatabase;
    String patientAppointmentDate;
    String doctorMail,doctorName;
    String timeOfAppointment,stringDisease;
    int indexFromActivity;
    FirebaseDatabase firebaseDatabasePatient;
    DatabaseReference rootPatient,rootDoctor;
    String doctorMailForDatabase;
    String patientEmail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.after_clicking_book);

        m9t012=(TextView) findViewById(R.id.tv9to12);
        m1to3=(TextView) findViewById(R.id.tv1to3);
        m4to7=(TextView) findViewById(R.id.tv4to7);
        m8to11=(TextView) findViewById(R.id.tv8to11);
        mConfirmAppointment=(Button) findViewById(R.id.buttonConfirmAppointment);
        mProgressBar=(ProgressBar) findViewById(R.id.afterClickingBookProgressBar);
        mDisease= findViewById(R.id.etDiseaseAfterClickingBook);



        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        patientEmail=firebaseUser.getEmail();
        int atTheRateSymbolIndex=patientEmail.indexOf('@');
        patientEmailForDatabase = patientEmail.substring(0,atTheRateSymbolIndex);


        doctorMail=getIntent().getStringExtra("doctorMail");
        int atRateSymbol=doctorMail.indexOf('@');
        doctorMailForDatabase=doctorMail.substring(0,atRateSymbol);
        doctorName=getIntent().getStringExtra("doctorName");

        firebaseDatabasePatient=FirebaseDatabase.getInstance();
        rootPatient=firebaseDatabasePatient.getReference().child(patientEmailForDatabase);
        rootDoctor=firebaseDatabasePatient.getReference().child(doctorMailForDatabase);

        Calendar startDate=Calendar.getInstance();
        //startDate.add(Calendar.DAY_OF_MONTH,0);


        Calendar endDate=Calendar.getInstance();
        endDate.add(Calendar.DAY_OF_MONTH,5);

        HorizontalCalendar horizontalCalendar=new HorizontalCalendar.Builder(this,R.id.horizontalCalendar)
                .range(startDate,endDate)
                .datesNumberOnScreen(1)
                .defaultSelectedDate(startDate)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                patientAppointmentDate=(date.getTime().toString()).substring(0,10);
                Toast.makeText(getApplicationContext(),patientAppointmentDate,Toast.LENGTH_SHORT).show();
            }
        });

        mConfirmAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                mConfirmAppointment.setVisibility(View.INVISIBLE);
                stringDisease=mDisease.getEditText().getText().toString().trim();
                confirmButtonThread thread=new confirmButtonThread();
                thread.run();

            }
        });

       // indexFromActivity=getIntent().getIntExtra("position",0);


        m9t012.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string9to12=m9t012.getText().toString();
                m9t012.setBackgroundResource(R.color.green);
                m1to3.setBackgroundResource(R.color.grey);
                m4to7.setBackgroundResource(R.color.grey);
                m8to11.setBackgroundResource(R.color.grey);
                string1to3=string4to7=string8to11="";

            }
        });
        m1to3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string1to3=m1to3.getText().toString();
                m1to3.setBackgroundResource(R.color.green);
                m9t012.setBackgroundResource(R.color.grey);
                m4to7.setBackgroundResource(R.color.grey);
                m8to11.setBackgroundResource(R.color.grey);
                string9to12=string4to7=string8to11="";

            }
        });
        m4to7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string4to7=m4to7.getText().toString();
                m4to7.setBackgroundResource(R.color.green);
                m9t012.setBackgroundResource(R.color.grey);
                m1to3.setBackgroundResource(R.color.grey);
                m8to11.setBackgroundResource(R.color.grey);
                string9to12=string1to3=string8to11="";

            }
        });
        m8to11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string8to11=m8to11.getText().toString();
                m8to11.setBackgroundResource(R.color.green);
                m9t012.setBackgroundResource(R.color.grey);
                m4to7.setBackgroundResource(R.color.grey);
                m1to3.setBackgroundResource(R.color.grey);
                string9to12=string4to7=string1to3="";

            }
        });




    }

    class confirmButtonThread implements Runnable{

        @Override
        public void run() {
            saveDataToFirebase();
        }
    }

    private void saveDataToFirebase() {

        if(!string9to12.isEmpty()){
            timeOfAppointment=string9to12.trim();
        }else if(!string1to3.isEmpty()){
            timeOfAppointment=string1to3.trim();
        }else if(!string4to7.isEmpty()){
            timeOfAppointment=string4to7.trim();
        }else if(!string8to11.isEmpty()){
            timeOfAppointment=string8to11.trim();
        }else{
            timeOfAppointment=string9to12.trim();
        }

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("date", patientAppointmentDate);
        hashMap.put("time", timeOfAppointment);
        hashMap.put("doctorMail", doctorMail);
        hashMap.put("doctorName", doctorName);
        hashMap.put("disease", stringDisease);
        rootPatient.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "Patient appointment added", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Patient appointment failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

        });





        HashMap<String, String> map = new HashMap<>();
        map.put("patientMail", patientEmail);
        map.put("date", patientAppointmentDate);
        map.put("time", timeOfAppointment);
        map.put("disease",stringDisease);
        rootDoctor.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Doctor's Appointment added", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Doctor Appointment failed", Toast.LENGTH_SHORT).show();
                }
            }
        });



        Toast.makeText(getApplicationContext(),"Appointment Confirmed",Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.INVISIBLE);
        mConfirmAppointment.setVisibility(View.VISIBLE);
        startActivity(new Intent(getApplicationContext(),BookingConfirmationScreen.class));

    }


}
