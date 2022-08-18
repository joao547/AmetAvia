package br.edu.ifpe.tads.ametavia.activities;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import br.edu.ifpe.tads.ametavia.R;
import br.edu.ifpe.tads.ametavia.models.Address;
import br.edu.ifpe.tads.ametavia.models.Ong;
import br.edu.ifpe.tads.ametavia.adapters.OngListAdapter;
import br.edu.ifpe.tads.ametavia.models.Volunteer;

public class Dashboard extends AppCompatActivity {

    private Button helpButton;
    private ExtendedFloatingActionButton floatingActionButton;

    private static final String TAG = "Dashboard";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ArrayList<Ong> ongList = new ArrayList<>();

        ListView listView = (ListView)findViewById(R.id.list_view);
        OngListAdapter adapter = new OngListAdapter(this,
                R.layout.ong_card_item, ongList);
        initOngs(ongList, adapter);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(parent.getContext(), "Ong selecionada: " +
                        ongList.get(position).getName(), Toast.LENGTH_SHORT).show());

        initiateComponents();

    }

    private void initOngs(ArrayList<Ong> ongList, OngListAdapter ongAdapter) {
        DatabaseReference drOng = FirebaseDatabase.getInstance().getReference("ong");
        Query mQuery = drOng.orderByKey();
        ValueEventListener mQueryValueListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while (iterator.hasNext()) {
                    DataSnapshot next = (DataSnapshot) iterator.next();
                    ongList.add(next.getValue(Ong.class));
                    ongAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Value = " + next.getValue());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mQuery.addListenerForSingleValueEvent(mQueryValueListener);
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