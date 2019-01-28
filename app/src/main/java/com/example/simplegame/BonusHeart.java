package com.example.simplegame;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by ph666 on 14.05.2017.
 */

public class BonusHeart extends BonusBull {
    static public boolean oneMoreChance = false;

    public BonusHeart(Resources resources, MainSurfaceView mainSurfaceView) {
        super(resources, mainSurfaceView);
        this.bmp = BitmapFactory.decodeResource(resources, R.drawable.shield);
        this.width = bmp.getWidth()*2;
        this.height = bmp.getHeight();
        this.speedX = 0;
        this.speedY = mainSurfaceView.getHeight()/200;
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void draw(Canvas c) {
        super.draw(c);
    }
}
