package trophy.projetc2.Match;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-04-17.
 */

public class MyMatch extends AppCompatActivity{
    ListView MyMatch_ListView_List;
    ImageView MyMatch_ImageView_Back;
    String[][] parsedData_Match;
    String Team_Pk, Team_Duty, User_Pk;
    ArrayList<MyMatch_MyData> MyMatch_MyData;
    MyMatch_MyAdapter adapter;
    boolean lastitemVisibleFlag = false;
    int ContentCount = 100000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_mymatch);
        Intent intent1 = getIntent();
        User_Pk = intent1.getStringExtra("User_Pk");
        Team_Pk = intent1.getStringExtra("Team_Pk");
        Team_Duty = intent1.getStringExtra("Team_Duty");

        MyMatch_ImageView_Back = (ImageView)findViewById(R.id.MyMatch_ImageView_Back);
        MyMatch_ListView_List = (ListView)findViewById(R.id.MyMatch_ListView_List);

        HttpClient http_match = new HttpClient();
        String result = http_match.HttpClient("Trophy_part1","MyMatch.jsp", User_Pk);
        parsedData_Match = jsonParserList_Match(result);


        MyMatch_MyData = new ArrayList<MyMatch_MyData>();
        for (int i = 0; i < parsedData_Match.length; i++) {
            MyMatch_MyData.add(new MyMatch_MyData(parsedData_Match[i][0], parsedData_Match[i][1], parsedData_Match[i][2],parsedData_Match[i][3],parsedData_Match[i][4],parsedData_Match[i][5],parsedData_Match[i][6],parsedData_Match[i][7],MyMatch.this,User_Pk,parsedData_Match[i][8],parsedData_Match[i][9]));
            ContentCount = Integer.parseInt(parsedData_Match[i][0]);
        }
        adapter = new MyMatch_MyAdapter(this, MyMatch_MyData);
        MyMatch_ListView_List.setAdapter(adapter);

        MyMatch_ImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
        MyMatch_ListView_List.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if(i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastitemVisibleFlag) {
                    MyMatch_Scroll MyMatch_Scroll = new MyMatch_Scroll();
                    MyMatch_Scroll.execute();
                }
                Log.i("tt123", Integer.toString(ContentCount));
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastitemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
    public String[][] jsonParserList_Match(String pRecvServerPage){
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try{
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1","msg2","msg3","msg4","msg5","msg6","msg7","msg8","msg9","msg10"};
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
    public class MyMatch_Scroll extends AsyncTask<String, Void, String> {
        ProgressDialog asyncDialog = new ProgressDialog(MyMatch.this);
        String[][] parsedData;

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("잠시만 기다려주세요..");
            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient http_match = new HttpClient();
                String result = http_match.HttpClient("Trophy_part1","MyMatch_Scroll.jsp", User_Pk, Integer.toString(ContentCount));
                parsedData_Match = jsonParserList_Match(result);

                for (int i = 0; i < parsedData_Match.length; i++) {
                    MyMatch_MyData.add(new MyMatch_MyData(parsedData_Match[i][0], parsedData_Match[i][1], parsedData_Match[i][2],parsedData_Match[i][3],parsedData_Match[i][4],parsedData_Match[i][5],parsedData_Match[i][6],parsedData_Match[i][7],MyMatch.this,User_Pk,parsedData_Match[i][8],parsedData_Match[i][9]));
                    ContentCount = Integer.parseInt(parsedData_Match[i][0]);
                }
                adapter.notifyDataSetChanged();
                return "succed";
            } catch (Exception e) {
                e.printStackTrace();
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {

            asyncDialog.dismiss();
            super.onPostExecute(result);
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        HttpClient http_match = new HttpClient();
        String result = http_match.HttpClient("Trophy_part1","MyMatch.jsp", User_Pk);
        parsedData_Match = jsonParserList_Match(result);


        MyMatch_MyData = new ArrayList<MyMatch_MyData>();
        for (int i = 0; i < parsedData_Match.length; i++) {
            MyMatch_MyData.add(new MyMatch_MyData(parsedData_Match[i][0], parsedData_Match[i][1], parsedData_Match[i][2],parsedData_Match[i][3],parsedData_Match[i][4],parsedData_Match[i][5],parsedData_Match[i][6],parsedData_Match[i][7],MyMatch.this,User_Pk,parsedData_Match[i][8],parsedData_Match[i][9]));
            ContentCount = Integer.parseInt(parsedData_Match[i][0]);
        }
        adapter = new MyMatch_MyAdapter(this, MyMatch_MyData);
        MyMatch_ListView_List.setAdapter(adapter);
    }
}

