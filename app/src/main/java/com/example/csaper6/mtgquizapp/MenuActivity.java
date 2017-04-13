package com.example.csaper6.mtgquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class MenuActivity extends AppCompatActivity {
    private RadioButton standard,legacy,modern,allCards,raresMythics;
    private Button go;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

                standard = (RadioButton) findViewById(R.id.standard_button);
                legacy = (RadioButton) findViewById(R.id.button_legacy);
                modern= (RadioButton) findViewById(R.id.button_modern);
                allCards = (RadioButton) findViewById(R.id.button_allcards);
                raresMythics = (RadioButton) findViewById(R.id.button_raresandmythics);
                go = (Button) findViewById(R.id.button_go);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
