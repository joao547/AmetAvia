package br.edu.ifpe.tads.ametavia.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import br.edu.ifpe.tads.ametavia.R;

public class OngAdapter extends ArrayAdapter<Ong> {
    private ArrayList<Ong> ongs = new ArrayList<>();
    public OngAdapter(@NonNull Context context, int resource, ArrayList<Ong> ongs) {
        super(context, resource, ongs);
        this.ongs = ongs;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View listItem = inflater.inflate(R.layout.ong_card_item, null, true);
        TextView bioView = listItem.findViewById(R.id.card_info_text);
        bioView.setText(ongs.get(position).getBio());
        setImage(listItem, ongs.get(position).getUrlPath());
        return listItem;
    }

    private void setImage(View view, String url) {
        ImageView imageView = (ImageView) view.findViewById(R.id.card_main_image);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        AtomicReference<Bitmap> image = new AtomicReference<Bitmap>();
        executor.execute(() -> {
            String imageURL = "https://images.unsplash.com/photo-1603314585442-ee3b3c16fbcf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80";
            try {
                InputStream in = new URL(url.isEmpty() ? imageURL : url).openStream();
                image.set(BitmapFactory.decodeStream(in));
                handler.post(() -> imageView.setImageBitmap(image.get()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
