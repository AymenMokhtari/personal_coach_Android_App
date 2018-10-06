package com.example.aymen.personalcoach;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aymen.personalcoach.data.DBhelper;
import com.example.aymen.personalcoach.tools.RunningData;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {
    private static final String TAG = "test123";
    private GoogleApiClient mGoogleApiClient;
    LineData data;
    LineData data2;
    LineChart mChart ;
    LineChart mChartweight ;
    LineChart caloriesChart;
    ArrayList<Entry> yvalues;
       ArrayList<Entry> cvalues;
       DBhelper helper;
    SharedPreferences prefs;
    ListView runingDataHistory;
    int idSP;
    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history , container, false);
helper = new DBhelper(getActivity());
        Log.d( TAG, "onCreateView: caloriesPerDats" );

        runingDataHistory = view.findViewById( R.id.runingDataHistory );
        prefs = getActivity().getSharedPreferences("myapp", Context.MODE_PRIVATE);

        idSP =    prefs.getInt( "id" ,0);
        helper.getRunningDate( idSP, new DBhelper.VolleyCallbackRunningData() {
            @Override
            public void onSuccessResponse(ArrayList<RunningData> result) {

                try {


                runingDataHistory.setAdapter( new runningDataAdapter(getActivity(), R.layout.one_runningdata_item, result ) );
                
                }catch (NullPointerException e){
                    Toast.makeText( getActivity(), "Something went wrong", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onFail(String msg) {

            }
        } );
helper.getWeightHistory( idSP, new DBhelper.VolleyCallback() {
    @Override
    public void onSuccessResponse(ArrayList<Entry> dataEntry ) {

        yvalues = new ArrayList<>(dataEntry);
        Collections.sort(dataEntry, new EntryXComparator());
        Log.d( TAG, "onSuccessResponse: 1" );
        mChartweight = (LineChart)view.findViewById(R.id.stepsLineChart);
        mChartweight = (LineChart)view.findViewById(R.id.weightsLineChart);
        mChartweight.setDragEnabled( true );
        mChartweight.setScaleEnabled( true );


        Log.d( TAG, "onSuccessResponse: 2" );

        LineDataSet set1 = new LineDataSet(dataEntry , "Date Set 1");
        Log.d( TAG, "onSuccessResponse: 3" );

        set1.setFillAlpha(110);
        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        Log.d( TAG, "onSuccessResponse: 4" );

        data = new LineData(set1);
        Log.d( TAG, "onSuccessResponse: 5" );

        set1.setColor( Color.BLUE );

        mChartweight.setData(data);
        Log.d( TAG, "onSuccessResponse: 6" );

        mChartweight.invalidate();
        Log.d( TAG, "onSuccessResponse: 7" );












    }

    @Override
    public void onFail(String msg) {

    }
} );

        helper.caloriesPerDays(idSP, new DBhelper.VolleyCallback() {
    @Override
    public void onSuccessResponse(ArrayList<Entry> result) {
        yvalues = new ArrayList<>(result);
        Collections.sort(result, new EntryXComparator());
        Log.d( TAG, "onSuccessResponse: 1" );
        mChart = (LineChart)view.findViewById(R.id.stepsLineChart);
        mChartweight = (LineChart)view.findViewById(R.id.weightsLineChart);
        mChartweight.setDragEnabled( true );
        mChartweight.setScaleEnabled( true );


        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        Log.d( TAG, "onSuccessResponse: 2" );

        LineDataSet set1 = new LineDataSet(result , "Date Set 1");
        Log.d( TAG, "onSuccessResponse: 3" );

        set1.setFillAlpha(110);
        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        Log.d( TAG, "onSuccessResponse: 4" );

        data = new LineData(set1);
        Log.d( TAG, "onSuccessResponse: 5" );

        set1.setColor( Color.BLUE );

        mChart.setData(data);
        Log.d( TAG, "onSuccessResponse: 6" );

        mChart.invalidate();
        Log.d( TAG, "onSuccessResponse: 7" );

    }

    @Override
    public void onFail(String msg) {
        Log.d( TAG, "onFail: "+msg );
    }
} );




        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi( Fitness.HISTORY_API)
                .addScope(new Scope( Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(this).build();

/*
        new HistoryFragment.ViewWeekStepCountTask().execute();
*/
        if (mListener != null) {
            mListener.onFragmentInteraction("hist");
        }
        return view;
    }

    @Override
    public void onClick(View view) {

    }
    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


/*
    private void showDataSet(DataSet dataSet) {
        Log.e("History", "Data returned for Data type: " + dataSet.getDataType().getName());
        DateFormat dateFormat = DateFormat.getDateInstance();
        DateFormat timeFormat = DateFormat.getTimeInstance();

        for (DataPoint dp : dataSet.getDataPoints()) {
            Log.e("History", "Data point:");
            Log.e("History", "\tType: " + dp.getDataType().getName());
            Log.e("History", "\tStart: " + dateFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)) + " " + timeFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
            Log.e("History", "\tEnd: " + dateFormat.format(dp.getEndTime(TimeUnit.MILLISECONDS)) + " " + timeFormat.format(dp.getStartTime(TimeUnit.MILLISECONDS)));
            for(Field field : dp.getDataType().getFields()) {
                Log.e("History", "\tField: " + field.getName() +
                        " Value: " + dp.getValue(field));
            }
        }
    }
    private void displayLastWeeksData() {
        Calendar cal = Calendar.getInstance();
        Date now = new Date();
        cal.setTime(now);
        long endTime = cal.getTimeInMillis();
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        long startTime = cal.getTimeInMillis();

        DateFormat dateFormat = DateFormat.getDateInstance();

        Log.e("History", "Range Start: " + dateFormat.format(startTime));
        Log.e("History", "Range End: " + dateFormat.format(endTime));

        //Check how many steps were walked and recorded in the last 7 days
        DataReadRequest readRequest = new DataReadRequest.Builder()
                .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
                //.aggregate(DataType.TYPE_CALORIES_EXPENDED, DataType.AGGREGATE_CALORIES_EXPENDED)
                .bucketByTime(1, TimeUnit.DAYS)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .build();
        DataReadResult dataReadResult = Fitness.HistoryApi.readData(mGoogleApiClient, readRequest).await(1, TimeUnit.MINUTES);
        Log.d( TAG, "displayLastWeeksData: "+dataReadResult.getBuckets().size() );

        //Used for aggregated data
        if (dataReadResult.getBuckets().size() > 0) {
            Log.e("History", "Number of buckets: " + dataReadResult.getBuckets().size());
            for (Bucket bucket : dataReadResult.getBuckets()) {
                List<DataSet> dataSets = bucket.getDataSets();
                for (DataSet dataSet : dataSets) {
                    showDataSet(dataSet);
                }
            }
        }
        //Used for non-aggregated data
        else if (dataReadResult.getDataSets().size() > 0) {
            Log.e("History", "Number of returned DataSets: " + dataReadResult.getDataSets().size());
            for (DataSet dataSet : dataReadResult.getDataSets()) {
                showDataSet(dataSet);
            }
        }
    }




    private class ViewWeekStepCountTask extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            displayLastWeeksData();
            return null;
        }
    }
*/
        @Override
        public void onPause() {
            super.onPause();
            mGoogleApiClient.disconnect();
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
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String title);
    }


}


