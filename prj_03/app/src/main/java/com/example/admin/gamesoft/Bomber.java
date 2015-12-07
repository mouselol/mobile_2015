package com.example.admin.gamesoft;


import android.content.res.Resources;
import android.graphics.Canvas;


import java.util.ArrayList;

/**
 * Created by admin on 02.12.2015.
 */
public class Bomber extends MovingObject {

    final int ROCKET_COUNT = 6; // bomb count


    double flightHeight = 100000;

    public ArrayList<Rocket> getRockets(){
        return this.rockets;
    }


    public ArrayList<Rocket> rockets;

    public Bomber(Resources resources) {
        super(resources);
        setImage(R.drawable.su25bomber);

        rockets = new ArrayList<Rocket>();
        for(int i=1;i<= ROCKET_COUNT;i++){
            Rocket rocket = new Rocket(this, resources);
            rocket.speed = this.image.getHeight() * (int)Game.TIME_STEP / 500;
            rockets.add(rocket);
        }

    }

    public boolean fireRocket(){
        Rocket rocketToFire = findRocketToFire();
        if(rocketToFire == null) {
            move(0, -100);// для проверки
        }else{
           // move(100,0);
            rocketToFire.status = Rocket.rocketStatus.INFLIGHT;
            rocketToFire.timeFired = System.currentTimeMillis();
            rocketToFire.setImage(R.drawable.rocket);
            rocketToFire.moveTo(this.x + this.image.getWidth()/ (Game.randInt(2,3)),this.y);
            return true;
        }
        return false;
    }

    Rocket findRocketToFire() {
        for(Rocket r : rockets) {
            if(r.status == Rocket.rocketStatus.ABOARD) {
                return r;
            }
        }
        return null;
    }

    public void lifeCycle(){

    }

    @Override
    public void render(Canvas canvas) {
        resizeImage(canvas.getWidth()/4,0);
        for (Rocket r: rockets) {
            r.render(canvas);
        }
        super.render(canvas);
    }

    @Override
    public void lifeCycleNextStep() {
        for (Rocket r: rockets) {
            r.lifeCycleNextStep();
        }
        super.lifeCycleNextStep();
    }
}
