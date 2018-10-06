package com.example.aymen.personalcoach;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aymen.personalcoach.data.DBhelper;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment  implements OnDataPointListener,

        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private TextView stepsCount;
    TextView stepsTextHome;
    private static final int REQUEST_OAUTH = 1;
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;
    private static  String LOG_TAG = "TESTtUTS";
    private GoogleApiClient mApiClient;
    private static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1;

    private static final String TAG = "chart";
    BottomNavigationView botNav ;
    PieChart pieChart;
    private String[] xData = {"Mitch", "Jessica" , "Mohammad" , "Kelsey", "Sam", "Robert", "Ashley"};
    private float stapsGoal;
    ArrayList<Entry> yvalues;
    ImageButton updateWeight ;
    ImageButton startRunningHomeBtn ;
    TextView updateDailySteps;
    SharedPreferences prefs;
    ImageButton startRunning;
    Float TotalSteps = 0f;
DBhelper helper;
int idSP;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home , container, false);
        prefs = getActivity().getSharedPreferences("myapp", Context.MODE_PRIVATE);
        idSP =    prefs.getInt( "id" ,0);
helper = new DBhelper( getActivity() );
        updateWeight = view.findViewById( R.id.updateWeight );
        stepsTextHome  = view.findViewById( R.id.stepsTextHome );
        startRunning = view.findViewById( R.id.startRunningHomeBtn );
        updateDailySteps  = view.findViewById( R.id.updateDailySteps );
         helper = new DBhelper( getActivity() );
helper.getDalyStepsById( idSP, new DBhelper.VolleyCallbackGetDailySteps() {
    @Override
    public void onSuccessResponse(String steps) {
        stepsTextHome.setText(steps );

    }

    @Override
    public void onFail(String fail_msg) {

    }
} );
        helper = new DBhelper(getActivity());
        Log.d( TAG, "onCreateView: caloriesPerDats" );

        //Log.d( TAG, "onCreateView: "+String.valueOf(  yvalues.get( 0).getX()));







        pieChart =view.findViewById( R.id.stepPieChart );


        pieChart.setRotationEnabled(true);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(70f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Daily Steps");
        pieChart.setCenterTextSize(10);
        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }
        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType( DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();

        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(getActivity()), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    getActivity(), // your activity
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(getActivity()),
                    fitnessOptions);
        }
        mApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi( Fitness.SENSORS_API)
                .addScope(new Scope( Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        startRunning.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.distancedialogue, (ViewGroup) view.findViewById(R.id.popup_element), false);
                final PopupWindow pwindo = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

                EditText distanceTarget =  layout.findViewById( R.id.distanceTaregetText );
                ImageButton closeWindosw = layout.findViewById( R.id.closewindowDistance );

                closeWindosw.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pwindo.dismiss();
                    }
                } );

                Button setDistance = layout.findViewById( R.id.setDistanceBtn );
                setDistance.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int distance = 0;
                        try {
                            distance  = Integer.parseInt( distanceTarget.getText().toString() );

                        }catch (NumberFormatException e){
                            distance = 0;
                        }

                        Bundle bundle = new Bundle(  );
                        bundle.putInt( "distance", distance );
                        RunningGps runningGps = new RunningGps();
                        runningGps.setArguments( bundle );
                        getFragmentManager().beginTransaction().replace( R.id.home_container  , runningGps).addToBackStack( null ).commit();
                        pwindo.dismiss();

                    }
                } );




            }
        } );
        updateDailySteps.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.updatestepsdialogue, (ViewGroup) view.findViewById(R.id.popup_element), false);
                final PopupWindow pwindo = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);



                EditText updateStepsText =  layout.findViewById( R.id.updateStepsText );
                ImageButton closeWindowUpdateSteps = layout.findViewById( R.id.closeWindowUpdateSteps );

                closeWindowUpdateSteps.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pwindo.dismiss();
                    }
                } );


                Button updateDailyStepsBtn = layout.findViewById( R.id.updateDailyStepsBtn );
                updateDailyStepsBtn.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int tempSteps = Integer.parseInt( updateStepsText.getText().toString());
                        addDataSet( tempSteps -TotalSteps, TotalSteps);
                        stepsTextHome.setText( String.valueOf(tempSteps  ) );

                        helper.updateStepTarget( idSP, Integer.parseInt( updateStepsText.getText().toString() ), new DBhelper.VolleyCallbackUpdateDailySteps() {
                            @Override
                            public void onSuccessResponse(String succes_msg) {


                                Toast.makeText( getActivity(), "your target daily steps updated!", Toast.LENGTH_SHORT ).show();

                                pieChart.notifyDataSetChanged();
                                pieChart.invalidate();
                            }

                            @Override
                            public void onFail(String fail_msg) {
                                Toast.makeText( getActivity(), fail_msg, Toast.LENGTH_SHORT ).show();

                            }
                        } );
                    }
                } );
            }
        } );


        updateWeight.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.updateweightdialogue, (ViewGroup) view.findViewById(R.id.popup_element), false);
                final PopupWindow pwindo = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);



                EditText updateWeightText =  layout.findViewById( R.id.updateWeightText );
                ImageButton closeWindosw = layout.findViewById( R.id.closeWindowUpdate );

                closeWindosw.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pwindo.dismiss();
                    }
                } );


                Button updateWeightBtn = layout.findViewById( R.id.updateWeightBtn );
                updateWeightBtn.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(updateWeightText.getText().toString().matches( "" )){
                            Toast.makeText( getActivity() , "Please enter you new weight", Toast.LENGTH_SHORT ).show();
                        }else {
                            float newweight = Float.valueOf( updateWeightText.getText().toString() );
                            helper.getUserById( idSP, new DBhelper.VolleyCallbackGetUserById() {
                                @Override
                                public void onSuccessResponse(UserInfo userInfo) {

                                    helper.updateWheightAndMBR( idSP, newweight, helper.calculateBMR( userInfo ), new DBhelper.VolleyCallbackUpdateWeightMBR() {
                                        @Override
                                        public void onSuccessResponse(String succes_msg) {
                                            Toast.makeText( getActivity(), "Weigh Updated", Toast.LENGTH_SHORT ).show();
                                            helper.addWeight( idSP, newweight );

                                        }

                                        @Override
                                        public void onFail(String fail_msg) {

                                        }
                                    } );
                                }

                                @Override
                                public void onFail(String msg) {

                                }
                            } );
                            pwindo.dismiss();
                        }
                    }
                } );




            }
        } );

        return view;
    }


    private void addDataSet(float x1 , float x2) {
        float[] yData = {x1, x2};

        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Daily Steps");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset


        ArrayList<Integer> colors = new ArrayList<>();
        colors.add( Color.GRAY);
        colors.add(Color.LTGRAY);


        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);


        pieChart.setData(pieData);
        pieChart.invalidate();

    }

    @Override
    public void onStart() {
        Log.d( TAG, "onStart: " );
        super.onStart();
        mApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();

        Fitness.SensorsApi.remove( mApiClient, this )
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            mApiClient.disconnect();
                        }
                    }
                });
    }

    private void registerFitnessDataListener(DataSource dataSource, DataType dataType) {
        Log.d( TAG, "registerFitnessDataListener: " );
        SensorRequest request = new SensorRequest.Builder()
                .setDataSource( dataSource )
                .setDataType( dataType )
                .setSamplingRate( 3, TimeUnit.SECONDS )
                .build();

        Fitness.SensorsApi.add(mApiClient, request, this)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            Log.e("GoogleFit", "SensorApi successfully added");
                        } else {
                            Log.e("GoogleFit", "adding status: " + status.getStatusMessage());
                        }
                    }
                });
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d( TAG, "onConnected: " );
        DataSourcesRequest dataSourceRequest = new DataSourcesRequest.Builder()
                .setDataTypes( DataType.TYPE_STEP_COUNT_CUMULATIVE )
                //.setDataSourceTypes( DataSource.TYPE_DERIVED )
                .build();

        ResultCallback<DataSourcesResult> dataSourcesResultCallback = new ResultCallback<DataSourcesResult>() {
            @Override
            public void onResult(DataSourcesResult dataSourcesResult) {
                Log.d( TAG, "onResult: " +dataSourcesResult.toString());
                Log.d( TAG, "onResult: "+dataSourcesResult );
                for( DataSource dataSource : dataSourcesResult.getDataSources() ) {
                    Log.d( TAG, "onResult: datatype "+dataSource.getType() );
                    if( DataType.TYPE_STEP_COUNT_CUMULATIVE.equals( dataSource.getDataType() ) ) {
                        Log.d( TAG, "onResult: regiter" );
                        registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_CUMULATIVE);
                    }
                }
            }
        };

        Fitness.SensorsApi.findDataSources(mApiClient, dataSourceRequest)
                .setResultCallback(dataSourcesResultCallback);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d( TAG, "onConnectionFailed:teszt 8899999 " );

        if( !authInProgress ) {
            try {
                authInProgress = true;
                connectionResult.startResolutionForResult( getActivity(), REQUEST_OAUTH );
                Log.d( TAG, "onConnectionFailed:teszt 88 " );
            } catch(IntentSender.SendIntentException e ) {
                Log.e( "GoogleFit", "sendingIntentException " + e.getMessage() );
            }
        } else {
            Log.e( "GoogleFit", "authInProgress" );
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode == REQUEST_OAUTH ) {
            authInProgress = false;
            if( resultCode == getActivity().RESULT_OK ) {
                if( !mApiClient.isConnecting() && !mApiClient.isConnected() ) {
                    mApiClient.connect();
                }
            } else if( resultCode == getActivity().RESULT_CANCELED ) {
                Log.e( "GoogleFit", "RESULT_CANCELED" );
            }
        } else {
            Log.e("GoogleFit", "requestCode NOT request_oauth");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d( TAG, "onConnectionSuspended: " );
    }

    @Override
    public void onDataPoint(DataPoint dataPoint) {
        Log.d( TAG, "onDataPoint: " );
        for( final Field field : dataPoint.getDataType().getFields() ) {
            Log.d( TAG, "onDataPoint: " +field.getName());
            final Value value = dataPoint.getValue( field );
            TotalSteps = Float.valueOf( value.asInt());
            Log.d( TAG, "onDataPoint: "+value );
            pieChart.setCenterText( "Your daily Steps "+String.valueOf( value.asInt()) );

            int dailySteps  = 10000;
            helper.getDalyStepsById( idSP, new DBhelper.VolleyCallbackGetDailySteps() {

                @Override
                public void onSuccessResponse(String steps) {
                    addDataSet(Float.valueOf( steps )-Float.valueOf( value.asInt()), Float.valueOf( value.asInt()));


                }

                @Override
                public void onFail(String fail_msg) {

                }
            } );

            Toast.makeText(getActivity(), "Field: " + field.getName() + " Value: " + value, Toast.LENGTH_SHORT).show();
            //   stepsCount.setText( value.asInt() );
        }

    }
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String title);
    }
}
