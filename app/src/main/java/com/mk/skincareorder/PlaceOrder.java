package com.mk.skincareorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mk.skincareorder.adapters.PlaceOrderAdapter;
import com.mk.skincareorder.DatabaseHelper;
import com.mk.skincareorder.model.Menus;
import com.mk.skincareorder.model.SkincareModel;

import java.util.Locale;

public class PlaceOrder extends AppCompatActivity {
    // Kjo klasë përfaqëson një aktivitet në Android që lejon përdoruesit të përfundojnë një porosi dhe të paguajnë për të.

    private EditText inputName, inputAddress, inputCity, inputState, inputZip, inputCardNumber, inputCardExpiry, inputCardPin;
    // Deklaron EditText për marrjen e informacionit nga përdoruesi për emrin, adresën, qytetin, shtetin, ZIP-in, numrin e kartës, skadencën dhe PIN-in.

    private RecyclerView cartItemsRecyclerView;
    // RecyclerView që shfaq artikujt e shtuar në karrocë nga përdoruesi.

    private TextView btnPlaceOrder;
    // Buton që përdoruesi klikon për të përfunduar porosinë.

    private SwitchCompat switchDelivery;
    // Switch për të zgjedhur nëse porosia do të dorëzohet ose jo.

    private boolean isDeliveryOn;
    // Variabël që ruan gjendjen e switch-it të dorëzimit.

    private PlaceOrderAdapter placeOrderAdapter;
    // Adapter për menaxhimin e shfaqjes së artikujve të karrocës në RecyclerView.

    private ActivityResultLauncher<Intent> orderSucceededLauncher;
    // Launcher për nisjen e një aktiviteti që pret një rezultat (p.sh., konfirmimi i porosisë).

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        // Vendos layout-in e aktivitetit duke përdorur skedarin XML 'activity_place_order'.

        SkincareModel skincareModel = getIntent().getParcelableExtra("SkincareModel");
        // Merr objektin SkincareModel të dërguar nga aktiviteti i mëparshëm përmes një Intenti.

        if (skincareModel == null) {
            finish();
            return;
        }
        // Kontrollon nëse SkincareModel është null dhe mbyll aktivitetin nëse nuk ka të dhëna të vlefshme.

        // Lidh të gjitha EditText dhe komponentët e tjerë të UI me kodin Java.
        inputName = findViewById(R.id.inputName);
        inputAddress = findViewById(R.id.inputAddress);
        inputCity = findViewById(R.id.inputCity);
        inputState = findViewById(R.id.inputState);
        inputZip = findViewById(R.id.inputZip);
        inputCardNumber = findViewById(R.id.inputCardNumber);
        inputCardExpiry = findViewById(R.id.inputCardExpiry);
        inputCardPin = findViewById(R.id.inputCardPin);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        switchDelivery = findViewById(R.id.switchDelivery);
        cartItemsRecyclerView = findViewById(R.id.cartItemsRecycler);

        orderSucceededLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                }
        );
        // Regjistron një ActivityResultLauncher për të menaxhuar rezultatin e kthyer nga aktiviteti që konfirmon porosinë.

        btnPlaceOrder.setOnClickListener(v -> onPlaceOrderButtonClick(skincareModel));
        // Vendos një dëgjues për klikimet mbi butonin e vendosjes së porosisë.

        switchDelivery.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int visibility = isChecked ? View.VISIBLE : View.GONE;
            inputAddress.setVisibility(visibility);
            inputCity.setVisibility(visibility);
            inputState.setVisibility(visibility);
            inputZip.setVisibility(visibility);
            findViewById(R.id.DeliverychargeAmount).setVisibility(visibility);
            findViewById(R.id.Deliverycharge).setVisibility(visibility);
            isDeliveryOn = isChecked;
            calculateTotalAmount(skincareModel);
        });
        // Vendos një dëgjues për ndryshimin e gjendjes së switch-it të dorëzimit dhe përditëson UI në përputhje me zgjedhjen e përdoruesit.

        initRecyclerView(skincareModel);
        // Inicjalizon RecyclerView që shfaq artikujt e karrocës.

        calculateTotalAmount(skincareModel);
        // Llogarit dhe shfaq shumën totale të porosisë.

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
        // Shton një callback për të menaxhuar butonin e kthimit prapa të pajisjes dhe për të kthyer një rezultat të anuluar nëse përdoruesi largohet nga aktiviteti.
    }

    private float calculateTotalAmount(SkincareModel skincareModel) {
        float subTotalAmount = 0f;
        // Inicializon shumën nën-total të porosisë me 0.

        for (Menus m : skincareModel.getMenus()) {
            subTotalAmount += m.getPrice() * m.getTotalinTheCart();
        }
        // Kalon nëpër të gjitha artikujt e karrocës dhe llogarit shumën nën-total të porosisë.

        TextView subtotalAmount = findViewById(R.id.subtotalAmount);
        TextView DeliverychargeAmount = findViewById(R.id.DeliverychargeAmount);
        TextView TotalAmount = findViewById(R.id.TotalAmount);
        // Lidh komponentët e UI që shfaqin shumën nën-total, tarifën e dorëzimit dhe shumën totale me kodin Java.

        subtotalAmount.setText(String.format(Locale.getDefault(), "$%.2f", subTotalAmount));
        // Shfaq shumën nën-total në UI.

        if (isDeliveryOn) {
            DeliverychargeAmount.setText(String.format(Locale.getDefault(), "$%.2f", skincareModel.getDelivery_charge()));
            subTotalAmount += skincareModel.getDelivery_charge();
        }
        // Nëse dorëzimi është aktiv, shton tarifën e dorëzimit në shumën nën-total dhe e shfaq atë në UI.

        TotalAmount.setText(String.format(Locale.getDefault(), "$%.2f", subTotalAmount));
        // Shfaq shumën totale në UI.

        return subTotalAmount;
        // Kthen shumën totale për përdorim të mëtejshëm.
    }

    private void onPlaceOrderButtonClick(SkincareModel skincareModel) {
        Log.d("PlaceOrder", "Place order button clicked.");
        // Logon një mesazh për të treguar se butoni i vendosjes së porosisë është klikuar.

        if (TextUtils.isEmpty(inputName.getText().toString())) {
            Log.e("PlaceOrder", "Name validation failed.");
            inputName.setError("Please enter name ");
            return;
        } else if (isDeliveryOn && TextUtils.isEmpty(inputAddress.getText().toString())) {
            Log.e("PlaceOrder", "Address validation failed.");
            inputAddress.setError("Please enter address ");
            return;
        } else if (isDeliveryOn && TextUtils.isEmpty(inputCity.getText().toString())) {
            Log.e("PlaceOrder", "City validation failed.");
            inputCity.setError("Please enter city ");
            return;
        } else if (isDeliveryOn && TextUtils.isEmpty(inputState.getText().toString())) {
            Log.e("PlaceOrder", "State validation failed.");
            inputState.setError("Please enter state ");
            return;
        } else if (isDeliveryOn && TextUtils.isEmpty(inputZip.getText().toString())) {
            Log.e("PlaceOrder", "ZIP validation failed.");
            inputZip.setError("Please enter zip ");
            return;
        } else if (TextUtils.isEmpty(inputCardNumber.getText().toString())) {
            Log.e("PlaceOrder", "Card number validation failed.");
            inputCardNumber.setError("Please enter card number ");
            return;
        } else if (TextUtils.isEmpty(inputCardExpiry.getText().toString())) {
            Log.e("PlaceOrder", "Card expiry validation failed.");
            inputCardExpiry.setError("Please enter card expiry ");
            return;
        } else if (TextUtils.isEmpty(inputCardPin.getText().toString())) {
            Log.e("PlaceOrder", "Card pin validation failed.");
            inputCardPin.setError("Please enter card pin/cvv ");
            return;
        }
        // Validon të gjitha fushat e nevojshme të formularit dhe shfaq gabime nëse ndonjëra është bosh.

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        float totalAmount = calculateTotalAmount(skincareModel);
        long orderId = dbHelper.addOrder(skincareModel.getName(), totalAmount);
        // Shton porosinë në bazën e të dhënave dhe merr ID-në e porosisë së sapo shtuar.

        Log.d("PlaceOrder", "Order saved with ID: " + orderId);
        // Logon një mesazh për të treguar se porosia u ruajt me sukses dhe tregon ID-në e saj.

        Intent i = new Intent(PlaceOrder.this, orderSucceeeded.class);
        i.putExtra("SkincareModel", skincareModel);
        startActivity(i);
        // Nis aktivitetin 'orderSucceeeded' për të treguar që porosia u vendos me sukses.
    }

    private void initRecyclerView(SkincareModel skincareModel) {
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Vendos një LayoutManager linear për RecyclerView.

        placeOrderAdapter = new PlaceOrderAdapter(skincareModel.getMenus());
        cartItemsRecyclerView.setAdapter(placeOrderAdapter);
        // Inicializon dhe vendos adapterin për shfaqjen e artikujve në karrocë në RecyclerView.
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
        // Menaxhon klikimet në butonin e kthimit prapa në Toolbar dhe mbyll aktivitetin nëse ky buton shtypet.
    }
}
