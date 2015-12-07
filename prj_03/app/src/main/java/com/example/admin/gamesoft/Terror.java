package com.example.admin.gamesoft;


import android.content.res.Resources;
import android.graphics.Canvas;


/**
 * Created by admin on 02.12.2015.
 */
public class Terror extends MovingObject{

    boolean flagRotated = false;

    boolean isDead = false;

    public Terror(Resources resources){
        super(resources);
        setImage(R.drawable.terror2);
    }

    @Override
    public void render(Canvas canvas) {

        if(isDead && !flagRotated){
            rotate(90);
            flagRotated = true;
        }

        super.render(canvas);
    }
}
