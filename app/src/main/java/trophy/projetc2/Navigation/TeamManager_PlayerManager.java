package trophy.projetc2.Navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

import static trophy.projetc2.Navigation.TeamManager.TeamManager_TeamName;

/**
 * Created by 박효근 on 2017-01-10.
 */

public class TeamManager_PlayerManager extends Fragment {
    ListView Layout_Navigation_TeamManager_ListView_Joiner;
    TeamManager_PlayerManager_Customlist_MyAdapter TeamManager_PlayerManager_Customlist_MyAdapter;
    ArrayList<TeamManager_PlayerManager_Customlist_MyData> TeamManager_PlayerManager_Customlist_MyData;
    String[][] parsedData_Joiner;
    String TeamName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.layout_navigation_teammanager_playermanager, container, false);
        Layout_Navigation_TeamManager_ListView_Joiner = (ListView)rootView.findViewById(R.id.Layout_Navigation_TeamManager_ListView_Joiner);

        TeamName = TeamManager_TeamName;

        HttpClient http_Joiner= new HttpClient();
        String result = http_Joiner.HttpClient("Trophy_part1","TeamManager_Joiner.jsp",TeamName);
        parsedData_Joiner = jsonParserList_Joiner(result);
        setData_Joiner();
        TeamManager_PlayerManager_Customlist_MyAdapter = new TeamManager_PlayerManager_Customlist_MyAdapter(getContext(), TeamManager_PlayerManager_Customlist_MyData);
        //리스트뷰에 어댑터 연결
        Layout_Navigation_TeamManager_ListView_Joiner.setAdapter(TeamManager_PlayerManager_Customlist_MyAdapter);
        return rootView;
    }
    public String[][] jsonParserList_Joiner(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8","msg9"};
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
    private void setData_Joiner()
    {
        TeamManager_PlayerManager_Customlist_MyData = new ArrayList<TeamManager_PlayerManager_Customlist_MyData>();
        for(int i =0; i<parsedData_Joiner.length; i++)
        {
            TeamManager_PlayerManager_Customlist_MyData.add(new TeamManager_PlayerManager_Customlist_MyData(parsedData_Joiner[i][0],parsedData_Joiner[i][1],parsedData_Joiner[i][2],parsedData_Joiner[i][3],parsedData_Joiner[i][4],parsedData_Joiner[i][5],parsedData_Joiner[i][6],parsedData_Joiner[i][7],parsedData_Joiner[i][8]));
        }
    }
}