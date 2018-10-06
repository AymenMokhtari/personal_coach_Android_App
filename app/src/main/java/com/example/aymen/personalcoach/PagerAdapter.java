package com.example.aymen.personalcoach;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

class PagerAdapter extends FragmentPagerAdapter {


    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return P1Fragment.newInstance("Group page n : "+(position+1));


            case 1 :
                return P2Fragment.newInstance("Date page "+(position+1));

            case 2 :
                return P3Fragment.newInstance("Check page n : "+(position+1));

            case 3 :
                return P4Fragment.newInstance("Time page n : "+(position+1));
            case 4 :
                return P5Fragment.newInstance("Check page n : "+(position+1));
            case 5 :
                return P6Fragment.newInstance("Check page n : "+(position+1));


        }
        return P1Fragment.newInstance("Group page n : "+(position+1));

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

    @Override
    // To update fragment in ViewPager, we should override getItemPosition() method,
    // in this method, we call the fragment's public updating method.
    public int getItemPosition(Object object) {
        Log.d("hh", "getItemPosition(" + object.getClass().getSimpleName() + ")");

        return super.getItemPosition(object);
    }


}