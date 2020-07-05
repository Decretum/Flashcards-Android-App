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
        List<String> rawData = Arrays.asList(
                "100",
                "What is the supreme law of the land?", "The Constitution",
                "What does the Constitution do?", "Sets up the government",
                "The idea of self-government is in the first three words of the Constitution. What are these words?", "We the People",
                "What is an amendment?", "A change to the Constitution",
                "What do we call the first ten amendments to the Constitution?", "The Bill of Rights",
                "What is one right or freedom from the First Amendment?", "Freedom of speech",
                "How many amendments does the Constitution have?", "27",
                "What did the Declaration of Independence do?", "Declared our independence from Great Britain",
                "What are two rights in the Declaration of Independence?", "Life / liberty / pursuit of happiness",
                "What is freedom of religion?", "You can practice any religion, or not practice a religion",
                "What is the economic system in the United States?", "Capitalist economy",
                "What is the 'rule of law'?", "Everyone must follow the law.",
                "Name one branch or part of the government", "The President",
                "What stops one branch of government from becoming too powerful?", "Checks and balances",
                "Who is in charge of the executive branch?", "The President",
                "Who makes federal laws?", "Congress",
                "What are the two parts of the U.S. Congress?", "The Senate and the House of Representatives",
                "How many U.S. Senators are there?", "100",
                "We elect a U.S. Senator for how many years? ", "6",
                "Who is one of your state’s U.S. Senators now?", "Kamala Harris",
                "The House of Representatives has how many voting members? ", "435",
                "We elect a U.S. Representative for how many years?", "2",
                "Name your U.S. Representative.", "Nancy Pelosi",
                "Who does a U.S. Senator represent?", "All people of the state",
                "Why do some states have more Representatives than other states?", "The number of Representatives is based on the state's population",
                "We elect a President for how many years?", "4",
                "In what month do we vote for President?", "November",
                "What is the name of the President of the United States now?", "Donald Trump",
                "What is the name of the Vice President of the United States now?", "Mike Pence",
                "If the President can no longer serve, who becomes President?", "The Vice President",
                "If both the President and the Vice President can no longer serve, who becomes President?", "The Speaker of the House",
                "Who is the Commander in Chief of the military?", "The President",
                "Who signs bills to become laws?", "The President",
                "Who vetos bills?", "The President",
                "What does the President's Cabinet do?", "Advises the President",
                "What are two Cabinet-level positions?", "Vice President and Secretary of State",
                "What does the judicial branch do?", "Resolves disputes",
                "What is the highest court in the United States?", "The Supreme Court",
                "How many justices are on the Supreme Court?", "9",
                "Who is the Chief Justice of the United States now?", "John Roberts",
                "Under our Constitution, some powers belong to the federal government. What is one power of the federal government?", "Print money",
                "Under our Constitution, some powers belong to the states. What is one power of the states?", "Give a driver's license",
                "Who is the Governor of your state now?", "Gavin Newsom",
                "What is the capital of your state?", "Sacramento",
                "What are the two major political parties in the United States?", "Democratic and Republican",
                "What is the political party of the President now?", "Republican",
                "What is the name of the Speaker of the House of Representatives now? ", "Nancy Pelosi",
                "There are four amendments to the Constitution about who can vote. Describe one of them.", "Women and men can vote",
                "What is one responsibility that is only for United States Citizens?", "Vote",
                "Name one right only for United States citizens.", "Vote in a federal election",
                "What are two rights of everyone living in the United States?", "Freedom of speech and freedom of religion",
                "What do we show loyalty to when we say the Pledge of Allegiance?", "The United States",
                "What is one promise you make when you become a United States citizen?", "To obey the laws of the United States",
                "How old do citizens have to be to vote for President?", "18+",
                "What are two ways that Americans can participate in their democracy?", "Vote, run for office",
                "When is the last day you can send in federal income tax forms?", "April 15",
                "When must all men register for the Selective Service?", "18",
                "What is one reason colonists came to America?", "Freedom",
                "Who lived in America before the Europeans arrived?", "Native Americans",
                "What group of people were taken to America and sold as slaves?", "Africans",
                "Why did the colonists fight the British?", "Because of high taxes",
                "Who wrote the Declaration of Independence?", "Thomas Jefferson",
                "When was the Declaration of Independence adopted?", "July 4, 1776",
                "There were 13 original states. Name three.", "New York, North Carolina, South Carolina",
                "What happened at the Constitutional Convention?", "The Constitution was written",
                "When was the Constitution written?", "1787",
                "The Federalist Papers supported the passage of the U.S. Constitution. Name one of the writers.", "Alexander Hamilton",
                "What is one thing Benjamin Franklin is famous for?", "U.S. Diplomat",
                "Who is the 'Father of Our Country'?", "George Washington",
                "Who was the first President?", "George Washington",
                "What territory did the United States buy from France in 1803?", "Louisiana",
                "Name one war fought by the United States in the 1800s.", "War of 1812",
                "Name the U.S. war between the North and the South", "The Civil War",
                "Name one problem that led to the Civil War.", "Slavery",
                "What was one important thing that Abraham Lincoln did?", "Freed the slaves",
                "What did the Emancipation Proclamation do?", "Freed the slaves",
                "What did Susan B. Anthony do?", "Fought for women's rights",
                "Name one war fought by the United States in the 1900s.", "World War II",
                "Who was President during World War I?", "Wilson",
                "Who was President during the Great Depression and World War II?", "Franklin Roosevelt",
                "Who did the United States fight in World War II?", "Japan, Germany, and Italy",
                "Before he was President, Eisenhower was a general. What war was he in?", "World War II",
                "During the Cold War, what was the main concern of the United States?", "Communism",
                "What movement tried to end racial discrimination?", "civil rights (movement)",
                "What did Martin Luther King, Jr. do?", "Fought for civil rights",
                "What major event happened on September 11, 2001, in the United States?", "Terrorists attacked the United States",
                "Name one American Indian tribe in the United States", "Apache",
                "Name one of the two longest rivers in the United States.", "Mississippi",
                "What ocean is on the West Coast of the United States?", "Pacific Ocean",
                "What ocean is on the East Coast of the United States?", "Atlantic Ocean",
                "Name one U.S. territory.", "Puerto Rico",
                "Name one state that borders Canada.", "Washington",
                "Name one state that borders Mexico.", "California",
                "What is the capital of the United States?", "Washington, D.C.",
                "Where is the Statue of Liberty?", "New York Harbor",
                "Why does the flag have 13 stripes?", "There were 13 original colonies",
                "Why does the flag have 50 stars?", "There are 50 states",
                "What is the name of the national anthem?", "The Star-Spangled Banner",
                "When do we celebrate Independence Day?", "July 4",
                "Name two national U.S. Holidays", "Christmas, Thanksgiving");
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