package trophy.projetc2.Match;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-06-23.
 */

public class Match_Write_Push extends BroadcastReceiver {
    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;

    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
        //NotificationManager 안드로이드 상태바에 메세지를 던지기위한 서비스 불러오고
        NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, Match_Write_Push.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.icon).setTicker("HETT").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("오늘의 농구").setContentText("오늘 운동은 어떠셨나요?")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingIntent).setAutoCancel(true);

        notificationmanager.notify(1, builder.build());
    }
}
