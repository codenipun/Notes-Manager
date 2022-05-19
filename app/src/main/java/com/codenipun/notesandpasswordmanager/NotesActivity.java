package com.codenipun.notesandpasswordmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codenipun.notesandpasswordmanager.databinding.ActivityNotesBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class NotesActivity extends AppCompatActivity {

    ActivityNotesBinding binding;
    FirebaseAuth mAuth;
    FirebaseUser fUser;
    FirebaseFirestore fFirestore;
    RecyclerView mRecyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    // we are going to use firebase recycler adapter for fetching data from firestore to our recyclerview

    FirestoreRecyclerAdapter<firebaseModel, noteViewHolder> notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().setTitle("Notes Manager");

        mAuth = FirebaseAuth.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        fFirestore = FirebaseFirestore.getInstance();

        binding.addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this, createNoteActivity.class);
                startActivity(intent);
            }
        });
    }














    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:
                mAuth.signOut();
                Toast.makeText(NotesActivity.this, "Log-out successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NotesActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.setting:
                Toast.makeText(NotesActivity.this, "Under Maintenance", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}