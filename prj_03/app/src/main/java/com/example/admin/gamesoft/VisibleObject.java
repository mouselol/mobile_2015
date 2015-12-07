package com.example.admin.gamesoft;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * Created by admin on 03.12.2015.
 */
public class VisibleObject extends GameObject {

    int x = 20, y = 0;
    Bitmap image;
    Matrix matrix;



    public VisibleObject(Resources resources) {
        super(resources);
        Matrix m = new Matrix();
        m.setTranslate(x, y);
        this.matrix = m;

    }

    public void setImage(int resource){
        this.image = BitmapFactory.decodeResource(resources, resource);
    }

    public void render(Canvas canvas){

        try {
            canvas.drawBitmap(image, this.matrix, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void resizeImage(int maxWidth, int maxHeight) {

        int width = image.getWidth();
        int height = image.getHeight();

        float ratioX, ratioY;

        if(maxHeight == 0){
            ratioY = 1;
        }else{
            ratioY = (float)maxHeight / height;
        }

        if(maxWidth == 0){
            ratioX = 1;
        }else{
            ratioX = (float)maxWidth / width;
        }

        float ratio = Math.min(ratioX, ratioY);

        Matrix matrix = new Matrix();
        matrix.postScale(ratio, ratio);
        image = Bitmap.createBitmap(image, 0, 0, width, height, matrix, false);
    }


}
