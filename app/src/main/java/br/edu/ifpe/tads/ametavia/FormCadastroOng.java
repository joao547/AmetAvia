package br.edu.ifpe.tads.ametavia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class FormCadastroOng extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro_ong);

        getSupportActionBar().hide();


    }
}