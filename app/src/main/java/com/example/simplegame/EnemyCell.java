package com.example.simplegame;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class EnemyCell
{

    public int x;
    public int y;


    public float speedY;
    public float speedX;

    public int width;
    public int height;

    public MainSurfaceView mainSurfaceView;
    public Bitmap bmp;

    public Random rnd = new Random();

    public int point;

    public EnemyCell(Resources resources, MainSurfaceView mainSurfaceView) {
        this.mainSurfaceView = mainSurfaceView;
        bmp = BitmapFactory.decodeResource(resources, R.drawable.cell);


        this.x = rnd.nextInt(mainSurfaceView.screenWidth - bmp.getWidth());
        this.y = 0;
        this.speedX = 0;
        this.speedY = mainSurfaceView.screenHeight / 164;

        if (TextScore.Score > 30) {
            speedX *= 1.8;
            speedY *= 1.8;
        } else if (TextScore.Score >= 30 && TextScore.Score < 20){
            speedX *=1.5;
            speedY *=1.5;
    }else if (TextScore.Score >10 && TextScore.Score <= 20) {
            speedY *= 1.2;
            speedX *= 1.2;
        }

       // this.point = 10;

        this.width = bmp.getWidth() ;
        this.height = bmp.getHeight();
    }

    public void update(){
        y += speedY;
    }

    public void draw(Canvas c){
        update();
        c.drawBitmap(bmp, x, y, null);
    }
}