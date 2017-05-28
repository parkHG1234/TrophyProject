package trophy.projetc2.OutCourt;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import trophy.projetc2.MainActivity;
import trophy.projetc2.R;
import trophy.projetc2.User.Login;

/**
 * Created by 박효근 on 2017-05-02.
 */

public class OutCourt_CourtInfo_MyAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<OutCourt_CourtInfo_MyData> arrData;
    TextView Layout_Navigation_OutCourt_CourtInfo_CourtAddress,Layout_Navigation_OutCourt_CourtInfo_CourtName,Layout_Navigation_OutCourt_CourtInfo_TodayWrite;
    ImageView Layout_Navigation_OutCourt_CourtInfo_ImageView_CourtImage;
    public OutCourt_CourtInfo_MyAdapter(Context c, ArrayList<OutCourt_CourtInfo_MyData> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return arrData.size();
    }

    public Object getItem(int position) {
        return arrData.get(position).getCourtName();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_navigation_outcourt_courtinfo_customlist, parent, false);
        }
        Layout_Navigation_OutCourt_CourtInfo_CourtAddress = (TextView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_CourtAddress);
        Layout_Navigation_OutCourt_CourtInfo_CourtName = (TextView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_CourtName);
        Layout_Navigation_OutCourt_CourtInfo_TodayWrite = (TextView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_TodayWrite);
        Layout_Navigation_OutCourt_CourtInfo_ImageView_CourtImage = (ImageView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_ImageView_CourtImage);

        try{
            String En_Profile = URLEncoder.encode(arrData.get(position).getImage(), "utf-8");
            if(En_Profile.equals("."))
            {
                Glide.with(context).load(R.drawable.court_basic).into(Layout_Navigation_OutCourt_CourtInfo_ImageView_CourtImage);
            }
            else
            {
                Glide.with(context).load("http://210.122.7.193:8080/Trophy_img/court/"+En_Profile+".jpg")
                        .into(Layout_Navigation_OutCourt_CourtInfo_ImageView_CourtImage);
            }
        } catch (UnsupportedEncodingException e){
        }
        Layout_Navigation_OutCourt_CourtInfo_CourtName.setText(arrData.get(position).getCourtName());
        Layout_Navigation_OutCourt_CourtInfo_TodayWrite.setText("오늘의 게시글 : " + arrData.get(position).getTodayWrite());
        Layout_Navigation_OutCourt_CourtInfo_CourtAddress.setText(arrData.get(position).getCourtAddress());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrData.get(position).getUser_Pk().equals(".")){
                    Intent intent_login = new Intent(arrData.get(position).getActivity(), Login.class);
                    context.startActivity(intent_login);
                    arrData.get(position).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }else{
                    Intent intent1 = new Intent(context, OutCourt_CourtInfo_Focus.class);
                    intent1.putExtra("CourtName", arrData.get(position).getCourtName());
                    intent1.putExtra("User_Pk", arrData.get(position).getUser_Pk());
                    intent1.putExtra("Court_Pk", arrData.get(position).getCourt_Pk());
                    intent1.putExtra("Today_Content", arrData.get(position).getTodayWrite());
                    context.startActivity(intent1);
                    arrData.get(position).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            }
        });

        return convertView;

    }
}

