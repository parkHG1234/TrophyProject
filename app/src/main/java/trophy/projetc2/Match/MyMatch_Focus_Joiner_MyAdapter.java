package trophy.projetc2.Match;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.Navigation.TeamFocus;
import trophy.projetc2.Navigation.TeamSearch_Focus;
import trophy.projetc2.R;

import static trophy.projetc2.Match.MyMatch_Focus.MatchDate_All;
import static trophy.projetc2.Match.MyMatch_Focus.Match_Focus_ImageView_OpponentEmblem;
import static trophy.projetc2.Match.MyMatch_Focus.Match_Focus_ImageView_Status;
import static trophy.projetc2.Match.MyMatch_Focus.Match_Focus_TextView_OpponentTeamName;
import static trophy.projetc2.Match.MyMatch_Focus.MyMatch_Focus_Joined_ImageView_Phone;
import static trophy.projetc2.Match.MyMatch_Focus.MyMatch_Focus_LinerLayout_Joining;
import static trophy.projetc2.Match.MyMatch_Focus.OtherTeam_Phone;
import static trophy.projetc2.Match.MyMatch_Focus.TeamName;
import static trophy.projetc2.Match.MyMatch_Focus.away;
import static trophy.projetc2.Match.MyMatch_Focus.vs;


/**
 * Created by 박효근 on 2017-04-18.
 */

public class MyMatch_Focus_Joiner_MyAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MyMatch_Focus_Joiner_MyData> arrData;
    ImageView MyMatch_Focus_Joiner_CustomList_ImageView_Emblem;
    TextView MyMatch_Focus_Joiner_CustomList_TextView_TeamName, MyMatch_Focus_Joiner_CustomList_TextView_Title;
    Button MyMatch_Focus_Joiner_CustomList_Button_Agree;
    String[][] parsedData_MyMatch_Focus_Join;
    public MyMatch_Focus_Joiner_MyAdapter(Context c, ArrayList<MyMatch_Focus_Joiner_MyData> arr) {
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
            convertView = inflater.inflate(R.layout.layout_navigation_mymatch_focus_joiner_customlist, parent, false);
        }
        MyMatch_Focus_Joiner_CustomList_ImageView_Emblem = (ImageView) convertView.findViewById(R.id.MyMatch_Focus_Joiner_CustomList_ImageView_Emblem);
        MyMatch_Focus_Joiner_CustomList_TextView_TeamName = (TextView) convertView.findViewById(R.id.MyMatch_Focus_Joiner_CustomList_TextView_TeamName);
        MyMatch_Focus_Joiner_CustomList_TextView_Title = (TextView) convertView.findViewById(R.id.MyMatch_Focus_Joiner_CustomList_TextView_Title);
        MyMatch_Focus_Joiner_CustomList_Button_Agree = (Button)convertView.findViewById(R.id.MyMatch_Focus_Joiner_CustomList_Button_Agree);
        if(arrData.get(position).getTeamName().equals(".")){
            MyMatch_Focus_Joiner_CustomList_TextView_TeamName.setText("삭제된 팀");
        }
        else{
            MyMatch_Focus_Joiner_CustomList_TextView_TeamName.setText(arrData.get(position).getTeamName());
        }
        MyMatch_Focus_Joiner_CustomList_TextView_Title.setText(arrData.get(position).getMemo());

        try {
            String En_Profile = URLEncoder.encode(arrData.get(position).getEmblem(), "utf-8");
            if (arrData.get(position).getEmblem().equals(".")) {
                Glide.with(context).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .into(MyMatch_Focus_Joiner_CustomList_ImageView_Emblem);
            } else {
                Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(MyMatch_Focus_Joiner_CustomList_ImageView_Emblem);
            }
        } catch (UnsupportedEncodingException e) {

        }
        MyMatch_Focus_Joiner_CustomList_Button_Agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrData.get(position).getTeamName().equals("."))
                {
                    Snackbar.make(view,"삭제된 팀입니다.",Snackbar.LENGTH_SHORT).show();
                }
                else
                {
                    LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.layout_customdialog_2choice, (ViewGroup) view.findViewById(R.id.Customdialog_2Choice_Root));
                    final TextView Customdialog_2Choice_Title = (TextView)layout.findViewById(R.id.Customdialog_2Choice_Title);
                    final ImageView Customdialog_2Choice_Back = (ImageView)layout.findViewById(R.id.Customdialog_2Choice_Back);
                    final TextView Customdialog_2Choice_Content = (TextView)layout.findViewById(R.id.Customdialog_2Choice_Content);
                    final Button Customdialog_2Choice_First = (Button)layout.findViewById(R.id.Customdialog_2Choice_First);
                    final Button Customdialog_2Choice_Second = (Button)layout.findViewById(R.id.Customdialog_2Choice_Second);
                    Customdialog_2Choice_Title.setText("교류전");
                    Customdialog_2Choice_Content.setText("수락시 교류전이 성사되며, 다른 신청팀은 자동 취소됩니다.\n성사된 교류전은 교류전결과에서 확인하실 수 있습니다.");
                    Customdialog_2Choice_First.setText("수 락");
                    Customdialog_2Choice_Second.setText("취 소");
                    final MaterialDialog TeamInfo_Dialog = new MaterialDialog(view.getContext());
                    TeamInfo_Dialog
                            .setContentView(layout)
                            .setCanceledOnTouchOutside(true);
                    TeamInfo_Dialog.show();
                    Customdialog_2Choice_Back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TeamInfo_Dialog.dismiss();
                        }
                    });
                    Customdialog_2Choice_First.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            HttpClient http_mymatch_focus_joiner = new HttpClient();
                            String result = http_mymatch_focus_joiner.HttpClient("Trophy_part1","MyMatch_Focus_Join.jsp",arrData.get(position).getMatch_Pk(),arrData.get(position).getMatch_Joiner_Pk(),arrData.get(position).getUser_Pk(),arrData.get(position).getOpponent_TeamPk());
                            parsedData_MyMatch_Focus_Join = jsonParserList_MyMatch_Focus_join(result);
                            if(parsedData_MyMatch_Focus_Join[0][0].equals("succed")){
                                HttpClient http_push = new HttpClient();
                                http_push.HttpClient("TodayBasket_manager","push.jsp", arrData.get(position).getUser_Pk(), TeamName+"팀 교류전 신청 수락! "+MatchDate_All[1]+"월 "+MatchDate_All[2]+"일 다른 신청은 자동 삭제됩니다");
                                AlarmHATT a = new AlarmHATT(context);
                                a.Alarm();
                                TeamInfo_Dialog.dismiss();
                                arrData.get(position).getActivity().finish();
                                arrData.get(position).getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                            }
                            else{
                                Snackbar.make(view,"잠시 후 다시 시도해주세요.", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Customdialog_2Choice_Second.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TeamInfo_Dialog.dismiss();
                        }
                    });
                }
            }
        });
        MyMatch_Focus_Joiner_CustomList_ImageView_Emblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,TeamSearch_Focus.class);
                intent.putExtra("User_Pk", arrData.get(position).getMy_User_Pk());
                intent.putExtra("TeamName", arrData.get(position).getTeamName());
                intent.putExtra("Team_Pk",arrData.get(position).getTeam_Pk());
                context.startActivity(intent);
                arrData.get(position).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        return convertView;
    }
    public String[][] jsonParserList_MyMatch_Focus_join(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for(int i = 0; i<jArr.length();i++){
                json = jArr.getJSONObject(i);
                for (int j=0;j<jsonName.length; j++){
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }
            return parseredData;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
    public class AlarmHATT {
        private Context context;
        public AlarmHATT(Context context) {
            this.context=context;
        }
        public void Alarm() {
            AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, Match_Write_Push.class);

            PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            //알람시간 calendar에 set해주기
           calendar.set(Integer.parseInt(MatchDate_All[0]), Integer.parseInt(MatchDate_All[1])-1, Integer.parseInt(MatchDate_All[2]), Integer.parseInt(MatchDate_All[3]), Integer.parseInt(MatchDate_All[4]), 0);
            Log.i("test", Integer.toString(Integer.parseInt(MatchDate_All[1])-1));
            //알람 예약
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        }
    }
}
