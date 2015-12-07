package com.example.admin.gamesoft;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by admin on 02.12.2015.
 */
public class Game {

    Resources resources;
    public ArrayList<Terror> terrors;

    Bomber bomber;

    public gameState state = gameState.HOME_PAGE;

    public enum gameState{
        HOME_PAGE, PLAY_PROCESS
    }

    public enum gameEvent{
        FIRE_ROCKET, START_NEW_GAME, STOP_GAME, BOMBER_MOVE_RIGHT, BOMBER_MOVE_LEFT
    }

    public final static long TIME_STEP = 30, MAX_TIME = 1000*60;
    final int TERROR_COUNT = 3;


    int field_width, field_height;

    public ArrayList<VisibleObject> visibleObjects;

    public Game(Resources resources) {
        super();

        terrors = new ArrayList<Terror>();
        this.resources = resources;

        //prepareGame();
    }



    public void handleGameEvent(Game.gameEvent e){

        switch (e){
            case FIRE_ROCKET:
                if(bomber.fireRocket()){

                }else{

                }
                break;
            case START_NEW_GAME:
                prepareGame();
                break;
            case STOP_GAME:
                stopGame();
                break;
            case BOMBER_MOVE_LEFT:
                bomber.move(-100,0);
                break;
            case BOMBER_MOVE_RIGHT:
                bomber.move(100,0);
                break;

        }
    }


    public void render(Canvas canvas){

        switch (state){
            case HOME_PAGE:
                showHome(canvas);
                break;
            case PLAY_PROCESS:
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                for (Rocket r : bomber.getRockets()) {
                    r.notifyAboutTerror(terrors);
                }

                for (VisibleObject v : visibleObjects) {
                    if (v instanceof MoveAble){
                        MovingObject m = (MovingObject)v;
                        m.lifeCycleNextStep();
                    }
                }

                for (VisibleObject v : visibleObjects) {
                    v.render(canvas);
                }


                break;
        }


    }

    public static int randInt(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public void showHome(Canvas canvas){

        canvas.drawARGB(0, 225, 225, 255);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);

        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        canvas.drawText("Приказано уничтожить!", 10, 25, paint);

        visibleObjects = new ArrayList<VisibleObject>();
        MovingObject photoHome  = new MovingObject(resources);
        photoHome.setImage(R.drawable.su25home);
        photoHome.resizeImage(field_width, 0);
        photoHome.moveTo(0, 300);

        photoHome.render(canvas);
    }

    public void prepareGame(){
        state = gameState.PLAY_PROCESS;
        visibleObjects = new ArrayList<VisibleObject>();

        VisibleObject ground  = new VisibleObject(resources);
        ground.setImage(R.drawable.greenfields);
        visibleObjects.add(ground);

        bomber = new Bomber(resources);
        bomber.resizeImage(field_width / 4, 0);
        bomber.moveTo((field_width - bomber.image.getWidth()) / 2, field_height - bomber.image.getHeight());
        visibleObjects.add(bomber);

        for(int i=1; i<= TERROR_COUNT;i++){
            Terror newTerror = new Terror(resources);
            //Log.w("myApp", "no 4444START_NEW_GAME");
            this.terrors.add(newTerror);
            newTerror.resizeImage(field_width / 8, 0);
            newTerror.moveTo(
                    Game.randInt(0, field_width - newTerror.image.getWidth()),
                    Game.randInt(0, field_height - bomber.image.getHeight() - newTerror.image.getHeight() - 100));
            //newTerror.moveTo(0,0);
            visibleObjects.add(newTerror);

        }



    }

    public void stopGame(){
        state = gameState.HOME_PAGE;


    }

}
