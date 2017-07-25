package trophy.projetc2.Score;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-06-28.
 */

public class Score_MyAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Score_MyData> arrData;
    String strCurYear, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime,strCurAll;
    TextView Score_Customlist_MatchDate, Score_Customlist_Place;
    ImageView Score_Customlist_HomeEmblem, Score_Customlist_AwayEmblem;
    TextView Score_Customlist_HomeTeamName, Score_Customlist_HomeScore, Score_Customlist_AwayScore, Score_Customlist_AwayTeamName;
    TextView Score_Customlist_Status, Score_Customlist_MatchTime;
    String GameStatus="";
    public Score_MyAdapter(Context c, ArrayList<Score_MyData> arr) {
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
            convertView = inflater.inflate(R.layout.layout_navigation_score_customlist, parent, false);
        }
        Score_Customlist_HomeEmblem = (ImageView) convertView.findViewById(R.id.Score_Customlist_HomeEmblem);
        Score_Customlist_AwayEmblem = (ImageView) convertView.findViewById(R.id.Score_Customlist_AwayEmblem);
        Score_Customlist_MatchDate = (TextView)convertView.findViewById(R.id.Score_Customlist_MatchDate);
        Score_Customlist_Place = (TextView)convertView.findViewById(R.id.Score_Customlist_Place);
        Score_Customlist_HomeTeamName = (TextView)convertView.findViewById(R.id.Score_Customlist_HomeTeamName);
        Score_Customlist_AwayTeamName = (TextView)convertView.findViewById(R.id.Score_Customlist_AwayTeamName);
        Score_Customlist_HomeScore = (TextView)convertView.findViewById(R.id.Score_Customlist_HomeScore);
        Score_Customlist_AwayScore = (TextView)convertView.findViewById(R.id.Score_Customlist_AwayScore);
        Score_Customlist_Status = (TextView)convertView.findViewById(R.id.Score_Customlist_Status);
        Score_Customlist_MatchTime = (TextView)convertView.findViewById(R.id.Score_Customlist_MatchTime);

       //Match_CustomList_TextView_Title.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/BMJUA_ttf.ttf"));

        //시간 분할
        String str = arrData.get(position).getMatch_Date();
        String[] data = str.split(":::");
        String str1 = data[1];
        String[] data1 = str1.split(" / ");
        if(Integer.parseInt(data1[0]) < 10){
            Score_Customlist_MatchDate.setText(data[0] + " / " + data1[0].charAt(1) + " / " +data1[1]);
        }
        else{
            Score_Customlist_MatchDate.setText(data[0] + " / " + data[1]);
        }
        Score_Customlist_Place.setText(arrData.get(position).getMatch_Place());

        //홈팀 정보
        try {
            String En_Profile = URLEncoder.encode(arrData.get(position).getHome_Emblem(), "utf-8");
            if (En_Profile.equals(".")) {
                Glide.with(context).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .into(Score_Customlist_HomeEmblem);
            } else {
                Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Score_Customlist_HomeEmblem);
            }
        } catch (UnsupportedEncodingException e) {

        }
        if(arrData.get(position).getHome_TeamName().equals(".")){
            Score_Customlist_HomeTeamName.setText("삭제된 팀");
        }
        else{
            Score_Customlist_HomeTeamName.setText(arrData.get(position).getHome_TeamName());
        }
        //어웨이팀 정보
        try {
            String En_Profile = URLEncoder.encode(arrData.get(position).getAway_Emblem(), "utf-8");
            if (En_Profile.equals(".")) {
                Glide.with(context).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .into(Score_Customlist_AwayEmblem);
            } else {
                Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Score_Customlist_AwayEmblem);
            }
        } catch (UnsupportedEncodingException e) {

        }
        if(arrData.get(position).getAway_TeamName().equals(".")){
            Score_Customlist_AwayTeamName.setText("삭제된 팀");
        }
        else{
            Score_Customlist_AwayTeamName.setText(arrData.get(position).getAway_TeamName());
        }
        Score_Customlist_MatchTime.setText(time_changestr(arrData.get(position).getStartTime()) + " ~ "+time_changestr(arrData.get(position).getFinishTime()));

        if(arrData.get(position).getGame_Status().equals("Before")){
            Score_Customlist_Status.setText("경기전");
            Score_Customlist_HomeScore.setVisibility(View.INVISIBLE);
            Score_Customlist_AwayScore.setVisibility(View.INVISIBLE);
        }
        else if(arrData.get(position).getGame_Status().equals("Gameing")){
            Score_Customlist_Status.setText("경기중");
            Score_Customlist_HomeScore.setVisibility(View.INVISIBLE);
            Score_Customlist_AwayScore.setVisibility(View.INVISIBLE);
        }
        else{
            if(arrData.get(position).getStatus().equals("Not_Insert")){
                Score_Customlist_Status.setText("결과 미입력");
                Score_Customlist_HomeScore.setVisibility(View.INVISIBLE);
                Score_Customlist_AwayScore.setVisibility(View.INVISIBLE);
            }
            else if(arrData.get(position).getStatus().equals("Home_Insert")){
                Score_Customlist_Status.setText("결과 확인중");
                Score_Customlist_HomeScore.setVisibility(View.VISIBLE);
                Score_Customlist_AwayScore.setVisibility(View.VISIBLE);
                Score_Customlist_HomeScore.setText(arrData.get(position).getGame1_home());
                Score_Customlist_AwayScore.setText(arrData.get(position).getGame1_away());
            }
            else{
                Score_Customlist_Status.setText("경기종료");
                Score_Customlist_HomeScore.setVisibility(View.VISIBLE);
                Score_Customlist_AwayScore.setVisibility(View.VISIBLE);
                Score_Customlist_HomeScore.setText(arrData.get(position).getGame1_home());
                Score_Customlist_AwayScore.setText(arrData.get(position).getGame1_away());
            }
        }

//        if(data[0].equals(strCurToday)){
//
//        }
//        else{
//            String str1 = data[0];
//            String[] data1 = str1.split(" / ");
//            Match_CustomList_TextView_Time.setText(data1[1] + " / " + data1[2]);
//        }

//        if(arrData.get(position).getTeamName().equals(".")){
//            Match_CustomList_TextView_TeamName.setText("삭제된 팀");
//        }
//        else{
//            Match_CustomList_TextView_TeamName.setText(arrData.get(position).getTeamName());
//        }
//        Match_CustomList_TextView_Title.setText(arrData.get(position).getTitle());
//        Match_CustomList_TextView_MatchTime.setText(time_changestr(arrData.get(position).getStartTime()) + " ~ "+time_changestr(arrData.get(position).getFinishTime()));
//        Match_CustomList_TextView_MatchPlace.setText(arrData.get(position).getMatchPlace());
//        try {
//            String En_Profile = URLEncoder.encode(arrData.get(position).getEmblem(), "utf-8");
//            if (arrData.get(position).getEmblem().equals(".")) {
//                Glide.with(context).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
//                        .into(Match_CustomList_ImageView_Emblem);
//            } else {
//                Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
//                        .diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .skipMemoryCache(true)
//                        .into(Match_CustomList_ImageView_Emblem);
//            }
//        } catch (UnsupportedEncodingException e) {
//
//        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(context, Score_Focus.class);
                intent1.putExtra("Match_Pk", arrData.get(position).getMatch_Pk());
                intent1.putExtra("User_Pk", arrData.get(position).getUser_Pk());
                intent1.putExtra("GameStatus", arrData.get(position).getGame_Status());
                context.startActivity(intent1);
                arrData.get(position).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        return convertView;
    }
    public void currentTime(){
        long now = System.currentTimeMillis();
// 현재 시간을 저장 한다.
        Date date = new Date(now);
// 시간 포맷 지정
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy / MM / dd");
        SimpleDateFormat CurTimeFormat = new SimpleDateFormat("HH : mm");
        SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd");
        SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH");
        SimpleDateFormat CurMinuteFormat = new SimpleDateFormat("mm");
        SimpleDateFormat ALLFormat = new SimpleDateFormat("yyyyMMddHHmm");
// 지정된 포맷으로 String 타입 리턴
        strCurToday = CurDateFormat.format(date);
        strCurTime = CurTimeFormat.format(date);
        strCurYear = CurYearFormat.format(date);
        strCurYear = CurYearFormat.format(date);
        strCurMonth = CurMonthFormat.format(date);
        strCurDay = CurDayFormat.format(date);
        strCurHour = CurHourFormat.format(date);
        strCurMinute = CurMinuteFormat.format(date);
        strCurAll = ALLFormat.format(date);
    }
    public String time_changestr(String time){
        String str = time;
        String[] data = str.split(":");
        if(Integer.parseInt(data[0])>12){
            return "오후 " +Integer.toString(Integer.parseInt(data[0])-12)+"시 "+Integer.parseInt(data[1])+"분";
        }
        else{
            return "오전 " +Integer.toString(Integer.parseInt(data[0]))+"시 "+Integer.parseInt(data[1])+"분";
        }
    }
}

