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

public class DoctorScheduledAppointment extends AppCompatActivity {
    RecyclerView mScheduledAppointmentRecyclerView;
    MyDoctorScheduledAppointmentAdapter myDoctorScheduledAppointmentAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_scheduled_appointment);

        mScheduledAppointmentRecyclerView=findViewById(R.id.doctorScheduledAppointmentRecyclerView);
        mScheduledAppointmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbarDoctorScheduledAppointment);
        setSupportActionBar(toolbar);
        TextView mTitle=(TextView) toolbar.findViewById(R.id.toolbarTVDoctorScheduledAppointment);
        mTitle.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String doctorMail=firebaseUser.getEmail();
        int atRateSymbol=doctorMail.indexOf('@');
        String doctorScheduledAppointmentDatabaseName=doctorMail.substring(0,atRateSymbol);

        FirebaseRecyclerOptions<doctorAppointmentDataFromFirebase> options =
                new FirebaseRecyclerOptions.Builder<doctorAppointmentDataFromFirebase>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child(doctorScheduledAppointmentDatabaseName), doctorAppointmentDataFromFirebase.class)
                        .build();

        myDoctorScheduledAppointmentAdapter=new MyDoctorScheduledAppointmentAdapter(options);
        mScheduledAppointmentRecyclerView.setAdapter(myDoctorScheduledAppointmentAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        myDoctorScheduledAppointmentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myDoctorScheduledAppointmentAdapter.stopListening();
    }
}
