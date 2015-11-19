package pranav.kalyan.suhas.trintrackr;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    private LatLng home = new LatLng(41.747270, -72.690354);
    private Button mCallShuttleButton;
    private Button mCancel;
    private Marker mStudent;

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


        mCallShuttleButton = (Button) findViewById(R.id.call_shuttle);
        mCallShuttleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStudent.setDraggable(false);
                mCancel.setEnabled(true);
                mCallShuttleButton.setEnabled(false);
            }
        });


        mCancel = (Button) findViewById(R.id.cancel_shuttle);
        mCancel.setEnabled(false);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStudent.setDraggable(true);
                mCallShuttleButton.setEnabled(true);
                mCancel.setEnabled(false);
            }
        });
    }
}
