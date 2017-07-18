package a2m.ingegneria.com.a2emme;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;

import java.util.HashMap;
import java.util.Map;

import a2m.ingegneria.com.a2emme.Controller.MainActivity;
import a2m.ingegneria.com.a2emme.Model.MainValues;
import a2m.ingegneria.com.a2emme.Model.User;

/**
 * Created by Arto on 30/06/17.
 */

public class SignUpDialogFragment extends DialogFragment {

    private EditText mUserEmail;
    private EditText mUserPassword;
    private EditText mUserCheckPassword;
    private EditText mUserName;
    private EditText mUserSurname;
    private EditText mUserCF;
    private EditText mUserAddress;
    private EditText mUserCell;
    private Button mContinueButton;
    private Button mExitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_sign_up, container, false);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mUserEmail = (EditText)v.findViewById(R.id.reg_user_email);
        mUserPassword = (EditText)v.findViewById(R.id.reg_password);
        mUserCheckPassword = (EditText)v.findViewById(R.id.reg_password_second_time);
        mUserName = (EditText)v.findViewById(R.id.reg_name);
        mUserSurname = (EditText)v.findViewById(R.id.reg_surname);
        mUserCF = (EditText)v.findViewById(R.id.reg_cf);
        mUserAddress = (EditText)v.findViewById(R.id.reg_address);
        mUserCell = (EditText)v.findViewById(R.id.reg_cell);

        mContinueButton = (Button)v.findViewById(R.id.reg_continue);
        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password, cf, email, name, surname, address;
                long tel;

                // Controllo sui valori in input
                if (checkEmail(mUserEmail) &&
                    checkField(mUserPassword) &&
                    checkPassword(mUserCheckPassword) &&
                    checkField(mUserName) &&
                    checkField(mUserSurname) &&
                    checkTaxCode(mUserCF, 16) &&
                    checkField(mUserAddress) &&
                    checkTel(mUserCell)) {

                    name = mUserName.getText().toString();
                    surname = mUserSurname.getText().toString();
                    cf = mUserCF.getText().toString();
                    address = mUserAddress.getText().toString();
                    email = mUserEmail.getText().toString();
                    password = mUserPassword.getText().toString();
                    tel = Long.parseLong(mUserCell.getText().toString().trim());

                    Bundle bundle = new Bundle();
                    bundle.putString("nome", name);
                    bundle.putString("cognome", surname);
                    bundle.putString("codice_fiscale", cf);
                    bundle.putString("indirizzo", address);
                    bundle.putString("username", email);
                    bundle.putString("password", password);
                    bundle.putLong("telefono", tel);
                    bundle.putString("carta", null);
                    bundle.putString("iban", null);
                    bundle.putString("paypal", null);

                    PaymentsDialogFragment paymentsDialogFragment = new PaymentsDialogFragment();
                    paymentsDialogFragment.setCancelable(false);
                    paymentsDialogFragment.setArguments(bundle);
                    paymentsDialogFragment.show(getFragmentManager(), "Payments");
                    dismiss();
                }
            }
        });

        mExitButton = (Button) v.findViewById(R.id.reg_exit);
        mExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }

    private boolean checkField (EditText toTest) {
        if (toTest.getText().toString().trim().equalsIgnoreCase("")) {
            toTest.setError("Il campo non può essere vuoto");
            return false;
        }
        return true;
    }

    private boolean checkPassword(EditText toTest) {
        if (!checkField(toTest))
            return false;

        String p1 = toTest.getText().toString().trim();
        String p2 = mUserPassword.getText().toString().trim();

        if(!p1.equals(p2)) {
            toTest.setError("Le password devono coincidere");
            return false;
        }

        return true;
    }

    private boolean checkEmail (EditText toTest) {
        String user = toTest.getText().toString().trim();

        if (!checkField(toTest))
            return false;

        if (!Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
            toTest.setError("Formato email non valido");
            return false;
        }

        for (User u : MainValues.getInstance().getUsers()) {
            if (u.getUserName().equals(user)) {
                toTest.setError("Utente già registrato");
                return false;
            }
        }

        return true;
    }

    private boolean checkTel(EditText toTest) {
        if (!checkField(toTest)) {
            return false;
        }

        try {
            Long.parseLong(toTest.getText().toString().trim());
            return true;
        } catch (Exception e) {
            toTest.setError("Il campo non deve contenere lettere");
            return false;
        }
    }

    private boolean checkTaxCode(EditText toTest, int maxRange) {
        if (!checkField(toTest))
            return false;

        if (toTest.getText().toString().length() != maxRange) {
            toTest.setError("Il campo deve contenere " + maxRange + " caratteri");
            return false;
        }
        return true;
    }
}