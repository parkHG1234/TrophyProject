package trophy.projetc2.Match;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import trophy.projetc2.Contest.Contest_Detail;
import trophy.projetc2.R;
import trophy.projetc2.User.Login;

/**
 * Created by 박효근 on 2017-04-04.
 */

public class Match_MyAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Match_MyData> arrData;
    String strCurYear, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime,strCurAll;
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
        Match_CustomList_TextView_Title.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/BMJUA_ttf.ttf"));
        Match_CustomList_TextView_TeamName.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/BMJUA_ttf.ttf"));
        //시간 분할
        String str = arrData.get(position).getTime();
        String[] data = str.split(":::");
        currentTime();
        if(data[0].equals(strCurToday)){
            Match_CustomList_TextView_Time.setText(data[1]);
        }
        else{
            String str1 = data[0];
            String[] data1 = str1.split(" / ");
            Match_CustomList_TextView_Time.setText(data1[1] + " / " + data1[2]);
        }

        if(arrData.get(position).getTeamName().equals(".")){
            Match_CustomList_TextView_TeamName.setText("삭제된 팀");
        }
        else{
            Match_CustomList_TextView_TeamName.setText(arrData.get(position).getTeamName());
        }
        Match_CustomList_TextView_Title.setText(arrData.get(position).getTitle());
        Match_CustomList_TextView_MatchTime.setText(time_changestr(arrData.get(position).getStartTime()) + " ~ "+time_changestr(arrData.get(position).getFinishTime()));
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
        }else{
            Match_CustomList_ImageView_Status.setImageResource(R.drawable.deadline);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrData.get(position).getTeamName().equals(".")){
                    Snackbar.make(view,"삭제된 팀의 게시글입니다.",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    if(arrData.get(position).getUser_Pk().equals(".")){
                        Intent intent_login = new Intent(context, Login.class);
                        arrData.get(position).getActivity().startActivity(intent_login);
                        arrData.get(position).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                    }
                    else{
                        Intent intent1 = new Intent(context, Match_Focus.class);
                        intent1.putExtra("Match_Pk", arrData.get(position).getMatch_Pk());
                        intent1.putExtra("User_Pk", arrData.get(position).getUser_Pk());
                        intent1.putExtra("MyTeam_Pk", arrData.get(position).getMyTeam_Pk());
                        intent1.putExtra("Status", arrData.get(position).getStatus());
                        context.startActivity(intent1);
                        arrData.get(position).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                    }
                }
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
