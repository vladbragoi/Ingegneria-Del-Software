package a2m.ingegneria.com.a2emme.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import a2m.ingegneria.com.a2emme.Model.Catalogo;
import a2m.ingegneria.com.a2emme.Model.Product;
import a2m.ingegneria.com.a2emme.R;
import a2m.ingegneria.com.a2emme.View.SearchFragment;

/**
 * Created by vlad on 27/06/17.
 */

public class SearchActivity extends AppCompatActivity {

    public static final Set<Product> products = new HashSet<Product>();

    private FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final EditText searchText = (EditText) findViewById(R.id.search_text);
        final ImageButton searchButton = (ImageButton) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                products.clear();
                searchProduct(searchText.getText());
                setFragmentsSearch();
                if (products.size() == 0) {
                    Toast t = Toast.makeText(getApplicationContext(), "Nessun elemento trovato", Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    t.show();
                }
            }
        });
    }

    public void setFragmentsSearch() {
        mFragmentManager = getSupportFragmentManager();
        SearchFragment fragment = new SearchFragment();
        mFragmentManager.beginTransaction().replace(R.id.search_container, fragment)
                .commit();
    }


    private void searchProduct(Editable text) {
        for (Product product : Catalogo.getInstance().getProducts())
            if (product.find(text))
                this.products.add(product);
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