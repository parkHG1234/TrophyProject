package trophy.projetc2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by ldong on 2017-01-16.
 */

public class SportChoiceActivity extends AppCompatActivity {
    Button Button_Basketball, Button_Baseball, Button_Coach, Button_Balling, Button_Biking, Button_Soccer;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_choice_sport);

        preferences = getSharedPreferences("trophy", MODE_PRIVATE);
        String sport = preferences.getString("sportType",".");
        Log.i("스포츠 타입은 : ", sport);

        if (sport.equals(".") || sport.equals("")) {
        } else {
            startActivity(new Intent(SportChoiceActivity.this, MainActivity.class));
            finish();
        }


        Button_Basketball = (Button) findViewById(R.id.layout_choice_basketball_button);
        Button_Baseball = (Button) findViewById(R.id.layout_choice_baseball_button);
        Button_Coach = (Button) findViewById(R.id.layout_choice_coach_button);
        Button_Balling = (Button) findViewById(R.id.layout_choice_balling_button);
        Button_Biking = (Button) findViewById(R.id.layout_choice_biking_button);
        Button_Soccer = (Button) findViewById(R.id.layout_choice_soccer_button);

        Button_Basketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor = preferences.edit();
                editor.putString("sportType","basketball");
                editor.commit();
                startActivity(new Intent(SportChoiceActivity.this, MainActivity.class));
                finish();
            }
        });
        Button_Baseball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "준비중 입니다.", Snackbar.LENGTH_LONG)
                        .show();

                editor = preferences.edit();
                editor.putString("sportType","baseball");
                editor.commit();
                startActivity(new Intent(SportChoiceActivity.this, MainActivity.class));
                finish();
            }
        });
        Button_Coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "준비중 입니다.", Snackbar.LENGTH_LONG)
                        .show();

                editor = preferences.edit();
                editor.putString("sportType","Coach");
                editor.commit();
                startActivity(new Intent(SportChoiceActivity.this, MainActivity.class));
                finish();
            }
        });
        Button_Balling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "준비중 입니다.", Snackbar.LENGTH_LONG)
                        .show();

                editor = preferences.edit();
                editor.putString("sportType","balling");
                editor.commit();
                startActivity(new Intent(SportChoiceActivity.this, MainActivity.class));
                finish();
            }
        });
        Button_Biking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "준비중 입니다.", Snackbar.LENGTH_LONG)
                        .show();

                editor = preferences.edit();
                editor.putString("sportType","biking");
                editor.commit();
                startActivity(new Intent(SportChoiceActivity.this, MainActivity.class));
                finish();
            }
        });
        Button_Soccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "준비중 입니다.", Snackbar.LENGTH_LONG)
                        .show();

                editor = preferences.edit();
                editor.putString("sportType","soccer");
                editor.commit();
                startActivity(new Intent(SportChoiceActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
