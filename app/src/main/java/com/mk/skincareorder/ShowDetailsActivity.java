package com.mk.skincareorder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ShowDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        // Vendos layout-in e aktivitetit duke përdorur skedarin XML 'activity_show_details'.

        // Vendos toolbar-in me një shigjetë kthimi prapa.
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Gjen Toolbar-in në layout-in e aktivitetit me ID 'toolbar'.

        setSupportActionBar(toolbar);
        // Vendos Toolbar-in si ActionBar-in mbështetës për këtë aktivitet.

        if (getSupportActionBar() != null) {
            // Kontrollon nëse ActionBar është inicializuar me sukses.

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // Shfaq një buton kthimi prapa në ActionBar për navigim.

            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);
            // Vendos një ikonë të personalizuar për butonin e kthimit prapa duke përdorur 'ic_back_arrow'.

            getSupportActionBar().setTitle("Product Details");
            // Vendos titullin e Toolbar-it si "Product Details".
        }

        // Merr Intent-in që ka nisur këtë aktivitet dhe nxjerr detajet e produktit.
        Intent intent = getIntent();
        // Merr Intent-in hyrës që ka nisur këtë aktivitet.

        String productName = intent.getStringExtra("PRODUCT_NAME");
        // Nxjerr emrin e produktit nga Intent-i duke përdorur çelësin "PRODUCT_NAME".

        String productPrice = intent.getStringExtra("PRODUCT_PRICE");
        // Nxjerr çmimin e produktit nga Intent-i duke përdorur çelësin "PRODUCT_PRICE".

        int productImage = intent.getIntExtra("PRODUCT_IMAGE", R.drawable.default_image);
        // Nxjerr ID-në e imazhit të produktit nga Intent-i duke përdorur çelësin "PRODUCT_IMAGE".
        // Nëse nuk gjendet, përdor 'R.drawable.default_image' si vlerë të paracaktuar.

        String productDescription = intent.getStringExtra("PRODUCT_DESCRIPTION");
        // Nxjerr përshkrimin e produktit nga Intent-i duke përdorur çelësin "PRODUCT_DESCRIPTION".

        // Kap komponentët e layout-it si TextView dhe ImageView dhe vendos detajet e produktit.
        TextView nameTextView = findViewById(R.id.product_name);
        // Gjen TextView për emrin e produktit në layout me ID 'product_name'.

        TextView priceTextView = findViewById(R.id.product_price);
        // Gjen TextView për çmimin e produktit në layout me ID 'product_price'.

        TextView descriptionTextView = findViewById(R.id.product_description);
        // Gjen TextView për përshkrimin e produktit në layout me ID 'product_description'.

        ImageView productImageView = findViewById(R.id.product_image);
        // Gjen ImageView për imazhin e produktit në layout me ID 'product_image'.

        nameTextView.setText(productName);
        // Vendos tekstin e emrit të produktit në TextView përkatës.

        priceTextView.setText("$" + productPrice);
        // Vendos tekstin e çmimit të produktit në TextView, duke shtuar simbolin '$' për formatim.

        descriptionTextView.setText(productDescription);
        // Vendos tekstin e përshkrimit të produktit në TextView përkatës.

        productImageView.setImageResource(productImage);
        // Vendos imazhin e produktit në ImageView duke përdorur ID-në e burimit të imazhit.
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        // Mbyll aktivitetin aktual dhe kthehet në aktivitetin e mëparshëm kur shtypet butoni i kthimit prapa.

        return true;
        // Kthen true për të treguar se veprimi i navigimit u trajtua me sukses.
    }
}
