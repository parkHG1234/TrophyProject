package trophy.projetc2.Navigation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

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
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2016-12-02.
 */

public class TeamSearch_CustomList_Adapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private List<TeamSearch_CustomList_MyData> test1 = null;
    private ArrayList<TeamSearch_CustomList_MyData> arrData;
    ImageView Layout_Navigation_TeamSearch_ImageView_Emblem;
    public TeamSearch_CustomList_Adapter(Context c, ArrayList<TeamSearch_CustomList_MyData> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.test1 = new ArrayList<TeamSearch_CustomList_MyData>();
        this.test1.addAll(arr);
    }

    public int getCount() {
        return arrData.size();
    }

    public Object getItem(int position) {
        return arrData.get(position).getTeamName();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_navigation_teamsearch_customlist, parent, false);
        }

        TextView Layout_Navigation_TeamSearch_TextView_TeamName = (TextView) convertView.findViewById(R.id.Layout_Navigation_TeamSearch_TextView_TeamName);

        Layout_Navigation_TeamSearch_ImageView_Emblem = (ImageView)convertView.findViewById(R.id.Layout_Navigation_TeamSearch_ImageView_Emblem);
        Layout_Navigation_TeamSearch_TextView_TeamName.setText(arrData.get(position).getTeamName());
        try {
            String En_Profile = URLEncoder.encode(arrData.get(position).getEmblem(), "utf-8");
            if (arrData.get(position).getEmblem().equals(".")) {
                Glide.with(context).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .into(Layout_Navigation_TeamSearch_ImageView_Emblem);
            } else {
                Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Layout_Navigation_TeamSearch_ImageView_Emblem);
            }
        } catch (UnsupportedEncodingException e) {

        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,TeamSearch_Focus.class);
                intent.putExtra("TeamName", arrData.get(position).getTeamName());
                intent.putExtra("User_Pk",arrData.get(position).getPk());
                intent.putExtra("Team_Pk",arrData.get(position).getTeam_Pk());
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        arrData.clear();
        if (charText.length() == 0) {
            arrData.addAll(test1);
        }
        else
        {
            for (TeamSearch_CustomList_MyData wp : test1)
            {
                if (wp.getTeamName().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    arrData.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
