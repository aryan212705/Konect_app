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
import com.example.noticeboard.Activities.GroupPostsActivity;
import com.example.noticeboard.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.noticeboard.Constants.BASE_URL;

public class AddGroupMembers extends AppCompatActivity {

    private TextView pagetitle, error;
    private EditText memberId;
    private Button add_member;
    public static Retrofit retrofit = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        pagetitle = findViewById(R.id.create_group_page_title);
        memberId = findViewById(R.id.add_group_name);
        add_member = findViewById(R.id.submit_group_name);
        error = findViewById(R.id.error_create_group);

        pagetitle.setText("Add member to group");
        memberId.setHint("User id of member");
        add_member.setText("Add member");

        Intent i = getIntent();
        final Integer group_id = i.getIntExtra("group_id", 0);
        add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username;
                username = memberId.getText().toString();
                if(username.isEmpty()) {
                    error.setText("User id cannot be empty");
                }
                else {
                    connectAndGetApiData(username, group_id);
                }
            }
        });
    }

    public void connectAndGetApiData(final String name, final Integer group_id) {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        AppApiService apiService = retrofit.create(AppApiService.class);
        SharedPreferences sharedpref = getSharedPreferences("mypref", 0);
        String token = "Token " + sharedpref.getString("token", "");
        Call<GeneralResponse> call = apiService.addMember(token, name, group_id);
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if(response.isSuccessful()) {
                    Intent i = new Intent(getBaseContext(), GroupPostsActivity.class);
                    i.putExtra("group_id", group_id);
                    startActivity(i);
                    Toast.makeText(getBaseContext(), name + " added to group", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    error.setText("Either user is already a member of the group or user does not exist");
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                error.setText("Something went wrong. Try again later.");
            }
        });
    }
}
