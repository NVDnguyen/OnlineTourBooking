package com.example.bookingapp.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookingapp.data.database.DatabaseHelper;
import com.example.bookingapp.data.model.PlaceBill;

import java.util.ArrayList;
import java.util.List;

public class PlaceBillRepository {
    private final SQLiteDatabase db;

    public PlaceBillRepository(Context context) {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);
        this.db = dbHelper.getWritableDatabase();
    }

    /**
     * Create a new PlaceBill in the database.
     * Returns the generated ID on success, -1 on failure.
     */
    public long createPlaceBill(PlaceBill placeBill) {
        if (placeBill == null) return -1;

        ContentValues values = new ContentValues();
        values.put("idUser",placeBill.getIdUser());
        values.put("price", placeBill.getPrice());
        values.put("idPlace", placeBill.getIdPlace());
        values.put("ticketNumber", placeBill.getTicketNumber());

        return db.insert("PlaceBill", null, values); // Returns generated row ID
    }

    /**
     * Convert Cursor to PlaceBill object.
     */
    private PlaceBill convertCursorToPlaceBill(Cursor cursor) {
        if (cursor == null) return null;

        return new PlaceBill(
                cursor.getInt(cursor.getColumnIndexOrThrow("id")), // Auto-incremented int ID
                cursor.getFloat(cursor.getColumnIndexOrThrow("price")),
                cursor.getInt(cursor.getColumnIndexOrThrow("ticketNumber")),
                cursor.getInt(cursor.getColumnIndexOrThrow("idPlace")),
                cursor.getInt(cursor.getColumnIndexOrThrow("idUser"))
        );
    }

    /**
     * Get all PlaceBills from the database.
     */
    public List<PlaceBill> getAllPlaceBills() {
        List<PlaceBill> placeBills = new ArrayList<>();
        try (Cursor cursor = db.rawQuery("SELECT * FROM PlaceBill", null)) {
            while (cursor.moveToNext()) {
                PlaceBill placeBill = convertCursorToPlaceBill(cursor);
                if (placeBill != null) {
                    placeBills.add(placeBill);
                }
            }
        }
        return placeBills;
    }

    /**
     * Get a PlaceBill by ID.
     */
    public PlaceBill getPlaceBillById(int id) {
        try (Cursor cursor = db.rawQuery("SELECT * FROM PlaceBill WHERE id = ?", new String[]{String.valueOf(id)})) {
            if (cursor.moveToFirst()) {
                return convertCursorToPlaceBill(cursor);
            }
        }
        return null;
    }

    /**
     * Delete a PlaceBill by ID.
     */
    public boolean deletePlaceBill(int id) {
        int rowsDeleted = db.delete("PlaceBill", "id = ?", new String[]{String.valueOf(id)});
        return rowsDeleted > 0;
    }
}
