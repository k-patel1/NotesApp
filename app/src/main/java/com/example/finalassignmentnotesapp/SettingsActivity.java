package com.example.finalassignmentnotesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch switchDarkMode = findViewById(R.id.switchDarkMode);
        Switch switchDailyAffirmation = findViewById(R.id.switchDailyAffirmation);
        Button btnSaveSettings = findViewById(R.id.btnSaveSettings);

        // TODO: Load saved settings

        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean darkMode = switchDarkMode.isChecked();
                boolean dailyAffirmation = switchDailyAffirmation.isChecked();

                // TODO: Save settings
                Toast.makeText(SettingsActivity.this, "Settings saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}