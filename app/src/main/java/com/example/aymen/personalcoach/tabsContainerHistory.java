package com.example.aymen.personalcoach;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aymen.personalcoach.data.DBhelper;

import java.util.ArrayList;
import java.util.List;


/**

 * create an instance of this fragment.
 */
public class tabsContainerHistory extends Fragment {
    SharedPreferences prefs;
    int idSP;
    DBhelper helper ;
    private OnFragmentInteractionListener mListener;

    TabLayout tabs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tabs_container_history,container, false);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.fixture_tabs);
        prefs = getActivity().getSharedPreferences("myapp", Context.MODE_PRIVATE);

        idSP =    prefs.getInt( "id" ,0);
        helper = new DBhelper( getActivity() );






        tabs.setupWithViewPager(viewPager);
        tabs.post(new Runnable() {
            @Override
            public void run() {
                tabs.setupWithViewPager(viewPager);
            }
        });


        if (mListener != null) {



            helper.getTodayCaloriesAndMBR( idSP, new DBhelper.VolleyCallbackTodayCalMBR() {
                @Override
                public void onSuccessResponse(String cal , String mbr) {


                    mListener.onFragmentInteraction(cal+ " Calories eaten today/ "+mbr);

                }

                @Override
                public void onFail(String msg) {

                }
            } );

        }
        return view;

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {


        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new HistoryFragment(), "Stats");
        adapter.addFragment(new RunningHistoryList(), "Running History");
        viewPager.setAdapter(adapter);

    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }



        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }




    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String title);
    }




}