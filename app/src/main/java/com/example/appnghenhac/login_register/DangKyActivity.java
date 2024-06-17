package com.example.appnghenhac.login_register;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.UnstableApi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.appnghenhac.R;
import com.example.appnghenhac.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthActionCodeException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DangKyActivity extends AppCompatActivity {
   private EditText editTextFullName, editTextEmail,editTextPassword,editTextConfirmPassword,editTextPhoneNum,editTextDate;
   private ProgressBar progressBar;
   private RadioGroup radioGroupGender;
   private RadioButton radioButtonSelectGender;

   private static final String TAG ="DangKyActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
//        getSupportActionBar().setTitle("ĐĂNG KÝ");
        Toast.makeText(DangKyActivity.this,"Bạn có thể đăng ký ngay bây giờ!!!",Toast.LENGTH_SHORT).show();

        progressBar = findViewById(R.id.idprogressbar);
        editTextFullName =findViewById(R.id.idFullName);
        editTextEmail = findViewById(R.id.idEmail);
        editTextPassword = findViewById(R.id.idPassword);
        editTextConfirmPassword = findViewById(R.id.idConfirmPassword);
        editTextDate = findViewById(R.id.idBirthDate);
        editTextPhoneNum = findViewById(R.id.idPhone);

        //RadioButton cho gender
        radioGroupGender = findViewById(R.id.idRGender);
        radioGroupGender.clearCheck();

        Button btnDangky = findViewById(R.id.btnDK);
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectGenderId = radioGroupGender.getCheckedRadioButtonId();
                radioButtonSelectGender = findViewById(selectGenderId);

                String textFullName = editTextFullName.getText().toString();
                String textEmail = editTextEmail.getText().toString();
                String textPass = editTextPassword.getText().toString();
                String textConfirmPass = editTextConfirmPassword.getText().toString();
                String textBirthDate = editTextDate.getText().toString();
                String textPhoneNum = editTextPhoneNum.getText().toString();
                String textGender;

                //validate number phone
                String phoneRegex = "[0-9][0-9][0-9]";
                Matcher phoneMatcher;
                Pattern phonePatterns = Pattern.compile(phoneRegex);
                phoneMatcher = phonePatterns.matcher(textPhoneNum);


                if(TextUtils.isEmpty(textFullName)){
                    Toast.makeText(DangKyActivity.this,"Vui lòng nhập đầy đủ Họ và tên",Toast.LENGTH_SHORT).show();
                    editTextFullName.setError("Họ và Tên là bắt buộc!!!");
                    editTextFullName.requestFocus();
                }else  if(TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(DangKyActivity.this, "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Email là bắt buộc!!!");
                    editTextEmail.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(DangKyActivity.this, "Vui lòng nhập lại Email", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Valid email is required!!!");
                    editTextEmail.requestFocus();
                }else if(TextUtils.isEmpty(textBirthDate)){
                    Toast.makeText(DangKyActivity.this,"Please your date of birth",Toast.LENGTH_SHORT).show();
                    editTextDate.setError("Date of Birth is required");
                    editTextDate.requestFocus();
                }else if(radioGroupGender.getCheckedRadioButtonId()==-1){
                    Toast.makeText(DangKyActivity.this,"Please select your gender",Toast.LENGTH_SHORT).show();
                    radioButtonSelectGender.setError("Gender is required");
                    radioButtonSelectGender.requestFocus();
                }else if(TextUtils.isEmpty(textPhoneNum)){
                    Toast.makeText(DangKyActivity.this,"Please enter your phone number",Toast.LENGTH_SHORT).show();
                    editTextPhoneNum.setError("Phone Number is required");
                    editTextPhoneNum.requestFocus();
                }else if(textPhoneNum.length()!= 10) {
                    Toast.makeText(DangKyActivity.this, "Please re-enter your phone number", Toast.LENGTH_SHORT).show();
                    editTextPhoneNum.setError("Number phone should be 10 digits");
                    editTextPhoneNum.requestFocus();
                }else if(!phoneMatcher.find()){
                    Toast.makeText(DangKyActivity.this, "Please re-enter your phone number", Toast.LENGTH_SHORT).show();
                    editTextPhoneNum.setError("Number phone no. is not valid");
                    editTextPhoneNum.requestFocus();

                }else if(TextUtils.isEmpty(textPass)){
                    Toast.makeText(DangKyActivity.this,"Please enter your password",Toast.LENGTH_SHORT).show();
                    editTextPassword.setError("Password is required");
                    editTextPassword.requestFocus();
                }else if(textPass.length() < 6){
                    Toast.makeText(DangKyActivity.this,"Password should be at least 6 digits",Toast.LENGTH_SHORT).show();
                    editTextPassword.setError("Password too weak");
                    editTextPassword.requestFocus();
                }else if(TextUtils.isEmpty(textConfirmPass)){
                    Toast.makeText(DangKyActivity.this,"Please confirm your password",Toast.LENGTH_SHORT).show();
                    editTextConfirmPassword.setError("Password Confirm is required");
                    editTextConfirmPassword.requestFocus();
                }else if (!textPass.equals(textConfirmPass)){
                    Toast.makeText(DangKyActivity.this,"Please the same password",Toast.LENGTH_SHORT).show();
                    editTextConfirmPassword.setError("Password confirm is required");
                    editTextConfirmPassword.requestFocus();

                    //clear the entered password
                    editTextPassword.clearComposingText();
                    editTextConfirmPassword.clearComposingText();
                }else{
                    textGender = radioButtonSelectGender.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textFullName,textEmail,textPass,textGender,textBirthDate,textPhoneNum);
                }
            }
        });






   }

    private void registerUser(String textFullName, String textEmail, String textPass, String textGender, String textBirthDate, String textPhoneNum) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        //Create user profile
        auth.createUserWithEmailAndPassword(textEmail,textPass).addOnCompleteListener(DangKyActivity.this, new OnCompleteListener<AuthResult>() {
            @OptIn(markerClass = UnstableApi.class) @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser firebaseUser =  auth.getCurrentUser();
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                    firebaseUser.updateProfile(profileChangeRequest);
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textGender,textBirthDate,textPhoneNum);

                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("User");

                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(DangKyActivity.this, "User registered successfully. Please verify your email", Toast.LENGTH_SHORT).show();

                               Intent intent = new Intent(DangKyActivity.this, DangNhapActivity.class);
                                //To prevent user from returning back to Register Activity on pressing back button after registation
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish(); //close DangKyACtivity

                            } else {
                                Toast.makeText(DangKyActivity.this, "User registered successfully. Please verify your email", Toast.LENGTH_SHORT).show();

                            }
                            //Hide ProgressBar whether User creation is successful or failed
                            progressBar.setVisibility(View.GONE);

                        }});
                }else {
                                try{
                                    throw task.getException();
                                }catch (FirebaseAuthWeakPasswordException e){
                                    editTextPassword.setError("Your password is to weak");
                                    editTextPassword.requestFocus();

                                }catch (FirebaseAuthInvalidCredentialsException e){
                                    editTextPassword.setError("Your email is invalid or already in use.");
                                    editTextPassword.requestFocus();
                                }catch (FirebaseAuthUserCollisionException e){
                                    editTextPassword.setError("User is already registered with this email. Use another email");
                                    editTextPassword.requestFocus();

                                }catch (Exception e){
                                    Log.e(TAG,e.getMessage());
                                    Toast.makeText(DangKyActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                                //Hide ProgressBar whether User creation is successful or failed
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });

                }}
