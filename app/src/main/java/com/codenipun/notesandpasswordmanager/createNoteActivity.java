package com.codenipun.notesandpasswordmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codenipun.notesandpasswordmanager.databinding.ActivityCreateNoteBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class createNoteActivity extends AppCompatActivity {
    ActivityCreateNoteBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore mFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNoteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        getSupportActionBar().setTitle("Create New Note");
//        setSupportActionBar(binding.toolBarOfEditText);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.titleOfNote.getText().toString().trim();
                String content = binding.contentOfNote.getText().toString().trim();

                if(title.isEmpty() || content.isEmpty()){
                    Toast.makeText(createNoteActivity.this, "Both fields are required", Toast.LENGTH_SHORT).show();
                }else{
                    // To save the data on cloud firestore first we have to take the object of document reference bcoz we store data in document type in firestore
                    DocumentReference documentReference = mFirestore.collection("notes").document(mUser.getUid()).collection("MyNotes").document();

                    Map<String, Object> note = new HashMap<>();

                    note.put("title", title);
                    note.put("content", content);

                    documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(createNoteActivity.this, "Note Created Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(createNoteActivity.this, NotesActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(createNoteActivity.this, "Failed to Create Note", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}