package com.example.admin.gamesoft;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.SurfaceHolder;

import java.util.Random;

class GameThread extends Thread{

    Handler mHandler;
    //public volatile GameHandler handler;
    Game game;

    private boolean runFlag = false;
    public SurfaceHolder surfaceHolder;
    private long prevTime, rocketActTime = 0, timeRocketDown;
    MovingObject terror,bomb,bomber;
    PlayView parentView;
    Resources res;

    public GameThread(SurfaceHolder surfaceHolder, Resources resources, PlayView view){

        parentView = view;



        res = resources;

        this.surfaceHolder = surfaceHolder;

        game = new Game(resources);
        game.field_width = parentView.getWidth();
        game.field_height = parentView.getHeight();

        // сохраняем текущее время
        prevTime = System.currentTimeMillis();
    }

    public void setRunning(boolean run) {
        runFlag = run;
    }

    @Override
    public void run() {
/*
        Looper.prepare();
        //mHandler = new GameHandler();
        //notifyAll(); // <- ADDED
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                // прием сообщений от активити

            }
        };
        Looper.loop();
*/
        //game = new Game(res);
        //publishProgress("7777");
        callGameEvent(Game.gameEvent.START_NEW_GAME);
        //publishProgress("888");


        Canvas canvas;
        while (runFlag) {
            // получаем текущее время и вычисляем разницу с предыдущим
            // сохраненным моментом времени
            long now = System.currentTimeMillis();
            long elapsedTime = now - prevTime;
            if (elapsedTime > Game.TIME_STEP){
                prevTime = now;
            }
            canvas = null;
            try {
                // получаем объект Canvas и выполняем отрисовку
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {

                    game.render(canvas);

                }
            }
            finally {
                if (canvas != null) {
                    // отрисовка выполнена. выводим результат на экран
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void callGameEvent(Game.gameEvent e){
        game.handleGameEvent(e);
    }


    private void publishProgress(String text) {
        //Bundle msgBundle = new Bundle();
        //msgBundle.putString("result", text);
        //Message msg = new Message();
        //msg.setData(msgBundle);
        MainActivity ma = (MainActivity)parentView.getContext();//.resultHandler.sendMessage(msg);
        //ma.resultHandler.handleMessage();
        ma.showMessage("test 1 " + text);

    }

}