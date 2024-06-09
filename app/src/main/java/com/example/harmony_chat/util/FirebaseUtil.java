package com.example.harmony_chat.util;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class FirebaseUtil {

    public static CollectionReference getChatroomMessageReference(String chatroomId) {
        return FirebaseFirestore.getInstance().collection("CHATS");
    }

    public static String timestampToString(Timestamp timestamp) {
        return new SimpleDateFormat("HH:MM").format(timestamp.toDate());
    }
}
