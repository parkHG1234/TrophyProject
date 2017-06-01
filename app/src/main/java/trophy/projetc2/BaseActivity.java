package trophy.projetc2;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by ldong on 2017-02-01.
 */

public class BaseActivity extends Application {

    @Override public void onCreate() {
        super.onCreate();
        // 폰트 정의
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/BMJUA_ttf.ttf"))
                .addBold(Typekit.createFromAsset(this, "fonts/BMJUA_ttf.ttf"));


    }

}
