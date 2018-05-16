package com.shehryarapps.android.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int turnCount;
    private int p1Score;
    private int p2Score;

    private TextView textViewP1;
    private TextView textViewP2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewP1 = findViewById(R.id.text_view_p1);
        textViewP2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button button_res = findViewById(R.id.button_reset);
        button_res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }
        if (player1Turn) {
            ((Button) view).setText("X");
            ((Button) view).setTextSize(50);
        } else {
            ((Button) view).setText("O");
            ((Button) view).setTextSize(50);
        }
        turnCount++;

        if (checkWin()){
            if (player1Turn){
                player1Wins();
            }else
                player2Wins();
        }else if(turnCount == 9){
            draw();
        }else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkWin() {
        String[][] field = new String[3][3];
        for (int i = 0 ; i < 3; i++){
            for (int j=0 ; j < 3; j++){
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0 ; i < 3; i++){
            if (field[i][0].equals(field[i][1]) &&
                    field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")){
                return true;
            }
        }
        for (int i = 0 ; i < 3; i++){
            if (field[0][i].equals(field[1][i]) &&
                    field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")){
                return true;
            }
        }
        if (field[0][0].equals(field[1][1]) &&
                field[0][0].equals(field[2][2])
                && !field[0][0].equals("")){
            return true;
        }

        if (field[0][2].equals(field[1][1]) &&
                field[0][2].equals(field[2][0])
                && !field[0][2].equals("")){
            return true;
        }
        return false;
    }

    private void player1Wins(){
        p1Score++;
        Toast.makeText(this, "Player 1 wins", Toast.LENGTH_SHORT).show();
        updateScoreText();
        resetBoard();
    }

    private void player2Wins() {
        p2Score++;
        Toast.makeText(this, "Player 2 wins", Toast.LENGTH_SHORT).show();
        updateScoreText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    private void updateScoreText(){
        textViewP1.setText("Player 1: " + p1Score);
        textViewP2.setText("Player 2: " + p2Score);
    }

    private void resetBoard(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j< 3; j++){
                buttons[i][j].setText("");
            }
        turnCount = 0;
            player1Turn = true;
        }
    }

    private void resetGame(){
        p2Score=0;
        p1Score=0;
        updateScoreText();
        resetBoard();
    }

    //save the values on rotate
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("turnCount", turnCount);
        outState.putInt("Player1Score", p1Score);
        outState.putInt("Player2Score", p2Score);
        outState.putBoolean("Player1Turn", player1Turn);
    }

    //restoring saved values on rotate
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        turnCount = savedInstanceState.getInt("turnCount");
        p1Score = savedInstanceState.getInt("Player1Score");
        p2Score = savedInstanceState.getInt("Player2Score");
        player1Turn = savedInstanceState.getBoolean("Player1Turn");
    }
}
