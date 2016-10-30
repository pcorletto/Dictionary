package com.example.android.dictionary.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.android.dictionary.R;
import com.example.android.dictionary.model.EntryItem;
import com.example.android.dictionary.model.EntryItemAdapter;
import com.example.android.dictionary.model.ReloadListFromDB;
import com.example.android.dictionary.model.Wordlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hernandez on 10/18/2016.
 */
public class WordFragment extends Fragment {

    private EditText searchWordEditText;
    private ImageButton searchWordImageButton;
    private ListView listview;
    private EntryItem[] mEntryItems;
    private List<EntryItem> list = new ArrayList<>();
    private EntryItemAdapter mAdapter;
    private int mRowNumber;
    private String searchItem;

    // Need a newList, for when entries are deleted, a new list is created
    private List<EntryItem> newList = new ArrayList<>();


    private Wordlist mWordlist = new Wordlist();

    ReloadListFromDB reloadedList = new ReloadListFromDB();




    public WordFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_word, container, false);

        searchWordEditText = (EditText) view.findViewById(R.id.searchWordEditText);

        searchWordImageButton = (ImageButton) view.findViewById(R.id.searchWordImageButton);

        listview = (ListView) view.findViewById(R.id.list_view);

        Bundle bundle = this.getArguments();
        Parcelable[] parcelables = bundle.getParcelableArray(getString(R.string.ENTRIES_ARRAY));

        mRowNumber = bundle.getInt(getString(R.string.ROW_NUMBER));

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
                int entryID = listItem.getEntryID();
                String word = listItem.getWord();
                String definition = listItem.getDefinition();

                // Send the definition string from the word fragment
                // to the definition fragment

                DefinitionFragment frag = new DefinitionFragment();
                Bundle args = new Bundle();
                args.putInt(getString(R.string.ENTRY_ID), entryID);
                args.putString(getString(R.string.WORD), word);
                args.putString(getString(R.string.DEFINITION), definition);
                frag.setArguments(args);

                // Inflate the fragment

                getFragmentManager().beginTransaction().replace(R.id.container, frag).commit();

            }
        });

        searchWordImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Clear any previous lists by clearing the adapter.

                EntryItemAdapter mAdapter = (EntryItemAdapter) listview.getAdapter();
                mAdapter.clear();
                mAdapter.notifyDataSetChanged();

                searchItem = searchWordEditText.getText().toString().toLowerCase();

                // Reload the list from the SQLite Database.

                mWordlist = reloadedList.reloadListFromDB("search", searchItem, getContext());

                int count = reloadedList.getListSize();

                for(int i=0; i<count; i++){

                    list.add(mWordlist.getEntryItem(i));

                }

                mAdapter = new EntryItemAdapter(getContext(), list);

                listview.setAdapter(mAdapter);


            }
        });

        return view;

    }


    @Override
    public void onResume(){

        // This method is needed so that, if we delete an item from the database,
        // the list is refreshed from the adapter and reflects one less item
        // Otherwise, the list will still display the old item.

        super.onResume();
        list.clear();

        // Reload the list from the SQLite Database.

        // Reload the list from the SQLite Database. Since we are not searching for anything
        // we will make searchItem just be blank.

        String searchItem = "";

        mWordlist = reloadedList.reloadListFromDB("sort", searchItem, getContext());

        int count = reloadedList.getListSize();

        for(int i=0; i<count; i++){

            list.add(mWordlist.getEntryItem(i));

        }

        mAdapter = new EntryItemAdapter(getContext(), list);

        // Done reloading items from the database

        mAdapter.refresh(list);

        }


    }



