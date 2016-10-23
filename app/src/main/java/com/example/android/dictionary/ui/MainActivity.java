package com.example.android.dictionary.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.android.dictionary.R;
import com.example.android.dictionary.model.EntryDbHelper;
import com.example.android.dictionary.model.EntryItem;
import com.example.android.dictionary.model.Wordlist;


public class MainActivity extends ActionBarActivity {

    // Data structures

    private EntryItem mEntryItem;
    private int mRowNumber;
    private Wordlist mWordlist = new Wordlist();

    Context context;
    EntryDbHelper entryDbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;


    private Button storeEntryButton, displayEntriesButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        storeEntryButton = (Button) findViewById(R.id.storeEntryButton);
        displayEntriesButton = (Button) findViewById(R.id.displayEntriesButton);

        // When the storeEntryButton is clicked, the StoreActivity is invoked.

        storeEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, StoreActivity.class);
                startActivity(intent);

            }
        });


        // Initialize entry item

        mEntryItem = new EntryItem();

        //Initialize EntryDbHelper and SQLiteDB

        entryDbHelper = new EntryDbHelper(getApplicationContext());
        sqLiteDatabase = entryDbHelper.getReadableDatabase();

        cursor = entryDbHelper.getEntryItem(sqLiteDatabase);

        // Initialize the Row Number

        mRowNumber = 0;

        if(cursor.moveToFirst()){

            do{

                int entry_ID;
                String word, definition;

                // These corresponds to the columns in the videoDbHelper: video_ID (column 0),
                // rank (col. 1), title (col. 2), author (col. 3), and year (col. 4)

                // See below:

                /*
                private static final String CREATE_QUERY = "CREATE TABLE " + VideoListDB.NewVideoItem.TABLE_NAME +
                "(" + VideoListDB.NewVideoItem.VIDEO_ID + " TEXT," +
                VideoListDB.NewVideoItem.RANK + " INTEGER," +
                VideoListDB.NewVideoItem.TITLE + " TEXT," +
                VideoListDB.NewVideoItem.AUTHOR + " TEXT," +
                VideoListDB.NewVideoItem.YEAR + " INTEGER);"; */

                entry_ID = cursor.getInt(0);
                word = cursor.getString(1);
                definition = cursor.getString(2);


                mEntryItem = new EntryItem(entry_ID, word, definition);

                mWordlist.addEntryItem(mEntryItem, mRowNumber);

                mRowNumber++;

            }

            while(cursor.moveToNext());

        }



        displayEntriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cursor = entryDbHelper.sortEntryItems(sqLiteDatabase);

                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);

                // Next, I will pass in the array of video items, mVideoList, a Playlist object
                // to DisplayActivity.java


                intent.putExtra(getString(R.string.ROW_NUMBER), mRowNumber);

                intent.putExtra(getString(R.string.ENTRY_LIST), mWordlist.mEntryItem);

                startActivity(intent);

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}


