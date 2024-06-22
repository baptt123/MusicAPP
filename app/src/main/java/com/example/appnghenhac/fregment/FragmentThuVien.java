package com.example.appnghenhac.fregment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appnghenhac.R;
import com.example.appnghenhac.asynctask.musicService;
import com.example.appnghenhac.model.Song;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentThuVien#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentThuVien extends Fragment {
    private FirebaseDatabase data ;
    private DatabaseReference reference ;

    private String root = "user";
    private String leaf = "";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FragmentThuVien() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentThuVien.
     */
    public static FragmentThuVien newInstance(String param1, String param2) {
        FragmentThuVien fragment = new FragmentThuVien();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thu_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Play list
        LinearLayout ln =  view.findViewById(R.id.layoutPlayList);
        setNumberPlay();
        ImageButton imgbPlayList = view.findViewById(R.id.imgbPlayList);

        new musicService(this).execute("1J3SmWwlYAG68LGKr86MVH");


        ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), PlayListActivity.class);
//                startActivity(intent);
            }
        });

        imgbPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO chức năng thêm play list
                TextView tv = view.findViewById(R.id.tvPlayList);
                tv.setText(tv.getText()+"+ 1");
            }
        });

        String userName = getUserName(savedInstanceState);
        leaf = userName;
        data = FirebaseDatabase.getInstance();
        reference = data.getReference();
//        // ghi du lieu
//        Map<String, String> pl = new ArrayMap<>();
//        pl.put("pl001", "s001");
//        pl.put("pl002", "s001,s002");
//
//        User u = new User("tam2", new Date("14/04/2003"), "", "013131313", pl);
//        reference.child("user").child(u.getFullName()).setValue(u);

        // Đọc dữ liệu từ Firebase
//        reference.child(root).child(leaf).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    user user = dataSnapshot.getValue(com.example.appnghenhac.model.user.class);
////                    String user = snapshot.getKey();
//                    Log.d("home", user.toString());
//                }
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }

    private String getUserName(Bundle bundle) {
//        TODO lay user name cua login
        return "tam2";
    }


    private void setNumberPlay() {
//        TODO thiết lập số lượng play list
    }

    public void getSong(Song res) {
//        TODO do no thing
    }
}