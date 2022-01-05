package com.example.pms_patientmanagementsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PatientBookAppointment extends AppCompatActivity {

    RecyclerView recyclerView;
    MyPatientDoctorAdapter myPatientDoctorAdapter;
    public String patientEmailForDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_book_appointment);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbarPatientBookAppointment);
        setSupportActionBar(toolbar);
        TextView mTitle=(TextView) toolbar.findViewById(R.id.toolbarTVPatientBookAppointment);
        mTitle.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        patientEmailForDatabase= getIntent().getStringExtra("patientEmail");

        recyclerView=findViewById(R.id.recyclerViewPatientBookAppointment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //myPatientDoctorAdapter=new MyPatientDoctorAdapter(this,list);

                FirebaseRecyclerOptions<patientDataFromFirebase> options =
                        new FirebaseRecyclerOptions.Builder<patientDataFromFirebase>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctor"), patientDataFromFirebase.class)
                                .build();

                myPatientDoctorAdapter=new MyPatientDoctorAdapter(options);
                recyclerView.setAdapter(myPatientDoctorAdapter);
            }

    @Override
    protected void onStart() {
        super.onStart();
        myPatientDoctorAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myPatientDoctorAdapter.stopListening();
    }
}




