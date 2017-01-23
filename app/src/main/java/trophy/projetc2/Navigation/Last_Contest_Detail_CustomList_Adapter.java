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
            int cnt = Integer.parseInt(arrData.get(position).getCnt().toString());
            String[] Images = Image.split("/");
                    if (Images[cnt].equals(".")) {
                        Glide.with(context).load(R.drawable.emblem)
                                .into(Layout_Last_Contest_detail_ImageView_first);
                    } else {
                        Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/last_contest/" + Images[cnt] + ".jpg")
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(Layout_Last_Contest_detail_ImageView_first);
                    }
                    if (Images[cnt+1].equals(".")) {
                        Glide.with(context).load(R.drawable.emblem)
                                .into(Layout_Last_Contest_detail_ImageView_second);
                    } else {
                        Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/last_contest/" + Images[cnt+1] + ".jpg")
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(Layout_Last_Contest_detail_ImageView_second);
                    }
                    if (Images[cnt+2].equals(".")) {
                        Glide.with(context).load(R.drawable.emblem)
                                .into(Layout_Last_Contest_detail_ImageView_third);
                    } else {
                        Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/last_contest/" + Images[cnt+2] + ".jpg")
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(Layout_Last_Contest_detail_ImageView_third);
                    }


        } catch (UnsupportedEncodingException e) {

        }
        return convertView;
    }

}
