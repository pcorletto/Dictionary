package com.example.android.dictionary.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hernandez on 10/18/2016.
 */
public class EntryDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ENTRYLIST.DB";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_QUERY = "CREATE TABLE " + EntryListDB.NewEntryItem.TABLE_NAME +
            "(" + EntryListDB.NewEntryItem.ENTRY_ID + " INTEGER," +
            EntryListDB.NewEntryItem.WORD + " TEXT," +
            EntryListDB.NewEntryItem.DEFINITION + " TEXT);";

    // Default Constructor:

    public EntryDbHelper(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DATABASE OPERATIONS", "Database created / opened ...");

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATIONS", "Table created ...");

    }

    // Insert the item next. Method for inserting the entry item.

    public void addItem(String id, String word, String definition, SQLiteDatabase db){

        // Map key-values

        ContentValues contentValues = new ContentValues();
        contentValues.put(EntryListDB.NewEntryItem.ENTRY_ID, id);
        contentValues.put(EntryListDB.NewEntryItem.WORD, word);
        contentValues.put(EntryListDB.NewEntryItem.DEFINITION, definition);

        // Save all these into the database

        db.insert(EntryListDB.NewEntryItem.TABLE_NAME, null, contentValues);

        Log.e("DATABASE OPERATIONS", "One row is inserted ...");

    }

    public Cursor getEntryItem(SQLiteDatabase db){

        // The return type of Object is "Cursor"
        Cursor cursor;

        // Create projections, or the needed column names
        //String[] projections = {EntryListDB.NewEntryItem.ENTRY_ID,
               // EntryListDB.NewEntryItem.WORD,
               // EntryListDB.NewEntryItem.DEFINITION};

        // We only need the table name and projection parameters. No conditions will be specified,
        // so, we will pass in null for the last five parameters.

        //cursor = db.query(EntryListDB.NewEntryItem.TABLE_NAME, projections, null, null, null, null, null);

        // Modified this method so that the results are sorted in alphabetical ascending order,
        // by the word. It worked!!!

        cursor = db.rawQuery("SELECT * FROM " + EntryListDB.NewEntryItem.TABLE_NAME +
                " ORDER BY " + EntryListDB.NewEntryItem.WORD + " COLLATE NOCASE ASC", null);

        // Another valid way to sort it, case insensitive, in SQL is as follows:
        // cursor = db.rawQuery("SELECT * FROM " + EntryListDB.NewEntryItem.TABLE_NAME +
        //        " ORDER BY LOWER(" + EntryListDB.NewEntryItem.WORD + ") ASC", null);

        return cursor;

    }

    public Cursor sortEntryItems(SQLiteDatabase db){

        Cursor cursor;

        cursor = db.rawQuery("SELECT * FROM " + EntryListDB.NewEntryItem.TABLE_NAME +
                        " ORDER BY " + EntryListDB.NewEntryItem.WORD + " ASC", null);

        return cursor;


    }



    public void deleteEntryItem(int entry_id, SQLiteDatabase sqLiteDatabase){

        String selection = EntryListDB.NewEntryItem.ENTRY_ID + " LIKE ?";

// Use the primary key, or the Item ID (which is stored and retrieved from a SharedPreferences file), for
// selecting the item to be deleted from the DB. This
// will ensure that we will only delete the selected item(s) and not anything else.

        String[] selection_args = {entry_id+""};

        sqLiteDatabase.delete(EntryListDB.NewEntryItem.TABLE_NAME, selection, selection_args);

    }


    public void updateDefinition(String word, String definition, SQLiteDatabase sqLiteDatabase){

        ContentValues contentValues = new ContentValues();
        // THE NEXT FIELD, "DEFINITION", NEEDS TO BE “PUT”. THIS
        //  IS THE ONE THAT I AM UPDATING IN THE DB
        contentValues.put(EntryListDB.NewEntryItem.DEFINITION, definition);

        // Provide a condition or selection

        String selection = EntryListDB.NewEntryItem.WORD + " LIKE ?";

        String[] selection_args = {word};

        sqLiteDatabase.update(EntryListDB.NewEntryItem.TABLE_NAME, contentValues,
                selection, selection_args);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
