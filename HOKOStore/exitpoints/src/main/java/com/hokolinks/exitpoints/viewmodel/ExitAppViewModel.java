package com.hokolinks.exitpoints.viewmodel;

import com.hokolinks.exitpoints.model.ExitApp;

public class ExitAppViewModel extends ExitBaseViewModel {

    private ExitApp mApp;

    private String mName;
    private String mIconURL;
    private ExitAppClickListener mAppClickListener;

    public ExitAppViewModel(ExitApp app, ExitAppClickListener appClickListener) {
        mApp = app;

        mName = app.getName();
        mIconURL = app.getIconURL();
        mAppClickListener = appClickListener;
    }

    public String getName() {
        return mName;
    }

    public String getIconURL() {
        return mIconURL;
    }

    public void appClicked() {
        mAppClickListener.appClicked(mApp);
    }

    @Override
    public int spanSize() {
        return 1;
    }

    public interface ExitAppClickListener {
        void appClicked(ExitApp app);
    }

}
