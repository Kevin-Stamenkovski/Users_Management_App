package app.netlify.usersmanagementapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;

public class Database extends SQLiteOpenHelper {
//    public static void main(String[] args){
//        String url = "jdbc:mysql://localhost:3306/users_db";
//
//    }
    private static final String DB_NAME = "user.db";
    private static final int DB_VERSION = 1;

//    Table
public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";


    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_USERNAME + " TEXT, " +
            COLUMN_EMAIL + " TEXT, " +
            COLUMN_PASSWORD + " TEXT);";

    public Database(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
//      Update Db version
    }

    public User getUser(String identifier, String password){
        User user = null;
        

        return user;
    }
}
