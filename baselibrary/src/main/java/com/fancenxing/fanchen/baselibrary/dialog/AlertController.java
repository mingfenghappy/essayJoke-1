package com.fancenxing.fanchen.baselibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/4/27.
 */

class AlertController {

    private AlertDialog mDialog;
    private Window mWindow;

    public AlertController() {
    }

    public AlertController(AlertDialog mDialog, Window mWindow) {
        this.mDialog = mDialog;
        this.mWindow = mWindow;
    }

    public AlertDialog getDialog() {
        return mDialog;
    }

    public Window getWindow() {
        return mWindow;
    }

    public static class AlertParams {
        public Context context;
        public int themeResId;

        public boolean cancelable;

        public DialogInterface.OnCancelListener cancelListener;

        public DialogInterface.OnDismissListener dismissListener;

        public DialogInterface.OnKeyListener keyListener;

        public View contentView;
        public int contentViewResId;
        public DialogViewHelper viewHelper;

        //存放字体的修改
        public SparseArray<CharSequence> textArray = new SparseArray<>();

        //存放点击事件
        public SparseArray<WeakReference<View.OnClickListener>> clickArray = new SparseArray<>();

        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mAnimation;
        public int mGravity = Gravity.CENTER;

        public AlertParams(Context context, int themeResId) {
            this.context = context;
            this.themeResId = themeResId;
        }

        public void apply(AlertController alert) {
            //1、设置布局
            if (contentViewResId != 0) {
                viewHelper = new DialogViewHelper(context, contentViewResId);
            }

            if (contentView != null) {
                viewHelper = new DialogViewHelper();
                viewHelper.setContentView(contentView);
            }

            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置布局");
            }

            alert.getDialog().setContentView(viewHelper.getContentView());

            //2、设置文本
            int textSize = textArray.size();
            for (int i = 0; i < textSize; i++) {
                viewHelper.setText(textArray.keyAt(i), textArray.valueAt(i));
            }

            //3、设置点击
            int clickSize = clickArray.size();
            for (int i = 0; i < clickSize; i++) {
                viewHelper.setOnClickListener(clickArray.keyAt(i), clickArray.valueAt(i));
            }

            //4、配置自定义的效果 全屏  从底部弹出 默认动画
            Window window = alert.getWindow();
            window.setGravity(mGravity);
            if (mAnimation != 0) {
                window.setWindowAnimations(mAnimation);
            }

            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            window.setAttributes(params);
        }
    }
}
