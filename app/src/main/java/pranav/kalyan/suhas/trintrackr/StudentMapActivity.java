package pranav.kalyan.suhas.trintrackr;

import android.location.Location;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
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


public class StudentMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double lati = 41.747270;
    private double longi = 72.690354;
    private LatLng home = new LatLng(41.747270, -72.690354 );
    private Button mCall;
    private Button mCancel;
    private Marker mStudent;
    private Marker mShuttle;
    private Button mStartShuttle;
    private Button mStopShuttle;
    private TextView mMessage;
    private int mInterval = 1000; // 1 seconds by default, can be changed later
    private int mTest = 1000;
    private int count = 0;
    private Handler mHandler;
    DriverTracker track = new DriverTracker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.studentMap);
        mapFragment.getMapAsync(this);

        mMessage = (TextView) findViewById(R.id.student_message_board);

        mMessage.setText(R.string.no_shuttle);


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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 16));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        mMap.animateCamera(zoom);
        MarkerOptions mOptions = new MarkerOptions().position(home).title("Where do you want the shuttle?");
        mStudent = mMap.addMarker(mOptions);
        mStudent.setDraggable(true);
        mShuttle = mMap.addMarker(mOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home));
        mMap.setMyLocationEnabled(true);


        mCall = (Button) findViewById(R.id.student_call_shuttle);
        mCall.setEnabled(false);
        mCancel = (Button) findViewById(R.id.student_cancel_shuttle);
        mCancel.setEnabled(false);
        mStartShuttle = (Button) findViewById(R.id.student_start_shuttle);
        mStopShuttle = (Button) findViewById(R.id.student_stop_shuttle);
        mStopShuttle.setEnabled(false);

        mStartShuttle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStudent.setDraggable(true);
                mCancel.setEnabled(false);
                mCall.setEnabled(true);
                mStopShuttle.setEnabled(true);
                mStartShuttle.setEnabled(false);
                mMessage.setText(R.string.yes_shuttle);
            }
        });


        mStopShuttle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStudent.setDraggable(true);
                mCancel.setEnabled(false);
                mCall.setEnabled(false);
                mStartShuttle.setEnabled(true);
                mStopShuttle.setEnabled(false);
                mMessage.setText(R.string.no_shuttle);
            }
        });


        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStudent.setDraggable(false);
                mCancel.setEnabled(true);
                mCall.setEnabled(false);
                mMessage.setText(R.string.shuttle_called);
            }
        });



        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStudent.setDraggable(true);
                mCall.setEnabled(true);
                mCancel.setEnabled(false);
                mMessage.setText(R.string.shuttle_cancel);
            }
        });

        mHandler = new Handler();
        startRepeatingTask();
    }

  Runnable mStatusChecker = new Runnable() {
    @Override
    public void run() {
      lati = lati + 0.0001;
        longi = longi + 0.0001;
        //mStudent.setPosition(new LatLng(lati, longi));
        mShuttle.setPosition(new LatLng(lati, -longi));


        mMessage.setText(String.valueOf(lati));
      mHandler.postDelayed(mStatusChecker, mInterval);
    }
  };

  void startRepeatingTask() {
    mStatusChecker.run();
  }

  void stopRepeatingTask() {
    mHandler.removeCallbacks(mStatusChecker);
  }
}