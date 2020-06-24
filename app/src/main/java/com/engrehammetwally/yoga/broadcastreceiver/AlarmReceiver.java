package com.engrehammetwally.yoga.broadcastreceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import android.widget.Toast;

import com.engrehammetwally.yoga.DailyTrainingActivity;
import com.engrehammetwally.yoga.R;
import com.engrehammetwally.yoga.service.RingtonePlayingService;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm On", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, RingtonePlayingService.class);
        context.startService(i);

        createNotification(context);
    }

    private void createNotification(Context context) {

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("It's Time")
                .setContentText("Time to training")
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        Intent intent = new Intent(context, DailyTrainingActivity.class);



        PendingIntent pendingIntent = PendingIntent.getActivity(context, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);


        Intent dismissIntent = new Intent(context, RingtonePlayingService.class);
        dismissIntent.setAction(RingtonePlayingService.ACTION_DISMISS);
        PendingIntent pendingServiceIntent = PendingIntent
                .getService(context, 123, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Action action = new NotificationCompat
                .Action(android.R.drawable.ic_lock_idle_alarm, "DISMISS", pendingServiceIntent);
        builder.addAction(action);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(321, notification);
    }
}
