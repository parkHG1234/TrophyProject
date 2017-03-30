package trophy.projetc2.Contest;

import android.app.Activity;

/**
 * Created by KimIkJoong on 2016-11-08.
 */

public class Contests_Customlist_MyData {
    String Contest1_Pk;
    String Contest1_Title;
    String Contest1_Image;
    String Contest1_currentNum;
    String Contest1_maxNum;
    String Contest1_Date;
    String Contest1_RecruitFinishDate;
    String Contest1_Place;

    String Contest2_Pk;
    String Contest2_Title;
    String Contest2_Image;
    String Contest2_currentNum;
    String Contest2_maxNum;
    String Contest2_Date;
    String Contest2_RecruitFinishDate;
    String Contest2_Place;

    Activity Activity;
    public Contests_Customlist_MyData(Activity activity, String contest1_Pk, String contest1_Title, String contest1_Image, String contest1_currentNum, String contest1_maxNum, String contest1_Date, String contest1_RecruitFinishDate,String contest1_Place
            , String contest2_Pk, String contest2_Title, String contest2_Image, String contest2_currentNum, String contest2_maxNum, String contest2_Date, String contest2_RecruitFinishDate, String contest2_Place) {

        Activity = activity;

        Contest1_Pk = contest1_Pk;
        Contest1_Title = contest1_Title;
        Contest1_Image = contest1_Image;
        Contest1_currentNum = contest1_currentNum;
        Contest1_maxNum = contest1_maxNum;
        Contest1_Date = contest1_Date;
        Contest1_RecruitFinishDate = contest1_RecruitFinishDate;
        Contest1_Place = contest1_Place;

        Contest2_Pk = contest2_Pk;
        Contest2_Title = contest2_Title;
        Contest2_Image = contest2_Image;
        Contest2_currentNum = contest2_currentNum;
        Contest2_maxNum = contest2_maxNum;
        Contest2_Date = contest2_Date;
        Contest2_RecruitFinishDate = contest2_RecruitFinishDate;
        Contest2_Place = contest2_Place;

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

    public String getContest2_Pk() {
        return Contest2_Pk;
    }
    public String getContest2_Title() {
        return Contest2_Title;
    }
    public String getContest2_Image() {
        return Contest2_Image;
    }
    public String getContest2_currentNum() {
        return Contest2_currentNum;
    }
    public String getContest2_maxNum() {
        return Contest2_maxNum;
    }
    public String getContest2_Date() {
        return Contest2_Date;
    }
    public String getContest2_RecruitFinishDate() {
        return Contest2_RecruitFinishDate;
    }
    public String getContest2_Place() {
        return Contest2_Place;
    }

}
