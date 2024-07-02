package com.example.appnghenhac.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserService {
    private final FirebaseDatabase data;
    private DatabaseReference reference;
    private String userId;
    public static UserService userService = new UserService();
    public UserService(){
        this.userId = loadUserid();
        data = FirebaseDatabase.getInstance();
        reference = data.getReference();
    }

    public static UserService getInstance() {
        return userService;
    }

    String loadUserid(){
        String res = "";
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null){
            res = firebaseUser.getUid();
        }
        return res;
    }
    public String getUserId(){
        return userId;
    }

}
