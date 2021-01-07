package com.example.wsf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class guardianLogin extends AppCompatActivity {
    EditText mgEmail ,mgPassword;
    Button mgLoginBtn;
    ProgressBar mgprogressBar;
    TextView mgCreateBtn,mgforgotTextLink;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_login);

        mgEmail= findViewById(R.id.guardianEmail);
        mgPassword=findViewById(R.id.guardianName);
        mgprogressBar =findViewById((R.id.progressBar2));
        fAuth=FirebaseAuth.getInstance();
        mgLoginBtn=findViewById(R.id.guardianLoginBtn);
        mgCreateBtn=findViewById(R.id.guardianCreate);
        mgforgotTextLink= findViewById(R.id.guardianForgot);

        mgLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gemail = mgEmail.getText().toString().trim();
                String gpassword= mgPassword.getText().toString().trim();

                if(TextUtils.isEmpty(gemail)){
                    mgEmail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(gpassword)){
                    mgPassword.setError("Password is required");
                    return;
                }

                if(gpassword.length()<6){
                    mgPassword.setError("Password must be 6 char long");
                }
                mgprogressBar.setVisibility(View.VISIBLE);

                /* Authenticate the user */

                fAuth.signInWithEmailAndPassword(gemail,gpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(guardianLogin.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),mainGuardianPage.class));
                        }
                        else{
                            Toast.makeText(guardianLogin.this,"Error!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            mgprogressBar.setVisibility(View.GONE);
                        }
                    }
                });


            }
        });

        mgCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),GuardianRegistration.class));
            }
        });

        mgforgotTextLink.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                final EditText resetMail= new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter Your Email to Received the Reset Link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Extract the email and send the reset link
                        String mail= resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(guardianLogin.this,"Reset Link Sent to the Email",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(guardianLogin.this,"Error! Reset Link is not Sent" + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Else close the dialog
                    }
                });
                passwordResetDialog.create().show();
            }
        });



    }
}
