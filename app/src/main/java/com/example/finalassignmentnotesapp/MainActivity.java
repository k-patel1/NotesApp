package com.example.finalassignmentnotesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    private TextView tvAffirmation;
    private boolean showAffirmations;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Volley request queue
        requestQueue = Volley.newRequestQueue(this);

        // Initialize views
        tvAffirmation = findViewById(R.id.tvAffirmation);
        Button btnNewNote = findViewById(R.id.btnNewNote);
        Button btnViewNotes = findViewById(R.id.btnViewNotes);
        Button btnSettings = findViewById(R.id.btnSettings);
        Button btnAbout = findViewById(R.id.btnAbout);

        // Load settings
        loadSettings();

        // Set click listeners
        btnNewNote.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, NewNoteActivity.class)));
        btnViewNotes.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ViewNotesActivity.class)));
        btnSettings.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));
        btnAbout.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AboutActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload settings when returning from SettingsActivity
        loadSettings();
    }

    private void loadSettings() {
        SharedPreferences prefs = getSharedPreferences("AppSettings", MODE_PRIVATE);
        showAffirmations = prefs.getBoolean("dailyAffirmation", false);

        if (showAffirmations) {
            fetchAffirmation();
            tvAffirmation.setVisibility(View.VISIBLE);
        } else {
            tvAffirmation.setVisibility(View.GONE);
        }
    }

    private void fetchAffirmation() {
        String url = "https://www.affirmations.dev/";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        String affirmation = response.getString("affirmation");
                        tvAffirmation.setText(affirmation);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        tvAffirmation.setText("Couldn't load affirmation");
                    }
                },
                error -> {
                    error.printStackTrace();
                    tvAffirmation.setText("Error loading affirmation");
                }
        );

        requestQueue.add(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (requestQueue != null) {
            requestQueue.cancelAll(this);
        }
    }
}