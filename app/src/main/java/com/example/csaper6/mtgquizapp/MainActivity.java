package com.example.csaper6.mtgquizapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button button1, button2, button3, button4;
    private JSONObject card1, card2, card3, card4;
    private JSONArray sets;
    private Bitmap bm2;
    private TextView question; //questionNumbers;
    private ImageView card;
    private ViewGroup.LayoutParams params;
    public static final String TAG = MainActivity.class.getSimpleName();
    private boolean one = false, two = false, three = false, four = false;
    public final int TOTALQUESTIONS = 20;
    private int totalCorrect = 0, questionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //changing the card to a bigger size
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        card = (ImageView) findViewById(R.id.card);
        params = card.getLayoutParams();
        params.width = 1000;
        params.height = 1000;


        // Load json
        String jsonString = "";
        try {
            InputStream fileInput = getAssets().open("AllSets.json");
            //make buffered reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput));
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString += line;
            }
            JSONObject obj = new JSONObject(jsonString);
            //get all the sets
            sets = obj.getJSONArray("sets");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "onCreate: IT CRASHED BECAUSE FILE NOT FOUND");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //wire
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        question = (TextView) findViewById(R.id.question_text);
        //questionNumbers = (TextView) findViewById(R.id.questionNumber);


        //parse local json
        question.setText("Name the card that matches the art." + totalCorrect + "/" + TOTALQUESTIONS);


        //if the answer is true(correct) then add1 to the total and run the execute
        //if its false(incorrect) run the execute
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (one) {
                    totalCorrect++;
                    //questionNumbers.setText(questionNumber);
                    //change the question and keep a live total correct/total question tally
                    question.setText("Name the card that matches the art." + totalCorrect + "/" + questionNumber);
                    //get a new question
                    new QuestionGenerator().execute();
                } else
                    //if its not correct then dont add 1 to total correct
                    new QuestionGenerator().execute();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (two) {
                    totalCorrect++;
                    //questionNumbers.setText(questionNumber);

                    question.setText("Name the card that matches the art." + totalCorrect + "/" + questionNumber);


                    new QuestionGenerator().execute();
                } else
                    new QuestionGenerator().execute();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (three) {
                    totalCorrect++;
                    //questionNumbers.setText(questionNumber);

                    question.setText("Name the card that matches the art." + totalCorrect + "/" + questionNumber);

                    new QuestionGenerator().execute();
                } else
                    new QuestionGenerator().execute();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (four) {
                    totalCorrect++;
                    //questionNumbers.setText(questionNumber);

                    question.setText("Name the card that matches the art." + totalCorrect + "/" + questionNumber);

                    new QuestionGenerator().execute();
                } else
                    new QuestionGenerator().execute();
            }
        });
        //the first execute
        new QuestionGenerator().execute();
    }

    private class QuestionGenerator extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            //add one to the total questions done so far
            questionNumber++;
            //setQuestionNumber();
            //set all the answers to incorrect at first
            one = false;
            two = false;
            three = false;
            four = false;
            //Random rand = new Random();

            //get 4 cards

            card1 = getRandomCard();
            card2 = getRandomCard();
            card3 = getRandomCard();
            card4 = getRandomCard();

//            } else if (questionNumber < 15) {
//                card1 = getRandomCardWithSameType("Instant");
//                card2 = getRandomCardWithSameType("Instant");
//                card3 = getRandomCardWithSameType("Instant");
//                card4 = getRandomCardWithSameType("Instant");
//            } else if (questionNumber < 18)
//                generateHardQuestion();
//            else
//                generateImpossQuestion();


            //make an array of 4 cards and pick a random one
            JSONObject variousCards[] = {card1, card2, card3, card4};

            int r = (int) (Math.random() * 4);


            try {

                String multiverseid = variousCards[r].getString("multiverseid");
//            Picasso.with(getApplicationContext()).load("http://image.deckbrew.com/mtg/multiverseid/" + multiverseid + ".jpg").into(card);


//            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.id.card);
                //make the art appear
                URL url = new URL("http://image.deckbrew.com/mtg/multiverseid/" + multiverseid + ".jpg");
                Bitmap bm = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                //if it doesnt have a multiverse id it will be null so redo until it isnt null
                if (bm2 != null) {
                    bm2.recycle();
                    bm2 = null;
                }

                bm2 = Bitmap.createBitmap(bm, 20, 38, bm.getWidth() - 40, bm.getHeight() - 175);

                bm.recycle();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //set one of the questions to true
            if (r == 0) {
                one = true;

            } else if (r == 1) {
                two = true;

            } else if (r == 2) {
                three = true;

            } else if (r == 3) {
                four = true;

            }
            //question.setText("What card is this? " + totalCorrect + "/" + TOTALQUESTIONS);


            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //end at 20 questions
            if (questionNumber >= 21)
                endGame();
            try {
                //if the name is very long(more than 12 characters), then change the text size to accomodate for it
                if (card1.getString("name").length() > 12)
                    button1.setTextSize(10);
                else
                    button1.setTextSize(14);
                //set the button to the name then make the card null fo future use
                button1.setText(card1.getString("name"));
                card1 = null;
                if (card2.getString("name").length() > 12)
                    button2.setTextSize(10);
                else
                    button2.setTextSize(14);
                button2.setText(card2.getString("name"));
                card2 = null;
                if (card3.getString("name").length() > 12)
                    button3.setTextSize(10);
                else
                    button3.setTextSize(14);
                button3.setText(card3.getString("name"));
                card3 = null;
                if (card4.getString("name").length() > 12)
                    button4.setTextSize(10);
                else
                    button4.setTextSize(14);
                button4.setText(card4.getString("name"));
                card4 = null;
                card.setImageBitmap(bm2);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void endGame() {
        //make everything invisible and display the end screen
        button1.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);
        button3.setVisibility(View.INVISIBLE);
        button4.setVisibility(View.INVISIBLE);
        //questionNumbers.setVisibility(View.INVISIBLE);
        question.setTextSize(16);
        question.setText("Congratulations you got " + totalCorrect + " out of " + TOTALQUESTIONS + " total questions!");
    }

//    private void setQuestionNumber() {
//        questionNumbers.setText(questionNumber);
//    }
    // create InputStream

    private JSONObject getRandomCard() {
        Random rand = new Random();
        try {
            //get a random set
            int temp = rand.nextInt(sets.length());

            JSONObject obj = sets.getJSONObject(temp);

            //get a random card within the set
            temp = rand.nextInt(obj.optJSONArray("cards").length());
            if (obj.optJSONArray("cards").optJSONObject(temp).optString("multiverseid", null) != null)
                return obj.optJSONArray("cards").optJSONObject(temp);
                //call the method again if the card has no multiverse id
            else
                return getRandomCard();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

//this is a possibility for a future design

//    private JSONObject getRandomCardWithSameType(String type) {
//        String tType = "";
//        int temp = 0;
//        Random rand = new Random();
//        String jsonString = "";
//        try {
//            InputStream fileInput = getAssets().open("AllSets.json");
//            //make buffered reader
//            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInput));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                jsonString += line;
//            }
//            JSONObject obj = new JSONObject(jsonString);
//            //get all the sets
//            JSONArray sets = obj.getJSONArray("sets");
//            //Log.d(TAG, "getRandomCard: " + sets.opt(1).toString());
//            //get a random set
//            while (tType != type) {
//                temp = rand.nextInt(sets.length());
//                obj = sets.getJSONObject(temp);
//                //get a random card within the set
//                temp = rand.nextInt(obj.optJSONArray("cards").length());
//                tType = obj.optJSONArray("cards").optJSONObject(temp).getString("type");
//            }
//            //Log.d(TAG, "getRandomCard: "+obj.optJSONArray("cards").optJSONObject(temp));
//            return obj.optJSONArray("cards").optJSONObject(temp);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.d(TAG, "onCreate: IT CRASHED BECAUSE FILE NOT FOUND");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}







