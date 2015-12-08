package soft.example.com.camerasoft;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

/**
 * Created by user on 06.12.2015.
 */
public class MainActivity extends Activity{ //implements SurfaceHolder.Callback{

    ImageView im;
    Camera camera;
    //SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    Camera.PictureCallback myPictureCallback_RAW;
    Camera.ShutterCallback myShutterCallback;
    Camera.PictureCallback myPictureCallback_JPG;

    private SurfaceView preview=null;
    private SurfaceHolder previewHolder=null;
    private boolean inPreview=false;
    private boolean cameraConfigured=false;

    private Bitmap bmp, operation;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        im = (ImageView)findViewById(R.id.imageViewPhoto);

        LinearLayout lin = (LinearLayout) findViewById(R.id.mainLinearLayout);
        preview = new SurfaceView(this);
        lin.addView(preview);
/*
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
*/
        //preview=(SurfaceView)findViewById(R.id.preview);
        previewHolder=preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        myShutterCallback = new Camera.ShutterCallback(){
            @Override
            public void onShutter() {}};

        myPictureCallback_RAW = new Camera.PictureCallback(){
            @Override
            public void onPictureTaken(byte[] arg0, Camera arg1) {}};

        myPictureCallback_JPG = new Camera.PictureCallback(){
            @Override
            public void onPictureTaken(byte[] arg0, Camera arg1) {
               //Bitmap bitmapPicture = BitmapFactory.decodeByteArray(arg0, 0, arg0.length);
                bmp = BitmapFactory.decodeByteArray(arg0, 0, arg0.length);

                // use ExifInterface from filename to check orientation
                bmp = rotateBitmap(bmp,90); // bad solution


                im.setImageBitmap(bmp);
                //im.setImageBitmap(bitmapPicture);
                startPreview();
            }};
    }

    @Override
    protected void onResume() {
        super.onResume();


        //start_camera();
        camera=Camera.open();
        startPreview();
    }

    @Override
    public void onPause() {
        if (inPreview) {
            camera.stopPreview();
        }

        camera.release();
        camera=null;
        inPreview=false;

        super.onPause();
    }

    private Camera.Size getBestPreviewSize(int width, int height,Camera.Parameters parameters) {
        Camera.Size result=null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width<=width && size.height<=height) {
                if (result==null) {
                    result=size;
                }
                else {
                    int resultArea=result.width*result.height;
                    int newArea=size.width*size.height;

                    if (newArea>resultArea) {
                        result=size;
                    }
                }
            }
        }

        return(result);
    }

    private void initPreview(int width, int height) {
        if (camera!=null && previewHolder.getSurface()!=null) {
            try {
                camera.setPreviewDisplay(previewHolder);
            }
            catch (Throwable t) {
                //Log.e("PreviewDemo-surfaceCallback",
                //        "Exception in setPreviewDisplay()", t);
                Toast
                        .makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG)
                        .show();
            }

            if (!cameraConfigured) {
                Camera.Parameters parameters=camera.getParameters();
                Camera.Size size=getBestPreviewSize(width, height,
                        parameters);

                /*Camera.Parameters params=camera.getParameters();
                List<Camera.Size> supportedSizes = params.getSupportedPictureSizes();
                Camera.Size size = (one of the sizes from supportedSizes list);
                params.setPictureSize(sizePicture.width, sizePicture.height);
                */
                if (size!=null) {
                    parameters.setPreviewSize(size.width, size.height);
                    parameters.setPictureSize(size.width, size.height);

                    camera.setParameters(parameters);
                    cameraConfigured=true;
                }
            }
        }
    }

    private void startPreview() {
        if (cameraConfigured && camera!=null) {
            setCameraDisplayOrientation(this, Camera.CameraInfo.CAMERA_FACING_BACK, camera);
            camera.startPreview();
            inPreview=true;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bmp = (Bitmap) extras.get("data");
            int height = (im.getWidth()*bmp.getHeight())/bmp.getWidth();
            bmp = Bitmap.createScaledBitmap(bmp,im.getWidth(),height, true);

            // use ExifInterface from filename to check orientation
            bmp = rotateBitmap(bmp,90); // bad solution

            im.setImageBitmap(bmp);
        }

    }

    public void buttonCameraIntent(View v) {
        //stop_camera();

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void buttonCameraSurface(View v){
        //
        // not work -
        // setCameraDisplayOrientation(this, Camera.CameraInfo.CAMERA_FACING_BACK, camera);

        camera.takePicture(myShutterCallback, myPictureCallback_RAW, myPictureCallback_JPG);

    }




    public void buttonFilterFirstClick(View v){
        changeBitmapColor(bmp, im, Color.RED);
    }

    public void buttonFilterSecondClick(View v){
        operation = Bitmap.createBitmap(bmp.getWidth(),bmp.getHeight(), bmp.getConfig());

        for(int i=0;i < bmp.getWidth(); i++){
            for(int j=0; j<bmp.getHeight(); j++){
                int p = bmp.getPixel(i, j);
                int r = Color.red(p);
                int g = Color.green(p);
                int b = Color.blue(p);
                int alpha = Color.alpha(p);

                r =  0;
                g =  g+150;
                b =  0;
                alpha = 0;
                operation.setPixel(i, j, Color.argb(Color.alpha(p), r, g, b));
            }
        }
        im.setImageBitmap(operation);
    }

    private void changeBitmapColor(Bitmap sourceBitmap, ImageView image, int color) {

        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap, 0, 0,
                sourceBitmap.getWidth() - 1, sourceBitmap.getHeight() - 1);
        Paint p = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 1);
        p.setColorFilter(filter);
        image.setImageBitmap(resultBitmap);

        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, p);
    }

    /*
    private void start_camera() {
        try{
            camera = Camera.open();
        }catch(RuntimeException e){
            showMessage("Error init_camera: " + e);
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();
        //modify parameter
        param.setPreviewFrameRate(20);
        param.setPreviewSize(176, 144);
        camera.setParameters(param);
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
            showMessage("Error init_camera: " + e);
            return;
        }
    }

    private void stop_camera() {
        camera.stopPreview();
        camera.release();
    }




    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }

    */

    public void showMessage(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("сообщение")
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    SurfaceHolder.Callback surfaceCallback=new SurfaceHolder.Callback() {
        public void surfaceCreated(SurfaceHolder holder) {
            // no-op -- wait until surfaceChanged()

           // setCameraDisplayOrientation(getActivity(), CameraInfo.CAMERA_FACING_BACK, camera);
        }

        public void surfaceChanged(SurfaceHolder holder,
                                   int format, int width,
                                   int height) {
            initPreview(width, height);
            startPreview();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // no-op
        }
    };

    public static void setCameraDisplayOrientation(Activity activity, int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else { // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
