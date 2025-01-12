package com.example.a4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class CustomerEditActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, cityEditText, addressEditText;
    private Button saveButton;
    private UserDao userDao;
    private String customerEmail;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_edit);

        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        cityEditText = findViewById(R.id.cityEditText);
        addressEditText = findViewById(R.id.addressEditText);
        saveButton = findViewById(R.id.saveButton);

        UserDatabase db = Room.databaseBuilder(getApplicationContext(),
                UserDatabase.class, "user-database").build();
        userDao = db.userDao();

        customerEmail = getIntent().getStringExtra("email");

        new Thread(() -> {
            currentUser = userDao.getUserByEmail(customerEmail);
            runOnUiThread(() -> {
                if (currentUser != null) {
                    nameEditText.setText(currentUser.name);
                    phoneEditText.setText(currentUser.phone);
                    cityEditText.setText(currentUser.city);
                    addressEditText.setText(currentUser.address);
                }
            });
        }).start();

        saveButton.setOnClickListener(v -> {
            String updatedName = nameEditText.getText().toString();
            String updatedPhone = phoneEditText.getText().toString();
            String updatedCity = cityEditText.getText().toString();
            String updatedAddress = addressEditText.getText().toString();

            currentUser.name = updatedName;
            currentUser.phone = updatedPhone;
            currentUser.city = updatedCity;
            currentUser.address = updatedAddress;

            new Thread(() -> {
                userDao.update(currentUser);
                runOnUiThread(() -> {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("updatedName", updatedName);
                    returnIntent.putExtra("updatedPhone", updatedPhone);
                    returnIntent.putExtra("updatedCity", updatedCity);
                    returnIntent.putExtra("updatedAddress", updatedAddress);
                    setResult(RESULT_OK, returnIntent);
                    Intent intent = new Intent(CustomerEditActivity.this, CustomerHomeActivity.class);
                    startActivity(intent);
                });
            }).start();
        });
    }
}
