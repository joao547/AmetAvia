package br.edu.ifpe.tads.ametavia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FormCadastroVol extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro_vol);

        getSupportActionBar().hide();
    }
}