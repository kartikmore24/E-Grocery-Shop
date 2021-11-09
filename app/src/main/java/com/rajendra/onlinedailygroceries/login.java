package com.rajendra.onlinedailygroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private TextView register;
    private EditText e_email, e_password;
    private Button login;
    private ProgressBar progressbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        e_email = (EditText)findViewById(R.id.editTextTextEmailAddress);
        e_password = (EditText)findViewById(R.id.editTextTextPassword);
        login = (Button)findViewById(R.id.button2);
        progressbar = (ProgressBar)findViewById(R.id.progressBar2);
        mAuth = FirebaseAuth.getInstance();

        register = (TextView)findViewById(R.id.textView11);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
    }

    private void userLogin() {
        String email = e_email.getText().toString().trim();
        String password = e_password.getText().toString().trim();

        if(email.isEmpty()){
            e_email.setError("Email is required");
            e_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            e_email.setError("Please Provide valid email");
            e_email.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            e_password.setError("Email is required");
            e_password.requestFocus();
            return;
        }
        progressbar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(login.this, MainActivity.class);
                    startActivity(intent);

                }
                else{
                    Toast.makeText(login.this, "Failed to login", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}