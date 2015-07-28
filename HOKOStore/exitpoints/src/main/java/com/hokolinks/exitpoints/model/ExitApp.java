package com.hokolinks.exitpoints.model;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivanbruel on 28/07/15.
 */
class ExitApp {

    // Constants
    private static final String GOOGLE_PLAY_PACKAGE_NAME = "com.android.vending";
    private static final String GOOGLE_PLAY_DEEPLINK_FORMAT = "market://details?id=%s";
    private static final String GOOGLE_PLAY_URL_FORMAT =
            "https://play.google.com/store/apps/details?id=%s";

    // Fields
    private String mIdentifier;
    private String mName;
    private String mDeeplink;
    private String mIcon;
    private String mPackageName;

    // Constructors
    private ExitApp(JSONObject jsonObject) {
        this(jsonObject.optString("id"), jsonObject.optString("name"),
                jsonObject.optString("deeplink"), jsonObject.optString("icon_url"),
                jsonObject.optString("store_id"));

    }

    private ExitApp(String identifier, String name, String deeplink, String icon, String packageName) {
        mIdentifier = identifier;
        mName = name;
        mDeeplink = deeplink;
        mIcon = icon;
        mPackageName = packageName;
    }

    protected static List<ExitApp> appsFromJSONArray(JSONArray jsonArray) {
        List<ExitApp> apps = new ArrayList<>();
        for (int index = 0; index < jsonArray.length(); index ++) {
            apps.add(new ExitApp(jsonArray.optJSONObject(index)));
        }
        return apps;
    }

    // Accessors
    public String getName() {
        return mName;
    }

    public String getIcon() {
        return mIcon;
    }

    @Override
    public String toString() {
        return "ExitApp{" +
                "mIdentifier='" + mIdentifier + '\'' +
                ", mName='" + mName + '\'' +
                ", mDeeplink='" + mDeeplink + '\'' +
                ", mIcon='" + mIcon + '\'' +
                ", mPackageName='" + mPackageName + '\'' +
                '}';
    }

    // Interaction
    protected void performAction(Context context) {
        if (canOpenPackage(context, mPackageName)) {
            openLink(context, mDeeplink);
        } else if (canOpenPackage(context, GOOGLE_PLAY_PACKAGE_NAME)) {
            openLink(context, getGooglePlayDeeplink());
        } else {
            openLink(context, getGooglePlayUrl());
        }
    }

    // Static Helpers
    private static void openLink(Context context, String link) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
    }

    private static boolean canOpenPackage(Context context, String packageName) {
        try
        {
            context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }

    private static String getGooglePlayDeeplink() {
        return String.format(GOOGLE_PLAY_DEEPLINK_FORMAT, GOOGLE_PLAY_PACKAGE_NAME);
    }

    private static String getGooglePlayUrl() {
        return String.format(GOOGLE_PLAY_URL_FORMAT, GOOGLE_PLAY_PACKAGE_NAME);
    }
}
