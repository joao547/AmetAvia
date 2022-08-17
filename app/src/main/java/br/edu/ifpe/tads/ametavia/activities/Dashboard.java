package br.edu.ifpe.tads.ametavia.activities;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

import br.edu.ifpe.tads.ametavia.R;
import br.edu.ifpe.tads.ametavia.models.Address;
import br.edu.ifpe.tads.ametavia.models.Ong;
import br.edu.ifpe.tads.ametavia.adapters.OngListAdapter;

public class Dashboard extends AppCompatActivity {

    private Button helpButton;
    private ExtendedFloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ArrayList<Ong> ongList = new ArrayList<>();
        ongList.add(new Ong(
                "Lar das vovozinhas",
                new Address(
                        UUID.randomUUID(),
                        -8.0244782,
                        -34.9183673,
                        "Estrada do Arraial",
                        "3259","52051-430",
                        "Pernambuco",
                        "Recife",
                        "Brasil"),
                "lardasvovozinhas@gmail.com",
                "Fusce tincidunt, orci sit amet sollicitudin placerat, sapien ipsum blandit turpis, nec rutrum sem nunc a tellus. Integer eu dictum urna, eget ornare nulla. ",
                "051659865982"));
        ongList.add(new Ong("nome2", new Address(), "email2", "bio2", "051659865982"));
        ongList.add(new Ong("nome3", new Address(), "email3", "bio3", "051659865982"));

        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(new OngListAdapter(this,
                        R.layout.ong_card_item, ongList)
        );

        listView.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(parent.getContext(), "Ong selecionada: " +
                        ongList.get(position).getName(), Toast.LENGTH_SHORT).show());

        initiateComponents();

    }

    private void initOngs(ArrayList<Ong> ongList, OngListAdapter ongAdapter) {
        DatabaseReference drOng = FirebaseDatabase.getInstance().getReference().child("ongs");
        drOng.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ongList.add(dataSnapshot.getValue(Ong.class));
                    ongAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.show();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, NearbyOngsActivity.class);
                startActivity(intent);
            }
        });
    }
}