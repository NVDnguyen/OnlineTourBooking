package com.example.bookingapp.data.repository;

import static com.example.bookingapp.utils.Constant.DATE_TIME_FORMAT;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookingapp.data.database.DatabaseHelper;
import com.example.bookingapp.data.model.Bill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BillRepository {
    private final SQLiteDatabase db;

    public BillRepository(Context context) {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);
        this.db = dbHelper.getWritableDatabase();
    }

    /**
     * Create a new Bill in the database.
     */
    public boolean createBill(Bill bill) {
        if (bill == null) return false;

        ContentValues values = new ContentValues();
        values.put("id", bill.getId());
        values.put("idFlightBill", bill.getFlightBillId());
        values.put("idHotelBill", bill.getHotelBillId());
        values.put("idPlaceBill", bill.getPlaceBillId());
        values.put("idUser", bill.getUserId());
        values.put("totalPrice", bill.getTotalPrice());

        // Convert Date to String format
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());
        values.put("date", sdf.format(bill.getDate()));

        long result = db.insert("Bill", null, values);
        return result != -1;
    }

    /**
     * Convert Cursor to Bill object.
     */
    private Bill convertCursorToBill(Cursor cursor) {
        if (cursor == null) return null;

        try {
            String dateString = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            Date date = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).parse(dateString);

            return new Bill(
                    date,
                    cursor.getString(cursor.getColumnIndexOrThrow("idFlightBill")),
                    cursor.getString(cursor.getColumnIndexOrThrow("idHotelBill")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("idPlaceBill")),
                    cursor.getFloat(cursor.getColumnIndexOrThrow("totalPrice")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("idUser"))
            );
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get all Bills from the database.
     */
    public List<Bill> getAllBill() {
        List<Bill> bills = new ArrayList<>();
        try (Cursor cursor = db.rawQuery("SELECT * FROM Bill", null)) {
            while (cursor.moveToNext()) {
                Bill bill = convertCursorToBill(cursor);
                if (bill != null) {
                    bills.add(bill);
                }
            }
        }
        return bills;
    }

    /**
     * Get Bills by User ID.
     */
    public List<Bill> getBillByUserId(String uid) {
        List<Bill> bills = new ArrayList<>();
        if (uid == null || uid.trim().isEmpty()) return bills; // Prevent invalid queries

        try (Cursor cursor = db.rawQuery("SELECT * FROM Bill WHERE idUser = ?", new String[]{uid})) {
            while (cursor.moveToNext()) {
                Bill bill = convertCursorToBill(cursor);
                if (bill != null) {
                    bills.add(bill);
                }
            }
        }
        return bills;
    }

    /**
     * Get a single Bill by its ID.
     */
    public Bill getBillById(String id) {
        if (id == null || id.trim().isEmpty()) return null;

        try (Cursor cursor = db.rawQuery("SELECT * FROM Bill WHERE id = ?", new String[]{id})) {
            if (cursor.moveToFirst()) {
                return convertCursorToBill(cursor);
            }
        }
        return null;
    }

    /**
     * Delete a Bill by its ID.
     */
    public boolean deleteBill(String id) {
        if (id == null || id.trim().isEmpty()) return false;

        int rowsDeleted = db.delete("Bill", "id = ?", new String[]{id});
        return rowsDeleted > 0;
    }
}
