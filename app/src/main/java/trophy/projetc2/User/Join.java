package trophy.projetc2.User;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import trophy.projetc2.BaseActivity;
import trophy.projetc2.R;

/**
 * Created by ldong on 2017-02-01.
 */

public class Join extends BaseActivity {
    EditText Join_EditText_Name, Join_EditText_Password, Join_EditText_Password_Confirm, Join_EditText_Birth_Year, Join_EditText_Birth_Month, Join_EditText_Birth_Day, Join_EditText_Phone, Join_EditText_Phone_Confirm;
    CheckBox Join_CheckBox_SexM, Join_CheckBox_SexW;
    Button Join_Button_Phone, Join_Button_Phone_Confirm;
    LinearLayout Join_LinearLayout_Join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_join);

        Join_EditText_Name = (EditText) findViewById(R.id.Join_EditText_Name);
        Join_EditText_Password = (EditText) findViewById(R.id.Join_EditText_Password);
        Join_EditText_Password_Confirm = (EditText) findViewById(R.id.Join_EditText_Password_Confirm);
        Join_EditText_Birth_Year = (EditText) findViewById(R.id.Join_EditText_Birth_Year);
        Join_EditText_Birth_Month = (EditText) findViewById(R.id.Join_EditText_Birth_Month);
        Join_EditText_Birth_Day = (EditText) findViewById(R.id.Join_EditText_Birth_Day);
        Join_EditText_Phone = (EditText) findViewById(R.id.Join_EditText_Phone);
        Join_EditText_Phone_Confirm = (EditText) findViewById(R.id.Join_EditText_Phone_Confirm);
        Join_CheckBox_SexM = (CheckBox) findViewById(R.id.Join_CheckBox_SexM);
        Join_CheckBox_SexW = (CheckBox) findViewById(R.id.Join_CheckBox_SexW);
        Join_Button_Phone = (Button) findViewById(R.id.Join_Button_Phone);
        Join_Button_Phone_Confirm = (Button) findViewById(R.id.Join_Button_Phone_Confirm);
        Join_LinearLayout_Join = (LinearLayout) findViewById(R.id.Join_LinearLayout_Join);

        Join_CheckBox_SexM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Join_CheckBox_SexM.isChecked()) {
                    Join_CheckBox_SexM.setChecked(true);
                }
            }
        });



    }
}
