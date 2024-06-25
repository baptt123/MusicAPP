package com.example.appnghenhac.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appnghenhac.R;
//import com.example.appnghenhac.main.MainActivity;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
//import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class DangNhapActivity extends AppCompatActivity {
  private   Button btnDongYDN,btnDangKy;
  private   EditText edEmail,edMatkhau;
//  private   FirebaseAuth fAuth;
  private   ProgressBar progressBar;
  private static final String TAG = "DangNhapActivity";

//    @Override
//
//    protected void onCreate( Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login);
//        getSupportActionBar().setTitle("ĐĂNG NHẬP");
//
//        btnDangKy = (Button) findViewById(R.id.btn_dangky);
//        btnDangKy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               register();
//            }
//        });
//        btnDongYDN= (Button) findViewById(R.id.btn_dongyDN);
//        btnDongYDN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                login();
//            }
//        });
//        edEmail = (EditText) findViewById(R.id.t_dangnhap);
//        edMatkhau = (EditText) findViewById(R.id.t_matkhau);
//        progressBar = findViewById(R.id.idprogressbarDN);
////        fAuth = FirebaseAuth.getInstance();
//
//        Button buttonForgotPassword = findViewById(R.id.btn_forgot_password);
//        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(DangNhapActivity.this,"You can reset your password now!!",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(DangNhapActivity.this,ForgotPasswordActivity.class));
//            }
//        });
//
//    }

//    private void register() {
//        Intent intent = new Intent(DangNhapActivity.this, DangKyActivity.class);
//        startActivity(intent);
//    }

//    private void login() {
//        String email,pass;
//        email= edEmail.getText().toString();
//        pass = edMatkhau.getText().toString();
//        if(TextUtils.isEmpty(email)){
//            Toast.makeText(this,"Vui lòng nhập Email!!",Toast.LENGTH_SHORT).show();
//            edEmail.setError("Email is required");
//            edEmail.requestFocus();
//        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//            Toast.makeText(this,"Vui lòng nhập lại Email!!",Toast.LENGTH_SHORT).show();
//            edEmail.setError("Valid email is required");
//            edEmail.requestFocus();
//        }else if(TextUtils.isEmpty(pass)){
//            Toast.makeText(DangNhapActivity.this,"Vui lòng nhập mật khẩu",Toast.LENGTH_SHORT).show();
//            edMatkhau.setError("Password is required");
//            edMatkhau.requestFocus();
//        }else{
//            progressBar.setVisibility(View.VISIBLE);
//            loginUser(email,pass);
//        }


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

