package pranav.kalyan.suhas.trintrackr;

import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class DriverMapActivity extends FragmentActivity implements OnMapReadyCallback {

    // Broad Stree other end location = 41.745589, -72.687198
    // Good step for longitude (2nd value) will be -0.000043 per second
    // Good step for latitude (1st value) will be -0.0002 per second

    private GoogleMap mMap;
    private LatLng trin = new LatLng(41.747270, -72.690354);
    private LatLng home = new LatLng(41.752264, -72.687111);
    private Marker mVehicle;
    private Button mPass1Button;
    private Button mPass2Button;
    private Button mPass3Button;

    private Button mShuttleStart;
    private Button mShuttleStop;
    private boolean mShuttleStarted;

    private Marker mPassenger1;
    private Marker mPassenger2;
    private Marker Passenger3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.driverMap);
        mapFragment.getMapAsync(this);

        mPass1Button = (Button) findViewById(R.id.passenger1);
        mPass2Button = (Button) findViewById(R.id.passenger2);
        mPass3Button = (Button) findViewById(R.id.passenger3);
        mShuttleStart = (Button) findViewById(R.id.driver_start_shuttle);
        mShuttleStop = (Button) findViewById(R.id.driver_stop_shuttle);

        mShuttleStarted = false;
        mPass1Button.setEnabled(false);
        mPass2Button.setEnabled(false);
        mPass3Button.setEnabled(false);
        mShuttleStop.setEnabled(false);
        mShuttleStart.setEnabled(true);

        mPass1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Make passenger 1 appear
            }
        });

        mPass2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Make passenger 2 appear and disappear on clicking again maybe
            }
        });

        mPass3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Make passenger 3 appear
            }
        });

        mShuttleStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShuttleStarted = true;
                mPass1Button.setEnabled(true);
                mPass2Button.setEnabled(true);
                mPass3Button.setEnabled(true);
                mShuttleStop.setEnabled(true);
                mShuttleStart.setEnabled(false);
            }
        });

        mShuttleStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShuttleStarted = false;
                mPass1Button.setEnabled(false);
                mPass2Button.setEnabled(false);
                mPass3Button.setEnabled(false);
                mShuttleStop.setEnabled(false);
                mShuttleStart.setEnabled(true);
            }
        });




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
        MarkerOptions mOptions = new MarkerOptions().position(home).title("Where do you want the shuttle?");
        mVehicle = mMap.addMarker(mOptions);
        mVehicle.setDraggable(true);
    }

/*
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 16));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        mMap.animateCamera(zoom);
        MarkerOptions mOptions = new MarkerOptions().position(home).title("Where do you want the shuttle?");
        mStudent = mMap.addMarker(mOptions);
        mStudent.setDraggable(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home));
    }
    */
}
//
//package pranav.kalyan.suhas.trintrackr;
//
//        import android.os.Handler;
//        import android.support.v4.app.FragmentActivity;
//        import android.os.Bundle;
//        import android.view.Gravity;
//        import android.view.View;
//        import android.widget.Button;
//        import android.widget.TextView;
//
//        import com.google.android.gms.maps.CameraUpdate;
//        import com.google.android.gms.maps.CameraUpdateFactory;
//        import com.google.android.gms.maps.GoogleMap;
//        import com.google.android.gms.maps.OnMapReadyCallback;
//        import com.google.android.gms.maps.SupportMapFragment;
//        import com.google.android.gms.maps.model.LatLng;
//        import com.google.android.gms.maps.model.Marker;
//        import com.google.android.gms.maps.model.MarkerOptions;
//
//
//    private GoogleMap mMap;
//    private LatLng home = new LatLng(41.747270, -72.690354);
//    private Button mCall;
//    private Button mCancel;
//    private Marker mStudent;
//    private Button mStartShuttle;
//    private Button mStopShuttle;
//    private TextView mMessage;
//    private int mInterval = 1000; // 1 seconds by default, can be changed later
//    private int mTest = 1000;
//    private Handler mHandler;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_driver_map);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        //startRepeatingTask();
//    }
//
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 16));
//        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
//        mMap.animateCamera(zoom);
//        MarkerOptions mOptions = new MarkerOptions().position(home).title("Where do you want the shuttle?");
//        mStudent = mMap.addMarker(mOptions);
//        mStudent.setDraggable(true);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(home));
//    }
//}
