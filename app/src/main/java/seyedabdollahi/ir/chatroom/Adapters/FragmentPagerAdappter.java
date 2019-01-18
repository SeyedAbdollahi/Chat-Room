package seyedabdollahi.ir.chatroom.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import seyedabdollahi.ir.chatroom.Fragments.ProfileFragment;
import seyedabdollahi.ir.chatroom.Fragments.RoomsFragment;

public class FragmentPagerAdappter extends FragmentPagerAdapter {

    private String[] titles = {"Profile" , "Rooms"};

    public FragmentPagerAdappter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ProfileFragment();
            case 1:
                return new RoomsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
