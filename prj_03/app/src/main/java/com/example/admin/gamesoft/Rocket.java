package com.example.admin.gamesoft;


import android.content.res.Resources;
import android.graphics.Canvas;

import java.util.ArrayList;


/**
 * Created by admin on 02.12.2015.
 */
public class Rocket extends MovingObject{

    public rocketStatus status;
    Bomber owner;
    ArrayList<Terror> terrors;

    boolean flagCorrectCrater = true;

    final long periodBanging = Game.TIME_STEP * 6;

    long timeFired, timeDetonation, periodFlight;



    public void lifeCycleNextStep() {
        //Log.w("myApp", " status 777 =" + terrors.size());
        switch (status){
            case ABOARD:move(10, -10);break;
            case INFLIGHT:
                //if((timeCurrent - timeFired) > periodFlight){
                Terror killedTerror = isNearTerror();
                if(killedTerror != null){
                    //Log.w("myApp", "bah rocket ");
                    this.status = rocketStatus.BANG;
                    this.timeDetonation = timeCurrent; //System.currentTimeMillis();
                    this.setImage(R.drawable.bang3);
                    killedTerror.isDead = true;
                }else{
                    //Log.w("myApp", "go rocket " + speed);
                    move(0,0 - speed);
                }
                break;
            case BANG:
                //Log.w("myApp", "timeCurrent = " + timeCurrent);
                //Log.w("myApp", "timeDetonation = " + timeDetonation);
                //Log.w("myApp", "periodBanging = " + periodBanging);

                if((timeCurrent - timeDetonation) > periodBanging){
                    status = rocketStatus.AFTERMATH;
                }
                break;
            //case AFTERMATH:setImage(R.drawable.crater1);break;
        }
        super.lifeCycleNextStep();

    }

    public enum rocketStatus {
        ABOARD, INFLIGHT, BANG, AFTERMATH
    }

    public Rocket(Bomber bomber, Resources resources) {
        super(resources);
        owner = bomber;
        status = rocketStatus.ABOARD;
        //speed = ;
        //periodFlight = speed * (long)Math.sqrt(2 * owner.flightHeight / 9.8);
       // periodFlight = 1000 * 2;
    }

    @Override
    public void render(Canvas canvas) {

        switch (status){
            case ABOARD:
                setImage(R.drawable.none);

                break;
            case INFLIGHT:
                setImage(R.drawable.rocket);
                resizeImage(this.owner.image.getWidth() / 6, 0);
                break;
            case BANG:
                setImage(R.drawable.bang3);
                resizeImage(this.owner.image.getWidth(), 0);
                if(flagCorrectCrater) {
                    moveTo(this.x - this.image.getWidth() / 2, this.y - this.image.getHeight() / 2);
                    flagCorrectCrater = false;
                }
                break;
            case AFTERMATH:

                if(!flagCorrectCrater) {
                    moveTo(this.x + this.image.getWidth() / 2, this.y + this.image.getHeight() / 2);
                    flagCorrectCrater = true;
                }

                setImage(R.drawable.crater2);
                resizeImage(this.owner.image.getWidth() / 2, 0);

                break;
        }
        super.render(canvas);
    }



    public Terror isNearTerror(){
        for (VisibleObject v : terrors) {
            if (Terror.class.isInstance(v)) {
                Terror t = (Terror) v;
                if(
                        (this.y < (t.y + t.image.getHeight())) &&
                                (!t.isDead) &&
                                (this.x > t.x) &&
//                                (this.x < (t.x + t.image.getWidth() - this.image.getWidth()))
                                  (this.x < (t.x + t.image.getWidth()))

                ){
                    return t;
                }
            }
        }

        return null;
    }

    public void notifyAboutTerror(ArrayList<Terror> terrors){
        this.terrors = terrors;
    }

}
