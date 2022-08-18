package br.edu.ifpe.tads.ametavia.adapters;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import br.edu.ifpe.tads.ametavia.R;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    public static final String TAG = GeofenceBroadcastReceiver.class.getSimpleName();
    private Context context;

    public GeofenceBroadcastReceiver() {}
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override

    public void onReceive(Context context, Intent intent) {
        this.context = context;
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }

        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ) {
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            sendNotification("Há ongs proximas da sua localização!");

        } else {
            // Log the error.
            Log.e(TAG, "Error on receive geofence broadcast");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendNotification(String notificationDetails) {
        Log.i(TAG, "Send message called");

        String channelId = "geofence-channel";
        Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);

        builder.setColor(Notification.COLOR_DEFAULT)
                .setChannelId(channelId)
                .setContentTitle(notificationDetails)
                .setContentText("Click notification to remove")
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setVibrate(new long[]{1000, 1000})
                .setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
        notificationManager.notify(generateRandom(), builder.build());
    }

    public int generateRandom() {
        Random random = new Random();
        return random.nextInt(10000 - 100) + 100;
    }

}
