package com.hokolinks.exitpoints.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hokolinks.exitpoints.R;
import com.hokolinks.exitpoints.viewholder.ExitActionNameViewHolder;
import com.hokolinks.exitpoints.viewholder.ExitAppViewHolder;
import com.hokolinks.exitpoints.viewholder.ExitTitleViewHolder;
import com.hokolinks.exitpoints.viewmodel.ExitActionNameViewModel;
import com.hokolinks.exitpoints.viewmodel.ExitAppViewModel;
import com.hokolinks.exitpoints.viewmodel.ExitBaseViewModel;
import com.hokolinks.exitpoints.viewmodel.ExitTitleViewModel;

import java.util.List;

public class ExitAdapter extends RecyclerView.Adapter {

    private static final int EXIT_APP_VIEW_MODEL_TYPE = 0;
    private static final int EXIT_TITLE_VIEW_MODEL_TYPE = 1;
    private static final int EXIT_ACTION_TITLE_VIEW_MODEL_TYPE = 2;

    private List<ExitBaseViewModel> mViewModels;

    public ExitAdapter(List<ExitBaseViewModel> appViewModels) {
        mViewModels = appViewModels;
    }

    @Override
    public int getItemViewType(int position) {
        ExitBaseViewModel baseViewModel = mViewModels.get(position);
        if (baseViewModel instanceof ExitAppViewModel) {
            return EXIT_APP_VIEW_MODEL_TYPE;
        } else if (baseViewModel instanceof ExitTitleViewModel) {
            return EXIT_TITLE_VIEW_MODEL_TYPE;
        } else if (baseViewModel instanceof ExitActionNameViewModel) {
            return EXIT_ACTION_TITLE_VIEW_MODEL_TYPE;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case EXIT_APP_VIEW_MODEL_TYPE:
                return new ExitAppViewHolder(layoutInflater
                        .inflate(R.layout.exitpoint_app_item, viewGroup, false));
            case EXIT_TITLE_VIEW_MODEL_TYPE:
                return new ExitTitleViewHolder(layoutInflater
                        .inflate(R.layout.exitpoint_title_item, viewGroup, false));
            case EXIT_ACTION_TITLE_VIEW_MODEL_TYPE:
                return new ExitActionNameViewHolder(layoutInflater
                        .inflate(R.layout.exitpoint_action_name_item, viewGroup, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ExitBaseViewModel baseViewModel = mViewModels.get(i);
        if (baseViewModel instanceof ExitAppViewModel) {
            ((ExitAppViewHolder) viewHolder).setViewModel((ExitAppViewModel) baseViewModel);
        } else if (baseViewModel instanceof ExitTitleViewModel) {
            ((ExitTitleViewHolder) viewHolder).setViewModel((ExitTitleViewModel) baseViewModel);
        } else if (baseViewModel instanceof ExitActionNameViewModel) {
            ((ExitActionNameViewHolder) viewHolder)
                    .setViewModel((ExitActionNameViewModel) baseViewModel);
        }
    }

    @Override
    public int getItemCount() {
        return mViewModels != null ? mViewModels.size() : 0;
    }


}
