package com.codenipun.notesandpasswordmanager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codenipun.notesandpasswordmanager.databinding.ActivityNotesBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.Distribution;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesActivity extends AppCompatActivity {

//    ActivityNotesBinding binding;
    FloatingActionButton addNotes;
    FirebaseAuth mAuth;
    FirebaseUser fUser;
    FirebaseFirestore fFirestore;
    RecyclerView mRecyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;

    // we are going to use firebase recycler adapter for fetching data from firestore to our recyclerview

    FirestoreRecyclerAdapter<firebasemodel, NoteViewHolder> notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityNotesBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_notes);
//        View view = binding.getRoot();
//        setContentView(view);
        getSupportActionBar().setTitle("Notes Manager");

        addNotes = findViewById(R.id.addNotes);

        mAuth = FirebaseAuth.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        fFirestore = FirebaseFirestore.getInstance();

        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this, createNoteActivity.class);
                startActivity(intent);
            }
        });



        // when user come to this activity we have to load data for that particular data and for that we use query

        Query query = fFirestore.collection("notes").document(fUser.getUid()).collection("myNotes").orderBy("title", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<firebasemodel> allUserNotes = new FirestoreRecyclerOptions.Builder<firebasemodel>().setQuery(query,firebasemodel.class).build();

        notesAdapter = new FirestoreRecyclerAdapter<firebasemodel, NoteViewHolder>(allUserNotes) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected void onBindViewHolder(@NonNull NoteViewHolder holder, int position, @NonNull firebasemodel model) {

                int colourCode = getRandomColor();

                holder.mnote.setBackgroundColor(holder.itemView.getResources().getColor(colourCode, null));

                holder.notetitle.setText(model.getTitle());
                holder.notecontent.setText(model.getContent());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(NotesActivity.this, "item clicked", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout, parent, false);
                return new NoteViewHolder(view);
            }
        };


        mRecyclerView = findViewById(R.id.notesRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setAdapter(notesAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        notesAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(notesAdapter!=null){
            notesAdapter.stopListening();
        }
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{
        private TextView notetitle;
        private TextView notecontent;
        LinearLayout mnote;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            notetitle = itemView.findViewById(R.id.noteTitle);
            notecontent = itemView.findViewById(R.id.noteContent);
            mnote = itemView.findViewById(R.id.note);
        }
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

    private int getRandomColor(){
        List<Integer> colour = new ArrayList<>();
        colour.add(R.color.color1);
        colour.add(R.color.color2);
        colour.add(R.color.color3);
        colour.add(R.color.color4);
        colour.add(R.color.color5);
        colour.add(R.color.color6);
        colour.add(R.color.color7);
        colour.add(R.color.color8);
        colour.add(R.color.color9);
        colour.add(R.color.color10);

        Random random = new Random();

        int number = random.nextInt(colour.size());
        return colour.get(number);
    }
}