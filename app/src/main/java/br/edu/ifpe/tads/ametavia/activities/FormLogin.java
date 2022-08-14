package br.edu.ifpe.tads.ametavia.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.edu.ifpe.tads.ametavia.R;

public class FormLogin extends AppCompatActivity {

    private Button newLogin;
    private Button newOng;
    private Button newVolunteer;
    private EditText edEmail;
    private EditText edPass;
    private FirebaseAuth mAuth;


    final private int FINE_LOCATION_REQUEST = 3;
    private boolean fine_location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);



        mAuth = FirebaseAuth.getInstance();

        mAuth.signOut();

        initiateComponents();
        getSupportActionBar().hide();
    }

    private void initiateComponents(){
        newLogin = findViewById(R.id.login_button);
        newOng = findViewById(R.id.new_ong_button);
        newVolunteer = findViewById(R.id.new_volunteer_button);
        edEmail = findViewById(R.id.email);
        edPass = findViewById(R.id.password);

        newLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString();
                String password = edPass.getText().toString();

//                mAuth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(FormLogin.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    updateUI(user);
//                                } else {
//                                    Toast.makeText(FormLogin.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
                Intent intent = new Intent(FormLogin.this, Dashboard.class);
                startActivity(intent);
            }
        });

        newOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormLogin.this, RegistrationOngForm.class);
                startActivity(intent);
            }
        });

        newVolunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FormLogin.this, RegistrationVolunteerForm.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser);
        }
    }

    private void updateUI(FirebaseUser user) {
        Log.d("userInfo", user.getEmail());
        Intent intent = new Intent(FormLogin.this, Dashboard.class);
        startActivity(intent);
    }


}