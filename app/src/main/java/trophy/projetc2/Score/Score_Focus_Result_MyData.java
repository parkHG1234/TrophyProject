package trophy.projetc2.Score;

/**
 * Created by 박효근 on 2017-07-10.
 */

public class Score_Focus_Result_MyData {
    private String Home_Emblem;
    private String Home_TeamName;
    private String Away_Emblem;
    private String Away_TeamName;
    private String Home_Score;
    private String Away_Score;
    public Score_Focus_Result_MyData(String Home_Emblem, String Home_TeamName, String Away_Emblem, String Away_TeamName, String Home_Score, String Away_Score){
        this.Home_Emblem = Home_Emblem;
        this.Home_TeamName = Home_TeamName;
        this.Away_Emblem = Away_Emblem;
        this.Away_TeamName = Away_TeamName;
        this.Home_Score = Home_Score;
        this.Away_Score = Away_Score;
    }
    public String getHome_Emblem(){
        return Home_Emblem;
    }
    public String  getHome_TeamName(){
        return Home_TeamName;
    }
    public String getAway_Emblem(){return Away_Emblem;}
    public String getAway_TeamName(){return Away_TeamName;}
    public String getHome_Score(){return Home_Score;}
    public String getAway_Score(){return Away_Score;}
}

