package com.example.jalapeno.eaglevision2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;


import com.wikitude.architect.ArchitectView;




public class MainActivity extends AppCompatActivity {

    private ArchitectView architectView;

    private static final int WIKITUDE_PERMISSIONS_REQUEST = 1;

    private String ARkey() {
        String key = "DSL1nGDeEq4jFlkW1riw7Ox+TcDdKICrN6e4kyglRvib2M5DHNTHj2ZQuL2a4HhRrJ7t7J+aoeEzTW+JfOhINnXbA0rF9S/7vC3sYd5eYJ1csdUzYL4KOnTBGb3WRUn4SkVWP38S1/S0F7FQVZf9p9BS+tT+zBsTKtjtBHqkqs5TYWx0ZWRfX0zBTbiXTAOyUfA6KDGwqMISvP325v+pKfZE+QfJEzF189ZIuR77GKIL2Wr83XzcTB0cMRxYqhNvQfV0Ozi4wVoWkjISDcfg3GjOM8wAHEbKn5yyRtCMMXmMlZWKjWHFvqJxS5/3Wf05tD2VIiZGziUZ2IgyszWq+3r9rQJ4NpselWGCJgzp8zMkrPUGHTby7peNYHxxjdmXTcdmY/zZf6sU3bitkJUm71jTDbFtvju5SGJzwYWyQSNG4s7prCVL9mO+43VAe8QIho+xsviv54/PerruJ+D4JXlUl6fkMFnGECFko/YBM9poguDSlpnZGlw/rqyo2X+3qBBw6zitUGRLf+7rzBKQUGFYknPon8FbUguaEN6lmFuyGSbsL+4iB/wsdQTpaIzTKnXHrSKLHy9Kksylm8ggbXd5ve9wHwIBkkNqopr4Y2xuIoapsqPypnQ7YsZe3r/ZQhdjTpcNZL+H0QkRmwpuITroSk2+oO7aUsEG44s9viPZR3s+3SmcCnzHBtyhKyx58Y2+OPmGyfFOQcE1+WxMUM1VqLSZsNl0DD7MgKSQ+aU=\n";
        return key;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        //Checking to see if permissions are needed
        if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CAMERA },
                    WIKITUDE_PERMISSIONS_REQUEST);

        }

        this.architectView = (ArchitectView)this.findViewById( R.id.architectView );
      // final StartupConfiguration config = new StartupConfiguration(ARkey());


      //  this.architectView.onCreate( config );
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        architectView.onPostCreate();

        try{
            this.architectView.load("file:///android_asset/SinglePoi/index.html");

        } catch(Exception e) {

            e.printStackTrace();

        }

    }

    @Override
    protected void onResume(){

        super.onResume();

        architectView.onResume();
    }

    @Override
    protected void onDestroy(){

        super.onDestroy();

        architectView.onDestroy();

    }

    @Override

    protected void onPause(){

        super.onPause();

        architectView.onPause();

    }

}
