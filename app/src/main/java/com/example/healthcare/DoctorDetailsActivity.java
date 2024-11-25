package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {

    // Doctor details for different categories
    private String[][] doctordetails1 = {
            { "Doctor Name  : Mahmudul Hasan", "Hospital Address : Rangpur", "Exp : 5yrs", "Mobile NO: 01911033099", "700" },
            { "Doctor Name: Shahinur Rahman", "Hospital Address: Dhaka", "Exp: 7yrs", "Mobile No: 01913044112", "800" },
            { "Doctor Name: Fariha Sultana", "Hospital Address: Chattogram", "Exp: 6yrs", "Mobile No: 01914055223", "750" },
            { "Doctor Name: Ismail Hossain", "Hospital Address: Sylhet", "Exp: 8yrs", "Mobile No: 01915066334", "900" },
            { "Doctor Name: Nafisa Akter", "Hospital Address: Khulna", "Exp: 4yrs", "Mobile No: 01916077445", "650" }
    };

    private String[][] doctordetails2 = {
            { "Doctor Name: Shirin Akter", "Hospital Address: Dhaka", "Exp: 6yrs", "Mobile No: 01917088566", "600" },
            { "Doctor Name: Imran Hossain", "Hospital Address: Chattogram", "Exp: 4yrs", "Mobile No: 01918099677", "650" },
            { "Doctor Name: Jannatul Ferdous", "Hospital Address: Sylhet", "Exp: 5yrs", "Mobile No: 01919010788", "700" },
            { "Doctor Name: Rifat Sultana", "Hospital Address: Khulna", "Exp: 7yrs", "Mobile No: 01920021899", "750" }
    };

    private String[][] doctordetails3 = {
            { "Doctor Name: Dr. Azizul Hakim", "Hospital Address: Dhaka", "Exp: 10yrs", "Mobile No: 01921032900", "1000" },
            { "Doctor Name: Rifat Ahmed", "Hospital Address: Chattogram", "Exp: 8yrs", "Mobile No: 01922044011", "900" },
            { "Doctor Name: Nusrat Jahan", "Hospital Address: Sylhet", "Exp: 6yrs", "Mobile No: 01923055122", "850" },
            { "Doctor Name: Asif Rahman", "Hospital Address: Khulna", "Exp: 5yrs", "Mobile No: 01924066233", "800" }
    };

    private String[][] doctordetails4 = {
            { "Doctor Name: Dr. Anwar Hossain", "Hospital Address: Dhaka", "Exp: 15yrs", "Mobile No: 01925077344", "2000" },
            { "Doctor Name: Monirul Islam", "Hospital Address: Chattogram", "Exp: 12yrs", "Mobile No: 01926088455", "1800" },
            { "Doctor Name: Sultana Kamal", "Hospital Address: Sylhet", "Exp: 14yrs", "Mobile No: 01927099566", "1900" },
            { "Doctor Name: Ahsan Habib", "Hospital Address: Khulna", "Exp: 10yrs", "Mobile No: 01928010677", "1700" }
    };

    private String[][] doctordetails5 = {
            { "Doctor Name: Dr. Faizul Islam", "Hospital Address: Dhaka", "Exp: 20yrs", "Mobile No: 01929021788", "3000" },
            { "Doctor Name: Nurul Hasan", "Hospital Address: Chattogram", "Exp: 18yrs", "Mobile No: 01930032899", "2800" },
            { "Doctor Name: Sharmin Akter", "Hospital Address: Sylhet", "Exp: 22yrs", "Mobile No: 01931043900", "3200" },
            { "Doctor Name: Rashedul Islam", "Hospital Address: Khulna", "Exp: 25yrs", "Mobile No: 01932055011", "3500" }
    };

    // Views
    private TextView tvTitle;
    private Button btnBack;
    private String[][] doctor_details = {};
    private HashMap<String, String> item;
    private ArrayList<HashMap<String, String>> list;
    private SimpleAdapter sa;

    // HashMap to map category titles to doctor details
    private HashMap<String, String[][]> doctorDetailsMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        // Initialize Views
        tvTitle = findViewById(R.id.textViewBMTitle);
        btnBack = findViewById(R.id.buttonBack);

        // Initialize doctor details map
        doctorDetailsMap = new HashMap<>();
        doctorDetailsMap.put("Family Physician", doctordetails1);
        doctorDetailsMap.put("Dietician", doctordetails2);
        doctorDetailsMap.put("Dentist", doctordetails3);
        doctorDetailsMap.put("Surgeon", doctordetails4);
        doctorDetailsMap.put("Cardiologists", doctordetails5);

        // Get title from Intent and select the corresponding doctor details
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        if (title != null && doctorDetailsMap.containsKey(title)) {
            doctor_details = doctorDetailsMap.get(title);
        }

        // Set the title in the TextView
        tvTitle.setText(title);

        // Back Button Functionality
        btnBack.setOnClickListener(view -> finish());

        // Populate ListView with doctor details
        list = new ArrayList<>();
        for (int i = 0; i < doctor_details.length; i++) {
            item = new HashMap<>();
            item.put("line1", doctor_details[i][0]);
            item.put("line2", doctor_details[i][1]);
            item.put("line3", doctor_details[i][2]);
            item.put("line4", doctor_details[i][3]);
            item.put("line5", "Cons Fee: " + doctor_details[i][4] + " Tk");
            Log.d("DoctorDetails", "Item " + i + ": " + doctor_details[i][0] + ", " +
                    doctor_details[i][1] + ", " + doctor_details[i][2] + ", " +
                    doctor_details[i][3] + ", " + doctor_details[i][4]);
            list.add(item);
        }

        // Set the adapter to the ListView
        sa = new SimpleAdapter(this, list, R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        ListView lst = findViewById(R.id.listViewBM);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(DoctorDetailsActivity.this, BookAppoinmentActivity.class);
                it.putExtra("text1", title);
                it.putExtra("text2",doctor_details[i][0]);
                it.putExtra("text3",doctor_details[i][1]);
                it.putExtra("text4",doctor_details[i][3]);
                it.putExtra("text5",doctor_details[i][4]);
                startActivity(it);



            }
        } );
    }
}
