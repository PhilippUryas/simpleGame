package com.example.simplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Bullet {
  static public Bitmap bmp;

    public int x;
    public int y;

    private float speed;

    public int width;
    public int height;

    public MainSurfaceView mainSurfaceView;

    Matrix matrix;

    private long prevTime;


    public Bullet(Resources resources, int x, int y, MainSurfaceView mainSurfaceView) {
        this.mainSurfaceView = mainSurfaceView;
        bmp = BitmapFactory.decodeResource(resources, R.drawable.bulletv2);

        this.speed =-1 * mainSurfaceView.screenHeight/128;
        this.x = x;
        this.y = y;
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();

    }




    private void update() {
        y += speed;


    }

    public void draw(Canvas canvas) {
        update();
        Matrix matrix;
        matrix = new Matrix();
       // matrix.postScale(2.0f, 2.0f);
        matrix.postTranslate(x, y);
        canvas.drawBitmap(bmp, matrix, null);

    }
}
