package a2m.ingegneria.com.a2emme;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import a2m.ingegneria.com.a2emme.Model.Carrello;
import a2m.ingegneria.com.a2emme.Model.MainValues;
import a2m.ingegneria.com.a2emme.Model.Product;

/**
 * Created by Arto on 05/07/17.
 */

public class SelectShipmentDialog extends DialogFragment {

    private ImageButton mBartolini;
    private ImageButton mGls;
    private ImageButton mPosta;
    private ImageButton mUps;
    private TextView mSSPrice;
    private TextView mTotalPrice;
    private Button mExitButton;
    private Button continueButton;
    private Bundle bundle;
    private String shipmentMethod;
    private boolean setted;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_shipment, container, false);

        bundle = new Bundle();

        mBartolini = (ImageButton) v.findViewById(R.id.bartolini_button);
        mGls = (ImageButton) v.findViewById(R.id.gls_button);
        mPosta = (ImageButton) v.findViewById(R.id.posta_button);
        mUps = (ImageButton) v.findViewById(R.id.ups_button);
        mExitButton = (Button) v.findViewById(R.id.exit_spedizione_button);
        continueButton = (Button) v.findViewById(R.id.conferma_spedizione_button);
        mSSPrice = (TextView) v.findViewById(R.id.prezzo_spedizione_dialog);
        mTotalPrice = (TextView) v.findViewById(R.id.prezzo_totale_dialog);

        mBartolini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shipmentMethod = "Bartolini";
                setSSPrice(10);
            }
        });
        mGls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shipmentMethod = "Gls";
                setSSPrice(12);
            }
        });
        mPosta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shipmentMethod = "Poste";
                setSSPrice(4);
            }
        });
        mUps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shipmentMethod = "Ups";
                setSSPrice(7);
            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (setted) {
                    SelectPaymentDialog selectPaymentDialog = new SelectPaymentDialog();
                    selectPaymentDialog.setCancelable(false);
                    selectPaymentDialog.setArguments(bundle);
                    selectPaymentDialog.show(getFragmentManager(), "Pay_Set");
                    dismiss();
                } else {
                    Toast toast = Toast.makeText(getContext(), "Seleziona la modalità di spedizione", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0 , 0 );
                    toast.show();
                }
            }
        });
        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }


    private void setSSPrice (float ssPrice) {
        if (MainValues.getInstance().getUser().isBonus())
            ssPrice = 0;
        mSSPrice.setText("Prezzo spese di spedizione\t\t\t€ " + String.format("%.2f", ssPrice));
        updateTotalPrice(ssPrice);
    }

    private void updateTotalPrice (float ssPrice) {
        float totalPrice = 0;
        for (Product element : Carrello.getInstance().getProducts()) {
            totalPrice += element.getPrice();
        }
        totalPrice += ssPrice;
        bundle.putString("corriere", shipmentMethod);
        bundle.putFloat("TOTALE", totalPrice);
        mTotalPrice.setText("Prezzo finale\t\t\t€ " + String.format("%.2f", totalPrice));
        setted = true;
    }

}
