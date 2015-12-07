package com.example.admin.gamesoft;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PlayView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread drawThread;
    boolean retry;
    Context context;

    public PlayView(Context context) {
        super(context);
        this.context = context;
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {


    }




    public void stopGame(String s){
        retry = false;
        ((MainActivity) getContext()).showMessage(s);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        drawThread = new GameThread(getHolder(), getResources(),this);
        drawThread.setRunning(true);
        drawThread.start();
        /*
        try {
            drawThread.wait(); // <- ADDED
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        drawThread.handler.sendMessage();
        */
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        retry = true;
        // завершаем работу потока
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // если не получилось, то будем пытаться еще и еще
            }
        }

    }

    public void callGameEvent(Game.gameEvent e){
        drawThread.callGameEvent(e);
    }
}