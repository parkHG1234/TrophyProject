package trophy.projetc2.Main;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-03-24.
 */

public class MainActivity_VierPage1 extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.layout_main_viewpage1, container, false);
        ImageView Main_ViewPage1_Image = (ImageView)rootView.findViewById(R.id.Main_ViewPage1_Image);

        //광고 이미지 1
        Glide.with(getContext()).load("http://210.122.7.193:8080/Trophy_img/slidemenu/1.jpg").into(Main_ViewPage1_Image);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //이동 경로


            }
        });
        return rootView;
    }
}