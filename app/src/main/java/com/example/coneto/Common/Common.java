package com.example.coneto.Common;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import com.example.coneto.Model.UserModel;

import java.util.Random;

public class Common {
    public static final String CHAT_REFERENCE = "Chat";
    public static final String CHAT_DETAIL_REFERENCE = "Detail";
    public static UserModel currentUser = new UserModel();
    public static final String USER_REFERENCES = "People";
    public static final String CHAT_LIST_REFERENCES = "ChatList";

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
}
