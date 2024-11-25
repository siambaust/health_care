package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class FindDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

        // CardView for Family Physician
        CardView familyPhysician = findViewById(R.id.cardFDFamilyPhysician);
        familyPhysician.setOnClickListener(view -> openDoctorDetailsActivity("Family Physician"));

        // CardView for Dietician
        CardView dietician = findViewById(R.id.cardFDDietcian);
        dietician.setOnClickListener(view -> openDoctorDetailsActivity("Dietician"));

        // CardView for Dentist
        CardView dentist = findViewById(R.id.cardFDDentist);
        dentist.setOnClickListener(view -> openDoctorDetailsActivity("Dentist"));

        // CardView for Surgeon
        CardView surgeon = findViewById(R.id.cardFDSurgeon);
        surgeon.setOnClickListener(view -> openDoctorDetailsActivity("Surgeon"));

        // CardView for Cardiologists
        CardView cardiologists = findViewById(R.id.cardFDCardiologists);
        cardiologists.setOnClickListener(view -> openDoctorDetailsActivity("Cardiologists"));
    }

    private void openDoctorDetailsActivity(String title) {
        Intent intent = new Intent(FindDoctorActivity.this, DoctorDetailsActivity.class);
        intent.putExtra("title", title);
        startActivity(intent);
    }
}
