package com.mk.skincareorder;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class YourOrdersActivity extends AppCompatActivity {
    // Kjo klasë përfaqëson një aktivitet që shfaq porositë e përdoruesit nga një bazë të dhënash.

    private DatabaseHelper databaseHelper;
    // Një instancë e klasës DatabaseHelper që ndihmon për të ndërvepruar me bazën e të dhënave.

    private TextView ordersListTextView;
    // Një referencë për TextView që do të shfaqë listën e porosive të përdoruesit.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_orders);
        // Vendos layout-in për këtë aktivitet duke përdorur skedarin 'activity_your_orders.xml'.

        ordersListTextView = findViewById(R.id.ordersListTextView);
        // Lidh TextView në layout me kodin Java për të shfaqur porositë e përdoruesit.

        databaseHelper = new DatabaseHelper(this);
        // Inicializon instancën e DatabaseHelper për të menaxhuar operacionet e bazës së të dhënave.

        displayOrders();
        // Thërret metodën për të shfaqur porositë e përdoruesit.
    }

    private void displayOrders() {
        // Metoda që merr dhe shfaq të gjitha porositë e ruajtura në bazën e të dhënave.

        Cursor cursor = databaseHelper.getAllOrders();
        // Merr një Cursor që përmban të gjitha rreshtat nga tabela e porosive në bazën e të dhënave.

        if (cursor.getCount() == 0) {
            ordersListTextView.setText("No orders found");
            // Nëse nuk ka porosi të ruajtura, shfaq mesazhin "No orders found".
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        // StringBuilder përdoret për të ndërtuar një varg teksti që përmban detajet e të gjitha porosive.

        while (cursor.moveToNext()) {
            // Kalon nëpër çdo rresht në Cursor dhe merr të dhënat e nevojshme për secilën porosi.

            int idIndex = cursor.getColumnIndex("_id");
            // Merr indeksin e kolonës për ID-në e porosisë.

            int nameIndex = cursor.getColumnIndex("order_name");
            // Merr indeksin e kolonës për emrin e porosisë.

            int totalIndex = cursor.getColumnIndex("total_amount");
            // Merr indeksin e kolonës për shumën totale të porosisë.

            if (idIndex >= 0 && nameIndex >= 0 && totalIndex >= 0) {
                // Kontrollon nëse të gjitha kolonat ekzistojnë në bazën e të dhënave.

                stringBuilder.append("Order ID: ")
                        .append(cursor.getInt(idIndex))
                        .append("\nOrder Name: ")
                        .append(cursor.getString(nameIndex))
                        .append("\nOrder Total: $")
                        .append(cursor.getFloat(totalIndex))
                        .append("\n\n");
                // Ndërton vargun e tekstit që përfaqëson detajet e porosisë dhe e shton në StringBuilder.
            }
        }

        ordersListTextView.setText(stringBuilder.toString());
        // Vendos tekstin e ndërtuar në TextView për ta shfaqur përdoruesit.

        cursor.close();
        // Mbyll Cursor për të liruar burimet e përdorura.
    }
}