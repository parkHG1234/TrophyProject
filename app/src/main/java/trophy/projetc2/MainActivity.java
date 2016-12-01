package trophy.projetc2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    protected ImageView Login_ImageView_Main;
    protected EditText Login_EditText_ID;
    protected EditText Login_EditText_PW;
    protected CheckBox Login_Checkbox_Auto;
    protected Button Login_Button_Login;
    protected TextView Login_TextView_Membership;
    protected TextView Login_TextView_FindPW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        Login_ImageView_Main = (ImageView)findViewById(R.id.Login_ImageView_Main);
        Login_EditText_ID = (EditText)findViewById(R.id.Login_EditText_ID);
        Login_EditText_PW = (EditText)findViewById(R.id.Login_EditText_PW);
        Login_Checkbox_Auto = (CheckBox) findViewById(R.id.Login_Checkbox_Auto);
        Login_Button_Login = (Button)findViewById(R.id.Login_Button_Login);
        Login_TextView_Membership = (TextView)findViewById(R.id.Login_TextView_Membership);
        Login_TextView_FindPW = (TextView)findViewById(R.id.Login_TextView_FindPW);


        Login_TextView_Membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent membershipintent =  new Intent(MainActivity.this, MemberShip.class);
                startActivity(membershipintent);
            }
        });
    }
}
