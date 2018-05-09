package com.fancenxing.fanchen.essayjoke;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fancenxing.fanchen.baselibrary.http.HttpUtils;
import com.fancenxing.fanchen.baselibrary.ioc.OnClick;
import com.fancenxing.fanchen.baselibrary.ioc.ViewById;
import com.fancenxing.fanchen.framelibrary.BaseSkinActivity;
import com.fancenxing.fanchen.framelibrary.DefaultNavigationBar;
import com.fancenxing.fanchen.framelibrary.HttpCallback;

public class MainActivity extends BaseSkinActivity {


    @ViewById(R.id.ll_parent)
    LinearLayout llParent;
    @ViewById(R.id.test)
    private Button mButton;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
                .setTitle("测试标题")
                .setRightTitle("右边标题")
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "onClick---", Toast.LENGTH_SHORT).show();
                    }
                })
                .builder();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        HttpUtils.with(this)
                .url("")
                .addParams(null)
                .get()
                .execute(new HttpCallback<Bean>() {
                    @Override
                    public void onError(Exception e) {

                    }

                    @Override
                    public void onSuccess(Bean result) {

                    }
                });
    }

    @OnClick(R.id.test)
    public void test(View view) {

    }
}
