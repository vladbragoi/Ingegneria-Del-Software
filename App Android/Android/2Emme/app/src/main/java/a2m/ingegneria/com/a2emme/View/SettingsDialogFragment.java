package a2m.ingegneria.com.a2emme.View;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import a2m.ingegneria.com.a2emme.Controller.MainActivity;
import a2m.ingegneria.com.a2emme.Model.MainValues;
import a2m.ingegneria.com.a2emme.R;

/**
 * Created by Arto on 05/07/17.
 */

public class SettingsDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_credits, container, false);

        Button logout = (Button) v.findViewById(R.id.button_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainValues.getInstance().getUser() != null) {
                    MainValues.getInstance().setUser(null);
                    Toast.makeText(getContext(), "Logout effettuato", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }

                dismiss();
            }
        });
        return v;
    }




}
