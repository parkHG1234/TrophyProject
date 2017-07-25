package trophy.projetc2.OutCourt;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

import static android.content.Context.WINDOW_SERVICE;
import static trophy.projetc2.OutCourt.OutCourt_CourtInfo_Focus.Content_Total_ImageH;
import static trophy.projetc2.OutCourt.OutCourt_CourtInfo_Focus.adapter;
import static trophy.projetc2.OutCourt.OutCourt_CourtInfo_Focus.imageH;


/**
 * Created by 박효근 on 2017-05-07.
 */

public class OutCourt_CourtInfo_Focus_MyAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<OutCourt_CourtInfo_Focus_MyData> arrData;
    TextView Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Name, Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Date,Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Content;
    ImageView Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Profile;
    ImageView Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Delete;
    TextView Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_comment;
    ImageView Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Content;
    String Content_Date; String Content_Time;
    String[][] parsedData_Content_Delete;
    String[][] parsedData_Player_Focus;
    int ratio = 0; int ratio_Height=0;
    int Display_Weight=0, Display_Height=0;
    public OutCourt_CourtInfo_Focus_MyAdapter(Context c, ArrayList<OutCourt_CourtInfo_Focus_MyData> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return arrData.size();
    }

    public Object getItem(int position) {
        return arrData.get(position).getOutCourt_Content_Pk();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_navigation_outcourt_courtinfo_focus_customlist, parent, false);
        }
        Display display = ((WindowManager)context.getSystemService(WINDOW_SERVICE)).getDefaultDisplay() ;
        Display_Weight = display.getWidth();
        Display_Height = display.getHeight();
        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Profile= (ImageView) convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Profile);
        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Name= (TextView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Name);
        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Date= (TextView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Date);
        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Content =(TextView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Content);
        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Delete = (ImageView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Delete);
        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_comment = (TextView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_comment);
        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Content = (ImageView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Content);
        if(arrData.get(position).getImage_W().equals(".")){

        }else{
            ratio = Integer.parseInt(arrData.get(position).getImage_W()) / Display_Weight;
            ratio_Height = Integer.parseInt(arrData.get(position).getImage_H()) / ratio;
            Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Content.getLayoutParams().height = ratio_Height;
            Content_Total_ImageH += ratio_Height;
        }
//        int a = Integer.parseInt(String.valueOf(Math.round(imageH*0.8)));
//
//        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Content.getLayoutParams().height = a;
        try{
            String En_Profile = URLEncoder.encode(arrData.get(position).getUser_Profile(), "utf-8");
            if(En_Profile.equals("."))
            {
                Glide.with(context).load(R.drawable.profile_basic_image).diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true).into(Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Profile);
            }
            else
            {
                Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/profile/"+En_Profile+".jpg").diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                    .into(Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Profile);
            }
        }
        catch (UnsupportedEncodingException e){
        }
        try{
            String En_Profile = URLEncoder.encode(arrData.get(position).getImage(), "utf-8");
            if(En_Profile.equals("."))
            {
                Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Content.setVisibility(View.GONE);
            }
            else
            {
                Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Content.setVisibility(View.VISIBLE);
                Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/content/"+En_Profile+".jpg").diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Content);
            }
        }
        catch (UnsupportedEncodingException e){
        }

        if(arrData.get(position).getContent_User_Pk().equals(arrData.get(position).getUser_Pk())){
            Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Delete.setVisibility(View.VISIBLE);
            Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Delete.setEnabled(true);
        }
        else{
            Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Delete.setVisibility(View.INVISIBLE);
            Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Delete.setEnabled(false);
        }
        if(arrData.get(position).getUser_Name().equals(".")){
            Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Name.setText("탈퇴한 이용자");
        }
        else{
            Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Name.setText(arrData.get(position).getUser_Name());
        }


        String str = arrData.get(position).getDate();
        String[] data = str.split(":::");
        if(data[0].equals(arrData.get(position).getNow_Date())){
            Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Date.setText(data[1]);
        }
       else{
            Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Date.setText(data[0].substring(2));
        }
        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrData.get(position).getUser_Name().equals(".")){
                    Snackbar.make(view, "탈퇴한 이용자입니다.",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    final View layout = inflater.inflate(R.layout.layout_customdialog_teaminfo_player_focus, (ViewGroup) view.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Root));
                    final TextView Layout_CustomDialog_TeamInfo_Player_Focus_Title = (TextView)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Title);
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Back = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Back);
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Profile = (ImageView)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_TeamName = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_TeamName);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Age = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Age);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Sex = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Sex);
                    final Button Layout_CustomDialog_TeamInfo_Player_Focus_Address = (Button)layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Address);
                    final ImageView Layout_CustomDialog_TeamInfo_Player_Focus_Phone = (ImageView) layout.findViewById(R.id.Layout_CustomDialog_TeamInfo_Player_Focus_Phone);
                    final LinearLayout Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Duty = (LinearLayout) layout.findViewById(R.id.Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Duty);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Title.setText("선수 정보");
                    try{
                        String En_Profile = URLEncoder.encode(arrData.get(position).getUser_Profile(), "utf-8");
                        if(En_Profile.equals("."))
                        {
                            Glide.with(context).load(R.drawable.profile_basic_image).diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true).into(Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                        }
                        else
                        {
                            Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/profile/"+En_Profile+".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .skipMemoryCache(true)
                                    .into(Layout_CustomDialog_TeamInfo_Player_Focus_Profile);
                        }
                    }
                    catch (UnsupportedEncodingException e)
                    {

                    }
                    HttpClient http_Joiner= new HttpClient();
                    String result = http_Joiner.HttpClient("Trophy_part1","OutCourt_Player_Focus.jsp",arrData.get(position).getContent_User_Pk());
                    parsedData_Player_Focus = jsonParserList_Player_Focus(result);
                    Layout_CustomDialog_TeamInfo_Player_Focus_TeamName.setText(parsedData_Player_Focus[0][6]+" . "+parsedData_Player_Focus[0][0]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Age.setText(ChangeAge(parsedData_Player_Focus[0][1])+" / "+parsedData_Player_Focus[0][2]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Sex.setText(parsedData_Player_Focus[0][4]+"cm / "+parsedData_Player_Focus[0][5]+"kg");
                    Layout_CustomDialog_TeamInfo_Player_Focus_Address.setText(parsedData_Player_Focus[0][3]);
                    Layout_CustomDialog_TeamInfo_Player_Focus_Phone.setVisibility(View.GONE);
                    Layout_CustomDialog_TeamManager_PlayerManager_Focus_LinearLayout_Duty.setVisibility(View.GONE);
                    final MaterialDialog TeamPlayerDialog = new MaterialDialog(context);
                    TeamPlayerDialog
                            .setTitle("신청자 정보")
                            .setView(layout)
                            .setCanceledOnTouchOutside(true);
                    TeamPlayerDialog.show();
                    Layout_CustomDialog_TeamInfo_Player_Focus_Back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TeamPlayerDialog.dismiss();
                        }
                    });
                }
            }
        });
        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Content.setText(arrData.get(position).getOutCourt_Content());
        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                final View layout = inflater.inflate(R.layout.layout_customdialog_2choice, (ViewGroup) view.findViewById(R.id.Customdialog_2Choice_Root));
                final TextView Customdialog_2Choice_Title = (TextView)layout.findViewById(R.id.Customdialog_2Choice_Title);
                final ImageView Customdialog_2Choice_Back = (ImageView)layout.findViewById(R.id.Customdialog_2Choice_Back);
                final TextView Customdialog_2Choice_Content = (TextView)layout.findViewById(R.id.Customdialog_2Choice_Content);
                final Button Customdialog_2Choice_First = (Button)layout.findViewById(R.id.Customdialog_2Choice_First);
                final Button Customdialog_2Choice_Second = (Button)layout.findViewById(R.id.Customdialog_2Choice_Second);
                Customdialog_2Choice_Title.setText("게시글 삭제");
                Customdialog_2Choice_First.setText("삭 제");
                Customdialog_2Choice_Second.setText("취 소");
                Customdialog_2Choice_Content.setText("게시글을 삭제합니다.");
                final MaterialDialog TeamInfo_Dialog = new MaterialDialog(view.getContext());
                TeamInfo_Dialog
                        .setContentView(layout)
                        .setCanceledOnTouchOutside(true);
                TeamInfo_Dialog.show();
                Customdialog_2Choice_First.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HttpClient http_delete = new HttpClient();
                        String result1 = http_delete.HttpClient("Trophy_part1","OutCourt_Focus_Content_Delete.jsp",arrData.get(position).getOutCourt_Content_Pk(),arrData.get(position).getNow_Date());
                        parsedData_Content_Delete = jsonParserList_Content_Delete(result1);
                        if(parsedData_Content_Delete[0][0].equals("succed")){
                            TeamInfo_Dialog.dismiss();
                            arrData.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            Snackbar.make(view,"잠시 후 다시 시도해주시기 바랍니다.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
                Customdialog_2Choice_Back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TeamInfo_Dialog.dismiss();
                    }
                });
                Customdialog_2Choice_Second.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TeamInfo_Dialog.dismiss();
                    }
                });
            }
        });
        if(arrData.get(position).getComment_Count().equals("0")){
            Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_comment.setText("댓글쓰기");
        }
        else{
            Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_comment.setText("댓글 "+arrData.get(position).getComment_Count()+"개 모두 보기");
        }
        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(context,OutCourt_CourtInfo_Focus_Comment.class);
                intent1.putExtra("Court_Content_Pk", arrData.get(position).getOutCourt_Content_Pk());
                intent1.putExtra("User_Pk", arrData.get(position).getUser_Pk());
                context.startActivity(intent1);
                arrData.get(position).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        return convertView;

    }
    public String[][] jsonParserList_Content_Delete(String pRecvServerPage){
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
    public String[][] jsonParserList_Player_Focus(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);
                for (int j = 0; j < jsonName.length; j++) {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    //date 입력받아 나이 구하는 함수
    public String ChangeAge(String Age){
        Calendar cal= Calendar.getInstance ();
        String[] str = new String(Age).split(" \\/ ");
        String[] str_day = new String(str[2]).split(" ");
        int year = Integer.parseInt(str[0]);
        int month = Integer.parseInt(str[1]);
        int day = Integer.parseInt(str_day[0]);

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DATE, day);

        Calendar now = Calendar.getInstance ();

        int age = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
        if (  (cal.get(Calendar.MONTH) > now.get(Calendar.MONTH))
                || (    cal.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                && cal.get(Calendar.DAY_OF_MONTH) > now.get(Calendar.DAY_OF_MONTH)   )
                ){
            age--;
        }
        String Str_age = Integer.toString(age);
        return Str_age;
    }

}

