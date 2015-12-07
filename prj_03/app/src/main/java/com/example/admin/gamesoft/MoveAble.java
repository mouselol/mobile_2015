package com.example.admin.gamesoft;

/**
 * Created by admin on 03.12.2015.
 */
public interface MoveAble {

    void moveTo(int x, int y);

    void move(int x, int y);

    void lifeCycleNextStep();

    void rotate(int degrees);

}
