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
 * Created by 박효근 on 2017-06-01.
 */

public class MainActivity_SlideMenu1_ViewPage3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.layout_main_viewpage2, container, false);
        ImageView Main_ViewPage1_Image = (ImageView)rootView.findViewById(R.id.Main_ViewPage2_Image);
        //광고 이미지 3
        Glide.with(getContext()).load(R.drawable.guide3)
                .into(Main_ViewPage1_Image);
        return rootView;
    }

}
