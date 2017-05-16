package trophy.projetc2.Match;

import android.app.Activity;

/**
 * Created by 박효근 on 2017-04-18.
 */

public class MyMatch_Focus_Joiner_MyData {
    private String Match_Joiner_Pk;
    private String User_Pk;
    private String Match_Pk;
    private String Time;
    private String Memo;
    private String Emblem;
    private String TeamName;
    private String Team_Pk;
    private Activity activity;
    private String Phone;
    public MyMatch_Focus_Joiner_MyData(String Match_Joiner_Pk, String User_Pk, String Match_Pk, String Time, String Memo,String Emblem, String TeamName, String Team_Pk, Activity activity,String Phone){
        this.Match_Joiner_Pk = Match_Joiner_Pk;
        this.User_Pk = User_Pk;
        this.Match_Pk = Match_Pk;
        this.Emblem = Emblem;
        this.TeamName = TeamName;
        this.Memo = Memo;
        this.Time = Time;
        this.Team_Pk = Team_Pk;
        this.activity = activity;
        this.Phone = Phone;
    }
    public String getMatch_Joiner_Pk() {
        return Match_Joiner_Pk;
    }
    public String getEmblem() {
        return Emblem;
    }
    public String getTeamName(){return TeamName;}
    public String getMemo(){
        return Memo;
    }
    public String getTime() {
        return Time;
    }
    public String getUser_Pk(){return User_Pk;}
    public String getMatch_Pk(){return Match_Pk;}
    public String getTeam_Pk(){return Team_Pk;}
    public Activity getActivity(){return activity;}
    public String getPhone(){return Phone;}
}

