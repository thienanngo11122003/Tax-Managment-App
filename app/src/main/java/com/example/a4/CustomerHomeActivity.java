package com.example.a4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class CustomerHomeActivity extends AppCompatActivity {

    private TextView nameTextView, phoneTextView, cityTextView, addressTextView, statusTextView;
    private Button editButton, logoutButton;
    private UserDao userDao;
    private String customerEmail;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        nameTextView = findViewById(R.id.nameTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        cityTextView = findViewById(R.id.cityTextView);
        addressTextView = findViewById(R.id.addressTextView);
        statusTextView = findViewById(R.id.statusTextView);
        editButton = findViewById(R.id.editButton);
        logoutButton = findViewById(R.id.logoutButton);

        UserDatabase db = Room.databaseBuilder(getApplicationContext(),
                UserDatabase.class, "user-database").build();
        userDao = db.userDao();

        customerEmail = getIntent().getStringExtra("email");

        if (customerEmail != null) {
            new Thread(() -> {
                currentUser = userDao.getUserByEmail(customerEmail);
                runOnUiThread(() -> {
                    if (currentUser != null) {
                        // Display customer data in the TextViews
                        nameTextView.setText("Name: " + currentUser.name);
                        phoneTextView.setText("Phone: " + currentUser.phone);
                        cityTextView.setText("City: " + currentUser.city);
                        addressTextView.setText("Address: " + currentUser.address);
                        statusTextView.setText("Status: " + currentUser.status);
                        updateStatusColor(currentUser.status);
                    } else {
                        Toast.makeText(CustomerHomeActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        } else {
            Toast.makeText(CustomerHomeActivity.this, "No customer email provided", Toast.LENGTH_SHORT).show();
        }

        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerHomeActivity.this, CustomerEditActivity.class);
            intent.putExtra("email", customerEmail);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(CustomerHomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void updateStatusColor(String status) {
        int color;
        switch (status) {
            case "AWAITED":
                color = getResources().getColor(R.color.yellow);
                break;
            case "FAILEDTOREACH":
                color = getResources().getColor(R.color.red_light);
                break;
            case "ONBOARDED":
                color = getResources().getColor(R.color.green_light);
                break;
            case "INPROCESS":
                color = getResources().getColor(R.color.green_mid);
                break;
            case "COMPLETED":
                color = getResources().getColor(R.color.green_dark);
                break;
            case "DENIED":
                color = getResources().getColor(R.color.red);
                break;
            default:
                color = getResources().getColor(R.color.gray);
                break;
        }
        statusTextView.setBackgroundColor(color);
    }
}
