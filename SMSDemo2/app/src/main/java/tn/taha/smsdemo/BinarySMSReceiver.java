package tn.taha.smsdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;

import com.varma.samples.smsdemo.R;

public class BinarySMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;

        if (null != bundle) {
            String contentMessage = "";
            String sender = "";
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            byte[] data = null;

            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                sender = msgs[i].getOriginatingAddress();
                contentMessage += "";

                data = msgs[i].getUserData();

                for (int index = 0; index < data.length; ++index) {
                    contentMessage += Character.toString((char) data[index]);
                }
            }


            showNotification(context, sender, contentMessage);

        }
    }


    private void showNotification(Context context, String numberFrom, String Message) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.mail)
                        .setContentTitle("From:"+numberFrom)
                        .setContentText(Message);
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

    }
}
