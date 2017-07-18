package a2m.ingegneria.com.a2emme.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Catalogo {

    private final List<Product> products = new ArrayList<Product>();
    private final Map<String, Product> product_map = new HashMap<String, Product>();

    private static Catalogo catalogo;

    private Catalogo() {}

    public static Catalogo getInstance() {
        if(catalogo == null)
            catalogo = new Catalogo();
        return catalogo;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Map<String, Product> getProduct_map() {
        return product_map;
    }

    public void setProducts(List<Product> productList) {
        products.clear();
        for (Product product : productList) {
            products.add(product);
            product_map.put(product.id, product);
        }
    }

    public void sortByID() {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.id.compareTo(o2.id);
            }
        });
    }

    public void sortByAuthor() {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getAuthor().compareTo(o2.getAuthor());
            }
        });
    }

    public void sortByTitle() {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
    }

    public void sortByPrice() {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return (int)(o1.getPrice() - o2.getPrice());
            }
        });
    }
}
