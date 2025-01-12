package com.example.a4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserDatabase db = Room.databaseBuilder(getApplicationContext(),
                UserDatabase.class, "user-database").build();
        userDao = db.userDao();

        // Pre-register the admin user
        new Thread(() -> {
            User admin = userDao.getUserByEmail("admin@gmail.com");
            if (admin == null) {
                User adminUser = new User("Admin", "admin@gmail.com", "4374391234", "Toronto",
                        "AWAITED", "20 Milicient Street", 0.0, 0.0, "password", "Admin");
                userDao.insert(adminUser);
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Admin pre-registered", Toast.LENGTH_SHORT).show();

                    navigateToLogin();
                });
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Admin already exists", Toast.LENGTH_SHORT).show();
                    navigateToLogin();
                });
            }
        }).start();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Close MainActivity
    }
}
