package br.edu.ifpe.tads.ametavia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.Date;

import br.edu.ifpe.tads.ametavia.models.Ong;
import br.edu.ifpe.tads.ametavia.models.Volunteer;

public class FormCadastroOng extends AppCompatActivity {

    private Button saveButton;
    private EditText edEmail;
    private EditText edPass;
    private EditText edName;
    private EditText edAddress;
    private EditText edBio;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro_ong);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Nova ONG");
        actionBar.setDisplayHomeAsUpEnabled(true);

        initComponents();
        mAuth = FirebaseAuth.getInstance();
    }

    private void initComponents(){
        saveButton = findViewById(R.id.save_button);
        edEmail = findViewById(R.id.email);
        edPass = findViewById(R.id.password);
        edName = findViewById(R.id.name);
        edBio = findViewById(R.id.bio);
        edAddress = findViewById(R.id.adredd);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString();
                String password = edPass.getText().toString();
                String name = edName.getText().toString();
                String bio = edBio.getText().toString();
                String address = edAddress.getText().toString();
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(FormCadastroOng.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Ong volunteer = new Ong(name, address, email, bio);
                                    updateUI(user,volunteer);
                                } else {
                                    Toast.makeText(FormCadastroOng.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void updateUI(FirebaseUser user, Ong ong) {
        DatabaseReference drOng = FirebaseDatabase.getInstance().getReference("ong");
        DatabaseReference drOngs = FirebaseDatabase.getInstance().getReference("ongs");
        drOng.child(user.getUid()).setValue(ong);
        drOngs.setValue(ong);
        Intent intent = new Intent(FormCadastroOng.this, FormLogin.class);
        startActivity(intent);
    }
}