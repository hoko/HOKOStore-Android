package com.hoko.hokostore;

import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by pedrovieira on 21/07/15.
 */
public class ProductListAdapter extends ArrayAdapter<Product> {

    public ProductListAdapter(Context context, Product[] values) {
        super(context, R.layout.product_row_layout, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductViewHolder productViewHolder;
        if (convertView == null) {
            productViewHolder = new ProductViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.product_row_layout, parent, false);

            productViewHolder.discountBadge = (TextView) convertView.findViewById(R.id.discountBadge);
            productViewHolder.nameLabel = (TextView) convertView.findViewById(R.id.productTextView);
            productViewHolder.imageView = (ImageView) convertView.findViewById(R.id.productImageView);
            convertView.setTag(productViewHolder);
        } else {
            productViewHolder = (ProductViewHolder) convertView.getTag();
        }

        Product currentProduct = getItem(position);
        productViewHolder.nameLabel.setText(currentProduct.getTitle());
        productViewHolder.imageView.setImageResource(currentProduct.getImageResource());

        this.setupDiscountBadge(productViewHolder, position);

        return convertView;
    }

    private void setupDiscountBadge (ProductViewHolder viewHolder, int position) {
        Coupon coupon = HOKOStoreApplication.getAvailableDiscountForProduct(getContext(),  getItem(position).getId());
        if (coupon == null) {
            viewHolder.discountBadge.setVisibility(View.INVISIBLE);
        } else {
            int percentage = (int) (coupon.getValue() * 100 / getItem(position).getPrice() + 0.5);
            String text = percentage == 100 ? "FREE" : "-" + percentage + "%";
            viewHolder.discountBadge.setText(text);
            viewHolder.discountBadge.setVisibility(View.VISIBLE);
        }

    }

    private class ProductViewHolder {
        TextView discountBadge;
        TextView nameLabel;
        ImageView imageView;
    }
}
