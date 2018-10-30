package com.example.aniket.agriculture.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.transition.Slide;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aniket.agriculture.global_data.GlobalData;
import com.example.aniket.agriculture.model_classes.MarketData;
import com.example.aniket.agriculture.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Iterator;
import java.util.Vector;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public boolean mLocationPermissionGranted;

    public static int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    private static final int DEFAULT_ZOOM = 5;
    private static final String TAG = MapsActivity.class.getSimpleName();

    //private final LatLng mDefaultLocation = new LatLng(18.4575, 73.8508);
    private Location mLastKnownLocation;

    private CameraPosition mCameraPosition;

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    int PLACE_PICKER_REQUEST = 1;

    ImageView dropDown;
    TextView crop;

    public static String[] markets;
    public static String[] prices;

    Vector<Pair<String,LatLng>> market = new Vector<Pair<String,LatLng>>();
    Pair<String,LatLng> Shrirampur = new Pair<String, LatLng>("Shrirampur",new LatLng(19.6222,74.6576));
    Pair<String,LatLng> Rahata = new Pair<String, LatLng>("Rahata",new LatLng(19.7127,74.4833));
    Pair<String,LatLng> Kopargaon = new Pair<String, LatLng>("Kopargaon",new LatLng(19.8917,74.4791));
    Pair<String,LatLng> Vadgaonpeth = new Pair<String, LatLng>("Vadgaonpeth",new LatLng(16.8375,74.3140));
    Pair<String,LatLng> Mumbai = new Pair<String, LatLng>("Mumbai",new LatLng(19.0760,72.8777));
    Pair<String,LatLng> Kalmeshwar = new Pair<String, LatLng>("Kalmeshwar",new LatLng(21.2335,78.9119));
    Pair<String,LatLng> Kamthi = new Pair<String, LatLng>("Kamthi",new LatLng(21.2275,79.1901));
    Pair<String,LatLng> Chakan = new Pair<String, LatLng>("Chakan",new LatLng(18.7603,73.8630));
    Pair<String,LatLng> Pimpri = new Pair<String, LatLng>("Pimpri",new LatLng(18.6298,73.7997));
    Pair<String,LatLng> Palus = new Pair<String, LatLng>("Palus",new LatLng(17.0976,74.4496));
    Pair<String,LatLng> Vai = new Pair<String, LatLng>("Vai",new LatLng(17.9487,73.8919));
    Pair<String,LatLng> Pandharpur = new Pair<String, LatLng>("Pandharpur",new LatLng(17.6746,75.3237));

    String selectedCrop;
    String[] crops = {"Bottle Gourd", "Brinjal", "Cabbage", "Carrot", "Coriander", "Cucumber", "Garlic", "Green Chilli", "LadyFinger", "Millet", "Oats", "Potato", "Spinach", "Sugarcane", "Tomato"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        setContentView(R.layout.activity_maps);

        dropDown = (ImageView) findViewById(R.id.dropDown);

        crop = (TextView)findViewById(R.id.crop);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        addCrops();
        dropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayCrops();
            }
        });
    }

    private void addCrops() {
        market.add(Shrirampur);
        market.add(Rahata);
        market.add(Kopargaon);
        market.add(Vadgaonpeth);
        market.add(Mumbai);
        market.add(Kalmeshwar);
        market.add(Kamthi);
        market.add(Chakan);
        market.add(Pimpri);
        market.add(Palus);
        market.add(Vai);
        market.add(Pandharpur);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(String.valueOf(place.getName())).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getLocationPermission();

        updateLocationUI();

        getDeviceLocation();

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View view = getLayoutInflater().inflate(R.layout.market_info, null);

                TextView city = (TextView) view.findViewById(R.id.city);
                TextView price = (TextView)view.findViewById(R.id.price);

                city.setText(marker.getTitle());

                String city1 = marker.getTitle();

                for(int i=0;i<markets.length;i++){
                    if(markets[i].equals(city1)){
                        String amt = prices[i];
                        price.setText("Rs."+amt);
                        break;
                    }
                }

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Toast.makeText(MapsActivity.this, marker.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });

                return view;
            }
        });

    }

    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }

    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }

    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void displayCrops(){
        final Dialog dialog = new Dialog(this);

        View view = getLayoutInflater().inflate(R.layout.dialog, null);

        ListView lv = (ListView) view.findViewById(R.id.cropList);

        ArrayAdapter<String> cropAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,crops);

        lv.setAdapter(cropAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mMap.clear();
                selectedCrop = crops[position];
                dialog.dismiss();
                crop.setText(selectedCrop);
                searchMarket(selectedCrop);

            }
        });

        dialog.setContentView(view);
        Window window = dialog.getWindow();

        if (window != null) {
            window.setLayout(900,1050);
            window.setTitle("Available Crops");
            window.setEnterTransition(new Slide());
        }


        dialog.show();
    }

    private void searchMarket(String selectedCrop) {
        MarketData md = GlobalData.marketData.get(selectedCrop);
        markets = md.getMarket().split(",");
        prices = md.getPrice().split(",");

        int i=0;
        while(i < markets.length) {
            Iterator<Pair<String, LatLng>> it = market.iterator();
            while (it.hasNext()) {
                Pair<String, LatLng> pair = it.next();
                if (markets[i].equalsIgnoreCase(pair.first)) {
                    mMap.addMarker(new MarkerOptions().position(pair.second).title(pair.first).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(pair.second));
                }
            }
            i++;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(MapsActivity.this,HomeActivity.class);
        startActivity(intent);
    }
}