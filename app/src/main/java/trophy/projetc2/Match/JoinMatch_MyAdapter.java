package trophy.projetc2.Match;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-05-25.
 */

public class JoinMatch_MyAdapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<JoinMatch_MyData> arrData;
    String strCurYear, strCurMonth, strCurDay, strCurHour,strCurMinute, strCurToday, strCurTime;
    ImageView JoinMatch_CustomList_ImageView_Emblem, JoinMatch_CustomList_ImageView_Status;
    TextView JoinMatch_CustomList_TextView_Time, JoinMatch_CustomList_TextView_TeamName,
            JoinMatch_CustomList_TextView_Title, JoinMatch_CustomList_TextView_MatchTime, JoinMatch_CustomList_TextView_MatchPlace;

    public JoinMatch_MyAdapter(Context c, ArrayList<JoinMatch_MyData> arr) {
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
            convertView = inflater.inflate(R.layout.layout_navigation_joinmatch_customlist, parent, false);
        }
        JoinMatch_CustomList_ImageView_Status = (ImageView) convertView.findViewById(R.id.JoinMatch_CustomList_ImageView_Status);
        JoinMatch_CustomList_ImageView_Emblem = (ImageView) convertView.findViewById(R.id.JoinMatch_CustomList_ImageView_Emblem);
        JoinMatch_CustomList_TextView_Time = (TextView) convertView.findViewById(R.id.JoinMatch_CustomList_TextView_Time);
        JoinMatch_CustomList_TextView_TeamName = (TextView) convertView.findViewById(R.id.JoinMatch_CustomList_TextView_TeamName);
        JoinMatch_CustomList_TextView_Title = (TextView) convertView.findViewById(R.id.JoinMatch_CustomList_TextView_Title);
        JoinMatch_CustomList_TextView_MatchTime = (TextView) convertView.findViewById(R.id.JoinMatch_CustomList_TextView_MatchTime);
        JoinMatch_CustomList_TextView_MatchPlace = (TextView) convertView.findViewById(R.id.JoinMatch_CustomList_TextView_MatchPlace);

        String str = arrData.get(position).getTime();
        String[] data = str.split(":::");
        currentTime();
        if(data[0].equals(strCurToday)){
            JoinMatch_CustomList_TextView_Time.setText(data[1]);
        }
        else{
            String str1 = data[0];
            String[] data1 = str1.split(" / ");
            JoinMatch_CustomList_TextView_Time.setText(data1[1] + " / " + data1[2]);
        }

        JoinMatch_CustomList_TextView_TeamName.setText(arrData.get(position).getTeamName());
        JoinMatch_CustomList_TextView_Title.setText(arrData.get(position).getTitle());
        JoinMatch_CustomList_TextView_MatchTime.setText(arrData.get(position).getMatchTime());
        JoinMatch_CustomList_TextView_MatchPlace.setText(arrData.get(position).getMatchPlace());

        try {
            String En_Profile = URLEncoder.encode(arrData.get(position).getEmblem(), "utf-8");
            if (arrData.get(position).getEmblem().equals(".")) {
                Glide.with(context).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .into(JoinMatch_CustomList_ImageView_Emblem);
            } else {
                Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(JoinMatch_CustomList_ImageView_Emblem);
            }
        } catch (UnsupportedEncodingException e) {

        }
        if(arrData.get(position).getStatus().equals("Joining")){
            JoinMatch_CustomList_ImageView_Status.setImageResource(R.drawable.joinmatch_joining);
        }
        else if(arrData.get(position).getStatus().equals("refuse")){
            JoinMatch_CustomList_ImageView_Status.setImageResource(R.drawable.joinmatch_refuse);
        }
        else if(arrData.get(position).getStatus().equals("allow")){
            JoinMatch_CustomList_ImageView_Status.setImageResource(R.drawable.joinmatch_allow);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(context, JoinMatch_Focus.class);
                intent1.putExtra("Match_Pk", arrData.get(position).getMatch_Pk());
                intent1.putExtra("User_Pk", arrData.get(position).getUser_Pk());
                intent1.putExtra("Status", arrData.get(position).getStatus());
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
// 지정된 포맷으로 String 타입 리턴
        strCurToday = CurDateFormat.format(date);
        strCurTime = CurTimeFormat.format(date);
        strCurYear = CurYearFormat.format(date);
        strCurYear = CurYearFormat.format(date);
        strCurMonth = CurMonthFormat.format(date);
        strCurDay = CurDayFormat.format(date);
        strCurHour = CurHourFormat.format(date);
        strCurMinute = CurMinuteFormat.format(date);
    }
}
