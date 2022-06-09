package br.edu.ifpe.tads.ametavia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FormLogin extends AppCompatActivity {

    private AppCompatButton buttonNewOng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        getSupportActionBar().hide();
        iniciarComponentes();

        buttonNewOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormLogin.this, HomePage.class);
                startActivity(intent);
            }
        });
    }

    private void iniciarComponentes(){
        buttonNewOng = findViewById(R.id.ButtonStart);
    }
}