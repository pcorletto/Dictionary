package com.example.android.dictionary.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by hernandez on 10/29/2016.
 */
public class ReloadListFromDB {

    // Data structures

    private EntryItem mEntryItem;
    private int mRowNumber;
    private Wordlist mWordlist = new Wordlist();

    EntryDbHelper entryDbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    public ReloadListFromDB(){

    }

    public Wordlist reloadListFromDB(String selector, String searchItem, Context context){

        // Initialize entry item
        mEntryItem = new EntryItem();



        // Initialize EntryDbHelper and SQLiteDB
        entryDbHelper = new EntryDbHelper(context);
        sqLiteDatabase = entryDbHelper.getReadableDatabase();

        if (selector.equals("search")) {

                cursor = entryDbHelper.searchEntryItems(searchItem, sqLiteDatabase);

        }

        else if (selector.equals("get")){

            cursor = entryDbHelper.getEntryItem(sqLiteDatabase);
        }

        else if (selector.equals("sort")){

            cursor = entryDbHelper.sortEntryItems(sqLiteDatabase);
        }

        // Initialize the row number

        mRowNumber = 0;

        if(cursor.moveToFirst()){

            do{
                int entry_ID;
                String word, definition;

                entry_ID = cursor.getInt(0);
                word = cursor.getString(1);
                definition = cursor.getString(2);

                mEntryItem = new EntryItem(entry_ID, word, definition);

                mWordlist.addEntryItem(mEntryItem, mRowNumber);

                mRowNumber++;

            }

            while(cursor.moveToNext());
        }

       return mWordlist;
    }

    public int getListSize(){

        // This method is used to count the number of items in the reloaded list

        // Initialize the row number

        mRowNumber = 0;

        if(cursor.moveToFirst()){

            do{

                mRowNumber++;

            }

            while(cursor.moveToNext());
        }
        return mRowNumber;
    }
}
