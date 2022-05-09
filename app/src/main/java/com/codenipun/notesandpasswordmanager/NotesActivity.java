package com.codenipun.notesandpasswordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.codenipun.notesandpasswordmanager.databinding.ActivityNotesBinding;

public class NotesActivity extends AppCompatActivity {

    ActivityNotesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().setTitle("Notes Manager");



    }
}