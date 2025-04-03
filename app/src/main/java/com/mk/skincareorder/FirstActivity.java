package com.mk.skincareorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class FirstActivity extends AppCompatActivity {
    // Kjo klasë përfaqëson aktivitetin e parë (splash screen) që shfaqet kur hapet aplikacioni.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen); // Përdor layout-in e splash screen-it.

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        // Fshin ActionBar-in për këtë aktivitet për të krijuar një përvojë më të pastër gjatë splash screen-it.

        // Vonesë prej 2 sekondash përpara se të kalojë në LoginActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(FirstActivity.this, LoginActivity.class)); // Kalon te LoginActivity
                finish(); // Mbyll aktivitetin e splash screen-it
            }
        }, 2000); // 2 sekonda vonesë
    }
}

