package com.example.aymen.personalcoach;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.PieChart;

public class HomePage extends AppCompatActivity implements
        tabsContainerFRagment.OnFragmentInteractionListener,
        HistoryFragment.OnFragmentInteractionListener,
        RunningGps.OnFragmentInteractionListener,
        HomeFragment.OnFragmentInteractionListener,
        SearchFoodFragment.OnFragmentInteractionListener,
        tabsContainerHistory.OnFragmentInteractionListener,
        RunningHistoryList.OnFragmentInteractionListener


{
    private static final String TAG = "chart";
    BottomNavigationView botNav ;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_home_page );






        botNav = findViewById( R.id.NavBot );
        BottomNavigationViewHelper.disableShiftMode( botNav );
        getFragmentManager().beginTransaction().replace(R.id.home_container,new HomeFragment()).addToBackStack(null).commit();
        botNav.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_nav:
                        getFragmentManager().beginTransaction().replace(R.id.home_container,new HomeFragment()).addToBackStack(null).commit();

                        break;
                    case R.id.diet_nav:
                        getFragmentManager().beginTransaction().replace( R.id.home_container, new tabsContainerFRagment() ).addToBackStack( null ).commit();

                        break;
                    case R.id.stat_nav:

                        getFragmentManager().beginTransaction().replace( R.id.home_container, new tabsContainerHistory() ).addToBackStack( null ).commit();

                        break;
                    case  R.id.runbtn:
                        getFragmentManager().beginTransaction().replace(R.id.home_container,new RunningGps()).addToBackStack(null).commit();
                      break;
                    case  R.id.mapRun:
Intent intent = new Intent( HomePage.this , testMap.class );
startActivity( intent );
                }
                return false;
            }
        } );
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onFragmentInteraction(String title) {
        getSupportActionBar().setTitle(title);

    }
}
