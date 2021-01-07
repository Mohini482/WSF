package com.example.wsf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FistAidMainPage extends AppCompatActivity {
       Button b1,b2,b3,b4,b5,b6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fist_aid_main_page);

           b1=(Button)findViewById(R.id.burn);
           b2=(Button)findViewById(R.id.bleeding);
           b3=(Button)findViewById(R.id.choking);
           b4=(Button)findViewById(R.id.diabetes);
           b5=(Button)findViewById(R.id.heart);
           b6=(Button)findViewById(R.id.unconscious);
    }

    public void choke(View view) {
        Intent intent1 = new Intent(FistAidMainPage.this, Choking.class);
        startActivity(intent1);
    }

    public void diab(View view) {
        Intent intent = new Intent(FistAidMainPage.this, Diabetes.class);
        startActivity(intent);
    }

    public void heart(View view) {
        Intent intent1 = new Intent(FistAidMainPage.this, Heart.class);
        startActivity(intent1);
    }

    public void uncon(View view) {
        Intent intent2 = new Intent(FistAidMainPage.this, Unconscious.class);
        startActivity(intent2);
    }

    public void burn(View view) {
        Intent intent3 = new Intent(FistAidMainPage.this, Burn.class);
        startActivity(intent3);
    }

    public void bleed(View view) {
        Intent intent4 = new Intent(FistAidMainPage.this, Bleeding.class);
        startActivity(intent4);
    }
}