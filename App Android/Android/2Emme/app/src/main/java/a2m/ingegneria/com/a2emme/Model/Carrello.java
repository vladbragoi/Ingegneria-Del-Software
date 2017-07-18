package a2m.ingegneria.com.a2emme.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vlad on 07/07/17.
 */

public class Carrello {

    private static Carrello carrello;
    private final List<Product> products = new ArrayList<Product>();
    private final Map<String, Product> product_map = new HashMap<String, Product>();

    private Carrello() {}

    public static Carrello getInstance() {
        if (carrello == null)
            carrello = new Carrello();
        return carrello;
    }


    public List<Product> getProducts() {
        return products;
    }

    public Map<String, Product> getProduct_map() {
        return product_map;
    }

    public void addProduct (Product newItem) {
        products.add(newItem);
        product_map.put(newItem.id, newItem);
    }

    public void removeProduct (Product deleteItem) {
        products.remove(deleteItem);
        product_map.remove(deleteItem.id);
    }

    public void removeAll() {
        products.clear();
        product_map.clear();
    }

    public int getNAcquisti() {
        return products.size();
    }

    public float getTotalPrice() {
        float totalPrice = 0;
        for (Product element : products)
            totalPrice += element.getPrice();
        if (MainValues.getInstance().getUser() != null) {
            if (MainValues.getInstance().getUser().isBonus())
                totalPrice = (totalPrice - ((totalPrice * 10) / 100));
        }
        return totalPrice;
    }
}
