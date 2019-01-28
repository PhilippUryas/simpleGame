package com.example.simplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by Uryas on 11.05.2017.
 */

public class TextScore {
    private MainSurfaceView mainSurfaceView;

    public static int Score;

    public int sc;

    private float x;
    private float y;

    private Paint paint;
    private Paint numPaint;
    private Bitmap shield;
    private Bitmap bull;

    public TextScore(Resources resources, Context context, MainSurfaceView mainSurfaceView, int sc){
        this.mainSurfaceView = mainSurfaceView;
        this.sc = sc;

        shield = BitmapFactory.decodeResource(resources, R.drawable.shield);
        bull = BitmapFactory.decodeResource(resources, R.drawable.bulletv2);
        numPaint = new Paint();
        paint = new Paint();
        int myColor = context.getResources().getColor(R.color.blue);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(myColor);
        paint.setTextSize(mainSurfaceView.screenHeight/2);


            this.x = mainSurfaceView.screenWidth / 4 +paint.getTextSize()/24  ;
            this.y = mainSurfaceView.screenHeight / 2;
        Log.d("TextSize",paint.getTextSize() + "");




    }

    //String abc = "abc";
    public void update(MainSurfaceView mainSurfaceView){
        if (Score > 8) {
            this.x = mainSurfaceView.screenWidth / 4 - mainSurfaceView.screenWidth/4;

        }
        if (Score > 98){
            this.x = mainSurfaceView.screenWidth / 4 - mainSurfaceView.screenWidth/16;
            this.y = mainSurfaceView.screenHeight / 2;
            paint.setTextSize(mainSurfaceView.screenHeight/4);
        }
        Score += sc;

    }


    public void draw(Canvas canvas){
        for (int i = 0; i<=MainSurfaceView.num;i++){
            Matrix matrix = new Matrix();
            //matrix.postScale(2.0f, 2.0f);
            matrix.postTranslate(0 + bull.getWidth()*i/2, 0);
            canvas.drawBitmap(bull,matrix,null);
        }
        if (BonusHeart.oneMoreChance == true) {
            canvas.drawBitmap(shield, 0 + shield.getWidth()+MainSurfaceView.maxNum*bull.getWidth()/2,
                    0, null);
        }
        canvas.drawText(Score + "",x,y,paint);

    }

}
