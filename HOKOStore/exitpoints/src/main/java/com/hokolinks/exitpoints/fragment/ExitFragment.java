package com.hokolinks.exitpoints.fragment;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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
    private RelativeLayout mBackgroundRelativeLayout;
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
        animateBackgroundColorIn();
        return rootView;
    }

    private void setupModel() {
        mExit = getArguments().getParcelable(EXIT_BUNDLE_KEY);
        mTitle = getArguments().getString(TITLE_BUNDLE_KEY);

        mViewModels = getViewModels();
    }

    private void setupViews(View rootView) {
        mBackgroundRelativeLayout =
                (RelativeLayout) rootView.findViewById(R.id.backgroundRelativeLayout);
        mBackgroundRelativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, @NonNull MotionEvent event) {
                if (v != mRecyclerView) {
                    closeFragment();
                }
                return true;
            }
        });

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setAdapter(new ExitAdapter(mViewModels));

        WrappedContentGridLayoutManager gridLayoutManager = new WrappedContentGridLayoutManager(getActivity(), 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mViewModels.get(position).spanSize();
            }
        });
        mRecyclerView.setLayoutManager(gridLayoutManager);
    }

    private void animateBackgroundColorIn() {
        animateBackgroundColor(R.color.transparent, R.color.gray_50_opacity, 500, null);
    }

    private void animateBackgroundColorOut(Animator.AnimatorListener animatorListener) {
        animateBackgroundColor(R.color.gray_50_opacity, R.color.transparent, 0, animatorListener);
    }

    private void animateBackgroundColor(int fromColorId, int toColorId, int delay, Animator.AnimatorListener animatorListener) {
        Integer colorFrom = getResources().getColor(fromColorId);
        Integer colorTo = getResources().getColor(toColorId);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(500);
        colorAnimation.setStartDelay(delay);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animator) {
                mBackgroundRelativeLayout.setBackgroundColor((Integer) animator.getAnimatedValue());
            }

        });
        if (animatorListener != null)
            colorAnimation.addListener(animatorListener);
        colorAnimation.start();
    }

    private void closeFragment() {
        animateBackgroundColorOut(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                getFragmentManager().popBackStack();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                getFragmentManager().popBackStack();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
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
