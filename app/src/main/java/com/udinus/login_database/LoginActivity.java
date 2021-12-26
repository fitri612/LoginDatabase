package com.udinus.login_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    // deklarasi variabel dan connect firebase
    EditText editEmail;
    EditText editPassword;
    TextView signUp;
    TextView forgotPass;
    Button btnLogin;
    ImageView back;

    // deklare chackbox
    CheckBox CheckRememberUsername;
    CheckBox CheckKeepLogin;

    //connect firebase
    FirebaseAuth mAuth;


    //sharedpreference yang akan digunakan untuk menulis dan membaca data
    private SharedPreferences shareprefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        editEmail = findViewById(R.id.edit_txt_email);
        editPassword = findViewById(R.id.edit_txte_password);
        signUp = findViewById(R.id.btnsignup);
        btnLogin = findViewById(R.id.btnlogin);
        back = findViewById(R.id.button);
        forgotPass = findViewById(R.id.forgot_password);

        // check box
        CheckRememberUsername = findViewById(R.id.chk_remember_username);
        CheckKeepLogin = findViewById(R.id.chk_keep_login);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> {
            LoginUser();
        });

        back.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        });

        signUp.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        forgotPass.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, ResetPassword.class));
        });
    }

    private void LoginUser(){
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            editEmail.setError("Email cannot be empty");
            editEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password)){
            editPassword.setError("Password cannot be empty");
            editPassword.requestFocus();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}