package com.example.aymen.personalcoach;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.example.aymen.personalcoach.data.DBhelper;
import com.example.aymen.personalcoach.tools.Session;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        P1Fragment.OnFragmentInteractionListener,
        P2Fragment.OnFragmentInteractionListener,
        P3Fragment.OnFragmentInteractionListener,
        P4Fragment.OnFragmentInteractionListener,
        P5Fragment.OnFragmentInteractionListener,
        P6Fragment.OnFragmentInteractionListener,
        P1Fragment.SendMessage,
        P2Fragment.SendMessage,
        P3Fragment.SendMessage,
        P4Fragment.SendMessage,
        P5Fragment.SendMessage,
        P6Fragment.SendMessage
{

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
UserInfo mainUser;
int lastPage =0;
boolean right =true;
    DBhelper helper;

    private boolean canSwipe = false;

    /**activity listener to alert fragments**/
//keep track od listeners
    private List<DataUpdateListener> mListeners;
    private List<TimeUpdateListener> timeListeners;
    /**ends**/
    Session session ;

SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new Session( this );
        prefs = getSharedPreferences("myapp", Context.MODE_PRIVATE);

         int idSP =    prefs.getInt( "id" ,0);
         String emailSP =      prefs.getString( "email" ,  null );
         String passwordSP =   prefs.getString( "password" , null );


if(session.loggedin()){

    Intent intent  = new Intent( this , HomePage.class );
    startActivity( intent );
}

        mainUser = new UserInfo( );
        helper = new DBhelper( this );
        mListeners = new ArrayList<>();
        timeListeners = new ArrayList<>();
        pager = (ViewPager) findViewById(R.id.pager);
        final StepperIndicator indicator = (StepperIndicator) findViewById(R.id.stepper_indicator);
        assert pager != null;
        pagerAdapter = new com.example.aymen.personalcoach.PagerAdapter(getSupportFragmentManager());

        pager.setAdapter(pagerAdapter);

        //stop draging pager
        pager.beginFakeDrag();

        //
        indicator.setViewPager(pager, true);
        indicator.addOnStepClickListener(new StepperIndicator.OnStepClickListener() {
            @Override
            public void onStepClicked(int step) {
                if(step<=pager.getCurrentItem()){
                    pager.setCurrentItem(step, true);
                }

            }
        });
pager.setOnPageChangeListener( new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(lastPage>position)
        {

            right = false;

        }
    else if(lastPage<position)
                {

                    right = true;

                }
        lastPage=position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
} );
        pager.setOnTouchListener(new OnSwipeTouchListener() {
            public void onSwipeRight() {

                Log.d("hh", "onSwipeRight: ");
                if(canSwipe){
                    pager.setCurrentItem(pager.getCurrentItem()-1);
                    //canSwipe = false;
                }
            }

            public void onSwipeLeft() {

                Log.d("hh", "onSwipeLeft: ");

//nothing, this means,swipes to left will be ignored
            }

        });

    }



    public synchronized void registerDataUpdateListener(DataUpdateListener listener) {
        mListeners.add(listener);
    }

    public synchronized void unregisterDataUpdateListener(DataUpdateListener listener) {
        mListeners.remove(listener);
    }

    //register listener for time

    public synchronized void registerTimeUpdateListener(TimeUpdateListener listener) {
        timeListeners.add(listener);
    }

    public synchronized void unregisterTimeUpdateListener(TimeUpdateListener listener) {
        timeListeners.remove(listener);
    }


    @Override
    public void onFragment1Interaction(boolean drag) {
        canSwipe =drag;
        if(drag){

            if(pager!=null){
                pager.setCurrentItem(pager.getCurrentItem()+1,true);
                Log.d( "current item ", "onFragment2Interaction: " +pager.getCurrentItem());

            }
        }

    }


    @Override
    public void onFragment2Interaction(boolean drag) {
        canSwipe =drag;
        if(drag){

            if(pager!=null){
                pager.setCurrentItem(pager.getCurrentItem()+1,true);
                Log.d( "current item ", "onFragment2Interaction: " +pager.getCurrentItem());
            }
        }

    }

    @Override
    public void onFragment3Interaction(boolean drag) {
        canSwipe =drag;
        if(drag){

            if(pager!=null){
                pager.setCurrentItem(pager.getCurrentItem()+1,true);
                Log.d( "current item ", "onFragment2Interaction: " +pager.getCurrentItem());

            }
        }

    }

    @Override
    public void onFragment4Interaction(boolean drag) {
        canSwipe =drag;
        if(drag){

            if(pager!=null){
                pager.setCurrentItem(pager.getCurrentItem()+1,true);
                Log.d( "current item ", "onFragment2Interaction: " +pager.getCurrentItem());

            }
        }

    }
    @Override
    public void onFragment5Interaction(boolean drag) {
        canSwipe =drag;
        if(drag){

            if(pager!=null){
                pager.setCurrentItem(pager.getCurrentItem()+1,true);
            }
        }

    }

    @Override
    public void onFragment6Interaction(boolean drag) {
        canSwipe =drag;
        if(drag){

            if(pager!=null){
                pager.setCurrentItem(pager.getCurrentItem()+1,true);
            }
        }

    }



    @Override
    public void sendData5(UserInfo user) {
        if(right){
            mainUser.setGoal( user.getGoal() );
            mainUser.setRole( user.getRole() );
        String tag = "android:switcher:" + R.id.pager + ":" + 5;
        P6Fragment f = (P6Fragment) getSupportFragmentManager().findFragmentByTag(tag);
        f.displayReceivedData(mainUser);
    }
    }
    @Override
    public void sendData4(UserInfo user) {
        if(right){
            mainUser.setActivitylevel( user.getActivitylevel() );
        String tag = "android:switcher:" + R.id.pager + ":" + 4;
        P5Fragment f = (P5Fragment) getSupportFragmentManager().findFragmentByTag(tag);
        f.displayReceivedData(mainUser);
    }}

    @Override
    public void sendData2(UserInfo user) {
            if(right){
            mainUser.setCurrentWheight( user.getCurrentWheight() );
            mainUser.setGender( user.getGender());
        String tag = "android:switcher:" + R.id.pager + ":" + 2;
        P3Fragment f = (P3Fragment) getSupportFragmentManager().findFragmentByTag(tag);
        f.displayReceivedData(mainUser);
    }}

    @Override
    public void sendData6(UserInfo user) {
        if(right){
            mainUser.setFirstname( user.getFirstname() );
            mainUser.setLastname( user.getLastname() );
            mainUser.setEmail( user.getEmail() );
            mainUser.setPassword( user.getPassword() );
            helper.registerUser( mainUser, new DBhelper.VolleyCallbackUserLogin() {
                @Override
                public void onSuccessResponse(int id, String email, String password) {
                    session.setLoggedin(true,email , password , id);
Intent intent = new Intent( MainActivity.this , HomePage.class );
startActivity( intent );

                }

                @Override
                public void onFail(String msg) {

                }
            } );
            String tag = "android:switcher:" + R.id.pager + ":" + 6;
            P6Fragment f = (P6Fragment) getSupportFragmentManager().findFragmentByTag(tag);
            Toast.makeText( this, mainUser.toString() , Toast.LENGTH_SHORT  ).show();
         //   f.displayReceivedData(mainUser);
        }
    }

    @Override
    public void sendData1(UserInfo user) {
                if(right){

                    mainUser.setFirstname( user.getFirstname() );
        String tag = "android:switcher:" + R.id.pager + ":" + 1;
        P2Fragment f = (P2Fragment) getSupportFragmentManager().findFragmentByTag(tag);
        f.displayReceivedData(mainUser);
    }}

    @Override
    public void sendData3(UserInfo user) {
                    if(right){
                        mainUser.setHeight( user.getHeight() );
        mainUser.setDateOfBirth( user.getDateOfBirth() );
        String tag = "android:switcher:" + R.id.pager + ":" + 3;
        P4Fragment f = (P4Fragment) getSupportFragmentManager().findFragmentByTag(tag);
        f.displayReceivedData(mainUser);
                    }
    }

}
