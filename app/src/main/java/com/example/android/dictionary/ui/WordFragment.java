package com.example.android.dictionary.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.dictionary.R;
import com.example.android.dictionary.model.EntryDbHelper;
import com.example.android.dictionary.model.EntryItem;
import com.example.android.dictionary.model.EntryItemAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hernandez on 10/18/2016.
 */
public class WordFragment extends Fragment {

    private ListView listview;
    private EntryItem[] mEntryItems;
    private List<EntryItem> list = new ArrayList<>();

    // Need a newList, for when entries are deleted, a new list is created
    private List<EntryItem> newList = new ArrayList<>();

    private EntryItemAdapter mAdapter;
    EntryDbHelper entryDbHelper;
    SQLiteDatabase sqLiteDatabase;
    private int mRowNumber;

    public WordFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_word, container, false);

        listview = (ListView) view.findViewById(R.id.list_view);

        Bundle bundle = this.getArguments();
        Parcelable[] parcelables = bundle.getParcelableArray(getString(R.string.ENTRIES_ARRAY));

        mEntryItems = Arrays.copyOf(parcelables, mRowNumber, EntryItem[].class);

        for(int i=0; i<mRowNumber; i++){

            list.add(mEntryItems[i]);

        }

        mAdapter = new EntryItemAdapter(getContext(), list);

        listview.setAdapter(mAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                EntryItem listItem = list.get(position);
                String definition = listItem.getDefinition();

                // Send the definition string from the word fragment
                // to the definition fragment

                DefinitionFragment frag = new DefinitionFragment();
                Bundle args = new Bundle();
                args.putString(getString(R.string.DEFINITION), definition);
                frag.setArguments(args);

                // Inflate the fragment

                getFragmentManager().beginTransaction().replace(R.id.container, frag).commit();

            }
        });

        return view;

    }


    @Override
    public void onResume(){

        super.onResume();
        list.clear();

        // Reload the items from the database

        // Initialize entry item

        EntryItem mEntryItem = new EntryItem();

        //Initialize EntryDbHelper and SQLiteDB

        entryDbHelper = new EntryDbHelper(getContext());
        sqLiteDatabase = entryDbHelper.getReadableDatabase();

        Cursor cursor = entryDbHelper.getEntryItem(sqLiteDatabase);

        mRowNumber = 0;

        if(cursor.moveToFirst()){

            do{

                int entry_ID;
                String word, definition;

                // These corresponds to the columns in the videoDbHelper: expense_ID (column 0),
                // date (col. 1), expense_amount (col. 2), category (col. 3), store (col. 4),
                // and description (col. 5)

                // See sample below:

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

                list.add(mEntryItem);

                mRowNumber++;

            }

            while(cursor.moveToNext());

        }

        // Done reloading items from the database

        mAdapter.refresh(list);

    }


    }



