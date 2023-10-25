package app.netlify.usersmanagementapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class UserDataSource {
    private SQLiteDatabase sqLiteDatabase;
    private Database database;

    public UserDataSource(Context context) {
        database = new Database(context);
    }

    public void open() throws SQLException {
        sqLiteDatabase = database.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public void createUser(User user) {
        ContentValues values = new ContentValues();
        values.put(Database.COLUMN_USERNAME, user.getUsername());
        values.put(Database.COLUMN_EMAIL, user.getEmail());
        values.put(Database.COLUMN_PASSWORD, user.getPassword()); // .hashCode() to password

        sqLiteDatabase.insert(Database.TABLE_USERS, null, values);
    }

    public User loginUser(String identifier, String password){

        Cursor cursor = sqLiteDatabase.query(Database.TABLE_USERS,
                new String[]{Database.COLUMN_ID,
                        Database.COLUMN_USERNAME,
                        Database.COLUMN_EMAIL},
                Database.COLUMN_USERNAME + "=? OR " +
                        Database.COLUMN_EMAIL + "=? AND " +
                        Database.COLUMN_PASSWORD + "=? ",
                new String[]{identifier, identifier, password},
                null, null, null);
        User user = null;
        System.err.println("Bup1");
        if(cursor != null && cursor.moveToFirst()){
            user = cursorToUser(cursor);
            cursor.close();
        }

        return user;
    }
    @SuppressLint("Range")
    private User cursorToUser(Cursor cursor){
        System.err.println("Bup2");
        User user = new User();
        user.setId(cursor.getInt(cursor.getColumnIndex(Database.COLUMN_ID)));
        user.setUsername(cursor.getString(cursor.getColumnIndex(Database.COLUMN_USERNAME)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(Database.COLUMN_EMAIL)));
        System.err.println("Bup3");
        if (user != null) {
            return user;
        } else {
            System.err.println("USER NOT FOUND!");
            return null;
        }
    }
}
