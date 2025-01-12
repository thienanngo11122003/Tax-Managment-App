package com.example.a4;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "user_table")
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "phone")
    public String phone;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "status")
    public String status;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "latitude")
    public double latitude;

    @ColumnInfo(name = "longitude")
    public double longitude;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "userType")
    public String userType;


    public int getId() {
        return id;
    }

    public User(String name, String email, String phone, String city, String status, String address, double latitude, double longitude, String password, String userType) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.status = status;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.password = password;
        this.userType = userType;
    }
}



