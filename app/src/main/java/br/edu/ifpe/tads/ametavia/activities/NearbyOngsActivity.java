package br.edu.ifpe.tads.ametavia.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.PendingIntent;
import android.os.Bundle;

import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.edu.ifpe.tads.ametavia.R;
import br.edu.ifpe.tads.ametavia.adapters.Geofencing;
import br.edu.ifpe.tads.ametavia.fragments.MapsFragment;
import br.edu.ifpe.tads.ametavia.models.Address;

public class NearbyOngsActivity extends AppCompatActivity {

    Geofencing geofencing;
    List<Address> addressList = new ArrayList<Address>();

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_ongs);
        addressList.add(new Address(
                UUID.randomUUID(),
                -8.0229191,
                -34.9210447,
                "Padre Lemos",
                "293","52070-200",
                "Pernambuco",
                "Recife",
                "Brasil"));
        addressList.add(new Address(
                UUID.randomUUID(),
                -8.0244782,
                -34.9183673,
                "Estrada do Arraial",
                "3259","52051-430",
                "Pernambuco",
                "Recife",
                "Brasil"));

        addressList.add(new Address(
                UUID.randomUUID(),
                -8.0305521,
                -34.9163229,
                "Estrada do Arraial",
                "3359","52051-380",
                "Pernambuco",
                "Recife",
                "Brasil"));


        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container_map,  new MapsFragment(this), "MapsFragment")
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();

        geofencing = new Geofencing(this);
        geofencing.registerAllGeofences(addressList);
    }
}