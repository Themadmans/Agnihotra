package com.example.tomer.agnihotra;

import android.content.IntentSender;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {



    private FusedLocationProviderClient mFusedLocationClient;
    Location loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        final String querystring = "lat_deg=23&lon_deg=78&timeZoneId=Asia%2FKolkata&date=07%2F23%2F2017&end_date=07%2F24%2F2018";


        try {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Log.d("RAJU", "ÏNSIDE LOCATION SUCESS" + location.getLatitude());
                                loc=location;

                            }
                            else {
                                 Log.d("RAJU", "ÏNSIDE LOCATION FAILLED " + location);
                            }
                        }
                    });
            mFusedLocationClient.getLastLocation().addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    int statusCode = ((ApiException) e).getStatusCode();
                    switch (statusCode) {
                        case CommonStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied, but this can be fixed
                            // by showing the user a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                ResolvableApiException resolvable = (ResolvableApiException) e;
                                resolvable.startResolutionForResult(MainActivity.this,400);
                            } catch (IntentSender.SendIntentException sendEx) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way
                            // to fix the settings so we won't show the dialog.
                            break;
                }
            }});
        }
        catch (SecurityException e ) {
            Log.d("RAJU", "ISSUE WITH LOCATION");
        }
        Button btn= (Button) findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText1 = (EditText) findViewById(R.id.editTextlati);
                EditText editText2 = (EditText) findViewById(R.id.editTextLongi);
                EditText editText3 = (EditText) findViewById(R.id.editTextdate);
                String lat,longi,date1;
                lat = editText1.getText().toString();
                longi = editText2.getText().toString();
                date1 = editText3.getText().toString();
                String querystring1 = "lat_deg=" +  lat + "&lon_deg=" + longi + "&timeZoneId=Asia%2FKolkata&date=" +  date1 + "&end_date=08%2F24%2F2017";
                new Asynctaskclass().execute(querystring1);
            }
        });
        Button btn2= (Button) findViewById(R.id.button2);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), " is the latitude " + " Login is = " +loc.getLatitude(),Toast.LENGTH_LONG).show();

            }
        });

    }
}
