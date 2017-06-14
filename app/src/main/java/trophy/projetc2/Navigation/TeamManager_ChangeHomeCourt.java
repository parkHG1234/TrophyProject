package trophy.projetc2.Navigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-06-04.
 */

public class TeamManager_ChangeHomeCourt extends AppCompatActivity{
    ImageView TeamManger_Change_HomeCourt_ImageView_Back;
    EditText TeamManger_Change_EditText_HomeCourt;
    Button TeamManger_Change_HomeCourt_Button_Input;
    int rnd;
    String Team_Pk, HomeCourt;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_teammanager_change_homecourt);
        Intent intent = getIntent();
        Team_Pk = intent.getStringExtra("Team_Pk");

        TeamManger_Change_HomeCourt_ImageView_Back = (ImageView)findViewById(R.id.TeamManger_Change_HomeCourt_ImageView_Back);
        TeamManger_Change_EditText_HomeCourt = (EditText) findViewById(R.id.TeamManger_Change_EditText_HomeCourt);
        TeamManger_Change_HomeCourt_Button_Input = (Button) findViewById(R.id.TeamManger_Change_HomeCourt_Button_Input);

        TeamManger_Change_HomeCourt_Button_Input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeCourt = TeamManger_Change_EditText_HomeCourt.getText().toString();
                HttpClient homecourt = new HttpClient();
                String result = homecourt.HttpClient("Trophy_part1", "TeamManager_ChangeHomeCourt.jsp",Team_Pk, HomeCourt);
                if (result.equals("succed")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(TeamManager_ChangeHomeCourt.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();     //닫기
                            finish();
                            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                        }
                    });
                    alert.setMessage("홈코트가 변경되었습니다");
                    alert.show();
                } else {
                    Snackbar.make(v, "잠시 후 다시 시도해주시기 바랍니다.", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        TeamManger_Change_HomeCourt_ImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
    }

    private String[][] jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jarr = json.getJSONArray("List");

            String[] jsonName = {"msg1"};
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

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
