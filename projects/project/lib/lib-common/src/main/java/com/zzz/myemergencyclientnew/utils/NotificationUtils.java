package com.zzz.myemergencyclientnew.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

import com.zzz.myemergencyclientnew.common.R;

public class NotificationUtils extends ContextWrapper {
    /**
     * 全局弹出通知栏方法,携带参数跳转页面
     */
    public static void showNotification(Context context, String title, String text, Intent intent) {
        //通知栏点击时需要一个唯一ID，确认地址跳转，这里我用了时间戳截取方式，一定程度上保证唯一
        String timeString = "" + System.currentTimeMillis();
        int timeId = Integer.valueOf(timeString.substring(timeString.length() - 4, timeString.length()));
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(title)
                .setContentText(text)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, timeId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(timeId, notification);
    }

    /**
     * 全局弹出通知栏方法,纯显示
     */
    public static void showNotification(Context context, String title, String text) {
        //通知栏点击时需要一个唯一ID，确认地址跳转，这里我用了时间戳截取方式，一定程度上保证唯一
        String timeString = "" + System.currentTimeMillis();
        int timeId = Integer.valueOf(timeString.substring(timeString.length() - 4, timeString.length()));
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle(title)
                .setContentText(text)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
//        PendingIntent pendingIntent = PendingIntent.getActivity(HostApplication.getApp(), timeId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(timeId, notification);
    }
    /**
     * 测试锁屏开通知
     */
//    public static void sendSimpleNotification(Context context) {
//
//        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        //创建点击通知时发送的广播
//        Intent intent = new Intent(context, NotificationService.class);
//        intent.setAction(ACTION_SIMPLE);
//        PendingIntent pi = PendingIntent.getService(context,0,intent,0);
//        //创建删除通知时发送的广播
//        Intent deleteIntent = new Intent(context,NotificationService.class);
//        deleteIntent.setAction(ACTION_DELETE);
//        PendingIntent deletePendingIntent = PendingIntent.getService(context,0,deleteIntent,0);
//        //创建通知
//        Notification.Builder nb = new Notification.Builder(context, NotificationChannels.LOW)
//                //设置通知左侧的小图标
//                .setSmallIcon(R.mipmap.default_head_photo)
//                //设置通知标题
//                .setContentTitle("Simple notification")
//                //设置通知内容
//                .setContentText("Demo for simple notification !")
//                //设置点击通知后自动删除通知
//                .setAutoCancel(true)
//                //设置显示通知时间
//                .setShowWhen(true)
//                //设置通知右侧的大图标
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.default_head_photo))
//                //设置点击通知时的响应事件
//                .setContentIntent(pi)
//                //设置删除通知时的响应事件
//                .setDeleteIntent(deletePendingIntent);
//        //发送通知
//        nm.notify(NOTIFICATION_SAMPLE,nb.build());
//    }

//    public static void showAlwaysNotify(Context context){
//        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
////        PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, getIntent(), 0);
//        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
//                .setTicker("setTicker")
//                .setContentTitle("setContentTitle")
//                .setContentText("setContentText");
////                .setContentIntent(pendingIntent);
//        Notification mNotification = mBuilder.build();
//        mNotification.icon = R.mipmap.ic_launcher;
//        //在通知栏上点击此通知后自动清除此通知
//        //FLAG_ONGOING_EVENT 在顶部常驻，可以调用下面的清除方法去除
//        //FLAG_AUTO_CANCEL 点击和清理(例如左滑)可以去调
//        mNotification.flags = Notification.FLAG_ONGOING_EVENT;
//        mNotification.defaults = Notification.DEFAULT_VIBRATE;
//        mNotification.tickerText = "mNotification.tickerText";
//        mNotification.when=System.currentTimeMillis();
//        nm.notify(12345, mNotification);
//    }

    private NotificationManager mManager;
    public static final String ANDROID_CHANNEL_ID = "com.baidu.baidulocationdemo";
    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";

    public NotificationUtils(Context base) {
        super(base);
//        createChannels();
    }

//    public void createChannels() {
//        // create android channel
//        NotificationChannel androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
//                ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
//        // Sets whether notifications posted to this channel should display notification lights
//        androidChannel.enableLights(true);
//        // Sets whether notification posted to this channel should vibrate.
//        androidChannel.enableVibration(true);
//        // Sets the notification light color for notifications posted to this channel
//        androidChannel.setLightColor(Color.GREEN);
//        // Sets whether notifications posted to this channel appear on the lockscreen or not
//        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
//
//        getManager().createNotificationChannel(androidChannel);
//    }

    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public Notification.Builder getAndroidChannelNotification(String title, String body) {
        return new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(android.R.drawable.stat_notify_more)
                .setAutoCancel(true);
    }


//    public void showChangZhu(){
//
//        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification n = new Notification(R.drawable.n,
//                "Hello,there!",
//                System.currentTimeMillis());
//        n.flags
//                = Notification.FLAG_AUTO_CANCEL;
//        Intent i = new Intent(arg0.getContext(), NotificationShow.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
////PendingIntent
//        PendingIntent contentIntent = PendingIntent.getActivity(
//                arg0.getContext(),
//                R.string.app_name,
//                i,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//
//        n.setLatestEventInfo(
//                arg0.getContext(),
//                "Hello,there!",
//                "Hello,there,I'm john.",
//                contentIntent);
//        nm.notify(R.string.app_name,
//                n);
//
//    }
}