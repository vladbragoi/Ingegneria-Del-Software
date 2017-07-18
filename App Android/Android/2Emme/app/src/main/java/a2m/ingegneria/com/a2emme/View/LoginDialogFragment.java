package a2m.ingegneria.com.a2emme.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import a2m.ingegneria.com.a2emme.Controller.MainActivity;
import a2m.ingegneria.com.a2emme.Model.MainValues;
import a2m.ingegneria.com.a2emme.Model.User;
import a2m.ingegneria.com.a2emme.R;
import a2m.ingegneria.com.a2emme.SignUpDialogFragment;

/**
 * Created by Arto on 30/06/17.
 */

public class LoginDialogFragment extends DialogFragment {

    private Button mButtonLogin;
    private Button mButtonRegistrazione;
    private EditText textUser;
    private EditText textPassword;
    private MainValues values = MainValues.getInstance();

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_login_interface, container, false);

        textUser = (EditText) view.findViewById(R.id.user_name);
        textPassword = (EditText) view.findViewById(R.id.user_password);

        mButtonLogin = (Button) view.findViewById(R.id.button_login);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user;
                if ((user = (checkUser(textUser, textPassword))) != null) {
                    values.setUser(user);
                    Toast.makeText(getContext(), "Accesso effettuato", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                    dismiss();
                }
            }
        });

        mButtonRegistrazione = (Button) view.findViewById(R.id.button_registrazione);
        mButtonRegistrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpDialogFragment signUpDialogFragment = new SignUpDialogFragment();
                signUpDialogFragment.setCancelable(false);
                signUpDialogFragment.show(getFragmentManager(), "Registrazione");
                dismiss();
            }
        });

        return view;
    }

    private User checkUser(EditText user, EditText pass) {
        if (!checkField(user) || !checkField(pass))
            return null;

        String username = user.getText().toString();
        String password = pass.getText().toString();

        for (User u : values.getUsers())
            if (u.getUserName().equalsIgnoreCase(username)) {
                if (!u.getPassword().equals(password)) {
                    user.setError(null);
                    pass.setError("Password errata");
                } else
                    return u;
            } else
                user.setError("Utente non registrato");

        return null;
    }

    boolean checkField(EditText toTest) {
        if (toTest.getText().toString().trim().equalsIgnoreCase("")) {
            toTest.setError("Il campo non può essere vuoto");
            return false;
        }
        return true;
    }
}