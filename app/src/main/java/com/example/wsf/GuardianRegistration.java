package com.example.wsf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GuardianRegistration extends AppCompatActivity {
    EditText mgFullName, mgEmail, mgPassword, mgPhone;
    Button mgRegisterBtn;
    FirebaseAuth fAuth;
    TextView mgLoginBtn;
    ProgressBar mgprogressBar;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_registration);

        mgFullName = findViewById(R.id.guardName);
        mgEmail = findViewById(R.id.guardEmail);
        mgPassword = findViewById(R.id.guardPass);
        mgPhone = findViewById(R.id.guardPhone);
        mgRegisterBtn = findViewById(R.id.guardRegBtn);
        mgLoginBtn = findViewById(R.id.guardCreate);

        fAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        mgprogressBar = findViewById(R.id.guardianProgressBar3);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(GuardianRegistration.this, mainGuardianPage.class));
            finish();
        }

        mgRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String gemail = mgEmail.getText().toString().trim();
                String gpassword = mgPassword.getText().toString().trim();
                final String gfullName = mgFullName.getText().toString();
                final String gphone = mgPhone.getText().toString();
                if (TextUtils.isEmpty(gemail)) {
                    mgEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(gpassword)) {
                    mgPassword.setError("Password is required");
                    return;
                }
                if (gpassword.length() < 6) {
                    mgPassword.setError("Password must be 6 char long");
                    return;
                }
                mgprogressBar.setVisibility(View.VISIBLE);

                /* Register the user in firebase */
                fAuth.createUserWithEmailAndPassword(gemail,gpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(GuardianRegistration.this, "Guardian Created Successfully", Toast.LENGTH_SHORT).show();
                            userID= fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference  = fStore.collection("guardians").document(userID);
                            Map<String, Object> guardian = new HashMap<>();

                            guardian.put("fName",gfullName);
                            guardian.put("email",gemail);
                            guardian.put("phone",gphone);
                            documentReference.set(guardian).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
//                                Log.d("TAG"," onSuccess : User Profile created for " + userID);
                                    Log.d("TAG", "onSuccess : Guardian Profile created for " + userID);
                                }
                            });
//                            addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d("TAG", "onFailure:" + e.toString());
//                                }
//
//                            });
                            startActivity(new Intent(GuardianRegistration.this, mainGuardianPage.class));
                        } else {
                            Toast.makeText(GuardianRegistration.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            mgprogressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        mgLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(GuardianRegistration.this,guardianLogin.class);
                startActivity(i1);
            }
        });

    }
}