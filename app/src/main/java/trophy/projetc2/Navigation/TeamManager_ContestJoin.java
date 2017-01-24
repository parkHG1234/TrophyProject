package trophy.projetc2.Navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import trophy.projetc2.R;

/**
 * Created by 박효근 on 2017-01-10.
 */

public class TeamManager_ContestJoin extends Fragment {
    ListView Layout_Navigation_TeamManager_ContestJoin_ListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.layout_navigation_teammanager_contestjoin, container, false);
        Layout_Navigation_TeamManager_ContestJoin_ListView = (ListView)rootView.findViewById(R.id.Layout_Navigation_TeamManager_ContestJoin_ListView);

        return rootView;
    }
}
