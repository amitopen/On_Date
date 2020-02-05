package com.live.date.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.live.date.Adapter.UserAdapter;
import com.live.date.Model.Chat;
import com.live.date.Model.User;
import com.live.date.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ChatsFragment extends Fragment {


    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;
     FirebaseUser fuser;
     DatabaseReference reference;
     private List<String> usersList;

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView= view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               usersList.clear();
               for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                   Chat chat =snapshot.getValue(Chat.class);

                   assert chat != null;
                   if (chat.getSender().equals(fuser.getUid())){
                       usersList.add(chat.getReceiver());
                   }
                   if (chat.getReceiver().equals(fuser.getUid())){
                       usersList.add(chat.getSender());
                   }
               }
                Set<String> hashSet = new HashSet<>(usersList);
                usersList.clear();
                usersList.addAll(hashSet);
               readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
        private void readChats(){

        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //mUsers.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);

                    //Display 1 user from chats
                    for (String id : usersList){
                        assert user != null;
                        if (user.getId().equals(id)){
                           // if (mUsers.size() != 0){
                                mUsers.add(user);
//                                for (User user1 : mUsers){
//                                    if (!user.getId().equals(user1.getId())){
//                                        mUsers.add(user);
//                                        break;
//                                    }
//                                }
//                            }else {
//                                mUsers.add(user);
//
//                            }
                        }
                    }

                }
                userAdapter = new UserAdapter(getContext(),mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        }

        }



