package trophy.projetc2.Main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-03-24.
 */

public class MainActivity_VierPage3 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.layout_main_viewpage3, container, false);
        ImageView Main_ViewPage3_Image = (ImageView)rootView.findViewById(R.id.Main_ViewPage3_Image);

//        //광고 이미지 1
//        Glide.with(getContext()).load("http://210.122.7.193:8080/Trophy_img/slidemenu/3.jpg").diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(Main_ViewPage3_Image);
//        rootView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //이동 경로
//
//
//            }
//        });
        return rootView;
    }
}