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

/**
 * Created by Pedro Vieira on 22/07/15.
 * Copyright © 2015 HOKO. All rights reserved.
 */

// This annotation @DeeplinkRoute will tell the SDK that every time a new deep link is opened
// that matches the route given in the annotation parameter (in this case, "product/:product_id"),
// this Activity will be automatically called to be presented and process the respective deep link.
@DeeplinkRoute("product/:product_id")
public class ProductViewActivity extends Activity {

    // This variable will hold the product ID from the HOKO smart link. For instance,
    // if your URI is 'yourapp://product/2', the variable will get the value '2'.
    // By using the annotation @DeeplinkRouteParameter the SDK will automatically set the value to
    // this var when you use 'Hoko.deeplinking().inject(this)' on the 'onCreate()' method.
    @DeeplinkRouteParameter("product_id")
    private int productID;

    // The HOKO smart link's metadata object variable. Similarly to the previous variable, by using
    // the annotation @DeeplinkMetadata, the SDK will automatically set the value to this var
    // when you use Hoko.deeplinking().inject(this) on the 'onCreate()' method.
    @DeeplinkMetadata
    public JSONObject metadata;

    // A private boolean variable that is used to know whether this Activity was
    // called from a HOKO smart link with a coupon metadata. If so, a new Alert will
    // be displayed.
    private boolean hasRedeemedACoupon;

    // A private variable that will hold the Coupon object, if there is one.
    private Coupon currentCoupon;

    // A private variable that will hold the current Product object.
    private Product currentProduct;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        // The way our SDK sets the corresponding values to the variables described above, is by
        // code Injection via 'Hoko.deeplinking().inject(this)'. By calling this function, all the
        // annotated vars will get their own smart link values.
        //
        // The Hoko's inject method returns a boolean variable that tells you whether the whether
        // this Activity was activated through a HOKO smart link or manually.
        //
        // NOTE: if the 'inject' method returns false, meaning that the Activity was not activated
        // by HOKO, the annotated variables declared above will not get any values from the SDK,
        // therefore you need to manually set up them.
        if (!Hoko.deeplinking().inject(this)) {
            // This code will be executed if the 'ProductViewActivity' was created manually.

            Intent intent = getIntent();

            this.productID = intent.getIntExtra("id", 0);
            Product myProduct = Product.getProductWithId(this.productID);
            this.currentCoupon = HOKOStoreApplication.getAvailableDiscountForProduct(this, this.productID);

            setupActivityWithProduct(myProduct);

        } else {
            // This code _will be executed_ if the 'ProductViewActivity' was started by HOKO.


            // ____ This is the core part of this Use Case app ____
            //
            // >> http://black.hoko.link/save20 << this is the main link we are using for this example.
            //
            // The example link contains the following metadata:
            //  {
            //    "coupon": "save20",
            //    "value": "20"
            //  }


            // We will try to create a Product object with the product ID given in the deep link's
            // data
            Product myProduct = Product.getProductWithId(this.productID);
            if (myProduct != null) {

                // Check if the deep link contains metadata
                if (this.metadata != null) {

                    // In this case, we will check if the deep link's contains the key 'coupon'
                    // which is its code and the key 'value' which is its discount value.
                    // In this case we want the coupon name (which is 'save20'
                    //
                    // Since the deep link's metadata object is a JSON object, we use the
                    // 'opt...()' instance method to get the value we want.

                    // the 'coupon' key contains a string value, so we use 'optString()'
                    String couponName = metadata.optString("coupon");

                    // the 'value' key contains a numeric value, so we use 'optDouble()'
                    double couponDiscount = metadata.optDouble("value");

                    if (couponName != null) {
                        this.hasRedeemedACoupon = true;
                        this.currentCoupon = new Coupon(couponName, couponDiscount);

                        // We will save on the app's Shared Preferences that the user already has
                        // redeemed a coupon for product X which will be used later to show a
                        // discount badge on that product's ListView cell.
                        //
                        //
                        // ___ DEVELOPER NOTE ___
                        // Instead of saving this coupon data on your app's Shared Preferences,
                        // you can save it on your application's backend, making it more secure.
                        // This enables you to limit the amount of times each user can redeem your
                        // coupons by attaching the redeemed coupon data along with your user ID.
                        HOKOStoreApplication.saveCouponForProduct(this, this.productID, this.currentCoupon);
                    }
                }

                // We display the product whether there was a coupon or not.
                setupActivityWithProduct(myProduct);

            } else {
                // The product ID is invalid. Present an Alert to the user.
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // If a coupon was recently redeemed we will present an Alert to notify the user
        if (this.hasRedeemedACoupon) {
            this.showPopup("Congratulations",
                    "You just redeemed '" + this.currentCoupon.getName() +
                            "' coupon and received a discount of $" +
                            this.currentCoupon.getValue() + " because you clicked on the right link!",
                    "Awesome!");
        }
    }

    private void setupActivityWithProduct(Product product) {
        this.currentProduct = product;

        setTitle(product.getTitle());

        ImageView imgView = (ImageView) findViewById(R.id.singleProductImageView);
        imgView.setImageResource(this.currentProduct.getImageResource());

        this.setupPriceTextViewWithDiscount();

        TextView nameTextView = (TextView) findViewById(R.id.singleProductNameTextView);
        nameTextView.setText(this.currentProduct.getTitle());

        TextView descriptionTextView = (TextView) findViewById(R.id.singleProductDescriptionTextView);
        descriptionTextView.setText(this.currentProduct.getDescription());

        this.setupBuyButton();
    }

    private void setupPriceTextViewWithDiscount() {
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
