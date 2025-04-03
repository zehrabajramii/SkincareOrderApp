package com.mk.skincareorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;

public class HomePageActivity extends AppCompatActivity {
    // Kjo klasë përfaqëson aktivitetin HomePage në aplikacion, që është përgjegjës për shfaqjen e ekranit kryesor.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        // Vendos layout-in e aktivitetit duke përdorur skedarin XML 'activity_home_page'.

        // Vendos Toolbar-in si ActionBar-in mbështetës për këtë aktivitet.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Personalizon Toolbar-in
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  // Aktivizon shigjetën për kthim prapa (back).
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);  // Vendos një ikonë të personalizuar për shigjetën e kthimit prapa.
            getSupportActionBar().setTitle("");  // Opsionalisht heq titullin e Toolbar-it.
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Menaxhon klikimet mbi shigjetën për kthim prapa.
            onBackPressed();  // Kthehet në aktivitetin e mëparshëm.
            return true;
        }
        return super.onOptionsItemSelected(item);
        // Nëse artikulli nuk është butoni i kthimit prapa, menaxhohet nga superklasa.
    }
}
