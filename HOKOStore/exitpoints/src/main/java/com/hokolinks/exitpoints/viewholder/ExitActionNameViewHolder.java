package com.hokolinks.exitpoints.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hokolinks.exitpoints.R;
import com.hokolinks.exitpoints.viewmodel.ExitActionNameViewModel;

public class ExitActionNameViewHolder extends RecyclerView.ViewHolder {

    private TextView mtitleTextView;

    public ExitActionNameViewHolder(View itemView) {
        super(itemView);
        mtitleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
    }

    public void setViewModel(ExitActionNameViewModel actionNameViewModel) {
        mtitleTextView.setText(actionNameViewModel.getName());
    }
}