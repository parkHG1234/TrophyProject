package trophy.projetc2.Navigation;

import android.content.Context;
import android.content.Intent;
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

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

import static trophy.projetc2.Navigation.MyMatch_Focus.Match_Focus_ImageView_Status;
import static trophy.projetc2.Navigation.MyMatch_Focus.MyMatch_Focus_Joined_ImageView_Emblem;
import static trophy.projetc2.Navigation.MyMatch_Focus.MyMatch_Focus_Joined_TextView_TeamName;
import static trophy.projetc2.Navigation.MyMatch_Focus.MyMatch_Focus_Joined_TextView_Title;
import static trophy.projetc2.Navigation.MyMatch_Focus.MyMatch_Focus_LinerLayout_Joined;
import static trophy.projetc2.Navigation.MyMatch_Focus.MyMatch_Focus_LinerLayout_Joining;


/**
 * Created by 박효근 on 2017-04-18.
 */

public class MyMatch_Focus_Joiner_MyAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<MyMatch_Focus_Joiner_MyData> arrData;
    ImageView MyMatch_Focus_Joiner_CustomList_ImageView_Emblem;
    TextView MyMatch_Focus_Joiner_CustomList_TextView_Time, MyMatch_Focus_Joiner_CustomList_TextView_TeamName, MyMatch_Focus_Joiner_CustomList_TextView_Title;
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
        MyMatch_Focus_Joiner_CustomList_TextView_Time = (TextView) convertView.findViewById(R.id.MyMatch_Focus_Joiner_CustomList_TextView_Time);
        MyMatch_Focus_Joiner_CustomList_TextView_TeamName = (TextView) convertView.findViewById(R.id.MyMatch_Focus_Joiner_CustomList_TextView_TeamName);
        MyMatch_Focus_Joiner_CustomList_TextView_Title = (TextView) convertView.findViewById(R.id.MyMatch_Focus_Joiner_CustomList_TextView_Title);
        MyMatch_Focus_Joiner_CustomList_Button_Agree = (Button)convertView.findViewById(R.id.MyMatch_Focus_Joiner_CustomList_Button_Agree);

        MyMatch_Focus_Joiner_CustomList_TextView_Time.setText("10:20");
        MyMatch_Focus_Joiner_CustomList_TextView_TeamName.setText(arrData.get(position).getTeamName());
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
                LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.layout_customdialog_1, (ViewGroup) view.findViewById(R.id.TeamInfo_Customdialog_1_Root));
                final TextView TeamInfo_Customdialog_1_Title = (TextView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Title);
                final ImageView TeamInfo_Customdialog_1_Back = (ImageView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Back);
                final TextView TeamInfo_Customdialog_1_Content = (TextView)layout.findViewById(R.id.TeamInfo_Customdialog_1_Content);
                final Button TeamInfo_Customdialog_1_Ok = (Button)layout.findViewById(R.id.TeamInfo_Customdialog_1_Ok);
                TeamInfo_Customdialog_1_Title.setText("시합 요청");
                TeamInfo_Customdialog_1_Content.setText("수락시 시합 요청이 마감되며, 다른 신청팀은 자동 취소됩니다.");
                final MaterialDialog TeamInfo_Dialog = new MaterialDialog(view.getContext());
                TeamInfo_Dialog
                        .setContentView(layout)
                        .setCanceledOnTouchOutside(true);
                TeamInfo_Dialog.show();
                TeamInfo_Customdialog_1_Back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TeamInfo_Dialog.dismiss();
                    }
                });
                TeamInfo_Customdialog_1_Ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HttpClient http_mymatch_focus_joiner = new HttpClient();
                        String result = http_mymatch_focus_joiner.HttpClient("Trophy_part1","MyMatch_Focus_Join.jsp",arrData.get(position).getMatch_Pk(),arrData.get(position).getMatch_Joiner_Pk(),arrData.get(position).getUser_Pk());
                        parsedData_MyMatch_Focus_Join = jsonParserList_MyMatch_Focus_join(result);
                        if(parsedData_MyMatch_Focus_Join[0][0].equals("succed")){
                            TeamInfo_Dialog.dismiss();
                            MyMatch_Focus_LinerLayout_Joined.setVisibility(View.VISIBLE);
                            MyMatch_Focus_LinerLayout_Joining.setVisibility(View.GONE);
                            Match_Focus_ImageView_Status.setImageResource(R.drawable.deadline);
                            try {
                                String En_Profile = URLEncoder.encode(arrData.get(position).getEmblem(), "utf-8");
                                if (arrData.get(position).getEmblem().equals(".")) {
                                    Glide.with(context).load(R.drawable.emblem).bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                                            .into(MyMatch_Focus_Joined_ImageView_Emblem);
                                } else {
                                    Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/team/" + En_Profile + ".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                                            .skipMemoryCache(true)
                                            .into(MyMatch_Focus_Joined_ImageView_Emblem);
                                }
                            } catch (UnsupportedEncodingException e) {

                            }
                            MyMatch_Focus_Joined_TextView_TeamName.setText(arrData.get(position).getTeamName());
                            MyMatch_Focus_Joined_TextView_Title.setText(arrData.get(position).getMemo());
                        }
                        else{
                            Snackbar.make(view,"잠시 후 다시 시도해주세요.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        MyMatch_Focus_Joiner_CustomList_ImageView_Emblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,TeamFocus.class);
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
}
