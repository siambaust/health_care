package com.example.healthcare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CartLabActivity extends AppCompatActivity {
    HashMap<String, String> item;
    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;
    TextView tvTotal;
    ListView lst;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, timeButton, btnCheckout, btnCartBack;
    private String[][] packages;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_lab);

        // Initialize buttons
        dateButton = findViewById(R.id.buttonLTCartDate);
        timeButton = findViewById(R.id.buttonCartTime);
        btnCheckout = findViewById(R.id.buttonLTGoToCart);
        btnCartBack = findViewById(R.id.buttonCartBack);
        tvTotal = findViewById(R.id.textViewCartTotalCost);
        lst = findViewById(R.id.listViewLTCart);

        // Get username from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");  // default empty string

        // Database instance
        Database db = new Database(getApplicationContext());

        float totalAmount = 0;
        ArrayList<HashMap<String, String>> dbData = db.getCartData(username, "lab"); // Fetching cart data for lab tests

        // Initialize packages array
        packages = new String[dbData.size()][];

        for (int i = 0; i < packages.length; i++) {
            packages[i] = new String[5];
        }

        // Fill in the packages array with the lab test data
        for (int i = 0; i < dbData.size(); i++) {
            HashMap<String, String> mapData = dbData.get(i);
            String testName = mapData.get("testName");
            String cost = mapData.get("cost");

            packages[i][0] = testName; // Test name
            packages[i][4] = "Cost: $" + cost + "/-"; // Cost
            totalAmount += Float.parseFloat(cost);
        }

        // Update the total cost display
        tvTotal.setText("Total Cost : $" + totalAmount);

        // Populate the ListView with lab test data
        list = new ArrayList<>();
        for (String[] packageItem : packages) {
            item = new HashMap<>();
            item.put("line1", packageItem[0]); // Test name
            item.put("line5", packageItem[4]); // Cost
            list.add(item);
        }

        // Set up the SimpleAdapter to display the list of tests in the ListView
        sa = new SimpleAdapter(
                this,
                list,
                R.layout.multi_lines,  // Assuming multi_lines is the layout you want to display in each row
                new String[]{"line1", "line5"},
                new int[]{R.id.line_a, R.id.line_e}
        );
        lst.setAdapter(sa);

        // Back button click listener
        btnCartBack.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(CartLabActivity.this, LabTestActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();  // Log error in Logcat
                Toast.makeText(CartLabActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Initialize DatePicker
        initDatePicker();
        dateButton.setOnClickListener(view -> datePickerDialog.show());

        // Initialize TimePicker
        initTimePicker();
        timeButton.setOnClickListener(view -> timePickerDialog.show());
    }

    // Initialize DatePickerDialog
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1; // Month is 0-based
            String date = day + "/" + month + "/" + year;
            dateButton.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() + 86400000);  // Set minimum date to tomorrow
    }

    // Initialize TimePickerDialog
    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, hourOfDay, minute) -> {
            String time = hourOfDay + ":" + String.format("%02d", minute); // Format time as HH:mm
            timeButton.setText(time);
        };

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hour, minute, true);
    }
}