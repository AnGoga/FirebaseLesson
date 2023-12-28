package com.itschool.firebaselesson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.itschool.firebaselesson.databinding.ActivityUsersBinding;

public class UsersActivity extends AppCompatActivity {
    private ActivityUsersBinding binding;
    private UserActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initRecyclerView();
        UserActivityViewModel.getInstance(this, adapter);
    }

    private void initViews() {
        binding.buttonSave.setOnClickListener(v -> {
            String name = binding.editTextName.getText().toString();
            String bio = binding.editTextBio.getText().toString();
            if (name.isEmpty() || bio.isEmpty()) {
                Toast.makeText(this, "Введите данные", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = new User(FirebaseAuth.getInstance().getCurrentUser().getUid(), name, bio);
            UserActivityViewModel.getInstance(this, adapter).saveUser(user);
        });
    }

    private void initRecyclerView() {
        adapter = new UserActivityAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}