package com.example.finalassignmentnotesapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ViewNotesActivity extends AppCompatActivity {

    private ListView lvNotes;
    private TextView tvEmptyState;
    private ArrayList<String> notesList;
    private ArrayList<String> noteFiles;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        // Initialize views
        lvNotes = findViewById(R.id.lvNotes);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        Button btnRefresh = findViewById(R.id.btnRefresh);
        Button btnBack = findViewById(R.id.btnBack);

        // Initialize lists and adapter
        notesList = new ArrayList<>();
        noteFiles = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                notesList
        );
        lvNotes.setAdapter(adapter);

        // Load notes on startup
        loadNotes();

        // Set click listener for note items
        lvNotes.setOnItemClickListener((parent, view, position, id) -> viewNote(position));

        // Set long click listener for deletion
        lvNotes.setOnItemLongClickListener((parent, view, position, id) -> {
            deleteNote(position);
            return true;
        });

        // Refresh button
        btnRefresh.setOnClickListener(v -> loadNotes());

        // Back button
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadNotes() {
        notesList.clear();
        noteFiles.clear();

        File directory = getFilesDir();
        File[] files = directory.listFiles((dir, name) -> name.startsWith("note_"));

        if (files != null && files.length > 0) {
            // Sort files by timestamp (newest first)
            ArrayList<File> fileList = new ArrayList<>();
            Collections.addAll(fileList, files);
            Collections.sort(fileList, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));

            for (File file : fileList) {
                try {
                    FileInputStream fis = openFileInput(file.getName());
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader bufferedReader = new BufferedReader(isr);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }

                    JSONObject noteJson = new JSONObject(sb.toString());
                    notesList.add(noteJson.getString("title"));
                    noteFiles.add(file.getName());

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        // Update empty state visibility
        if (notesList.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            lvNotes.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            lvNotes.setVisibility(View.VISIBLE);
        }

        adapter.notifyDataSetChanged();
    }

    private void viewNote(int position) {
        try {
            String filename = noteFiles.get(position);
            FileInputStream fis = openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject noteJson = new JSONObject(sb.toString());

            Intent intent = new Intent(this, NewNoteActivity.class);
            intent.putExtra("filename", filename);
            intent.putExtra("title", noteJson.getString("title"));
            intent.putExtra("content", noteJson.getString("content"));
            startActivity(intent);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error opening note", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteNote(int position) {
        String filename = noteFiles.get(position);
        File file = new File(getFilesDir(), filename);
        if (file.delete()) {
            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
            loadNotes();
        } else {
            Toast.makeText(this, "Error deleting note", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }
}