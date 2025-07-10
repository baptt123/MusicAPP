package com.example.appnghenhac.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnghenhac.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
  private   Button btnDongYDN;
  private   EditText edEmail,edMatkhau;
  private   FirebaseAuth fAuth;
  private   ProgressBar progressBar;
  private static final String TAG = "DangNhapActivity";

    @Override

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        TextView textViewRegister = findViewById(R.id.textView_link_register);
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               register();
            }
        });
        btnDongYDN= (Button) findViewById(R.id.button_login);
        btnDongYDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        edEmail = (EditText) findViewById(R.id.editText_login_email);
        edMatkhau = (EditText) findViewById(R.id.editText_login_password);
        progressBar = findViewById(R.id.progress);

        //SHow/hide password
        ImageView imageViewShowHidePass = findViewById(R.id.image_show_hide_pass);
        imageViewShowHidePass.setImageResource(R.drawable.visibility_off_24dp_fill0_wght400_grad0_opsz24);
        imageViewShowHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edMatkhau.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //if password is visible then Hide it
                    edMatkhau.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //chang icon
                    imageViewShowHidePass.setImageResource(R.drawable.visibility_off_24dp_fill0_wght400_grad0_opsz24);
                } else {
                    edMatkhau.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePass.setImageResource(R.drawable.visibility_24dp_fill0_wght400_grad0_opsz24);
                }
            }
        });

        fAuth = FirebaseAuth.getInstance();

        TextView textViewForgotPassword = findViewById(R.id.textView_link_forgot_password);
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"Bạn có thể đặt lại mật khẩu ngay bây giờ!!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            }
        });

    }

    private void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void login() {
        String email,pass;
        email= edEmail.getText().toString();
        pass = edMatkhau.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Vui lòng nhập Email!!",Toast.LENGTH_SHORT).show();
            edEmail.setError("Email phải cần thiết");
            edEmail.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Vui lòng nhập lại Email!!",Toast.LENGTH_SHORT).show();
            edEmail.setError("Cần có email hợp lệ");
            edEmail.requestFocus();
        }else if(TextUtils.isEmpty(pass)){
            Toast.makeText(LoginActivity.this,"Vui lòng nhập mật khẩu",Toast.LENGTH_SHORT).show();
            edMatkhau.setError("Cần có mật khẩu");
            edMatkhau.requestFocus();
        }else{
            progressBar.setVisibility(View.VISIBLE);
            loginUser(email,pass);
        }


    }

    private void loginUser(String email, String pass) {
        fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                    //Get intance  of the current User
                    FirebaseUser firebaseUser= fAuth.getCurrentUser();

                    //Check if email is verified before user can access their profile
                    if (firebaseUser.isEmailVerified()){
                        Toast.makeText(LoginActivity.this,"Bạn đã đăng nhập thành công",Toast.LENGTH_SHORT).show();

                        //Open user profile

                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                        finish();
                    }else{
                        firebaseUser.sendEmailVerification();
                        fAuth.signOut();
                        showArletDialog();
                    }

                }else {
                    try{
                        throw task.getException();

                    }catch (FirebaseAuthInvalidUserException e){
                        edEmail.setError("Người dùng không tồn tại hoặc không còn hợp lệ. Vui lòng đăng ký lại.");
                        edEmail.requestFocus();

                    }catch (FirebaseAuthInvalidCredentialsException e){
                        edEmail.setError("Thông tin không hợp lệ. Vui lòng kiểm tra và nhập lại");
                        edEmail.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(LoginActivity.this,"Đã xảy ra lỗi!",Toast.LENGTH_SHORT).show();

                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    private void showArletDialog() {

        //Setup the Alert Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Email chưa được xác minh");
        builder.setMessage("Hãy xác minh email của bạn ngay bây giờ. Bạn không thể đăng nhập nếu không xác minh email.");

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


    @Override
    protected void onStart() {
        super.onStart();
        if(fAuth.getCurrentUser() != null){
            Toast.makeText(LoginActivity.this,"Đã đăng nhập!!",Toast.LENGTH_SHORT).show();

            startActivity(new Intent(LoginActivity.this, HomeActivity.class));


            finish();
        }else {
            Toast.makeText(LoginActivity.this,"Bạn có thể đăng nhập ngay bây giờ!!",Toast.LENGTH_SHORT).show();
        }
    }
}

//    private void loginUser(String email, String pass) {
//        fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(DangNhapActivity.this,new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(DangNhapActivity.this,"You are logged in now",Toast.LENGTH_SHORT).show();
//
//                }else {
//                    try{
//                        throw task.getException();
//
//                    }catch (FirebaseAuthInvalidUserException e){
//                        edEmail.setError("User does not exists or is no longer valid. Please register again.");
//                        edEmail.requestFocus();
//
//                    }catch (FirebaseAuthInvalidCredentialsException e){
//                        edEmail.setError("Invalid credentials");
//                        edEmail.requestFocus();
//                    }catch (Exception e){
//                        Log.e(TAG,e.getMessage());
//                        Toast.makeText(DangNhapActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
//                    Toast.makeText(DangNhapActivity.this,"Something went wrong!",Toast.LENGTH_SHORT).show();
//
//                }
//                progressBar.setVisibility(View.GONE);
//            }
//        });
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(fAuth.getCurrentUser() != null){
//            Toast.makeText(DangNhapActivity.this,"Already logged in!!",Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(DangNhapActivity.this, MainActivity.class));

//            finish();
//        }else {
//            Toast.makeText(DangNhapActivity.this,"You can login now!!",Toast.LENGTH_SHORT).show();
//        }
//    }

