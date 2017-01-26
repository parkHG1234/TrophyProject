package trophy.projetc2.Navigation.Last_Contest_ImageView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import trophy.projetc2.CapturePhotoUtils;
import trophy.projetc2.R;

import static android.app.Activity.RESULT_OK;
import static trophy.projetc2.Navigation.Last_Contest_ViewPager.Line;
import static trophy.projetc2.Navigation.Last_Contest_ViewPager.Num;
import static trophy.projetc2.Navigation.Last_Contest_ViewPager.imgs;

/**
 * Created by 박효근 on 2017-01-10.
 */

public class Last_Contest_ImageView1 extends Fragment {
    private static String Pk;
    public Last_Contest_ImageView1(String Pk){
        this.Pk = Pk ;
    }
    private ImageView Last_Contest_ImageView;
    private ImageButton Last_Contest_ImageButton_Save;
    private CapturePhotoUtils CapturePhotoUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.layout_last_contest_imageview, container, false);
        Last_Contest_ImageView = (ImageView) rootView.findViewById(R.id.Last_Contest_ImageView);
        Last_Contest_ImageButton_Save = (ImageButton)rootView.findViewById(R.id.Last_Contest_ImageButton_Save);
        Glide.with(getContext()).load("http://210.122.7.193:8080/Trophy_img/last_contest/" +  Pk + "01.jpg")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(Last_Contest_ImageView);

        Last_Contest_ImageButton_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeCache(v,Pk+"01.jpg");
            }
        });
        return rootView;
    }
    static public void MakeCache(View v,String filename){

        String StoragePath =
                Environment.getExternalStorageDirectory().getAbsolutePath();
        String savePath = StoragePath + "/Trophy";
        File f = new File(savePath);
        if (!f.isDirectory())f.mkdirs();

        v.buildDrawingCache();
        Bitmap bitmap = v.getDrawingCache();
        FileOutputStream fos;
        try{
            Snackbar.make(v, "성공.", Snackbar.LENGTH_SHORT).show();
            Log.i("afdsfadsfadsfasd",savePath+"/"+filename);
            fos = new FileOutputStream(savePath+"/"+filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG,80,fos);
            Log.i("ImgSave", "이미지 디렉토리 및 파일생성 성공~");
        }catch (Exception e){
            Snackbar.make(v, "실패.", Snackbar.LENGTH_SHORT).show();
            Log.i("ImgSave", "이미지 디렉토리 및 파일생성 실패");
        }
    }
}