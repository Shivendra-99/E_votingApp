package com.example.evotingapp.Adopter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.evotingapp.Fragment.DashBorad;
import com.example.evotingapp.Fragment.FeedBackFragment;
import com.example.evotingapp.Fragment.ResultFragment;

public class PageAdopter extends FragmentPagerAdapter {
    int tabCount;
    public PageAdopter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabCount=behavior;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new DashBorad();
            case 1:return new ResultFragment();
            case 2:return new FeedBackFragment();
            default: return null;
        }
    }
    @Override
    public int getCount() {
        return tabCount;
    }
}
