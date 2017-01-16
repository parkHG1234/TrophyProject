package trophy.projetc2.Navigation;

/**
 * Created by 박효근 on 2017-01-14.
 */

public class TeamManager_PlayerManager_Customlist_MyData {
    private String First_Profile;
    private String First_Name;
    private String First_Pk;
    private String Second_Profile;
    private String Second_Name;
    private String Second_Pk;
    private String Third_Profile;
    private String Third_Name;
    private String Third_Pk;
    public TeamManager_PlayerManager_Customlist_MyData(String First_Profile, String First_Name, String First_Pk, String Second_Profile, String Second_Name, String Second_Pk, String Third_Profile, String Third_Name,String Third_Pk){
        this.First_Profile = First_Profile;
        this.First_Name = First_Name;
        this.First_Pk = First_Pk;
        this.Second_Profile = Second_Profile;
        this.Second_Name = Second_Name;
        this.Second_Pk = Second_Pk;
        this.Third_Profile = Third_Profile;
        this.Third_Name = Third_Name;
        this.Third_Pk = Third_Pk;
    }
    public String getFirst_Profile() {
        return First_Profile;
    }
    public String getFirst_Name() {
        return First_Name;
    }
    public String getFirst_Pk(){return First_Pk;}
    public String getSecond_Profile(){
        return Second_Profile;
    }
    public String getSecond_Name(){
        return Second_Name;
    }
    public String getSecond_Pk(){
        return Second_Pk;
    }
    public String getThird_Profile(){
        return Third_Profile;
    }
    public String getThird_Name() {
        return Third_Name;
    }
    public String getThird_Pk() {
        return Third_Pk;
    }
}
