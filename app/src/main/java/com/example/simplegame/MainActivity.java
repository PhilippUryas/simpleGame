package com.example.simplegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {
    private CheckBox musicBox;
    private Intent intent;
    private MusicService musicService;
    static boolean sound = true;
    static boolean music = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextScore.Score = 0;
        MainSurfaceView.num = 0;
        MainSurfaceView.maxNum = 0;
        Button playButton = (Button) findViewById(R.id.PLAY);
        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switchActivity();
            }
        });

        TextView button = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        R.string.sujet2, LENGTH_LONG);
                toast.show();
            }
        });
      // startService(new Intent(this, MusicService.class));

        intent = new Intent(this, MusicService.class);

        musicBox = (CheckBox) findViewById(R.id.music);
        musicBox.setChecked(music);
        musicBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicBox.isChecked()){
                    music = true;
                } else {
                    music = false;
                }
                Log.d("Music", music + "");
                checkcheck();
            }
        });
        final CheckBox soundBox = (CheckBox)findViewById(R.id.sound);
        soundBox.setChecked(sound);
        soundBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soundBox.isChecked()){
                sound = true;
                }else {
                    sound =false;
                }
                Log.d("Sound", sound + "");
            }
        });
checkcheck();
    }


    @Override
    public void onBackPressed() {}

    public void checkcheck(){
        if (music == true) {
            startService(intent);
            //checked = false;
        }else {
            stopService(intent);
            //checked = true;

        }
    }


    public void switchActivity(){
        Intent intentJ = new Intent(this, GameActivity.class);
        startActivity(intentJ);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextScore.Score = 0;
        MainSurfaceView.num = 0;
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MusicService.class));
    }
}
