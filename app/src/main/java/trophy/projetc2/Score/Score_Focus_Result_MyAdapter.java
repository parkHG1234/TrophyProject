package trophy.projetc2.Score;

import android.content.Context;
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

import static trophy.projetc2.R.id.Score_Customlist_HomeEmblem;

/**
 * Created by 박효근 on 2017-07-10.
 */

public class Score_Focus_Result_MyAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Score_Focus_Result_MyData> arrData;
    String strCurYear, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime,strCurAll;
    TextView Score_Focus_Result_Customlist_HomeTeamName, Score_Focus_Result_Customlist_AwayTeamName;
    ImageView Score_Focus_Result_Customlist_HomeEmblem, Score_Focus_Result_Customlist_AwayEmblem;
    TextView Score_Focus_Result_Customlist_HomeScore, Score_Focus_Result_Customlist_AwayScore;
    public Score_Focus_Result_MyAdapter(Context c, ArrayList<Score_Focus_Result_MyData> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return arrData.size();
    }

    public Object getItem(int position) {
        return arrData.get(position).getHome_TeamName();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_navigation_score_focus_result_customlist, parent, false);
        }
        Score_Focus_Result_Customlist_HomeEmblem = (ImageView) convertView.findViewById(R.id.Score_Focus_Result_Customlist_HomeEmblem);
        Score_Focus_Result_Customlist_AwayEmblem = (ImageView) convertView.findViewById(R.id.Score_Focus_Result_Customlist_AwayEmblem);
        Score_Focus_Result_Customlist_HomeTeamName = (TextView)convertView.findViewById(R.id.Score_Focus_Result_Customlist_HomeTeamName);
        Score_Focus_Result_Customlist_AwayTeamName = (TextView)convertView.findViewById(R.id.Score_Focus_Result_Customlist_AwayTeamName);
        Score_Focus_Result_Customlist_HomeScore = (TextView)convertView.findViewById(R.id.Score_Focus_Result_Customlist_HomeScore);
        Score_Focus_Result_Customlist_AwayScore = (TextView)convertView.findViewById(R.id.Score_Focus_Result_Customlist_AwayScore);

        //홈팀 정보
        try {
            String En_Profile = URLEncoder.encode(arrData.get(position).getHome_Emblem(), "utf-8");
            if (En_Profile.equals(".")) {
                Glide.with(context).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .into(Score_Focus_Result_Customlist_HomeEmblem);
            } else {
                Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Score_Focus_Result_Customlist_HomeEmblem);
            }
        } catch (UnsupportedEncodingException e) {

        }
        Score_Focus_Result_Customlist_HomeTeamName.setText(arrData.get(position).getHome_TeamName());
        //어웨이팀 정보
        try {
            String En_Profile = URLEncoder.encode(arrData.get(position).getAway_Emblem(), "utf-8");
            if (En_Profile.equals(".")) {
                Glide.with(context).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .into(Score_Focus_Result_Customlist_AwayEmblem);
            } else {
                Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Score_Focus_Result_Customlist_AwayEmblem);
            }
        } catch (UnsupportedEncodingException e) {

        }
        Score_Focus_Result_Customlist_AwayTeamName.setText(arrData.get(position).getAway_TeamName());

        Score_Focus_Result_Customlist_HomeScore.setText(arrData.get(position).getHome_Score());
        Score_Focus_Result_Customlist_AwayScore.setText(arrData.get(position).getAway_Score());
        if(Integer.parseInt(arrData.get(position).getHome_Score()) > Integer.parseInt(arrData.get(position).getAway_Score())){
            Score_Focus_Result_Customlist_HomeScore.setTextColor(convertView.getResources().getColor(R.color.main1color));
        }
        if(Integer.parseInt(arrData.get(position).getHome_Score()) < Integer.parseInt(arrData.get(position).getAway_Score())){
            Score_Focus_Result_Customlist_AwayScore.setTextColor(convertView.getResources().getColor(R.color.main1color));
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return convertView;
    }
}

