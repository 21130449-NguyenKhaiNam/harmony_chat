package com.example.harmony_chat.util;

import com.example.harmony_chat.model.ChatroomModel;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class FirebaseUtil {

    public static String currentUserId() {
        return FirebaseAuth.getInstance().getUid();
    }

    public static DocumentReference getChatroomReference(String chatroomId) {
        return FirebaseFirestore.getInstance().collection("CHATROOMS").document(chatroomId);
    }

    public static String getChatroomId(String userId1, String userId2) {
        return (userId1.hashCode() < userId2.hashCode()) ? userId1 + "_" + userId2 : userId2 + "_" + userId1;
    }

    public static String timestampToString(Timestamp timestamp) {
        return new SimpleDateFormat("HH:MM").format(timestamp.toDate());
    }
}
