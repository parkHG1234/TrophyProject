package trophy.projetc2.OutCourt;

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

public class OutCourt_Address_MyAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<OutCourt_Address_MyData> arrData;
    TextView Layout_Navigation_OutCourt_Address_Se;
    public OutCourt_Address_MyAdapter(Context c, ArrayList<OutCourt_Address_MyData> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return arrData.size();
    }

    public Object getItem(int position) {
        return arrData.get(position).getUser_Pk();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_navigation_outcourt_address_customlist, parent, false);
        }
        Layout_Navigation_OutCourt_Address_Se = (TextView)convertView.findViewById(R.id.Layout_Navigation_OutCourt_Address_Se);
        Layout_Navigation_OutCourt_Address_Se.setText(arrData.get(position).getAddress_Si());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(context, OutCourt_CourtInfo.class);
                intent1.putExtra("Address_Do", arrData.get(position).getAddress());
                intent1.putExtra("Address_Si", arrData.get(position).getAddress_Si());
                intent1.putExtra("User_Pk", arrData.get(position).getUser_Pk());
                context.startActivity(intent1);
                arrData.get(position).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        return convertView;
    }
}

