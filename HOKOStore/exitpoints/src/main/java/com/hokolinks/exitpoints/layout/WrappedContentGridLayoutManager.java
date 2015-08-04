package com.hokolinks.exitpoints.layout;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class WrappedContentGridLayoutManager extends GridLayoutManager {

    private int[] mMeasuredDimension = new int[3];

    public WrappedContentGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr,
                                           int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public WrappedContentGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public WrappedContentGridLayoutManager(Context context, int spanCount, int orientation,
                                           boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state,
                          int widthSpec, int heightSpec) {
        final int widthMode = View.MeasureSpec.getMode(widthSpec);
        final int heightMode = View.MeasureSpec.getMode(heightSpec);
        final int widthSize = View.MeasureSpec.getSize(widthSpec);
        final int heightSize = View.MeasureSpec.getSize(heightSpec);
        int totalWidth = 0;
        int totalHeight = 0;

        int widthToAdd = 0;
        int heightToAdd = 0;
        int previousSpanIndex = 0;
        for (int i = 0; i < getItemCount(); i++) {
            measureScrapChild(recycler, i,
                    View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                    mMeasuredDimension);

            int width = mMeasuredDimension[0];
            int height = mMeasuredDimension[1];
            int spanIndex = mMeasuredDimension[2];

            if (spanIndex != previousSpanIndex) {
                previousSpanIndex = spanIndex;
                totalHeight += heightToAdd;
                heightToAdd = 0;
            }
            heightToAdd = Math.max(heightToAdd, height);

        }

        totalHeight += heightToAdd;
        totalWidth += widthToAdd;

        switch (widthMode) {
            case View.MeasureSpec.EXACTLY:
                totalWidth = widthSize;
            case View.MeasureSpec.AT_MOST:
            case View.MeasureSpec.UNSPECIFIED:
        }

        switch (heightMode) {
            case View.MeasureSpec.EXACTLY:
                totalHeight = heightSize;
            case View.MeasureSpec.AT_MOST:
            case View.MeasureSpec.UNSPECIFIED:
        }
        Log.e("SIZE", totalWidth + " " + totalHeight);
        setMeasuredDimension(totalWidth, totalHeight);
    }

    private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec,
                                   int heightSpec, int[] measuredDimension) {
        int spanSize = getSpanCount();
        View view = recycler.getViewForPosition(position);
        if (view != null) {
            RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();
            int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                    getPaddingLeft() + getPaddingRight(), p.width);
            int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                    getPaddingTop() + getPaddingBottom(), p.height);
            measureChild(view, childWidthSpec, childHeightSpec);
            measuredDimension[0] = getDecoratedMeasuredWidth(view) + p.leftMargin + p.rightMargin;
            measuredDimension[1] = getDecoratedMeasuredHeight(view) + p.bottomMargin + p.topMargin;
            measuredDimension[2] = lineNumberForPosition(position, spanSize);
            recycler.recycleView(view);
        }
    }

    private int lineNumberForPosition(int position, int spanSize) {
        int spanCount = 0;

        for (int index = 0; index < position; index++) {
            spanCount += getSpanSizeLookup().getSpanSize(index);
        }
        return spanCount / spanSize;
    }
}