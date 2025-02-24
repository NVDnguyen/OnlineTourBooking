package com.example.bookingapp.data.repository;

import static com.example.bookingapp.utils.Constant.DATE_TIME_FORMAT;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookingapp.data.database.DatabaseHelper;
import com.example.bookingapp.data.model.FlightBill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FlightBillRepository {
    private final SQLiteDatabase db;

    public FlightBillRepository(Context context) {
        DatabaseHelper dbHelper = DatabaseHelper.getInstance(context);
        this.db = dbHelper.getWritableDatabase();
    }

    /**
     * Create a new FlightBill in the database.
     * Returns the generated ID on success, -1 on failure.
     */
    public long createFlightBill(FlightBill flightBill) {
        if (flightBill == null) return -1;

        ContentValues values = new ContentValues();
        values.put("idFlight", flightBill.getIdFlight());
        values.put("idUser", flightBill.getIdUser());
        values.put("price", flightBill.getPrice());
        values.put("arrivalTime", new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(flightBill.getArrivalTime()));
        values.put("departureTime", new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()).format(flightBill.getDepartureTime()));
        values.put("ticketNumber", flightBill.getTicketNumber());

        return db.insert("FlightBill", null, values); // Returns new row ID
    }

    /**
     * Convert Cursor to FlightBill object.
     */
    private FlightBill convertCursorToFlightBill(Cursor cursor) {
        if (cursor == null) return null;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());

            return new FlightBill(
                    sdf.parse(cursor.getString(cursor.getColumnIndexOrThrow("arrivalTime"))),
                    sdf.parse(cursor.getString(cursor.getColumnIndexOrThrow("departureTime"))),
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")), // Auto-incremented int ID
                    cursor.getInt(cursor.getColumnIndexOrThrow("idFlight")),
                    cursor.getFloat(cursor.getColumnIndexOrThrow("price")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("ticketNumber")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("idUser"))
            );
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get all FlightBills from the database.
     */
    public List<FlightBill> getAllFlightBills() {
        List<FlightBill> flightBills = new ArrayList<>();
        try (Cursor cursor = db.rawQuery("SELECT * FROM FlightBill", null)) {
            while (cursor.moveToNext()) {
                FlightBill flightBill = convertCursorToFlightBill(cursor);
                if (flightBill != null) {
                    flightBills.add(flightBill);
                }
            }
        }
        return flightBills;
    }

    /**
     * Get a single FlightBill by its ID.
     */
    public FlightBill getFlightBillById(int id) {
        try (Cursor cursor = db.rawQuery("SELECT * FROM FlightBill WHERE id = ?", new String[]{String.valueOf(id)})) {
            if (cursor.moveToFirst()) {
                return convertCursorToFlightBill(cursor);
            }
        }
        return null;
    }

    /**
     * Delete a FlightBill by its ID.
     */
    public boolean deleteFlightBill(int id) {
        int rowsDeleted = db.delete("FlightBill", "id = ?", new String[]{String.valueOf(id)});
        return rowsDeleted > 0;
    }
}
