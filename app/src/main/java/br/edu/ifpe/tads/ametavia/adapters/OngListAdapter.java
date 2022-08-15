package br.edu.ifpe.tads.ametavia.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import br.edu.ifpe.tads.ametavia.activities.DetailsOng;
import br.edu.ifpe.tads.ametavia.R;
import br.edu.ifpe.tads.ametavia.models.Ong;

public class OngListAdapter extends ArrayAdapter<Ong> {
    private ArrayList<Ong> ongs = new ArrayList<>();
    public OngListAdapter(@NonNull Context context, int resource, ArrayList<Ong> ongs) {
        super(context, resource, ongs);
        this.ongs = ongs;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        String imageURL = "https://images.unsplash.com/photo-1603314585442-ee3b3c16fbcf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80";

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View listItem = inflater.inflate(R.layout.ong_card_item, null, true);
        TextView bioView = listItem.findViewById(R.id.card_info_text);
        ImageView imageView = listItem.findViewById(R.id.card_main_image);
        Picasso.get().load(imageURL).into(imageView);
        bioView.setText(ongs.get(position).getBio());
        initiateComponent(getContext(), listItem, ongs.get(position));
        return listItem;
    }

    private void initiateComponent(Context context, View view, Ong ong) {
        Button helpButton = view.findViewById(R.id.help_button);

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsOng.class);
                intent.putExtra("ong", ong);
                context.startActivity(intent);
            }
        });
    }
}
