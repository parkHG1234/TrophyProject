package trophy.projetc2.Navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-01-14.
 */

public class TeamManager_PlayerManager_Customlist_MyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<TeamManager_PlayerManager_Customlist_MyData> arrData;
    private LayoutInflater inflater;
    String ProfileUrl;
    public TeamManager_PlayerManager_Customlist_MyAdapter(Context c, ArrayList<TeamManager_PlayerManager_Customlist_MyData> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return arrData.size();
    }

    public Object getItem(int position) {
        return arrData.get(position).getFirst_Pk();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_navigation_teammanager_playermanager_joiner_customlist, parent, false);
        }

        ImageView FirstProfile = (ImageView) convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_ImageView_FirstProfile);
        TextView FirstName = (TextView) convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_TextView_FirstName);
        ImageView SecondProfile = (ImageView) convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_ImageView_SecondProfile);
        TextView SecondName = (TextView) convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_TextView_SecondName);
        ImageView ThirdProfile = (ImageView) convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_ImageView_ThirdProfile);
        TextView ThirdName = (TextView) convertView.findViewById(R.id.TeamManager_PlayerManager_Joiner_CustomList_TextView_ThirdName);

//        Contest_Detail_Form_Player_CustomList_ProfileImage = (ImageView)convertView.findViewById(R.id.Contest_Detail_Form_Player_CustomList_ProfileImage);
//        Name.setText(arrData.get(position).getName());
//        Duty.setText(arrData.get(position).getDuty());
//        Age.setText(ChangeAge(arrData.get(position).getBirth()));
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

        return convertView;
    }
}
