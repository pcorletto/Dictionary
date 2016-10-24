package com.example.android.dictionary.ui;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.dictionary.R;
import com.example.android.dictionary.model.EntryDbHelper;

/**
 * Created by hernandez on 10/18/2016.
 */
public class DefinitionFragment extends Fragment {

    TextView word_textView;
    EditText definition_editText;
    Button updateDefinitionButton, deleteEntryButton;
    private int mEntryID;
    private String mWord, mDefinition, new_definition;

    //private Context mContext;
    EntryDbHelper entryDbHelper;
    SQLiteDatabase sqLiteDatabase;
    //Cursor cursor;

    public DefinitionFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_definition, container, false);

        word_textView = (TextView) view.findViewById(R.id.word_textView);
        definition_editText = (EditText) view.findViewById(R.id.definition_editText);
        updateDefinitionButton = (Button) view.findViewById(R.id.updateDefinitionButton);
        deleteEntryButton = (Button) view.findViewById(R.id.deleteEntryButton);

        mEntryID = getArguments().getInt(
                getString(R.string.ENTRY_ID));

        mWord = getArguments().getString(
                getString(R.string.WORD));

        mDefinition = getArguments().getString(
                getString(R.string.DEFINITION));

        word_textView.setText(mWord);

        definition_editText.setText(mDefinition);

        updateDefinitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new_definition = definition_editText.getText().toString();

                updateDefinition(mWord, new_definition);

                getActivity().finish();

            }
        });

        deleteEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteEntryItem(mEntryID);

                getActivity().finish();

            }
        });

        return view;
    }

    public void updateDefinition(String word, String definition){

        entryDbHelper = new EntryDbHelper(getContext().getApplicationContext());
        sqLiteDatabase = entryDbHelper.getWritableDatabase();

        entryDbHelper.updateDefinition(word, definition, sqLiteDatabase);

    }

    public void deleteEntryItem(int entryID){

        entryDbHelper = new EntryDbHelper(getContext().getApplicationContext());
        sqLiteDatabase = entryDbHelper.getWritableDatabase();

        entryDbHelper.deleteEntryItem(entryID, sqLiteDatabase);

    }

}
