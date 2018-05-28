package com.fancenxing.fanchen.essayjoke;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fancenxing.fanchen.baselibrary.ioc.OnClick;
import com.fancenxing.fanchen.baselibrary.ioc.ViewById;
import com.fancenxing.fanchen.framelibrary.BaseSkinActivity;
import com.fancenxing.fanchen.framelibrary.skin.SkinManager;

public class MainActivity extends BaseSkinActivity {


    @ViewById(R.id.ll_parent)
    LinearLayout llParent;
    @ViewById(R.id.iv_bg)
    ImageView ivBg;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initTitle() {
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Intent intent = new Intent(this, KeepLiveService.class);
        startService(intent);

        startService(new Intent(this, GuardService.class));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startService(new Intent(this, JobWeakUpService.class));
        }

    }

    @OnClick({R.id.change_skin, R.id.restore_default, R.id.skip})
    public void test(View view) {
        switch (view.getId()) {
            case R.id.change_skin:
                changeSkin();
                break;
            case R.id.restore_default:
                restoreDefault();
                break;
            case R.id.skip:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void restoreDefault() {
        SkinManager.getInstance()
                .restoreDefault();
    }

    private void changeSkin() {
        SkinManager.getInstance()
                .loadSkin("/storage/emulated/0/Android/data/skin.skin");
    }

}
