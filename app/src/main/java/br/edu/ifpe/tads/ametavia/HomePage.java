package br.edu.ifpe.tads.ametavia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomePage extends AppCompatActivity {

    private AppCompatButton buttonDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        getSupportActionBar().hide();
        iniciarComponentes();

        buttonDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, DetailsOng.class);
                startActivity(intent);
            }
        });
    }

    private void iniciarComponentes(){
        buttonDetail = findViewById(R.id.ButtonAjudar);
    }
}