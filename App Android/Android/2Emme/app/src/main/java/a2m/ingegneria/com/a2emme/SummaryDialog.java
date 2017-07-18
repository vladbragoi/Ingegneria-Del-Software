package a2m.ingegneria.com.a2emme;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a2m.ingegneria.com.a2emme.Model.Carrello;
import a2m.ingegneria.com.a2emme.Model.MainValues;
import a2m.ingegneria.com.a2emme.Model.Model;
import a2m.ingegneria.com.a2emme.Model.Product;
import a2m.ingegneria.com.a2emme.View.GenerateOrder;

/**
 * Created by Arto on 06/07/17.
 */

// catturare segnale CB per aggiornare il fragment

public class SummaryDialog extends DialogFragment {

    private static Model vendite;
    private TextView mSelectAlbum;
    private TextView mTotal;
    private Button mConferm;
    private Button mBack;
    private float totalPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_summary, container, false);

        mSelectAlbum = (TextView) v.findViewById(R.id.elenco_acquisti);
        mTotal = (TextView) v.findViewById(R.id.totale_acquisto);
        mConferm = (Button) v.findViewById(R.id.conferma_acquisto);
        mBack = (Button) v.findViewById(R.id.annulla_acquisto);

        String tmp = "";
        List<Product> productList = Carrello.getInstance().getProducts();
        for (Product element : productList) {
            tmp = tmp + element.getTitle() + " - " + element.getAuthor() + "\n";
        }
        mSelectAlbum.setText(tmp);

        totalPrice = getArguments().getFloat("TOTALE");
        tmp = "Totale Acquisto:\t\t€" + String.format("%.2f", totalPrice);
        mTotal.setText(tmp);


        mConferm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerateOrder buyDone = new GenerateOrder();
                buyDone.show(getFragmentManager(), "DONE");

                registerSell();
                dismiss();
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPaymentDialog selectPaymentDialog = new SelectPaymentDialog();
                selectPaymentDialog.setArguments(getArguments());
                selectPaymentDialog.setCancelable(false);
                selectPaymentDialog.show(getFragmentManager(), "BackToSP");
                dismiss();
            }
        });

        return v;
    }

    private void registerSell() {
        if (vendite == null) {
            vendite = new Model(getContext(), "vendite");
            vendite.setAuthentication("ingegneria", "prodottimusicali");
            vendite.setPushReplication();
        }

        Document doc = vendite.getDocument("" + Calendar.getInstance().getTime());
        Map<String, Object> properties = new HashMap<>();

        List<String> articoli = new ArrayList<>();

        List<Product> productList = Carrello.getInstance().getProducts();
        for (Product p : productList) {
            articoli.add(p.getID() + " : " + p.getTitle().toUpperCase() + " - " + p.getAuthor());
        }

        MainValues values = MainValues.getInstance();

        properties.put("cliente", values.getUser().getName() + " " + values.getUser().getLastName());
        properties.put("articoli", articoli);
        properties.put("corriere", getArguments().getString("corriere"));
        properties.put("indirizzo", values.getUser().getAddress());
        properties.put("prezzo", totalPrice);


        try {
            doc.putProperties(properties);
        } catch (CouchbaseLiteException e) {
            vendite.resolveConflicts(properties, doc);
        }
    }
}