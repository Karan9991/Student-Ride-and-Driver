package com.example.sandhu.Student;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "user_databasee";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "users";
    private static final String TABLE_USER_PAY = "users_pay";
   // private static final String TABLE_USER_SIGN = "signin";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_SID = "studentid";
    private static final String KEY_UNI = "university";
    private static final String KEY_CREDITCARD = "creditcard";
    private static final String KEY_EXPIRY = "expiry";
    private static final String KEY_PHOTO = "photo";
    private static final String KEY_PASS = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    //private static final String KEY_SIGN = "sign";





    private static final String CREATE_TABLE_STUDENTRIDE = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FIRSTNAME + " TEXT, " + KEY_LASTNAME + " TEXT, " + KEY_SID + " TEXT, " + KEY_UNI + " TEXT, " + KEY_PASS + " TEXT, " + KEY_EMAIL + " TEXT, " + KEY_PHONE + " TEXT, " + KEY_PHOTO + " BLOB );";

    private static final String CREATE_TABLE_USER_CITY = "CREATE TABLE "
            + TABLE_USER_PAY + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CREDITCARD + " TEXT, " + KEY_EXPIRY + " TEXT );";
//    private static final String CREATE_TABLE_SIGN = "CREATE TABLE "
//            + TABLE_USER_SIGN + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SIGN + " TEXT );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("table", CREATE_TABLE_STUDENTRIDE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENTRIDE);
        db.execSQL(CREATE_TABLE_USER_CITY);
   //     db.execSQL(CREATE_TABLE_SIGN);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USER + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USER_PAY + "'");
   //     db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USER_SIGN + "'");

        onCreate(db);
    }

    // Saving profile in database
    public void addProfile(UserModel userModel){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_FIRSTNAME, userModel.getFname());
        values.put(KEY_LASTNAME, userModel.getLname());
        values.put(KEY_SID, userModel.getSid());
        values.put(KEY_UNI, userModel.getUni());
        values.put(KEY_PASS, userModel.getPass());
        values.put(KEY_EMAIL, userModel.getEmail());
        values.put(KEY_PHONE, userModel.getPhone());
        values.put(KEY_PHOTO, userModel.getImage());


        db.insert(TABLE_USER, null, values);
        db.close();
    }

//    public void sign(UserModel userModel){
//        SQLiteDatabase db = this.getReadableDatabase();
//        ContentValues values=new ContentValues();
//        values.put(KEY_SIGN, userModel.getSign());
//
//        db.insert(TABLE_USER_SIGN, null, values);
//        db.close();
//    }


    // Getting one profile by id

    public UserModel getMoviep(int movie_id) {
        UserModel userModel = new UserModel();
        SQLiteDatabase db = this.getReadableDatabase();
        //specify the columns to be fetched
        String[] columns = {KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_SID, KEY_UNI, KEY_PASS, KEY_EMAIL,KEY_PHONE, KEY_PHOTO};
        //Select condition
        String selection = KEY_ID + " = ?";
        //Arguments for selection
        String[] selectionArgs = {String.valueOf(movie_id)};


        Cursor cursor = db.query(TABLE_USER, columns, selection,
                selectionArgs, null, null, null);
        if (null != cursor) {
            cursor.moveToFirst();
            userModel.setId(Integer.parseInt(cursor.getString(0)));
            userModel.setFname(cursor.getString(1));
            userModel.setLname(cursor.getString(2));
            userModel.setSid(cursor.getString(3));
            userModel.setUni(cursor.getString(4));
            userModel.setPass(cursor.getString(5));
            userModel.setEmail(cursor.getString(6));
            userModel.setPhone(cursor.getString(7));
            userModel.setImage(cursor.getBlob(8));



        }
        db.close();
        return userModel;

    }
//    public UserModel getsign(int movie_id) {
//        UserModel userModel = new UserModel();
//        SQLiteDatabase db = this.getReadableDatabase();
//        //specify the columns to be fetched
//        String[] columns = {KEY_ID, KEY_SIGN};
//        //Select condition
//        String selection = KEY_ID + " = ?";
//        //Arguments for selection
//        String[] selectionArgs = {String.valueOf(movie_id)};
//
//
//        Cursor cursor = db.query(TABLE_USER_SIGN, columns, selection,
//                selectionArgs, null, null, null);
//        if (null != cursor) {
//            cursor.moveToFirst();
//            userModel.setId(Integer.parseInt(cursor.getString(0)));
//            userModel.setSign(cursor.getString(1));
//
//        }
//        db.close();
//        return userModel;
//
//    }
//Saving payment in database
    public void addpay(String creditcard, String expiry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CREDITCARD, creditcard);
        values.put(KEY_EXPIRY, expiry);

        long id = db.insertWithOnConflict(TABLE_USER_PAY, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    //Getting one user pay by id
    public UserModel getpay(int movie_id) {

        UserModel userModel = new UserModel();
        SQLiteDatabase db = this.getReadableDatabase();
        //specify the columns to be fetched
        String[] columns = {KEY_ID, KEY_CREDITCARD, KEY_EXPIRY};
        //Select condition
        String selection = KEY_ID + " = ?";
        //Arguments for selection
        String[] selectionArgs = {String.valueOf(movie_id)};


        Cursor cursor = db.query(TABLE_USER_PAY, columns, selection,
                selectionArgs, null, null, null);
        if (null != cursor) {
            cursor.moveToFirst();
            userModel.setPid(cursor.getInt(0));
            userModel.setCreditcard(cursor.getString(1));
            userModel.setExpiry(cursor.getString(2));


        }
        db.close();
        return userModel;

    }
    //updating profile
    public void updateUserdatap(int id, String fname, String lname, String sid, String uni,String pass, String email, String phone, byte[] img) {
        SQLiteDatabase db = this.getWritableDatabase();

        // updating name in users table
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, fname);
        values.put(KEY_LASTNAME, lname);
        values.put(KEY_SID, sid);
        values.put(KEY_UNI, uni);
        values.put(KEY_PASS, pass);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PHONE, phone);
        values.put(KEY_PHOTO, img);
        db.update(TABLE_USER, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});

        }
    public void updateUserpassword(int id,String pass) {
        SQLiteDatabase db = this.getWritableDatabase();

        // updating name in users table
        ContentValues values = new ContentValues();
        values.put(KEY_PASS, pass);
        db.update(TABLE_USER, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});

    }
//    public void updatesignin(int id, String sign) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // updating name in users table
//        ContentValues values = new ContentValues();
//        values.put(KEY_SIGN, sign);
//
//        db.update(TABLE_USER_SIGN, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
//
//    }
//    public void deletesignAll()
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//         db.delete(TABLE_USER_SIGN,null,null);
//       // db.execSQL("delete from "+ TABLE_USER_SIGN);
//        db.close();
//    }
        //updating pay
    public void updateUserdatapay(int id, String creditcard, String expiry) {
        SQLiteDatabase db = this.getWritableDatabase();

        // updating name in users table
        ContentValues values = new ContentValues();
        values.put(KEY_CREDITCARD, creditcard);
        values.put(KEY_EXPIRY, expiry);
        db.update(TABLE_USER_PAY, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        }
        //Deleting profile and pay
    public void deleteUSer(int id) {

        // delete row in students table based on id
        SQLiteDatabase db = this.getWritableDatabase();

        //deleting from users table
        db.delete(TABLE_USER, KEY_ID + " = ?",new String[]{String.valueOf(id)});

        //deleting from users_hobby table

        //deleting from users_city table
        db.delete(TABLE_USER_PAY, KEY_ID + " = ?",new String[]{String.valueOf(id)});
    }

//Checking number of rows of profile
    public boolean checkdb(){
        SQLiteDatabase dbc = this.getReadableDatabase() ;

        Cursor mCursor = dbc.rawQuery("SELECT * FROM " + TABLE_USER, null);
        Boolean rowExists;

        if (mCursor.moveToFirst())
        {
            // DO SOMETHING WITH CURSOR
            rowExists = true;

        } else
        {
            // I AM EMPTY
            rowExists = false;
        }
        return rowExists;
    }
    //Checking number of rows of pay
    public boolean checkdbpay(){
        SQLiteDatabase dbc = this.getReadableDatabase() ;

        Cursor mCursor = dbc.rawQuery("SELECT * FROM " + TABLE_USER_PAY, null);
        Boolean rowExists;

        if (mCursor.moveToFirst())
        {
            // DO SOMETHING WITH CURSOR
            rowExists = true;

        } else
        {
            // I AM EMPTY
            rowExists = false;
        }
        return rowExists;
    }
//    public boolean checkdbsign(){
//        SQLiteDatabase dbc = this.getReadableDatabase() ;
//
//        Cursor mCursor = dbc.rawQuery("SELECT * FROM " + TABLE_USER_SIGN, null);
//        Boolean rowExists;
//
//        if (mCursor.moveToFirst())
//        {
//            // DO SOMETHING WITH CURSOR
//            rowExists = true;
//
//        } else
//        {
//            // I AM EMPTY
//            rowExists = false;
//        }
//        return rowExists;
//    }
//count total number of profile records
    public int countRecords(){
        SQLiteDatabase dbc = this.getReadableDatabase() ;
        int numRows = (int)DatabaseUtils.longForQuery(dbc, "SELECT COUNT(*) FROM users", null);
return numRows;
    }
    //count total number of pay records
    public int countPayRecords(){
        SQLiteDatabase dbc = this.getReadableDatabase() ;
        int numRows = (int)DatabaseUtils.longForQuery(dbc, "SELECT COUNT(*) FROM users_pay", null);
        return numRows;
    }
//    public int countsignRecords(){
//        SQLiteDatabase dbc = this.getReadableDatabase() ;
//        int numRows = (int)DatabaseUtils.longForQuery(dbc, "SELECT COUNT(*) FROM signin", null);
//        return numRows;
//    }
}
