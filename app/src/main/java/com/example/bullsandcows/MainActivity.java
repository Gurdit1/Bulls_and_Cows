package com.example.bullsandcows;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public String n = numGenerator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static String numGenerator(){
        String number = "";
        //Generates a 4-digit random number

        for(int i=0;i<4;++i){
            Random r = new Random();
            int n = r.nextInt(9);
            //Checks if n is not already in number
            while(number.contains(String.valueOf(n))){
                n = r.nextInt(9);
            }
            number += String.valueOf(n);
        }
        return number;
    }

    public int bullCount(String guess, String n){
        //Counts the bulls
        int bulls = 0;
        //Checks if the digits at i in both guess and n match
        for(int i=0; i<4;++i){
            if((String.valueOf(guess.charAt(i))).equals(String.valueOf(n.charAt(i)))){
                bulls+=1;
            }
        }

        return bulls;
    }

    public int cowBullCount(String guess, String n){
        //Counts the cows and bulls
        int cows = 0;
        for(int i=0;i<4;++i){
            //Checks if digit from guess is contained in n
            for(int j=0;j<4;++j){
                if((String.valueOf(guess.charAt(i))).equals(String.valueOf(n.charAt(j)))){
                    cows+=1;
                }
            }
        }
        return cows;
    }

    public boolean repeatingDigitsChecker(String word){
        //Checks if a word has repeating characters
        String checkWord = "";
        int i=0;
        boolean repeating = false;
        while((i<word.length())&&(repeating==false)){
            if(checkWord.contains(String.valueOf(word.charAt(i)))){
                //compares against checkWord which contains all currently checked digits
                repeating = true;
            }
            else{
                //adds new character to checkWord
                checkWord += String.valueOf(word.charAt(i));
                i+=1;
            }
        }
        return repeating;
    }

    public void onClick(View view){
        android.widget.ImageButton rs = this.findViewById(R.id.restartButton);
        android.widget.ImageButton ad = this.findViewById(R.id.addGuessButton);

        android.widget.TextView g = this.findViewById(R.id.userInput);
        android.widget.TextView tx = this.findViewById(R.id.textView);

        ///Checks user input for repeating digits
        if(repeatingDigitsChecker(String.valueOf(g.getText()))){
            tx.setText("Digit repeat");
        }
        //Checks if user entered a 4 digit number
        else if((String.valueOf(g.getText())).length()<4){
            //if statement used for grammatical correctness
            if((String.valueOf(g.getText())).length()==3){
                tx.setText("Needs 1 more digit");
            }
            else{
                tx.setText("Needs " + String.valueOf(4 - ((String.valueOf(g.getText())).length())) + " more digits");
            }
        }
        else {
            tx.setText(String.valueOf(n));////////////////////////

            android.widget.TextView gdp1 = this.findViewById(R.id.guessDisplay1);
            android.widget.TextView gdp2 = this.findViewById(R.id.guessDisplay2);
            android.widget.TextView gdp3 = this.findViewById(R.id.guessDisplay3);
            android.widget.TextView gdp4 = this.findViewById(R.id.guessDisplay4);
            android.widget.TextView gdp5 = this.findViewById(R.id.guessDisplay5);
            android.widget.TextView gdp6 = this.findViewById(R.id.guessDisplay6);
            android.widget.TextView gdp7 = this.findViewById(R.id.guessDisplay7);

            android.widget.TextView[] gdpArr = {gdp1, gdp2, gdp3, gdp4, gdp5, gdp6, gdp7}; //Array of all the guessDisplay widgets

            android.widget.TextView rdp1 = this.findViewById(R.id.resultDIsplay1);
            android.widget.TextView rdp2 = this.findViewById(R.id.resultDisplay2);
            android.widget.TextView rdp3 = this.findViewById(R.id.resultDisplay3);
            android.widget.TextView rdp4 = this.findViewById(R.id.resultDisplay4);
            android.widget.TextView rdp5 = this.findViewById(R.id.resultDisplay5);
            android.widget.TextView rdp6 = this.findViewById(R.id.resultDisplay6);
            android.widget.TextView rdp7 = this.findViewById(R.id.resultDisplay7);

            android.widget.TextView[] rdpArr = {rdp1, rdp2, rdp3, rdp4, rdp5, rdp6, rdp7}; //Array of all the resultDisplay widgets

            //Searches for the first empty guessDisplay widget to know which widget needs editing or if all widgets are full
            int i = 0;
            while ((!(String.valueOf(gdpArr[i].getText()).equals("")) && (i < 7))) {
                i += 1;
            }


            //Returns the number of cows and bulls
            int bulls = bullCount(String.valueOf(g.getText()), n);
            int cows = cowBullCount(String.valueOf(g.getText()), n) - bulls; //Counts the number of cows and subtracts it from the number of bulls to prevent repeated counting


            gdpArr[i].setText(String.valueOf(g.getText()));
            rdpArr[i].setText(String.valueOf(bulls) + "B" + String.valueOf(cows) + "C");

            //tx.setText(String.valueOf(bulls) + "B" + String.valueOf(cows) + "C");

            //checks if game has been won and outputs "You won"
            if ((String.valueOf(g.getText())).equals(String.valueOf(n))) {
                tx.setText("You won");
                //Disables + button and activates restart button
                ad.setClickable(false);
                rs.setClickable(true);
            }
            //checks if game a been lost and outputs "You lost"
            else if (i == 6) {
                tx.setText("You lost");
                //Disables + button and activates restart button
                ad.setClickable(false);
                rs.setClickable(true);
            }
        }
    }


}
