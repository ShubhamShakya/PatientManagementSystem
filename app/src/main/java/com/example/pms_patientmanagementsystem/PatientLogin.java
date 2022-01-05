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

import java.util.regex.Pattern;

public class PatientLogin extends AppCompatActivity {
    private TextInputLayout mEmailPatient;
    private TextInputLayout mPasswordPatient;
    private Button mLoginPatientButton;

    private ProgressBar progressBar;

    private String emailString,passwordString;

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
        setContentView(R.layout.login_patient);


               mEmailPatient=(TextInputLayout) findViewById(R.id.etPatientLoginEmail);
               mPasswordPatient=(TextInputLayout) findViewById(R.id.etPatientLoginPassword);
               mLoginPatientButton=(Button) findViewById(R.id.buttonPatientLogIn);
               progressBar=(ProgressBar) findViewById(R.id.progressBar4);



        mAuth=FirebaseAuth.getInstance();

        mLoginPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        emailString=mEmailPatient.getEditText().getText().toString().trim();
                        passwordString=mPasswordPatient.getEditText().getText().toString().trim();
                    }
                }).start();

                if(validate()) {
                    progressBar.setVisibility(View.VISIBLE);
                    mLoginPatientButton.setVisibility(View.INVISIBLE);
                    ThreadRunnable4 t4=new ThreadRunnable4();
                    new Thread(t4).start();
                }
            }
        });
    }
    class ThreadRunnable4 implements Runnable{

        @Override
        public void run() {
            patientLogin();
        }
    }
    private void patientLogin(){
        mAuth.signInWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(PatientLogin.this,"Patient Login Successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PatientLogin.this,PatientDashboard.class));
                }else{
                    Toast.makeText(PatientLogin.this,"Patient Login Failed"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
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
            mEmailPatient.setError("E-Mail of Patient/User is required");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()){
            mEmailPatient.setError("Please enter valid E-Mail address");
            return false;
        }else{
            mEmailPatient.setError(null);
            return true;
        }
    }

    private boolean passwordValidate(){
        if(TextUtils.isEmpty(passwordString)){
            mPasswordPatient.setError("Password is required");
            return false;
        }
        else if(!PASSWORD_PATTERN.matcher(passwordString).matches()){
            mPasswordPatient.setError("Password is not correct");
            return false;
        }else{
            mPasswordPatient.setError(null);
            return true;
        }
    }
}

