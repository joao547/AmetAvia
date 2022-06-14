package br.edu.ifpe.tads.ametavia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class Dashboard extends AppCompatActivity {

    private Button helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        final TextView helloTextView = (TextView) findViewById(R.id.info_text);

        //TODO: this information should come from backend
        helloTextView.setText(R.string.card_info_text);
        //TODO: think about refactor this method, maybe put this in a helper file
        setImage();
        initiateComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
      return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
//                openSearch();
                return true;
            case R.id.action_settings:
//                openSettings();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void initiateComponents() {
        helpButton = findViewById(R.id.help_button);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, FormCadastroVol.class);
                startActivity(intent);
            }
        });
    }

    private void setImage() {
        ImageView imageView = (ImageView) findViewById(R.id.main_image);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        AtomicReference<Bitmap> image = new AtomicReference<Bitmap>();
        executor.execute(() -> {
            String imageURL = "https://images.unsplash.com/photo-1603314585442-ee3b3c16fbcf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80";
            try {
                InputStream in = new URL(imageURL).openStream();
                image.set(BitmapFactory.decodeStream(in));
                handler.post(() -> imageView.setImageBitmap(image.get()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}