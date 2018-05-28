package com.fancenxing.fanchen.essayjoke.recycelrView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/23.
 */

public class GridLayoutItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private int size;

    public GridLayoutItemDecoration(Context context, int dividerResId) {
        this.mDivider = ContextCompat.getDrawable(context, dividerResId);
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int bottom;
        int right;
        if (isLastColumn(view, parent)) {//最后一列
            right = 0;
        } else {
            right = mDivider.getIntrinsicWidth();
        }
        if (isLastRow(view, parent)) {//最后一行
            bottom = 0;
        } else {
            bottom = mDivider.getIntrinsicHeight();
        }
        outRect.bottom = bottom;
        outRect.right = right;
    }

    private boolean isLastColumn(View view, RecyclerView parent) {
        //获取当前位置
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        //获取列数
        int column = getSpanCount(parent);

        if (size != 0) {
            return true;
        }
        if ((position - size) % column == column - 1) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isLastRow(View view, RecyclerView parent) {
        //获取当前位置
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        //列数
        int column = getSpanCount(parent);

        //行数
        int total = parent.getAdapter().getItemCount();
        int rowNum = (total - size) / column;
        if (total % column != 0) {
            rowNum++;
        }
        rowNum += size;

        int realPosition = position + 1 - size;
        int currentRow = realPosition / column + size;
        if (realPosition % column != 0) {
            currentRow++;
        }
        if (rowNum == currentRow) {
            return true;
        } else {
            return false;
        }
    }

    private int getSpanCount(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int column = ((GridLayoutManager) layoutManager).getSpanCount();
            return column;
        } else {
            return 1;
        }
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {

        //绘制
        drawHorizontal(canvas, parent);
        drawVertical(canvas, parent);
    }

    /**
     * 绘制水平方向分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - params.leftMargin;
            int right = child.getRight() + params.rightMargin + mDivider.getIntrinsicWidth();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }

    /**
     * 绘制垂直方向分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop() - params.topMargin;
            int bottom = child.getBottom() + params.bottomMargin + mDivider.getIntrinsicWidth();
            int left = child.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }

}
