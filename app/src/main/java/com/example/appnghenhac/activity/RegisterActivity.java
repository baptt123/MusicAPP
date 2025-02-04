package com.example.appnghenhac.activity;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.UnstableApi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.appnghenhac.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextFullName, editTextEmail, editTextPassword, editTextConfirmPassword, editTextPhoneNum, editTextDate;
    private ProgressBar progressBar;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonSelectGender;
    private DatePickerDialog picker;

    private static final String TAG = "RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
//        getSupportActionBar().setTitle("ĐĂNG KÝ");
        Toast.makeText(RegisterActivity.this, "Bạn có thể đăng ký ngay bây giờ!!!", Toast.LENGTH_SHORT).show();

        progressBar = findViewById(R.id.idprogressbar);
        editTextFullName = findViewById(R.id.idFullName);
        editTextEmail = findViewById(R.id.idEmail);
        editTextPassword = findViewById(R.id.idPassword);
        editTextConfirmPassword = findViewById(R.id.idConfirmPassword);
        editTextDate = findViewById(R.id.idBirthDate);
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //Date Picker Dialog
                picker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextDate.setText(dayOfMonth + "/" + (month +1) + "/"+year);

                    }
                }, year, month, day);
                picker.show();
            }
        });

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
                editTextDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                String textPhoneNum = editTextPhoneNum.getText().toString();
                String textGender;

                //validate number phone
                String phoneRegex = "[0-9][0-9][0-9]";
                Matcher phoneMatcher;
                Pattern phonePatterns = Pattern.compile(phoneRegex);
                phoneMatcher = phonePatterns.matcher(textPhoneNum);


                if (TextUtils.isEmpty(textFullName)) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ Họ và tên", Toast.LENGTH_SHORT).show();
                    editTextFullName.setError("Họ và Tên là bắt buộc!!!");
                    editTextFullName.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Email là bắt buộc!!!");
                    editTextEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập lại Email", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Cần có email hợp lệ!!!");
                    editTextEmail.requestFocus();
                } else if (TextUtils.isEmpty(textBirthDate)) {
                    Toast.makeText(RegisterActivity.this, "Xin vui lòng ngày sinh của bạn", Toast.LENGTH_SHORT).show();
                    editTextDate.setError("Ngày sinh là bắt buộc");
                    editTextDate.requestFocus();
                } else if (radioGroupGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng chọn giới tính của bạn", Toast.LENGTH_SHORT).show();
                    radioButtonSelectGender.setError("Giới tính là bắt buộc");
                    radioButtonSelectGender.requestFocus();
                } else if (TextUtils.isEmpty(textPhoneNum)) {
                    Toast.makeText(RegisterActivity.this, "Xin vui lòng điền số điện thoại của bạn", Toast.LENGTH_SHORT).show();
                    editTextPhoneNum.setError("Số điện thoại là bắt buộc");
                    editTextPhoneNum.requestFocus();
                } else if (textPhoneNum.length() != 10) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập lại số điện thoại của bạn", Toast.LENGTH_SHORT).show();
                    editTextPhoneNum.setError("Số điện thoại phải có 10 chữ số");
                    editTextPhoneNum.requestFocus();
                } else if (!phoneMatcher.find()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập lại số điện thoại của bạn", Toast.LENGTH_SHORT).show();
                    editTextPhoneNum.setError("Số điện thoại không hợp lệ");
                    editTextPhoneNum.requestFocus();

                } else if (TextUtils.isEmpty(textPass)) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập mật khẩu của bạn", Toast.LENGTH_SHORT).show();
                    editTextPassword.setError("Cần có mật khẩu");
                    editTextPassword.requestFocus();
                } else if (textPass.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu phải có ít nhất 6 chữ số", Toast.LENGTH_SHORT).show();
                    editTextPassword.setError("Mật khẩu quá yếu");
                    editTextPassword.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPass)) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng xác nhận mật khẩu của bạn", Toast.LENGTH_SHORT).show();
                    editTextConfirmPassword.setError("Xác nhận mật khẩu là bắt buộc");
                    editTextConfirmPassword.requestFocus();
                } else if (!textPass.equals(textConfirmPass)) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập cùng một mật khẩu", Toast.LENGTH_SHORT).show();
                    editTextConfirmPassword.setError("Cần phải xác nhận mật khẩu");
                    editTextConfirmPassword.requestFocus();

                    //clear the entered password
                    editTextPassword.clearComposingText();
                    editTextConfirmPassword.clearComposingText();
                } else {
                    textGender = radioButtonSelectGender.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textFullName, textEmail, textPass, textGender, textBirthDate, textPhoneNum);
                }
            }
        });


    }

    private void registerUser(String textFullName, String textEmail, String textPass, String textGender, String textBirthDate, String textPhoneNum) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        //Create user profile
        auth.createUserWithEmailAndPassword(textEmail, textPass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @OptIn(markerClass = UnstableApi.class)
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký người dùng thành công", Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    //Enter USer data into the Firebase Realtime Database
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textGender, textBirthDate, textPhoneNum);

                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Register User");

                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(RegisterActivity.this, "Người dùng đã đăng ký thành công. Vui lòng xác minh email của bạn", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(RegisterActivity.this, ProfileActivity.class);
                                //To prevent user from returning back to Register Activity on pressing back button after registation
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish(); //close RegisterActivity

                            } else {
                                Toast.makeText(RegisterActivity.this, "Người dùng đã đăng ký thành công. Vui lòng xác minh email của bạn", Toast.LENGTH_SHORT).show();

                            }
                            //Hide ProgressBar whether User creation is successful or failed
                            progressBar.setVisibility(View.GONE);

                        }
                    });
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        editTextPassword.setError("Mật khẩu của bạn quá yếu");
                        editTextPassword.requestFocus();

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        editTextPassword.setError("Email của bạn không hợp lệ hoặc đã được sử dụng.");
                        editTextPassword.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e) {
                        editTextPassword.setError("Người dùng đã được đăng ký với email này. Sử dụng một email khác");
                        editTextPassword.requestFocus();

                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    //Hide ProgressBar whether User creation is successful or failed
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

    }


}
