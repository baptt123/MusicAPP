package com.example.appnghenhac.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnghenhac.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;

public class UploadFileActivity extends AppCompatActivity {
    private Uri uri_image;
    private EditText name_song;
    private EditText name_artist;
    //    private EditText link_file_song;
    private ImageView image_song;
    private Button btn_add_image;
    private Button btn_add_file_song;
    private Button btn_send_file_song_to_firebase;
    private int PICK_FILE_REQUEST_CODE = 1;
    private int PICK_IMAGE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_activity);
        initView();
        FirebaseApp.initializeApp(this);
    }

    public void initView() {
        btn_add_image = findViewById(R.id.btn_add_image);
        btn_add_file_song = findViewById(R.id.btn_add_file_song);
        btn_add_image.setOnClickListener(v -> {
            openImagePicker();
        });
        btn_send_file_song_to_firebase = findViewById(R.id.btn_send_file_song_to_firebase);
        btn_send_file_song_to_firebase.setOnClickListener(v -> {
            openFilePicker();
        });
    }

    //hàm này dùng để gửi định dạng file mp3 lên firebase
    public void sendSongToFirebase(Uri uri,Uri image) throws FileNotFoundException {
        // TODO: Send song to Firebase
        name_song = findViewById(R.id.name_song);
        name_artist = findViewById(R.id.name_artist);
//        link_file_song = findViewById(R.id.link_file_song);
        image_song = findViewById(R.id.image_song);
        String name = name_song.getText().toString();
        String artist = name_artist.getText().toString();
        Drawable drawable = image_song.getDrawable();

        // TODO: Save song to Firebase
        if (!name.isEmpty()) {
            if (!artist.isEmpty()) {
                if (drawable != null) {
                    //tham chiếu đến file nhạc
                    FirebaseStorage firebaseStorage_song = FirebaseStorage.getInstance();
                    StorageReference storageReference = firebaseStorage_song.getReference();
                    //tham chiếu đến file ảnh
                    FirebaseStorage firebaseStorage_image = FirebaseStorage.getInstance();
                    StorageReference storageReference_image = firebaseStorage_image.getReference();
                    // Tạo một tên duy nhất cho tệp
                    String fileNamesong = name;
                    // Tạo tham chiếu tới vị trí bạn muốn lưu trữ tệp trong Firebase Storage
                    StorageReference fileReference = storageReference.child("uploads/" + "files/" + fileNamesong);
                    StorageReference imageReference = storageReference_image.child("uploads/" + "pictures/" + fileNamesong + "_image");
                    // Upload file
                    //truyền uri vào để đưa lên firebase
                    UploadTask uploadTask_file_song = fileReference.putFile(uri);
                    uploadTask_file_song.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(UploadFileActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            Toast.makeText(UploadFileActivity.this, "Upload success", Toast.LENGTH_SHORT).show();
                        }
                    });
                    UploadTask uploadTask_image = imageReference.putFile(uri_image);
                    uploadTask_image.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            Toast.makeText(UploadFileActivity.this, "Upload image failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            Toast.makeText(UploadFileActivity.this, "Upload image success", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }

    public void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
    }

    public void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    public Uri getFileURI(Uri uri) {
        return uri;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                // Handle the selected file URI
                Uri fileUri = data.getData();
                Uri uri = getFileURI(fileUri);
                try {
                    sendSongToFirebase(uri,uri_image);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                // Handle the selected image URI
                uri_image = data.getData();
                image_song = findViewById(R.id.image_song);
                image_song.setImageURI(uri_image);
            } else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No file or image selected", Toast.LENGTH_SHORT).show();
        }
    }
}





