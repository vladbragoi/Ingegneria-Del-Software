package a2m.ingegneria.com.a2emme;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import a2m.ingegneria.com.a2emme.R;
import a2m.ingegneria.com.a2emme.MyPaymentsDialogFragment;


/**
 * Created by Arto on 01/07/17.
 */

public class PaymentsDialogFragment extends DialogFragment {

    private ImageButton mIban;
    private ImageButton mCreditCard;
    private ImageButton mPaypal;
    private Button mExitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_payments, container, false);

        final Bundle bundle = new Bundle();
        bundle.putAll(getArguments());

        mIban = (ImageButton) v.findViewById(R.id.reg_iban);
        mIban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPaymentsDialogFragment myPaymentsDialogFragment = new MyPaymentsDialogFragment();
                bundle.putInt("method", 0);
                myPaymentsDialogFragment.setCancelable(false);
                myPaymentsDialogFragment.setArguments(bundle);
                myPaymentsDialogFragment.show(getFragmentManager(), "Iban");
                dismiss();
            }
        });

        mCreditCard = (ImageButton) v.findViewById(R.id.reg_carta);
        mCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPaymentsDialogFragment myPaymentsDialogFragment = new MyPaymentsDialogFragment();
                bundle.putInt("method", 1);
                myPaymentsDialogFragment.setCancelable(false);
                myPaymentsDialogFragment.setArguments(bundle);
                myPaymentsDialogFragment.show(getFragmentManager(), "Carta di Credito");
                dismiss();
            }
        });

        mPaypal = (ImageButton) v.findViewById(R.id.reg_paypal);
        mPaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPaymentsDialogFragment myPaymentsDialogFragment = new MyPaymentsDialogFragment();
                bundle.putInt("method", 2);
                myPaymentsDialogFragment.setCancelable(false);
                myPaymentsDialogFragment.setArguments(bundle);
                myPaymentsDialogFragment.show(getFragmentManager(), "Paypal");
                dismiss();

            }
        });

        mExitButton = (Button) v.findViewById(R.id.pay_exit);
        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpDialogFragment signUpDialogFragment = new SignUpDialogFragment();
                signUpDialogFragment.setCancelable(false);
                signUpDialogFragment.show(getFragmentManager(), "BackToSignUp");
                dismiss();
            }
        });

        return v;
    }

}
