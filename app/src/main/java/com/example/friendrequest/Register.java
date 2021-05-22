package com.example.friendrequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    TextInputEditText inputEmail;
    TextInputEditText inputPassword;
    Button move_to_login;
    Button registration;
    FirebaseAuth mAuth;
    ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputEmail = (TextInputEditText)findViewById(R.id.email);
        inputPassword = (TextInputEditText)findViewById(R.id.password);
        registration = (Button) findViewById(R.id.register_btn);
        move_to_login = (Button) findViewById(R.id.move_to_login);
        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(this);

        // if user is already registered then go to login screen
        move_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        // on clicking Registration Button
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttemptRegistration();
            }
        });

    }

    private void AttemptRegistration() {
        String e_mail = inputEmail.getText().toString().trim();
        String pass_word = inputPassword.getText().toString().trim();

        mLoadingBar.setTitle("Registration");
        mLoadingBar.setMessage("Please Wait!");
        mLoadingBar.setCanceledOnTouchOutside(false);
        mLoadingBar.show();
        mAuth.createUserWithEmailAndPassword(e_mail,pass_word).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mLoadingBar.dismiss();
                    Toast.makeText(Register.this, "Registration is Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, SetUp.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                }
                else{
                    mLoadingBar.dismiss();
                    Toast.makeText(Register.this, "Registration is Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}










