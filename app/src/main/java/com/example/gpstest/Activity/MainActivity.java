package com.example.gpstest.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gpstest.Adapter.CustomAdapter;
import com.example.gpstest.Model.Satellite;
import com.example.gpstest.R;

import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity implements LocationListener, GpsStatus.Listener {

    private DrawerLayout mDrawerLayout;
    TextView tvLat, tvLong, tvInView;
    LocationManager locationManager = null;
    ListView lv;
    ArrayList<Satellite> arrSl = new ArrayList<>();

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLat = findViewById(R.id.tvLat);
        tvLong = findViewById(R.id.tvLong);
        tvInView = findViewById(R.id.tvInView);
        lv =findViewById(R.id.lv);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        /*switch (menuItem.getItemId()){
                            case R.id.nav_status:
                                //Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.nav_sky:
                                Intent intent = new Intent(getBaseContext(), SkyPlot.class);
                                startActivity(intent);
                                return true;
                        }*/

                        return true;
                    }
                });

        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );
        
        Toolbar toolbar = findViewById(R.id.tb);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

        locationManager.addGpsStatusListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGpsStatusChanged(int event) {

        //Retrieves information about the current status of the GPS engine
        @SuppressLint("MissingPermission") GpsStatus gpsStatus = locationManager.getGpsStatus(null);

        if(gpsStatus != null) {
            //Returns an array of GpsSatellite objects, which represent the current state of the GPS engine.
            Iterable<GpsSatellite>satellites = gpsStatus.getSatellites();
            Iterator<GpsSatellite> sat = satellites.iterator();
            int i=0;
            arrSl.clear();

            while (sat.hasNext()) {
                i++;
                GpsSatellite satellite = sat.next();
                Satellite satellite1 = new Satellite();
                satellite1.setPrn(satellite.getPrn());
                satellite1.setAzimuth(satellite.getAzimuth());
                satellite1.setElevation(satellite.getElevation());
                satellite1.setSnr(satellite.getSnr());
                arrSl.add(satellite1);
            }

            CustomAdapter arrayAdapter = new CustomAdapter(this, R.layout.item_sl, arrSl);
            lv.setAdapter(arrayAdapter);
            tvInView.setText(String.valueOf(i));


        }

    }

    @Override
    public void onLocationChanged(Location location) {
        tvLat.setText(String.valueOf(location.getLatitude()));
        tvLong.setText(String.valueOf(location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
