package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText edUsername, edEmail, edPassword, edConfirmPassword;
    Button btn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        edUsername = findViewById(R.id.editTextAppFullName);
        edEmail = findViewById(R.id.editTextAppAddress);
        edPassword = findViewById(R.id.editTextAppContactNumber);
        edConfirmPassword = findViewById(R.id.editTextAppFees);
        btn = findViewById(R.id.buttonAppBack);
        tv = findViewById(R.id.textViewAppTitle);

        // Navigate back to LoginActivity if user is an existing user
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        // Handle registration logic
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                String confirmPassword = edConfirmPassword.getText().toString().trim();

                // Correct Database instantiation
                Database db = new Database(getApplicationContext());

                // Check if any field is empty
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if password and confirm password match
                    if (password.equals(confirmPassword)) {
                        // Validate password strength
                        if (isValid(password)) {
                            db.registerUser(username, email, password);
                            Toast.makeText(getApplicationContext(), "Record Inserted", Toast.LENGTH_SHORT).show();
                            // Navigate to LoginActivity
                            startActivity(new Intent(RegisterActivity.this, loginActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Password must contain at least 8 characters, one letter, one number, and one special character",
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Password and Confirm Password did not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Password validation method
    public static boolean isValid(String password) {
        int flagLetter = 0, flagDigit = 0, flagSpecialChar = 0;

        if (password.length() < 8) {
            return false;
        } else {
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);

                // Check for letter
                if (Character.isLetter(c)) {
                    flagLetter = 1;
                }

                // Check for digit
                if (Character.isDigit(c)) {
                    flagDigit = 1;
                }

                // Check for special character
                if ((c >= 33 && c <= 46) || c == 64) {
                    flagSpecialChar = 1;
                }
            }
        }

        return (flagLetter == 1 && flagDigit == 1 && flagSpecialChar == 1);
    }
}
