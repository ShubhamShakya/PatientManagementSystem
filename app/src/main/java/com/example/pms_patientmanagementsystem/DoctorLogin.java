package com.example.pms_patientmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class DoctorLogin extends AppCompatActivity {
    private TextInputLayout mEmailDoctor;

    private TextInputLayout mPasswordDoctor;

    private Button loginDoctorButton;

    private ProgressBar progressBar;

    private String emailString, passwordString;

    private FirebaseAuth mAuth;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=z?])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_doctor);

        mEmailDoctor=(TextInputLayout) findViewById(R.id.etDoctorLoginEmail);
        mPasswordDoctor=(TextInputLayout) findViewById(R.id.etDoctorLoginPassword);
        loginDoctorButton=(Button)findViewById(R.id.buttonDoctorLogIn);
        progressBar=(ProgressBar)findViewById(R.id.progressBar3);



        mAuth=FirebaseAuth.getInstance();
        loginDoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailString=mEmailDoctor.getEditText().getText().toString().trim();
                passwordString=mPasswordDoctor.getEditText().getText().toString().trim();
                if(validate()) {
                    progressBar.setVisibility(View.VISIBLE);
                    loginDoctorButton.setVisibility(View.INVISIBLE);
                    doctorLogin();
                }
            }
        });

    }

    private void doctorLogin(){
        mAuth.signInWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    loginDoctorButton.setVisibility(View.VISIBLE);
                    Toast.makeText(DoctorLogin.this,"login successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),DoctorDashboard.class));
                }else
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    loginDoctorButton.setVisibility(View.VISIBLE);
                    Toast.makeText(DoctorLogin.this,"Login failed"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.finish();
    }

    private boolean validate(){
        if(!emailValidate() | !passwordValidate())
            return false;
        return true;
    }

    private boolean emailValidate(){
        if(TextUtils.isEmpty(emailString)){
            mEmailDoctor.setError("E-Mail of Patient/User is required");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()){
            mEmailDoctor.setError("Please enter valid E-Mail address");
            return false;
        }else{
            mEmailDoctor.setError(null);
            return true;
        }
    }

    private boolean passwordValidate(){
        if(TextUtils.isEmpty(passwordString)){
            mPasswordDoctor.setError("Password is required");
            return false;
        }
        else if(!PASSWORD_PATTERN.matcher(passwordString).matches()){
            mPasswordDoctor.setError("Password is not correct");
            return false;
        }else{
            mPasswordDoctor.setError(null);
            return true;
        }
    }

}
