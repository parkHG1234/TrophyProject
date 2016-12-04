package trophy.projetc2.User;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.MainActivity;
import trophy.projetc2.R;

public class Login extends AppCompatActivity {
    protected ImageView Login_ImageView_Main;
    protected EditText Login_EditText_Phone;
    protected EditText Login_EditText_PW;
    protected CheckBox Login_Checkbox_Auto;
    protected Button Login_Button_Login;
    protected TextView Login_TextView_Membership;
    protected TextView Login_TextView_FindPW;

    protected  String Phone=null;
    protected  String Password=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        Login_ImageView_Main = (ImageView)findViewById(R.id.Login_ImageView_Main);
        Login_EditText_Phone = (EditText)findViewById(R.id.Login_EditText_Phone);
        Login_EditText_PW = (EditText)findViewById(R.id.Login_EditText_PW);
        Login_Checkbox_Auto = (CheckBox) findViewById(R.id.Login_Checkbox_Auto);
        Login_Button_Login = (Button)findViewById(R.id.Login_Button_Login);
        Login_TextView_Membership = (TextView)findViewById(R.id.Login_TextView_Membership);
        Login_TextView_FindPW = (TextView)findViewById(R.id.Login_TextView_FindPW);




        Login_Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Phone = Login_EditText_Phone.getText().toString();
                Password = Login_EditText_PW.getText().toString();

                HttpClient user = new HttpClient();
                String result = user.HttpClient("Trophy_part2", "Login_Confirm.jsp",Phone,Password);
                String[][] parseData = jsonParserList(result);

                if(parseData[0][0].equals("isOk")){
                    Intent MainActivityintent =  new Intent(Login.this, MainActivity.class);
                    startActivity(MainActivityintent);
                }else{
                    Snackbar.make(getCurrentFocus(), "비밀번호를확인해주세요", Snackbar.LENGTH_SHORT).show();
                }
                Log.i("Aaa",parseData[0][0]);
            }
        });

        Login_TextView_Membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent membershipintent =  new Intent(Login.this, MemberShip.class);
                startActivity(membershipintent);
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
}



