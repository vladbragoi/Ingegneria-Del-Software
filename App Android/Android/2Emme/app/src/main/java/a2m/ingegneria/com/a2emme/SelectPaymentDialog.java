 package a2m.ingegneria.com.a2emme;

 import android.graphics.Color;
 import android.os.Build;
 import android.os.Bundle;
 import android.support.annotation.RequiresApi;
 import android.support.v4.app.DialogFragment;
 import android.support.v4.content.ContextCompat;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.ImageButton;

 import a2m.ingegneria.com.a2emme.Controller.CarrelloActivity;
 import a2m.ingegneria.com.a2emme.Model.MainValues;
 import a2m.ingegneria.com.a2emme.Model.User;

 /**
 * Created by Arto on 06/07/17.
 */

public class SelectPaymentDialog extends DialogFragment {

     private ImageButton mIban;
     private ImageButton mPaypal;
     private ImageButton mCarta;
     private Button mBackButton;
     private Button mConfermOrderButton;
     private EditText mSetPay;
     private Bundle mBundle;
     private User user;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_select_payment, container, false);

        mIban = (ImageButton) v.findViewById(R.id.select_iban);
        mPaypal = (ImageButton) v.findViewById(R.id.select_paypal);
        mCarta = (ImageButton) v.findViewById(R.id.select_carta);
        mBackButton = (Button) v.findViewById(R.id.pay_back_button);
        mConfermOrderButton = (Button) v.findViewById(R.id.conferm_order_button);
        mSetPay = (EditText) v.findViewById(R.id.set_payment);

        mBundle = new Bundle();
        mBundle.putAll(getArguments());

        mIban.setBackgroundColor(Color.WHITE);
        mCarta.setBackgroundColor(Color.WHITE);
        mPaypal.setBackgroundColor(Color.WHITE);

        user = MainValues.getInstance().getUser();
        String mSelection = user.getPaymentMethod().toUpperCase();

        // showing selected payment method
        switch (mSelection) {
            case "IBAN":
                mIban.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border_button));
                mSetPay.setText(user.getCodPaymentMethod());
                break;
            case "CARTA":
                mCarta.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border_button));
                mSetPay.setText(user.getCodPaymentMethod());
                break;
            case "PAYPAL":
                mPaypal.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border_button));
                mSetPay.setText(user.getCodPaymentMethod());
                break;
        }

        mIban.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                mIban.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border_button));
                mCarta.setBackgroundColor(Color.WHITE);
                mPaypal.setBackgroundColor(Color.WHITE);

                if (user.getPaymentMethod().equalsIgnoreCase("IBAN")) {
                    mSetPay.setText(user.getCodPaymentMethod());
                }
                else {
                    mSetPay.setText("");
                    mSetPay.getText().toString();
                }
            }
        });


        mCarta.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                mCarta.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border_button));
                mIban.setBackgroundColor(Color.WHITE);
                mPaypal.setBackgroundColor(Color.WHITE);

                if (user.getPaymentMethod().equalsIgnoreCase("CARTA")) {
                    mSetPay.setText(user.getCodPaymentMethod());
                }
                else {
                    mSetPay.setText("");
                    mSetPay.getText().toString();
                }
            }
        });

        mPaypal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                mPaypal.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.border_button));
                mCarta.setBackgroundColor(Color.WHITE);
                mIban.setBackgroundColor(Color.WHITE);

                if (user.getPaymentMethod().equalsIgnoreCase("PAYPAL")) {
                    mSetPay.setText(user.getCodPaymentMethod());
                }
                else {
                    mSetPay.setText("");
                    mSetPay.getText().toString();
                }
            }
        });

        mConfermOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSetPay.getText().toString().trim().equalsIgnoreCase("")) {
                    CarrelloActivity.setFragments();
                    SummaryDialog generateOrderDialog = new SummaryDialog();
                    generateOrderDialog.setCancelable(false);
                    generateOrderDialog.setArguments(mBundle);
                    generateOrderDialog.show(getFragmentManager(), "FINE");
                    getDialog().cancel();
                }
                else {
                    mSetPay.setError("Il campo non deve essere vuoto!");
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectShipmentDialog selectShipmentDialog = new SelectShipmentDialog();
                selectShipmentDialog.setCancelable(false);
                selectShipmentDialog.show(getFragmentManager(), "BackToSS");
                dismiss();
            }
        });

        return v;
    }
}