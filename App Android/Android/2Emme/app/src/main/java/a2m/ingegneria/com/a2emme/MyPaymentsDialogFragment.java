package a2m.ingegneria.com.a2emme;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.couchbase.lite.Revision;
import com.couchbase.lite.SavedRevision;
import com.couchbase.lite.UnsavedRevision;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import a2m.ingegneria.com.a2emme.Controller.MainActivity;
import a2m.ingegneria.com.a2emme.Model.MainValues;
import a2m.ingegneria.com.a2emme.Model.User;

import static android.R.attr.firstDayOfWeek;
import static android.R.attr.name;
import static android.R.attr.numberPickerStyle;
import static android.R.attr.password;
import static android.R.attr.process;

/**
 * Created by Arto on 01/07/17.
 */

public class MyPaymentsDialogFragment extends DialogFragment {

    private int mNum;
    private Bundle bundle;
    private String paymentNumber, paymentMethod;

    /**
     * Create a new instance of MyPaymentsDialogFragment, providing "num"
     * as an argument.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("method");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_mypayments, container, false);

        Button mConfermRegistrazion, mExit;
        TextView mTextView;
        ImageView mImageView;
        final EditText mCodPayment;

        final CheckBox mPrivacy;

        mImageView = (ImageView) v.findViewById(R.id.pay_image);
        mTextView = (TextView) v.findViewById(R.id.pay_text);
        mCodPayment = (EditText) v.findViewById(R.id.reg_pay_text);
        mPrivacy = (CheckBox) v.findViewById(R.id.reg_privacy);

        bundle = getArguments();

        switch (mNum) {
            case 0:
                mImageView.setImageResource(R.mipmap.iban_logo);
                mTextView.setText(R.string.iban_text);
                paymentMethod = "iban";
                break;
            case 1:
                mImageView.setImageResource(R.mipmap.carta_logo);
                mTextView.setText(R.string.cc_text);
                paymentMethod = "carta";
                break;
            case 2:
                mImageView.setImageResource(R.drawable.paypal_logo);
                mTextView.setText(R.string.paypal_text);
                paymentMethod = "paypal";
                break;
        }

        mConfermRegistrazion = (Button) v.findViewById(R.id.reg_finish);
        mConfermRegistrazion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (checkField(mCodPayment) && checkPrivacy(mPrivacy)) {

                paymentNumber = mCodPayment.getText().toString();

                registerUser();

                dismiss();
            }
            }
        });


        mExit = (Button) v.findViewById(R.id.pay_exit);
        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentsDialogFragment paymentsDialogFragment = new PaymentsDialogFragment();
                paymentsDialogFragment.setArguments(bundle);
                paymentsDialogFragment.setCancelable(false);
                paymentsDialogFragment.show(getFragmentManager(), "BackToPayment");
                dismiss();
            }
        });

        return v;
    }

    private void registerUser() {

        String  nome = bundle.getString("nome"),
                cognome = bundle.getString("cognome"),
                codice_fiscale = bundle.getString("codice_fiscale"),
                username = bundle.getString("username"),
                password = bundle.getString("password"),
                indirizzo = bundle.getString("indirizzo");
        long    telefono = bundle.getLong("telefono");

        MainValues.getInstance().setUser(new User(nome, cognome, codice_fiscale, username, password, indirizzo, telefono, paymentMethod, paymentNumber, false));
        MainValues.getInstance().getUser().toDocument();

        getActivity().finish();
        startActivity(getActivity().getIntent());
    }

    boolean checkField (EditText toTest) {
        if (toTest.getText().toString().trim().equalsIgnoreCase("")) {
            toTest.setError("Il campo non può essere vuoto");
            return false;
        }
        return true;
    }

    boolean checkPrivacy (CheckBox toTest) {
        if (!toTest.isChecked()) {
            toTest.setError("Accetta la nostra politica della privacy");
            return false;
        }
        return true;
    }
}
