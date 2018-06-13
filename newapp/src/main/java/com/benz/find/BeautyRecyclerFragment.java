package com.benz.find;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class BeautyRecyclerFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beauty_recycler, container, false);
        TextView view = rootView.findViewById(R.id.txView);
        String id = getArguments().getString("ID");
        view.setText(id);
        return rootView;
    }

}
