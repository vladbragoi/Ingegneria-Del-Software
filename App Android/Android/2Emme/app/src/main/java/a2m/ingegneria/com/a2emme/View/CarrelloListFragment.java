package a2m.ingegneria.com.a2emme.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import a2m.ingegneria.com.a2emme.Controller.CarrelloActivity;
import a2m.ingegneria.com.a2emme.Controller.RecyclerFragmentViewAdapterCarrello;
import a2m.ingegneria.com.a2emme.Model.Carrello;
import a2m.ingegneria.com.a2emme.R;

/**
 * Created by Arto on 03/07/17.
 */

public class CarrelloListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerFragmentViewAdapterCarrello mRecyclerViewFragmentDetailAdapter;

    public CarrelloListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_carrello_list, container, false);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.carrello_list);
        mRecyclerViewFragmentDetailAdapter = new RecyclerFragmentViewAdapterCarrello(Carrello.getInstance().getProducts());

        mRecyclerView.setAdapter(mRecyclerViewFragmentDetailAdapter);
        return view;
    }
}
