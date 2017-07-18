package a2m.ingegneria.com.a2emme.Controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import a2m.ingegneria.com.a2emme.Model.MainValues;
import a2m.ingegneria.com.a2emme.Model.Product;
import a2m.ingegneria.com.a2emme.R;
import a2m.ingegneria.com.a2emme.View.ProductDetailFragment;

/**
 * Created by vlad on 28/06/17.
 */

public class RecyclerFragmentViewAdapter
        extends RecyclerView.Adapter<RecyclerFragmentViewAdapter.ViewHolder> {

    private final List<Product> mValues;
    private Context mContext;

    public RecyclerFragmentViewAdapter(Context context, List<Product> items) {
        mContext = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerFragmentViewAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        float price = mValues.get(position).getPrice();
        String tmp = String.format("%.2f", price);
        if (MainValues.getInstance().getUser() != null) {
            if (MainValues.getInstance().getUser().isBonus()) {
                float bonusPriceValue = (price - ((price * 10) / 100));
                String bonusValue = String.format("%.2f", bonusPriceValue);
                holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.price.setTextSize(12);
                holder.bonusPrice.setText("€ " + bonusValue);
            } else {
                holder.bonusPrice.setText("");
            }
            }
        else
        {
            holder.bonusPrice.setText("");
            }
        holder.price.setText("€ " + tmp);
        holder.mAuthorView.setText(mValues.get(position).getAuthor());
        holder.mAlbumView.setText(mValues.get(position).getTitle());
        holder.roundCover.setImageDrawable(mValues.get(position).getSmallCover());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainValues.getInstance().getTwoPane()) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ProductDetailFragment.item_id, holder.mItem.id);

                    ProductDetailFragment fragment = new ProductDetailFragment();
                    fragment.setArguments(arguments);
                    ((MainActivity) mContext).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.product_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra(ProductDetailFragment.item_id, holder.mItem.id);

                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mAuthorView;
        public final TextView mAlbumView;
        public final TextView price;
        public final TextView bonusPrice;
        public final ImageView roundCover;
        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAuthorView = (TextView) view.findViewById(R.id.author);
            mAlbumView = (TextView) view.findViewById(R.id.album);
            price = (TextView) view.findViewById(R.id.price);
            bonusPrice = (TextView) view.findViewById(R.id.bonus_price);
            roundCover = (ImageView) view.findViewById(R.id.cover);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mAlbumView.getText() + "'";
        }
    }
}