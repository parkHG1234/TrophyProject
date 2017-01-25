package trophy.projetc2.Navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import trophy.projetc2.R;

import static trophy.projetc2.Navigation.Last_Contest_ViewPager.Line;
import static trophy.projetc2.Navigation.Last_Contest_ViewPager.Num;
import static trophy.projetc2.Navigation.Last_Contest_ViewPager.imgs;

/**
 * Created by 박효근 on 2017-01-10.
 */

public class Last_Contest_ImageView2 extends Fragment {

    ImageView Last_Contest_ImageView2;
    private String num;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.layout_last_contest_imageview2, container, false);
        Last_Contest_ImageView2 = (ImageView) rootView.findViewById(R.id.Last_Contest_ImageView2);
        Glide.with(getContext()).load("http://210.122.7.193:8080/Trophy_img/last_contest/" +  imgs[(Integer.parseInt(Line)*3)+Integer.parseInt(Num)] + ".jpg")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(Last_Contest_ImageView2);

        return rootView;
    }
}