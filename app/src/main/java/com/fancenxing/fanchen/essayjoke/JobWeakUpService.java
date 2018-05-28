package com.fancenxing.fanchen.essayjoke;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.List;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/22.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobWeakUpService extends JobService {

    private int mJobId = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //开启轮寻
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo info = new JobInfo.Builder(mJobId, new ComponentName(this, JobWeakUpService.class))
                .setPeriodic(2000)
                .build();
        jobScheduler.schedule(info);

        return START_STICKY;

    }

    @Override
    public boolean onStartJob(JobParameters params) {
        //开启定时任务，定时轮寻 看KeepLiveService是否被杀死
        //如果杀死了启动 轮询onStartJob

        //判断服务是否在运行
        if (!serviceAlive(KeepLiveService.class.getName())) {
            startService(new Intent(this, KeepLiveService.class));
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private boolean serviceAlive(String serviceName) {
        boolean isWork = false;
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> list = activityManager.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo info : list) {
            String name = info.service.getClassName();
            if (name.equals(serviceName)) {
                isWork = true;
                break;
            }

        }
        return isWork;
    }
}
