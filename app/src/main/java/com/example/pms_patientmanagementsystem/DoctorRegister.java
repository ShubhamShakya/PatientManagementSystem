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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class DoctorRegister extends AppCompatActivity {
    TextInputLayout registerDoctorName;

    TextInputLayout registerDoctorEmail;

    TextInputLayout registerDoctorDegree;

    TextInputLayout registerDoctorSpecialization;

    TextInputLayout registerDoctorPassword;

    TextInputLayout registerDoctorConfirmPassword;

    Button registerDoctorButton;

    private ProgressBar progressBar;

    String emailString,passwordString,confirmPasswordString,nameString,degreeString,specializationString;

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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_register);

        registerDoctorName=(TextInputLayout)findViewById(R.id.etDoctorRegisterFullName);
        registerDoctorEmail=(TextInputLayout) findViewById(R.id.etDoctorRegisterEmail);
        registerDoctorDegree=(TextInputLayout)findViewById(R.id.etDoctorRegisterDegree);
        registerDoctorSpecialization=(TextInputLayout)findViewById(R.id.etDoctorRegisterSpecialization);
        registerDoctorPassword=(TextInputLayout)findViewById(R.id.etDoctorRegisterPassword);
        registerDoctorConfirmPassword=(TextInputLayout)findViewById(R.id.etDoctorRegisterConfirmPassword);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        registerDoctorButton=findViewById(R.id.button);



        mAuth=FirebaseAuth.getInstance();

        db=FirebaseDatabase.getInstance();
        root=db.getReference().child("Doctor");


            registerDoctorButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            nameString=registerDoctorName.getEditText().getText().toString().trim();
                            emailString=registerDoctorEmail.getEditText().getText().toString().trim();
                            degreeString=registerDoctorDegree.getEditText().getText().toString().trim();
                            specializationString=registerDoctorSpecialization.getEditText().getText().toString().trim();
                            passwordString=registerDoctorPassword.getEditText().getText().toString().trim();
                            confirmPasswordString=registerDoctorConfirmPassword.getEditText().getText().toString().trim();
                        }
                    }).start();


                        if(validate()) {
                            progressBar.setVisibility(View.VISIBLE);
                            registerDoctorButton.setVisibility(View.INVISIBLE);
                            ThreadRunnable2 t2=new ThreadRunnable2();
                            new Thread(t2).start();
                        }

                }
            });


    }
    class ThreadRunnable2 implements Runnable{

        @Override
        public void run() {
            createUser();
        }
    }
     void  createUser(){
        mAuth.createUserWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(DoctorRegister.this, "Doctor Registered successfully", Toast.LENGTH_SHORT).show();
                    HashMap<String,String> map=new HashMap<>();
                    map.put("name",nameString);
                    map.put("email",emailString);
                    map.put("Degree",degreeString);
                    map.put("specialization",specializationString);
                    root.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(DoctorRegister.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(DoctorRegister.this,"data not saved"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }

                    });
                    //Todo open login activity
                    startActivity(new Intent(DoctorRegister.this,DoctorLogin.class));

                }else{
                    Toast.makeText(DoctorRegister.this,"Registration Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    registerDoctorButton.setVisibility(View.VISIBLE);
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
            registerDoctorEmail.setError("For registration, E-Mail of Patient/User is required");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()){
            registerDoctorEmail.setError("Please enter valid E-Mail address");
            return false;
        }else{
            registerDoctorEmail.setError(null);
            return true;
        }
    }

    private boolean passwordValidate(){
        if(TextUtils.isEmpty(passwordString)){
            registerDoctorPassword.setError("For registration, Patient/User must have to create password");
            return false;
        }else  if(TextUtils.isEmpty(confirmPasswordString)){
            registerDoctorConfirmPassword.setError("For registration, please Re-enter your password in confirm password");
            return false;
        }else if(!passwordString.equals(confirmPasswordString)){
            registerDoctorConfirmPassword.setError("Password are not matching");
            return false;
        }else if(!PASSWORD_PATTERN.matcher(passwordString).matches()){
            registerDoctorPassword.setError("Password is weak, use at least 1 uppercase 1 lowercase alphabet 1 special character and 1 digit");
            return false;
        }else{
            registerDoctorPassword.setError(null);
            registerDoctorConfirmPassword.setError(null);
            return true;
        }
    }



}
