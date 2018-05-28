package com.fancenxing.fanchen.essayjoke.commonAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/24.
 */

public abstract class RecyclerCommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    private int mLayoutId;
    private List<T> mData;

    private LayoutInflater mInflater;
    private ItemClickListener mItemClickListener;

    private MultiTypeSupport mTypeSupport;

    public RecyclerCommonAdapter(Context context, int layoutId, List<T> data) {
        this.mLayoutId = layoutId;
        this.mData = data;
        mInflater = LayoutInflater.from(context);
    }

    public RecyclerCommonAdapter(Context context, int layoutId, List<T> data, MultiTypeSupport<T> typeSupport) {
        this(context, layoutId, data);
        this.mTypeSupport = typeSupport;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mTypeSupport != null) {
            mLayoutId = viewType;
        }
        View view = mInflater.inflate(mLayoutId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (mTypeSupport != null) {
            return mTypeSupport.getLayoutId(mData.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        convert(holder, mData.get(position), position);
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * 绑定数据
     *
     * @param holder   viewHolder
     * @param data     当前位置的数据
     * @param position 当前位置
     */
    protected abstract void convert(ViewHolder holder, T data, int position);
}
