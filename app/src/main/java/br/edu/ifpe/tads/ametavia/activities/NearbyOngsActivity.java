package br.edu.ifpe.tads.ametavia.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import br.edu.ifpe.tads.ametavia.R;
import br.edu.ifpe.tads.ametavia.adapters.Geofencing;
import br.edu.ifpe.tads.ametavia.fragments.MapsFragment;
import br.edu.ifpe.tads.ametavia.models.Address;
import br.edu.ifpe.tads.ametavia.models.Ong;

public class NearbyOngsActivity extends AppCompatActivity {

    Geofencing geofencing;
    List<Ong> ongList = new ArrayList<Ong>();

    private FragmentManager fragmentManager;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_ongs);

        ongList.add(new Ong(
                "Lar das vovozinhas",
                new Address(
                        UUID.randomUUID(),
                        -8.0229191,
                        -34.9210447,
                        "Padre Lemos",
                        "293","52070-200",
                        "Pernambuco",
                        "Recife",
                        "Brasil"),
                "lardasvovozinhas@gmail.com",
                "Fusce tincidunt, orci sit amet sollicitudin placerat, sapien ipsum blandit turpis, nec rutrum sem nunc a tellus. Integer eu dictum urna, eget ornare nulla. ",
                "051659865982"));
        ongList.add(new Ong(
                "nome2",
                new Address(
                UUID.randomUUID(),
                -8.0244782,
                -34.9183673,
                "Estrada do Arraial",
                "3259","52051-430",
                "Pernambuco",
                "Recife",
                "Brasil"),
                "email2",
                "bio2",
                "051659865982"));
        ongList.add(new Ong(
                "nome3",
                new Address(
                UUID.randomUUID(),
                -8.0305521,
                -34.9163229,
                "Estrada do Arraial",
                "3359","52051-380",
                "Pernambuco",
                "Recife",
                "Brasil"),
                "email3",
                "bio3",
                "051659865982"));


        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container_map,  new MapsFragment(this), "NearbyOngMapsFragment")
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
        fragmentManager.executePendingTransactions();

        populateMapAddresses(ongList);

        geofencing = new Geofencing(this);
        geofencing.registerAllGeofences(ongList.stream().map(ong -> ong.getAddress()).collect(Collectors.toList()));
    }

    public void populateMapAddresses(List<Ong> ongList) {
        MapsFragment mapsFragment = (MapsFragment) fragmentManager.findFragmentByTag("NearbyOngMapsFragment");
        mapsFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                for (Ong ong : ongList) {
                LatLng location = new LatLng(ong.getAddress().getLatitude(), ong.getAddress().getLongitude());
                googleMap.addMarker(new MarkerOptions()
                        .position(location)
                        .title(ong.getName()));
                }
            }
        });
    }
}