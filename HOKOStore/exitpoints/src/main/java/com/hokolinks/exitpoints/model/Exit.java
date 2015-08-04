package com.hokolinks.exitpoints.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Exit implements Parcelable {
    public static final Creator<Exit> CREATOR = new Creator<Exit>() {
        public Exit createFromParcel(@NonNull Parcel source) {
            return new Exit(source);
        }

        @NonNull
        public Exit[] newArray(int size) {
            return new Exit[size];
        }
    };
    // Constants
    private static final String HOKO_EXIT_ENDPOINT_FORMAT = "http://28137e2f.ngrok.com/v2/menus/%s.json";
    // Singletons
    private static OkHttpClient sOkHttpClient;
    // Fields
    private List<ExitAppsAction> mActions;

    // Constructors
    public Exit(JSONObject jsonObject) {
        this(ExitAppsAction.actionsFromJSONArray(jsonObject.optJSONArray("actions")));
    }

    private Exit(List<ExitAppsAction> actions) {
        mActions = actions;
    }

    protected Exit(Parcel in) {
        this.mActions = new ArrayList<>();
        in.readList(this.mActions, List.class.getClassLoader());
    }

    // Network
    public static void getExitWithIdentifier(String identifier,
                                              final ExitResponseListener exitResponseListener) {
        getHttpClient().newCall(getHttpRequest(identifier)).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                exitResponseListener.onFailure(e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    String stringResponse = response.body().string();
                    Log.e("RESPONSE", stringResponse);
                    exitResponseListener.onSucccess(
                            new Exit(new JSONObject(stringResponse)));
                } catch (JSONException jsonException) {
                    exitResponseListener.onFailure(jsonException);
                }
            }
        });
    }

    private static Request getHttpRequest(String identifier) {
        return new Request.Builder()
                .url(getExitURL(identifier))
                .addHeader("Authorization", "Token 9fad9a7b52e539d000c8f1c73a808afcf4ae4851")
                .build();
    }

    private static OkHttpClient getHttpClient() {
        synchronized (Exit.class) {
            if (sOkHttpClient == null) {
                sOkHttpClient = new OkHttpClient();
            }
        }
        return sOkHttpClient;
    }

    // Helpers
    private static String getExitURL(String identifier) {
        return String.format(HOKO_EXIT_ENDPOINT_FORMAT, identifier);
    }

    // Accessors
    public List<ExitAppsAction> getActions() {
        return mActions;
    }

    @Override
    public String toString() {
        return "Exit{" +
                "actions=" + mActions +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeList(this.mActions);
    }

    public interface ExitResponseListener {
        void onSucccess(Exit exit);
        void onFailure(Exception exception);
    }
}
