package com.example.noticeboard.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.noticeboard.Activities.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedpref = getSharedPreferences("mypref", 0);
        String token = sharedpref.getString("token", "");
        if (token.isEmpty()) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        else {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }
}
