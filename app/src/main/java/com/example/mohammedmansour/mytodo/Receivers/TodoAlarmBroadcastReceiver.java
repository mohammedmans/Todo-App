package com.example.mohammedmansour.mytodo.Receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.mohammedmansour.mytodo.AddTodo;
import com.example.mohammedmansour.mytodo.R;
import com.example.mohammedmansour.mytodo.TodoList;

public class TodoAlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        Log.e("todoalarm", "recieved");
        showNotification(context, title);
    }

    public void showNotification(Context context, String title) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "todo");

        builder.setContentTitle(title);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setColor(Color.rgb(255, 0, 0));
        builder.setVibrate(new long[]{500, 1000, 5000});

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, TodoList.class), PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(contentIntent);

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(500, builder.build());

    }
}
