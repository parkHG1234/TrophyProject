package trophy.projetc2.Navigation;


/**
 * Created by 박효근 on 2016-12-02.
 */

public class TeamSearch_CustomList_MyData {
    private String Emblem;
    private String TeamName;
    private String Pk;
    public TeamSearch_CustomList_MyData(String Pk,String TeamName, String Emblem){
        this.Pk = Pk;
        this.Emblem = Emblem;
        this.TeamName = TeamName;
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
}
