package com.mk.skincareorder;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    // Kjo klasë përfaqëson aktivitetin e hyrjes në aplikacion, ku përdoruesit mund të futen me kredencialet e tyre ose të regjistrohen për një llogari të re.

    EditText username;
    // EditText për marrjen e emrit të përdoruesit nga përdoruesi.

    EditText password;
    // EditText për marrjen e fjalëkalimit nga përdoruesi.

    Button loginButton;
    // Button që përdoruesi klikon për të kryer hyrjen në aplikacion.

    TextView signUpText;
    // TextView që lejon përdoruesit të kalojnë në ekranin e regjistrimit nëse nuk kanë një llogari.

    DatabaseHelpeer db;
    // Një referencë për klasën DatabaseHelpeer që përdoret për të ndërvepruar me bazën e të dhënave.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Vendos layout-in e aktivitetit duke përdorur skedarin XML 'activity_login'.

        db = new DatabaseHelpeer(this);
        // Inicializon instancën e DatabaseHelpeer për të menaxhuar operacionet e bazës së të dhënave.

        username = findViewById(R.id.username);
        // Lidh EditText për emrin e përdoruesit me kodin Java.

        password = findViewById(R.id.password);
        // Lidh EditText për fjalëkalimin me kodin Java.

        loginButton = findViewById(R.id.loginButton);
        // Lidh Button për hyrjen me kodin Java.

        signUpText = findViewById(R.id.signupText);
        // Lidh TextView për navigimin në ekranin e regjistrimit me kodin Java.

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredUsername = username.getText().toString().trim();
                String enteredPassword = password.getText().toString().trim();
                // Merr dhe pastron emrin e përdoruesit dhe fjalëkalimin nga EditText-et.

                if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Kontrollon nëse ndonjë fushë është bosh dhe shfaq një mesazh gabimi nëse është kështu.

                boolean userExists = db.checkUserExists(enteredUsername);
                if (!userExists) {
                    Toast.makeText(LoginActivity.this, "Account does not exist. Please sign up.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Kontrollon nëse përdoruesi ekziston në bazën e të dhënave. Nëse nuk ekziston, shfaq një mesazh gabimi.

                boolean isValid = db.checkUserCredentials(enteredUsername, enteredPassword);
                if (isValid) {
                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

                    // Në rast të hyrjes së suksesshme, nis aktivitetin HomeActivity.
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();  // Mbyll aktivitetin LoginActivity.
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed! Incorrect username or password.", Toast.LENGTH_SHORT).show();
                }
                // Kontrollon nëse kredencialet janë të sakta. Nëse po, përdoruesi hyn në aplikacion, përndryshe shfaqet një mesazh gabimi.
            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigon te aktiviteti SignUpActivity kur përdoruesi klikon mbi tekstin për regjistrim.
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
