package com.itschool.firebaselesson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.itschool.firebaselesson.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            // Человек авторизован
            goToNextActivity();
        }

        initViews();
    }

    private void initViews() {
        binding.buttonLogin.setOnClickListener(v -> {
            Pair<String, String> pair = getUserData();
            if (pair == null) return;
            login(pair);
        });

        binding.buttonRegister.setOnClickListener(v -> {
            Pair<String, String> pair = getUserData();
            if (pair == null) return;
            registration(pair);
        });

    }

    private void registration(Pair<String, String> pair) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(pair.first, pair.second)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            goToNextActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void login(Pair<String, String> pair) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(pair.first, pair.second)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            goToNextActivity();
                        } else {

                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private Pair<String, String> getUserData() {
        String login = binding.editTextLogin.getText().toString();
        String password = binding.editTextPassword.getText().toString();
        if (login.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Введите данные", Toast.LENGTH_SHORT).show();
            return null;
        }
        return Pair.create(login, password);
    }

    private void goToNextActivity() {
        startActivity(new Intent(this, UsersActivity.class));
        finish();
    }
}