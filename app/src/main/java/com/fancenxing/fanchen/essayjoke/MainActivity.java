package com.fancenxing.fanchen.essayjoke;

import android.util.Log;

import com.fancenxing.fanchen.baselibrary.base.ExceptionCrashHandler;
import com.fancenxing.fanchen.framelibrary.BaseSkinActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends BaseSkinActivity {

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
                int len = 0;
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
        int i = 12 / 0;
    }

}
