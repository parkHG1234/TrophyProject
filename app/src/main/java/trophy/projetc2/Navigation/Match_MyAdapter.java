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

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-04-04.
 */

public class Match_MyAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Match_MyData> arrData;
    ImageView Match_CustomList_ImageView_Status, Match_CustomList_ImageView_Emblem;
    TextView Match_CustomList_TextView_Time, Match_CustomList_TextView_TeamName,
            Match_CustomList_TextView_Title, Match_CustomList_TextView_MatchTime, Match_CustomList_TextView_MatchPlace;

    public Match_MyAdapter(Context c, ArrayList<Match_MyData> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return arrData.size();
    }

    public Object getItem(int position) {
        return arrData.get(position).getMatch_Pk();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_navigation_match_customlist, parent, false);
        }
        Match_CustomList_ImageView_Status = (ImageView) convertView.findViewById(R.id.Match_CustomList_ImageView_Status);
        Match_CustomList_ImageView_Emblem = (ImageView) convertView.findViewById(R.id.Match_CustomList_ImageView_Emblem);
        Match_CustomList_TextView_Time = (TextView) convertView.findViewById(R.id.Match_CustomList_TextView_Time);
        Match_CustomList_TextView_TeamName = (TextView) convertView.findViewById(R.id.Match_CustomList_TextView_TeamName);
        Match_CustomList_TextView_Title = (TextView) convertView.findViewById(R.id.Match_CustomList_TextView_Title);
        Match_CustomList_TextView_MatchTime = (TextView) convertView.findViewById(R.id.Match_CustomList_TextView_MatchTime);
        Match_CustomList_TextView_MatchPlace = (TextView) convertView.findViewById(R.id.Match_CustomList_TextView_MatchPlace);

        Match_CustomList_TextView_Time.setText("10:20");
        Match_CustomList_TextView_TeamName.setText(arrData.get(position).getTeamName());
        Match_CustomList_TextView_Title.setText(arrData.get(position).getTitle());
        Match_CustomList_TextView_MatchTime.setText(arrData.get(position).getMatchTime());
        Match_CustomList_TextView_MatchPlace.setText(arrData.get(position).getMatchPlace());
        try {
            String En_Profile = URLEncoder.encode(arrData.get(position).getEmblem(), "utf-8");
            if (arrData.get(position).getEmblem().equals(".")) {
                Glide.with(context).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .into(Match_CustomList_ImageView_Emblem);
            } else {
                Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Match_CustomList_ImageView_Emblem);
            }
        } catch (UnsupportedEncodingException e) {

        }
        if(arrData.get(position).getStatus().equals("recruiting")){
            Match_CustomList_ImageView_Status.setImageResource(R.drawable.recruiting);
        }
        else if(arrData.get(position).getStatus().equals("deadline")){
            Match_CustomList_ImageView_Status.setImageResource(R.drawable.deadline);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(context, Match_Focus.class);
                intent1.putExtra("Match_Pk", arrData.get(position).getMatch_Pk());
                intent1.putExtra("User_Pk", arrData.get(position).getUser_Pk());
                context.startActivity(intent1);
                arrData.get(position).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        return convertView;
    }

}
