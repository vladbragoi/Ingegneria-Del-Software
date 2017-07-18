package a2m.ingegneria.com.a2emme.Controller;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import a2m.ingegneria.com.a2emme.Model.Carrello;
import a2m.ingegneria.com.a2emme.Model.MainValues;
import a2m.ingegneria.com.a2emme.Model.Product;
import a2m.ingegneria.com.a2emme.R;

/**
 * Created by Arto on 03/07/17.
 */

public class RecyclerFragmentViewAdapterCarrello extends RecyclerView.Adapter<RecyclerFragmentViewAdapterCarrello.ViewHolderCarrello> {

    private final List<Product> mValues;

    public RecyclerFragmentViewAdapterCarrello(List<Product> items) {
        mValues = items;
    }

    @Override
    public ViewHolderCarrello onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carrello_list_content, parent, false);
        return new ViewHolderCarrello(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerFragmentViewAdapterCarrello.ViewHolderCarrello holder, final int position) {
        holder.mItem = mValues.get(position);

        float price = mValues.get(position).getPrice();
        if (MainValues.getInstance().getUser() != null) {
            if (MainValues.getInstance().getUser().isBonus()) {
                price = (price - ((price * 10) / 100));
            }
        }
        String tmp = String.format("%.2f", price);
        holder.priceCarrello.setText("€ " + tmp);
        holder.albumCarrello.setText(mValues.get(position).getTitle());
        holder.coverCarrello.setImageDrawable(mValues.get(position).getCover());

        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Carrello.getInstance().removeProduct(holder.mItem); //Catalogo.product_map.get(mValues.get(position))
                CarrelloActivity.setFragments();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolderCarrello extends RecyclerView.ViewHolder {
        public final TextView albumCarrello;
        public final TextView priceCarrello;
        public final ImageView coverCarrello;
        public ImageButton mDeleteButton;
        public Product mItem;

        public ViewHolderCarrello(View view) {
            super(view);
            albumCarrello = (TextView) view.findViewById(R.id.album_carrello);
            priceCarrello = (TextView) view.findViewById(R.id.price_carrello);
            coverCarrello = (ImageView) view.findViewById(R.id.cover_carrello);
            mDeleteButton = (ImageButton) view.findViewById(R.id.delete_item);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + albumCarrello.getText() + "'";
        }
    }
}