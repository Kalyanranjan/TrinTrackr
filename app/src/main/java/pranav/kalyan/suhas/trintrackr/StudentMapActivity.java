package pranav.kalyan.suhas.trintrackr;

import android.location.Location;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Gravity;
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

public class StudentMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private double lati = 41.747270;
    private double longi = 72.690354;
    private LatLng home = new LatLng(41.747270, -72.690354 );
    private Button mCall;
    private Button mCancel;
    private Marker mStudent;
    private Marker mShuttle;
    private int mShuttleStarted = 0;
    private int mInterval = 1000; // 1 seconds by default, can be changed later
    private Handler mHandler;
    private double final_lat = 0.0;
    private double final_lon = 0.0;
    private LatLng final_pos;



    private String mSudentName;
    DriverTracker track = new DriverTracker();

    /* Drivers on the road */
    private int mNumDriver = 0;
    private boolean[] mMarkerTracker = {false, false, false, false, false};
    private Marker[] mVehicles = new Marker[5];
    private String[] mDrivers = new String[15];


    public String toString(){
        String string = "|";
        for (int i=1; i<=mNumDriver; i++){
            string+=this.mDrivers[3*i-3]+" | "+this.mDrivers[3*i-2]+" | "+this.mDrivers[3*i-1]+" | ";
        }
        return string;
   };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.studentMap);
        mapFragment.getMapAsync(this);
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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home));
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerDragListener(this);


        mCall = (Button) findViewById(R.id.student_call_shuttle);
        mCall.setEnabled(false);
        mCancel = (Button) findViewById(R.id.student_cancel_shuttle);
        mCancel.setEnabled(false);

        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStudent.setDraggable(false);
                mCancel.setEnabled(true);
                mCall.setEnabled(false);

                String x = Double.toString(final_lat);
                String y = Double.toString(final_lon);

                Toast.makeText(StudentMapActivity.this, "Calling Shuttle", Toast.LENGTH_SHORT).show();
                new StudentRequestActivity(StudentMapActivity.this).execute("pranav", "1", x, y);

            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStudent.setDraggable(true);
                mCall.setEnabled(true);
                mCancel.setEnabled(false);

                Toast.makeText(StudentMapActivity.this, "Cancelling or Boarding Shuttle", Toast.LENGTH_SHORT).show();
                StudentRequestActivity sra = (StudentRequestActivity)new StudentRequestActivity(StudentMapActivity.this).execute("pranav", "0", "0", "0");

            }
        });

        mHandler = new Handler();
        startRepeatingTask();
    }

  Runnable mStatusChecker = new Runnable() {
    @Override
    public void run() {

        final GetDrLocActivity getDriver = new GetDrLocActivity(StudentMapActivity.this);
        getDriver.execute();
        Handler mHandler2 = new Handler();
        mHandler2.postDelayed(new Runnable() {
            public void run() {
                mNumDriver = getDriver.getDrNum();
                if (mNumDriver > 0) {
                    if (mShuttleStarted == 0) {
                        mShuttleStarted = 1;
                        mCall.setEnabled(true);
                    }
                } else {
                    mShuttleStarted = 0;
                    mCall.setEnabled(false);
                    mCancel.setEnabled(false);
                }
                mDrivers = getDriver.getDrivers();
                MarkerOptions mOptions = new MarkerOptions().position(home).title("Shuttle")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));;
                for (int i=0; i<5; i++) {
                    if (mMarkerTracker[i]) {
                        mVehicles[i].remove();
                        mMarkerTracker[i] = false;
                    }
                }

                for (int i=0; i<5; i++) {
                    if ( i < mNumDriver){
                        mVehicles[i] = mMap.addMarker(mOptions);
                        mMarkerTracker[i] = true;
                        lati = Double.parseDouble(mDrivers[3 * i + 1]);
                        longi = Double.parseDouble(mDrivers[3*i+2]);
                        mVehicles[i].setPosition(new LatLng(lati, longi));
                    }
                }
            }
        }, 500);
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
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        final_pos = marker.getPosition();
        final_lat = final_pos.latitude;
        final_lon = final_pos.longitude;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
    }
}