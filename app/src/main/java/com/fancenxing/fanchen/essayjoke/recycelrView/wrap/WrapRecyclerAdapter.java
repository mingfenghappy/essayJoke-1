package com.fancenxing.fanchen.essayjoke.recycelrView.wrap;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/25.
 */

public class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    //不包含头部的adapter
    private RecyclerView.Adapter mAdapter;

    //头部和底部的集合  必须用map集合进行标识
    private SparseArray<View> mHeaders, mFooters;

    private static int BASE_HEADER_KEY = 1000;
    private static int BASE_FOOTER_KEY = 2000;

    public WrapRecyclerAdapter(RecyclerView.Adapter mAdapter) {
        this.mAdapter = mAdapter;
        mHeaders = new SparseArray<>();
        mFooters = new SparseArray<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaders.indexOfKey(viewType) >= 0) {
            //头部
            return createHeaderViewHolder(mHeaders.get(viewType));
        } else if (mFooters.indexOfKey(viewType) >= 0) {
            //底部
            return createFooterViewHolder(mFooters.get(viewType));
        }
        //列表
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    //创建底部ViewHolder
    private RecyclerView.ViewHolder createFooterViewHolder(View view) {
        return new RecyclerView.ViewHolder(view) {

        };
    }

    //创建头部viewHolder
    private RecyclerView.ViewHolder createHeaderViewHolder(View view) {
        return new RecyclerView.ViewHolder(view) {
        };
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int numHeaders = mHeaders.size();
        if (position < numHeaders) {
            return;
        }
        int adjPosition = position - numHeaders;
        if (mAdapter != null) {
            int adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                mAdapter.onBindViewHolder(holder, adjPosition);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        //根据当前位置--->viewType
        int numHeaders = mHeaders.size();
        //头部 返回key
        if (position < numHeaders) {
            return mHeaders.keyAt(position);
        }
        //正常数据 正常返回
        int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(position);
            }
        }
        //底部 返回 key
        int footPosition = adjPosition - adapterCount;
        return mFooters.keyAt(footPosition);
    }

    @Override
    public int getItemCount() {
        return mHeaders.size() + mFooters.size() + mAdapter.getItemCount();
    }


    //添加头部
    protected void addHeaderView(View view) {
        int index = mHeaders.indexOfValue(view);
        if (index == -1) {
            mHeaders.put(BASE_HEADER_KEY, view);
            BASE_HEADER_KEY++;
        }
    }

    //移除头部
    protected void removeHeaderView(View view) {
        int index = mHeaders.indexOfValue(view);
        if (index != -1) {
            mHeaders.removeAt(index);
            notifyDataSetChanged();
        }
    }

    //添加底部
    protected void addFooterView(View view) {
        int index = mFooters.indexOfValue(view);
        if (index == -1) {
            mFooters.put(BASE_FOOTER_KEY++, view);
        }
    }

    //移除底部
    protected void removeFooterView(View view) {
        int index = mFooters.indexOfValue(view);
        if (index != -1) {
            mFooters.removeAt(index);
            notifyDataSetChanged();
        }
    }


    public int getHeaderSize(){
        return mHeaders.size();
    }

    public int getRealSize(){
        return mAdapter.getItemCount();
    }

    public int getFooterSize(){
        return mFooters.size();
    }

}
