package a2m.ingegneria.com.a2emme.Model;

import android.content.Context;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by vlad on 05/07/17.
 */

public class UsersDb extends Model {

    public UsersDb(Context context, String name) {
        super(context, name);
    }

    public Database getDb() {
        return mDatabase;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        List<Document> documents = super.getAllDocuments();

        String firstName, secondName, taxCode, userName, password, address, paymentMethod = "none", codPaymentMethod;
        long telephone;
        boolean bonus;

        for(Document document : documents) {
            firstName = (String)document.getProperty("nome");
            secondName = (String)document.getProperty("cognome");
            taxCode = (String)document.getProperty("codice_fiscale");
            userName = (String)document.getProperty("_id");
            password = (String)document.getProperty("password");
            address = (String)document.getProperty("indirizzo");
            try {
                bonus = (boolean) document.getProperty("bonus");
            } catch (NullPointerException e) {
                bonus = false;
            }

            String tmp = "" + document.getProperty("telefono");
            if (tmp != null && !tmp.equals("null"))
                telephone = Long.parseLong(tmp);
            else
                telephone = 0000000000;

            codPaymentMethod = (String)document.getProperty("carta");
            if (codPaymentMethod != null && !codPaymentMethod.equals("null"))
                paymentMethod = "Carta";
            else {
                codPaymentMethod = (String) document.getProperty("iban");
                if (codPaymentMethod != null && !codPaymentMethod.equals("null"))
                    paymentMethod = "Iban";
                else {
                    codPaymentMethod = (String) document.getProperty("paypal");
                    if (codPaymentMethod != null && !codPaymentMethod.equals("null"))
                        paymentMethod = "PayPal";
                }

            }
            users.add(new User(firstName, secondName, taxCode, userName, password, address, telephone, paymentMethod, codPaymentMethod, bonus));
        }

        return users;
    }
}

