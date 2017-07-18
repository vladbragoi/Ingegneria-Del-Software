package a2m.ingegneria.com.a2emme.Controller;

import android.content.DialogInterface;

import a2m.ingegneria.com.a2emme.Model.MainValues;

/**
 * Created by vlad on 14/07/17.
 */

public class ProgressDialogListener implements DialogInterface.OnDismissListener {

    public ProgressDialogListener() {
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        MainValues.getInstance().updateCatalog();
        MainActivity.refreshFragments();
    }
}
