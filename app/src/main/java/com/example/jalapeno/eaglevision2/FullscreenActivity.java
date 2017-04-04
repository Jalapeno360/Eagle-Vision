package com.example.jalapeno.eaglevision2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.SensorManager;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;


import com.wikitude.architect.ArchitectView;
import com.wikitude.common.camera.CameraSettings;

import java.io.File;
import java.io.FileOutputStream;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AbstractArchitectCamActivity {

    private ArchitectView architectView;
    private long lastCalibrationToastShownTimeMillis = System.currentTimeMillis();
    private static final int WIKITUDE_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 3;
    protected Bitmap screenCapture = null;
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
//        this.architectView = (ArchitectView)this.findViewById( R.id.architectView );
//        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
//        config.setLicenseKey( "E1krMhAHOJww+jhRpE88zkYFxN/P9Jh+1zmKNqm2Xedo8T6s1SVtJBs4Q+ZhxfBEHzmCmFib1FDWFGzB2rdVyfhRAiloJlxl3mIUUO7/QeKN1tySqgNDBRsK5V/WqzkRTeeylZKs0/SMwMfeRyw9PHZUh8ZjN8kLDvFYWEYNDgRTYWx0ZWRfX7T3pErJIwmijAs0BDX1270GMSrzN8ER/j6P2bHbed1C6DQnJNh3duNbs989Oocy8DS8iq+kDUJj1alrRlb/AGTYnzh6Yrr3mxRTY5vj18hoKLMBaGhJnt1BdMv/TSEREM7QhhvBLcdZ3cOaQAZAV8v0F4gQQUtoWF/aPfI17qoWe3aNPK8xe2/v+2j6Lc1dVqSYVNT4oWzJXTyc8VxQ1jlCvgg9AG1F+UO/4J1KAsJgFK769SL93UUM4bLnYkeshN8dv55pPLiAa6mEEbtin2E8jf9vbKVd7f4Dda1rwLGzMln0W195Fh7cc8f/SHD55nrVdRT0HzfYvhObO9jD1ce0rK72iI/vD/yI7OwTzKnoLxZgklQD7C3lDIeWWb5ZML1KXh3nYAjk2/R1BzfhIkDN0AikziGfJQgNnPDOiDlu6PQQCGxdzE4raUct6lP9pnOuxcVyg4EfFFO8n6AykG7FM7som1oVJYIlnuI3WzHQNOzkIEREYQw=" );
//        this.architectView.onCreate( config );





    }*/

    @Override
    protected CameraSettings.CameraPosition getCameraPosition() {
        return CameraSettings.CameraPosition.DEFAULT;
    }

    @Override
    protected boolean hasGeo() {
        return true;
    }

    @Override
    protected boolean hasIR() {
        return false;
    }

    @Override
    protected boolean hasInstant() {
        return false;
    }

    @Override
    public ILocationProvider getLocationProvider(final LocationListener locationListener) {
        return new LocationProvider(this, locationListener);
    }

    @Override
    public ArchitectView.SensorAccuracyChangeListener getSensorAccuracyListener() {
        return new ArchitectView.SensorAccuracyChangeListener() {
            @Override
            public void onCompassAccuracyChanged( int accuracy ) {
				/* UNRELIABLE = 0, LOW = 1, MEDIUM = 2, HIGH = 3 */
                if ( accuracy < SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM && FullscreenActivity.this != null && !FullscreenActivity.this.isFinishing() && System.currentTimeMillis() - FullscreenActivity.this.lastCalibrationToastShownTimeMillis > 5 * 1000) {
                    Toast.makeText( FullscreenActivity.this, R.string.compass_accuracy_low, Toast.LENGTH_LONG ).show();
                    FullscreenActivity.this.lastCalibrationToastShownTimeMillis = System.currentTimeMillis();
                }
            }
        };
    }

    @Override
    public float getInitialCullingDistanceMeters() {
        return CULLING_DISTANCE_DEFAULT_METERS;
    }

    @Override
    public ArchitectView.ArchitectWorldLoadedListener getWorldLoadedListener() {
        return null;
    }


  /*  @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        architectView.onPostCreate();
//
//        try {
//            this.architectView.load("file:///android_asset/06_PointOfInterest_3_MultiplePois/index.html");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        architectView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        architectView.onDestroy();
    }
*/

    @Override
    public String getARchitectWorldPath() {
        return null;
    }

    @Override
    public ArchitectView.ArchitectUrlListener getUrlListener() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_fullscreen;
    }


    @Override
    public String getWikitudeSDKLicenseKey() {
      // return "DSL1nGDeEq4jFlkW1riw7Ox+TcDdKICrN6e4kyglRvib2M5DHNTHj2ZQuL2a4HhRrJ7t7J+aoeEzTW+JfOhINnXbA0rF9S/7vC3sYd5eYJ1csdUzYL4KOnTBGb3WRUn4SkVWP38S1/S0F7FQVZf9p9BS+tT+zBsTKtjtBHqkqs5TYWx0ZWRfX0zBTbiXTAOyUfA6KDGwqMISvP325v+pKfZE+QfJEzF189ZIuR77GKIL2Wr83XzcTB0cMRxYqhNvQfV0Ozi4wVoWkjISDcfg3GjOM8wAHEbKn5yyRtCMMXmMlZWKjWHFvqJxS5/3Wf05tD2VIiZGziUZ2IgyszWq+3r9rQJ4NpselWGCJgzp8zMkrPUGHTby7peNYHxxjdmXTcdmY/zZf6sU3bitkJUm71jTDbFtvju5SGJzwYWyQSNG4s7prCVL9mO+43VAe8QIho+xsviv54/PerruJ+D4JXlUl6fkMFnGECFko/YBM9poguDSlpnZGlw/rqyo2X+3qBBw6zitUGRLf+7rzBKQUGFYknPon8FbUguaEN6lmFuyGSbsL+4iB/wsdQTpaIzTKnXHrSKLHy9Kksylm8ggbXd5ve9wHwIBkkNqopr4Y2xuIoapsqPypnQ7YsZe3r/ZQhdjTpcNZL+H0QkRmwpuITroSk2+oO7aUsEG44s9viPZR3s+3SmcCnzHBtyhKyx58Y2+OPmGyfFOQcE1+WxMUM1VqLSZsNl0DD7MgKSQ+aU=\n";
        return "E1krMhAHOJww+jhRpE88zkYFxN/P9Jh+1zmKNqm2Xedo8T6s1SVtJBs4Q+ZhxfBEHzmCmFib1FDWFGzB2rdVyfhRAiloJlxl3mIUUO7/QeKN1tySqgNDBRsK5V/WqzkRTeeylZKs0/SMwMfeRyw9PHZUh8ZjN8kLDvFYWEYNDgRTYWx0ZWRfX7T3pErJIwmijAs0BDX1270GMSrzN8ER/j6P2bHbed1C6DQnJNh3duNbs989Oocy8DS8iq+kDUJj1alrRlb/AGTYnzh6Yrr3mxRTY5vj18hoKLMBaGhJnt1BdMv/TSEREM7QhhvBLcdZ3cOaQAZAV8v0F4gQQUtoWF/aPfI17qoWe3aNPK8xe2/v+2j6Lc1dVqSYVNT4oWzJXTyc8VxQ1jlCvgg9AG1F+UO/4J1KAsJgFK769SL93UUM4bLnYkeshN8dv55pPLiAa6mEEbtin2E8jf9vbKVd7f4Dda1rwLGzMln0W195Fh7cc8f/SHD55nrVdRT0HzfYvhObO9jD1ce0rK72iI/vD/yI7OwTzKnoLxZgklQD7C3lDIeWWb5ZML1KXh3nYAjk2/R1BzfhIkDN0AikziGfJQgNnPDOiDlu6PQQCGxdzE4raUct6lP9pnOuxcVyg4EfFFO8n6AykG7FM7som1oVJYIlnuI3WzHQNOzkIEREYQw=";
    //return "RXZjZD1NqqovELEtLkhqf9Gmv9GX+JATINUdDQMQyioXbEtpR6yoO8y2ET1UO9kKyhFGENiK04OhlGbwsVjlytBcBKPR+WqjEBCwcjYhmr5qHZEn9q0uUUn8SlaLQLd8M938yo/HhcrQSN60UwlXtXw1w0OxAH/kFphMO7UqCE9TYWx0ZWRfXxPuHj22gDMU7w5hx5+5XPL5wSFyzj2B4wWZPR0EPF+O/+9bT5LmXX3Vxp0zHNlNtzr11SACtcW499QHXnwa6rs22t1Yn/1W394v2hseZSARlKqU30wHfu+VbmQz1rMc2Dhv+blIqD1GdvNE521a0TZZWJbBnVS42TGX2JrIpVCFYEZSaLFUJiUPNN2q5TUgXE8YvqpRAKH2yyem6J5rOxMh3jqCymg6Cx825cTaTqicp3HLHPcptlgu4FUKErR8uIshYcb6fxtns2JeM5VGKnoYuSwwZ/u1FRXSjUAuX0qaEIoAVFew/UauSleQIvc2AqZ4onxSuNjNbM+fzgLPq+D64QST9UVM8+YrqczVK0Z6ZAXrs6+tCZBjm7a5WYdPqnLciOH4L4ETGVEWvjF6kkpy9KCFs0ifOwuh0Nruaafur/dly/0i0/cW/b0Tou5d9TojUWXVNCDXJFO09qoahkLFbKGvBWuvQyURugFMFoQ3SWFx3gmHuLdQMyGm8ANoY1k1klfj+x2CZGEl4k3zxyvAIELZwG1Jsq8bBXrO+rm91aHWG2Aid0aHFTaqvAGB3ZtRi6ZsNlzY6CqPLo1Z/0eIckZ/E5GsoQ==";
    }

    @Override
    public int getArchitectViewId() {
        return R.id.architectView;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case WIKITUDE_PERMISSIONS_REQUEST_EXTERNAL_STORAGE: {
                if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    this.saveScreenCaptureToExternalStorage(FullscreenActivity.this.screenCapture);
                } else {
                    Toast.makeText(this, "Please allow access to external storage, otherwise the screen capture can not be saved.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    protected void saveScreenCaptureToExternalStorage(Bitmap screenCapture) {
        if ( screenCapture != null ) {
            // store screenCapture into external cache directory
            final File screenCaptureFile = new File(Environment.getExternalStorageDirectory().toString(), "screenCapture_" + System.currentTimeMillis() + ".jpg");

            // 1. Save bitmap to file & compress to jpeg. You may use PNG too
            try {

                final FileOutputStream out = new FileOutputStream(screenCaptureFile);
                screenCapture.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();

                // 2. create send intent
                final Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpg");
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(screenCaptureFile));

                // 3. launch intent-chooser
                final String chooserTitle = "Share Snaphot";
                FullscreenActivity.this.startActivity(Intent.createChooser(share, chooserTitle));

            } catch (final Exception e) {
                // should not occur when all permissions are set
                FullscreenActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // show toast message in case something went wrong
                        Toast.makeText(FullscreenActivity.this, "Unexpected error, " + e, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

   /* @Override
    protected void onPause() {
        super.onPause();
        architectView.onPause();
    }*/

}
