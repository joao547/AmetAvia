package br.edu.ifpe.tads.ametavia.fragments;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Activity activity;
    private LatLng location = new LatLng(-8.05, -34.9);
    private String defaultMarkLabel = "Recife";

    final private int FINE_LOCATION_REQUEST = 3;
    private boolean fine_location;

    public MapsFragment(){}

    public MapsFragment(Activity activity) {
        super();
        this.activity = activity;
    }

    public MapsFragment(Activity activity, double latitude, double longitude, String label) {
        super();
        this.activity = activity;
        this.location = new LatLng(latitude, longitude);
        this.defaultMarkLabel = label;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermission();
        getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions()
                .position(this.location)
                .title(defaultMarkLabel));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.location, 15));
        mMap.setOnMyLocationButtonClickListener(
                () -> {
                    Toast.makeText(this.activity,
                            "Indo para a sua localização.", Toast.LENGTH_SHORT).show();
                    return false;
                });

        mMap.setOnMyLocationClickListener(
                location -> Toast.makeText(this.activity,
                        "Você está aqui!", Toast.LENGTH_SHORT).show());

        mMap.setMyLocationEnabled(this.fine_location);
    }

    private void requestPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_FINE_LOCATION);
        this.fine_location = (permissionCheck == PackageManager.PERMISSION_GRANTED);
        if (this.fine_location) return;
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_REQUEST);
    }

    @SuppressLint("MissingPermission")
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println("on request permission result");
        boolean granted = (grantResults.length > 0) &&
                (grantResults[0] == PackageManager.PERMISSION_GRANTED);
        this.fine_location = (requestCode == FINE_LOCATION_REQUEST) && granted;
        if (mMap != null) {
            mMap.setMyLocationEnabled(this.fine_location);
        }
    }

    public br.edu.ifpe.tads.ametavia.models.Address foundLatLong(String address) {
        Geocoder geoCoder = new Geocoder(this.activity, Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(address, 1);
            if (addresses.size() > 0) {
                Address addrs = addresses.get(0);
                Double lat = (double) (addrs.getLatitude());
                Double lon = (double) (addrs.getLongitude());

                Log.d("lat-long", "" + lat + "......." + lon);
                final LatLng user = new LatLng(lat, lon);
                mMap.clear();
                mMap.addMarker(new MarkerOptions()
                        .position(user)
                        .title(address));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 30));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
                return new br.edu.ifpe.tads.ametavia.models.Address(UUID.randomUUID(),lat,lon, addrs.getThoroughfare(), addrs.getSubThoroughfare(),
                        addrs.getPostalCode(), addrs.getAdminArea(),  addrs.getSubAdminArea(), addrs.getCountryName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addMarkMap(br.edu.ifpe.tads.ametavia.models.Address address, String label) {
        LatLng location = new LatLng(address.getLatitude(), address.getLongitude());
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title(label));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 30));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }
}