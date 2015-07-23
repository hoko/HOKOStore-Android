package com.hoko.hokostore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hokolinks.Hoko;
import com.hokolinks.deeplinking.annotations.DeeplinkMetadata;
import com.hokolinks.deeplinking.annotations.DeeplinkRoute;
import com.hokolinks.deeplinking.annotations.DeeplinkRouteParameter;

import org.json.JSONObject;

@DeeplinkRoute("product/:product_id")
public class ProductViewActivity extends Activity {

    @DeeplinkRouteParameter("product_id")
    private int productID;

    @DeeplinkMetadata
    public JSONObject metadata;

    private Coupon currentCoupon;

    private Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        if (!Hoko.deeplinking().inject(this)) {
            Intent intent = getIntent();

            this.productID = intent.getIntExtra("id", 0);
            Product myProduct = Product.getProductWithId(this.productID);
            this.currentCoupon = HOKOStoreApplication.getAvailableDiscountForProduct(this, this.productID);

            setupActivityWithProduct(myProduct);

        } else {
            Product myProduct = Product.getProductWithId(this.productID);
            if (myProduct != null) {
                if (this.metadata != null) {
                    String couponName = metadata.optString("coupon");
                    double couponDiscount = metadata.optDouble("value");

                    if (couponName != null) {
                        this.currentCoupon = new Coupon(couponName, couponDiscount);
                        HOKOStoreApplication.saveCouponForProduct(this, this.productID, this.currentCoupon);

                        this.showPopup("Congratulations",
                                "You just redeemed '" + this.currentCoupon.getName() +
                                        "' coupon and received a discount of $" +
                                        this.currentCoupon.getValue() + " because you clicked on the right link!",
                                "Awesome!");
                    }
                }

                // no coupon, display product normally
                setupActivityWithProduct(myProduct);

            } else {
                //error, there is no product
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //mostrar o popup
    }

    private void setupActivityWithProduct(Product product) {
        this.currentProduct = product;

        setTitle(product.getTitle());

        ImageView imgView = (ImageView) findViewById(R.id.singleProductImageView);
        imgView.setImageResource(this.currentProduct.getImageResource());

        this.setupPriceWithDiscountTextView();

        TextView nameTextView = (TextView) findViewById(R.id.singleProductNameTextView);
        nameTextView.setText(this.currentProduct.getTitle());

        TextView descriptionTextView = (TextView) findViewById(R.id.singleProductDescriptionTextView);
        descriptionTextView.setText(this.currentProduct.getDescription());

        this.setupBuyButton();
    }

    private void setupPriceWithDiscountTextView() {
        TextView priceTextView = (TextView) findViewById(R.id.singleProductPriceTextView);

        if (this.currentCoupon == null) {
            priceTextView.setText("$" + this.currentProduct.getPrice());
        } else {
            String text = "<b>$" + String.format("%.2f", this.currentProduct.getPrice() -
                    this.currentCoupon.getValue()) + "</b> — ";
            priceTextView.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);

            int length = priceTextView.length();
            text = text + "<font color='red'>$" + this.currentProduct.getPrice() + "</font>";

            priceTextView.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);

            Spannable spannable = (Spannable) priceTextView.getText();
            spannable.setSpan(new StrikethroughSpan(), length, priceTextView.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private void setupBuyButton() {
        Button myButton = (Button) findViewById(R.id.buyNowButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup("Thank you",
                        "An e-pigeon will arrive shortly at your destination with your shiny new product.",
                        "Alright!"
                );
            }
        });
    }

    private void showPopup(String title, String description, String buttonTitle) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ProductViewActivity.this);
        alertBuilder.setTitle(title);
        alertBuilder.setMessage(description);
        alertBuilder.setCancelable(true);
        alertBuilder.setPositiveButton(buttonTitle,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

}
