package a2m.ingegneria.com.a2emme.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import a2m.ingegneria.com.a2emme.Controller.MainActivity;
import a2m.ingegneria.com.a2emme.Controller.ProductDetailActivity;
import a2m.ingegneria.com.a2emme.Model.Catalogo;
import a2m.ingegneria.com.a2emme.Model.Product;
import a2m.ingegneria.com.a2emme.R;

/**
 * A fragment representing a single Product detail screen.
 * This fragment is either contained in a {@link MainActivity}
 * in two-pane mode (on tablets) or a {@link ProductDetailActivity}
 * on handsets.
 */
public class ProductDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String item_id = "_id";

    /**
     * The Product content this fragment is presenting.
     */
    public Product mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(item_id)) {
            // Load the Product content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = Catalogo.getInstance().getProduct_map().get(getArguments().getString(item_id));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_detail, container, false);

        // Show the Product content in a TextView.
        if (mItem != null) {
            float price = mItem.getPrice();
            String tmp = String.format("%.2f", price);

            ((TextView) rootView.findViewById(R.id.title_detail)).setText(mItem.getTitle());
            ((TextView) rootView.findViewById(R.id.autor_detail)).setText(mItem.getAuthor());
            ((TextView) rootView.findViewById(R.id.price_detail)).setText("€ " + tmp);;
            ((TextView) rootView.findViewById(R.id.description_detail)).setText(mItem.getDescription());
            ((TextView) rootView.findViewById(R.id.product_detail)).setText(mItem.getTracks());
            ((TextView) rootView.findViewById(R.id.performers_detail)).setText("Band: " + mItem.getPerformers());
            ((TextView) rootView.findViewById(R.id.muscialInstruments_detail))
                    .setText("Strumenti musicali: " + mItem.getMusicalInstruments().toUpperCase());
            ((TextView) rootView.findViewById(R.id.insertDate_detail)).setText("Data inserimento: " +mItem.getInsertDate());
            ((TextView) rootView.findViewById(R.id.category_detail)).setText("Genere: " + mItem.getCategory());
        }
        return rootView;
    }
}
