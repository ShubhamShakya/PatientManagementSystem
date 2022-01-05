package com.example.pms_patientmanagementsystem;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorPreviousAppointment extends AppCompatActivity {
    RecyclerView mRecyclerViewDoctorPreviousAppointment;
    MyDoctorPreviousAppointmentAdapter myDoctorPreviousAppointmentAdapter;
    String doctorPreviousAppointmentDatabaseName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_previous_appointment);

        mRecyclerViewDoctorPreviousAppointment=findViewById(R.id.recyclerViewDoctorPreviousAppointment);
        mRecyclerViewDoctorPreviousAppointment.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String temp=firebaseUser.getEmail();
        int i=temp.indexOf('@');
       doctorPreviousAppointmentDatabaseName="prev" + temp.substring(0,i);

        FirebaseRecyclerOptions<previousAppointmentDoctorDataFromFirebase> options =
                new FirebaseRecyclerOptions.Builder<previousAppointmentDoctorDataFromFirebase>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(doctorPreviousAppointmentDatabaseName), previousAppointmentDoctorDataFromFirebase.class)
                        .build();

        myDoctorPreviousAppointmentAdapter=new MyDoctorPreviousAppointmentAdapter(options);
        mRecyclerViewDoctorPreviousAppointment.setAdapter(myDoctorPreviousAppointmentAdapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        myDoctorPreviousAppointmentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myDoctorPreviousAppointmentAdapter.stopListening();
    }
}
