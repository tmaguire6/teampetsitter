package com.teampet.petsitter;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Random;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener{

    LocationManager  locManager;
    LocationListener locListener;
    private GoogleMap mMap;
    private static final LatLng BENTLEY = new LatLng(42.3889167, -71.2208033);
    private static final float zoom = 14.0f;
    private ProgressDialog dialog;
    NotificationCompat.Builder notification;
    private static final int uniqueNotificationID = 12345;
    private ImageView ImView;
    Button buttonSend;
    private ArrayList<Petsitter> foundSitters;
    private Petowner owner = new Petowner("12345", "James", "Pepe", "jpepe@bentley.edu", "(555) 555-5555", "Some Background Nonsense", new String[]{""});

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // handle the menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dialog = ProgressDialog.show(this, "Locating you...",
                "Working....", true);





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the profile action
            Intent profile = new Intent(MapsActivity.this, Profile.class);
            // todo: fix this to actually grab correct sitter
            profile.putExtra("type", SQLHelper.TableType.Owner);
            profile.putExtra("owner", owner);
            startActivity(profile);

        } else if (id == R.id.nav_nearbySitters) {
            // Handle the nearby sitters action
            Intent nearbySitters = new Intent(MapsActivity.this, NearbySitters.class);
            nearbySitters.putExtra("sitters", foundSitters);
            nearbySitters.putExtra("type", SQLHelper.TableType.Sitter);
            startActivity(nearbySitters);

        }else if (id == R.id.nav_history) {
            // Handle the history action
            Intent history = new Intent(MapsActivity.this, History.class);
            startActivity(history);

        } else if (id == R.id.nav_payment) {
            // Handle the payment action
            Intent payment = new Intent(MapsActivity.this, Payment.class);
            startActivity(payment);

        } else if (id == R.id.nav_review) {
            // Handle the review action
            Intent review = new Intent(MapsActivity.this, Review.class);
            startActivity(review);

        } else if (id == R.id.nav_settings) {
            // Handle the settings action
            Intent settings = new Intent(MapsActivity.this, Settings.class);
            startActivity(settings);

        } else if (id == R.id.nav_terms) {
            // Handle the terms & conditions action
            Intent terms = new Intent(MapsActivity.this, TermsConditions.class);
            startActivity(terms);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker at Bentley University.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;




        //choose map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        //set listener on marker click
        mMap.setOnMarkerClickListener(
                new GoogleMap.OnMarkerClickListener() {

                    public boolean onMarkerClick(Marker m) {
                        String title = m.getTitle();
                        String snip = m.getSnippet();
                        Toast.makeText(getApplicationContext(), title + "\n" + snip, Toast.LENGTH_LONG).show();
                        // Handle the profile action
                        Intent profile = new Intent(MapsActivity.this, Profile.class);
                        // todo: fix this to actually grab correct sitter
                        profile.putExtra("sitter", foundSitters.get(0));
                        profile.putExtra("type", SQLHelper.TableType.Sitter);
                        startActivity(profile);

                        return true;
                    }
                }
        );


        //enable map tracking of current location
        try {
            mMap.setMyLocationEnabled(true);


            // Use the LocationManager class to obtain GPS locations
            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locListener = new MyLocationListener();

            //Register for location updates using the named provider, and a pending intent.
            //10 second minimum interval between updates, 0 meters minimum distance between updates
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locListener);



        } catch(SecurityException e) {
            Toast.makeText(this, "Security Exception - setup", Toast.LENGTH_LONG).show();
        }
    }

    //inner class used to create object that will receive Location update callbacks
    public class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {

            double latitude = loc.getLatitude();
            double longitude = loc.getLongitude();

            LatLng position = new LatLng(latitude, longitude);

            mMap.clear();
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, zoom));

            // get nearby pet sitters lat/long coordinates and place markers
            findAndPlaceSitters(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Disabled",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(),
                    "Gps Enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { }

    }// End MyLocationListener

    private void findAndPlaceSitters(Location loc) {
        // Obviously in real life, these would have fixed locations. To make this easier to use with our fake data, assign them
        // locations that place them roughly around the current location instead.
        SQLHelper helper = new SQLHelper();
        ArrayList<Object> sitters = helper.getDatabaseValues("select * from petsitter;", SQLHelper.TableType.Sitter);
        ArrayList<Location> nearbyLocations = new ArrayList<>();

        // get a random number between 2 and 12, this will be divided by 1000 to get a latitude, then we'll get another one for longitude, then a third to determine if it will be plus or minus

        Random r = new Random();
        int low = 2;
        int high = 12;
        int result = r.nextInt(high - low) + low;

        for(int i=0; i<5; i++){
            double latitudeDiff = ((double)(r.nextInt(high-low) + low)) / 1000;
            double longitudeDiff = ((double)(r.nextInt(high-low) + low)) / 1000;
            boolean minus = false;
            Location locToAdd = new Location(loc);
            // if even, minus, else plus
            if(((double)(r.nextInt(high-low) + low)) % 2 == 0){
                minus = true;
            }
            if(minus){
                locToAdd.setLatitude(locToAdd.getLatitude() - latitudeDiff);
                locToAdd.setLongitude(locToAdd.getLongitude() - longitudeDiff);
            }
            else{
                locToAdd.setLatitude(locToAdd.getLatitude() + latitudeDiff);
                locToAdd.setLongitude(locToAdd.getLongitude() + longitudeDiff);
            }
            nearbyLocations.add(locToAdd);
        }
        // now that we've gathered our random locations
        // loop through them, grab a random pet sitter from our list, and use it to create a marker
        low = 0;
        high = sitters.size();
        ArrayList<Integer> usedPositions = new ArrayList<>();
        foundSitters = new ArrayList<>();

        for(Location x : nearbyLocations ){
            // Add a marker at recent location
            int randomPosition = r.nextInt(high-low) + low;
            while(usedPositions.contains(randomPosition)){
                randomPosition = r.nextInt(high-low) + low;
            }
            usedPositions.add(randomPosition);
            // add sitter to list for use in other activities
            Petsitter sitter = (Petsitter)sitters.get(randomPosition);

            foundSitters.add(sitter);
            mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(x.getLatitude(), x.getLongitude()))
                            .title(sitter.getFname() + " " + sitter.getLname())
                            .snippet(sitter.getEmail())

            );
        }
        dialog.dismiss();

        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        //Build a notification that will tell them how many sitters we found
        notification.setSmallIcon(R.drawable.notificationpic);
        notification.setTicker("We found some Pet Sitters near you!");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Sitters Found!");
        notification.setContentText("Found " + sitters.size() + " Pet Sitters near you!");
        //Creates an explicit intent for an Activity in app
        Intent intent = new Intent(this, MapsActivity.class);
        //stack builder object will contain an artificial back stack for the started activity
        //Ensures that navigating backward from Activity leads out of app to Home screen
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        //Adds the back stack for the Intent (but not Intent itself)
        stackBuilder.addParentStack(MapsActivity.class);
        //Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        //Builds notification and sends out notification
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(uniqueNotificationID, notification.build());

        try {
            locManager.removeUpdates(locListener);
        }
        catch(SecurityException e){
            Log.e("LocationException", e.getMessage());
        }


    }
    //testing 2  - Quang Nguyen
    //stop updates
    public void onStop() {
        super.onStop();
        try {
            locManager.removeUpdates(locListener);
        } catch(SecurityException e) {Toast.makeText(this,
                "Security Exception - stop", Toast.LENGTH_LONG).show();}
    }
}
