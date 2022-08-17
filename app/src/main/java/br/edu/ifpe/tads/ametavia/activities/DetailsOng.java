package br.edu.ifpe.tads.ametavia.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.tads.ametavia.fragments.MapsFragment;
import br.edu.ifpe.tads.ametavia.R;
import br.edu.ifpe.tads.ametavia.models.Ong;

public class DetailsOng extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Ong ongDetailed;
    private List<String> sampleImages = new ArrayList<String>();
    private CarouselView carouselView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_ong);

        sampleImages.add("https://images.unsplash.com/photo-1603314585442-ee3b3c16fbcf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80");
        sampleImages.add("https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1443&q=80");
        sampleImages.add("https://images.unsplash.com/photo-1511275539165-cc46b1ee89bf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80");

        initiateComponent();
    }

    private void initiateComponent () {
        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.size());
        carouselView.setImageListener(imageListener);

        final TextView ongNameTextView = (TextView) findViewById(R.id.ong_name);
        final TextView ongBioTextView = (TextView) findViewById(R.id.ong_bio);
        TextView ongAddressTextView = findViewById(R.id.address);

        ongDetailed = (Ong) getIntent().getSerializableExtra("ong");
        ongNameTextView.setText(ongDetailed.getName());
        ongBioTextView.setText(ongDetailed.getBio());
        ongAddressTextView.setText(ongDetailed.getAddress().formatted());

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container_map, new MapsFragment(this, ongDetailed.getAddress().getLatitude(), ongDetailed.getAddress().getLongitude(), ongDetailed.getName()), "MapsFragment")
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Picasso.get().load(sampleImages.get(position)).into(imageView);
        }
    };

    public void onClickContact() {
        String url = "https://api.whatsapp.com/send?phone=" + ongDetailed.getWhatsapp();
        try {
            PackageManager pm = this.getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}