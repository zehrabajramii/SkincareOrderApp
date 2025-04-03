package com.mk.skincareorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.mk.skincareorder.adapters.SkincareListAdapter;
import com.mk.skincareorder.model.SkincareModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SkincareListAdapter.SkincareListClickListener {
    // Kjo klasë përfaqëson aktivitetin kryesor të aplikacionit, që shfaq një listë të dyqaneve të kujdesit për lëkurën.
    // Ajo implementon ndërfaqen SkincareListAdapter.SkincareListClickListener për të menaxhuar klikimet në artikujt e listës.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Vendos layout-in e aktivitetit duke përdorur skedarin XML 'activity_main'.

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Vendos Toolbar-in si ActionBar-in mbështetës për këtë aktivitet.

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);
            getSupportActionBar().setTitle("");
            // Konfiguron Toolbar-in për të përfshirë një buton kthimi prapa (back) me një ikonë të personalizuar dhe pa titull.
        }

        List<SkincareModel> skincareModelList = getSkincareData();
        // Merr të dhënat e dyqaneve të kujdesit për lëkurën nga një skedar JSON dhe i ruan ato në një listë.

        initRecyclerView(skincareModelList);
        // Inicializon RecyclerView dhe e mbush atë me të dhënat e dyqaneve të kujdesit për lëkurën.
    }

    private void initRecyclerView(List<SkincareModel> skincareModelList) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Vendos një LinearLayoutManager për RecyclerView për të shfaqur artikujt në një listë vertikale.

        SkincareListAdapter adapter = new SkincareListAdapter(skincareModelList, this);
        recyclerView.setAdapter(adapter);
        // Inicializon dhe vendos adapterin për RecyclerView, duke kaluar listën e të dhënave dhe dëgjuesin e klikimeve.
    }

    private List<SkincareModel> getSkincareData() {
        InputStream is = getResources().openRawResource(R.raw.skincares);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        // Hap një burim të skedarit JSON që përmban të dhënat e dyqaneve dhe përgatit një buffer për leximin e tij.

        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            // Lexon skedarin JSON dhe e shkruan përmbajtjen e tij në një varg (String) duke përdorur një buffer.

        } catch (Exception e) {
            e.printStackTrace();
            // Kap dhe shtyp çdo përjashtim që ndodh gjatë leximit të skedarit.

        }

        String jsonStr = writer.toString();
        // Konverton përmbajtjen e skedarit në një varg (String).

        Gson gson = new Gson();
        SkincareModel[] skincareModels = gson.fromJson(jsonStr, SkincareModel[].class);
        // Përdor bibliotekën Gson për të deserializuar vargun JSON në një array të objekteve SkincareModel.

        return Arrays.asList(skincareModels);
        // Kthen array-në e deserializuar si një listë për përdorim të mëtejshëm në aktivitet.
    }

    @Override
    public void onItemClick(SkincareModel skincareModel) {
        Intent intent = new Intent(MainActivity.this, SkincareMenu.class);
        intent.putExtra("SkincareModel", skincareModel);
        startActivity(intent);
        // Ky metodë thirret kur përdoruesi klikon mbi një artikull në listë.
        // Nis aktivitetin SkincareMenu dhe i kalon objektin SkincareModel përkatës përmes një Intenti.
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Kontrollon nëse përdoruesi ka klikuar butonin e kthimit prapa (back) në Toolbar.

            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
            // Krijon dhe nis një Intent për të kthyer përdoruesin në aktivitetin HomeActivity dhe mbyll aktivitetin aktual.
            return true;
        }
        return super.onOptionsItemSelected(item);
        // Nëse artikulli nuk është butoni i kthimit prapa, menaxhohet nga superklasa.
    }
}
