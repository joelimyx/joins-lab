package com.joelimyx.sqliteemploye;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.content.ContextCompat;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Joe on 10/28/16.
 */

public class EmployeeHelper extends SQLiteOpenHelper {
    private static EmployeeHelper mInstance;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "employee.db";

    public static final String EMPLOYEE_TABLE = "employee";
    public static final String JOB_TABLE = "job";

    public static final String COL_ID = "id";
    public static final String COL_SSN = "SSN";
    public static final String COL_FIRST_NAME = "first_name";
    public static final String COL_LAST_NAME = "last_name";
    public static final String COL_YOB = "year_of_birth";
    public static final String COL_CITY = "city";

    public static final String COL_COMPANY = "company";
    public static final String COL_SALARY = "salary";
    public static final String COL_EXPERIENCE = "experience";

    public static final String CREATE_EMPLOYEE_TABLE =
            "CREATE TABLE "+EMPLOYEE_TABLE+"("+
            COL_SSN+" INTEGER PRIMARY KEY,"+
            COL_FIRST_NAME+ " TEXT,"+
            COL_LAST_NAME+ " TEXT,"+
            COL_YOB+" TEXT,"+
            COL_CITY+" TEXT )";
    public static final String CREATE_JOB_TABLE =
            "CREATE TABLE "+ JOB_TABLE+"("+
            COL_SSN+" INTEGER PRIMARY KEY,"+
            COL_COMPANY+" TEXT,"+
            COL_SALARY+" INT,"+
            COL_EXPERIENCE+ " TEXT )";

    public static EmployeeHelper getInstance(Context context){
        if (mInstance == null){
            mInstance = new EmployeeHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public EmployeeHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EMPLOYEE_TABLE);
        db.execSQL(CREATE_JOB_TABLE);
        //populateData();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+EMPLOYEE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+JOB_TABLE);
        this.onCreate(db);
    }
    public ContentValues insertValues(int SSN, String first, String last, String YOB, String city){
        ContentValues values = new ContentValues();
        values.put(COL_SSN,SSN);
        values.put(COL_FIRST_NAME,first);
        values.put(COL_LAST_NAME,last);
        values.put(COL_YOB,YOB);
        values.put(COL_CITY,city);
        return values;
    }
    public ContentValues insertJobValues(int SSN, String Company, int Salary, String Experience){
        ContentValues values = new ContentValues();
        values.put(COL_SSN,SSN);
        values.put(COL_COMPANY,Company);
        values.put(COL_SALARY,Salary);
        values.put(COL_EXPERIENCE,Experience);
        return values;
    }

    public void populateData(){

        SQLiteDatabase db = getWritableDatabase();
        //String Table
        ContentValues john = insertValues(123045678,"John","Smith", "1973","NY");
        db.insert(EMPLOYEE_TABLE,null,john);

        ContentValues David = insertValues(123045679, "David", "McWill", "1982", "Seattle");
        db.insert(EMPLOYEE_TABLE,null,David);

        ContentValues Katerina = insertValues(123045680, "Katerina", "Wise", "1973", "Boston");
        db.insert(EMPLOYEE_TABLE,null,Katerina);

        ContentValues Donald = insertValues(123045681, "Donald", "Lee", "1992", "London");
        db.insert(EMPLOYEE_TABLE,null,Donald);

        ContentValues Gary = insertValues(123045682, "Gary", "Henwood", "1987", "Las Vegas");
        db.insert(EMPLOYEE_TABLE,null,Gary);

        ContentValues Anthony = insertValues(123045683, "Anthony", "Bright", "1963", "Seattle");
        db.insert(EMPLOYEE_TABLE,null,Anthony);

        ContentValues William = insertValues(123045684, "William", "Newey", "1995", "Boston");
        db.insert(EMPLOYEE_TABLE,null,William);

        ContentValues Melony = insertValues(123045685, "Melony", "Smith", "1970", "Chicago");
        db.insert(EMPLOYEE_TABLE,null,Melony);

        //JOB table
        ContentValues Fuzz = insertJobValues(123045678, "Fuzz", 60, "1");
        db.insert(JOB_TABLE,null,Fuzz);

        ContentValues GA = insertJobValues(123045679, "GA", 70, "2");
        db.insert(JOB_TABLE,null,GA);

        ContentValues LittlePlace = insertJobValues(123045680, "Little Place", 120, "5");
        db.insert(JOB_TABLE,null,LittlePlace);

        ContentValues Macy = insertJobValues(123045681, "Macy''s", 78, "3");
        db.insert(JOB_TABLE,null,Macy);

        ContentValues NewLife = insertJobValues(123045682, "New Life", 65, "1");
        db.insert(JOB_TABLE,null,NewLife);

        ContentValues Believe = insertJobValues(123045683, "Believe", 158, "6");
        db.insert(JOB_TABLE,null,Believe);

        ContentValues MacyS = insertJobValues(123045684, "Macy''s", 200, "8");
        db.insert(JOB_TABLE,null,MacyS);

        ContentValues Stop = insertJobValues(123045685, "Stop", 299, "12");
        db.insert(JOB_TABLE,null,Stop);

        db.close();
    }

    public List<String> getEmployeeAtMacy(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT "+COL_FIRST_NAME+", "+COL_LAST_NAME+
                " FROM "+EMPLOYEE_TABLE+" JOIN "+JOB_TABLE+
                " ON "+EMPLOYEE_TABLE+"."+COL_SSN+ " = "+
                JOB_TABLE+"."+COL_SSN+
                " WHERE "+COL_COMPANY+" LIKE 'macy%' ",null);

        List<String> list = new LinkedList<>();
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                list.add(cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME))+" "+
                        cursor.getString(cursor.getColumnIndex(COL_LAST_NAME)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public List<String> getCompaniesAtBoston(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT "+COL_COMPANY+" FROM "+
                EMPLOYEE_TABLE+" JOIN "+JOB_TABLE+
                " ON "+EMPLOYEE_TABLE+"."+COL_SSN+" = "+
                JOB_TABLE+"."+COL_SSN+
                " WHERE "+COL_CITY+" LIKE 'boston'",null);
        List<String> list = new LinkedList<>();
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                list.add(cursor.getString(cursor.getColumnIndex(COL_COMPANY)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    public String getHighestSalary(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT "+COL_FIRST_NAME+", "+COL_LAST_NAME+
                " FROM "+ EMPLOYEE_TABLE+" JOIN "+JOB_TABLE+
                " ON "+EMPLOYEE_TABLE+"."+COL_SSN+" = "+
                JOB_TABLE+"."+COL_SSN+
                " ORDER BY "+COL_SALARY+" DESC LIMIT 1",null);

        String highest = "";
        if(cursor.moveToFirst()){
            highest = cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME))+" "+
                    cursor.getString(cursor.getColumnIndex(COL_LAST_NAME));
        }
        cursor.close();
        return highest;
    }
}
