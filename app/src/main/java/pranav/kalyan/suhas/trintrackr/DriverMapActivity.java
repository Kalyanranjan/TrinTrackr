package pranav.kalyan.suhas.trintrackr;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DriverMapActivity extends FragmentActivity implements OnMapReadyCallback , LocationListener{

    // Broad Street other end location = 41.745589, -72.687198
    // Good step for longitude (2nd value) will be -0.000043 per second
    // Good step for latitude (1st value) will be -0.0002 per second

    private GoogleMap mMap;
    private LatLng trin = new LatLng(41.747270, -72.690354);
    private LatLng home = new LatLng(41.744433, -72.69118110);

    private double lati = 41.747270;
    private double longi = 72.690354;
    private Marker mVehicle;

    private Button mShuttleStart;
    private Button mShuttleStop;
    private boolean mShuttleStarted;
    private Marker mPassenger1;

    private int mInterval = 1000; // 1 seconds by default, can be changed later
    private int mTest = 1000;
    private Handler mHandler;
    private LocationManager locationManager;


    private Handler mHandler2 = new Handler();
    private final GetStLocActivity getStudent = new GetStLocActivity(DriverMapActivity.this);

    public String mDriver;

    /* Students on the road */
    private int mNumStudent = 0;
    private int prevNumPassengers = 0;
    private boolean[] mMarkerTracker = {false, false, false, false, false, false, false, false, false, false};
    //private boolean[] mMarkerTracker2 = {false, false, false, false, false, false, false, false, false, false};
    private Marker[] mPassengers = new Marker[10];
    private String[] mStudents = new String[30];

    public String toString(){
        String string = "|";
        for (int i=1; i<=mNumStudent; i++){
            string+=this.mStudents[3*i-3]+" | "+this.mStudents[3*i-2]+" | "+this.mStudents[3*i-1]+" | ";
        }
        return string;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.driverMap);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, this);

        getStudent.execute();
        mHandler2.postDelayed(new Runnable() {
            public void run() {
                mNumStudent = getStudent.getStNum();
                mStudents = getStudent.getStudents();
                //Toast.makeText(DriverMapActivity.this, getStudent.toString(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(DriverMapActivity.this, String.valueOf(mNumStudent), Toast.LENGTH_SHORT).show();
            }
        }, 500);

        mShuttleStart = (Button) findViewById(R.id.driver_start_shuttle);
        mShuttleStop = (Button) findViewById(R.id.driver_stop_shuttle);

        mShuttleStarted = false;

        mShuttleStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShuttleStarted = true;
                mShuttleStop.setEnabled(true);
                mShuttleStart.setEnabled(false);
                mShuttleStarted = true;

                Toast.makeText(DriverMapActivity.this, "Starting Shuttle", Toast.LENGTH_SHORT).show();
                new DriverRequestActivity(DriverMapActivity.this).execute("kalyan", "1", "41.744433", "-72.69118110");
            }
        });

        mShuttleStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShuttleStop.setEnabled(false);
                mShuttleStart.setEnabled(true);

                mShuttleStarted = false;


                Toast.makeText(DriverMapActivity.this, "Stopping Shuttle", Toast.LENGTH_SHORT).show();
                new DriverRequestActivity(DriverMapActivity.this).execute("kalyan", "0", "0", "0");

            }
        });


        mHandler = new Handler();
        startRepeatingTask();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trin, 16));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        mMap.animateCamera(zoom);
        MarkerOptions mOptions = new MarkerOptions().position(home).title("This is my title")
                .snippet("and snippet")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mVehicle = mMap.addMarker(mOptions);
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {


            final GetStLocActivity getStudent = new GetStLocActivity(DriverMapActivity.this);
            getStudent.execute();
            //Toast.makeText(DriverMapActivity.this, "hola", Toast.LENGTH_SHORT).show();
            Handler mHandler2 = new Handler();
            mHandler2.postDelayed(new Runnable() {
                public void run() {

                    mNumStudent = getStudent.getStNum();
                    mStudents = getStudent.getStudents();
                    MarkerOptions mOptions = new MarkerOptions().position(home).title("Student");

                    for (int i=0; i<10; i++) {
                        if (mMarkerTracker[i]) {
                            mPassengers[i].remove();
                            mMarkerTracker[i] = false;
                        }
                    }

                    for (int i=0; i<10; i++) {
                        if ( i < mNumStudent){
                            mPassengers[i] = mMap.addMarker(mOptions);
                            mMarkerTracker[i] = true;
                            lati = Double.parseDouble(mStudents[3 * i + 1]);
                            longi = Double.parseDouble(mStudents[3*i+2]);
                            mPassengers[i].setPosition(new LatLng(lati, longi));
                        }
//                        else {
//                            if (i < prevNumPassengers) {
//                                mPassengers[i].remove();
//                                Toast.makeText(DriverMapActivity.this, String.valueOf(mNumStudent), Toast.LENGTH_SHORT).show();
//                            }
//                        }
                    }

//                    prevNumPassengers = mNumStudent;

//                    if (mNumStudent > 0) {
//                            mPassenger1 = mMap.addMarker(mOptions);
////                            mStRequested = 1;
//                        lati = Double.parseDouble(mStudents[1]);
//                        longi = Double.parseDouble(mStudents[2]);
//                        mPassenger1.setPosition(new LatLng(lati, longi));
//                    } else {
////                        if (mStRequested == 1) {
//                            mPassenger1.remove();
////                            mStRequested = 0;
//                        }
//                    }

                    //Toast.makeText(DriverMapActivity.this, getStudent.toString(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(DriverMapActivity.this, String.valueOf(mNumStudent), Toast.LENGTH_SHORT).show();
                }
            }, 500);

            //getDriver.cancel(true);

            //mStudent.setPosition(new LatLng(lati, longi));
            //mShuttle.setPosition(new LatLng(lati, -longi));


            mHandler.postDelayed(mStatusChecker, mInterval);
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }
    @Override
    public void onLocationChanged(Location location) {

        String msg = "New Latitude: " + location.getLatitude()
                + "New Longitude: " + location.getLongitude();

        MarkerOptions mar = new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .title("This is my title")
                .snippet("and snippet")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));

        mVehicle.remove();
        mVehicle = mMap.addMarker(mar);

        if (mShuttleStarted)
            new DriverRequestActivity(DriverMapActivity.this).execute("kalyan", "1",
                        String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));

        //Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Gps is turned off!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {

        Toast.makeText(getBaseContext(), "Gps is turned on!! ",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

}