package trophy.projetc2.Navigation;

import android.content.Context;
import android.util.Log;
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

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2016-12-02.
 */

public class Last_Contest_Detail_CustomList_Adapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Last_Contest_Detail_CustomList_MyData> arrData;
    private ImageView Layout_Last_Contest_detail_ImageView_first;
    private ImageView Layout_Last_Contest_detail_ImageView_second;
    private ImageView Layout_Last_Contest_detail_ImageView_third;

    public Last_Contest_Detail_CustomList_Adapter(Context c, ArrayList<Last_Contest_Detail_CustomList_MyData> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return arrData.size();
    }

    public Object getItem(int position) {
        return arrData.get(position).getAward();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_last_contest_detail_customlist, parent, false);
        }
        Layout_Last_Contest_detail_ImageView_first = (ImageView) convertView.findViewById(R.id.Layout_Last_Contest_detail_ImageView_first);
        Layout_Last_Contest_detail_ImageView_second = (ImageView) convertView.findViewById(R.id.Layout_Last_Contest_detail_ImageView_second);
        Layout_Last_Contest_detail_ImageView_third = (ImageView) convertView.findViewById(R.id.Layout_Last_Contest_detail_ImageView_third);
        try {
            String En_Profile = URLEncoder.encode(arrData.get(position).getImage(), "utf-8");
            String Image = arrData.get(position).getImage().toString();
            String[] Images = Image.split("/");
            for (int i = 0; i < Images.length; i++) {
                Log.i("Image",Images[i]);
                Log.i("ImageNum",String.valueOf(i%3));
                if (i % 3 == 0) {
                    if (Images[i].equals(".")) {
                        Glide.with(context).load(R.drawable.emblem)
                                .into(Layout_Last_Contest_detail_ImageView_first);
                    } else {
                        Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/last_contest/" + Images[i] + ".jpg")
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(Layout_Last_Contest_detail_ImageView_first);
                    }
                } else if (i % 3 == 1) {
                    if (Images[i].equals(".")) {
                        Glide.with(context).load(R.drawable.emblem)
                                .into(Layout_Last_Contest_detail_ImageView_second);
                    } else {
                        Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/last_contest/" + Images[i] + ".jpg")
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(Layout_Last_Contest_detail_ImageView_second);
                    }
                } else if (i % 3 == 2) {
                    if (Images[i].equals(".")) {
                        Glide.with(context).load(R.drawable.emblem)
                                .into(Layout_Last_Contest_detail_ImageView_third);
                    } else {
                        Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/last_contest/" + Images[i] + ".jpg")
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(Layout_Last_Contest_detail_ImageView_third);
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {

        }
        return convertView;
    }

}
