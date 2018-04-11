package com.fancenxing.fanchen.essayjoke;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fancenxing.fanchen.baselibrary.ioc.CheckNet;
import com.fancenxing.fanchen.baselibrary.ioc.OnClick;
import com.fancenxing.fanchen.baselibrary.ioc.ViewById;
import com.fancenxing.fanchen.baselibrary.ioc.ViewUtils;

public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.tv)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        mTextView.setText("IOC");
    }

    @CheckNet
    @OnClick(R.id.tv)
    public void onClick(View view) {
        Toast.makeText(this, "show", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.iv)
    public void test(View view) {
        Toast.makeText(this, "showImg", Toast.LENGTH_SHORT).show();
    }
}
