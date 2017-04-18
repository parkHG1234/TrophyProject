package trophy.projetc2.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import me.drakeet.materialdialog.MaterialDialog;
import trophy.projetc2.BaseActivity;
import trophy.projetc2.R;

/**
 * Created by ldong on 2017-02-01.
 */

public class Terms extends BaseActivity {
    ImageView Terms_ImageView_Back;
    ImageView User_Terms_ImageView_Agree1, User_Terms_ImageView_Agree2, User_Terms_ImageView_Agree3;
    Button User_Terms_Button_Allagree;
    String Agree1 = "disagree", Agree2 = "disagree", Agree3 = "disagree";
    static Activity activity_terms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_terms);
        activity_terms = Terms.this;
        Terms_ImageView_Back  = (ImageView)findViewById(R.id.Terms_ImageView_Back);
        User_Terms_ImageView_Agree1 = (ImageView)findViewById(R.id.User_Terms_ImageView_Agree1);
        User_Terms_ImageView_Agree2 = (ImageView)findViewById(R.id.User_Terms_ImageView_Agree2);
        User_Terms_ImageView_Agree3 = (ImageView)findViewById(R.id.User_Terms_ImageView_Agree3);
        User_Terms_Button_Allagree = (Button)findViewById(R.id.User_Terms_Button_Allagree);

        Terms_ImageView_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            }
        });
        User_Terms_ImageView_Agree1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Agree1.equals("disagree")){
                    User_Terms_ImageView_Agree1.setImageResource(R.drawable.agree);
                    Agree1="agree";
                }
                else{
                    User_Terms_ImageView_Agree1.setImageResource(R.drawable.disagree);
                    Agree1="disagree";
                }
            }
        });
        User_Terms_ImageView_Agree2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Agree2.equals("disagree")){
                    User_Terms_ImageView_Agree2.setImageResource(R.drawable.agree);
                    Agree2="agree";
                }
                else{
                    User_Terms_ImageView_Agree2.setImageResource(R.drawable.disagree);
                    Agree2="disagree";
                }
            }
        });
        User_Terms_ImageView_Agree3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Agree3.equals("disagree")){
                    User_Terms_ImageView_Agree3.setImageResource(R.drawable.agree);
                    Agree3="agree";
                }
                else{
                    User_Terms_ImageView_Agree3.setImageResource(R.drawable.disagree);
                    Agree3="disagree";
                }
            }
        });
        User_Terms_Button_Allagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Agree1.equals("agree")&&Agree2.equals("agree")&&Agree3.equals("agree")){
                    Intent intent1 = new Intent(Terms.this,Join.class);
                    startActivity(intent1);

                }
                else{
                    Snackbar.make(view,"모든 약관의 동의하셔야 가입이 진행됩니다.",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
