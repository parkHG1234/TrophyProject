package trophy.projetc2.User;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import trophy.projetc2.Http.HttpClient;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-06-16.
 */

public class ChangeHWP  extends AppCompatActivity {
    String Height, Weight, Position;
    EditText User_Change_hwp_EditText_Height, User_Change_hwp_EditText_Weight;
    CheckBox User_Change_hwp_CheckBox_PG, User_Change_hwp_CheckBox_SG, User_Change_hwp_CheckBox_SF, User_Change_hwp_CheckBox_PF, User_Change_hwp_CheckBox_C;
    Button User_Change_hwp_Button_Input;
    ImageView User_Change_hwp_ImageView_Back;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_change_hwp);

        Intent intent = getIntent();
        Height = intent.getStringExtra("Height");
        Weight = intent.getStringExtra("Weight");
        Position = intent.getStringExtra("Position");

        User_Change_hwp_ImageView_Back = (ImageView)findViewById(R.id.User_Change_hwp_ImageView_Back);
        User_Change_hwp_EditText_Height = (EditText) findViewById(R.id.User_Change_hwp_EditText_Height);
        User_Change_hwp_EditText_Weight = (EditText) findViewById(R.id.User_Change_hwp_EditText_Weight);
        User_Change_hwp_CheckBox_PG = (CheckBox) findViewById(R.id.User_Change_hwp_CheckBox_PG);
        User_Change_hwp_CheckBox_SG = (CheckBox) findViewById(R.id.User_Change_hwp_CheckBox_SG);
        User_Change_hwp_CheckBox_SF = (CheckBox) findViewById(R.id.User_Change_hwp_CheckBox_SF);
        User_Change_hwp_CheckBox_PF = (CheckBox) findViewById(R.id.User_Change_hwp_CheckBox_PF);
        User_Change_hwp_CheckBox_C = (CheckBox) findViewById(R.id.User_Change_hwp_CheckBox_C);
        User_Change_hwp_Button_Input = (Button) findViewById(R.id.User_Change_hwp_Button_Input);

        User_Change_hwp_EditText_Height.setText(Height);User_Change_hwp_EditText_Weight.setText(Weight);
        if(Position.equals("PG")){
            User_Change_hwp_CheckBox_PG.setChecked(true);
        }else if(Position.equals("SG")){
            User_Change_hwp_CheckBox_SG.setChecked(true);
        }else if(Position.equals("SF")){
            User_Change_hwp_CheckBox_SF.setChecked(true);
        }else if(Position.equals("PF")){
            User_Change_hwp_CheckBox_PF.setChecked(true);
        }else{
            User_Change_hwp_CheckBox_C.setChecked(true);
        }
        User_Change_hwp_CheckBox_PG.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    User_Change_hwp_CheckBox_SG.setChecked(false);User_Change_hwp_CheckBox_SF.setChecked(false);User_Change_hwp_CheckBox_PF.setChecked(false);
                    User_Change_hwp_CheckBox_C.setChecked(false);
                    Position = "PG";
                }
            }
        });
        User_Change_hwp_CheckBox_SG.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    User_Change_hwp_CheckBox_PG.setChecked(false);User_Change_hwp_CheckBox_SF.setChecked(false);User_Change_hwp_CheckBox_PF.setChecked(false);
                    User_Change_hwp_CheckBox_C.setChecked(false);
                    Position = "SG";
                }
            }
        });
        User_Change_hwp_CheckBox_SF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    User_Change_hwp_CheckBox_SG.setChecked(false);User_Change_hwp_CheckBox_PG.setChecked(false);User_Change_hwp_CheckBox_PF.setChecked(false);
                    User_Change_hwp_CheckBox_C.setChecked(false);
                    Position = "SF";
                }
            }
        });
        User_Change_hwp_CheckBox_PF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    User_Change_hwp_CheckBox_SG.setChecked(false);User_Change_hwp_CheckBox_SF.setChecked(false);User_Change_hwp_CheckBox_PG.setChecked(false);
                    User_Change_hwp_CheckBox_C.setChecked(false);
                    Position = "PG";
                }
            }
        });
        User_Change_hwp_CheckBox_C.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    User_Change_hwp_CheckBox_SG.setChecked(false);User_Change_hwp_CheckBox_SF.setChecked(false);User_Change_hwp_CheckBox_PG.setChecked(false);
                    User_Change_hwp_CheckBox_PG.setChecked(false);
                    Position = "C";
                }
            }
        });
        User_Change_hwp_Button_Input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = getSharedPreferences("trophy", MODE_PRIVATE);
                String Pk = preferences.getString("Pk", ".");
                HttpClient http_change = new HttpClient();
                String result = http_change.HttpClient("Trophy_part1", "User_Change_HWP.jsp", Pk, User_Change_hwp_EditText_Height.getText().toString(), User_Change_hwp_EditText_Weight.getText().toString(), Position);
                if (result.equals("succed")) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ChangeHWP.this);
                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ChangeHWP.this, ChangePersonalInfoActivity.class);
                            finish();
                            ChangePersonalInfoActivity CA = (ChangePersonalInfoActivity) ChangePersonalInfoActivity.activity;
                            CA.finish();
                            startActivity(intent);
                            dialog.dismiss();     //닫기
                        }
                    });
                    alert.setMessage("개인정보가 변경되었습니다.");
                    alert.show();
                } else {
                    Snackbar.make(getCurrentFocus(), "다시 시도해 주세요", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        User_Change_hwp_ImageView_Back.setOnClickListener(new View.OnClickListener() {
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
