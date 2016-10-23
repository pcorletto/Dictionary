package com.example.android.dictionary.model;

/**
 * Created by hernandez on 10/18/2016.
 */
public class Wordlist {

    // The class Wordlist is only going to hold 1,000 dictionary entries
    // taken from EntryListDB, which is an
    // SQLite database of entries you store. However, Wordlist is an array of EntryItem
    // objects that only holds 1,000 dictionary entries.

    public EntryItem[] mEntryItem = new EntryItem[1000];

    public void addEntryItem(EntryItem entryItem, int rowNumber){

        mEntryItem[rowNumber] = entryItem;

    }

    public String getEntryItem(int i){
        return mEntryItem[i].getWord() +
                " " + mEntryItem[i].getDefinition()+ "\n\n";
    }




}
