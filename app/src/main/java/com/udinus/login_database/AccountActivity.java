package com.udinus.login_database;

import static com.udinus.login_database.R.layout.activity_account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AccountActivity extends AppCompatActivity {
    ImageView plus;
    Button btnLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_account);
        plus = findViewById(R.id.plus);
        btnLog = findViewById(R.id.btnLogout);

        plus.setOnClickListener(view -> {
            startActivity(new Intent(AccountActivity.this, TopUpActivity.class));
        });

        btnLog.setOnClickListener(view -> {
            startActivity(new Intent(AccountActivity.this, LoginActivity.class));
        });
    }
}