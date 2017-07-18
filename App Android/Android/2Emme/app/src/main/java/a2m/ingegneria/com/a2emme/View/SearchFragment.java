package a2m.ingegneria.com.a2emme.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import a2m.ingegneria.com.a2emme.Controller.RecyclerFragmentViewAdapter;
import a2m.ingegneria.com.a2emme.Controller.SearchActivity;
import a2m.ingegneria.com.a2emme.R;

/**
 * Created by vlad on 04/07/17.
 */

public class SearchFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerFragmentViewAdapter mRecyclerViewFragmentDetailAdapter;

    public SearchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.product_list);
        mRecyclerViewFragmentDetailAdapter = new RecyclerFragmentViewAdapter(getContext(), new ArrayList<>(SearchActivity.products));

        mRecyclerView.setAdapter(mRecyclerViewFragmentDetailAdapter);
        return view;
    }
}
