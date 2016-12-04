package trophy.projetc2.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import trophy.projetc2.MainActivity;
import trophy.projetc2.R;

/**
 * Created by 박효근 on 2016-12-01.
 */

public class User_Focus extends AppCompatActivity{
    String Pk, Name, Team, Profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_navigation_user_focus);
        ///인텐트 값 받아온다.
        final Intent CommentIntent = getIntent();
        Pk = CommentIntent.getExtras().getString("Pk");
        Name = CommentIntent.getExtras().getString("Name");
        Team = CommentIntent.getExtras().getString("Team");
        Profile = CommentIntent.getExtras().getString("Profile");

        //인텐트 값 적용
        ImageButton Main_Navigation_User_Focus_ImageButton_Profile = (ImageButton) findViewById(R.id.Main_Navigation_User_Focus_ImageButton_Profile);
        Button Main_Navigation_User_Focus_Button_Name = (Button)findViewById(R.id.Main_Navigation_User_Focus_Button_Name);
        Button Main_Navigation_User_Focus_Button_Team = (Button)findViewById(R.id.Main_Navigation_User_Focus_Button_Team);

        try{
            String Profile1 = URLEncoder.encode(Profile, "utf-8");
            if(Profile1.equals(".")){
                Glide.with(User_Focus.this).load(R.drawable.profile_basic_image)
                        .into(Main_Navigation_User_Focus_ImageButton_Profile);
            }
            else{
                Glide.with(User_Focus.this).load("http://210.122.7.193:8080/Trophy_part1/imgs/Profile/"+Profile1+".jpg") .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(Main_Navigation_User_Focus_ImageButton_Profile);
            }
        }
        catch (UnsupportedEncodingException e){

        }
        Main_Navigation_User_Focus_Button_Name.setText(Name);
        Main_Navigation_User_Focus_Button_Team.setText(Team);


    }
}
