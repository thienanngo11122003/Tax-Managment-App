package com.example.a4;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.room.Room;

import java.util.List;

public class AdminHomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserDao userDao;
    private UserAdapter userAdapter;
    private Button logoutButton;
    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        recyclerView = findViewById(R.id.customerRecyclerView);
        logoutButton = findViewById(R.id.logoutButton);

        UserDatabase db = Room.databaseBuilder(getApplicationContext(),
                UserDatabase.class, "user-database").build();
        userDao = db.userDao();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new Thread(() -> {
            users = userDao.getAllUsers();  // Fetch users
            runOnUiThread(() -> {
                userAdapter = new UserAdapter(users, AdminHomeActivity.this, user -> {
                    Intent intent = new Intent(AdminHomeActivity.this, CustomerDetailActivity.class);
                    intent.putExtra("user_id", user.getId());
                    startActivity(intent);

                });

                recyclerView.setAdapter(userAdapter);
            });
        }).start();

        logoutButton.setOnClickListener(v -> {
            startActivity(new Intent(AdminHomeActivity.this, LoginActivity.class));
            finish();
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                User user = users.get(viewHolder.getAdapterPosition());
                new AlertDialog.Builder(AdminHomeActivity.this)
                        .setMessage("Are you sure you want to delete this user?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            new Thread(() -> {
                                userDao.delete(user);
                                runOnUiThread(() -> {
                                    users.remove(viewHolder.getAdapterPosition());
                                    userAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                                });
                            }).start();
                        })
                        .setNegativeButton("No", (dialog, which) -> {
                            userAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                        })
                        .show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
