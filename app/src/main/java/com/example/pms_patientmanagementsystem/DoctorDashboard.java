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

public class DoctorDashboard extends AppCompatActivity {
    private Button mScheduledButton;
    private Button mCancelButton;
    private Button mPreviousButton;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_dashboard);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbarDoctorDashboard);
        setSupportActionBar(toolbar);
        TextView mTitle=(TextView) toolbar.findViewById(R.id.toolbarTVDoctorDashboard);
        mTitle.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mScheduledButton=(Button) findViewById(R.id.buttonScheduledAppointment);
        mCancelButton=(Button) findViewById(R.id.buttonCancelAppointmentDoctorDashboard);
        mPreviousButton=(Button) findViewById(R.id.buttonPreviousAppointmentDoctorDashboard);

        mScheduledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DoctorScheduledAppointment.class);
                startActivity(intent);
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DoctorCancelAppointment.class);
                startActivity(intent);

            }
        });

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DoctorPreviousAppointment.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_doctor_dashboard,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.changePasswordDoctorDashboard){
            startActivity(new Intent(getApplicationContext(),ChangePassword.class));
            Toast.makeText(getApplicationContext(),"Change Password Click",Toast.LENGTH_SHORT).show();
        }else{
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),LogIn.class));
            Toast.makeText(getApplicationContext(),"LogOut clicked",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
