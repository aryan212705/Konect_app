package com.example.noticeboard.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.noticeboard.Adapters.HomeTabAdapter;
import com.example.noticeboard.Activities.MyPostFragment;
import com.example.noticeboard.R;
import com.example.noticeboard.Activities.RecentPostFragment;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {

    private HomeTabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button logout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpref = getSharedPreferences("mypref", 0);
                SharedPreferences.Editor editor = sharedpref.edit();
                editor.remove("token");
                editor.commit();
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        tabAdapter = new HomeTabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new GroupFragment(), "Groups");
        tabAdapter.addFragment(new RecentPostFragment(), "Recent Posts");
        tabAdapter.addFragment(new MyPostFragment(), "My Posts");

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        Intent i = getIntent();
        int tab = i.getIntExtra("tab", 0);
        tabLayout.getTabAt(tab).select();
    }
}
