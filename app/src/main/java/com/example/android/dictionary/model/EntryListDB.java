package com.example.android.dictionary.model;

/**
 * Created by hernandez on 10/18/2016.
 */
public class EntryListDB {

    // In the class EntryListDB a user can store as many dictionary entries as
    // he or she would like. However, Wordlist only holds the 1,000.

    public static abstract class NewEntryItem{

        // ENTRY_ID is the primary key. If the item does not have a unique identifier,
        // we must use an Item ID. Later, use the “ROW_NUMBER” incrementer to assign a value
        // to this Item ID or primary key.
        // This “ROW_NUMBER” needs to be stored and retrieved
        // from a SharedPreferences file.

        public static final String ENTRY_ID = "entry_id";
        public static final String WORD = "word";
        public static final String DEFINITION = "definition";

        // Table name

    public static final String TABLE_NAME = "entry_list";

    }


}
