package trophy.projetc2.Navigation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

import static trophy.projetc2.Navigation.TeamManager.TeamManager_Team_Pk;

/**
 * Created by 박효근 on 2017-01-10.
 */

public class TeamManager_ContestJoin extends Fragment {
    ListView Layout_Navigation_TeamManager_ContestJoin_ListView;
    static String refresh_contestjoin="stop";
    static TimerTask myTask;
    static Timer Contestjoin_timer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.layout_navigation_teammanager_contestjoin, container, false);
        Layout_Navigation_TeamManager_ContestJoin_ListView = (ListView)rootView.findViewById(R.id.Layout_Navigation_TeamManager_ContestJoin_ListView);
        HttpClient http_contestjoin = new HttpClient();
        String result = http_contestjoin.HttpClient("Trophy_part1", "TeamManager_ContestJoin.jsp",TeamManager_Team_Pk);
        String[][] ContestJoin = jsonParserList_ContestJoin(result);

        ArrayList<TeamManager_ContestJoin_Customlist_MyData> TeamManager_ContestJoin_Customlist_MyData;
        TeamManager_ContestJoin_Customlist_MyData = new ArrayList<TeamManager_ContestJoin_Customlist_MyData>();
        for (int i = 0; i < ContestJoin.length; i++) {
            TeamManager_ContestJoin_Customlist_MyData.add(new TeamManager_ContestJoin_Customlist_MyData(ContestJoin[i][0], ContestJoin[i][1],
                    ContestJoin[i][2], ContestJoin[i][3],ContestJoin[i][4],ContestJoin[i][5],getActivity()));
        }
        TeamManager_ContestJoin_Customlist_MyAdapter Adapter = new TeamManager_ContestJoin_Customlist_MyAdapter(getContext(), TeamManager_ContestJoin_Customlist_MyData);
        Layout_Navigation_TeamManager_ContestJoin_ListView.setAdapter(Adapter);
        //팀 신청자 및 팀원 새로고침 타이머
        myTask = new TimerTask() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(refresh_contestjoin.equals("refresh")){
                            HttpClient http_contestjoin = new HttpClient();
                            String result = http_contestjoin.HttpClient("Trophy_part1", "TeamManager_ContestJoin.jsp",TeamManager_Team_Pk);
                            String[][] ContestJoin = jsonParserList_ContestJoin(result);

                            ArrayList<TeamManager_ContestJoin_Customlist_MyData> TeamManager_ContestJoin_Customlist_MyData;
                            TeamManager_ContestJoin_Customlist_MyData = new ArrayList<TeamManager_ContestJoin_Customlist_MyData>();
                            for (int i = 0; i < ContestJoin.length; i++) {
                                TeamManager_ContestJoin_Customlist_MyData.add(new TeamManager_ContestJoin_Customlist_MyData(ContestJoin[i][0], ContestJoin[i][1],
                                        ContestJoin[i][2], ContestJoin[i][3],ContestJoin[i][4],ContestJoin[i][5],getActivity()));
                            }
                            TeamManager_ContestJoin_Customlist_MyAdapter Adapter = new TeamManager_ContestJoin_Customlist_MyAdapter(getContext(), TeamManager_ContestJoin_Customlist_MyData);
                            Layout_Navigation_TeamManager_ContestJoin_ListView.setAdapter(Adapter);
                            refresh_contestjoin = "stop";
                        }
                        else{

                        }
                    }
                });
            }
        };
        Contestjoin_timer = new Timer();
        //timer.schedule(myTask, 5000);  // 5초후 실행하고 종료
        Contestjoin_timer.schedule(myTask, 500, 2000); // 5초후 첫실행, 3초마다 계속실행
        return rootView;
    }

    private String[][] jsonParserList_ContestJoin(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jarr = json.getJSONArray("List");

            String[] jsonName = {"msg1", "msg2", "msg3", "msg4","msg5","msg6"};
            String[][] parseredData = new String[jarr.length()][jsonName.length];
            for (int i = 0; i < jarr.length(); i++) {
                json = jarr.getJSONObject(i);
                for (int j = 0; j < jsonName.length; j++) {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }
            for (int i = 0; i < parseredData.length; i++) {
                Log.i("JSON을 분석한 데이터" + i + ":", parseredData[i][0]);
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
