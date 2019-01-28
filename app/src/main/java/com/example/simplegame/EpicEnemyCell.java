package com.example.simplegame;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by Uryas on 11.05.2017.
 */

public class EpicEnemyCell extends EnemyCell {

    int speedX;

    public EpicEnemyCell(Resources resources, MainSurfaceView mainSurfaceView) {
        super(resources, mainSurfaceView);
        this.mainSurfaceView = mainSurfaceView;

        this.bmp = BitmapFactory.decodeResource(resources, R.drawable.cell);

        this.x = mainSurfaceView.screenWidth/2;
        this.y = y;

        this.width = bmp.getWidth();
        this.height = bmp.getHeight();


        this.speedY = mainSurfaceView.screenHeight/350;
        switch (rnd.nextInt(2)) {
            case 0:
            this.speedX = mainSurfaceView.screenWidth / 120;
                break;
            case 1:
            this.speedX = mainSurfaceView.screenWidth/120*-1;
                break;
        }
    }


    public void update() {
        if(x <= 0 || x >= mainSurfaceView.screenWidth - bmp.getWidth()){
            speedX *= -1;
        }
        y += speedY;
        x += speedX;
    }

    @Override
    public void draw(Canvas c) {
        super.draw(c);
    }
}
