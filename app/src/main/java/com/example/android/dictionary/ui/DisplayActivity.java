package com.example.android.dictionary.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;

import com.example.android.dictionary.R;

public class DisplayActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent intent = getIntent();

        Parcelable[] parcelables = intent.getParcelableArrayExtra(getString(R.string.ENTRY_LIST));

        int mRowNumber = intent.getIntExtra(getString(R.string.ROW_NUMBER),0);

        Bundle bundle = new Bundle();

        bundle.putParcelableArray(getString(R.string.ENTRIES_ARRAY), parcelables);
        bundle.putInt(getString(R.string.ROW_NUMBER),mRowNumber);

        WordFragment wordFragment = new WordFragment();
        wordFragment.setArguments(bundle);

        if(savedInstanceState == null){

            getSupportFragmentManager().beginTransaction().add(R.id.word_list, wordFragment).commit();
        }

    }

}
