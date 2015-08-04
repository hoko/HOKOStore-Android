package com.hokolinks.exitpoints.model;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExitApp implements Parcelable {

    public static final Parcelable.Creator<ExitApp> CREATOR = new Parcelable.Creator<ExitApp>() {
        public ExitApp createFromParcel(@NonNull Parcel source) {
            return new ExitApp(source);
        }

        @NonNull
        public ExitApp[] newArray(int size) {
            return new ExitApp[size];
        }
    };
    // Constants
    private static final String GOOGLE_PLAY_PACKAGE_NAME = "com.android.vending";
    private static final String GOOGLE_PLAY_DEEPLINK_FORMAT = "market://details?id=%s";
    private static final String GOOGLE_PLAY_URL_FORMAT =
            "https://play.google.com/store/apps/details?id=%s";
    // Fields
    private String mIdentifier;
    private String mName;
    private String mDeeplink;
    private String mIconURL;
    private String mPackageName;

    // Constructors
    private ExitApp(JSONObject jsonObject) {
        this(jsonObject.optString("id"), jsonObject.optString("name"),
                jsonObject.optString("deeplink"), jsonObject.optString("icon_url"),
                jsonObject.optString("store_id"));

    }

    private ExitApp(String identifier, String name, String deeplink, String iconURL, String packageName) {
        mIdentifier = identifier;
        mName = name;
        mDeeplink = deeplink;
        mIconURL = iconURL;
        mPackageName = packageName;
    }

    protected ExitApp(Parcel in) {
        this.mIdentifier = in.readString();
        this.mName = in.readString();
        this.mDeeplink = in.readString();
        this.mIconURL = in.readString();
        this.mPackageName = in.readString();
    }

    protected static List<ExitApp> appsFromJSONArray(JSONArray jsonArray) {
        List<ExitApp> apps = new ArrayList<>();
        for (int index = 0; index < jsonArray.length(); index++) {
            apps.add(new ExitApp(jsonArray.optJSONObject(index)));
        }
        return apps;
    }

    // Static Helpers
    private static void openLink(Context context, String link) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
    }

    private static boolean canOpenPackage(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private static String getGooglePlayDeeplink(String packageName) {
        return String.format(GOOGLE_PLAY_DEEPLINK_FORMAT, packageName);
    }

    private static String getGooglePlayUrl(String packageName) {
        return String.format(GOOGLE_PLAY_URL_FORMAT, packageName);
    }

    // Accessors
    public String getName() {
        return mName;
    }

    public String getIconURL() {
        return mIconURL;
    }

    @Override
    public String toString() {
        return "ExitApp{" +
                "identifier='" + mIdentifier + '\'' +
                ", name='" + mName + '\'' +
                ", deeplink='" + mDeeplink + '\'' +
                ", icon='" + mIconURL + '\'' +
                ", packageName='" + mPackageName + '\'' +
                '}';
    }

    // Interaction
    public void performAction(Context context) {
        if (canOpenPackage(context, mPackageName)) {
            openLink(context, mDeeplink);
        } else if (canOpenPackage(context, GOOGLE_PLAY_PACKAGE_NAME)) {
            openLink(context, getGooglePlayDeeplink(mPackageName));
        } else {
            openLink(context, getGooglePlayUrl(mPackageName));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.mIdentifier);
        dest.writeString(this.mName);
        dest.writeString(this.mDeeplink);
        dest.writeString(this.mIconURL);
        dest.writeString(this.mPackageName);
    }
}
