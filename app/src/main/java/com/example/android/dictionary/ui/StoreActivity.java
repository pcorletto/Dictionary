package com.example.android.dictionary.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.dictionary.R;
import com.example.android.dictionary.model.EntryDbHelper;
import com.example.android.dictionary.model.EntryItem;
import com.example.android.dictionary.model.Wordlist;


public class StoreActivity extends ActionBarActivity {

    private EditText wordEditText, definitionEditText;
    private Button storeButton;

    public static final String TAG = StoreActivity.class.getSimpleName();

    // Data structures

    private EntryItem mEntryItem;
    private int mRowNumber;
    private Wordlist mEntryList = new Wordlist();

    Context context;
    EntryDbHelper entryDbHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    // Member variables

    private int mEntryID;
    private String mWord, mDefinition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        wordEditText = (EditText) findViewById(R.id.wordEditText);
        definitionEditText = (EditText) findViewById(R.id.definitionEditText);
        storeButton = (Button) findViewById(R.id.storeButton);

        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addItem(v);
                finish();
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

                mEntryList.addEntryItem(mEntryItem, mRowNumber);

                mRowNumber++;

            }

            while(cursor.moveToNext());

        }

    }

    public void addItem(View view) {

        context = this;

        // Perform DB insertion...

        // Initialize entryDbhelper object and SQLiteDatabase object.

        entryDbHelper = new EntryDbHelper(context);
        sqLiteDatabase = entryDbHelper.getWritableDatabase();

        mWord = wordEditText.getText().toString();
        mDefinition = definitionEditText.getText().toString();

        // This block only applies if primary key goes from 000 to XXX counter
        // Before inserting the item, retrieve the last value of mEntryID (if we have a primary key that
        // goes from 000 to XXX), which is stored in SharedPref file. If there isn't any previous value, assign zero.

        SharedPreferences sharedPreferences = StoreActivity.this
                .getSharedPreferences(getString(R.string.PREF_FILE), MODE_PRIVATE);
        mEntryID = sharedPreferences.getInt(getString(R.string.ENTRY_ID),0);

        // Add one to entry ID

        mEntryID = mEntryID + 1;

        // If primary key goes from 000 to XXX counter. Block ends here.

        // Insert the item details in the database
        entryDbHelper.addItem(mEntryID+"", mWord, mDefinition, sqLiteDatabase);

        Toast.makeText(StoreActivity.this, "Entry Item # " + mEntryID + " Saved", Toast.LENGTH_LONG).show();

        // Optional block: applies if primary key goes from 000 to XXX counter.

        // Store new mEntryID in SharedPrefs file

        sharedPreferences = StoreActivity.this
                .getSharedPreferences(getString(R.string.PREF_FILE), MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.ENTRY_ID), mEntryID);
        editor.commit();

        // Optional block ends here.

        entryDbHelper.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_store, menu);
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
