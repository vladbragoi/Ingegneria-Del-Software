package a2m.ingegneria.com.a2emme.View;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import a2m.ingegneria.com.a2emme.Controller.CarrelloActivity;
import a2m.ingegneria.com.a2emme.Model.Carrello;
import a2m.ingegneria.com.a2emme.Model.Product;
import a2m.ingegneria.com.a2emme.R;
import a2m.ingegneria.com.a2emme.View.CarrelloListFragment;

/**
 * Created by Arto on 10/07/17.
 */

public class GenerateOrder extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_buy_done, container, false);

        Carrello.getInstance().removeAll();
        CarrelloActivity.setFragments();

        return v;
    }
}