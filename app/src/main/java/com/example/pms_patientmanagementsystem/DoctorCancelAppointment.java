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

public class DoctorCancelAppointment extends AppCompatActivity {
    RecyclerView mCancelAppointmentDoctorRecyclerView;
    MyDoctorCancelAppointmentAdapter myDoctorCancelAppointmentAdapter;
    String doctorAppointmentDatabaseName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_cancel_appointment);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbarDoctorCancelAppointment);
        setSupportActionBar(toolbar);
        TextView mTitle=(TextView) toolbar.findViewById(R.id.toolbarTVDoctorCancelAppointment);
        mTitle.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String temp=firebaseUser.getEmail();
        int i=temp.indexOf('@');
        doctorAppointmentDatabaseName=temp.substring(0,i);

        mCancelAppointmentDoctorRecyclerView=findViewById(R.id.doctorCancelAppointmentRecyclerView);
        mCancelAppointmentDoctorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<doctorCancelAppointmentDataFromFirebase> options=
                new FirebaseRecyclerOptions.Builder<doctorCancelAppointmentDataFromFirebase>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child(doctorAppointmentDatabaseName),doctorCancelAppointmentDataFromFirebase.class )
                .build();
        myDoctorCancelAppointmentAdapter=new MyDoctorCancelAppointmentAdapter(options);
        mCancelAppointmentDoctorRecyclerView.setAdapter(myDoctorCancelAppointmentAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        myDoctorCancelAppointmentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myDoctorCancelAppointmentAdapter.stopListening();
    }
}
