package com.example.android.dictionary.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.android.dictionary.R;
import com.example.android.dictionary.model.ReloadListFromDB;
import com.example.android.dictionary.model.Wordlist;


public class MainActivity extends ActionBarActivity {

    private Wordlist mWordlist = new Wordlist();

    private int mRowNumber;

    ReloadListFromDB reloadedList = new ReloadListFromDB();

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

        // Reload the list from the SQLite Database. Since we are not searching for anything
        // we will make searchItem just be blank.

        String searchItem = "";

        mWordlist = reloadedList.reloadListFromDB("sort", searchItem, getApplicationContext());

        mRowNumber = reloadedList.getListSize();



        displayEntriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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


