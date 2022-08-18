package br.edu.ifpe.tads.ametavia.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.edu.ifpe.tads.ametavia.R;
import br.edu.ifpe.tads.ametavia.fragments.MapsFragment;
import br.edu.ifpe.tads.ametavia.models.Address;
import br.edu.ifpe.tads.ametavia.models.Ong;

public class RegistrationOngForm extends AppCompatActivity {

    private Button saveButton;
    private Button uploadButton;
    private EditText edEmail;
    private EditText edPass;
    private EditText edName;
    private EditText edBio;
    private EditText edAddress;
    private FirebaseAuth mAuth;
    private Address newAddress;
    Uri imageuri = null;

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
        uploadButton = findViewById(R.id.upload_image);
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
                                    Ong ong = new Ong(name, newAddress, email, bio, "05198569859");
                                    updateUI(user, ong);
                                } else {
                                    Toast.makeText(RegistrationOngForm.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                // We will be redirected to choose pdf
                galleryIntent.setType("image/jpg");
                startActivityForResult(galleryIntent, 1);
            }
        });
    }

    ProgressDialog dialog;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            // Here we are initialising the progress dialog box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");

            // this will show message uploading
            // while pdf is uploading
            dialog.show();
            imageuri = data.getData();
            Toast.makeText(RegistrationOngForm.this, imageuri.toString(), Toast.LENGTH_SHORT).show();

        }
    }

    private ArrayList<String> uploadImage(FirebaseUser user){
        final String[] myurl = {""};
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        final StorageReference filepath = storageReference.child(user.getUid() + "." + "jpg");
        Toast.makeText(RegistrationOngForm.this, filepath.getName(), Toast.LENGTH_SHORT).show();
        filepath.putFile(imageuri).continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filepath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Uri uri = task.getResult();
                    myurl[0] = uri.toString();
                    Toast.makeText(RegistrationOngForm.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(RegistrationOngForm.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ArrayList<String> listUri = new ArrayList<String>();
        Collections.addAll(listUri, myurl);
        return listUri;
    }

    private void updateUI(FirebaseUser user, Ong ong) {
        ong.setImages(uploadImage(user));
        DatabaseReference drOng = FirebaseDatabase.getInstance().getReference("ong");
        drOng.child(user.getUid()).setValue(ong);
        Intent intent = new Intent(RegistrationOngForm.this, FormLogin.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}