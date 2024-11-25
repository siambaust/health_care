package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class BuyMedicineActivity extends AppCompatActivity {
    private String[][] medicine_packages = {
            {"Package 1 : Cold & Flu Essentials", "", "", "", "799"},
            {"Package 2 : Diabetes Care Kit", "", "", "", "1499"},
            {"Package 3 : Pain Relief Bundle", "", "", "", "899"},
            {"Package 4 : Immunity Booster Pack", "", "", "", "1099"},
            {"Package 5 : Heart Health Essentials", "", "", "", "1499"},
    };

    private String[] medicine_packages_details = {
            "Paracetamol Tablets (500mg) - 20 Tablets\n" +
                    "Cough Syrup (100ml)\n" +
                    "Vitamin C Tablets (500mg) - 30 Tablets\n" +
                    "Nasal Spray (20ml)",
            "Glucose Monitoring Strips - 50 Pieces\n" +
                    "Metformin Tablets (500mg) - 30 Tablets\n" +
                    "Insulin Syringes - 10 Pieces\n" +
                    "Multivitamins - 30 Tablets",
            "Ibuprofen Tablets (400mg) - 20 Tablets\n" +
                    "Pain Relief Gel (50g)\n" +
                    "Heating Patches (Pack of 3)\n" +
                    "Muscle Relaxant Tablets (10 Tablets)",
            "Vitamin C Tablets (1000mg) - 30 Tablets\n" +
                    "Zinc Supplements (50mg) - 30 Tablets\n" +
                    "Echinacea Capsules (300mg) - 30 Capsules\n" +
                    "Probiotic Supplements (10 Billion CFU) - 30 Capsules",
            "Aspirin Tablets (75mg) - 20 Tablets\n" +
                    "Omega-3 Fish Oil Capsules (1000mg) - 30 Capsules\n" +
                    "Coenzyme Q10 (CoQ10) - 30 Capsules\n" +
                    "Garlic Extract Capsules - 30 Capsules"
    };

    HashMap<String, String> item;
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;
    Button btnGoToCart, btnBack;
    ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);

        lst = findViewById(R.id.listViewBM);
        btnGoToCart = findViewById(R.id.buttonBMGoToCart);
        btnBack = findViewById(R.id.buttonBMBack);

        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to CartMedicineActivity (uncomment this when the activity is implemented)
                 startActivity(new Intent(BuyMedicineActivity.this, CartBuyMedicineActivity.class));
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyMedicineActivity.this, HomeActivity.class));
            }
        });

        list = new ArrayList<>();
        for (int i = 0; i < medicine_packages.length; i++) {
            item = new HashMap<>();
            item.put("line1", medicine_packages[i][0]);
            item.put("line2", medicine_packages[i][1]);
            item.put("line3", medicine_packages[i][2]);
            item.put("line4", medicine_packages[i][3]);
            item.put("line5", "Total Cost : " + medicine_packages[i][4] + "Tk");
            list.add(item);
        }

        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);

        // Corrected: Use setOnItemClickListener for ListView
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(BuyMedicineActivity.this, BuyMedicineDetailsActivity.class);
                it.putExtra("text1", medicine_packages[position][0]);
                it.putExtra("text2", medicine_packages_details[position]);
                it.putExtra("text3", medicine_packages[position][4]);
                startActivity(it);
            }
        });
    }
}
