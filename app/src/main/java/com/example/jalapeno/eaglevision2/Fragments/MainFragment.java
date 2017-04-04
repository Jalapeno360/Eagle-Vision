package com.example.jalapeno.eaglevision2.Fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;


import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.jalapeno.eaglevision2.Modules.DirectionFinderListener;
import com.example.jalapeno.eaglevision2.Modules.DirectionsFinder;
import com.example.jalapeno.eaglevision2.Modules.Route;
import com.example.jalapeno.eaglevision2.NavActivity;
import com.example.jalapeno.eaglevision2.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class MainFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener , DirectionFinderListener {
    private GoogleMap mMap;
    protected MarkerOptions option;
    private TextView buildingText;
    private TextView buildingName;
    private ImageView buildingPic;
    private CardView card;
    protected Polygon seidlerPolygon;
    protected Polygon holmesPolygon;
    protected Polygon lutgertPolygon;
    protected Polygon mariebPolygon;
    protected Polygon whitakerPolygon;
    protected Polygon edwardsPolygon;
    protected Polygon griffinPolygon;
    protected Polygon howardPolygon;
    protected Polygon mcTarnaghanPolygon;
    protected Polygon merwinPolygon;
    protected Polygon reedPolygon;
    protected Polygon sugdenPolygon;
    private static final int GOOGLE_APL_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String LOG_TAG = "NavActivity";




    private static Place place;
    /* private String seidlerId;
     private String holmesId;
     private String lutgurtId;
     private String mariebId;
     private String whitakerId;
     private String edwardsId;
     private String griffinId;
     private String howardId;
     private String mcTarnaghanId;
     private String merwinId;
     private String reedId;
     private String sugdenId;
 */
    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();

        return fragment;
    }

    /*public static MainFragment newInstance(@NonNull final boolean trueLocation , Place placesearched) {
        final MainFragment fragment = new MainFragment();
        final Bundle b = new Bundle();
        b.putBoolean("place", trueLocation);
       place = placesearched;
        Log.v("HEEEELLLELE","LOOK a new fragment   "+trueLocation);

        fragment.setArguments(b);
        //placeBool = trueLocation;
        return fragment;
    }*/

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                  //  callPLaceDetectionApi();
        }break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
/*
    private void callPLaceDetectionApi(){
        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient,null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(@NonNull PlaceLikelihoodBuffer likelyPlaces) {
                for(PlaceLikelihood placeLikelihood : likelyPlaces){
                    place = placeLikelihood.getPlace();
                }
                likelyPlaces.release();

            }
        });
    }*/
    private LatLngBounds latLngBounds = new LatLngBounds(new LatLng(26.4493619,-817967481),new LatLng(26.4724854,-81.7601301));
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.search) {


        try {

            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).setBoundsBias(latLngBounds)
                            .build(this.getActivity());


            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
     //   autocompleteFragment = (SupportPlaceAutocompleteFragment)
       //         getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);


   //  autocompleteFragment.setBoundsBias(latLngBounds);

        return true;
    }

    return super.onOptionsItemSelected(item);}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this.getActivity(), data);
                Log.i(TAG, "Place: " + place.getName());
                try {
                    new DirectionsFinder(this,"Seidler Hall" , place.getName().toString()).execute();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //MainFragment.newInstance(true);
                //getChildFragmentManager().beginTransaction().add(R.id.map,MainFragment.newInstance(true ,place)).commit();
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 20));
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this.getActivity(), data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                Log.v("HEYEEYE","NOthing searched");
            }
        }
    }
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this.getActivity(), "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));


            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    @Override


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.nav,menu);
    }
    FloatingActionButton currentButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        currentButton = (FloatingActionButton) getActivity().findViewById(R.id.current);
        mGoogleApiClient = new GoogleApiClient.Builder(this.getActivity()).addApi(Places.PLACE_DETECTION_API).build();
        currentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mGoogleApiClient.isConnected()){
                    if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
                    }else {
                       // callPLaceDetectionApi();
                    }
                }
            }
        });
        if (mGoogleApiClient.isConnected()){

            if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this.getActivity(),new String []{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);

            }else{
             //   callPLaceDetectionApi();
            }
        }

                mapFragment.getMapAsync(this);
        card = (CardView) view.findViewById(R.id.card_view);
        buildingName = (TextView) view.findViewById(R.id.buildingName);
        buildingText = (TextView) view.findViewById(R.id.buildingInfo);
        buildingPic = (ImageView) view.findViewById(R.id.buildingPic);
        return view;
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
    // private boolean handled = false;

    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        mMap.setMapType(4); //Hybrid map type



        LatLng fgcu = new LatLng(26.4640491, -81.77362790000001);

        mMap.addMarker(new MarkerOptions().position(fgcu).title("FGCU Marker"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fgcu, 17));
        /*Bundle b = getArguments();

       if (b != null) {

           boolean locationBool = b.getBoolean("place");
          Log.v("AGAAFA","this is the bool  "+ locationBool);

           mMap.clear();

               mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));

        }*/
        draw();
    }


//    public void setMarkers(String search) {
//        Log.v("HIIODIT", "LOOK " + search);
//
//        assert search != null;
//
//        search = search.toLowerCase();
//
//        switch(search) {
//            case "seidler":
//
//                mMap.clear();
//
//                Log.v("HEYIY", "Selder searched");
//
//                mMap.addMarker(new MarkerOptions().position(new LatLng(26.463655, -81.774980)).title("Seidler Hall"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.463655, -81.774980), 20));
//                break;
//
//            case "holmes":
//                Log.v("DONKEY", "Homles is searched");
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(new LatLng(26.4635396,-81.7757004)).title("Holmes Hall"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.4635396,-81.7757004),20));
//
//                break;
//            case "lutgert":
//                Log.v("DONKEY", "Lutgert was searched");
//
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(new LatLng(26.4635766,-81.7765915)).title("Lutgert Hall"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.4635766,-81.7765915),20));
//
//
//                break;
//            case "marieb":
//                Log.v("DONKEY", "Marieb was searched");
//
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(new LatLng(26.4641361,-81.7758464)).title("Marieb Hall"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.4641361,-81.7758464),20));
//
//                break;
//            case "edwards":
//                Log.v("Donkey", "Edwards was searched");
//
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(new LatLng(26.4641748,-81.7751604)).title("Edwards Hall"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.4641748,-81.7751604),20));
//
//                break;
//            case "merwin":
//                Log.v("DONKEY", "Merwin was searched");
//
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(new LatLng(26.4643752,-81.7744421)).title("Merwin Hall"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.4643752,-81.7744421),20));
//
//                break;
//            case "griffin":
//                Log.v("DONKEY", "Griffin was searched");
//
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(new LatLng(26.4644718,-81.773417)).title("Griffin Hall"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.4644718,-81.773417),20));
//
//                break;
//
//            case "howard":
//                Log.v("DONKEY","Howard was searched");
//
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(new LatLng(26.4651632,-81.77308)).title("Howard Hall"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.4651632,-81.77308),20));
//
//                break;
//
//            case "cohen":
//                Log.v("DONKEY","Cohen was searched");
//
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(new LatLng(26.4659691,-81.7719977)).title("Cohen Center"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.4659691,-81.7719977),20));
//
//                break;
//            case "library":
//                Log.v("DONKEY","Library was searched");
//
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(new LatLng(26.4634091,-81.7723404)).title("Library"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.4634091,-81.7723404),20));
//
//                break;
//            case "mctarnaghen":
//                Log.v("DONKEY","Mctarnagen was searched ");
//
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(new LatLng(26.4656022,-81.7726873)).title("Mctarnaghen Hall"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.4656022,-81.7726873),20));
//
//
//                break;
//            case "whitaker":
//                Log.v("DONKEY","Whitaker was searched");
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(new LatLng(26.4638312,-81.7742843)).title("Whitaker Hall"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.4638312,-81.7742843),20));
//
//                break;
//            case "music modular":
//                break;
//            case "sugden":
//                Log.v("DONKEY","Sugden was searched");
//
//                mMap.clear();
//                mMap.addMarker(new MarkerOptions().position(new LatLng(26.4659315,-81.7711591)).title("Sugden Hall"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.4659315,-81.7711591),20));
//
//                break;
//            default:
//                Log.v("DONKEY", "Not currently listed");
//                LatLng fgcu = new LatLng(26.4640491, -81.77362790000001);
//
//                mMap.addMarker(new MarkerOptions().position(fgcu).title("FGCU Marker"));
//
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fgcu, 15));
//                break;
//            //handled = true;
//
//        }
//    }





    public void draw(){
        PolygonOptions selder;
        PolygonOptions holmes;
        PolygonOptions lutgert;
        PolygonOptions marieb;
        PolygonOptions whitaker;
        PolygonOptions edwards;
        PolygonOptions griffin;
        PolygonOptions howard;
        PolygonOptions mcTarnaghan;
        PolygonOptions merwin;
        PolygonOptions reed;
        PolygonOptions sugden;

        whitaker = new PolygonOptions().add(
                new LatLng(26.463830,-81.774683),
                new LatLng(26.463917,-81.774226),
                new LatLng(26.464018,-81.774246),
                new LatLng(26.464086,-81.773834),
                new LatLng(26.463999,-81.773813),
                new LatLng(26.464003,-81.773771),
                new LatLng(26.463843,-81.773736),
                new LatLng(26.463834,-81.773772),
                new LatLng(26.463706,-81.773747),
                new LatLng(26.463634,-81.774166),
                new LatLng(26.463687,-81.774172),
                new LatLng(26.463672,-81.774260),
                new LatLng(26.463580,-81.774497),
                new LatLng(26.463647,-81.774555),
                new LatLng(26.463629,-81.774643),
                new LatLng(26.463685,-81.774653),
                new LatLng(26.463679,-81.774694),
                new LatLng(26.463796,-81.774718),
                new LatLng(26.463806,-81.774679)
        ).visible(false);

        selder = new PolygonOptions().add(
                new LatLng(26.463813,-81.775204), // 1
                new LatLng(26.463843,-81.775003), // 2
                new LatLng(26.463866,-81.775008), // 3
                new LatLng(26.463866,-81.774884), // 4
                new LatLng(26.463848,-81.774780), // 5
                new LatLng(26.463830,-81.774790), // 6
                new LatLng(26.463768,-81.774794), // 7
                new LatLng(26.463508,-81.774735), // 8
                new LatLng(26.463484,-81.774836), // 9
                new LatLng(26.463505,-81.774847), // 10
                new LatLng(26.463492,-81.774920), // 11
                new LatLng(26.463471,-81.774915), // 12
                new LatLng(26.463463,-81.774961), // 13
                new LatLng(26.463432,-81.774953), // 14
                new LatLng(26.463399,-81.775111)
        ).visible(false); // 15

        holmes = new PolygonOptions().add(
                new LatLng(26.463665,-81.775952),
                new LatLng(26.463766,-81.775365),
                new LatLng(26.463744,-81.775359),
                new LatLng(26.463749,-81.775317),
                new LatLng(26.463708,-81.775302),
                new LatLng(26.463710,-81.775262),
                new LatLng(26.463664,-81.775247),
                new LatLng(26.463658,-81.775262),
                new LatLng(26.463594,-81.775246),
                new LatLng(26.463586,-81.775278),
                new LatLng(26.463541,-81.775268),
                new LatLng(26.463536,-81.775302),
                new LatLng(26.463463,-81.775286),
                new LatLng(26.463387,-81.775672),
                new LatLng(26.463471,-81.775693),
                new LatLng(26.463440,-81.775833),
                new LatLng(26.463491,-81.775896),
                new LatLng(26.463479,-81.775951),
                new LatLng(26.463626,-81.775983),
                new LatLng(26.463636,-81.775946)
        ).visible(false);

        lutgert = new PolygonOptions().add(
                new LatLng(26.463590,-81.776511),
                new LatLng(26.463660,-81.776115),
                new LatLng(26.463490,-81.776078),
                new LatLng(26.463447,-81.776281),
                new LatLng(26.463417,-81.776272),
                new LatLng(26.463376,-81.776465),
                new LatLng(26.463451,-81.776486),
                new LatLng(26.463414,-81.776658),
                new LatLng(26.463512,-81.776678),
                new LatLng(26.463558,-81.776720),
                new LatLng(26.463660,-81.776786),
                new LatLng(26.463774,-81.776808),
                new LatLng(26.463895,-81.776797),
                new LatLng(26.463947,-81.776782),
                new LatLng(26.463975,-81.776623),
                new LatLng(26.463924,-81.776611),
                new LatLng(26.463989,-81.776225),
                new LatLng(26.463966,-81.776219),
                new LatLng(26.463990,-81.776096),
                new LatLng(26.463940,-81.776083),
                new LatLng(26.463929,-81.776147),
                new LatLng(26.463895,-81.776141),
                new LatLng(26.463812,-81.776569)
        ).visible(false);

        marieb = new PolygonOptions().add(
                new LatLng(26.464360,-81.776120),
                new LatLng(26.464471,-81.775568),
                new LatLng(26.464182,-81.775504),
                new LatLng(26.464131,-81.775763),
                new LatLng(26.463962,-81.775717),
                new LatLng(26.463913,-81.776021)
        ).visible(false);

        edwards = new PolygonOptions().add(
                new LatLng(26.464043,-81.775110),
                new LatLng(26.464073,-81.774970),
                new LatLng(26.464115,-81.774978),
                new LatLng(26.464136,-81.774874),
                new LatLng(26.464436,-81.774940),
                new LatLng(26.464418,-81.775045),
                new LatLng(26.464434,-81.775051),
                new LatLng(26.464493,-81.775018),
                new LatLng(26.464518,-81.775052),
                new LatLng(26.464508,-81.775207),
                new LatLng(26.464496,-81.775251),
                new LatLng(26.464474,-81.775278),
                new LatLng(26.464465,-81.775270),
                new LatLng(26.464428,-81.775460),
                new LatLng(26.464371,-81.775447),
                new LatLng(26.464366,-81.775474),
                new LatLng(26.464239,-81.775442),
                new LatLng(26.464292,-81.775220),
                new LatLng(26.464277,-81.775212),
                new LatLng(26.464287,-81.775172)
        ).visible(false);

        merwin = new PolygonOptions().add(
                new LatLng(26.464170,-81.774680),
                new LatLng(26.464201,-81.774495),
                new LatLng(26.464166,-81.774486),
                new LatLng(26.464188,-81.774368),
                new LatLng(26.464228,-81.774373),
                new LatLng(26.464254,-81.774203),
                new LatLng(26.464220,-81.774197),
                new LatLng(26.464243,-81.774068),
                new LatLng(26.464283,-81.774074),
                new LatLng(26.464316,-81.773895),
                new LatLng(26.464513,-81.773939),
                new LatLng(26.464492,-81.774074),
                new LatLng(26.464563,-81.774091),
                new LatLng(26.464600,-81.773914),
                new LatLng(26.464685,-81.773936),
                new LatLng(26.464656,-81.774110),
                new LatLng(26.464683,-81.774118),
                new LatLng(26.464666,-81.774227),
                new LatLng(26.464639,-81.774223),
                new LatLng(26.464582,-81.774512),
                new LatLng(26.464611,-81.774517),
                new LatLng(26.464586,-81.774636),
                new LatLng(26.464561,-81.774625),
                new LatLng(26.464520,-81.774805),
                new LatLng(26.464437,-81.774787),
                new LatLng(26.464470,-81.774609),
                new LatLng(26.464399,-81.774594),
                new LatLng(26.464373,-81.774720)
        ).visible(false);

        griffin = new PolygonOptions().add(
                new LatLng(26.464344,-81.773630),
                new LatLng(26.464443,-81.773103),
                new LatLng(26.464547,-81.773127),
                new LatLng(26.464556,-81.773075),
                new LatLng(26.464739,-81.773118),
                new LatLng(26.464729,-81.773182),
                new LatLng(26.464814,-81.773201),
                new LatLng(26.464715,-81.773715),
                new LatLng(26.464624,-81.773695),
                new LatLng(26.464596,-81.773828),
                new LatLng(26.464430,-81.773791),
                new LatLng(26.46445,-81.773657)
        ).visible(false);

        //TODO: Must add whitaker hall and reed and set them clickable and visible to false.
        whitakerPolygon =  mMap.addPolygon(whitaker);
        griffinPolygon = mMap.addPolygon(griffin);
        merwinPolygon =  mMap.addPolygon(merwin);
        edwardsPolygon = mMap.addPolygon(edwards);
        mariebPolygon = mMap.addPolygon(marieb);
        lutgertPolygon = mMap.addPolygon(lutgert);
        holmesPolygon = mMap.addPolygon(holmes);
        seidlerPolygon = mMap.addPolygon(selder);


        seidlerPolygon.setClickable(true);
        holmesPolygon.setClickable(true);
        lutgertPolygon.setClickable(true);
        mariebPolygon.setClickable(true);
        edwardsPolygon.setClickable(true);
        merwinPolygon.setClickable(true);
        griffinPolygon.setClickable(true);
        whitakerPolygon.setClickable(true);


      /*  holmesId = holmesPolygon.getId();
        lutgurtId = lutgertPolygon.getId();
        mariebId = mariebPolygon.getId();
        edwardsId = edwardsPolygon.getId();
        merwinId = merwinPolygon.getId();
        griffinId = griffinPolygon.getId();
        whitakerId = whitakerPolygon.getId();
*/
        buildingClicked();

        // SearchMarker(s);
    }


    public void buildingClicked(){

        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {

            boolean click = false;

            public void onPolygonClick(Polygon polygon) {
                Resources res = getResources();
                if(!click) {
                    Log.v("DONKEY", "Building clicked:  ");
                    if(polygon.getId().equals(seidlerPolygon.getId())) {
                        Log.v("DONKEYYYYYY", "SHOW seidler card");
                        card.setVisibility(View.VISIBLE);
                        buildingPic.setImageResource(R.drawable.seidler_hall);
                        buildingName.setText(res.getText(R.string.Seidler));
                        buildingText.setText(res.getText(R.string.Seidler_hall));
                        click = true;
                    }else if (polygon.getId().equals(holmesPolygon.getId())){
                        Log.v("DONKEYYYYYY", "SHOW holmes card");
                        card.setVisibility(View.VISIBLE);
                        buildingPic.setImageResource(R.drawable.holmes_hall);
                        buildingText.setText(res.getText(R.string.Holmes_info));
                        buildingName.setText(res.getText(R.string.Holmes_name));

                        click = true;
                    }else if (polygon.getId().equals(lutgertPolygon.getId())){
                        Log.v("DONKEYYYYYY", "SHOW lutgert card");
                        card.setVisibility(View.VISIBLE);
                        click = true;
                    }else if(polygon.getId().equals(mariebPolygon.getId())){
                        Log.v("DONKEYYYYYY", "SHOW marieb card");
                        card.setVisibility(View.VISIBLE);
                        click = true;
                    }else if (polygon.getId().equals(edwardsPolygon.getId())){
                        Log.v("DONKEYYYYYY", "SHOW edwards card");
                        card.setVisibility(View.VISIBLE);
                        click = true;
                    }else if (polygon.getId().equals(merwinPolygon.getId())){
                        Log.v("DONKEYYYYYY", "SHOW merwin card");
                        card.setVisibility(View.VISIBLE);
                        click = true;
                    }else if (polygon.getId().equals(griffinPolygon.getId())){
                        Log.v("DONKEYYYYYY", "SHOW griffin card");
                        card.setVisibility(View.VISIBLE);
                        click = true;
                    }else if (polygon.getId().equals(whitakerPolygon.getId())){
                        Log.v("DONKEYYYYYY", "SHOW whitaker card");
                        card.setVisibility(View.VISIBLE);
                        click = true;
                    }



                }  /*  holmesId = holmesPolygon.getId();
        lutgurtId = lutgertPolygon.getId();
        mariebId = mariebPolygon.getId();
        edwardsId = edwardsPolygon.getId();
        merwinId = merwinPolygon.getId();
        griffinId = griffinPolygon.getId();
        whitakerId = whitakerPolygon.getId();
*/
                else {
                    Log.v("DONKEY", "HIDING CARDVIEW");
                    card.setVisibility(View.INVISIBLE);
                    click = false;
                }
            }
        });

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}