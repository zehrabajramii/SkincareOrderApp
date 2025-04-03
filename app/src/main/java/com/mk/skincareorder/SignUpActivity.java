package com.mk.skincareorder;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    // Kjo klasë përfaqëson një aktivitet që lejon përdoruesit të regjistrohen duke krijuar një llogari të re.

    EditText newUsername;
    // EditText për marrjen e emrit të ri të përdoruesit nga përdoruesi.

    EditText newPassword;
    // EditText për marrjen e fjalëkalimit të ri nga përdoruesi.

    EditText confirmPassword;
    // EditText për marrjen dhe konfirmimin e fjalëkalimit të ri.

    Button signUpButton;
    // Button për nisjen e procesit të regjistrimit kur përdoruesi klikon mbi të.

    TextView loginText;
    // TextView që lejon përdoruesit të kalojnë në ekranin e hyrjes nëse tashmë kanë një llogari.

    DatabaseHelpeer db;
    // Një referencë për klasën DatabaseHelpeer që përdoret për të ndërvepruar me bazën e të dhënave.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Vendos layout-in për këtë aktivitet duke përdorur skedarin 'activity_sign_up.xml'.

        db = new DatabaseHelpeer(this);
        // Inicializon instancën e DatabaseHelpeer për të menaxhuar operacionet e bazës së të dhënave.

        newUsername = findViewById(R.id.newUsername);
        // Lidh EditText për emrin e ri të përdoruesit me kodin Java.

        newPassword = findViewById(R.id.newPassword);
        // Lidh EditText për fjalëkalimin e ri me kodin Java.

        confirmPassword = findViewById(R.id.confirmPassword);
        // Lidh EditText për konfirmimin e fjalëkalimit me kodin Java.

        signUpButton = findViewById(R.id.signUpButton);
        // Lidh Button për regjistrimin e përdoruesit me kodin Java.

        loginText = findViewById(R.id.loginText);
        // Lidh TextView për navigimin në ekranin e hyrjes me kodin Java.

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vendos një dëgjues për klikimet mbi butonin e regjistrimit.

                String username = newUsername.getText().toString().trim();
                // Merr dhe pastron emrin e përdoruesit nga EditText.

                String password = newPassword.getText().toString().trim();
                // Merr dhe pastron fjalëkalimin nga EditText.

                String confirmPwd = confirmPassword.getText().toString().trim();
                // Merr dhe pastron fjalëkalimin e konfirmuar nga EditText.

                // Siguron që të gjitha fushat janë plotësuar
                if (username.isEmpty() || password.isEmpty() || confirmPwd.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill in all the fields.", Toast.LENGTH_SHORT).show();
                    // Nëse ndonjë fushë është bosh, shfaq një mesazh gabimi dhe ndalon procesin e regjistrimit.
                    return;
                }

                // Kontrollon nëse fjalëkalimet përputhen
                if (!password.equals(confirmPwd)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    // Nëse fjalëkalimet nuk përputhen, shfaq një mesazh gabimi dhe ndalon procesin e regjistrimit.
                    return;
                }

                // Kontrollon nëse emri i përdoruesit ekziston tashmë
                if (db.checkUserExists(username)) {
                    Toast.makeText(SignUpActivity.this, "Username already exists! Please choose another.", Toast.LENGTH_SHORT).show();
                    // Nëse emri i përdoruesit ekziston tashmë, shfaq një mesazh gabimi dhe ndalon procesin e regjistrimit.
                    return;
                }

                // Shton përdoruesin e ri në bazën e të dhënave
                boolean isInserted = db.insertUser(username, password);
                if (isInserted) {
                    Toast.makeText(SignUpActivity.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                    // Nëse regjistrimi është i suksesshëm, shfaq një mesazh suksesi.

                    // Navigon përsëri në LoginActivity
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, "Sign Up Failed! Please try again.", Toast.LENGTH_SHORT).show();
                    // Nëse regjistrimi dështon, shfaq një mesazh gabimi.
                }
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Vendos një dëgjues për klikimet mbi tekstin e hyrjes.

                // Navigon në LoginActivity nëse përdoruesi tashmë ka një llogari
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
