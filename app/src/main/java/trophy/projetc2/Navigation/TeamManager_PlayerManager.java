package trophy.projetc2.Navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
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

public class TeamManager_PlayerManager extends Fragment {
    ListView Layout_Navigation_TeamManager_ListView_Joiner;
    ListView Layout_Navigation_TeamManager_ListView_Player;
    Button Layout_Navigation_TeamManager_Button_PlayerTitle;
    TeamManager_PlayerManager_Customlist_MyAdapter_Joiner TeamManager_PlayerManager_Customlist_MyAdapter_Joiner;
    ArrayList<TeamManager_PlayerManager_Customlist_MyData_Joiner> TeamManager_PlayerManager_Customlist_MyData_Joiner;
    TeamManager_PlayerManager_Customlist_MyAdapter_Player TeamManager_PlayerManager_Customlist_MyAdapter_Player;
    ArrayList<TeamManager_PlayerManager_Customlist_MyData_Player> TeamManager_PlayerManager_Customlist_MyData_Player;
    String[][] parsedData_Joiner, parsedData_Player;
    String Team_Pk;
    int JoinerCount=0,Row=0,Extra=0,PlayerCount=0;
    static TimerTask myTask;
    static Timer PlayerManager_timer;
    static String refresh_joiner="stop",refresh_player="stop";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.layout_navigation_teammanager_playermanager, container, false);
        Layout_Navigation_TeamManager_ListView_Joiner = (ListView)rootView.findViewById(R.id.Layout_Navigation_TeamManager_ListView_Joiner);
        Layout_Navigation_TeamManager_ListView_Player = (ListView)rootView.findViewById(R.id.Layout_Navigation_TeamManager_ListView_Player);
        Layout_Navigation_TeamManager_Button_PlayerTitle = (Button)rootView.findViewById(R.id.Layout_Navigation_TeamManager_Button_PlayerTitle);
        Team_Pk = TeamManager_Team_Pk;
        Layout_Navigation_TeamManager_Button_PlayerTitle.setText("팀 원");
        //신청자 리스트
        HttpClient http_Joiner= new HttpClient();
        String result = http_Joiner.HttpClient("Trophy_part1","TeamManager_Joiner.jsp",Team_Pk);
        parsedData_Joiner = jsonParserList_Joiner(result);
        setData_Joiner();
        TeamManager_PlayerManager_Customlist_MyAdapter_Joiner = new TeamManager_PlayerManager_Customlist_MyAdapter_Joiner(getContext(), TeamManager_PlayerManager_Customlist_MyData_Joiner);
        //리스트뷰에 어댑터 연결
        Layout_Navigation_TeamManager_ListView_Joiner.setAdapter(TeamManager_PlayerManager_Customlist_MyAdapter_Joiner);
        setListViewHeightBasedOnChildren(Layout_Navigation_TeamManager_ListView_Joiner);
        //팀원 리스트
        HttpClient http_Player= new HttpClient();
        String result1 = http_Player.HttpClient("Trophy_part1","TeamManager_Player.jsp",Team_Pk);
        parsedData_Player = jsonParserList_Player(result1);
        setData_Player();
        TeamManager_PlayerManager_Customlist_MyAdapter_Player = new TeamManager_PlayerManager_Customlist_MyAdapter_Player(getContext(), TeamManager_PlayerManager_Customlist_MyData_Player);
        //리스트뷰에 어댑터 연결
        Layout_Navigation_TeamManager_ListView_Player.setAdapter(TeamManager_PlayerManager_Customlist_MyAdapter_Player);
        setListViewHeightBasedOnChildren(Layout_Navigation_TeamManager_ListView_Player);
        //팀 신청자 및 팀원 새로고침 타이머
        myTask = new TimerTask() {
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(refresh_joiner.equals("refresh")){
                            HttpClient http_Joiner= new HttpClient();
                            String result = http_Joiner.HttpClient("Trophy_part1","TeamManager_Joiner.jsp",Team_Pk);
                            parsedData_Joiner = jsonParserList_Joiner(result);
                            setData_Joiner();
                            TeamManager_PlayerManager_Customlist_MyAdapter_Joiner = new TeamManager_PlayerManager_Customlist_MyAdapter_Joiner(getContext(), TeamManager_PlayerManager_Customlist_MyData_Joiner);
                            //리스트뷰에 어댑터 연결
                            Layout_Navigation_TeamManager_ListView_Joiner.setAdapter(TeamManager_PlayerManager_Customlist_MyAdapter_Joiner);
                            setListViewHeightBasedOnChildren(Layout_Navigation_TeamManager_ListView_Joiner);
                            refresh_joiner = "stop";
                        }
                       else{

                        }
                        if(refresh_player.equals("refresh")){
                            HttpClient http_Player= new HttpClient();
                            String result = http_Player.HttpClient("Trophy_part1","TeamManager_Player.jsp",Team_Pk);
                            parsedData_Player = jsonParserList_Player(result);
                            setData_Player();
                            TeamManager_PlayerManager_Customlist_MyAdapter_Player = new TeamManager_PlayerManager_Customlist_MyAdapter_Player(getContext(), TeamManager_PlayerManager_Customlist_MyData_Player);
                            //리스트뷰에 어댑터 연결
                            Layout_Navigation_TeamManager_ListView_Player.setAdapter(TeamManager_PlayerManager_Customlist_MyAdapter_Player);
                            setListViewHeightBasedOnChildren(Layout_Navigation_TeamManager_ListView_Player);
                            refresh_joiner = "stop";
                        }
                        else{

                        }
                    }
                });
            }
        };
        PlayerManager_timer = new Timer();
        //timer.schedule(myTask, 5000);  // 5초후 실행하고 종료
        PlayerManager_timer.schedule(myTask, 500, 2000); // 5초후 첫실행, 3초마다 계속실행
        return rootView;
    }
    public String[][] jsonParserList_Joiner(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1","msg2","msg3","msg4"};
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
    public String[][] jsonParserList_Player(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");

            String[] jsonName = {"msg1","msg2","msg3","msg4"};
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
        JoinerCount = parsedData_Joiner.length;
        Row = JoinerCount/4;
        Extra = JoinerCount%4;
        TeamManager_PlayerManager_Customlist_MyData_Joiner = new ArrayList<TeamManager_PlayerManager_Customlist_MyData_Joiner>();

        for(int i =0; i<Row; i++)
        {
            int _i = i*4;
            TeamManager_PlayerManager_Customlist_MyData_Joiner.add(new TeamManager_PlayerManager_Customlist_MyData_Joiner(parsedData_Joiner[_i][0],parsedData_Joiner[_i][1],parsedData_Joiner[_i][2],parsedData_Joiner[_i+1][0],parsedData_Joiner[_i+1][1],parsedData_Joiner[_i+1][2],parsedData_Joiner[_i+2][0],parsedData_Joiner[_i+2][1],parsedData_Joiner[_i+2][2],parsedData_Joiner[_i+3][0],parsedData_Joiner[_i+3][1],parsedData_Joiner[_i+3][2]));
        }
        if(Extra==0){
        }
        else if(Extra==1){
            TeamManager_PlayerManager_Customlist_MyData_Joiner.add(new TeamManager_PlayerManager_Customlist_MyData_Joiner(parsedData_Joiner[(4*Row)][0],parsedData_Joiner[(4*Row)][1],parsedData_Joiner[(4*Row)][2],"null","null","null","null","null","null","null","null","null"));
        }else if(Extra==2){
            TeamManager_PlayerManager_Customlist_MyData_Joiner.add(new TeamManager_PlayerManager_Customlist_MyData_Joiner(parsedData_Joiner[(4*Row)][0],parsedData_Joiner[(4*Row)][1],parsedData_Joiner[(4*Row)][2],parsedData_Joiner[(4*Row)+1][0],parsedData_Joiner[(4*Row)+1][1],parsedData_Joiner[(4*Row)+1][2],"null","null","null","null","null","null"));
        }else if(Extra==3){
            TeamManager_PlayerManager_Customlist_MyData_Joiner.add(new TeamManager_PlayerManager_Customlist_MyData_Joiner(parsedData_Joiner[(4*Row)][0],parsedData_Joiner[(4*Row)][1],parsedData_Joiner[(4*Row)][2],parsedData_Joiner[(4*Row)+1][0],parsedData_Joiner[(4*Row)+1][1],parsedData_Joiner[(4*Row)+1][2],parsedData_Joiner[(4*Row)+2][0],parsedData_Joiner[(4*Row)+2][1],parsedData_Joiner[(4*Row)+2][2],"null","null","null"));
        }
    }
    private void setData_Player()
    {
        PlayerCount = parsedData_Player.length;
        Row = PlayerCount/4;
        Extra = PlayerCount%4;
        TeamManager_PlayerManager_Customlist_MyData_Player = new ArrayList<TeamManager_PlayerManager_Customlist_MyData_Player>();

        for(int i =0; i<Row; i++)
        {
            int _i = i*4;
            TeamManager_PlayerManager_Customlist_MyData_Player.add(new TeamManager_PlayerManager_Customlist_MyData_Player(parsedData_Player[_i][0],parsedData_Player[_i][1],parsedData_Player[_i][2],parsedData_Player[_i+1][0],parsedData_Player[_i+1][1],parsedData_Player[_i+1][2],parsedData_Player[_i+2][0],parsedData_Player[_i+2][1],parsedData_Player[_i+2][2],parsedData_Player[_i+3][0],parsedData_Player[_i+3][1],parsedData_Player[_i+3][2]));
        }
        if(Extra==0){
        }
        else if(Extra==1){
            TeamManager_PlayerManager_Customlist_MyData_Player.add(new TeamManager_PlayerManager_Customlist_MyData_Player(parsedData_Player[(4*Row)][0],parsedData_Player[(4*Row)][1],parsedData_Player[(4*Row)][2],"null","null","null","null","null","null","null","null","null"));
        }else if(Extra==2){
            TeamManager_PlayerManager_Customlist_MyData_Player.add(new TeamManager_PlayerManager_Customlist_MyData_Player(parsedData_Player[(4*Row)][0],parsedData_Player[(4*Row)][1],parsedData_Player[(4*Row)][2],parsedData_Player[(4*Row)+1][0],parsedData_Player[(4*Row)+1][1],parsedData_Player[(4*Row)+1][2],"null","null","null","null","null","null"));
        }else if(Extra==3){
            TeamManager_PlayerManager_Customlist_MyData_Player.add(new TeamManager_PlayerManager_Customlist_MyData_Player(parsedData_Player[(4*Row)][0],parsedData_Player[(4*Row)][1],parsedData_Player[(4*Row)][2],parsedData_Player[(4*Row)+1][0],parsedData_Player[(4*Row)+1][1],parsedData_Player[(4*Row)+1][2],parsedData_Player[(4*Row)+2][0],parsedData_Player[(4*Row)+2][1],parsedData_Player[(4*Row)+2][2],"null","null","null"));
        }
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}