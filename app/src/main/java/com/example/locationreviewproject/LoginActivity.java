package com.example.locationreviewproject;

import static com.example.locationreviewproject.MyApplicationClass.sharedPreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText txtMail, txtPassword;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtMail = findViewById(R.id.txtMail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        sharedPreferences = getSharedPreferences("K", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(txtMail.getText());
                String password = String.valueOf(txtPassword.getText());
                editor.putString("name", name);
                editor.putString("password", password);
                editor.apply();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}