package com.hokolinks.exitpoints.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ivanbruel on 28/07/15.
 */
public class ExitAppsAction {

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
                "mIdentifier='" + mIdentifier + '\'' +
                ", mName='" + mName + '\'' +
                ", mApps=" + mApps +
                '}';
    }
}
