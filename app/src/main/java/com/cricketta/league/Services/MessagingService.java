package com.cricketta.league.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.cricketta.league.CricApplication;
import com.cricketta.league.Main.Main_Activity;
import com.cricketta.league.R;
import com.cricketta.league.events.PlayerSelectedEvent;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import REST.ViewModel.ScoreCard;

/**
 * Created by rahul.sharma01 on 3/27/2017.
 */

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (CricApplication.isActivityVisible() && remoteMessage.getNotification() != null) {
            Intent intent = new Intent(this, Main_Activity.class);
            intent.putExtra("notData", remoteMessage.getData().toString());

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setAutoCancel(true)
                    .setSound(notificationSoundURI)
                    .setContentIntent(resultIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(getBaseContext().NOTIFICATION_SERVICE);

            notificationManager.notify(0, mNotificationBuilder.build());
        }
        Log.d("retro", "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d("retro", "Message data payload: " + remoteMessage.getData());
        }
        if (remoteMessage.getNotification() != null) {
            Log.d("retro", "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        if (remoteMessage.getNotification() == null)
            processData(remoteMessage.getData().toString());

    }

    private void processData(String data) {
        try {
            JSONObject requestData = new JSONObject(data);

            if (requestData.getString("Tag").equals("PLAYER_SELECTED")) {
                int playerId = requestData.getInt("playerId");
                int leagueMatchId = requestData.getInt("leagueMatchId");
                int userId = requestData.getInt("userId");
                boolean extra = requestData.getBoolean("isExtra");
                ScoreCard card = new ScoreCard();
                card.isExtra = extra;

                EventBus.getDefault().post(new PlayerSelectedEvent(playerId, leagueMatchId, 0, userId, card));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
