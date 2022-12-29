package com.example.coneto.Common;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;

import androidx.core.app.NotificationCompat;

import com.example.coneto.Model.UserModel;
import com.example.coneto.R;
import com.example.coneto.Services.MyFCMService;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class Common {
    public static final String CHAT_REFERENCE = "Chat";
    public static final String CHAT_DETAIL_REFERENCE = "Detail";
    public static final String NOTI_TITLE = "title";
    public static final String NOTI_CONTENT = "content";
    public static final String NOTI_SENDER = "sender";
    public static final String NOTI_ROOM_ID = "room_id";
    public static UserModel currentUser = new UserModel();
    public static final String USER_REFERENCES = "People";
    public static final String CHAT_LIST_REFERENCES = "ChatList";
    public static String roomSelected = "";

    public static UserModel chatUser = new UserModel();

    public static String generateChatRoomId(String a, String b) {
        if (a.compareTo(b) > 0) {
            return new StringBuilder(a).append(b).toString();
        } else if (a.compareTo(b) < 0) {
            return new StringBuilder(b).append(a).toString();
        } else {
            return new StringBuilder("Chat_Yourself_Error").append(new Random().nextInt()).toString();
        }
    }

    public static String getName(UserModel chatUser) {
        return new StringBuilder(chatUser.getFirstName())
                .append(" ").append(chatUser.getLastName()).toString();
    }

    @SuppressLint("Range")
    public static String getFilename(ContentResolver contentResolver, Uri fileUri) {
        String result = null;
        if (fileUri.getScheme().equals("content")) {
            Cursor cursor = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                cursor = contentResolver.query(fileUri, null, null, null);
            }
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = fileUri.getPath();
            int cut = result.lastIndexOf("/");
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static void showNotification(Context context, int id, String title, String content, String sender, String room_id, Intent intent) {
        PendingIntent pendingIntent = null;
        if (intent != null) {
            pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            String NOTIFICATION_CHANNEL_ID = "com.example.coneto";
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                        "Coneto",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationChannel.setDescription("Coneto");
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
                notificationChannel.enableVibration(true);

                notificationManager.createNotificationChannel(notificationChannel);
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
            builder.setContentTitle(title)
                    .setContentText(content)
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_baseline_image_24));
            if (pendingIntent != null) {
                builder.setContentIntent(pendingIntent);
            }
            Notification notification = builder.build();
            if(!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(sender) &&
            !Common.roomSelected.equals(room_id)) {
                notificationManager.notify(id, notification);
            }

        }
    }
}
