package com.example.simplegame;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by ph666 on 13.05.2017.
 */

public class BonusBull extends EnemyCell {
    public BonusBull(Resources resources, MainSurfaceView mainSurfaceView) {
        super(resources, mainSurfaceView);
        this.bmp = BitmapFactory.decodeResource(resources, R.drawable.blackbonus);
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
        this.speedX = 0;
        this.speedY = mainSurfaceView.getHeight()/200;
    }


    public void update() {
        super.update();
    }

    @Override
    public void draw(Canvas c) {
        super.draw(c);
    }
}
