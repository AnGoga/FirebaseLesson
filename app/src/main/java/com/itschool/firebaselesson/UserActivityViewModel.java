package com.itschool.firebaselesson;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserActivityViewModel {
    private static UserActivityViewModel INSTANCE;
    private Context context;
    private DatabaseReference root;
    private MutableLiveData<List<User>> liveData = new MutableLiveData<>();

    private UserActivityViewModel(Context context) {
        this.context = context;
        this.root = FirebaseDatabase.getInstance("https://fir-lesson-b9697-default-rtdb.europe-west1.firebasedatabase.app").getReference().getRoot();
        initListeners();
    }

    private void initListeners() {
        root.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, User> map = snapshot.getValue(new GenericTypeIndicator<Map<String, User>>() {});
                liveData.postValue(new ArrayList<>(map.values()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public synchronized static UserActivityViewModel getInstance(Context context) {
        if (INSTANCE == null) INSTANCE = new UserActivityViewModel(context);
        return INSTANCE;
    }

    public void saveUser(User user) {
        root.child("users").child(user.getId()).setValue(user)
           .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Успешно сохранено", Toast.LENGTH_SHORT).show();
                    }
           }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public LiveData<List<User>> getUsersLiveData() {
        return liveData;
    }
}
