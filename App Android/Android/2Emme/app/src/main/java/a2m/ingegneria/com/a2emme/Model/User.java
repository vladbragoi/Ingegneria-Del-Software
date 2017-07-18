package a2m.ingegneria.com.a2emme.Model;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;

import java.util.HashMap;
import java.util.Map;

import a2m.ingegneria.com.a2emme.Controller.MainActivity;

/**
 * Created by vlad on 05/07/17.
 */

public class User {
    private String firstName;
    private String lastName;
    private String taxCode;
    private String userName;
    private String password;
    private String address;
    private long telephone;
    private String paymentMethod;
    private String codPaymentMethod;
    private boolean bonus;

    public User(String firstName, String secondName, String taxCode, String userName, String password,
                String address, long telephone, String paymentMethod, String codPaymentMethod, boolean bonus) {
        this.firstName = firstName;
        this.lastName = secondName;
        this.taxCode = taxCode;
        this.userName = userName;
        this.password = password;
        this.address = address;
        this.telephone = telephone;
        this.paymentMethod = paymentMethod;
        this.codPaymentMethod = codPaymentMethod;
        this.bonus = bonus;
    }

    public boolean isBonus() {
        return bonus;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getCodPaymentMethod() {
        return codPaymentMethod;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Object getAddress() {
        return address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String toString() {
        return "Nome: " + firstName + "\nCognome: " + lastName + "\nCodice Fiscale: " + taxCode
                + "\nUsername: " + userName + "\nIndirizzo: " + address + "\nTelefono: " + telephone
                + "\nMetodo di pagamento: " + paymentMethod + "\nConto: " + codPaymentMethod;
    }

    public void toDocument() {

        Map<String, Object> properties = new HashMap<>();

        properties.put("nome", firstName);
        properties.put("cognome", lastName);
        properties.put("codice_fiscale", taxCode);
        properties.put("indirizzo", address);
        properties.put("password", password);
        properties.put("telefono", telephone);
        if (paymentMethod.equals("carta"))
            properties.put("carta", codPaymentMethod);
        else
            properties.put("carta", null);

        if (paymentMethod.equals("iban"))
            properties.put("iban", codPaymentMethod);
        else
            properties.put("iban", null);

        if (paymentMethod.equals("paypal"))
            properties.put("paypal", codPaymentMethod);
        else
            properties.put("paypal", null);

        properties.put("bonus", bonus);

        MainValues values = MainValues.getInstance();
        Document doc = values.getUsersDb().getDocument(userName);
        try {
            doc.putProperties(properties);
        } catch (CouchbaseLiteException e) {
            values.getUsersDb().resolveConflicts(properties, doc);
        }
    }
}
