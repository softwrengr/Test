package com.softwrengr.test;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.softwrengr.test.databinding.ActivityMainBinding;



public class MainActivity extends AppCompatActivity {

    private final static int PERMISSION_REQUEST_CODE = 200;
    private final static int FRONT_CAMERA = 1;
    private ActivityMainBinding binding;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);


        String[] perms = {"android.permission.CAMERA"};


        if (checkPermission()) {

            showCamera();

        } else {

            requestPermission(perms);

        }


    }

    private void showCamera(){
        Camera camera = Camera.open(FRONT_CAMERA);
        ShowCamera showCamera = new ShowCamera(this, camera);
        binding.framelayout.addView(showCamera);
    }

    public void onQuestionClick(View view) {
        onShowPopupWindowClick(view,"Text 1",Gravity.CENTER);
    }


    public void onexclamationClick(View view) {
        onShowPopupWindowClick(view,"Text 2", Gravity.BOTTOM);
    }


    public void onShowPopupWindowClick(View view, String text, int gravity) {

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.custom_popup_layout, null);
        TextView tvPopUp = popupView.findViewById(R.id.tv_popup);
        RelativeLayout popupLayout = popupView.findViewById(R.id.popup_layout);
        tvPopUp.setText(text);

        //showing animation only for bottom popup
        if(gravity==Gravity.BOTTOM){
            popupLayout.setBackground(new ColorDrawable(Color.TRANSPARENT));
            popupView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.popup_animation));
        }

        int width = WindowManager.LayoutParams.WRAP_CONTENT;
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.showAtLocation(popupView, gravity, 0, 0);

        Handler handler = new Handler();
        handler.postDelayed(popupWindow::dismiss,2500);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA_SERVICE);

        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String[] perms) {
        ActivityCompat.requestPermissions(this, perms, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_REQUEST_CODE:

                if(grantResults.length>0){
                    boolean cameraAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted){
                     showCamera();
                    }
                    else {
                        Toast.makeText(this, "Permission Denied, You cannot access camera.", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
        }

    }
}