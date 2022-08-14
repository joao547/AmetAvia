package br.edu.ifpe.tads.ametavia.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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

import br.edu.ifpe.tads.ametavia.R;
import br.edu.ifpe.tads.ametavia.fragments.MapsFragment;
import br.edu.ifpe.tads.ametavia.models.Address;
import br.edu.ifpe.tads.ametavia.models.Ong;

public class RegistrationOngForm extends AppCompatActivity {

    private Button saveButton;
    private EditText edEmail;
    private EditText edPass;
    private EditText edName;
    private EditText edBio;
    private EditText edAddress;
    private FirebaseAuth mAuth;
    private Address newAddress;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro_ong);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Nova ONG");
        actionBar.setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container_map,  new MapsFragment(this), "MapsFragment")
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();

        initComponents();
        mAuth = FirebaseAuth.getInstance();
    }

    private void initComponents() {
        System.out.println("Iniciando componentes");
        saveButton = findViewById(R.id.save_button);
        edEmail = findViewById(R.id.email);
        edPass = findViewById(R.id.password);
        edName = findViewById(R.id.name);
        edBio = findViewById(R.id.bio);
        edAddress = findViewById(R.id.address);

        edAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                   newAddress = ((MapsFragment)fragmentManager.findFragmentByTag("MapsFragment")).foundLatLong(edAddress.getText().toString());
                    System.out.println(newAddress.toString());
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString();
                String password = edPass.getText().toString();
                String name = edName.getText().toString();
                String bio = edBio.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegistrationOngForm.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Ong volunteer = new Ong(name, "", email, bio);
                                    updateUI(user, volunteer);
                                } else {
                                    Toast.makeText(RegistrationOngForm.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void updateUI(FirebaseUser user, Ong ong) {
        DatabaseReference drVolunteer = FirebaseDatabase.getInstance().getReference("ong");
        drVolunteer.setValue(ong);
        Intent intent = new Intent(RegistrationOngForm.this, FormLogin.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}