package com.hokolinks.exitpoints.model;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by ivanbruel on 28/07/15.
 */
public class Exit {
    // Constants
    private static String HOKO_EXIT_ENDPOINT_FORMAT = "http://192.168.10.135:3000/v2/menus/%s.json";

    // Singletons
    private static OkHttpClient sOkHttpClient;

    // Fields
    private List<ExitAppsAction> mActions;

    // Constructors
    private Exit(JSONObject jsonObject) {
        this(ExitAppsAction.actionsFromJSONArray(jsonObject.optJSONArray("actions")));
    }

    private Exit(List<ExitAppsAction> actions) {
        mActions = actions;
    }

    // Accessors
    public List<ExitAppsAction> getActions() {
        return mActions;
    }

    @Override
    public String toString() {
        return "Exit{" +
                "mActions=" + mActions +
                '}';
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
                    exitResponseListener.onSucccess(
                            new Exit(new JSONObject(response.body().string())));
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

    public interface ExitResponseListener {
        void onSucccess(Exit exit);
        void onFailure(Exception exception);
    }

}
