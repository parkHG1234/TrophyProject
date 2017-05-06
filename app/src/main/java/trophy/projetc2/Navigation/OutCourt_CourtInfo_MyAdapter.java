package trophy.projetc2.Navigation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-05-02.
 */

public class OutCourt_CourtInfo_MyAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<OutCourt_CourtInfo_MyData> arrData;
    TextView Layout_Navigation_OutCourt_CourtInfo_CourtAddress,Layout_Navigation_OutCourt_CourtInfo_CourtName,Layout_Navigation_OutCourt_CourtInfo_TodayWrite;
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
        Layout_Navigation_OutCourt_CourtInfo_CourtAddress= (TextView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_CourtAddress);
        Layout_Navigation_OutCourt_CourtInfo_CourtName= (TextView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_CourtName);
        Layout_Navigation_OutCourt_CourtInfo_TodayWrite= (TextView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_CourtInfo_TodayWrite);
        Layout_Navigation_OutCourt_CourtInfo_CourtName.setText(arrData.get(position).getCourtName());
        Layout_Navigation_OutCourt_CourtInfo_TodayWrite.setText("오늘의 게시글 : " + arrData.get(position).getTodayWrite());
        Layout_Navigation_OutCourt_CourtInfo_CourtAddress.setText(arrData.get(position).getCourtAddress());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(context, OutCourt_CourtInfo_Focus.class);
                intent1.putExtra("CourtName", arrData.get(position).getCourtName());
                intent1.putExtra("User_Pk", arrData.get(position).getUser_Pk());
                context.startActivity(intent1);
                arrData.get(position).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });

        return convertView;

    }
}

