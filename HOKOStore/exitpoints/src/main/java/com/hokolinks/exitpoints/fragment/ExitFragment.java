package com.hokolinks.exitpoints.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hokolinks.exitpoints.R;
import com.hokolinks.exitpoints.adapter.ExitAdapter;
import com.hokolinks.exitpoints.layout.WrappedContentGridLayoutManager;
import com.hokolinks.exitpoints.model.Exit;
import com.hokolinks.exitpoints.model.ExitApp;
import com.hokolinks.exitpoints.model.ExitAppsAction;
import com.hokolinks.exitpoints.viewmodel.ExitActionNameViewModel;
import com.hokolinks.exitpoints.viewmodel.ExitAppViewModel;
import com.hokolinks.exitpoints.viewmodel.ExitBaseViewModel;
import com.hokolinks.exitpoints.viewmodel.ExitTitleViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExitFragment extends Fragment implements ExitAppViewModel.ExitAppClickListener {

    private static final String EXIT_BUNDLE_KEY = "exit";
    private static final String TITLE_BUNDLE_KEY = "title";

    private RecyclerView mRecyclerView;
    private WrappedContentGridLayoutManager mGridLayoutManager;
    private Exit mExit;
    private String mTitle;

    private List<ExitBaseViewModel> mViewModels;

    public static ExitFragment newInstance(Exit exit, String title) {
        ExitFragment fragment = new ExitFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EXIT_BUNDLE_KEY, exit);
        bundle.putString(TITLE_BUNDLE_KEY, title);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exitpoints_fragment, container, false);
        setupModel();
        setupViews(rootView);
        return rootView;
    }

    private void setupModel() {
        mExit = getArguments().getParcelable(EXIT_BUNDLE_KEY);
        mTitle = getArguments().getString(TITLE_BUNDLE_KEY);

        mViewModels = getViewModels();
    }

    private void setupViews(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(new ExitAdapter(mViewModels));

        mGridLayoutManager = new WrappedContentGridLayoutManager(getActivity(), 4);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mViewModels.get(position).spanSize();
            }
        });
        mRecyclerView.setLayoutManager(mGridLayoutManager);
    }

    private List<ExitBaseViewModel> getViewModels() {
        List<ExitBaseViewModel> viewModels = new ArrayList<>();
        if (mTitle != null) {
            viewModels.add(new ExitTitleViewModel(mTitle));
        }
        for (ExitAppsAction action : mExit.getActions()) {
            viewModels.add(new ExitActionNameViewModel(action));
            for (ExitApp app : action.getApps()) {
                viewModels.add(new ExitAppViewModel(app, this));
            }
        }
        return viewModels;
    }

    @Override
    public void appClicked(ExitApp app) {
        app.performAction(getActivity());
    }
}
