package a2m.ingegneria.com.a2emme;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import a2m.ingegneria.com.a2emme.Controller.MainActivity;
import a2m.ingegneria.com.a2emme.Model.Catalogo;

/**
 * Created by Arto on 10/07/17.
 */

public class ModVisualOrderDialog extends DialogFragment {

    private Button mTitolo;
    private Button mAutore;
    private Button mPrezzo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_mod_visual, container, false);

        mTitolo = (Button) v.findViewById(R.id.button_titolo);
        mAutore = (Button) v.findViewById(R.id.button_autore);
        mPrezzo = (Button) v.findViewById(R.id.button_prezzo);

        mTitolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Catalogo.getInstance().sortByTitle();
                MainActivity.refreshFragments();
                dismiss();
            }
        });

        mAutore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Catalogo.getInstance().sortByAuthor();
                MainActivity.refreshFragments();
                dismiss();
            }
        });

        mPrezzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Catalogo.getInstance().sortByPrice();
                MainActivity.refreshFragments();
                dismiss();
            }
        });


        return v;
    }
}