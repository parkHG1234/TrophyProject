package trophy.projetc2.Navigation;

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
import trophy.projetc2.R;

import static trophy.projetc2.Navigation.TeamManager.TeamManager_TeamName;
import static trophy.projetc2.Navigation.TeamManager_PlayerManager.refresh_joiner;
import static trophy.projetc2.Navigation.TeamManager_PlayerManager.refresh_player;

/**
 * Created by 박효근 on 2017-01-24.
 */

public class TeamManager_ContestJoin_Customlist_MyAdapter  extends BaseAdapter {
    private Context context;
    private ArrayList<TeamManager_ContestJoin_Customlist_MyData> arrData;
    private LayoutInflater inflater;
    ImageView  contestjoin_Image;
    TextView contestjoin_Title, contestjoin_Status;
    public TeamManager_ContestJoin_Customlist_MyAdapter(Context c, ArrayList<TeamManager_ContestJoin_Customlist_MyData> arr) {
        this.context = c;
        this.arrData = arr;
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return arrData.size();
    }

    public Object getItem(int position) {
        return arrData.get(position).getContest_Pk();
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_customlist_teammanager_contestjoin, parent, false);
        }
        contestjoin_Image = (ImageView) convertView.findViewById(R.id.contestjoin_Image);
        contestjoin_Title = (TextView) convertView.findViewById(R.id.contestjoin_Title);
        contestjoin_Status = (TextView) convertView.findViewById(R.id.contestjoin_Status);

        //프로필 관리
        try{
            String Profile1 = URLEncoder.encode(arrData.get(position).getContest_Image(), "utf-8");
            if(Profile1.equals(".")){
                Glide.with(context).load(R.drawable.profile_basic_image)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(contestjoin_Image);
            }
            else{
                Glide.with(context).load("http://210.122.7.193:8080/Web_basket/imgs1/Contest/"+Profile1+".jpg")
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(contestjoin_Image);
            }
        }
        catch (UnsupportedEncodingException e){

        }
        contestjoin_Title.setText(arrData.get(position).getContest_Title());
        contestjoin_Status.setText(arrData.get(position).getContest_Status());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_contestfocus = new Intent(context, TeamManager_ContestJoin_ContestFocus.class);
                intent_contestfocus.putExtra("Contest_Pk",arrData.get(position).getContest_Pk());
                intent_contestfocus.putExtra("Contest_Image",arrData.get(position).getContest_Image());
                intent_contestfocus.putExtra("Contest_Title",arrData.get(position).getContest_Title());
                intent_contestfocus.putExtra("Contest_Status",arrData.get(position).getContest_Status());
                intent_contestfocus.putExtra("AcountName",arrData.get(position).getAcountName());
                intent_contestfocus.putExtra("AcountNumber",arrData.get(position).getAcountNumber());
                context.startActivity(intent_contestfocus);
                arrData.get(position).getActivity().overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            }
        });
        return convertView;
    }
    public String[][] jsonParserList_Player_Focus(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5"};
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

}
