package trophy.projetc2.Score;

import android.app.Activity;

/**
 * Created by 박효근 on 2017-06-28.
 */

public class Score_MyData {
    private String User_Pk;
    private String Match_Pk;
    private String Match_Date;
    private String Match_Place;
    private String Status;
    private String StartTime;
    private String FinishTime;
    private String Home_Emblem;
    private String Home_TeamName;
    private String Away_Emblem;
    private String Away_TeamName;
    private String Game_Status;
    private String Game1_home;
    private String Game1_away;
    private Activity Activity;
    public Score_MyData(String User_Pk, String Match_Pk, String Match_Date, String Match_Place, String Status, String StartTime, String FinishTime, String Home_Emblem, String Home_TeamName, String Away_Emblem, String Away_TeamName, String Game_Status, String Game1_home, String Game1_away, android.app.Activity activity){
        this.Match_Pk = Match_Pk;
        this.Match_Date = Match_Date;
        this.Match_Place = Match_Place;
        this.Status = Status;
        this.StartTime = StartTime;
        this.FinishTime = FinishTime;
        this.Home_Emblem = Home_Emblem;
        this.Status = Status;
        this.Home_TeamName = Home_TeamName;
        this.Away_Emblem = Away_Emblem;
        this.Away_TeamName = Away_TeamName;
        this.Game_Status = Game_Status;
        this.Game1_home = Game1_home;
        this.Game1_away = Game1_away;
        this.Activity = activity;
        this.User_Pk = User_Pk;
    }
    public String getUser_Pk(){return User_Pk;}
    public String getMatch_Pk() {
        return Match_Pk;
    }
    public String getMatch_Date() {
        return Match_Date;
    }
    public String getMatch_Place(){return Match_Place;}
    public String getStatus(){
        return Status;
    }
    public String getStartTime(){
        return StartTime;
    }
    public String getFinishTime(){
        return FinishTime;
    }
    public String getHome_Emblem(){
        return Home_Emblem;
    }
    public String  getHome_TeamName(){
        return Home_TeamName;
    }
    public String getAway_Emblem(){return Away_Emblem;}
    public String getAway_TeamName(){return Away_TeamName;}
    public String getGame_Status(){return Game_Status;}
    public String getGame1_home(){return Game1_home;}
    public String getGame1_away(){return Game1_away;}
    public Activity getActivity(){return Activity;}
}
