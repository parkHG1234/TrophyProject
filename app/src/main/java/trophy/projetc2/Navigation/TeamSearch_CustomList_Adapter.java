package trophy.projetc2.Navigation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
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
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2016-12-02.
 */

public class TeamSearch_CustomList_Adapter extends BaseAdapter{
    private Context context;
    private ArrayList<TeamSearch_CustomList_MyData> arrayList;
    private LayoutInflater inflater;
    private List<TeamSearch_CustomList_MyData> potionList = null;
    public TeamSearch_CustomList_Adapter(Context c, List<TeamSearch_CustomList_MyData> potionList) {
        this.context = c;
        this.potionList = potionList;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList = new ArrayList<TeamSearch_CustomList_MyData>();
        this.arrayList.addAll(potionList);
    }
    public class ViewHolder{
        ImageView Emblem;
        TextView TeamName;
    }
    public int getCount() {
        return potionList.size();
    }

    public TeamSearch_CustomList_MyData getItem(int position) {
        return potionList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View View, ViewGroup parent) {
        final ViewHolder holder;
        final TeamSearch_CustomList_MyData potion = potionList.get(position);
        if (View == null) {
            holder = new ViewHolder();
            View = inflater.inflate(R.layout.layout_navigation_teamsearch_customlist, null);
            holder.Emblem = (ImageView) View.findViewById(R.id.Layout_Navigation_TeamSearch_ImageView_Emblem);
            holder.TeamName = (TextView) View.findViewById(R.id.Layout_Navigation_TeamSearch_TextView_TeamName);
            View.setTag(holder);
        }
        else{
            holder = (ViewHolder)View.getTag();

        }
        holder.TeamName.setText(potion.getTeamName());
        Glide.with(context).load(R.drawable.emblem).into(holder.Emblem);
        // CheckBox check= (CheckBox)convertView.findViewById(R.id.Contest_Detail_Form_Player_CustomList_Check);

//        try {
//            String En_Profile = URLEncoder.encode(arrData.get(position).getProfile(), "utf-8");
//            if (arrData.get(position).getProfile().equals(".")) {
//                Glide.with(context).load(R.drawable.profile_basic_image).bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
//                        .into(Contest_Detail_Form_Player_CustomList_ProfileImage);
//            } else {
//                Glide.with(context).load("http://210.122.7.193:8080/Web_basket/imgs/Profile/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .skipMemoryCache(true)
//                        .into(Contest_Detail_Form_Player_CustomList_ProfileImage);
//            }
//        } catch (UnsupportedEncodingException e) {
//
//        }

        return View;
    }
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        potionList.clear();
        if(charText.length() == 0){
            potionList.addAll(arrayList);
        }
        else{
            for (TeamSearch_CustomList_MyData potion : arrayList) {
                String name = potion.getTeamName();
                if (name.toLowerCase().contains(charText)) {
                    potionList.add(potion);
                }
            }
        }
        notifyDataSetChanged();
    }

}
