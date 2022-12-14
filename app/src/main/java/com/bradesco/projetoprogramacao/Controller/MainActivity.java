package com.bradesco.projetoprogramacao.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.bradesco.projetoprogramacao.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.btn_start_mainMenu);

        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, OptionsActivity.class);
            startActivity(intent);
        });
    }
}