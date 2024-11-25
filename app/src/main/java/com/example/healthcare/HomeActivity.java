package com.example.healthcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "").toString();
        Toast.makeText(getApplicationContext(),"Welcome "+username,Toast.LENGTH_SHORT).show();

        CardView exit = findViewById(R.id.cardExit);
        exit.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.clear();
               editor.apply();
               startActivity(new Intent(HomeActivity.this,loginActivity.class));

            }

        });
        CardView findDoctor = findViewById(R.id.cardFindDoctor);
        findDoctor.setOnClickListener(view -> {
            // Debug Toast দিয়ে চেক করুন
            Toast.makeText(HomeActivity.this, "Find Doctor Clicked", Toast.LENGTH_SHORT).show();

            // FindDoctorActivity তে যাওয়ার Intent
            Intent intent = new Intent(HomeActivity.this, FindDoctorActivity.class);
            startActivity(intent);
        });


        CardView labtest = findViewById(R.id.cardLabTest);
        labtest.setOnClickListener(view -> {
            // Debug Toast দিয়ে চেক করুন
            Toast.makeText(HomeActivity.this, "Lab Test Clicked", Toast.LENGTH_SHORT).show();

            // FindDoctorActivity তে যাওয়ার Intent
            Intent intent = new Intent(HomeActivity.this, LabTestActivity.class);
            startActivity(intent);
        });
        CardView buyMedicine = findViewById(R.id.cardBuyMedicine);
        buyMedicine.setOnClickListener(view -> {
            // Debug Toast দিয়ে চেক করুন
            Toast.makeText(HomeActivity.this, "Buy Medicine Clicked", Toast.LENGTH_SHORT).show();

            // FindDoctorActivity তে যাওয়ার Intent
            Intent intent = new Intent(HomeActivity.this, BuyMedicineActivity.class);
            startActivity(intent);
        });

        CardView health = findViewById(R.id.cardHealthDoctor);
        health.setOnClickListener(view -> {

            Intent intent = new Intent(HomeActivity.this, HealthArticlesActivity.class);
            startActivity(intent);
        });
    }
}