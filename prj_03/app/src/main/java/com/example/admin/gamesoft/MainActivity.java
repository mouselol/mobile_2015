package com.example.admin.gamesoft;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by admin on 26.11.2015.
 */
public class MainActivity extends Activity {


    //public Handler resultHandler;
    PlayView playView;
    //int screen_width, screen_height;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //resultHandler = new HandlerExtension(this);

        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.myBaseLL);
        playView = new PlayView(this);
        linearLayout.addView(playView);

    }

    public void buttonBomb_Click(View v){
        playView.callGameEvent(Game.gameEvent.FIRE_ROCKET);
    }

    public void buttonStart_Click(View v){
        playView.callGameEvent(Game.gameEvent.START_NEW_GAME);
    }

    public void buttonStop_Click(View v){
        playView.callGameEvent(Game.gameEvent.STOP_GAME);
    }

    public void buttonRight_Click(View v){
        playView.callGameEvent(Game.gameEvent.BOMBER_MOVE_RIGHT);
    }

    public void buttonLeft_Click(View v){
        playView.callGameEvent(Game.gameEvent.BOMBER_MOVE_LEFT);
    }



    public void showMessage(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Сообщение");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void showMessage(long n){
        showMessage(String.valueOf(n));
    }
}
