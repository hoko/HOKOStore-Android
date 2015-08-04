package com.hokolinks.exitpoints.viewmodel;

/**
 * Created by ivanbruel on 04/08/15.
 */
public class ExitTitleViewModel extends ExitBaseViewModel {

    private String mTitle;

    public ExitTitleViewModel(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    @Override
    public int spanSize() {
        return 4;
    }
}
