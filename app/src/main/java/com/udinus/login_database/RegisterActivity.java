package com.udinus.login_database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {



    // deklarasi variabel dan connect firebase
    EditText editUsername;
    EditText editEmail;
    EditText editPassword;
    EditText editConfPassword;
    TextView signIp;
    Button btnRegister;
    ImageView back;

    //data

    //connect firebase
    FirebaseAuth mAuth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editUsername = findViewById(R.id.edit_txt_username);
        editEmail = findViewById(R.id.edit_txt_email);
        editPassword = findViewById(R.id.edit_txt_password);
        editConfPassword = findViewById(R.id.edit_txt_conf_pass);
        signIp = findViewById(R.id.btnsignin);
        btnRegister = findViewById(R.id.btnlogin);
        back = findViewById(R.id.button);


        // auth
        mAuth = FirebaseAuth.getInstance();

        // progress
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        btnRegister.setOnClickListener(view -> {
            CreateNewUser();
        });

        back.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        signIp.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }

    //create new fungsi handle new user
    private void CreateNewUser(){



        String username = editUsername.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        String confirmPass = editConfPassword.getText().toString();

        // handle beberapa kondisi
        if(TextUtils.isEmpty(username)){
            editUsername.setError("Username cannot be empty");
            editUsername.requestFocus();
        }
        else if(TextUtils.isEmpty(email)){
            editEmail.setError("Email cannot be empty");
            editEmail.requestFocus();
        }
        else if(TextUtils.isEmpty(password)){
            editPassword.setError("Password cannot be empty");
            editPassword.requestFocus();
        }
        else if(TextUtils.isEmpty(confirmPass)){
            editConfPassword.setError("Confirm password cannot be empty");
            editConfPassword.requestFocus();
        }
        else if(!confirmPass.equals(password)){
            editConfPassword.setError("Confirm password and password not equals");
            editConfPassword.requestFocus();
        }
        else{

            // show progress
            progressDialog.setMessage("Creating account...");
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            updateUserInfo();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private void updateUserInfo(){
        progressDialog.setMessage("Saving user info...");

        long timestamp = System.currentTimeMillis();

        String uid = mAuth.getUid();
        String username = editUsername.getText().toString();
        String email = editEmail.getText().toString();
        String password = editPassword.getText().toString();
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("uid", uid);
        hashMap.put("email", email);
        hashMap.put("username", username);
        hashMap.put("profileImage", ""); //add empty
        hashMap.put("password", password);
        hashMap.put("userType", "user");
        hashMap.put("timestamp", timestamp);

        // Get a reference to our posts
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.child(uid)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Account created...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });



    }
}