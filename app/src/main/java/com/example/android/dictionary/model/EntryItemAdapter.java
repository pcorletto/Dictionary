package com.example.android.dictionary.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.dictionary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hernandez on 10/20/2016.
 */
public class EntryItemAdapter extends ArrayAdapter<EntryItem> {

    private List<EntryItem> list;

    ArrayList<Boolean> positionArray;

    private Context mContext;

    public EntryItemAdapter(Context context, List<EntryItem> list) {
        super(context, R.layout.entry_list_item, list);
        this.mContext = context;
        this.list = list;

        positionArray = new ArrayList<Boolean>(list.size());
        for (int i = 0; i < list.size(); i++) {
            positionArray.add(false);
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public EntryItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        EntryItem entryItem = list.get(position);

        if (convertView == null) {
            //brand new
            convertView = LayoutInflater.from(mContext).inflate(R.layout.entry_list_item, null);

            holder = new ViewHolder();
            holder.wordTextView = (TextView) convertView.findViewById(R.id.wordTextView);

            convertView.setTag(holder);

        } else {
            // We have these views set up.
            holder = (ViewHolder) convertView.getTag();

        }

        // Now, set the data:


        holder.word = this.getItem(position).getWord();

        holder.wordTextView.setText(holder.word);

        return convertView;

        }

    public void refresh(List<EntryItem> list){

        this.list = list;
        notifyDataSetChanged();

    }

    private static class ViewHolder{

        int entryID;
        String word;
        String definition;

        TextView wordTextView;

    }

}
