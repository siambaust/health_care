package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class HealthArticlesActivity extends AppCompatActivity {

    // Declare the health articles and their details
    private String[][] healthArticles = {
            {"Walking Daily", "", "", "", "Click More Details"},
            {"Home Care of COVID-19", "", "", "", "Click More Details"},
            {"Stop Smoking", "", "", "", "Click More Details"},
            {"Menstrual Cramps", "", "", "", "Click More Details"},
            {"Healthy Gut", "", "", "", "Click More Details"}
    };

    // Array of images corresponding to each article
    private int[] images = {
            R.drawable.health1,
            R.drawable.health2,
            R.drawable.health3,
            R.drawable.health4,
            R.drawable.health5
    };

    // Declare necessary UI components and data structures
    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;
    ListView lst;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_articles);

        // Initialize UI components
        lst = findViewById(R.id.listViewHA);
        btnBack = findViewById(R.id.buttonHABack);

        // Set up the back button listener to navigate back to the home activity
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthArticlesActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        // Initialize the list to display articles
        list = new ArrayList<>();

        // Fill the list with article details
        for (int i = 0; i < healthArticles.length; i++) {
            item = new HashMap<>();
            item.put("line1", healthArticles[i][0]);  // Title of the article
            item.put("line2", healthArticles[i][1]);  // Empty field for potential use
            item.put("line3", healthArticles[i][2]);  // Empty field for potential use
            item.put("line4", healthArticles[i][3]);  // Empty field for potential use
            item.put("line5", healthArticles[i][4]);  // "Click More Details"
            list.add(item);
        }

        // Set up the SimpleAdapter to display the list in the ListView
        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,  // Assuming you have a layout for multi-line data
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);

        // Set up the click listener for each item in the ListView
        lst.setOnItemClickListener((adapterView, view, position, id) -> {
            // When an item is clicked, navigate to the article details page
            Intent it = new Intent(HealthArticlesActivity.this, HealthArticleDetailsActivity.class);
            it.putExtra("text1", healthArticles[position][0]);  // Title of the article
            it.putExtra("text2", images[position]);  // Image associated with the article
            startActivity(it);
        });
    }
}
