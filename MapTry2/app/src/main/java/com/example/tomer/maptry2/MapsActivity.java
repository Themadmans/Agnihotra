package com.example.tomer.maptry2;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerDragListener,
        View.OnClickListener
        {

    private GoogleMap mMap;
            private GoogleApiClient googleApiClient;
            private double longs,lats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        Button btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(this);
    }
            private void getCurrentLocation() {
                //Creating a location object

                Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                if (location != null) {
                    //Getting longitude and latitude
                    longs = location.getLongitude();
                    lats = location.getLatitude();

                    //moving the map to location
                    moveMap();
                }
            }

            private void moveMap() {
                //String to display current latitude and longitude
                String msg = lats + ", "+longs;
Log.d("Raju","inside movemap");
                //Creating a LatLng Object to store Coordinates
                LatLng latLng = new LatLng(lats, longs);
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addressList;
                 try {
                    addressList = geocoder.getFromLocation(lats,longs,1);
                     Log.d("Raju","inside movemap2");
                     if(addressList!=null) {
                         Log.d("Raju","inside movemap3");
                      String street = addressList.get(0).getAdminArea();
                      String city = addressList.get(0).getLocality();
                         String count = addressList.get(0).getCountryName();
                         msg = msg  + " " + city +  " " + count + " " + street;
                         Log.d("RAju", "ADress not null");
                     }
                     else{
                         Log.d("Raju","adress is null");
                     }


                }
                catch (Exception exception)
                {
Log.d("raju","caught");
                }

                //Adding marker to map
                mMap.addMarker(new MarkerOptions()
                        .position(latLng) //setting position
                        .draggable(true) //Making the marker draggable
                        .title("Current Location")); //Adding a title

                //Moving the camera
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                //Animating the camera
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                //Displaying current coordinates in toast
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(32, 75);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMarkerDragListener(this);
        //Adding a long click listener to the map
        mMap.setOnMapLongClickListener(this);
    }

            @Override
            public void onClick(View v) {
getCurrentLocation();
            }
            @Override
            protected void onStart() {
                googleApiClient.connect();
                super.onStart();
            }

            @Override
            protected void onStop() {
                googleApiClient.disconnect();
                super.onStop();
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

            @Override
            public void onMapLongClick(LatLng latLng) {

            }

            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }
        }
