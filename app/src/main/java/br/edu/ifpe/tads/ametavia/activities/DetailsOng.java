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

        ongDetailed = (Ong) getIntent().getSerializableExtra("ong");

        for (String uri : ongDetailed.getImages()){
            sampleImages.add(uri);
        }
        initiateComponent();
    }

    private void initiateComponent () {
        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.size());
        carouselView.setImageListener(imageListener);

        final TextView ongNameTextView = (TextView) findViewById(R.id.ong_name);
        final TextView ongBioTextView = (TextView) findViewById(R.id.ong_bio);
        TextView ongAddressTextView = findViewById(R.id.address);

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