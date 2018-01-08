package com.example.ss.contact_project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapContact extends FragmentActivity implements View.OnClickListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    Intent camedintent ;
    LatLng lng;
    Button locate , cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_contact);
        setUpMapIfNeeded();

        locate = (Button)findViewById(R.id.Locate_btn);
        cancel = (Button)findViewById(R.id.Cancel_locate_btn);
cancel.setOnClickListener(this);
        camedintent =getIntent();

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));

                lng = latLng;


            }
        });
        locate.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {




    }

    @Override
    public void onClick(View v) {
     if (v==locate)
     {

         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setMessage("Are you sure That you Want to save That Location");
         builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {

                 double latitude = lng.latitude;
                 double longitude = lng.longitude;

                 Intent Gotoadd = new Intent(getBaseContext(), Add_Activity.class);

                 Gotoadd.putExtra("Name", camedintent.getStringExtra("Name"));
                 Gotoadd.putExtra("Phone", camedintent.getStringExtra("Phone"));
                 Gotoadd.putExtra("Email", camedintent.getStringExtra("Email"));
                 Gotoadd.putExtra("lat", latitude);
                 Gotoadd.putExtra("lng", longitude);


                 startActivity(Gotoadd);


             }
         });
         builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {

             }
         });
         AlertDialog alertDialog = builder.create();
         alertDialog.show();



     }
        else if (v==cancel)
     {
         Intent Gotoadd = new Intent(this,Add_Activity.class);

         Gotoadd.putExtra("Name",camedintent.getStringExtra("Name"));
         Gotoadd.putExtra("Phone",camedintent.getStringExtra("Phone"));
         Gotoadd.putExtra("Email",camedintent.getStringExtra("Email"));



         startActivity(Gotoadd);
     }
    }
}
