package com.example.coneto.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.coneto.Fragments.ChatFragment;
import com.example.coneto.Fragments.PeopleFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {
            return ChatFragment.getInstance();
        } else {
            return PeopleFragment.getInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
