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
        List<String> rawData = Arrays.asList("35", "What does the Constitution do?", "sets up the government", "What is one right or freedom from the First Amendment?", "speech", "How many amendments does the Constitution have?", "twenty-seven (27)", "What are two rights in the Declaration of Independence?", "life, liberty, pursuit of happiness", "What is freedom of religion?", "You can practice any religion, or not practice a religion", "What is the economic system in the United States?", "capitalist economy", "What is the “rule of law”?", "Everyone must follow the law.", "We elect a U.S. Senator for how many years? ", "6", "Who is one of your state’s U.S. Senators now?", "Kamala Harris", "The House of Representatives has how many voting members? ", "435", "We elect a U.S. Representative for how many years?", "2", "Name your U.S. Representative.", "Nancy Pelosi", "In what month do we vote for President?", "November", "What does the President's Cabinet do?", "advises the President", "What are two Cabinet-level positions?", "Vice President and Secretary of State", "How many justices are on the Supreme Court?", "9", "Who is the Chief Justice of the United States now?", "John Roberts", "Who is the Governor of your state now?", "Gavin Newsom", "What is the name of the Speaker of the House of Representatives now? ", "Nancy Pelosi", "When is the last day you can send in federal income tax forms?", "April 15", "Who wrote the Declaration of Independence?", "Jefferson", "When was the Declaration of Independence adopted?", "July 4, 1776", "When was the Constitution written?", "1787", "The Federalist Papers supported the passage of the U.S. Constitution. Name one of the writers.", "Hamilton", "Name one war fought by the United States in the 1800s.", "1812", "What did Susan B. Anthony do?", "Fought for women's rights", "Name one war fought by the United States in the 1900s.", "WWII", "Who was President during World War I?", "Wilson", "Who was President during the Great Depression and World War II?", "Roosevelt", "Before he was President, Eisenhower was a general. What war was he in?", "WWII", "What movement tried to end racial discrimination?", "civil rights (movement)", "Name one American Indian tribe in the United States", "Apache", "Name one of the two longest rivers in the United States.", "Mississippi", "Name one U.S. territory.", "Puerto Rico", "What is the name of the national anthem?", "The Star-Spangled Banner");
        int numCards = Integer.parseInt(rawData.get(0));
        deck = new ArrayList<>(numCards);
        for (int x = 0; x < numCards; x++) {
            Card card = new Card();
            card.setFront(rawData.get(x * 2 + 1));
            card.setBack(rawData.get(x * 2 + 2));
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