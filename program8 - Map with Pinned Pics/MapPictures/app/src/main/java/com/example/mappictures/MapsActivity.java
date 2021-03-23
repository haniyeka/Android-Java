package com.example.mappictures;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private Toolbar toolbar;
    private GoogleMap mMap;
    LatLng LARAMIE = new LatLng(41.315723, -105.559586);
    private LatLng currentLocation = LARAMIE;
    String TAG = "MainActivity";
    MarkerOptions markerOptions = new MarkerOptions();
    SupportMapFragment mapFragment;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int REQUEST_CODE_LOCATION_PERMISSION =1;
    public Marker currentLocMarker;
    private FloatingActionButton fab;
    public static final int REQUEST_PERM_ACCESS = 1;
    public static List<Picture> pictures = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        CheckPerm();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Map Pictures");
        toolbar.setTitleTextColor(Color.WHITE);
        mFusedLocationClient =LocationServices.getFusedLocationProviderClient(this);
        if(ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION }, REQUEST_CODE_LOCATION_PERMISSION
            );
        } else {
            getCurrentLocation();
        }
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MapsActivity.this, CameraActivity.class);
                Double Lat = currentLocation.latitude;
                Double Long = currentLocation.longitude;
                myIntent.putExtra("lat", Lat);
                myIntent.putExtra("long", Long);
                MapsActivity.this.startActivity(myIntent);
            }
        });
        getCurrentLocation();
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        getCurrentLocation();
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15f));
        mMap.setIndoorEnabled(true);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //show the pictures
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
            else{
                Toast.makeText(this,"Permission denied!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void getCurrentLocation(){
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            //I'm on not explaining why, just asking for permission.
            Log.v(TAG, "asking for permissions");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MapsActivity.REQUEST_CODE_LOCATION_PERMISSION);
            return;
        }

        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000); //10 seconds
        locationRequest.setFastestInterval(5000);  //5 seconds
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mFusedLocationClient.requestLocationUpdates(locationRequest,
                new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(MapsActivity.this)
                                .requestLocationUpdates(locationRequest, new LocationCallback(),Looper.myLooper());
                        if(locationRequest != null){
                            int latestLocationIndex = locationResult.getLocations().size()-1;
                            double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            currentLocation = new LatLng(latitude,longitude);
                            if(currentLocMarker!=null) currentLocMarker.remove();
                            currentLocMarker = mMap.addMarker(markerOptions.position(currentLocation)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                                    .title("You are here!"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                            if(pictures != null){
                                for (Picture p:pictures) {
                                    Bitmap bitmap = BitmapFactory.decodeFile(p.getFile().getPath());
                                    bitmap = Bitmap.createScaledBitmap(bitmap,200,200,false);
                                    mMap.addMarker(new MarkerOptions().position(p.getPosition())
                                            .title(p.getFile().getPath()).icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                        @Override
                                        public boolean onMarkerClick(Marker marker) {
                                            if(!marker.equals(currentLocMarker)){
                                                Intent myint = new Intent(MapsActivity.this,PreviewActivity.class);
                                                String picPath = marker.getTitle();
                                                myint.putExtra("Path", picPath);
                                                MapsActivity.this.startActivity(myint);
                                                getCurrentLocation();
                                            }
                                            return true;
                                        }
                                    });
                                }
                            }
                            toolbar.setTitle(String.valueOf(currentLocation.latitude)+", "+ String.valueOf(currentLocation.longitude));
                        }
                    }
                }, Looper.myLooper());

    }

    public void CheckPerm() {
        if ((ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            Log.v(TAG, "asking for permissions");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    this.REQUEST_PERM_ACCESS);
        }
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            Log.v(TAG, "asking for permissions");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MapsActivity.REQUEST_CODE_LOCATION_PERMISSION);
        }
    }
}