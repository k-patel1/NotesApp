package com.example.finalassignmentnotesapp;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewNoteActivity extends AppCompatActivity {

    private EditText etNoteTitle, etNoteContent;
    private String currentFilename = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        // Initialize views
        etNoteTitle = findViewById(R.id.etNoteTitle);
        etNoteContent = findViewById(R.id.etNoteContent);
        Button btnSaveNote = findViewById(R.id.btnSaveNote);

        // Check if we're editing an existing note
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentFilename = extras.getString("filename");
            etNoteTitle.setText(extras.getString("title"));
            etNoteContent.setText(extras.getString("content"));

            // Update button text if editing
            btnSaveNote.setText("Update Note");
        }

        // Set up save button click listener
        btnSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

        // Optional: Add a cancel/back button
        Button btnCancel = findViewById(R.id.btnCancel);
        if (btnCancel != null) {
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    private void saveNote() {
        // Get input values
        String title = etNoteTitle.getText().toString().trim();
        String content = etNoteContent.getText().toString().trim();

        // Validate input
        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create JSON object with note data
        JSONObject noteJson = new JSONObject();
        try {
            noteJson.put("title", title);
            noteJson.put("content", content);
            noteJson.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    .format(new Date()));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating note", Toast.LENGTH_SHORT).show();
            return;
        }

        // Determine filename
        String filename;
        if (currentFilename != null) {
            // Editing existing note - use same filename
            filename = currentFilename;
        } else {
            // New note - create timestamped filename
            filename = "note_" + System.currentTimeMillis() + ".json";
        }

        // Save to file
        try {
            FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(noteJson.toString().getBytes());
            outputStream.close();

            // Show success message
            Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show();

            // Close activity and return to previous screen
            finish();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving note", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        // Optional: Confirm discard changes if note has content
        if (!etNoteTitle.getText().toString().isEmpty() ||
                !etNoteContent.getText().toString().isEmpty()) {

            Toast.makeText(this, "Note not saved. Press back again to discard.", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }
}