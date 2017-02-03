package trophy.projetc2;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by ldong on 2017-01-31.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "NanumGothic.ttf"))
                .addBold(Typekit.createFromAsset(this, "NanumGothicBold.ttf"));
    }


}


