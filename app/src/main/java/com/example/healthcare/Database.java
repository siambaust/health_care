package com.example.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Database extends SQLiteOpenHelper {

    // Constructor
    public Database(Context context) {
        super(context, "HealthcareDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table with email
        db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT, password TEXT)");
        // Create cart table
        db.execSQL("CREATE TABLE IF NOT EXISTS cart (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, itemName TEXT, cost REAL, otype TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS cart");
        onCreate(db);
    }

    // Method to register a user
    public boolean registerUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the username already exists
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false; // Username already exists
        }

        // Insert new user into the users table
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("password", password);
        long result = db.insert("users", null, values);
        cursor.close();
        db.close();

        return result != -1;
    }

    // Method to login a user
    public int login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});

        if (cursor.getCount() > 0) {
            cursor.close();
            return 1; // Login success
        }
        cursor.close();
        return 0; // Login failed
    }

    // Method to add an item to the cart
    public void addToCart(String username, String itemName, float cost, String otype) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("itemName", itemName);
        values.put("cost", cost);
        values.put("otype", otype);
        db.insert("cart", null, values);
        db.close();
    }

    // Method to check if an item already exists in the cart
    public int checkCart(String username, String itemName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cart WHERE username = ? AND itemName = ?", new String[]{username, itemName});

        if (cursor.getCount() > 0) {
            cursor.close();
            return 1; // Item exists in cart
        }
        cursor.close();
        return 0; // Item does not exist in cart
    }

    // Method to retrieve cart data
    public ArrayList<HashMap<String, String>> getCartData(String username, String otype) {
        ArrayList<HashMap<String, String>> cartData = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT itemName, cost FROM cart WHERE username = ? AND otype = ?", new String[]{username, otype});

        while (cursor.moveToNext()) {
            HashMap<String, String> item = new HashMap<>();
            item.put("itemName", cursor.getString(0)); // Column 1: itemName
            item.put("cost", cursor.getString(1)); // Column 2: cost
            cartData.add(item);
        }

        cursor.close();
        db.close();
        return cartData;
    }

    // Method to clear all cart data for a specific user and order type
    public void clearCart(String username, String otype) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cart", "username = ? AND otype = ?", new String[]{username, otype});
        db.close();
    }
}
