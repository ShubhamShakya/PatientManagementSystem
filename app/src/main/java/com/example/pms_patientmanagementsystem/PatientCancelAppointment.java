package com.example.pms_patientmanagementsystem;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class PatientCancelAppointment extends AppCompatActivity {
    RecyclerView recyclerView;
    MyPatientCancelAppointmentAdapter myPatientCancelAppointmentAdapter;
    String patientAppointmentDatabaseName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_cancel_appointment);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbarPatientCancelAppointment);
        setSupportActionBar(toolbar);
        TextView mTitle=(TextView) toolbar.findViewById(R.id.toolbarTVPatientCancelAppointment);
        mTitle.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String temp=firebaseUser.getEmail();
        int i=temp.indexOf('@');
        patientAppointmentDatabaseName=temp.substring(0,i);

        recyclerView=(RecyclerView) findViewById(R.id.patientCancelRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<patientAppointmentDataFromFirebase> options =
                new FirebaseRecyclerOptions.Builder<patientAppointmentDataFromFirebase>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(patientAppointmentDatabaseName), patientAppointmentDataFromFirebase.class)
                        .build();
        myPatientCancelAppointmentAdapter=new MyPatientCancelAppointmentAdapter(options);
        recyclerView.setAdapter(myPatientCancelAppointmentAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        myPatientCancelAppointmentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myPatientCancelAppointmentAdapter.stopListening();
    }

}
