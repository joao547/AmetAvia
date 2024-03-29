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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.ifpe.tads.ametavia.R;
import br.edu.ifpe.tads.ametavia.fragments.MapsFragment;
import br.edu.ifpe.tads.ametavia.models.Address;
import br.edu.ifpe.tads.ametavia.models.Volunteer;

public class RegistrationVolunteerForm extends AppCompatActivity {

    private Button saveButton;
    private EditText edEmail;
    private EditText edPass;
    private EditText edName;
    private EditText edBirthDate;
    private EditText edBio;
    private EditText edGender;
    private EditText edAddress;
    private Address newAddress;
    private FirebaseAuth mAuth;
    private final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro_vol);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Novo voluntário");
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

    private void initComponents(){
        saveButton = findViewById(R.id.save_button);
        edEmail = findViewById(R.id.email);
        edPass = findViewById(R.id.password);
        edName = findViewById(R.id.name);
        edBio = findViewById(R.id.bio);
        edBirthDate = findViewById(R.id.birthDate);
        edGender = findViewById(R.id.gender);
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
                String gender = edGender.getText().toString();
                String birthDateString = edBirthDate.getText().toString();
                Date birthDate = null;
                try {
                    birthDate = formatter.parse(birthDateString);
                } catch (ParseException e) {
                    Toast.makeText(RegistrationVolunteerForm.this, "Create Volunteer failed.",
                            Toast.LENGTH_SHORT).show();
                }
                Date finalBirthDate = birthDate;
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(RegistrationVolunteerForm.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Volunteer volunteer = new Volunteer(email, name, finalBirthDate, gender, newAddress, bio);
                                    updateUI(user,volunteer);
                                } else {
                                    Toast.makeText(RegistrationVolunteerForm.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void updateUI(FirebaseUser user, Volunteer volunteer) {
        DatabaseReference drVolunteer = FirebaseDatabase.getInstance().getReference("volunteer");
        drVolunteer.child(user.getUid()).setValue(volunteer);
        Intent intent = new Intent(RegistrationVolunteerForm.this, FormLogin.class);
        startActivity(intent);
    }
}