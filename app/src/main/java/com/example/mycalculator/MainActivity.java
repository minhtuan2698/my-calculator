package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayoutx;
    private ViewPager viewpagerx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayoutx = findViewById(R.id.tab_layout);
        viewpagerx = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapterx = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewpagerx.setAdapter(viewPagerAdapterx);
        tabLayoutx.setupWithViewPager(viewpagerx);

    }
}