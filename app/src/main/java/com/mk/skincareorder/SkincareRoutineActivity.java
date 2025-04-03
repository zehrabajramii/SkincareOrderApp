package com.mk.skincareorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;

public class SkincareRoutineActivity extends AppCompatActivity {
    // Kjo klasë përfaqëson një aktivitet që shfaq një rutinë të kujdesit për lëkurën dhe menaxhon veprimet e toolbar-it.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skincare_routine);
        // Vendos layout-in për këtë aktivitet duke përdorur skedarin 'activity_skincare_routine.xml'.

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Gjen Toolbar-in në layout dhe e vendos atë si toolbar-in mbështetës të këtij aktiviteti.

        if (getSupportActionBar() != null) {
            // Kontrollon nëse ActionBar është i disponueshëm.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // Shfaq një buton për kthim prapa (back) në toolbar.
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);
            // Vendos një ikonë të personalizuar për butonin e kthimit prapa (back).
            getSupportActionBar().setTitle("");
            // E lë të zbrazët titullin e toolbar-it për këtë aktivitet.
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Menaxhon klikimet në artikujt e menysë të toolbar-it.
        if (item.getItemId() == android.R.id.home) {
            // Kontrollon nëse përdoruesi ka klikuar butonin e kthimit prapa (back).
            finish();
            // Mbyll aktivitetin dhe kthehet në aktivitetin e mëparshëm.
            return true;
        }
        return super.onOptionsItemSelected(item);
        // Nëse artikulli nuk është butoni i kthimit prapa, menaxhohet nga superklasa.
    }
}
