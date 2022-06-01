package com.codenipun.notesandpasswordmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codenipun.notesandpasswordmanager.databinding.ActivityEditNoteBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

public class editNoteActivity extends AppCompatActivity {

    ActivityEditNoteBinding binding;

    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditNoteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        setSupportActionBar(binding.toolBarOfEditNote);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data = getIntent();

        binding.titleOfEditNote.setText(data.getStringExtra("title"));
        binding.contentOfEditNote.setText(data.getStringExtra("content"));

        binding.saveEditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTitle = binding.titleOfEditNote.getText().toString();
                String newContent = binding.contentOfEditNote.getText().toString();

                if(newTitle.isEmpty()){
                    Toast.makeText(editNoteActivity.this, "Please add title of note", Toast.LENGTH_SHORT).show();
                }else if(newContent.isEmpty()){
                    Toast.makeText(editNoteActivity.this, "Please write content of note", Toast.LENGTH_SHORT).show();
                }else{
                    binding.progresBarofEditNote.setVisibility(view.VISIBLE);
                    DocumentReference documentReference = mFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNotes").document(data.getStringExtra("noteId"));
                    Map<String, Object> updatedNote = new HashMap<>();
                    updatedNote.put("title", newTitle);
                    updatedNote.put("content", newContent);
                    documentReference.set(updatedNote).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            binding.progresBarofEditNote.setVisibility(view.INVISIBLE);
                            Toast.makeText(editNoteActivity.this, "Note Updated Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(editNoteActivity.this, NotesActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(editNoteActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}