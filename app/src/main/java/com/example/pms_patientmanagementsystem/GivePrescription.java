package com.example.pms_patientmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class GivePrescription extends AppCompatActivity {
    //To store the id's
    EditText mEtGivePrescriptionAddPrescription;
    Button mBtnGivePrescriptionAdd;

    //To store the prescription detail which is given by doctor to pateint
    String stringAddPrescriptionData;

    ProgressBar mPgGivePrescriptionProgressBar;

    //For storing the values received from the previous activity
    String patientMail,appTime,appDate,disease,doctorMail;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.give_prescription);

        //Fetching the id's
        mEtGivePrescriptionAddPrescription=(EditText) findViewById(R.id.etGivePrescriptionAddPrescription);
        mBtnGivePrescriptionAdd=(Button) findViewById(R.id.btnGivePrescriptionAdd);
        mPgGivePrescriptionProgressBar=(ProgressBar) findViewById(R.id.pgGivePrescriptionProgressBar);


        //Fetching the values which are send from previous activity
        patientMail=getIntent().getStringExtra("patientMail");
        appDate=getIntent().getStringExtra("appDate");
        appTime=getIntent().getStringExtra("appTime");
        disease=getIntent().getStringExtra("disease");

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        doctorMail=user.getEmail().toString();

        mBtnGivePrescriptionAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fetching the data of Add Prescription edit text
                stringAddPrescriptionData=mEtGivePrescriptionAddPrescription.getText().toString().trim();
                mPgGivePrescriptionProgressBar.setVisibility(View.VISIBLE);
                mBtnGivePrescriptionAdd.setVisibility(View.INVISIBLE);
                ThreadForAddPrescriptionButton thread=new ThreadForAddPrescriptionButton();
                thread.run();
            }
        });



    }
    class ThreadForAddPrescriptionButton implements Runnable{

        @Override
        public void run() {
            workingAfterClickingOnAddPrescriptionButton();
        }
    }

    private void workingAfterClickingOnAddPrescriptionButton() {
        int i=patientMail.indexOf('@');
        String patientAppointmentDatabaseName=patientMail.substring(0,i);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
        Query query=ref.child(patientAppointmentDatabaseName).orderByChild("doctorMail").equalTo(doctorMail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    dataSnapshot.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(getClass().getName(),"onCancelled", error.toException());
            }
        });

        int i1=doctorMail.indexOf('@');
        String doctorAppointmentDatabaseName=doctorMail.substring(0,i1);
        Query query1=ref.child(doctorAppointmentDatabaseName).orderByChild("patientMail").equalTo(patientMail);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    dataSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(getClass().getName(),"onCancelled", error.toException());
            }
        });

        String completedAppointmentDatabaseNameDoctor="prev"+doctorMail.substring(0,doctorMail.indexOf('@'));
        ref=FirebaseDatabase.getInstance().getReference().child(completedAppointmentDatabaseNameDoctor);

        HashMap<String,String> map1=new HashMap<>();
        map1.put("patientMail",patientMail);
        map1.put("date",appDate);
        map1.put("time",appTime);
        map1.put("disease",disease);
        map1.put("prescription",stringAddPrescriptionData);
        ref.push().setValue(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Prescription Successfully Added Doctor ",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getApplicationContext(),"Data added after appointment failed for Doctor "+task.getException(),Toast.LENGTH_SHORT).show();

                }
            }
        });

        String completedAppointmentDatabaseNamePatient="prev"+patientMail.substring(0,patientMail.indexOf('@'));
        ref=FirebaseDatabase.getInstance().getReference().child(completedAppointmentDatabaseNamePatient);
        HashMap<String,String> map=new HashMap<>();
//        map.put("patientMail",patientMail);
        map.put("doctorMail",doctorMail);
        map.put("date",appDate);
        map.put("time",appTime);
        map.put("disease",disease);
        map.put("prescription",stringAddPrescriptionData);
        ref.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    mPgGivePrescriptionProgressBar.setVisibility(View.INVISIBLE);
                    mBtnGivePrescriptionAdd.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"Prescription Successfully Added Patient ",Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(),DoctorDashboard.class));
                }
                else{
                    mPgGivePrescriptionProgressBar.setVisibility(View.INVISIBLE);
                    mBtnGivePrescriptionAdd.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"Data added after appointment failed for Patient "+task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.finish();
    }

}
