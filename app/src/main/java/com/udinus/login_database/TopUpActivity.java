package com.udinus.login_database;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class TopUpActivity extends AppCompatActivity {

    EditText editJumlah;
    int local = 10000;
    int topUp;
    ImageView back;

    Button btnTopUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        this.setTitle("TOP UP MONEY");
        editJumlah = findViewById(R.id.edit_txt_money);
        btnTopUp = findViewById(R.id.btnTop);
        back = findViewById(R.id.button);

        back.setOnClickListener(view -> {
            startActivity(new Intent(TopUpActivity.this, AccountActivity.class));
        });

        btnTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMessage();
            }
        });
    }

    private void getMessage(){

        String Edjumlah = editJumlah.getText().toString();


        if(TextUtils.isEmpty(Edjumlah)){
            editJumlah.setError("Username cannot be empty");
            editJumlah.requestFocus();
        }
        else{
            int jumlah = Integer.parseInt(editJumlah.getText().toString());
            topUp = local + jumlah;

            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            String hrg = formatRupiah.format((double) local);
            String ttl = formatRupiah.format((double) topUp);

            new AlertDialog.Builder(this)
                    .setTitle("Hasil TOP UP\n")
                    .setMessage("\nJumlah Top Up : "+jumlah+
                            "\nTotal E-Money Book : "+topUp)
                    .setCancelable(false)
                    .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            editJumlah.setText("");
                            Toast.makeText(getApplicationContext(), "Success Top Up", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNeutralButton("Cancel", null)
                    .setNegativeButton("", null)
                    .show();
        }
    }
}