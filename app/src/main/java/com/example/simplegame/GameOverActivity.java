package com.example.simplegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class GameOverActivity extends AppCompatActivity {

    // TextScore textScore;
    //HighScore highScore;
    MainSurfaceView mainSurfaceView;
    String fileName = "fileName";
    TextView highScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        int score = TextScore.Score;
        TextView textView = (TextView) findViewById(R.id.Score);
        Button button = (Button) findViewById(R.id.ret);
        textView.setText(score + "");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity();
            }
        });
        int high = readFile(highScore);
       if (high <= TextScore.Score){
           writeFile(TextScore.Score);
       }

           readFile(highScore);

    }


    public void switchActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        TextScore.Score = 0;
        MainSurfaceView.num = 0;
       // stopService(new Intent(this, MusicService.class));
    }

    @Override
    public void onBackPressed() {}

    public void writeFile(int score) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE )));
            bw.write(TextScore.Score + "");
            Log.d("EpTvou", TextScore.Score + "");
            bw.close();
            Log.d("fileM ","Файл записан");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int readFile(TextView highScore) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            String str;
            StringBuilder stringBuilder = new StringBuilder();
            str = br.readLine();
              //  stringBuilder.append(str);

            highScore = (TextView) findViewById(R.id.highScore);
            highScore.setText(str);
            return Integer.parseInt(str);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
