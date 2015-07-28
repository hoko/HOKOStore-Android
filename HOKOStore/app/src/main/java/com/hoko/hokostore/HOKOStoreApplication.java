package com.hoko.hokostore;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.hokolinks.Hoko;

/**
 * Created by pedrovieira on 21/07/15.
 */
public class HOKOStoreApplication extends Application {

    public void onCreate(){
        super.onCreate();

        Hoko.setup(this, "b18577532f0ef1d71cfc9e9ce670bca440cf2b69");
        Hoko.setVerbose(true);
    }

    public static void saveCouponForProduct(Context ctx, int productID, Coupon coupon) {
        SharedPreferences preferences = ctx.getSharedPreferences("MyPreferences",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(getPreferencesKeyStringForProductCouponName(productID), coupon.getName());

        editor.putLong(getPreferencesKeyStringForProductCouponValue(productID),
                Double.doubleToRawLongBits(coupon.getValue()));

        editor.apply();
    }

    public static Coupon getAvailableDiscountForProduct(Context ctx, int productID) {
        SharedPreferences preferences = ctx.getSharedPreferences("MyPreferences",
                Context.MODE_PRIVATE);

        String couponName = preferences.getString(
                getPreferencesKeyStringForProductCouponName(productID),
                null
        );

        if (couponName == null)
            return null;

        double couponValue = Double.longBitsToDouble(
                preferences.getLong(getPreferencesKeyStringForProductCouponValue(productID), 0)
        );

        return new Coupon(couponName, couponValue);

    }

    private static String getPreferencesKeyStringForProductCouponName(int productID) {
        return "Product " + productID + " coupon name";
    }

    private static String getPreferencesKeyStringForProductCouponValue(int productID) {
        return "Product " + productID + " coupon value";
    }
}
