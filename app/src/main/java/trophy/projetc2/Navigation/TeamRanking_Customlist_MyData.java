package trophy.projetc2.Navigation;

/**
 * Created by 박효근 on 2017-03-23.
 */

public class TeamRanking_Customlist_MyData {
    private String Team_Point;
    private String Emblem;
    private String TeamName;
    private String Team_Pk;
    private String Pk;
    private int Team_Ranking;
    public TeamRanking_Customlist_MyData(String Pk,String TeamName, String Emblem,String Team_Pk, String Team_Point, int Team_Ranking){
        this.Pk = Pk;
        this.Emblem = Emblem;
        this.TeamName = TeamName;
        this.Team_Pk = Team_Pk;
        this.Team_Point = Team_Point;
        this.Team_Ranking = Team_Ranking;
    }
    public String getEmblem() {
        return Emblem;
    }
    public String getTeamName() {
        return TeamName;
    }
    public String getPk(){
        return Pk;
    }
    public String getTeam_Pk(){
        return Team_Pk;
    }
    public String getTeam_Point(){
        return Team_Point;
    }
    public int getTeam_Ranking(){return Team_Ranking;}
}

