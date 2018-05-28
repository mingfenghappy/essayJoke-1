package com.fancenxing.fanchen.essayjoke.recycelrView.wrap;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/25.
 */

public class WrapRecyclerView extends RecyclerView {

    private WrapRecyclerAdapter mAdapter;
    private AdapterDataObserver mAdapterObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            isSetAdapter();
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            isSetAdapter();
            mAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            isSetAdapter();
            mAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            isSetAdapter();
            mAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            isSetAdapter();
            mAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            isSetAdapter();
            mAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };

    public WrapRecyclerView(Context context) {
        this(context, null);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof WrapRecyclerAdapter) {
            mAdapter = (WrapRecyclerAdapter) adapter;
        } else {
            mAdapter = new WrapRecyclerAdapter(adapter);
            adapter.registerAdapterDataObserver(mAdapterObserver);
        }
        super.setAdapter(mAdapter);
    }


    public void addHeaderView(View view) {
        isSetAdapter();
        mAdapter.addHeaderView(view);
    }

    public void removeHeaderView(View view) {
        isSetAdapter();
        mAdapter.removeHeaderView(view);
    }

    public void addFooterView(View view) {
        isSetAdapter();
        mAdapter.addFooterView(view);
    }

    public void removeFooterView(View view) {
        isSetAdapter();
        mAdapter.removeFooterView(view);
    }

    private void isSetAdapter() {
        if (mAdapter == null) {
            throw new RuntimeException("please set adapter first");
        }
    }

    @Override
    public void setLayoutManager(final LayoutManager layout) {
        super.setLayoutManager(layout);
        if (layout instanceof GridLayoutManager) {
            ((GridLayoutManager) layout).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int headerSize = mAdapter.getHeaderSize();
                    if (position < headerSize) {
                        return ((GridLayoutManager) layout).getSpanCount();
                    } else {
                        int size = mAdapter.getRealSize() + headerSize;
                        if (position >= size) {
                            return ((GridLayoutManager) layout).getSpanCount();
                        }
                    }
                    return 1;
                }
            });
        }
    }

}
