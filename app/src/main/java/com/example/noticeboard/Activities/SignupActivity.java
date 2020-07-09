package com.example.noticeboard.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.noticeboard.Activities.LoginActivity;
import com.example.noticeboard.ApiResponses.SignupResponse;
import com.example.noticeboard.AppApiService;
import com.example.noticeboard.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.noticeboard.Constants.BASE_URL;

public class SignupActivity extends AppCompatActivity {

    private EditText username, password;
    private Button signup;
    private TextView error, login_activity;
    public static Retrofit retrofit = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        username = findViewById(R.id.signup_username);
        password = findViewById(R.id.signup_password);
        signup = findViewById(R.id.signup);
        error = findViewById(R.id.error_signup);
        login_activity = findViewById(R.id.login_activity);

        login_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, pass;
                user = username.getText().toString();
                pass = password.getText().toString();
                if(user.isEmpty() || pass.isEmpty()) {
                    error.setText("Enter both username and password");
                }
                else {
                    connectAndGetApiData(user, pass);
                }
            }
        });
    }

    public void connectAndGetApiData(String username, String password) {

        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        AppApiService apiService = retrofit.create(AppApiService.class);
        Call<SignupResponse> call = apiService.userSignup(username, password);
        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if(response.isSuccessful()) {
                    String token = response.body().getToken();
                    SharedPreferences sharedpref = getSharedPreferences("mypref", 0);
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.putString("token", token);
                    editor.commit();

                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    error.setText("Username already exists. Try something unique");
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                error.setText("Something went wrong");
            }
        });
    }
}
