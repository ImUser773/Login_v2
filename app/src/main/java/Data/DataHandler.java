package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import model.User;

/**
 * Created by IamUser773 on 24/5/2559.
 */
public class DataHandler extends SQLiteOpenHelper {
    //กำหนดชื่อ และเวอร์ชั่นของฐานข้อมูล
    private static final String DATABASE_NAME ="login";
    private static final int DATABASE_VERSION = 1;
    //กำหนดชื่อตารางต่างๆ
    private static final String ID ="id";
    private static final String TABLE_NAME ="user_tb";
    private static final String USER_NAME ="username";
    private static final String PASSWORD ="password";
    private static final String EMAIL ="email";
    private static final String PICTURE ="picture";
    private final ArrayList<User> UserList = new ArrayList<>();



    //รับค่า context จาก activity ที่เรียกใช้
    public DataHandler(Context context) {
        //กำหนดชื่อ และเวอร์ชั่นฐานข้อมูล
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         //คำสั่งSQlใช้ในการสร้างฐานข้อมูล
        String CREATE_SQL = "CREATE TABLE " + TABLE_NAME + "(" +ID+ " INTEGER PRIMARY KEY, "
                + USER_NAME + " TEXT, " + PASSWORD + " TEXT, " + EMAIL + " TEXT, " + PICTURE + " TEXT);";
        db.execSQL(CREATE_SQL);
    }

    //อัพเดทฐานข้อมูล
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +DATABASE_NAME);
        Log.v("UPDATE_DATABASE","Update database success");
        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_NAME,user.getUsername());
        values.put(PASSWORD,user.getPassword());
        values.put(EMAIL,user.getEmail());
        values.put(PICTURE,user.getPicture());

        db.insert(TABLE_NAME,null,values);

        db.close();
        Log.d("ADD USER","ADD USER NOW");
    }

    public ArrayList<User> getUser(String username, String password){
        UserList.clear();

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USER_NAME + " = ?" + " AND " + PASSWORD + " = ?", new String[]{username,password});
        try {
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setUsername(cursor.getString(cursor.getColumnIndex(USER_NAME)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
                    user.setPicture(cursor.getString(cursor.getColumnIndex(PICTURE)));

                    UserList.add(user);
                } while (cursor.moveToNext());
            }
        }catch (SQLException e){
            Log.d("NO SUCH TABLE", e.toString());
        }
        cursor.close();
        db.close();


        return UserList;
    }
}
