package com.example.admin.gamesoft;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import java.util.ArrayList;

/**
 * Created by admin on 30.11.2015.
 */
public class MovingObject extends VisibleObject implements MoveAble{

    long timeCreated, timeCurrent;
    int speed;// скорость px \ millisec
    int movingStep;





    public MovingObject(Resources resources){
        super(resources);

        this.timeCreated = System.currentTimeMillis();
        this.timeCurrent = this.timeCreated;
    }

    public void moveTo(int x, int y){
        this.x = x;
        this.y = y;
        matrix.setTranslate(x, y);
    }

    public void rotate(int degrees){
        matrix.setRotate(degrees);
    }

    public void move(int x, int y){
        moveTo(this.x + x, this.y + y);
    }

    public void lifeCycleNextStep(){
        timeCurrent += Game.TIME_STEP;
    }




}
