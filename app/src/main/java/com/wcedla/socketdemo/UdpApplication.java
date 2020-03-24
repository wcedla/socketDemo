package com.wcedla.socketdemo;

import android.app.Application;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import static com.wcedla.socketdemo.data.SocketData.DEBUG_VERSION;
import static com.wcedla.socketdemo.data.SocketData.LOG_TAG;

public class UdpApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化Logger
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .methodCount(2)
                .tag(LOG_TAG)
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return DEBUG_VERSION;
            }
        });
        //初始化Hawk
        Hawk.init(this).build();
    }

}
