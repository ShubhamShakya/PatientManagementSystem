package com.example.pms_patientmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PatientDashboard extends AppCompatActivity {
    private Button mBookAppointment;
    private Button mCancelAppointment;
    private Button mPreviousAppointment;


FirebaseAuth firebaseAuth;



    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_dashboard);

        mBookAppointment=(Button) findViewById(R.id.buttonBookAppointment);
        mCancelAppointment=(Button) findViewById(R.id.buttonCancelAppointment);
        mPreviousAppointment=(Button) findViewById(R.id.buttonPreviousAppointment);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView mTitle=(TextView) toolbar.findViewById(R.id.toolbarTV);

        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        mTitle.setText(firebaseUser.getEmail().toString());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mBookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PatientDashboard.this,PatientBookAppointment.class);
                intent.putExtra("patientEmail",firebaseUser.getEmail().toString());
                startActivity(intent);
            }
        });


        mCancelAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(PatientDashboard.this,PatientCancelAppointment.class);
                startActivity(intent);

            }
        });

        mPreviousAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PatientDashboard.this,PatientPreviousAppointment.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.patientChangePassword){
            startActivity(new Intent(getApplicationContext(),ChangePassword.class));
            Toast.makeText(getApplicationContext(),"Change Password opening",Toast.LENGTH_SHORT).show();
        }else if(id==R.id.patientLogOut){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),LogIn.class));
            Toast.makeText(getApplicationContext(),"LogOut successful",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
