package com.mk.skincareorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageView homeImage = findViewById(R.id.homeImage);
        ImageView skincareRoutineImage = findViewById(R.id.skincareRoutineImage);
        ImageView skincareShopsImage = findViewById(R.id.skincareShopsImage);

        // Home Page Click Listener
        homeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HomePageActivity.class);  // Replace with your Home Page Activity
                startActivity(intent);
            }
        });

        // Skincare Routine Guide Click Listener
        skincareRoutineImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SkincareRoutineActivity.class);  // Replace with your Skincare Routine Activity
                startActivity(intent);
            }
        });

        // Skincare Shops Click Listener
        skincareShopsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);  // Replace with your Skincare Shops Activity
                startActivity(intent);
            }
        });
    }
}
