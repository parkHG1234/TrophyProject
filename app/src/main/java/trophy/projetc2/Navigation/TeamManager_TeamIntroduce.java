package trophy.projetc2.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-01-10.
 */

public class TeamManager_TeamIntroduce extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.layout_navigation_teammanager_teamintroduce, container, false);

        return rootView;
    }
}