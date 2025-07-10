package com.example.appnghenhac.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.appnghenhac.R;
import com.example.appnghenhac.model.ReadWriteUserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText editTextUpdateName,editTextUpdateBirthDate,editTextUpdatePhone;
    private RadioGroup radioGroupUpdateGender;
    private RadioButton radioButtonUpdateGenderSelected;
    private String textFullName,textBirthDate,textGender,textPhone;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    public UpdateProfileActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Update Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressBar = findViewById(R.id.progress);
        editTextUpdateName = findViewById(R.id.editText_update_profile_name);
        editTextUpdateBirthDate = findViewById(R.id.editText_update_profile_birthdate);
        editTextUpdatePhone = findViewById(R.id.editText_update_profile_phone);
        radioGroupUpdateGender = findViewById(R.id.radio_group_update_gender);



        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();

        //Show dữ liệu Profile
        showProfile(firebaseUser);

        //Upload Profile Pic
        Button buttonUploadProfilePic = findViewById(R.id.button_upload_profile_pic);
        buttonUploadProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProfileActivity.this, UploadProfilePicActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //Setting up Datepicker on Edittext
        editTextUpdateBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Extracting saved dd,m,yyyy into different variable by creating an array   delimited by"/"
                String textBD[] =textBirthDate.split("/");

                int day = Integer.parseInt(textBD[0]);
                int month = Integer.parseInt(textBD[1])-1; // to take care of month index staring from 0
                int year = Integer.parseInt(textBD[2]);

                DatePickerDialog picker;
                //Date Picker Dialog
                picker = new DatePickerDialog(UpdateProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextUpdateBirthDate.setText(dayOfMonth + "/" + (month +1) + "/"+year);

                    }
                }, year, month, day);
                picker.show();
            }
        });

        //Update Profile
        Button buttonUpdateProfile = findViewById(R.id.button_upload_profile);
        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(firebaseUser);
            }
        });
    }
    private  void updateProfile(FirebaseUser firebaseUser){
        int selectedGenderID = radioGroupUpdateGender.getCheckedRadioButtonId();
        radioButtonUpdateGenderSelected = findViewById(selectedGenderID);
        String phoneRegex = "[0-9][0-9][0-9]";
        Matcher phoneMatcher;
        Pattern phonePatterns = Pattern.compile(phoneRegex);
        phoneMatcher = phonePatterns.matcher(textPhone);

        if(TextUtils.isEmpty(textFullName)){
            Toast.makeText(UpdateProfileActivity.this,"Vui lòng nhập đầy đủ Họ và tên",Toast.LENGTH_SHORT).show();
            editTextUpdateName.setError("Họ và Tên là bắt buộc!!!");
            editTextUpdateName.requestFocus();
        }else if(TextUtils.isEmpty(textBirthDate)){
            Toast.makeText(UpdateProfileActivity.this,"Vui lòng chọn ngày sinh của bạn",Toast.LENGTH_SHORT).show();
            editTextUpdateBirthDate.setError("Ngày sinh là bắt buộc");
            editTextUpdateBirthDate.requestFocus();
        }else if(TextUtils.isEmpty(radioButtonUpdateGenderSelected.getText())){
            Toast.makeText(UpdateProfileActivity.this,"Vui lòng chọn giới tính của bạn",Toast.LENGTH_SHORT).show();
            radioButtonUpdateGenderSelected.setError("Giới tính là bắt buộc");
            radioButtonUpdateGenderSelected.requestFocus();
        }else if(TextUtils.isEmpty(textPhone)){
            Toast.makeText(UpdateProfileActivity.this,"Vui lòng điền số điện thoại của bạn",Toast.LENGTH_SHORT).show();
            editTextUpdatePhone.setError("Số điện thoại là bắt buộc");
            editTextUpdatePhone.requestFocus();
        }else if(textPhone.length()!= 10) {
            Toast.makeText(UpdateProfileActivity.this, "Vui lòng nhập lại số điện thoại của bạn", Toast.LENGTH_SHORT).show();
            editTextUpdatePhone.setError("Số điện thoại phải có 10 chữ số");
            editTextUpdatePhone.requestFocus();
        }else if(!phoneMatcher.find()) {
            Toast.makeText(UpdateProfileActivity.this, "Vui lòng nhập lại số điện thoại của bạn", Toast.LENGTH_SHORT).show();
            editTextUpdatePhone.setError("Số điện thoại không hợp lệ");
            editTextUpdatePhone.requestFocus();
        }else  {
            textGender = radioButtonUpdateGenderSelected.getText().toString();
            textFullName = editTextUpdateName.getText().toString();
            textBirthDate = editTextUpdateBirthDate.getText().toString();
            textPhone = editTextUpdatePhone.getText().toString();


            //Enter User Data into the Firebase Realtime Database.Set up dependencies;
            ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textGender,textBirthDate,textPhone);

            //Extract User reference from Database for "Register User"
            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Register User");
            String userId= firebaseUser.getUid();
            progressBar.setVisibility(View.VISIBLE);
            referenceProfile.child(userId).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        //Settings new display name
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                        firebaseUser.updateProfile(profileUpdate);
                        Toast.makeText(UpdateProfileActivity.this,"Cập nhật thành công!!",Toast.LENGTH_SHORT).show();


                        //Stop user from returning to UpdateProfileActivity on pressing back button and close activity
                        Intent intent = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }else{
                        try{
                            throw task.getException();
                        }catch (Exception e){
                            Toast.makeText(UpdateProfileActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }


    private void showProfile(FirebaseUser firebaseUser) {
        String userRegistered = firebaseUser.getUid();

        //Extracting User Reference from Database for "Registered User"
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Register User");
        progressBar.setVisibility(View.VISIBLE);
        referenceProfile.child(userRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);
                if(readUserDetails != null){
                    textFullName = firebaseUser.getDisplayName();
                    textGender = readUserDetails.gender;
                    textBirthDate = readUserDetails.birthDate;
                    textPhone = readUserDetails.phoneNumber;

                    editTextUpdateName.setText(textFullName);
                    editTextUpdateBirthDate.setText(textBirthDate);
                    editTextUpdatePhone.setText(textPhone);


                    //Show gender
                    if(textGender.equals("Nữ")){
                        radioButtonUpdateGenderSelected = findViewById(R.id.radio_female);
                    }else{
                        radioButtonUpdateGenderSelected = findViewById(R.id.radio_male);
                    }
                    radioButtonUpdateGenderSelected.setChecked(true);
                }else {
                    Toast.makeText(UpdateProfileActivity.this,"Something went wrong!!!!",Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfileActivity.this,"Đã xảy ra lỗi!!!!",Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    public boolean onCreateOptionMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_profile,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if(id == R.id.memu_refresh){
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        }else if(id == R.id.menu_update_profile ){
            Intent intent = new Intent(UpdateProfileActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        /*}else if(id == R.id.menu_update_email){
            Intent intent = new Intent(ProfileActivity.this, UpdateEmailActivity.class);
            startActivity(intent);*/
        }else if(id == R.id.menu_change_password){
            Intent intent = new Intent(UpdateProfileActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.menu_logout){
            auth.signOut();
            Toast.makeText(UpdateProfileActivity.this,"Logged Out",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UpdateProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(UpdateProfileActivity.this,"Đã xảy ra lỗi!!",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }
}