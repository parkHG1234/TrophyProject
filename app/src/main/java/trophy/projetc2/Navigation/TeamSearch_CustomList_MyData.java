package trophy.projetc2.Navigation;


/**
 * Created by 박효근 on 2016-12-02.
 */

public class TeamSearch_CustomList_MyData {
    private String Emblem;
    private String TeamName;
    private String Team_Pk;
    private String Pk;
    public TeamSearch_CustomList_MyData(String Pk,String TeamName, String Emblem,String Team_Pk){
        this.Pk = Pk;
        this.Emblem = Emblem;
        this.TeamName = TeamName;
        this.Team_Pk = Team_Pk;
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
}
