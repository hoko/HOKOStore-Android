package com.hokolinks.exitpoints.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hokolinks.exitpoints.R;
import com.hokolinks.exitpoints.viewmodel.ExitAppViewModel;
import com.squareup.picasso.Picasso;

public class ExitAppViewHolder extends RecyclerView.ViewHolder {

    private ImageView mIconImageView;
    private TextView mNameTextView;

    public ExitAppViewHolder(View itemView) {
        super(itemView);
        mIconImageView = (ImageView) itemView.findViewById(R.id.iconImageView);
        mNameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
    }

    public void setViewModel(final ExitAppViewModel appViewModel) {
        Picasso.with(itemView.getContext())
                .load(appViewModel.getIconURL())
                .into(mIconImageView);
        mNameTextView.setText(appViewModel.getName());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appViewModel.appClicked();
            }
        });
    }
}