package a2m.ingegneria.com.a2emme.Controller;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.Document;
import com.couchbase.lite.DocumentChange;

import java.util.List;

import a2m.ingegneria.com.a2emme.ModVisualOrderDialog;
import a2m.ingegneria.com.a2emme.Model.MainValues;
import a2m.ingegneria.com.a2emme.View.SettingsDialogFragment;
import a2m.ingegneria.com.a2emme.View.LoginDialogFragment;
import a2m.ingegneria.com.a2emme.Model.CatalogDb;
import a2m.ingegneria.com.a2emme.Model.Catalogo;
import a2m.ingegneria.com.a2emme.Model.UsersDb;
import a2m.ingegneria.com.a2emme.R;
import a2m.ingegneria.com.a2emme.Model.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private NavigationView navigationView;
    private static RecyclerFragmentViewAdapter adapter;
    private MainValues values = MainValues.getInstance();
    private boolean isFirst = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        values.setSwipeContainer((SwipeRefreshLayout)findViewById(R.id.swipeContainer));
        values.getSwipeContainer().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchUsers();
                values.updateCatalog();
                setFragments();
                values.getSwipeContainer().setRefreshing(false);

            }
        });

        values.getSwipeContainer().setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.visualizza_catalogo);
        navigationView.setNavigationItemSelectedListener(this);

        if (findViewById(R.id.product_detail_container) != null)
            values.setTwoPane(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(i);
            }
        });


        if (!MainValues.getInstance().getJustUpdated()) {
            createCatalog();
            fetchUsers();
            values.setJustUpdated(true);
        }

        if (values.getUser() != null) {
            View navHeaderView= navigationView.getHeaderView(0);
            TextView userView= (TextView) navHeaderView.findViewById(R.id.nav_user);
            String tmp = values.getUser().getFirstName();
            userView.setText(tmp);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.login).setVisible(false);
        }

        setFragments();

    }



    private void fetchUsers() {
        // carico la lista degli utenti nel programma, per poter controllare l'autenticazione dell'utente
        UsersDb usersDb = new UsersDb(this, "utenti");
        usersDb.setAuthentication("ingegneria", "prodottimusicali");
        usersDb.setPullReplication();
        usersDb.setPushReplication();
        values.setUsersDb(usersDb);

        values.setUsers(usersDb.getUsers());
    }

    private void setFragments() {
        // imposto i fragments nella activity di default, subito dopo l'avvio del programma
        View recyclerView = findViewById(R.id.product_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        adapter = new RecyclerFragmentViewAdapter(getApplicationContext(), Catalogo.getInstance().getProducts());
        recyclerView.setAdapter(adapter);
    }

    public static void refreshFragments() {
        adapter.notifyDataSetChanged();
    }

    private void createCatalog() {
        values.setCatalogo(new CatalogDb(this, "prodottimusicali"));
        values.getCatalogo().setAuthentication("ingegneria", "prodottimusicali");
        values.getCatalogo().setPullReplication();
        values.getCatalogo().onStopUpdating();

        values.updateCatalog();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent i;

        fragmentManager = getSupportFragmentManager();
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = item.getItemId();


        switch (id) {
            case R.id.visualizza_catalogo:
                Catalogo.getInstance().sortByID();
                refreshFragments();
                break;
            case R.id.mod_visualizzazione:
                ModVisualOrderDialog modVisualOrderDialog = new ModVisualOrderDialog();
                modVisualOrderDialog.show(fragmentManager, "Order");
                break;
            case R.id.carrello:
                i = new Intent(this, CarrelloActivity.class);
                startActivity(i);
                break;
            case R.id.login:
                if (values.getUser() == null) {
                    LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
                    loginDialogFragment.show(fragmentManager, "Login");
                } else

                    Toast.makeText(getApplicationContext(), "Accesso già effettuato", Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.impostazioni:
                SettingsDialogFragment creditsDialogFragment = new SettingsDialogFragment();
                creditsDialogFragment.show(fragmentManager, "Credits");
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
