package com.hokolinks.exitpoints;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.hokolinks.exitpoints.fragment.ExitFragment;
import com.hokolinks.exitpoints.model.Exit;
import com.hokolinks.exitpoints.model.ExitApp;

import org.json.JSONException;
import org.json.JSONObject;

public class ExitPoints implements Exit.ExitResponseListener {

    // Properties
    private Activity mActivity;
    private Exit mExit;
    private String mTitle;
    private String mIdentifier;
    private ExitPointsListener mListener;

    // Private Constructor
    private ExitPoints(@NonNull Activity activity, @NonNull String identifier, @Nullable String title,
                       @Nullable ExitPointsListener listener) {
        mActivity = activity;
        mIdentifier = identifier;
        mTitle = title;
        mListener = listener;
    }

    // Static presenters
    public static void presentExitPoints(@NonNull Activity activity, @NonNull String identifier) {
        presentExitPoints(activity, identifier, null);
    }

    public static void presentExitPoints(@NonNull Activity activity, @NonNull String identifier,
                                         @Nullable String title) {
        presentExitPoints(activity, identifier, title, null);
    }

    public static void presentExitPoints(@NonNull final Activity activity, @NonNull String identifier,
                                         @Nullable final String title,
                                         @Nullable final ExitPointsListener listener) {
        new ExitPoints(activity, identifier, title, listener).present();
    }

    // Methods
    private void present() {
        Exit.getExitWithIdentifier(mIdentifier, this);
    }

    private void show(final Exit exit) {
        mExit = exit;
        delegateShow();
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ExitFragment exitFragment = ExitFragment.newInstance(exit, mTitle);
                mActivity.getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.animator.slide_top, R.animator.slide_bottom,
                                R.animator.slide_top, R.animator.slide_bottom)
                        .add(android.R.id.content, exitFragment)
                        .addToBackStack("ExitPoints")
                        .commit();
            }
        });
    }

    private void appOpened(ExitApp exitApp, boolean googlePlay) {
        delegateAppOpened(exitApp, googlePlay);
    }

    // ExitPointsListener
    private void delegateException(Exception exception) {
        if (mListener != null) {
            mListener.onException(exception);
        }
    }

    private void delegateShow() {
        if (mListener != null) {
            mListener.onShow(mExit);
        }
    }

    private void delegateAppOpened(ExitApp app, boolean googlePlay) {
        if (mListener != null) {
            mListener.onAppOpened(app, googlePlay);
        }
    }

    // ExitResponseListener
    @Override
    public void onSucccess(Exit exit) {
        show(exit);
    }

    @Override
    public void onFailure(Exception exception) {
        delegateException(exception);
        try {
            show(new Exit(new JSONObject("{\"id\":3,\"actions\":[{\"id\":3,\"name\":\"Aplicativos\",\"apps\":[{\"id\":11,\"name\":\"Pinguino assustado\",\"icon_url\":\"https://lh5.ggpht.com/nNFD1ViaadHEi9-Kxf7uDCB9ac28YpvKJPB19yaSQ7d6yQ4l3GMccoudJ2EGf3STNHGm=w300-rw\",\"deeplink\":\"black://\",\"store_id\":\"test\",\"rating\":0.0},{\"id\":10,\"name\":\"Passaredo espacial\",\"icon_url\":\"https://lh5.ggpht.com/Ju6Su5337-hEXaKsZO4aZEH1H7M_Izu3FoKBSzoF93CbhICXYcISYruOW4ulGBeEIS4=w300-rw\",\"deeplink\":\"black://\",\"store_id\":\"test\",\"rating\":0.0},{\"id\":9,\"name\":\"Passarame\",\"icon_url\":\"https://lh3.googleusercontent.com/xBjI-GGSKVcuNslNIFj6qJBSaMC38B9EsNfbyxc1kBE1Z33P68YCRQsmSUOKOADI81w=w300-rw\",\"deeplink\":\"black://\",\"store_id\":\"test\",\"rating\":0.0}]}]}")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface ExitPointsListener {
        void onShow(Exit exit);

        void onException(Exception exception);

        void onAppOpened(ExitApp app, boolean googlePlay);
    }

}
