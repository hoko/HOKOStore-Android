package com.hokolinks.exitpoints.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExitAppsAction implements Parcelable {

    public static final Parcelable.Creator<ExitAppsAction> CREATOR = new Parcelable.Creator<ExitAppsAction>() {
        public ExitAppsAction createFromParcel(@NonNull Parcel source) {
            return new ExitAppsAction(source);
        }

        @NonNull
        public ExitAppsAction[] newArray(int size) {
            return new ExitAppsAction[size];
        }
    };
    // Fields
    private String mIdentifier;
    private String mName;
    private List<ExitApp> mApps;

    // Constructors
    private ExitAppsAction(JSONObject jsonObject) {
        this(jsonObject.optString("id"), jsonObject.optString("name"), ExitApp.appsFromJSONArray(jsonObject.optJSONArray("apps")));
    }

    private ExitAppsAction(String identifier, String name, List<ExitApp> apps) {
        mIdentifier = identifier;
        mName = name;
        mApps = apps;
    }

    protected ExitAppsAction(Parcel in) {
        this.mIdentifier = in.readString();
        this.mName = in.readString();
        this.mApps = in.createTypedArrayList(ExitApp.CREATOR);
    }

    protected static List<ExitAppsAction> actionsFromJSONArray(JSONArray jsonArray) {
        List<ExitAppsAction> actions = new ArrayList<>();
        for (int index = 0; index < jsonArray.length(); index ++) {
            actions.add(new ExitAppsAction(jsonArray.optJSONObject(index)));
        }
        return actions;
    }

    // Accessors
    public String getName() {
        return mName;
    }

    public List<ExitApp> getApps() {
        return mApps;
    }

    @Override
    public String toString() {
        return "ExitAppsAction{" +
                "identifier='" + mIdentifier + '\'' +
                ", name='" + mName + '\'' +
                ", apps=" + mApps +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.mIdentifier);
        dest.writeString(this.mName);
        dest.writeTypedList(mApps);
    }
}
