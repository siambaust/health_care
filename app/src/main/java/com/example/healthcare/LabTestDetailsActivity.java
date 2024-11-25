package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LabTestDetailsActivity extends AppCompatActivity {

    TextView tvPackageName, tvTotalCost;
    EditText edDetails;
    Button btnAddToCart, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_details);

        // Initialize views
        tvPackageName = findViewById(R.id.textViewCartPackageName);
        tvTotalCost = findViewById(R.id.textViewLDTotalCost);
        edDetails = findViewById(R.id.editTextLDTextMultiLine);
        btnAddToCart = findViewById(R.id.buttonLTGoToCart);
        btnBack = findViewById(R.id.buttonCartBack);

        // Make details EditText non-editable
        edDetails.setKeyListener(null);

        // Get data from Intent
        Intent intent = getIntent();
        tvPackageName.setText(intent.getStringExtra("text1"));
        edDetails.setText(intent.getStringExtra("text2"));
        tvTotalCost.setText("Total Cost : " + intent.getStringExtra("text3") + " Tk");

        // Back button click listener
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestDetailsActivity.this, LabTestActivity.class));
            }
        });

        // Add to cart button logic
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                String product = tvPackageName.getText().toString();
                float price = Float.parseFloat(intent.getStringExtra("text3"));
                String otype = "lab";  // Assuming 'otype' is always "lab" for this case

                // Correct Database instantiation
                Database db = new Database(getApplicationContext());

                // Check if the product is already in the cart
                if (db.checkCart(username, product) == 1) {
                    Toast.makeText(getApplicationContext(), "Product Already Added", Toast.LENGTH_SHORT).show();
                } else {
                    // Add product to cart
                    db.addToCart(username, product, price, otype);
                    Toast.makeText(getApplicationContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
                    // Navigate back to LabTestActivity
                    startActivity(new Intent(LabTestDetailsActivity.this, LabTestActivity.class));
                }
            }
        });
    }
}