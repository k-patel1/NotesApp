package com.example.finalassignmentnotesapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ViewNotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        ListView lvNotes = findViewById(R.id.lvNotes);

        // TODO: Replace with actual notes from database
        ArrayList<String> sampleNotes = new ArrayList<>();
        sampleNotes.add("Sample Note 1");
        sampleNotes.add("Sample Note 2");
        sampleNotes.add("Sample Note 3");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                sampleNotes
        );

        lvNotes.setAdapter(adapter);

        // TODO: Add click listener to open note details
    }
}