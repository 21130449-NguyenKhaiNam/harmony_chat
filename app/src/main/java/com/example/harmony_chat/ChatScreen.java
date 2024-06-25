package com.example.harmony_chat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harmony_chat.Adapter.ChatRecyclerAdapter;
import com.example.harmony_chat.config.DefinePathApi;
import com.example.harmony_chat.model.ChatMessageModel;
import com.example.harmony_chat.model.ChatroomModel;
import com.example.harmony_chat.model.Profile;
import com.example.harmony_chat.model.Room;
import com.example.harmony_chat.model.User;
import com.example.harmony_chat.service.CallService;
import com.example.harmony_chat.util.AndroidUtil;
import com.example.harmony_chat.util.CheckInfomation;
import com.example.harmony_chat.util.FirebaseUtil;
import com.example.harmony_chat.util.RxHelper;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatScreen extends AppCompatActivity implements OnMapReadyCallback {

    private TextView txtChatName, shareLocation, emoji, sharePicture, voice;
    private MediaRecorder mediaRecorder;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private SpeechRecognizer speechRecognizer;
    private String voiceFilePath;
    private FusedLocationProviderClient fusedLocationClient;
    private EditText txtChatMessage;
    private ImageView img_avatar;
    private ImageButton backBtn, btn_send, btnMore;
    private Room room;
    private ChatRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private List<Profile> profiles = new ArrayList<>();
    private String roomId, userId;
    private ChatroomModel chatroomModel;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private LinearLayout footer;
    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
//       // Setup config
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Kiểm tra và yêu cầu quyền ghi âm nếu cần
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO_PERMISSION);
        } else {
            setupSpeechRecognizer();
        }
        hideSystemUI();
        loadConfig();
    }

    private void setupSpeechRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            speechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {
                    // Đang chuẩn bị để bắt đầu nói
                    Log.d("SpeechRecognizer", "Ready for speech");
                }

                @Override
                public void onBeginningOfSpeech() {
                    // Bắt đầu nhận diện giọng nói
                    Log.d("SpeechRecognizer", "Beginning of speech");
                }

                @Override
                public void onRmsChanged(float rmsdB) {
                    // Độ mạnh của giọng nói thay đổi
                }

                @Override
                public void onBufferReceived(byte[] buffer) {
                    // Đã nhận được dữ liệu âm thanh
                }

                @Override
                public void onEndOfSpeech() {
                    // Kết thúc nhận diện giọng nói
                    Log.d("SpeechRecognizer", "End of speech");
                }

                @Override
                public void onError(int error) {
                    // Xảy ra lỗi trong quá trình nhận diện
                    Log.e("SpeechRecognizer", "Error: " + error);
                }

                @Override
                public void onResults(Bundle results) {
                    // Kết quả nhận diện giọng nói
                    ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    if (matches != null && !matches.isEmpty()) {
                        String text = matches.get(0); // Lấy kết quả nhận diện đầu tiên
                        AndroidUtil.showToast(ChatScreen.this, "Recognized text: " + text);
                        Log.d("SpeechRecognizer", "Recognized text: " + text);
                        // Ở đây bạn có thể gửi `text` đi đâu đó, ví dụ lưu vào cơ sở dữ liệu hoặc gửi qua mạng.
                        sendMessageToUser(text + "text");
                    }
                }

                @Override
                public void onPartialResults(Bundle partialResults) {
                    // Kết quả nhận diện tạm thời
                }

                @Override
                public void onEvent(int eventType, Bundle params) {
                    // Sự kiện không xác định
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }

    private void startSpeechToText() {
        if (speechRecognizer != null) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US"); // Chỉ định ngôn ngữ (ở đây là tiếng Anh)
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something...");
            speechRecognizer.startListening(intent);
        }
    }

    private void hideSystemUI() {
        final View decorView = getWindow().getDecorView();
        Runnable setSystemUiVisibility = () -> decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        setSystemUiVisibility.run();
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                setSystemUiVisibility.run();
            }
        });
    }

    private void loadConfig() {
        userId = AndroidUtil.getUserId(this);
        // Không có tài khoản
        if (CheckInfomation.isEmpty(userId)) {
            AndroidUtil.showError("UserId", userId);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        footer = findViewById(R.id.chat_screen_footer);
        txtChatName = findViewById(R.id.txt_chat_name);
        txtChatMessage = findViewById(R.id.txt_chat_message);
        txtChatMessage.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
        txtChatMessage.setTextIsSelectable(true);
        recyclerView = findViewById(R.id.chat_recycler_view);
        img_avatar = findViewById(R.id.img_avatar);
        backBtn = findViewById(R.id.backBtn);
        btn_send = findViewById(R.id.btn_send);
        btnMore = findViewById(R.id.btn_more);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle.isEmpty()) {
            back();
        }
        room = (Room) bundle.getSerializable("room");
        roomId = String.valueOf(room.getId());

        RxHelper.performImmediately(() -> {
            List<User> users = CallService.getInstance().getAllMembersRoom(roomId);
            for (User u : users) {
                Profile userPro5 = CallService.getInstance().viewMyProfile(u.getId());
                if (userPro5.getUser().getId().equals(userId)) {
                    profiles.add(0, userPro5);
                } else
                    profiles.add(userPro5);
            }
            runOnUiThread(() -> {
                txtChatName.setText(bundle.getString("chatname"));
                process();
                loadImage4Chatroom();
                setupChatRecyclerView();
                getOrCreateChatroomModel();
            });
        });

    }

    private void getOrCreateChatroomModel() {
        List<String> userIds = new ArrayList<>();
        RxHelper.performImmediately(() -> {
            List<User> users = CallService.getInstance().getAllMembersRoom(roomId);
            users.forEach(user -> {
                userIds.add(user.getId());
            });
            runOnUiThread(() -> {
                FirebaseUtil.getChatroomReference(roomId).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        chatroomModel = task.getResult().toObject(ChatroomModel.class);
                        if (chatroomModel == null) {
                            chatroomModel = new ChatroomModel(
                                    roomId,
                                    userIds,
                                    Timestamp.now(),
                                    ""
                            );
                            FirebaseUtil.getChatroomReference(roomId).set(chatroomModel);
                        }
                    }
                });
            });
        });
    }

    private void loadImage4Chatroom() {
        AndroidUtil.loadImage(room.getImage(), img_avatar);
    }

    private void process() {
        backBtn.setOnClickListener(v -> back());
        btn_send.setOnClickListener(e -> {
            String message = txtChatMessage.getText().toString().trim();
            if (!message.isEmpty()) {
                sendMessageToUser(message);
            }
        });
        btnMore.setOnClickListener(e -> createPopupMoreFeatures());
    }

    private void createPopupMoreFeatures() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.fragement_more_feature_chat, null);

        int width = getResources().getDimensionPixelSize(R.dimen.popup_options);
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        int[] location = new int[2];
        btnMore.getLocationOnScreen(location);  // get btnMore location

        popupWindow.showAtLocation(btnMore, Gravity.NO_GRAVITY,
                location[0] - width + btnMore.getWidth(),
                location[1] - 350);

        // Share location
        shareLocation = popupView.findViewById(R.id.share_location);
        shareLocation.setOnClickListener(v -> {
            popupWindow.dismiss();
            shareLocation();
        });

        // Voice
        voice = popupView.findViewById(R.id.voice);
        voice.setOnClickListener(v -> {
            popupWindow.dismiss();
            // send voice
            startRecording();
        });

        // Share picture
        sharePicture = popupView.findViewById(R.id.share_picture);
        sharePicture.setOnClickListener(v -> {
            popupWindow.dismiss();
            openGallery();
        });


        // Send Emoji
        emoji = popupView.findViewById(R.id.emoji);
        emoji.setOnClickListener(v -> {
            txtChatMessage.requestFocus();
            txtChatMessage.postDelayed(() -> {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    popupWindow.dismiss();
//                    imm.showSoftInput(txtChatMessage, InputMethodManager.SHOW_IMPLICIT);
                    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
//                    WindowCompat.getInsetsController(getWindow(), txtChatMessage).show(WindowInsetsCompat.Type.ime());
//                    imm.hideSoftInputFromWindow(txtChatMessage.getWindowToken(), 0);
                }
            }, 200); // Delay in milliseconds
        });
    }

    private void startRecording() {
        if (mediaRecorder != null) {
            stopRecording();
        } else if (checkPermission()) {
            // Setup MediaRecorder
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            voiceFilePath = getExternalCacheDir().getAbsolutePath() + "/voice_message.3gp";
            mediaRecorder.setOutputFile(voiceFilePath);

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
                showStopRecordingDialog(); // Hiển thị popup
                startSpeechToText();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            requestPermission();
        }
    }

    private void showStopRecordingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đang ghi âm");
        builder.setMessage("Nhấn 'Dừng lại' để dừng ghi âm.");
        builder.setPositiveButton("Dừng lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopRecording();
            }
        });
        builder.setCancelable(false); // Không cho phép tắt popup bằng cách khác
        builder.show();
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            speechRecognizer.stopListening();
        }
    }

    private boolean checkPermission() {
        // Check if permissions are granted
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECORD_AUDIO}, 2);
    }

    private void sendMessageWithVoice(String filePath) {
        // Upload the voice file to your server or cloud storage
        // Example: Upload to Firebase Storage or your custom server

        // After uploading, send a reference or URL of the voice message in the chat
        // For example:
        String voiceMessageUrl = uploadVoiceAndGetUrl(filePath);
        if (voiceMessageUrl != null) {
            sendMessageToUser(voiceMessageUrl);
        } else {
            AndroidUtil.showToast(this, "Failed to send voice message");
        }
    }

    private String uploadVoiceAndGetUrl(String filePath) {
        // Implement your logic to upload the voice file and get the URL
        // Similar to how you uploaded images
        // Example: Use Firebase Storage, or send directly to your server
        return null; // Replace with actual URL or handle the error
    }


    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            new Thread(() -> {
                String imageUrl = uploadImageAndGetUrl(imageUri);
                if (imageUrl != null) {
                    runOnUiThread(() -> sendMessageToUser(imageUrl));
                } else {
                    runOnUiThread(() -> Toast.makeText(ChatScreen.this, "Failed to upload image", Toast.LENGTH_SHORT).show());
                }
            }).start();
        }
    }

    private String uploadImageAndGetUrl(Uri imageUri) {
        try {
            byte[] imageData = getBytesFromUri(imageUri);

            OkHttpClient client = new OkHttpClient();

            // Assuming your server API accepts multipart file upload
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", UUID.randomUUID().toString(),
                            RequestBody.create(imageData, MediaType.parse("image/jpeg")))
                    .build();

            Request request = new Request.Builder()
                    .url(DefinePathApi.URL + DefinePathApi.IMAGE_UPLOAD) // Replace with your server URL
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful() && response.body() != null) {
                // Assuming your server returns the URL of the uploaded image in the response
                String url = response.body().string();
                return url;
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Or handle the error appropriately
        }
    }

    private byte[] getBytesFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private void shareLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            getCurrentLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            AndroidUtil.showToast(this, "Permission denied");
        }

        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startRecording();
            } else {
                AndroidUtil.showToast(this, "Permission denied");
            }
        }

        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupSpeechRecognizer();
            } else {
                AndroidUtil.showToast(this, "Permission denied");
            }
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            shareLocation(latitude, longitude);
                        }
                    });
        }
    }

    private void shareLocation(double latitude, double longitude) {
        String mapUrl = "https://www.google.com/maps?q=" + latitude + "," + longitude;
        // Create a URI from the latitude and longitude
        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude);
        // Create an Intent with action ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

        // Set the package to Google Maps
        mapIntent.setPackage("com.google.android.apps.maps");

        // Verify that the intent will resolve to an activity
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            // Start the Intent
            sendMessageToUser(mapUrl);
            startActivity(mapIntent);
        } else {
            AndroidUtil.showToast(this, "Google Maps app is not installed");
        }
        // Optionally, you can show a popup indicating the location has been shared
        showLocationSharedPopup();
    }


    private void showLocationSharedPopup() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_message, null);
        int width = WindowManager.LayoutParams.WRAP_CONTENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        TextView txtPopupMessage = popupView.findViewById(R.id.txtPopupMessage);
        txtPopupMessage.setText("Your location has been shared!");
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        popupView.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });
    }

    private void sendMessageToUser(String message) {

        chatroomModel.setLastMessageTimestamp(Timestamp.now());
        chatroomModel.setLastMessageSenderId(userId);
        FirebaseUtil.getChatroomReference(roomId).set(chatroomModel);

        ChatMessageModel chatMessageModel = new ChatMessageModel(message, roomId, userId, Timestamp.now());

        FirebaseUtil.getChatroomMessageReference(roomId)
                .add(chatMessageModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        txtChatMessage.setText("");
                    } else {
                        String m = "\"" + message + "\" isn't send!";
                        AndroidUtil.showToast(this, m);
                        AndroidUtil.showError("Can't send message", m);
                    }
                });
    }

    private void setupChatRecyclerView() {
        AndroidUtil.showError("Firebase", roomId);
        Query query = FirebaseUtil.getChatroomMessageReference(roomId)
                .orderBy("timestamp", Query.Direction.DESCENDING);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot document : task.getResult()) {
                    Log.d("FirestoreQuery", document.getId() + " => " + document.getData());
                }
            } else {
                Log.d("FirestoreQuery", "Error getting documents: ", task.getException());
            }
        });

        FirestoreRecyclerOptions<ChatMessageModel> options = new FirestoreRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(query, ChatMessageModel.class)
                .setLifecycleOwner(this)
                .build();

        adapter = new ChatRecyclerAdapter(options, getApplicationContext(), profiles.get(0).getUser().getId());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    private void back() {
        finish();
    }

    private void showFragmentGGMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}
