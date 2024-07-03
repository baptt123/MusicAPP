package com.example.appnghenhac;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnghenhac.activity.HomeActivity;
import com.example.appnghenhac.login_register.DangNhapActivity;
import com.example.appnghenhac.login_register.ReadWriteUserDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    private TextView textViewWelcome,textViewFullName,textViewEmail,textViewBirthdate,textViewGender,textViewPhoneNum;
    private ProgressBar progressBar;
    private String fullname,email,birthdate,gender,phone;
    private ImageView imageView;
    private FirebaseAuth authProfile;

    public ProfileActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);
        textViewWelcome = findViewById(R.id.textview_show_welcome);
        textViewFullName = findViewById(R.id.textview_show_full_name);
        textViewEmail = findViewById(R.id.textview_show_email);
        textViewBirthdate = findViewById(R.id.textview_show_birthdate);
        textViewGender = findViewById(R.id.textview_show_gender);
        textViewPhoneNum = findViewById(R.id.textview_show_phone);
        progressBar = findViewById(R.id.progress);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        imageView = findViewById(R.id.image_profile);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, UploadProfilePicActivity.class);
            startActivity(intent);
        });

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        if(firebaseUser == null){
            Toast.makeText(ProfileActivity.this, "Something went wrong!!! User's details are not available at the moment", Toast.LENGTH_SHORT).show();
        }else{
            checkIfEmailVerified(firebaseUser);
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
            
        }



    }
    // User's coming to ProfileActivity after successfully registration
    private void checkIfEmailVerified(FirebaseUser firebaseUser) {
        if (!firebaseUser.isEmailVerified()){
            showArletDialog();
        }
    }
    private void showArletDialog() {

        //Setup the Alert Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Email chưa được xác minh");
            builder.setMessage("Hãy xác minh email của bạn ngay bây giờ. Bạn không thể đăng nhập nếu không xác minh email vào lần tiếp theo.");

        //Open Email Apps if user clicks/taps continue button
        builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        //Create the AlerDialog
        AlertDialog alertDialog  = builder.create();

        //Show the AlerDialog
        alertDialog.show();

    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Register User");
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if(readUserDetails != null ){
                    fullname = firebaseUser.getDisplayName();
                    email = firebaseUser.getEmail();
                    birthdate = readUserDetails.birthDate;
                    gender = readUserDetails.gender;
                    phone = readUserDetails.phoneNumber;

                    textViewWelcome.setText("Xin chào, "+fullname);
                    textViewFullName.setText(fullname);
                    textViewEmail.setText(email);
                    textViewBirthdate.setText(birthdate);
                    textViewGender.setText(gender);
                    textViewPhoneNum.setText(phone);


                    //SET User (After user has uploaded)
                    Uri uri = firebaseUser.getPhotoUrl();
                    //ImageView setImageUri() should not be used with regular Uris .So we are using Picasso
                    Picasso.get().load(uri).into(imageView);
                }else {
                    Toast.makeText(ProfileActivity.this,"Đã xảy ra lỗi!!!",Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this,"Đã xảy ra lỗi!!!",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);


            }
        });

    }

    //Create ActionBar Menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_profile,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.memu_refresh){
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        }else if(id == R.id.menu_update_profile ){
            Intent intent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.menu_change_password){
            Intent intent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.menu_logout){
            authProfile.signOut();
            Toast.makeText(ProfileActivity.this,"Logged Out",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, DangNhapActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(ProfileActivity.this,"Đã xảy ra lỗi!!",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }
}
