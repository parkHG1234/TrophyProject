package trophy.projetc2.User;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.MainActivity;
import trophy.projetc2.R;
import trophy.projetc2.SportChoiceActivity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

/**
 * Created by ldong on 2017-01-25.
 */

public class Withdrawal extends AppCompatActivity {
    Button btn_goTeamManage, btn_commit_withdrawal;
    CheckBox chk_withdraw_agreement;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String Pk, TeamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_withdrawal);

        btn_goTeamManage = (Button) findViewById(R.id.btn_goTeamManage);
        btn_commit_withdrawal = (Button) findViewById(R.id.btn_commit_withdrawal);
        chk_withdraw_agreement = (CheckBox) findViewById(R.id.chk_withdraw_agreement);
        preferences = getSharedPreferences("trophy", MODE_PRIVATE);
        Pk = preferences.getString("Pk", ".");
        Intent intent = getIntent();
        TeamName = intent.getStringExtra("TeamName");

        btn_goTeamManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_commit_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_withdraw_agreement.isChecked()) {
                    HttpClient isTeamChief = new HttpClient();
                    String result = isTeamChief.HttpClient("Trophy_part3", "isTeamChief.jsp", Pk);
                    String[][] parsedData = jsonParserListisTeamChief(result);
                    if (parsedData[0][0].equals("true")) { //팀대표가 아닌 경우 회원탈퇴
                        HttpClient Withdrawal = new HttpClient();
                        String result1 = Withdrawal.HttpClient("Trophy_part3", "Withdrawal.jsp", Pk);
                        String[][] parsedData1 = jsonParserListWithdrawal(result1);
                        if (parsedData1[0][0].equals("true")) { //회원탈퇴 완료
                            AlertDialog.Builder alert = new AlertDialog.Builder(Withdrawal.this);
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    preferences = getSharedPreferences("trophy", MODE_PRIVATE);

                                    editor = preferences.edit();
                                    editor.clear();
                                    editor.commit();
                                    startActivity(new Intent(Withdrawal.this, SportChoiceActivity.class));
                                    MainActivity MA = (MainActivity) MainActivity.activity;
                                    ChangePersonalInfoActivity CA = (ChangePersonalInfoActivity) ChangePersonalInfoActivity.activity;
                                    MA.finish();
                                    CA.finish();
                                    finish();
                                    dialog.dismiss();     //닫기
                                }
                            });
                            alert.setMessage("회원탈퇴가 성공적으로 이루어졌습니다");
                            alert.show();
                        } else { //회원탈퇴 실패
                            Snackbar.make(v, "다시 시도해 주세요", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }else { //팀대표인 경우 회원탈퇴 불가능 메시지 표시
                        Snackbar.make(v, "팀대표인 경우 회원탈퇴가 불가능합니다", Snackbar.LENGTH_LONG)
                                .show();
                    }
                }else { //동의하지 않은 경우
                    Snackbar.make(v, "안내사항에 동의해 주세요", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });

    }

    private String[][] jsonParserListisTeamChief(String pRecvServerPage) {
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

    private String[][] jsonParserListWithdrawal(String pRecvServerPage) {
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
}
