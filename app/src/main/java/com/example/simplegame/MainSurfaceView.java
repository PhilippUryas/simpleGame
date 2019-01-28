package com.example.simplegame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * Created by ph666 on 30.04.2017.
 */


public class MainSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    public List<Bullet> cells = new ArrayList<Bullet>();
    public List<EnemyCell> enemy = new ArrayList<EnemyCell>();
    public List<BonusBull> bonusBulls = new ArrayList<BonusBull>();
    public List<BonusHeart> bonusHearts = new ArrayList<BonusHeart>();

    private SurfaceHolder holder;
    private GameActivity gameActivity;
    private DrawThread drawThread;

    private Bitmap picture;

    public int shotX;
    public int shotY;

    public int cellX;
    public int cellY;

    private Thread thread;

    private Random rnd;

    private TextScore textScore;

    private Random random = new Random();

    Context mContext;

    static public byte num;
    static public byte maxNum;

    private SoundPool sounds;
    private int soundCoin;
    private int soundBonus;
    private int sound1Up;
    private int soundShield;

    public MainSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);

        picture = BitmapFactory.decodeResource(getResources(), R.drawable.cell);
        cellX = 200 - picture.getWidth();
        cellY = screenHeight;

        textScore = new TextScore(getResources(),getContext(),this, 1);

        thread = new Thread(this);
        thread.start();
        mContext = context;
        sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        soundCoin = sounds.load(context, R.raw.coin, 1);
        soundBonus = sounds.load(context, R.raw.bonus, 1);
        sound1Up = sounds.load(context, R.raw.up, 1);
        soundShield = sounds.load(context, R.raw.shielddestroyed, 1);
    }

    DisplayMetrics metrics = this.getResources().getDisplayMetrics();
    int screenWidth = metrics.widthPixels;
    int screenHeight = metrics.heightPixels;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(this, getHolder());
        drawThread.setRunFlag(true);
        drawThread.start();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.setRunFlag(false);
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException ex) {

            }
        }
    }


    private Bullet createSprite(int resource) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        return new Bullet(getResources(), shotX, cellY, this);
    }


    public boolean onTouchEvent(MotionEvent e) {
        shotX = (int) e.getX();
        shotY = (int) e.getY();
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            if (cells.size() <= maxNum) {
                cells.add(createSprite(R.drawable.cell));
                num--;
            }

        }

        return true;
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        textScore.draw(canvas);
        Iterator<Bullet> j = cells.iterator();
        Iterator<EnemyCell> i = enemy.iterator();
        Iterator<BonusBull> bon = bonusBulls.iterator();
        Iterator<BonusHeart> h = bonusHearts.iterator();

        while (h.hasNext()){
            BonusHeart bonusHeart = h.next();
            bonusHeart.draw(canvas);
            if (bonusHeart.y>=screenHeight){
                h.remove();
            }
        }
        while (bon.hasNext()){
            BonusBull bs = bon.next();
            bs.draw(canvas);
            if (bs.y>=screenHeight){
                bon.remove();


            }
        }
        while (i.hasNext()) {
            EnemyCell e = i.next();
            e.draw(canvas);
            if (e.y >= screenHeight) {
                i.remove();
                if (BonusHeart.oneMoreChance == false) {
                    Intent intent = new Intent(mContext, GameOverActivity.class);
                    drawThread.setRunFlag(false);
                    mContext.startActivity(intent);
                    ((Activity) mContext).finish();
                }else {
                    BonusHeart.oneMoreChance = false;
                    if (MainActivity.sound == true){
                        sounds.play(soundShield, 3.0f, 3.0f, 0, 0, 1.5f);
                    }
                }
            }
        }
        while (j.hasNext()) {
            Bullet b = j.next();
            b.draw(canvas);
            if (b.y <= 0) {
                j.remove();
                num++;
            }
        }
    }

    public void testCollision() {
        Iterator<Bullet> b = cells.iterator();
        while (b.hasNext()) {
            Bullet bulletCell = b.next();
            Iterator<EnemyCell> i = enemy.iterator();
            Iterator<BonusBull> bon = bonusBulls.iterator();
            Iterator<BonusHeart> h = bonusHearts.iterator();
            while (i.hasNext()) {
                EnemyCell enemyCell = i.next();
                if ((Math.abs(bulletCell.x - enemyCell.x) <= (bulletCell.width + enemyCell.width) / 2f)
                        && (Math.abs(bulletCell.y - enemyCell.y) <= (bulletCell.height + enemyCell.height) / 2f)) {
                    i.remove();
                    b.remove();
                    if (MainActivity.sound == true){
                        sounds.play(soundCoin, 3.0f, 3.0f, 0, 0, 1.5f);
                    }
                    textScore.update(this);
                    num++;

                }
                while (h.hasNext()){
                    BonusHeart bonusHeart = h.next();
                    if ((Math.abs(bulletCell.x - bonusHeart.x) <= (bulletCell.width + bonusHeart.width) / 2f)
                            && (Math.abs(bulletCell.y - bonusHeart.y) <= (bulletCell.height + bonusHeart.height) / 2f)){
                        b.remove();
                        h.remove();
                        if (MainActivity.sound == true){
                            sounds.play(sound1Up, 3.0f, 3.0f, 0, 0, 1.5f);
                        }
                        BonusHeart.oneMoreChance = true;
                        num++;
                        textScore.update(this);

                    }
                }
                while (bon.hasNext()){
                    BonusBull bonusBull = bon.next();
                    if ((Math.abs(bulletCell.x - bonusBull.x) <= (bulletCell.width + bonusBull.width) / 2f)
                            && (Math.abs(bulletCell.y - bonusBull.y) <= (bulletCell.height + bonusBull.height) / 2f)) {
                        b.remove();
                        bon.remove();
                        num+=2  ;
                        maxNum++;

                        if (MainActivity.sound == true){
                            sounds.play(soundBonus, 3.0f, 3.0f, 0, 0,1.0f);
                        }
                        textScore.update(this);
                    }
                }
            }
        }
    }


        @Override
        public void run() {
            while (true){
                try {
                    int speed = 2000;
                    if (TextScore.Score % 10 == 0 && TextScore.Score != 0 && speed > 1000){
                        speed -= 200;
                    }
                    Thread.sleep(speed);
                    if (((TextScore.Score == 8&& bonusBulls.size() <= 0) ||
                            (TextScore.Score != 10  && TextScore.Score % 10 == 0)
                            && TextScore.Score != 0 && bonusBulls.size() <= 0 )&&maxNum <= 2) {
                        bonusBulls.add(new BonusBull(getResources(), this));
                    }
                    if (TextScore.Score % 18== 0 && TextScore.Score != 0 && bonusHearts.size() <= 0 &&
                            BonusHeart.oneMoreChance == false){
                        bonusHearts.add(new BonusHeart(getResources(),this));
                    }
                    if (TextScore.Score < 30) {
                        switch (random.nextInt(3)) {
                            case 0:
                                enemy.add(new EnemyCell(getResources(), this));
                                break;
                            case 1:
                                enemy.add(new EpicEnemyCell(getResources(), this));
                                break;
                            case 2:
                                enemy.add(new EnemyCell(getResources(), this));
                                enemy.add(new EpicEnemyCell(getResources(), this));
                                break;
                        }
                    } else {
                        enemy.add(new EnemyCell(getResources(),this));
                        enemy.add(new EpicEnemyCell(getResources(),this));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
}


