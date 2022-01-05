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

public class PatientPreviousAppointment extends AppCompatActivity {
    RecyclerView mRecyclerViewPatientPreviousAppointment;
    MyPatientPreviousAppointmentAdapter myPatientPreviousAppointmentAdapter;
    String patientPreviousAppointmentDatabaseName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_previous_appointment);

        mRecyclerViewPatientPreviousAppointment=findViewById(R.id.recyclerViewPatientPreviousAppointment);
        mRecyclerViewPatientPreviousAppointment.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String temp=firebaseUser.getEmail();
        int i=temp.indexOf('@');
        patientPreviousAppointmentDatabaseName="prev" + temp.substring(0,i);

        FirebaseRecyclerOptions<previousAppointmentPatientDataFromFirebase> options =
                new FirebaseRecyclerOptions.Builder<previousAppointmentPatientDataFromFirebase>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(patientPreviousAppointmentDatabaseName), previousAppointmentPatientDataFromFirebase.class)
                        .build();

        myPatientPreviousAppointmentAdapter=new MyPatientPreviousAppointmentAdapter(options);
        mRecyclerViewPatientPreviousAppointment.setAdapter(myPatientPreviousAppointmentAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        myPatientPreviousAppointmentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myPatientPreviousAppointmentAdapter.stopListening();
    }
}
