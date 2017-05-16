package trophy.projetc2.OutCourt;

import android.content.Context;
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
    String Content_Date; String Content_Time;
    String[][] parsedData_Content_Delete;
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
        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Profile= (ImageView) convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Profile);
        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Name= (TextView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Name);
        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Date= (TextView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Date);
        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Content =(TextView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Content);
        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Delete = (ImageView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Delete);

        try{
            String En_Profile = URLEncoder.encode(arrData.get(position).getUser_Profile(), "utf-8");
            if(En_Profile.equals("."))
            {
                Glide.with(context).load(R.drawable.profile_basic_image).into(Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Profile);
            }
            else
            {
                Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/profile/"+En_Profile+".jpg").bitmapTransform(new CropCircleTransformation(Glide.get(context).getBitmapPool()))
                        .into(Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_ImageView_Profile);
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
        Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Name.setText(arrData.get(position).getUser_Name());

        String str = arrData.get(position).getDate();
        String[] data = str.split(":::");
        if(data[0].equals(arrData.get(position).getNow_Date())){
            Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Date.setText(data[1]);
        }
       else{
            Layout_Navigation_OutCourt_CourtInfo_Focus_CustomList_TextView_Date.setText(data[0].substring(2));
        }
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
                        String result1 = http_delete.HttpClient("Trophy_part1","OutCourt_Focus_Content_Delete.jsp",arrData.get(position).getOutCourt_Content_Pk());
                        parsedData_Content_Delete = jsonParserList_Content_Delete(result1);
                        if(parsedData_Content_Delete[0][0].equals("succed")){
                            TeamInfo_Dialog.dismiss();
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
}

