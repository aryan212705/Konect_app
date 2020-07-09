package com.example.noticeboard.Adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeTabAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> fragmentNameList = new ArrayList<>();

    public HomeTabAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fg, String title) {
        fragmentList.add(fg);
        fragmentNameList.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentNameList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
