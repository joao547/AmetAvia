package br.edu.ifpe.tads.ametavia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FormLogin extends AppCompatActivity {

    private Button newLogin;
    private Button newOng;
    private Button newVolunteer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        initiateComponents();
        getSupportActionBar().hide();
    }

    private void initiateComponents(){
        newLogin = findViewById(R.id.login_button);
        newOng = findViewById(R.id.new_ong_button);
        newVolunteer = findViewById(R.id.new_volunteer_button);

        newLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormLogin.this, Dashboard.class);
                startActivity(intent);
            }
        });

        newOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormLogin.this, FormCadastroOng.class);
                startActivity(intent);
            }
        });

        newVolunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormLogin.this, FormCadastroVol.class);
                startActivity(intent);
            }
        });
    }
}