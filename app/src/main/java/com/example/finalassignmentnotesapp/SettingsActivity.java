package com.example.finalassignmentnotesapp;

import android.content.SharedPreferences;
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

        Switch switchDailyAffirmation = findViewById(R.id.switchDailyAffirmation);
        Button btnSaveSettings = findViewById(R.id.btnSaveSettings);

        // Load saved settings
        SharedPreferences prefs = getSharedPreferences("AppSettings", MODE_PRIVATE);
        switchDailyAffirmation.setChecked(prefs.getBoolean("dailyAffirmation", false));

        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("dailyAffirmation", switchDailyAffirmation.isChecked());
                editor.apply();

                Toast.makeText(SettingsActivity.this, "Settings saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}