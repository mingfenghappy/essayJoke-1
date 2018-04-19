package com.fancenxing.fanchen.essayjoke;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fancenxing.fanchen.baselibrary.base.ExceptionCrashHandler;
import com.fancenxing.fanchen.baselibrary.ioc.OnClick;
import com.fancenxing.fanchen.baselibrary.ioc.ViewById;
import com.fancenxing.fanchen.framelibrary.BaseSkinActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends BaseSkinActivity {


    @ViewById(R.id.test)
    private Button mButton;

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
        //获取上次的崩溃信息上传到服务器
        File file = ExceptionCrashHandler.getInstance()
                .getCrashFile();
        if (file.exists()) {
            //上传到服务器
            Log.d("file", "---crash-file-->" + file.toString());
            try {
                FileInputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis);
                char[] buffer = new char[512];
                int len;
                StringBuffer stringBuffer = new StringBuffer();
                while ((len = isr.read(buffer)) != -1) {
                    stringBuffer.append(new String(buffer, 0, len));
                }

                Log.d("file", "--crash info --->" + stringBuffer.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //获取本地的fix.aptach
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.apatch");
        if (fixFile.exists()) {
            //修复bug
            try {
                BaseApplication.patchManager.addPatch(fixFile.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "修复失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick(R.id.test)
    public void test(View view) {
        Toast.makeText(this, 2 / 1 + "测试", Toast.LENGTH_SHORT).show();
    }
}
