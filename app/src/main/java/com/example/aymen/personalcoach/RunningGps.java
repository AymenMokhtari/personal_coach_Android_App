package com.example.aymen.personalcoach;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.example.aymen.personalcoach.data.DBhelper;
import com.example.aymen.personalcoach.tools.GPSConversion;
import com.example.aymen.personalcoach.tools.GPSData;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.ArrayList;
import java.util.List;

import pl.pawelkleczkowski.customgauge.CustomGauge;


/**
 */
public class RunningGps extends Fragment implements LocationListener, OnMapReadyCallback {
    CustomGauge currentSpeed;
    private Context context;
    ToggleButton startstopBtn;
    private List<GPSData> gpsDatas = new ArrayList<>();
    private List<Double> distances = new ArrayList<>();
    private LocationManager locationManager;
    private boolean collecting;
    private MapView mapView;
    private GoogleMap mMap;
    ArrayList<LatLng> latLngs ;
    private OnFragmentInteractionListener mListener;
    int distance;
    View view;
    TextView currentSpeedText;
    ProgressBar distanceProgress;
    Button finishRun;
    DBhelper helper;
    SharedPreferences prefs;
    int idSP;
    double totalDistance;
    int seconds ;
    int minutes ;
    UserInfo user ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_running_gps, container, false );
        locationManager = (LocationManager) getActivity().getSystemService( Context.LOCATION_SERVICE );
        prefs = getActivity().getSharedPreferences("myapp", Context.MODE_PRIVATE);

        idSP =    prefs.getInt( "id" ,0);
        helper = new DBhelper( getActivity() );

distanceProgress = view.findViewById( R.id.distanceProgressBar );

        finishRun =  view.findViewById( R.id.finishRun );
        finishRun.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                helper.getUserById( idSP, new DBhelper.VolleyCallbackGetUserById() {
                    @Override
                    public void onSuccessResponse(UserInfo userInfo) {
                        Double caloriesBurned  = 1.038*totalDistance*userInfo.getCurrentWheight();
                        helper.addRunningDate(idSP, totalDistance , minutes+"m "+":"+seconds+"s "+"/Km" ,caloriesBurned  );

                        Toast.makeText( getActivity(), "Total Distance: "+totalDistance +" | "+"Calories Burned: "+caloriesBurned, Toast.LENGTH_LONG ).show();

                    }

                    @Override
                    public void onFail(String msg) {

                    }
                } );

                getFragmentManager().beginTransaction().replace(R.id.home_container,new HomeFragment()).addToBackStack(null).commit();
            }
        } );


Bundle bundle = getArguments();


         distance = bundle.getInt("distance"  );
        distanceProgress.setMax(distance *1000 );

        collecting = false;
        mapView = view.findViewById( R.id.mapView );
        latLngs=  new ArrayList<>(  );
currentSpeed = view.findViewById( R.id.currentSpeed );
        currentSpeedText = view.findViewById( R.id.currentSpeedText );
        mapView.onCreate( savedInstanceState );
        mapView.getMapAsync( this );
        startstopBtn = view.findViewById( R.id.btnStartStop );
        startstopBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop( view );
            }
        } );
        if (mListener != null) {
            mListener.onFragmentInteraction("Run");
        }
        return view;
    }


    @Override
    public void onLocationChanged(Location location) {
        setGPSStatus( " " );

        GPSData data = new GPSData( location.getTime(), location.getLatitude(), location.getLongitude() );
        gpsDatas.add( data );

        updateDistance();
        updatePace();

      //  Toast.makeText( this ,  String.valueOf(  location.getLatitude()) , Toast.LENGTH_SHORT  ).show();
       // mMap.moveCamera( CameraUpdateFactory.newLatLng(  new LatLng( location.getLatitude() ,location.getLongitude() ) ) );




        latLngs.add( new LatLng( location.getLatitude() ,location.getLongitude() )) ;

        drawPolyLineOnMap(latLngs ,mMap);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        if (i == LocationProvider.AVAILABLE) {
            setGPSStatus( "Connected" );
        } else if (i == LocationProvider.OUT_OF_SERVICE) {
            setGPSStatus( "No Connection" );
        } else if (i == LocationProvider.TEMPORARILY_UNAVAILABLE) {
            setGPSStatus( "No Connection" );
        }
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void setGPSStatus(String s) {
        TextView gps = (TextView) view.findViewById( R.id.statusgps );
        gps.setText( s );
    }

    private void setTime(int seconds, int minutes, TextView currentPaceText) {
        currentPaceText.setText( String.format( "%d:%02d", minutes, seconds ) + " / mile" );
        currentSpeed.setValue( minutes );
        currentSpeedText.setText( String.valueOf( minutes   ) );
    }

    private void updateTotalPace() {
        if (gpsDatas.size() > 1) {
            GPSData start = gpsDatas.get( 0 );
            GPSData end = gpsDatas.get( gpsDatas.size() - 1 );
            long time = end.timestamp - start.timestamp;
            double distance = sumDistances();

            double pace = time / distance;

             seconds = (int) Math.round( pace / 1000 );
             minutes = seconds / 60;
            seconds = seconds % 60;

            TextView paceText = (TextView) view.findViewById( R.id.averageTxt );
            setTime( seconds, minutes, paceText );


        }
    }

    private double sumDistances(int startIndex, int endIndex) {
        double sum = 0;
        int index = startIndex;
        do {
            sum += distances.get( index );
            index++;
        } while (index != endIndex);
        return sum;
    }

    private double sumDistances() {
        double sum = 0;
        for (Double num : distances) {
            sum += num;
        }
        return sum;
    }

    private void updateDistance() {
        calculateAndAddDistance();

         totalDistance = sumDistances();

        TextView distanceText = (TextView) view.findViewById( R.id.distanceTxt );
        distanceText.setText( String.format( "%.2f miles", totalDistance ) );
        distanceProgress.setProgress( (int)(totalDistance*1000 ));
    }

    private void calculateAndAddDistance() {
        if (gpsDatas.size() > 1) {
            GPSData start = gpsDatas.get( gpsDatas.size() - 2 );
            GPSData end = gpsDatas.get( gpsDatas.size() - 1 );

            double distance = GPSConversion.distance( start.latitude, start.longitude, end.latitude, end.longitude, 'M' );
            distances.add( distance );
        }
    }

    private void updatePace() {
        updateCurrentPace();
        updateTotalPace();
    }

    private void updateCurrentPace() {
        if (gpsDatas.size() > 6) {
            GPSData start = gpsDatas.get( gpsDatas.size() - 6 );
            GPSData end = gpsDatas.get( gpsDatas.size() - 1 );
            long time = end.timestamp - start.timestamp;
            double distance = sumDistances( gpsDatas.size() - 7, gpsDatas.size() - 2 );
            double currentPace = time / distance;

            int seconds = (int) Math.round( currentPace / 1000 );
            int minutes = seconds / 60;
            seconds = seconds % 60;

            TextView currentPaceText = (TextView) view.findViewById( R.id.currentTxt );
            setTime( seconds, minutes, currentPaceText );
        }
    }

    public void startStop(View view) {
        if (collecting) {
            System.out.println( "Stopped collecting" );
            collecting = false;

            if (locationManager.getAllProviders().size() != 0) {
                locationManager.removeUpdates( this );
            }
        } else {
            System.out.println( "Started collecting" );
            collecting = true;
            if (locationManager.getAllProviders().size() != 0) {

                gpsDatas = new ArrayList<>();
                distances = new ArrayList<>();
                if (ActivityCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 1000, 2, this );
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // DO WHATEVER YOU WANT WITH GOOGLEMAP
        googleMap.setMapType( GoogleMap.MAP_TYPE_HYBRID );
        if (ActivityCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled( true );
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng sydney = new LatLng( -34, 151 );
        mMap.addMarker( new MarkerOptions().position( sydney ).title( "Marker in Sydney" ) );
        mMap.moveCamera( CameraUpdateFactory.newLatLng( sydney ) );
    }



    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
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

    public void drawPolyLineOnMap(List<LatLng> list ,GoogleMap mMap) {
        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color( Color.RED);
        polyOptions.width(5);
        polyOptions.addAll(list);

        mMap.clear();
        mMap.addPolyline(polyOptions);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : list) {
            builder.include(latLng);
        }

        final LatLngBounds bounds = builder.build();

        //BOUND_PADDING is an int to specify padding of bound.. try 100.
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
        mMap.animateCamera(cu);
    }
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String title);
    }

}
      
    
