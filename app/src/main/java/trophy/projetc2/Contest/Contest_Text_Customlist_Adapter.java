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
 * Created by 박효근 on 2017-08-05.
 */

public class Contest_Text_Customlist_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Contest_Text_Customlist_MyData> arrData;
    private LayoutInflater inflater;
    String Contest1_Pk;   String Contest2_Pk;
    public Contest_Text_Customlist_Adapter(Context c, ArrayList<Contest_Text_Customlist_MyData> arr) {
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
            convertView = inflater.inflate(R.layout.layout_main_contest_text_customlist, parent, false);


            TextView Contest_name = (TextView)convertView.findViewById(R.id.Contest_name);
            Contest_name.setText(arrData.get(position).getContest1_Title());

            Contest_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("Pk",arrData.get(position).getContest1_Pk());
                    Intent intent = new Intent(context, Contest_Detail.class);
                    intent.putExtra("Contest_Pk", arrData.get(position).getContest1_Pk());
                    context.startActivity(intent);
                    arrData.get(position).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                }
            });
        }
        return convertView;
    }
}
