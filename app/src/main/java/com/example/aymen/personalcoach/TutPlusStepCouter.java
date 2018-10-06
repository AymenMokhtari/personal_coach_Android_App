package com.example.aymen.personalcoach;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.concurrent.TimeUnit;
// work
public class TutPlusStepCouter extends AppCompatActivity  implements OnDataPointListener,

        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private TextView stepsCount;
        private static final int REQUEST_OAUTH = 1;
        private static final String AUTH_PENDING = "auth_state_pending";
        private boolean authInProgress = false;
        private static  String TAG = "TESTtUTS";
        private static  String LOG_TAG = "TESTtUTS";
        private GoogleApiClient mApiClient;
        private static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 1;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tut_plus_step_couter);
            Log.d( TAG, "onCreate: " );
stepsCount = findViewById( R.id.stepsText );
            if (savedInstanceState != null) {
                authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
            }
        FitnessOptions fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();

        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this, // your activity
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(this),
                    fitnessOptions);
        }
        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(Fitness.SENSORS_API)
                .addScope(new Scope( Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        }

        @Override
        protected void onStart() {
            Log.d( TAG, "onStart: " );
            super.onStart();
            mApiClient.connect();
        }

        @Override
        protected void onStop() {
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
                    connectionResult.startResolutionForResult( TutPlusStepCouter.this, REQUEST_OAUTH );
                    Log.d( TAG, "onConnectionFailed:teszt 88 " );
                } catch(IntentSender.SendIntentException e ) {
                    Log.e( "GoogleFit", "sendingIntentException " + e.getMessage() );
                }
            } else {
                Log.e( "GoogleFit", "authInProgress" );
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if( requestCode == REQUEST_OAUTH ) {
                authInProgress = false;
                if( resultCode == RESULT_OK ) {
                    if( !mApiClient.isConnecting() && !mApiClient.isConnected() ) {
                        mApiClient.connect();
                    }
                } else if( resultCode == RESULT_CANCELED ) {
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
                Log.d( TAG, "onDataPoint: "+value );
                stepsCount.setText( String.valueOf( value.asInt()) );

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Field: " + field.getName() + " Value: " + value, Toast.LENGTH_SHORT).show();
                     //   stepsCount.setText( value.asInt() );

                    }
                });
            }
            
        }


}