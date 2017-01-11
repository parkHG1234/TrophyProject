package trophy.projetc2.Navigation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import trophy.projetc2.MainActivity;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2016-12-02.
 */

public class Last_Contest_CustomList_Adapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Last_Contest_CustomList_MyData> arrData;
    private TextView Layout_Last_Contest_TextView_ContestName;
    private ImageView Layout_Last_Contest_ImageView;
    public Last_Contest_CustomList_Adapter(Context c, ArrayList<Last_Contest_CustomList_MyData> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public int getCount() {
        return arrData.size();
    }

    public Object getItem(int position) {
        return arrData.get(position).getTitle();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_last_contest_customlist, parent, false);
        }
        Layout_Last_Contest_TextView_ContestName = (TextView) convertView.findViewById(R.id.Layout_Last_Contest_TextView_ContestName);
        Layout_Last_Contest_ImageView = (ImageView)convertView.findViewById(R.id.Layout_Last_Contest_ImageView);
        Layout_Last_Contest_TextView_ContestName.setText(arrData.get(position).getTitle());
        try {
            String En_Profile = URLEncoder.encode(arrData.get(position).getImage(), "utf-8");
            if (arrData.get(position).getImage().equals(".")) {
                Glide.with(context).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .into(Layout_Last_Contest_ImageView);
            } else {
                Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/contest/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Layout_Last_Contest_ImageView);
            }
        } catch (UnsupportedEncodingException e) {

        }
        return convertView;
    }

}
