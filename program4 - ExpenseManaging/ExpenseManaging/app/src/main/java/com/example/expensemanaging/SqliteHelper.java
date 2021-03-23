package com.example.expensemanaging;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {

    private	static final int DATABASE_VERSION =	5;
    private	static final String	DATABASE_NAME = "Expense";
    private	static final String TABLE_EXPENSES = "Expenses";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_NOTE = "Note";

    public SqliteHelper(MainActivity context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXPENSES_TABLE = "CREATE	TABLE " + TABLE_EXPENSES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT," + COLUMN_CATEGORY + " TEXT, " + COLUMN_DATE + " TEXT," + COLUMN_AMOUNT + " FLOAT," + COLUMN_NOTE + " TEXT" + ")";
        db.execSQL(CREATE_EXPENSES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        onCreate(db);
    }

    public List<Expense> listExpenses(){
        String sql = "select * from " + TABLE_EXPENSES;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Expense> Expenses = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                String category = cursor.getString(2);
                String date = cursor.getString(3);
                float amount = Float.parseFloat(cursor.getString(4));
                String note = cursor.getString(5);
                Expenses.add(new Expense(id, name, category,date,amount,note));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return Expenses;
    }

    public void addExpense(Expense expense){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, expense.getName());
        values.put(COLUMN_CATEGORY, expense.getCategory());
        values.put(COLUMN_DATE, expense.getDate());
        values.put(COLUMN_AMOUNT, expense.getAmount());
        values.put(COLUMN_NOTE, expense.getNote());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_EXPENSES, null, values);
    }

    public void updateExpense(Expense expense){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, expense.getName());
        values.put(COLUMN_CATEGORY, expense.getCategory());
        values.put(COLUMN_DATE, expense.getDate());
        values.put(COLUMN_AMOUNT, expense.getAmount());
        values.put(COLUMN_NOTE, expense.getNote());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_EXPENSES, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(expense.getId())});
    }

    public ArrayList<String> selectCategories(){
        String sql = "select DISTINCT category from " + TABLE_EXPENSES;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> categories = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                String category = cursor.getString(0);
                categories.add(category);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return categories;
    }

    public void deleteExpense(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSES, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }
}
