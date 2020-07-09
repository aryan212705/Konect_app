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

public class CreatePostActivity extends AppCompatActivity {

    private EditText title, content;
    private Button submit;
    private TextView error;
    public static Retrofit retrofit = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        title = findViewById(R.id.add_title);
        content = findViewById(R.id.add_content);
        submit = findViewById(R.id.submit_post);
        error = findViewById(R.id.error_posting);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String post_title, post_content;
                post_title = title.getText().toString();
                post_content = content.getText().toString();
                if(post_title.isEmpty() || post_content.isEmpty()) {
                    error.setText("Both fields are compulsory");
                }
                else {
                    connectAndGetApiData(post_title, post_content);
                }
            }
        });
    }

    public void connectAndGetApiData(String post_title, String post_content) {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        AppApiService apiService = retrofit.create(AppApiService.class);
        SharedPreferences sharedpref = getSharedPreferences("mypref", 0);
        String token = "Token " + sharedpref.getString("token", "");
        Call<GeneralResponse> call = apiService.createPost(token, post_title, post_content);
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if(response.isSuccessful()) {
                    Intent i = new Intent(getBaseContext(), HomeActivity.class);
                    i.putExtra("tab", 2);
                    startActivity(i);
                    Toast.makeText(getBaseContext(), "Post created successfully", Toast.LENGTH_SHORT).show();
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
