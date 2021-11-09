package com.rajendra.onlinedailygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText fullname, phone, email, password;
    private Button registeruser;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        fullname = (EditText)findViewById(R.id.editTextTextPersonName);
        phone = (EditText)findViewById(R.id.editTextPhone);
        email = (EditText)findViewById(R.id.editTextTextEmailAddress2);
        password = (EditText)findViewById(R.id.editTextTextPassword2);
        registeruser = (Button)findViewById(R.id.button3);
        progressbar = (ProgressBar)findViewById(R.id.progressBar3);

        registeruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        final String email1 = email.getText().toString().trim();
        String password1 = password.getText().toString().trim();
        final String fullname1 = fullname.getText().toString().trim();
        final String phone1 = phone.getText().toString().trim();
        if(fullname1.isEmpty()){
            fullname.setError("Full Name is required");
            fullname.requestFocus();
            return;
        }
        if(phone1.isEmpty()){
            phone.setError("Phone is required");
            phone.requestFocus();
            return;
        }
        if(email1.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
            email.setError("Please Provide valid email");
            email.requestFocus();
            return;
        }
        if(password1.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if(password1.length() < 6){
            password.setError("Min password length should be 6 characters");
            password.requestFocus();
            return;
        }
        progressbar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email1, password1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fullname1, phone1, email1);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(register.this, "User registered", Toast.LENGTH_LONG).show();
                                        progressbar.setVisibility(View.VISIBLE);
                                    }
                                    else{
                                        Toast.makeText(register.this, "Registration Failed", Toast.LENGTH_LONG).show();
                                        progressbar.setVisibility(View.GONE);

                                    }
                                }
                            });


                        }else{
                            Toast.makeText(register.this, "Registration Failed", Toast.LENGTH_LONG).show();
                            progressbar.setVisibility(View.GONE);

                        }
                    }
                });

    }
}