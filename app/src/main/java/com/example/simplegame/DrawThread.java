package com.example.simplegame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by ph666 on 30.04.2017.
 */

public class DrawThread extends Thread {
    private boolean runFlag = false;
    private SurfaceHolder surfaceHolder;
    private MainSurfaceView mainSurfaceView;

    DrawThread(MainSurfaceView mainSurfaceView, SurfaceHolder surfaceHolder){
        this.surfaceHolder = surfaceHolder;
        this.mainSurfaceView = mainSurfaceView;




    }
    public void setRunFlag(boolean run){ runFlag = run; };

    @Override
    public void run() {
        Canvas canvas;
        while (runFlag){
                canvas = null;
                    try {
                       canvas = surfaceHolder.lockCanvas(null);
                        synchronized (surfaceHolder){
                            mainSurfaceView.onDraw(canvas);
                            mainSurfaceView.testCollision();
                          }
                    } finally {
                        if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }

                }
            }
        }



