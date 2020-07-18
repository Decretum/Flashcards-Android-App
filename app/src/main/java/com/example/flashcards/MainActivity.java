package com.example.flashcards;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    TextView text;
    Button back;
    Button flip;

    List<Card> deck;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        back = findViewById(R.id.back);
        flip = findViewById(R.id.flip);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                back();
            }
        });

        flip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (flip.getText().equals("Flip")) {
                    flip();
                } else if (flip.getText().equals("Next")) {
                    next();
                }
            }
        });

        initialize();
    }

    private void initialize() {
        String[] citizenshipData = getResources().getStringArray(R.array.citizenship_flashcards);

        deck = new ArrayList<>(citizenshipData.length / 2);
        for (int x = 0; x < citizenshipData.length / 2; x++) {
            Card card = new Card();
            card.setFront(citizenshipData[x * 2]);
            card.setBack(citizenshipData[x * 2 + 1]);
            deck.add(card);
        }
        Collections.shuffle(deck);
        index = 0;
        text.setText(deck.get(index).front);
    }

    private void flip() {
        if (index != -1) {
            text.setText(deck.get(index).flipAndGetText());
            if (deck.get(index).isFront) {
                flip.setText("Flip");
            } else {
                flip.setText("Next");
            }
        }
    }

    private void next() {
        if (index != -1) {
            if (!deck.get(index).isFront) {
                deck.get(index).flipAndGetText();
            }

            if (index == deck.size() - 1) {
                index = 0;
            } else {
                index++;
            }

            text.setText(deck.get(index).getText());

            if (deck.get(index).isFront) {
                flip.setText("Flip");
            } else {
                flip.setText("Next");
            }
        }
    }

    private void back() {
        if (index != -1) {
            if (!deck.get(index).isFront) {
                flip();
            } else {
                if (index == 0) {
                    index = deck.size() - 1;
                } else {
                    index--;
                }

                text.setText(deck.get(index).getText());

                if (deck.get(index).isFront) {
                    flip.setText("Flip");
                } else {
                    flip.setText("Next");
                }
            }
        }
    }

}