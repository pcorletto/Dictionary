package com.example.android.dictionary.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hernandez on 10/18/2016.
 */
public class EntryItem implements Parcelable {

    // private member variables

    private int mEntryID;
    private String mWord;
    private String mDefinition;

    // Constructors

    public EntryItem(){

    }

    public EntryItem(int entryID, String word, String definition){

        this.mEntryID = entryID;
        this.mWord = word;
        this.mDefinition = definition;


    }

    // Accessor and mutator methods


    public int getEntryID() {
        return mEntryID;
    }

    public void setEntryID(int entryID) {
        mEntryID = entryID;
    }

    public String getWord() {
        return mWord;
    }

    public void setWord(String word) {
        mWord = word;
    }

    public String getDefinition() {
        return mDefinition;
    }

    public void setDefinition(String definition) {
        mDefinition = definition;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {


        dest.writeInt(mEntryID);
        dest.writeString(mWord);
        dest.writeString(mDefinition);


    }

    private EntryItem(Parcel in){

        mEntryID = in.readInt();
        mWord = in.readString();
        mDefinition = in.readString();


    }

    public static final Creator<EntryItem>CREATOR = new Creator<EntryItem>() {

        @Override
        public EntryItem createFromParcel(Parcel source) {
            return new EntryItem(source);
        }

        @Override
        public EntryItem[] newArray(int size) {
            return new EntryItem[size];
        }
    };


}
