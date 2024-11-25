package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HealthArticleDetailsActivity extends AppCompatActivity {

    TextView tv1;
    ImageView img;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_article_details);

        // Initialize UI components
        btnBack = findViewById(R.id.buttonHADBack);
        tv1 = findViewById(R.id.textViewHADTitle);
        img = findViewById(R.id.imageViewHAD);

        // Get the data passed from the previous activity
        Intent intent = getIntent();
        tv1.setText(intent.getStringExtra("text1"));

        // Retrieve the image ID from the intent
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int image = bundle.getInt("text2");
            img.setImageResource(image); // Set the image resource
        }

        // Set the back button click listener
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go back to the HealthArticlesActivity
                startActivity(new Intent(HealthArticleDetailsActivity.this, HealthArticlesActivity.class));
                // Finish the current activity to prevent back navigation to this screen
            }
        });
    }
}
