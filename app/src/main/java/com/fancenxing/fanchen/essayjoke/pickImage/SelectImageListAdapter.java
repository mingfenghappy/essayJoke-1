package com.fancenxing.fanchen.essayjoke.pickImage;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.fancenxing.fanchen.essayjoke.R;
import com.fancenxing.fanchen.essayjoke.SelectImageActivity;
import com.fancenxing.fanchen.essayjoke.commonAdapter.RecyclerCommonAdapter;
import com.fancenxing.fanchen.essayjoke.commonAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/29.
 */

public class SelectImageListAdapter extends RecyclerCommonAdapter<String> {

    private ViewHolder.HolderImageLoader imageLoader;
    private ArrayList<String> mSelected;
    private int mMaxCount;

    public SelectImageListAdapter(Context context, List<String> data) {
        super(context, R.layout.item_img, data);
        imageLoader = new GlideImageLoader(context);
        mSelected = new ArrayList<>();
    }

    @Override
    protected void convert(ViewHolder holder, String data, int position) {
        if (SelectImageActivity.EXTRA_SHOW_CAMERA.equals(data)) {
            holder.setImageResource(R.id.iv_img, R.mipmap.take_photo)
                    .setVisiable(R.id.cb_select, View.GONE);
        } else {
            CheckBox cb = holder.getView(R.id.cb_select);
            if (mSelected.contains(data)) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }
            cb.setTag(data);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Object tag = buttonView.getTag();
                    if (tag == null) {
                        return;
                    }
                    if (isChecked) {
                        if (mSelected.size() < mMaxCount) {
                            if (!mSelected.contains(tag)) {
                                mSelected.add((String) tag);
                            }
                        } else {
                            buttonView.setChecked(false);
                            Toast.makeText(buttonView.getContext(), "不能再添加了", Toast.LENGTH_SHORT).show();
                        }
                    } else if (mSelected.contains(tag)) {
                        mSelected.remove(tag);
                    }

                }
            });
            cb.setVisibility(View.VISIBLE);
            holder.loadImage(R.id.iv_img, imageLoader, data);
        }
    }

    public void setMaxCount(int maxCount) {
        this.mMaxCount = maxCount;
    }

    public ArrayList<String> getSelectList() {
        return mSelected;
    }
}
