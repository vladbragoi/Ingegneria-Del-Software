package a2m.ingegneria.com.a2emme.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import a2m.ingegneria.com.a2emme.Model.Catalogo;
import a2m.ingegneria.com.a2emme.Model.MainValues;
import a2m.ingegneria.com.a2emme.SelectShipmentDialog;
import a2m.ingegneria.com.a2emme.View.CarrelloListFragment;
import a2m.ingegneria.com.a2emme.Model.Carrello;
import a2m.ingegneria.com.a2emme.R;
import a2m.ingegneria.com.a2emme.View.LoginDialogFragment;

/**
 * Created by vlad on 27/06/17.
 */

public class CarrelloActivity extends AppCompatActivity {

    private static Carrello carrello;

    private static FragmentManager mFragmentManager;
    private Button confermaCarrello;
    private static TextView mNumeroAcquisti;
    private static TextView mTotalPrice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrello_view);
        View v = getCurrentFocus();

        Toolbar toolbar = (Toolbar) findViewById(R.id.carrello_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        carrello = Carrello.getInstance();

        mNumeroAcquisti = (TextView) findViewById(R.id.n_pezzi);
        mTotalPrice = (TextView) findViewById(R.id.prezzo_totale);

        confermaCarrello = (Button) findViewById(R.id.conferma_carrello);
        confermaCarrello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Carrello.getInstance().getProducts().size() != 0) {
                    if (MainValues.getInstance().getUser() != null) {
                        SelectShipmentDialog selectShipmentDialog = new SelectShipmentDialog();
                        selectShipmentDialog.setCancelable(false);
                        selectShipmentDialog.show(mFragmentManager, "Spedizione");
                    } else {
                        LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
                        loginDialogFragment.show(mFragmentManager, "LoginFromCarrello");
                    }
                } else {
                    Toast.makeText(CarrelloActivity.this, "Attenzione, il tuo carrello è vuoto!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mFragmentManager = getSupportFragmentManager();
        setFragments();
    }

    public static void setFragments() {
        CarrelloListFragment fragment = new CarrelloListFragment();
        mFragmentManager.beginTransaction().replace(R.id.layout, fragment)
                .commit();

        String tmp = String.format("%.2f", carrello.getTotalPrice());
        mNumeroAcquisti.setText("" + carrello.getNAcquisti());
        mTotalPrice.setText("€ " + tmp);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
