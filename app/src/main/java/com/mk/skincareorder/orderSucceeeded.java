package com.mk.skincareorder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mk.skincareorder.model.SkincareModel;

public class orderSucceeeded extends AppCompatActivity {
    // Kjo klasë përfaqëson një aktivitet që shfaq një ekran suksesi pasi një porosi është përfunduar me sukses.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_succeeeded);
        // Vendos layout-in e aktivitetit duke përdorur skedarin XML 'activity_order_succeeeded'.

        Log.d("orderSucceeeded", "Order Succeeded activity started.");
        // Logon një mesazh që tregon se aktiviteti i suksesshëm i porosisë është nisur.

        // Butoni për të vazhduar me porosi të reja
        TextView buttonDone = findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(view -> {
            Log.d("orderSucceeeded", "Continue Ordering button clicked. Returning to MainActivity.");
            // Logon një mesazh që tregon se butoni "Continue Ordering" u klikua dhe se po kthehet në MainActivity.

            Intent intent = new Intent(orderSucceeeded.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            // Krijon një Intent për të nisur MainActivity, duke pastruar të gjitha aktivitetet e mëparshme.

            startActivity(intent);
            // Nis MainActivity.

            finish();
            // Mbyll aktivitetin aktual për të parandaluar kthimin prapa.
        });

        // Butoni për të parë porositë e tua
        TextView buttonYourOrders = findViewById(R.id.buttonYourOrders);
        buttonYourOrders.setOnClickListener(view -> {
            Log.d("orderSucceeeded", "Your Orders button clicked. Navigating to YourOrdersActivity.");
            // Logon një mesazh që tregon se butoni "Your Orders" u klikua dhe se po navigohet te YourOrdersActivity.

            Intent intent = new Intent(orderSucceeeded.this, YourOrdersActivity.class);
            // Krijon një Intent për të nisur YourOrdersActivity.

            startActivity(intent);
            // Nis YourOrdersActivity.
        });
    }
}
