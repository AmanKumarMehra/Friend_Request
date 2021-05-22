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

public class Login extends AppCompatActivity {

    private TextInputEditText inputEmail, inputPassword;
    Button btn_login;
    Button forgot_Password;
    Button move_to_registration;
    FirebaseAuth mAuth;
    ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.e_mail);
        inputPassword = findViewById(R.id.pass_word);
        btn_login = findViewById(R.id.login_btn);
        forgot_Password = findViewById(R.id.forgot_password);
        move_to_registration = findViewById(R.id.move_to_registration);
        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(this);

        // if user is already registered then go to login screen
        move_to_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttemptLogin();
                
            }
        });

    }

    private void AttemptLogin() {
        String e_mail = inputEmail.getText().toString().trim();
        String pass_word = inputPassword.getText().toString().trim();

        mLoadingBar.setTitle("Registration");
        mLoadingBar.setMessage("Please Wait!");
        mLoadingBar.setCanceledOnTouchOutside(false);
        mLoadingBar.show();
        mAuth.signInWithEmailAndPassword(e_mail, pass_word).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mLoadingBar.dismiss();
                    Toast.makeText(Login.this, "Login is Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, SetUp.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else{
                    mLoadingBar.dismiss();
                    Toast.makeText(Login.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}