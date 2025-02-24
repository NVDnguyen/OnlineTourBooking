package com.example.bookingapp.data.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BookingApp.db";
    private static final int DATABASE_VERSION = 2;
    private static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE User (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "email TEXT UNIQUE NOT NULL, " +
                "location TEXT, " +
                "isAdmin INTEGER NOT NULL CHECK (isAdmin IN (0,1)))";

        String createFlightTable = "CREATE TABLE Flight (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "price REAL NOT NULL, " + // Added missing price field
                "departureLocation TEXT NOT NULL, " +
                "destinationLocation TEXT NOT NULL, " +
                "departureTime DATETIME NOT NULL, " + // Changed TEXT to DATETIME
                "arrivalTime DATETIME NOT NULL)"; // Changed TEXT to DATETIME

        String createFlightBillTable = "CREATE TABLE FlightBill (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idFlight INTEGER NOT NULL, " +
                "price REAL NOT NULL, " +
                "arrivalTime DATETIME NOT NULL, " + // Changed TEXT to DATETIME
                "departureTime DATETIME NOT NULL, " + // Changed TEXT to DATETIME
                "ticketNumber INTEGER NOT NULL, " +
                "FOREIGN KEY (idFlight) REFERENCES Flight(id))"; // Added foreign key

        String createHotelTable = "CREATE TABLE Hotel (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "location TEXT NOT NULL, " +
                "images TEXT, " + // Added image URLs field
                "rate REAL NOT NULL)";

        String createHotelBillTable = "CREATE TABLE HotelBill (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idHotel INTEGER NOT NULL, " +
                "idUser INTEGER NOT NULL, " + // Added user reference
                "date DATETIME NOT NULL, " + // Changed TEXT to DATETIME
                "price REAL NOT NULL, " +
                "roomType TEXT NOT NULL, " +
                "time INTEGER NOT NULL, " +
                "FOREIGN KEY (idHotel) REFERENCES Hotel(id), " +
                "FOREIGN KEY (idUser) REFERENCES User(id))"; // Added foreign key

        String createPlaceTable = "CREATE TABLE Place (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "overview TEXT, " +
                "location TEXT NOT NULL, " +
                "price REAL NOT NULL, " +
                "images TEXT)"; // Added image URLs field

        String createPlaceBillTable = "CREATE TABLE PlaceBill (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idPlace INTEGER NOT NULL, " +
                "idUser INTEGER NOT NULL, " + // Added user reference
                "price REAL NOT NULL, " +
                "ticketNumber INTEGER NOT NULL, " +
                "FOREIGN KEY (idPlace) REFERENCES Place(id), " +
                "FOREIGN KEY (idUser) REFERENCES User(id))"; // Added foreign key

        String createBillTable = "CREATE TABLE Bill (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idFlightBill INTEGER, " +
                "idHotelBill INTEGER, " +
                "idPlaceBill INTEGER, " +
                "idUser INTEGER NOT NULL, " + // Made user mandatory
                "totalPrice REAL NOT NULL, " +
                "date DATETIME NOT NULL, " + // Changed TEXT to DATETIME
                "FOREIGN KEY (idFlightBill) REFERENCES FlightBill(id), " +
                "FOREIGN KEY (idHotelBill) REFERENCES HotelBill(id), " +
                "FOREIGN KEY (idUser) REFERENCES User(id), " +
                "FOREIGN KEY (idPlaceBill) REFERENCES PlaceBill(id))";

        db.execSQL(createUserTable);
        db.execSQL(createFlightTable);
        db.execSQL(createFlightBillTable);
        db.execSQL(createHotelTable);
        db.execSQL(createHotelBillTable);
        db.execSQL(createPlaceTable);
        db.execSQL(createPlaceBillTable);
        db.execSQL(createBillTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Flight");
        db.execSQL("DROP TABLE IF EXISTS FlightBill");
        db.execSQL("DROP TABLE IF EXISTS Hotel");
        db.execSQL("DROP TABLE IF EXISTS HotelBill");
        db.execSQL("DROP TABLE IF EXISTS Place");
        db.execSQL("DROP TABLE IF EXISTS PlaceBill");
        db.execSQL("DROP TABLE IF EXISTS Bill");
        onCreate(db);
    }



}
