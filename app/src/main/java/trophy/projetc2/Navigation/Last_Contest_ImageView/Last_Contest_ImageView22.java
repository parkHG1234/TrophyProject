package trophy.projetc2.Navigation.Last_Contest_ImageView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-01-10.
 */

public class Last_Contest_ImageView22 extends Fragment {
    private String Pk;

    public Last_Contest_ImageView22(String Pk) {
        this.Pk = Pk;
    }

    private ImageView Last_Contest_ImageView;
    private ImageButton Last_Contest_ImageButton_Save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.layout_last_contest_imageview, container, false);
        Last_Contest_ImageView = (ImageView) rootView.findViewById(R.id.Last_Contest_ImageView);
        Last_Contest_ImageButton_Save = (ImageButton)rootView.findViewById(R.id.Last_Contest_ImageButton_Save);
        Glide.with(getContext()).load("http://210.122.7.193:8080/Trophy_img/last_contest/" + Pk + "22.jpg")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(Last_Contest_ImageView);

        return rootView;
    }
}