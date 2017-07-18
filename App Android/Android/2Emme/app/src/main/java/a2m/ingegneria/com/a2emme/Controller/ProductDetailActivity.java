package a2m.ingegneria.com.a2emme.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ImageView;

import a2m.ingegneria.com.a2emme.Model.Carrello;
import a2m.ingegneria.com.a2emme.Model.Catalogo;
import a2m.ingegneria.com.a2emme.R;
import a2m.ingegneria.com.a2emme.View.ProductDetailFragment;

import static a2m.ingegneria.com.a2emme.View.ProductDetailFragment.item_id;

/**
 * An activity representing a single Product detail screen.
 */
public class ProductDetailActivity extends AppCompatActivity {

    private Catalogo mCatalogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        ImageView coverImage = (ImageView) findViewById(R.id.cover_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(item_id,
                    getIntent().getStringExtra(item_id));
            ProductDetailFragment fragment = new ProductDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.product_detail_container, fragment)
                    .commit();

            mCatalogo = Catalogo.getInstance();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hai aggiunto un nuovo prodotto al carrello!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // Prova
                Carrello.getInstance().addProduct(mCatalogo.getProduct_map().get(getIntent().getStringExtra(item_id)));
            }
        });
        coverImage.setImageDrawable(mCatalogo.getProduct_map().get(getIntent().getStringExtra(ProductDetailFragment.item_id)).getCover());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
