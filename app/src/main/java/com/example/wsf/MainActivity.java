package com.example.wsf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button b1, b2, b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.user_btn);
        b2 = (Button) findViewById(R.id.guardian_btn);
        b3 = (Button) findViewById(R.id.admin_btn);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_user = new Intent(MainActivity.this, UserRegistration.class);
                startActivity(i_user);


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_guardian = new Intent(MainActivity.this, GuardianRegistration.class);
                startActivity(i_guardian);

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_admin = new Intent(MainActivity.this, AdminRegistration.class);
                startActivity(i_admin);

            }
        });
    }
}