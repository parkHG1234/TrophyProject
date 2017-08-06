package trophy.projetc2.Contest;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import trophy.projetc2.R;

/**
 * Created by KimIkJoong on 2016-11-08.
 */

public class Contests_Customlist_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Contests_Customlist_MyData> arrData;
    private LayoutInflater inflater;
    String Contest1_Pk;   String Contest2_Pk;
    public Contests_Customlist_Adapter(Context c, ArrayList<Contests_Customlist_MyData> arr) {
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
            convertView = inflater.inflate(R.layout.layout_main_contest_customlist, parent, false);

            String Da = " 일";
            //첫번째 대회 정보
            Contest1_Pk = arrData.get(position).getContest1_Pk();
            String Contest1_Title = arrData.get(position).getContest1_Title();
            String Contest1_Image = arrData.get(position).getContest1_Image();
            String Contest1_Date = arrData.get(position).getContest1_Date();
            String Contest1_currentNum = arrData.get(position).getContest1_currentNum();
            String Contest1_maxNum = arrData.get(position).getContest1_maxNum();
            String Contest1_RecruitFinishData = arrData.get(position).getContest1_RecruitFinishDate();
            String Contest1_Place = arrData.get(position).getContest1_Place();

            //두번째 대회 정보
            Contest2_Pk = arrData.get(position).getContest2_Pk();
            String Contest2_Title = arrData.get(position).getContest2_Title();
            String Contest2_Image = arrData.get(position).getContest2_Image();
            String Contest2_Date = arrData.get(position).getContest2_Date();
            String Contest2_currentNum = arrData.get(position).getContest2_currentNum();
            String Contest2_maxNum = arrData.get(position).getContest2_maxNum();
            String Contest2_RecruitFinishData = arrData.get(position).getContest2_RecruitFinishDate();
            String Contest2_Place = arrData.get(position).getContest2_Place();

            //첫번째 대회 레이아웃 연결
            ImageView Contest1_ImageView_Image = (ImageView)convertView.findViewById(R.id.Contest1_logoImage);
            TextView Contest1_TextView_Title = (TextView)convertView.findViewById(R.id.contest1_title);
            TextView Contest1_TextView_Date = (TextView)convertView.findViewById(R.id.contest1_date);
            TextView Contest1_TextView_Num = (TextView)convertView.findViewById(R.id.contest1_num);
            TextView Contest1_TextView_Place = (TextView) convertView.findViewById(R.id.Contest1_place);
            TextView Contest1_TextView_Remainder = (TextView)convertView.findViewById(R.id.contest1_list_remainder);
            TextView Contest1_TextView_Remainder1 = (TextView)convertView.findViewById(R.id.contest1_list_remainder1);
            TextView Contest1_TextView_Remainder2 = (TextView)convertView.findViewById(R.id.contest1_list_remainder2);

            //두번째 대회 레이아웃 연결
            ImageView Contest2_ImageView_Image = (ImageView)convertView.findViewById(R.id.Contest2_logoImage);
            TextView Contest2_TextView_Title = (TextView)convertView.findViewById(R.id.contest2_title);
            TextView Contest2_TextView_Date = (TextView)convertView.findViewById(R.id.contest2_date);
            TextView Contest2_TextView_Num = (TextView)convertView.findViewById(R.id.contest2_num);
            TextView Contest2_TextView_Place = (TextView) convertView.findViewById(R.id.Contest2_place);
            TextView Contest2_TextView_Remainder = (TextView)convertView.findViewById(R.id.contest2_list_remainder);
            TextView Contest2_TextView_Remainder1 = (TextView)convertView.findViewById(R.id.contest2_list_remainder1);
            TextView Contest2_TextView_Remainder2 = (TextView)convertView.findViewById(R.id.contest2_list_remainder2);

            //첫번째 대회 데이터 적용
            Contest1_TextView_Title.setText(Contest1_Title);
            Contest1_TextView_Date.setText(Contest1_Date);
            Contest1_TextView_Place.setText(Contest1_Place);
            Contest1_TextView_Num.setText("참가팀 외부 / " + Contest1_maxNum);
            Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/contest/"+Contest1_Image+".jpg")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(Contest1_ImageView_Image);

            //남은 일 계산
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy / MM / dd");
            try {
                Date date = dateFormat1.parse(Contest1_RecruitFinishData);
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
                    Contest1_TextView_Remainder.setText(diffday+Da);
                }
                else {

                    Contest1_TextView_Remainder.setText("마감");
                    Contest1_TextView_Remainder1.setVisibility(View.GONE);
                    Contest1_TextView_Remainder2.setVisibility(View.GONE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Contest1_ImageView_Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("Pk",arrData.get(position).getContest1_Pk());
                    Intent intent = new Intent(context, Contest_Detail.class);
                    intent.putExtra("Contest_Pk", arrData.get(position).getContest1_Pk());
                    context.startActivity(intent);
                    arrData.get(position).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            });
            //두번째 대회 데이터 적용
            Contest2_TextView_Title.setText(Contest2_Title);
            Contest2_TextView_Date.setText(Contest2_Date);
            Contest2_TextView_Place.setText(Contest2_Place);
            Contest2_TextView_Num.setText("참가팀 외부 / " + Contest2_maxNum);
            Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/contest/"+Contest2_Image+".jpg")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(Contest2_ImageView_Image);

            //남은 일 계산
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy / MM / dd");
            try {
                Date date = dateFormat2.parse(Contest2_RecruitFinishData);
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
                    Contest2_TextView_Remainder.setText(diffday+Da);
                }
                else {

                    Contest2_TextView_Remainder.setText("마감");
                    Contest2_TextView_Remainder1.setVisibility(View.GONE);
                    Contest2_TextView_Remainder2.setVisibility(View.GONE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Contest2_ImageView_Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("Pk",arrData.get(position).getContest2_Pk());
                    Intent intent = new Intent(context, Contest_Detail.class);
                    intent.putExtra("Contest_Pk", arrData.get(position).getContest2_Pk());
                    context.startActivity(intent);
                    arrData.get(position).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            });
        }

        return convertView;
    }
}
