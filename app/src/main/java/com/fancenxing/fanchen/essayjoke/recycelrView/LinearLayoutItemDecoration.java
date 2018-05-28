package com.fancenxing.fanchen.essayjoke.recycelrView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/23.
 */

public class LinearLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public LinearLayoutItemDecoration(Context context, int dividerResId) {
        this.mDivider = ContextCompat.getDrawable(context, dividerResId);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position != 0) {
            outRect.top = mDivider.getIntrinsicHeight();
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();

        Rect rect = new Rect();
        rect.left = parent.getPaddingLeft();
        rect.right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 1; i < childCount; i++) {
            rect.bottom = parent.getChildAt(i).getTop();
            rect.top = rect.bottom - mDivider.getIntrinsicHeight();
            mDivider.setBounds(rect);
            mDivider.draw(c);
        }
    }
}
