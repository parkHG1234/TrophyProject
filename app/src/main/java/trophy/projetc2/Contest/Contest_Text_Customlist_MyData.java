package trophy.projetc2.Contest;

import android.app.Activity;

/**
 * Created by 박효근 on 2017-08-05.
 */

public class Contest_Text_Customlist_MyData {
    String Contest1_Pk;
    String Contest1_Title;
    String Contest1_Image;
    String Contest1_currentNum;
    String Contest1_maxNum;
    String Contest1_Date;
    String Contest1_RecruitFinishDate;
    String Contest1_Place;

    android.app.Activity Activity;
    public Contest_Text_Customlist_MyData(Activity activity, String contest1_Pk, String contest1_Title, String contest1_Image, String contest1_currentNum, String contest1_maxNum, String contest1_Date, String contest1_RecruitFinishDate,String contest1_Place
    ) {

        Activity = activity;

        Contest1_Pk = contest1_Pk;
        Contest1_Title = contest1_Title;
        Contest1_Image = contest1_Image;
        Contest1_currentNum = contest1_currentNum;
        Contest1_maxNum = contest1_maxNum;
        Contest1_Date = contest1_Date;
        Contest1_RecruitFinishDate = contest1_RecruitFinishDate;
        Contest1_Place = contest1_Place;


    }
    public Activity getActivity(){
        return Activity;
    }

    public String getContest1_Pk() {
        return Contest1_Pk;
    }
    public String getContest1_Title() {
        return Contest1_Title;
    }
    public String getContest1_Image() {
        return Contest1_Image;
    }
    public String getContest1_currentNum() {
        return Contest1_currentNum;
    }
    public String getContest1_maxNum() {
        return Contest1_maxNum;
    }
    public String getContest1_Date() {
        return Contest1_Date;
    }
    public String getContest1_RecruitFinishDate() {
        return Contest1_RecruitFinishDate;
    }
    public String getContest1_Place() {
        return Contest1_Place;
    }


}

