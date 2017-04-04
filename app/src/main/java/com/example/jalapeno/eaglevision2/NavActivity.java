package com.example.jalapeno.eaglevision2;

import android.Manifest;
import android.app.SearchManager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;


import com.example.jalapeno.eaglevision2.Fragments.MainFragment;
import com.example.jalapeno.eaglevision2.Fragments.WebFragment;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private MainFragment mainMapFragment;
    private WebFragment webFragment;
    private final String[] buildings = {"Alico Arena", "Aquatics Center", "Arts Complex",
            "Bower School of Music", "Campus Support Complex", "Cohen Center", "Edwards Hall",
            "Egan Observatory", "Environmental Health & Safety", "Family Resource Center",
            "Griffin Hall", "Grounds Maintence", "Howard Hall", "Holmes Hall", "Information Booth",
            "Kleist Health Education Center", "Library", "West Library", "East Library", "Lutgert Hall",
            "Marieb Hall", "McTarnaghan Hall", "Merwin Hall", "Music Modular",
            "North Lake Village BathHouse", "North Lake Village Housing", "Outdoor Sports Complex",
            "Reed Hall", "South Modular Village", "South Village Housing", "SoVi Dining", "Sugden Hall",
            "Margaret S. Sugden Welcome Center", "Veterns Pavilion", "Wellness Center",
            "WGCU Broadcast Building", "Whitaker Hall"};

    private final int index = 0;
    /**
     * Id to identify a camera permission request.
     */
    private static final int REQUEST_CAMERA = 0;

    /**
     * Id to identify a contacts permission request.
     */
    private static final int REQUEST_Location = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        mLayout = findViewById(R.id.content_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mainMapFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.content_nav);

        if (mainMapFragment == null) {
            mainMapFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.content_nav, mainMapFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    /**
     * Root of the layout of this Activity.
     */
    private View mLayout;

    /**
     * Requests the Camera permission.
     * If the permission has been denied previously, a SnackBar will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private void requestCameraPermission() {
        Log.i(TAG, "CAMERA permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Log.i(TAG,
                    "Displaying camera permission rationale to provide additional context.");
            Snackbar.make(mLayout, R.string.permission_camera_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(NavActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    REQUEST_CAMERA);
                        }
                    })
                    .show();
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
        // END_INCLUDE(camera_permission_request)
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    public static final String[] Permission_contacts = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    public void locationRequestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {


            Log.i(TAG,
                    "Displaying camera permission rationale to provide additional context.");
            Snackbar.make(mLayout, R.string.permission_location_rationle,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(NavActivity.this,
                                    Permission_contacts,
                                    REQUEST_CAMERA);
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, Permission_contacts, MY_PERMISSIONS_REQUEST_LOCATION);
        }

    }

    /* @Override
     protected void onNewIntent(Intent intent) {
         handleIntent(intent);
     }

     private void handleIntent(Intent intent) {

   MainMapFragment mainMapFragment = new MainMapFragment();
                      Bundle bundle = new Bundle();
                      bundle.putString("Query",query);

                      mainMapFragment.setArguments(bundle);
                      getSupportFragmentManager().beginTransaction().add(R.id.content_nav,mainMapFragment).commit();
         if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
              search = intent.getStringExtra(SearchManager.QUERY);
             Log.v("Donkey","hwllo " +search);
         }
     }*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else { //TODO: Bro make sure that there is no curse words in the code.
            // super.onBackPressed(); don't use this will fuck it up
            //  mainMapFragment = (MainMapFragment) getSupportFragmentManager().findFragmentById(R.id.content_nav);

            //if (mainMapFragment == null){
            mainMapFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.content_nav, mainMapFragment).commit();
            //}
            Log.v("HEYY", "DUMB FUCK ITS NOT WORKING");
        }
    }

    /*
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the options menu from XML
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.nav, menu);


            *//*int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }*//*




        return true;
    }
    PlaceAutocompleteFragment autocompleteFragment;*/
    @Override
  /*  public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {


            try {
                Intent intent =
                        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                .build(this);
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            } catch (GooglePlayServicesRepairableException e) {
                // TODO: Handle the error.
            } catch (GooglePlayServicesNotAvailableException e) {
                // TODO: Handle the error.
            }
           autocompleteFragment = (PlaceAutocompleteFragment)
                    getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.
                    Log.i(TAG, "Place: " + place.getName());
*//*
                String placeDetailsStr = place.getName() + "\n"
                        + place.getId() + "\n"
                        + place.getLatLng().toString() + "\n"
                        + place.getAddress() + "\n"
                        + place.getAttributions();
                txtPlaceDetails.setText(placeDetailsStr);*//*
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    Log.i(TAG, "An error occurred: " + status);
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_canavs) {
//            webFragment = (WebFragment) getSupportFragmentManager().findFragmentById(R.id.content_nav);

            if (webFragment == null) {
                webFragment = WebFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_nav, webFragment).commit();
            }
            Intent web = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fgcu.instructure.com/login/oauth2/auth?client_id=10510000000000132&response_type=code&redirect_uri=https://fgcu.instructure.com/api/v1/courses"));
            startActivity(web);
            // makeServiceCall();

        } else if (id == R.id.nav_AR) {
            Log.i(TAG, "Show camera button pressed. Checking permission.");
            // BEGIN_INCLUDE(camera_permission)
            // Check if the Camera permission is already available.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Camera permission has not been granted.

                requestCameraPermission();
                locationRequestPermissions();

            } else {

                // Camera permissions is already available, show the camera preview.
                Log.i(TAG,
                        "CAMERA permission has already been granted. Displaying camera preview.");
                //Start the AR activity from MARC
                Intent i = new Intent(this, FullscreenActivity.class);
                startActivity(i);
            }


        } else if (id == R.id.nav_tapingo) {
            Intent tap = getPackageManager().getLaunchIntentForPackage("com.tapingo");

            if (tap != null) {
                startActivity(tap);
            } else {
                Intent link = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.tapingo"));

                startActivity(link);
            }
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static final String TAG = NavActivity.class.getSimpleName();

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mainMapFragment.onActivityResult(requestCode, resultCode, data);
    }
}
