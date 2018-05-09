package com.fancenxing.fanchen.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.fancenxing.fanchen.baselibrary.R;

import java.lang.ref.WeakReference;

/**
 * 功能描述：自定义万能dialog
 * Created by 孙中宛 on 2018/4/27.
 */

public class AlertDialog extends Dialog {

    private AlertController mAlert;

    public AlertDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mAlert = new AlertController(this, getWindow());
    }


    public static class Builder {
        AlertController.AlertParams params;

        public Builder(Context context) {
            this(context, R.style.dialog);
        }

        public Builder(Context context, int themeResId) {
            params = new AlertController.AlertParams(context, themeResId);
        }

        public Builder setContentView(View contentView) {
            params.contentView = contentView;
            return this;
        }

        public Builder setContentView(int contentViewResId) {
            params.contentViewResId = contentViewResId;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            params.cancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            params.dismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            params.keyListener = onKeyListener;
            return this;
        }

        public Builder setText(int viewId, CharSequence text) {
            params.textArray.put(viewId, text);
            return this;
        }

        public Builder setCancel(boolean cancelable) {
            params.cancelable = cancelable;
            return this;
        }

        public Builder setOnClickListener(int viewId, View.OnClickListener listener) {
            params.clickArray.put(viewId, new WeakReference<>(listener));
            return this;
        }

        public Builder fullWidth() {
            params.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        public Builder fromBottom(boolean isAnimation) {
//            if (isAnimation){
//设置位移动画
//            }
            params.mGravity = Gravity.BOTTOM;
            return this;
        }

        public Builder setWidthAndHeight(int width, int height) {
            params.mWidth = width;
            params.mHeight = height;
            return this;
        }

        public Builder addDefalultAniamtion() {
            //设置默认动画
//            params.mAnimation = R.style.;
            return this;
        }

        public Builder setAniamtion(int animationStyle) {
            //设置默认动画
            params.mAnimation = animationStyle;
            return this;
        }

        public AlertDialog create() {
            AlertDialog dialog = new AlertDialog(params.context, params.themeResId);
            params.apply(dialog.mAlert);
            return dialog;
        }

        public AlertDialog show() {
            AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }


}
