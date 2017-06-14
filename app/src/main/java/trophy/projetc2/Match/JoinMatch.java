package trophy.projetc2.Match;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-05-25.
 */

public class JoinMatch extends AppCompatActivity{
    ListView JoinMatch_ListView_List;
    ImageView JoinMatch_ImageView_Back;
    String[][] parsedData_JoinMatch;
    String Team_Pk, Team_Duty, User_Pk;
    int ContentCount = 100000;
    boolean lastitemVisibleFlag = false;
    JoinMatch_MyAdapter adapter;
    ArrayList<JoinMatch_MyData> JoinMatch_MyData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_joinmatch);
        Intent intent1 = getIntent();
        User_Pk = intent1.getStringExtra("User_Pk");
        Team_Pk = intent1.getStringExtra("Team_Pk");
        Team_Duty = intent1.getStringExtra("Team_Duty");

        JoinMatch_ImageView_Back = (ImageView)findViewById(R.id.JoinMatch_ImageView_Back);
        JoinMatch_ListView_List = (ListView)findViewById(R.id.JoinMatch_ListView_List);

        HttpClient http_joinmatch = new HttpClient();
        String result = http_joinmatch.HttpClient("Trophy_part1","JoinMatch.jsp",User_Pk);
        parsedData_JoinMatch = jsonParserList_JoinMatch(result);

        JoinMatch_MyData = new ArrayList<JoinMatch_MyData>();
        for (int i = 0; i < parsedData_JoinMatch.length; i++) {
            JoinMatch_MyData.add(new JoinMatch_MyData(parsedData_JoinMatch[i][0], parsedData_JoinMatch[i][1], parsedData_JoinMatch[i][2],parsedData_JoinMatch[i][3],parsedData_JoinMatch[i][4],parsedData_JoinMatch[i][5],parsedData_JoinMatch[i][6],parsedData_JoinMatch[i][7],JoinMatch.this,User_Pk,parsedData_JoinMatch[i][8]));
        }
        Log.i("tt123", Integer.toString(ContentCount));
        adapter = new JoinMatch_MyAdapter(this, JoinMatch_MyData);
        JoinMatch_ListView_List.setAdapter(adapter);
//        Match_ImageView_Write.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(User_Pk.equals(".")){
//                    Intent intent_login = new Intent(Match.this, Login.class);
//                    startActivity(intent_login);
//                    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
//                }
//                else{
//                    if(Team_Duty.equals("팀원")||Team_Duty.equals("팀대표")){
//                        Intent intent1 = new Intent(Match.this, Match_Write.class);
//                        intent1.putExtra("Team_Duty", Team_Duty);
//                        intent1.putExtra("Team_Pk", Team_Pk);
//                        intent1.putExtra("User_Pk", User_Pk);
//                        startActivity(intent1);
//                        overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
//                    }
//                    else{
//                        Snackbar.make(view, "팀 가입 후 이용해주세요.", Snackbar.LENGTH_LONG).show();
//                    }
//                }
//            }
//        });
//
//        Match_ListView_List.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//
//
//            public void onScrollStateChanged(AbsListView absListView, int i) {
//                if(i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastitemVisibleFlag) {
//                    Match_Scroll Match_Scroll = new Match_Scroll();
//                    Match_Scroll.execute();
//                }
//                Log.i("tt123", Integer.toString(ContentCount));
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                lastitemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
//            }
//        });
        JoinMatch_ImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        HttpClient http_joinmatch = new HttpClient();
        String result = http_joinmatch.HttpClient("Trophy_part1","JoinMatch.jsp",User_Pk);
        parsedData_JoinMatch = jsonParserList_JoinMatch(result);

        JoinMatch_MyData = new ArrayList<JoinMatch_MyData>();
        for (int i = 0; i < parsedData_JoinMatch.length; i++) {
            JoinMatch_MyData.add(new JoinMatch_MyData(parsedData_JoinMatch[i][0], parsedData_JoinMatch[i][1], parsedData_JoinMatch[i][2],parsedData_JoinMatch[i][3],parsedData_JoinMatch[i][4],parsedData_JoinMatch[i][5],parsedData_JoinMatch[i][6],parsedData_JoinMatch[i][7],JoinMatch.this,User_Pk,parsedData_JoinMatch[i][8]));
        }
        Log.i("tt123", Integer.toString(ContentCount));
        adapter = new JoinMatch_MyAdapter(this, JoinMatch_MyData);
        JoinMatch_ListView_List.setAdapter(adapter);
    }

    public String[][] jsonParserList_JoinMatch(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8","msg9"};
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
//    public class Match_Scroll extends AsyncTask<String, Void, String> {
//        ProgressDialog asyncDialog = new ProgressDialog(Match.this);
//        String[][] parsedData;
//
//        @Override
//        protected void onPreExecute() {
//            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            asyncDialog.setMessage("잠시만 기다려주세요..");
//            // show dialog
//            asyncDialog.show();
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                HttpClient http_match = new HttpClient();
//                String result = http_match.HttpClient("Trophy_part1","Match_Scroll.jsp",Integer.toString(ContentCount));
//                parsedData_Match = jsonParserList_Match(result);
//
//                for (int j = 0; j < parsedData_Match.length; j++) {
//                    Match_MyData.add(new Match_MyData(parsedData_Match[j][0], parsedData_Match[j][1], parsedData_Match[j][2],parsedData_Match[j][3],parsedData_Match[j][4],parsedData_Match[j][5],parsedData_Match[j][6],parsedData_Match[j][7],Match.this,User_Pk,parsedData_Match[j][9],Team_Pk));
//                    ContentCount = Integer.parseInt(parsedData_Match[j][8]);
//                }
//                adapter.notifyDataSetChanged();
//                return "succed";
//            } catch (Exception e) {
//                e.printStackTrace();
//                return "failed";
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//
//            asyncDialog.dismiss();
//            super.onPostExecute(result);
//        }
//    }
}

