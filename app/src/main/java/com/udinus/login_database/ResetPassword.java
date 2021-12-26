package com.udinus.login_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    //deklarasi variabel
    EditText editEmail;
    Button btnReset;
    ImageView back;

    //connect firebase
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editEmail = findViewById(R.id.edit_txt_email);
        btnReset = findViewById(R.id.btnreset);
        back = findViewById(R.id.button);

        mAuth = FirebaseAuth.getInstance();

        btnReset.setOnClickListener(view -> {
            ResetPass();
        });
    }

    private void ResetPass(){
        String email = editEmail.getText().toString();

        if(TextUtils.isEmpty(email)){
            editEmail.setError("Email cannot be empty");
            editEmail.requestFocus();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Email no valid !");
            editEmail.requestFocus();
        }
        else {
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ResetPassword.this, "Your email to reset your password", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPassword.this, ForgotPassword.class));
                    }
                    else{
                        Toast.makeText(ResetPassword.this, "Reset password in error : "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}