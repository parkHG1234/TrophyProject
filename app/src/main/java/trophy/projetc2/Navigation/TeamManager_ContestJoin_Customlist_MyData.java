package trophy.projetc2.Navigation;

/**
 * Created by 박효근 on 2017-01-24.
 */

public class TeamManager_ContestJoin_Customlist_MyData {
    private String Contest_Pk;
    private String Contest_Image;
    private String Contest_Title;
    private String Contest_Status;
    public TeamManager_ContestJoin_Customlist_MyData(String Contest_Pk, String Contest_Image, String Contest_Title, String Contest_Status){
        this.Contest_Pk = Contest_Pk;
        this.Contest_Image = Contest_Image;
        this.Contest_Title = Contest_Title;
        this.Contest_Status = Contest_Status;
    }
    public String getContest_Pk(){
        return Contest_Pk;
    }
    public String getContest_Image(){
        return Contest_Image;
    }
    public String getContest_Title(){
        return Contest_Title;
    }
    public String getContest_Status(){
        return Contest_Status;
    }
}
