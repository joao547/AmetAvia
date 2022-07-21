package br.edu.ifpe.tads.ametavia;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import br.edu.ifpe.tads.ametavia.models.Ong;
import br.edu.ifpe.tads.ametavia.models.OngAdapter;

public class Dashboard extends AppCompatActivity {

    private Button helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        ArrayList<Ong> ongList = new ArrayList<>();
        ongList.add(new Ong("nome1", "endereco1", "email1", "bio1"));
        ongList.add(new Ong("nome2", "endereco2", "email2", "bio2"));
        ongList.add(new Ong("nome3", "endereco3", "email3", "bio3"));

        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(new OngAdapter(this,
                        R.layout.ong_card_item, ongList)
        );

        listView.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(parent.getContext(), "Ong selecionada: " +
                        ongList.get(position).getName(), Toast.LENGTH_SHORT).show());

//        initiateComponents();
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

//    private void initiateComponents() {
//        helpButton = findViewById(R.id.help_button);
//
//        helpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Dashboard.this, DetailsOng.class);
//                startActivity(intent);
//            }
//        });
//    }
}