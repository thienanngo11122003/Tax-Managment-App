package com.example.a4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.widget.ArrayAdapter;

public class CustomerDetailActivity extends AppCompatActivity {

    private TextView nameTextView, phoneTextView, cityTextView, addressTextView, statusTextView;
    private Button updateStatusButton;
    private Spinner statusSpinner;
    private UserDao userDao;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);

        nameTextView = findViewById(R.id.nameTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        cityTextView = findViewById(R.id.cityTextView);
        addressTextView = findViewById(R.id.addressTextView);
        statusTextView = findViewById(R.id.statusTextView);
        updateStatusButton = findViewById(R.id.updateStatusButton);
        statusSpinner = findViewById(R.id.statusSpinner);

        UserDatabase db = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "user-database").build();
        userDao = db.userDao();

        int userId = getIntent().getIntExtra("user_id", -1);

        if (userId != -1) {
            new Thread(() -> {
                currentUser = userDao.getUserById(userId);
                runOnUiThread(() -> {
                    if (currentUser != null) {
                        // Set values for all customer information
                        nameTextView.setText(currentUser.name);
                        phoneTextView.setText(currentUser.phone);
                        cityTextView.setText(currentUser.city);
                        addressTextView.setText(currentUser.address);
                        statusTextView.setText(currentUser.status);
                        setUpStatusSpinner(currentUser.status);
                    } else {
                        Toast.makeText(CustomerDetailActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                });
            }).start();
        } else {
            Toast.makeText(CustomerDetailActivity.this, "Invalid User ID", Toast.LENGTH_SHORT).show();
        }

        updateStatusButton.setOnClickListener(v -> {
            String selectedStatus = statusSpinner.getSelectedItem().toString();
            currentUser.status = selectedStatus;

            new Thread(() -> {
                userDao.update(currentUser);
                runOnUiThread(() -> {
                    statusTextView.setText(currentUser.status);
                    Toast.makeText(CustomerDetailActivity.this, "Status Updated", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CustomerDetailActivity.this, AdminHomeActivity.class);
                    startActivity(intent);
                    finish();
                });
            }).start();
        });

    }

    private void setUpStatusSpinner(String currentStatus) {
        String[] statuses = {"AWAITED", "FAILEDTOREACH", "ONBOARDED", "INPROCESS", "COMPLETED", "DENIED"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(adapter);

        int statusIndex = getStatusIndex(currentStatus);
        statusSpinner.setSelection(statusIndex);
    }

    private int getStatusIndex(String status) {
        switch (status) {
            case "AWAITED": return 0;
            case "FAILEDTOREACH": return 1;
            case "ONBOARDED": return 2;
            case "INPROCESS": return 3;
            case "COMPLETED": return 4;
            case "DENIED": return 5;
            default: return -1;
        }
    }
}
