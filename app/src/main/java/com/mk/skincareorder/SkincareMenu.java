package com.mk.skincareorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mk.skincareorder.adapters.MenuListAdapter;
import com.mk.skincareorder.model.Menus;
import com.mk.skincareorder.model.SkincareModel;

import java.util.ArrayList;
import java.util.List;

public class SkincareMenu extends AppCompatActivity implements MenuListAdapter.MenuListClickListener {
    // Kjo klasë përfaqëson një aktivitet që shfaq një menu të produkteve të kujdesit për lëkurën.
    // Ajo zbaton ndërfaqen MenuListAdapter.MenuListClickListener për të menaxhuar klikimet në produktet e listës.

    private List<Menus> menuList = null;
    // Lista që përmban të gjitha objektet Menus që do të shfaqen në këtë aktivitet.

    private MenuListAdapter menuListAdapter;
    // Adapter për menaxhimin e RecyclerView që shfaq menunë e produkteve.

    private List<Menus> itemsInCartList = new ArrayList<>();
    // Lista që mban të gjitha produktet që përdoruesi ka shtuar në karrocë.

    private int totalItemInCart = 0;
    // Numri total i artikujve që janë shtuar në karrocë.

    private TextView buttonCheckout;
    // Një referencë për butonin e Checkout që shfaq numrin total të artikujve në karrocë.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skincare_menu);
        // Vendos layout-in për këtë aktivitet duke përdorur skedarin 'activity_skincare_menu.xml'.

        // Përcakton toolbar-in si ActionBar për këtë aktivitet.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SkincareModel skincareModel = getIntent().getParcelableExtra("SkincareModel");
        // Merr objektin SkincareModel të dërguar nga aktiviteti i mëparshëm përmes një Intenti.

        if (skincareModel != null) {
            // Kontrollon nëse objekti SkincareModel është i vlefshëm.

            if (getSupportActionBar() != null) {
                // Vendos titullin e toolbar-it dhe shfaq butonin e kthimit prapa (back) me një ikonë të personalizuar.
                getSupportActionBar().setTitle(skincareModel.getName());
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);  // Ikona e personalizuar për kthimin prapa.
            }

            menuList = skincareModel.getMenus();
            // Merr listën e menues nga objekti SkincareModel.

            initRecyclerView();
            // Inicjalizon RecyclerView që shfaq menunë e produkteve.

            buttonCheckout = findViewById(R.id.buttonCheckout);
            // Lidh butonin e Checkout me kodin Java.

            buttonCheckout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Vendos një dëgjues për klikimet mbi butonin e Checkout.

                    if (itemsInCartList.isEmpty()) {
                        // Kontrollon nëse karroca është bosh.
                        Toast.makeText(SkincareMenu.this, "Please add some items in cart.", Toast.LENGTH_SHORT).show();
                        // Shfaq një mesazh që tregon se duhet të shtohen artikuj në karrocë përpara se të vazhdohet.
                        return;
                    }

                    skincareModel.setMenus(itemsInCartList);
                    // Përditëson listën e menues në objektin SkincareModel me artikujt e shtuar në karrocë.

                    Intent i = new Intent(SkincareMenu.this, PlaceOrder.class);
                    // Krijon një Intent për të nisur aktivitetin PlaceOrder.

                    i.putExtra("SkincareModel", skincareModel);
                    // Dërgon objektin SkincareModel në aktivitetin e ri.

                    startActivityForResult(i, 1000);
                    // Nis aktivitetin PlaceOrder dhe pret një rezultat nga ai.
                }
            });
        }
    }

    private void initRecyclerView() {
        // Metoda që inicializon RecyclerView për shfaqjen e menues.

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        // Gjen RecyclerView në layout.

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        // Vendos një GridLayoutManager për të shfaqur menunë në formë gridi me 2 kolona.

        menuListAdapter = new MenuListAdapter(menuList, this);
        // Inicializon adapterin me listën e menues dhe dëgjuesin e klikimeve.

        recyclerView.setAdapter(menuListAdapter);
        // Vendos adapterin në RecyclerView.
    }

    @Override
    public void onAddToCartClick(Menus menus) {
        // Ky metodë thirret kur një artikull shtohet në karrocë.

        if (!itemsInCartList.contains(menus)) {
            // Kontrollon nëse artikulli nuk është tashmë në karrocë.
            itemsInCartList.add(menus);
            // Shton artikullin në listën e artikujve në karrocë.
        }
        updateCartTotal();
        // Përditëson numrin total të artikujve në karrocë dhe tekstin e butonit të Checkout.
    }

    @Override
    public void onUpdateCartClick(Menus menus) {
        // Ky metodë thirret kur një artikull në karrocë përditësohet (p.sh., sasia ndryshohet).

        int index = itemsInCartList.indexOf(menus);
        // Gjen indeksin e artikullit në listën e karrocës.

        if (index != -1) {
            // Nëse artikulli ekziston në karrocë, përditëson artikullin me vlerat e reja.
            itemsInCartList.set(index, menus);
        }
        updateCartTotal();
        // Përditëson numrin total të artikujve në karrocë dhe tekstin e butonit të Checkout.
    }

    @Override
    public void onRemoveFromCartClick(Menus menus) {
        // Ky metodë thirret kur një artikull hiqet nga karroca.

        itemsInCartList.remove(menus);
        // Heq artikullin nga lista e karrocës.

        updateCartTotal();
        // Përditëson numrin total të artikujve në karrocë dhe tekstin e butonit të Checkout.
    }

    private void updateCartTotal() {
        // Përditëson numrin total të artikujve në karrocë dhe përditëson tekstin e butonit të Checkout.

        totalItemInCart = 0;
        // Inicializon numrin total të artikujve në karrocë me 0.

        for (Menus m : itemsInCartList) {
            // Kalon nëpër çdo artikull në karrocë dhe mbledh sasinë e secilit.
            totalItemInCart += m.getTotalinTheCart();
        }

        buttonCheckout.setText("Checkout (" + totalItemInCart + ") items");
        // Vendos tekstin e butonit të Checkout për të treguar numrin total të artikujve në karrocë.
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Menaxhon rezultatin e kthyer nga aktivitetet që janë nisur nga ky aktivitet.

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            // Kontrollon nëse rezultati është kthyer nga aktiviteti PlaceOrder me sukses.
            finish();
            // Mbyll aktivitetin aktual dhe kthehet në aktivitetin e mëparshëm.
        }
    }
}
