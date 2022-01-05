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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class PatientRegister extends AppCompatActivity {
    TextInputLayout registerPatientName;

    TextInputLayout registerPatientEmail;

    TextInputLayout registerPatientPassword;

    TextInputLayout registerPatientConfirmPassword;

    Button registerPatientButton;

    ProgressBar progressBar;

    String emailString,passwordString,confirmPasswordString,nameString;

    FirebaseAuth mAuth;

    private FirebaseDatabase db;

    private DatabaseReference root;


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_register);

        registerPatientName=(TextInputLayout)findViewById(R.id.etPatientRegisterFullName);
        registerPatientEmail=(TextInputLayout) findViewById(R.id.etPatientRegisterEmail);
        registerPatientPassword=(TextInputLayout)findViewById(R.id.etPatientRegisterPassword);
        registerPatientConfirmPassword=(TextInputLayout)findViewById(R.id.etPatientRegisterConfirmPassword);
        progressBar=(ProgressBar)findViewById(R.id.progressBar2);

        registerPatientButton=(Button) findViewById(R.id.button);



        mAuth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        root=db.getReference().child("Patient");
        registerPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        nameString = registerPatientName.getEditText().getText().toString().trim();
                        emailString=registerPatientEmail.getEditText().getText().toString().trim();
                        passwordString=registerPatientPassword.getEditText().getText().toString().trim();
                        confirmPasswordString=registerPatientConfirmPassword.getEditText().getText().toString().trim();

                    }
                }).start();
                 if(validate()){
                     progressBar.setVisibility(View.VISIBLE);
                     registerPatientButton.setVisibility(View.INVISIBLE);
                     ThreadRunnable1 t1=new ThreadRunnable1();
                     new Thread(t1).start();
                 }
            }
        });
        this.finish();
    }
    class ThreadRunnable1 implements Runnable{

        @Override
        public void run() {
            createUser();
        }
    }

    private void createUser(){
        mAuth.createUserWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(PatientRegister.this,"Patient Registered Successfully",Toast.LENGTH_SHORT).show();
                    HashMap<String,String> map=new HashMap<>();
                    map.put("Name",nameString);
                    map.put("Email",emailString);
                    root.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PatientRegister.this,"Successfully added"+task.getResult().toString(),Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(PatientRegister.this,"Data adding failed"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    //todo open login activity
                    startActivity(new Intent(PatientRegister.this,PatientLogin.class));
                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    registerPatientButton.setVisibility(View.VISIBLE);
                    Toast.makeText(PatientRegister.this,"Registration Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
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
            registerPatientEmail.setError("For registration, E-Mail of Patient/User is required");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()){
            registerPatientEmail.setError("Please enter valid E-Mail address");
            return false;
        }else{
            registerPatientEmail.setError(null);
            return true;
        }
    }

    private boolean passwordValidate(){
        if(TextUtils.isEmpty(passwordString)){
            registerPatientPassword.setError("For registration, Patient/User must have to create password");
            return false;
        }else  if(TextUtils.isEmpty(confirmPasswordString)){
            registerPatientConfirmPassword.setError("For registration, please Re-enter your password in confirm password");
            return false;
        }else if(!passwordString.equals(confirmPasswordString)){
            registerPatientConfirmPassword.setError("Password are not matching");
            return false;
        }else if(!PASSWORD_PATTERN.matcher(passwordString).matches()){
            registerPatientPassword.setError("Password is weak, use at least 1 uppercase 1 lowercase alphabet 1 special character and 1 digit");
            return false;
        }else{
            registerPatientPassword.setError(null);
            registerPatientConfirmPassword.setError(null);
            return true;
        }
    }
}