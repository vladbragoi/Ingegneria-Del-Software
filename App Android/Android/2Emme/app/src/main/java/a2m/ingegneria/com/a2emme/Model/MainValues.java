package a2m.ingegneria.com.a2emme.Model;

import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

/**
 * Created by vlad on 10/07/17.
 */

public class MainValues {

    private static MainValues mainValues;

    private boolean mTwoPane;
    private boolean justUpdated;
    private CatalogDb catalogo;
    private UsersDb usersDb;
    private List<User> users;
    private User user;
    private SwipeRefreshLayout swipeContainer;

    public SwipeRefreshLayout getSwipeContainer() {
        return swipeContainer;
    }

    public void setSwipeContainer(SwipeRefreshLayout swipeContainer) {
        this.swipeContainer = swipeContainer;
    }

    private MainValues(){
    }

    public static MainValues getInstance() {
        if (mainValues == null)
            mainValues = new MainValues();
        return mainValues;
    }

    public boolean getTwoPane() {
        return mTwoPane;
    }

    public void setTwoPane(boolean state) {
        this.mTwoPane = state;
    }

    public boolean getJustUpdated() {
        return justUpdated;
    }

    public void setJustUpdated(boolean state) {
        this.justUpdated = state;
    }

    public CatalogDb getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(CatalogDb catalogo) {
        this.catalogo = catalogo;
    }

    public UsersDb getUsersDb() {
        return usersDb;
    }

    public void setUsersDb(UsersDb usersDb) {
        this.usersDb = usersDb;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updateCatalog() {
        Catalogo.getInstance().setProducts(catalogo.getProducts());
    }
}
