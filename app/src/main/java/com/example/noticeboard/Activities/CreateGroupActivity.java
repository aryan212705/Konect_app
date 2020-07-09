package com.example.noticeboard.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.noticeboard.ApiResponses.GeneralResponse;
import com.example.noticeboard.AppApiService;
import com.example.noticeboard.Activities.HomeActivity;
import com.example.noticeboard.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.noticeboard.Constants.BASE_URL;

public class CreateGroupActivity extends AppCompatActivity {

    private EditText group_name;
    private Button create;
    private TextView error;
    public static Retrofit retrofit = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        group_name = findViewById(R.id.add_group_name);
        create = findViewById(R.id.submit_group_name);
        error = findViewById(R.id.error_create_group);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name;
                name = group_name.getText().toString();
                if(name.isEmpty()) {
                    error.setText("Group name cannot be empty");
                }
                else {
                    connectAndGetApiData(name);
                }
            }
        });
    }

    public void connectAndGetApiData(final String name) {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        AppApiService apiService = retrofit.create(AppApiService.class);
        SharedPreferences sharedpref = getSharedPreferences("mypref", 0);
        String token = "Token " + sharedpref.getString("token", "");
        Call<GeneralResponse> call = apiService.createGroup(token, name);
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if(response.isSuccessful()) {
                    Intent i = new Intent(getBaseContext(), HomeActivity.class);
                    startActivity(i);
                    Toast.makeText(getBaseContext(), "Group " + name + " created", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    error.setText("Something went wrong. Try again later.");
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                error.setText("Something went wrong. Try again later.");
            }
        });
    }
}
