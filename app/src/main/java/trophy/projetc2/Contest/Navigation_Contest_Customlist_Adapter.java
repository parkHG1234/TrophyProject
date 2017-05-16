package trophy.projetc2.Contest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-03-24.
 */

public class Navigation_Contest_Customlist_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Navigation_Contest_Customlist_MyData> arrData;
    private LayoutInflater inflater;
    String Contest_Pk;
    public Navigation_Contest_Customlist_Adapter(Context c, ArrayList<Navigation_Contest_Customlist_MyData> arr) {
        this.context = c;
        this.arrData = arr;
        this.inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrData.size();
    }

    @Override
    public Object getItem(int position) {
        return arrData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.layout_navigation_contests_customlist, parent, false);

            String Da = " 일";

            Contest_Pk = arrData.get(position).getContest_Pk();
            String Contest_Title = arrData.get(position).getContest_Title();
            String Contest_Image = arrData.get(position).getContest_Image();
            String Contest_Date = arrData.get(position).getContestDate();
            String Contest_currentNum = arrData.get(position).getContest_currentNum();
            String Contest_maxNum = arrData.get(position).getContest_maxNum();
            String Contest_Payment = arrData.get(position).getContest_Payment();
            String Contest_RecruitFinishData = arrData.get(position).getRecruitFinishDate();
            String Contest_Place = arrData.get(position).getContest_Place();

            ImageView viewContest_Image = (ImageView)convertView.findViewById(R.id.Contest_logoImage);
            TextView viewContest_Title = (TextView)convertView.findViewById(R.id.contest_title);
            TextView viewContest_Date = (TextView)convertView.findViewById(R.id.contest_date);
            TextView viewContest_Num = (TextView)convertView.findViewById(R.id.contest_num);
            TextView viewContest_Place = (TextView) convertView.findViewById(R.id.Contest_place);

            TextView remainder = (TextView)convertView.findViewById(R.id.contest_list_remainder);
            TextView remainder1 = (TextView)convertView.findViewById(R.id.contest_list_remainder1);
            TextView remainder2 = (TextView)convertView.findViewById(R.id.contest_list_remainder2);
            //TextView viewContest_Point = (TextView)convertView.findViewById(R.id.contest_point);

            viewContest_Title.setText(Contest_Title);
            viewContest_Date.setText(Contest_Date);
            viewContest_Place.setText(Contest_Place);
            viewContest_Num.setText("참가팀 외부 / " + Contest_maxNum);
            Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/contest/"+Contest_Image+".jpg")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(viewContest_Image);

            //남은 일 계산
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy / MM / dd");
            try {
                Date date = dateFormat.parse(Contest_RecruitFinishData);
                Date currentDay = new Date();
                Long FinishTime = date.getTime()+86400000;
                if(currentDay.getTime() < FinishTime) {
                    long diff = FinishTime - currentDay.getTime();
                    long diffday = diff / (24 * 60 * 60 * 1000);
                    if(diffday > 7) {
                        diffday = diffday/7;
                        Da = " 주";
                    }

                    else{
                        Da = " 일";
                    }
                    Log.i("test123", diffday+Da);
                    remainder.setText(diffday+Da);
                }
                else {

                    remainder.setText("마감");
                    remainder1.setVisibility(View.GONE);
                    remainder2.setVisibility(View.GONE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("Pk",arrData.get(position).getContest_Pk());
                    Intent intent = new Intent(context, Contest_Detail.class);
                    intent.putExtra("Contest_Pk", arrData.get(position).getContest_Pk());
                    context.startActivity(intent);
                    arrData.get(position).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            });
        }

        return convertView;
    }
}
