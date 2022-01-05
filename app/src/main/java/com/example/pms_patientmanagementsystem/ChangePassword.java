package com.example.pms_patientmanagementsystem;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class ChangePassword extends AppCompatActivity {
    TextInputLayout mOldPassword,mNewPassword,mConfirmNewPassword;
    String passwordString,confirmPasswordString;
    String oldPasswordString;
    Button mChangePasswordButton;
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        mOldPassword = findViewById(R.id.changePasswordOldPassword);
        mNewPassword = findViewById(R.id.changePasswordNewPassword);
        mConfirmNewPassword = findViewById(R.id.changePasswordConfirmNewPassword);
        mChangePasswordButton=findViewById(R.id.buttonChangePassword);





        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String mail=firebaseUser.getEmail().toString();





        mChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPasswordString = mOldPassword.getEditText().getText().toString().trim();

                passwordString = mNewPassword.getEditText().getText().toString().trim();
                confirmPasswordString = mConfirmNewPassword.getEditText().getText().toString().trim();

                AuthCredential credential = EmailAuthProvider.getCredential(mail, oldPasswordString);
                if (passwordValidate()) {
                    firebaseUser.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        firebaseUser.updatePassword(passwordString).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "Failed! Password not updated" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Credentials are not correct " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });

    }


    private boolean passwordValidate(){
        if(TextUtils.isEmpty(passwordString)){
            mNewPassword.setError("New Password Can't be empty");
            return false;
        }else  if(TextUtils.isEmpty(confirmPasswordString)){
            mConfirmNewPassword.setError("Confirm New Password can't be empty");
            return false;
        }else if(!passwordString.equals(confirmPasswordString)){
            mConfirmNewPassword.setError("Password are not matching");
            return false;
        }else if(!PASSWORD_PATTERN.matcher(passwordString).matches()){
            mNewPassword.setError("Password is weak, use at least 1 uppercase 1 lowercase alphabet 1 special character and 1 digit");
            return false;
        }else{
            mNewPassword.setError(null);
            mConfirmNewPassword.setError(null);
            return true;
        }
    }
}
