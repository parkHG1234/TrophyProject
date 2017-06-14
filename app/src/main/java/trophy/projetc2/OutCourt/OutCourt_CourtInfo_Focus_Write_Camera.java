package trophy.projetc2.OutCourt;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.security.Policy;
import java.text.SimpleDateFormat;
import java.util.Date;

import trophy.projetc2.Manifest;
import trophy.projetc2.R;

import static android.Manifest.permission.CAMERA;

/**
 * Created by 박효근 on 2017-06-12.
 */

public class OutCourt_CourtInfo_Focus_Write_Camera extends AppCompatActivity implements SurfaceHolder.Callback {

    @SuppressWarnings("deprecation")
    View view;
    android.hardware.Camera camera;
    SurfaceView Camera_SurfaceView;
    SurfaceHolder surfaceHolder;
    Button Camera_Button;
    ImageView NewsFeed_Camera_Image;
    private static String ImageFile, ImageURL;
    static int rout= 0;
    @SuppressWarnings("deprecation")
    android.hardware.Camera.PictureCallback jpegCallback;

    final int MY_PERMISSON_REQUEST_CODE = 100;
    int APIVersion = Build.VERSION.SDK_INT;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_outcourt_courtinfo_focus_write_camera);


        Camera_Button = (Button) findViewById(R.id.Camera_Button);
        Camera_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view = v;
                camera.takePicture(null, null, jpegCallback);

            }
        });
        getWindow().setFormat(PixelFormat.UNKNOWN);
        Camera_SurfaceView = (SurfaceView) findViewById(R.id.Camera_SurfaceView);
        surfaceHolder = Camera_SurfaceView.getHolder();
        surfaceHolder.addCallback(this);

        jpegCallback = new android.hardware.Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, android.hardware.Camera camera) {
                File pictureFile = getOutputMediaFile();

                try {
                    if (pictureFile == null) {
                        Toast.makeText(getApplicationContext(), "Error camera image saving", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();
                    Intent IntentURL = getIntent();
                    IntentURL.putExtra("ImageURL", ImageURL);
                    IntentURL.putExtra("ImageFile", ImageFile);

                    setResult(RESULT_OK, IntentURL);
                    finish();
                } catch (Exception e) {
                    Log.e("사진저장", "사진실패", e);
                }
            }
        };
    }

    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            return;
        }
        try {
            camera.stopPreview();
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {
        }
    }

//    private String getPath(Uri uri)
//    {
//        String[] projection = { MediaStore.Images.Media.DATA };
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }


    private static File getOutputMediaFile() {
        //SD 카드가 마운트 되어있는지 먼저 확인
        // Environment.getExternalStorageState() 로 마운트 상태 확인 가능합니다
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "TodayBasket");

        // 없는 경로라면 따로 생성
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCamera", "failed to create directory");
                return null;
            }
        }

        // 파일명을 적당히 생성, 여기선 시간으로 파일명 중복을 피한다
        ImageFile = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + ImageFile + ".jpg");
        ImageURL = String.valueOf(mediaStorageDir.getPath() + File.separator + "IMG_" + ImageFile + ".jpg");


        return mediaFile;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(APIVersion >= android.os.Build.VERSION_CODES.M){
            if(checkCAMERAPermission()){
                camera = android.hardware.Camera.open();
                camera.stopPreview();
                int rotation = getWindowManager().getDefaultDisplay().getRotation();

                int degrees = 0;

                switch (rotation) {

                    case Surface.ROTATION_0: degrees = 0; break;

                    case Surface.ROTATION_90: degrees = 90; break;

                    case Surface.ROTATION_180: degrees = 180; break;

                    case Surface.ROTATION_270: degrees = 270; break;

                }

                int result  = (90 - degrees + 360) % 360;
                camera.setDisplayOrientation(result);
                try {
                    camera.setPreviewDisplay(surfaceHolder);
                    camera.startPreview();
                } catch (Exception e) {
                    System.err.println(e);
                    return;
                }
            }
            else{
                ActivityCompat.requestPermissions(OutCourt_CourtInfo_Focus_Write_Camera.this,new String[]{CAMERA},MY_PERMISSON_REQUEST_CODE);
            }
        }else{
            camera = android.hardware.Camera.open();
            camera.stopPreview();
            int rotation = getWindowManager().getDefaultDisplay().getRotation();

            int degrees = 0;

            switch (rotation) {

                case Surface.ROTATION_0: degrees = 0; break;

                case Surface.ROTATION_90: degrees = 90; break;

                case Surface.ROTATION_180: degrees = 180; break;

                case Surface.ROTATION_270: degrees = 270; break;

            }

            int result  = (90 - degrees + 360) % 360;
            camera.setDisplayOrientation(result);
           // camera.setDisplayOrientation(90);
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
            } catch (Exception e) {
                System.err.println(e);
                return;
            }
        }

    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        refreshCamera();
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {

            case Surface.ROTATION_0: degrees = 0; break;

            case Surface.ROTATION_90: degrees = 90; break;

            case Surface.ROTATION_180: degrees = 180; break;

            case Surface.ROTATION_270: degrees = 270; break;

        }
        rout  = (90 - degrees + 360) % 360;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    public void camera_ImageView(ImageView NewsFeed_Camera_Image) {
        this.NewsFeed_Camera_Image = NewsFeed_Camera_Image;
    }
    private boolean checkCAMERAPermission(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return  result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch ((requestCode)){
            case MY_PERMISSON_REQUEST_CODE:
                if(grantResults.length>0){
                    boolean cameraAccepted = (grantResults[0] == PackageManager.PERMISSION_GRANTED);
                    if(cameraAccepted){
                        camera = android.hardware.Camera.open();
                        camera.stopPreview();


                        int rotation = getWindowManager().getDefaultDisplay().getRotation();

                        int degrees = 0;

                        switch (rotation) {

                            case Surface.ROTATION_0: degrees = 0; break;

                            case Surface.ROTATION_90: degrees = 90; break;

                            case Surface.ROTATION_180: degrees = 180; break;

                            case Surface.ROTATION_270: degrees = 270; break;

                        }

                        int result  = (90 - degrees + 360) % 360;
                        camera.setDisplayOrientation(result);

                        try {
                            camera.setPreviewDisplay(surfaceHolder);
                            camera.startPreview();
                        } catch (Exception e) {
                            System.err.println(e);
                            return;
                        }
                    }
                    else{
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if(shouldShowRequestPermissionRationale(CAMERA)){
                                showMessagePermission("카메라 권한 허가를 요청합니다.", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                            requestPermissions(new String[]{CAMERA}, MY_PERMISSON_REQUEST_CODE);
                                        }
                                    }
                                });
                            }

                        }
                    }
                }
        }
    }
    private void showMessagePermission(String message, DialogInterface.OnClickListener okListener){
        new android.support.v7.app.AlertDialog.Builder(OutCourt_CourtInfo_Focus_Write_Camera.this)
                .setMessage(message)
                .setPositiveButton("허용", okListener)
                .setNegativeButton("거부", null)
                .create()
                .show();
    }
}