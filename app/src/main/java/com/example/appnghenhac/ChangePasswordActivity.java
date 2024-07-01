package com.example.appnghenhac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.appnghenhac.login_register.DangNhapActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText editTextPassCurrent, editTextPassNew,editTextPassNewConfirm;
    private TextView textViewAuthenticate;
    private Button buttonChangePass,buttonAuthenticate;
    private ProgressBar progressBar;
    private String userPasswordCurrent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!= null){
            getSupportActionBar().setTitle("Change Password");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        editTextPassCurrent = findViewById(R.id.editText_change_pass_current);
        editTextPassNew = findViewById(R.id.editText_change_pass_new);
        editTextPassNewConfirm = findViewById(R.id.editText_change_pass_confirm);
        textViewAuthenticate= findViewById(R.id.textView_change_pass_authenticated);
        progressBar = findViewById(R.id.progress);
        buttonAuthenticate = findViewById(R.id.button_change_pass_authenticate);
        buttonChangePass = findViewById(R.id.button_change_new_password);

        //Disable editText for new password, confirm new password and make change password button unclickable till user is authenticate
        editTextPassNew.setEnabled(false);
        editTextPassNewConfirm.setEnabled(false);
        buttonChangePass.setEnabled(false);

        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser =auth.getCurrentUser();
        if (firebaseUser.equals("")){
            Toast.makeText(ChangePasswordActivity.this,"Something went wrong!! User's details not available",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChangePasswordActivity.this,ProfileActivity.class);
            startActivity(intent);
            finish();
        }else{
            reAuthenticateUser(firebaseUser);
        }







    }
    //ReAuthenticate User before changing password
    private void reAuthenticateUser(FirebaseUser firebaseUser) {
        buttonAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPasswordCurrent = editTextPassCurrent.getText().toString();

                if (TextUtils.isEmpty(userPasswordCurrent)){
                    Toast.makeText(ChangePasswordActivity.this, "Password is needed",Toast.LENGTH_SHORT).show();
                    editTextPassCurrent.setError("Please enter your current password to authenticate ");
                    editTextPassCurrent.requestFocus();
                }else{
                    progressBar.setVisibility(View.VISIBLE);

                    //Reauthenticate user now
                    AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), userPasswordCurrent);
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);

                                //Disable editText for CUrrent Password. Enable edittext for new password and confirm new password
                                editTextPassCurrent.setEnabled(false);
                                editTextPassNew.setEnabled(true);
                                editTextPassNewConfirm.setEnabled(true);
                                buttonChangePass.setEnabled(true);

                                //Enable change password button. Disable authenticate button
                                buttonAuthenticate.setEnabled(false);
                                buttonChangePass.setEnabled(true);

                                //Set textview to show user is authenticated/verified
                                textViewAuthenticate.setText("You are authenticated/verified." +
                                        " You can change password now!!");
                                Toast.makeText(ChangePasswordActivity.this,"Password has been verified. " +
                                        "Change password now!!",Toast.LENGTH_SHORT).show();

                                //Update color of change password button
                                buttonChangePass.setBackgroundTintList(ContextCompat.getColorStateList(ChangePasswordActivity.this,R.color.misty_rose));
                                buttonChangePass.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        changePassword(firebaseUser);
                                    }
                                });
                            }else{
                                try{
                                    throw task.getException();
                                }catch (Exception e){
                                    Toast.makeText(ChangePasswordActivity.this,e.getMessage(),Toast.LENGTH_SHORT);
                                }
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void changePassword(FirebaseUser firebaseUser) {
        String userNewPassword = editTextPassNew.getText().toString();
        String userNewPasswordConfirm = editTextPassNewConfirm.getText().toString();

        if (TextUtils.isEmpty(userNewPassword)){
            Toast.makeText(ChangePasswordActivity.this,"New Password is needed",Toast.LENGTH_SHORT).show();
            editTextPassNew.setError("Please enter your new password");
            editTextPassNew.requestFocus();
        }else if(TextUtils.isEmpty(userNewPasswordConfirm)){
            Toast.makeText(ChangePasswordActivity.this,"Please confirm your new password",Toast.LENGTH_SHORT).show();
            editTextPassNewConfirm.setError("Please re-enter your new password");
            editTextPassNewConfirm.requestFocus();
        }else if(!userNewPassword.matches(userNewPasswordConfirm)){
            Toast.makeText(ChangePasswordActivity.this,"Password did not match",Toast.LENGTH_SHORT).show();
            editTextPassNewConfirm.setError("Please re-enter same password");
            editTextPassNewConfirm.requestFocus();
        }else if(userPasswordCurrent.matches(userNewPassword)){
            Toast.makeText(ChangePasswordActivity.this,"New Password cannot be same as old password",Toast.LENGTH_SHORT).show();
            editTextPassNew.setError("Please enter a new password");
            editTextPassNew.requestFocus();
        }else{
            progressBar.setVisibility(View.VISIBLE);
            firebaseUser.updatePassword(userNewPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ChangePasswordActivity.this,"Password has been changed",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangePasswordActivity.this,ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        try{
                            throw task.getException();
                        }catch (Exception e){
                            Toast.makeText(ChangePasswordActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
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
            Intent intent = new Intent(ChangePasswordActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
        /*}else if(id == R.id.menu_update_email){
            Intent intent = new Intent(ProfileActivity.this, UpdateEmailActivity.class);
            startActivity(intent);*/
        }else if(id == R.id.menu_change_password){
            Intent intent = new Intent(ChangePasswordActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
        }else if(id == R.id.menu_settings){
           Toast.makeText(ChangePasswordActivity.this,"menu_setting",Toast.LENGTH_SHORT).show();
        }else if(id == R.id.menu_logout){
            auth.signOut();
            Toast.makeText(ChangePasswordActivity.this,"Logged Out",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ChangePasswordActivity.this, DangNhapActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(ChangePasswordActivity.this,"Something went wrong!!",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }
}