package com.hokolinks.exitpoints.viewmodel;

import com.hokolinks.exitpoints.model.ExitAppsAction;

public class ExitActionNameViewModel extends ExitBaseViewModel {

    private String mName;

    public ExitActionNameViewModel(ExitAppsAction action) {
        mName = action.getName();
    }

    public String getName() {
        return mName;
    }

    @Override
    public int spanSize() {
        return 4;
    }
}
