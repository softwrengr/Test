package com.softwrengr.test;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.IOException;

public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback {

    private final Camera camera;
    private SurfaceHolder holder;


    public ShowCamera(Context context, Camera camera) {
        super(context);

        this.camera = camera;
        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
     Camera.Parameters parameters = camera.getParameters();

     if(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE){
      parameters.set("orientation","portrait");
      camera.setDisplayOrientation(90);
      parameters.setRotation(90);
     }
     else {
         parameters.set("orientation","landscape");
         camera.setDisplayOrientation(0);
         parameters.setRotation(0);
     }


        try {
            camera.setParameters(parameters);
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }
}
