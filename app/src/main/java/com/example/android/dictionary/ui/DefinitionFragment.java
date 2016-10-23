package com.example.android.dictionary.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.dictionary.R;

/**
 * Created by hernandez on 10/18/2016.
 */
public class DefinitionFragment extends Fragment {

    TextView definition_textView;
    private String mDefinition;

    public DefinitionFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_definition, container, false);

        definition_textView = (TextView) view.findViewById(R.id.definition_textView);

        mDefinition = getArguments().getString(
                getString(R.string.DEFINITION));

        definition_textView.setText(mDefinition);

        return view;
    }

}
