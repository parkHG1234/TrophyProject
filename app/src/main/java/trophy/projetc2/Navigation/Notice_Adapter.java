package trophy.projetc2.Navigation;

/**
 * Created by 박효근 on 2016-07-22.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import trophy.projetc2.R;

import static trophy.projetc2.Navigation.Notice.Notice_Num_Position;

/**
 * Created by 박지훈 on 2016-06-24.
 */
public class Notice_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Notice_Setting> arrData;
    private LayoutInflater inflater;
    TextView Notice_TextView_title;
    public Notice_Adapter(Context c, ArrayList<Notice_Setting> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return arrData.size();
    }

    public Object getItem(int position) {
        return arrData.get(position).getNotice_num();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_notice_customlist, parent, false);
        }
        Notice_TextView_title = (TextView) convertView.findViewById(R.id.Notice_TextView_title);
        if(Notice_Num_Position == position){
            Notice_TextView_title.setBackgroundColor(convertView.getResources().getColor(R.color.MainColor1));
            Notice_TextView_title.setTextColor(convertView.getResources().getColor(R.color.White));
        }
        else{
            Notice_TextView_title.setBackgroundColor(convertView.getResources().getColor(R.color.backColor));
            Notice_TextView_title.setTextColor(convertView.getResources().getColor(R.color.Black));
        }

        Notice_TextView_title.setText(" 〉 "+arrData.get(position).getNotice_title());


        return convertView;
    }

}
