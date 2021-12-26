package com.udinus.login_database;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgotPassword extends AppCompatActivity {

    EditText code;
    EditText editEmail;
    EditText editPassword;
    EditText editConfPassword;
    Button btnforgetPass;
    TextView help;
    ImageView back;


    FirebaseUser mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        code = findViewById(R.id.edit_txt_code);
        editEmail = findViewById(R.id.edit_txt_email);
        editPassword = findViewById(R.id.edit_txt_password);
        editConfPassword = findViewById(R.id.edit_txt_conf_pass);
        btnforgetPass = findViewById(R.id.btnForget);
        help = findViewById(R.id.btnHelp);
        back = findViewById(R.id.button);

        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        btnforgetPass.setOnClickListener(view -> {
            forgetPass();
        });

        back.setOnClickListener(view -> {
            startActivity(new Intent(ForgotPassword.this, ResetPassword.class));
        });

        help.setOnClickListener(view -> {
            startActivity(new Intent(ForgotPassword.this, MainActivity.class));
        });
    }

    private void forgetPass(){
        String codeForget = code.getText().toString();
        String email = editEmail.getText().toString();
        String newpassword = editPassword.getText().toString();
        String confirmPass = editConfPassword.getText().toString();

        if(TextUtils.isEmpty(codeForget)){
            code.setError("Code reset cannot be empty");
            code.requestFocus();
        }
        else if(TextUtils.isEmpty(email)){
            editEmail.setError("Email cannot be empty");
            editEmail.requestFocus();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Email no valid !");
            editEmail.requestFocus();
        }
        else if(TextUtils.isEmpty(newpassword)){
            editPassword.setError("Password cannot be empty");
            editPassword.requestFocus();
        }
        else if(TextUtils.isEmpty(confirmPass)){
            editConfPassword.setError("Confirm password cannot be empty");
            editConfPassword.requestFocus();
        }

        else{
            mAuth.updatePassword(newpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Log.d(TAG, "User password updated.");
                    }
                }
            });
        }
    }
}