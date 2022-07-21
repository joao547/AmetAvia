package br.edu.ifpe.tads.ametavia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.TextView;

import br.edu.ifpe.tads.ametavia.models.Ong;

public class DetailsOng extends AppCompatActivity {

    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_ong);

        final TextView ongNameTextView = (TextView) findViewById(R.id.ong_name);
        final TextView ongBioTextView = (TextView) findViewById(R.id.ong_bio);
        Ong ong = new Ong("Lar das vovós", "teste", "email", "orem ipsum dolor sit amet, consectetur adipiscing elit. Donec sed molestie sem. Donec est sem, rutrum et ante in, posuere eleifend ligula. Curabitur tempor enim id dolor semper, id faucibus nisi pretium. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Mauris semper ante accumsan justo scelerisque, ut semper lacus finibus. Duis vitae fringilla arcu, ac commodo augue.");

        ongNameTextView.setText(ong.getName());
        ongBioTextView.setText(ong.getBio());


        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.containerMap, new MapsFragment(), "MapsFragment");
        transaction.commitAllowingStateLoss();
    }
}