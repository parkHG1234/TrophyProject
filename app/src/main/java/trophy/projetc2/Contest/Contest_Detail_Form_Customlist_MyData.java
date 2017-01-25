package trophy.projetc2.Contest;

/**
 * Created by 박효근 on 2016-11-13.
 */

public class Contest_Detail_Form_Customlist_MyData {
    private String Name;
    private String Birth;
    private String Profile;
    private String Pk;
    private String Duty;
    public Contest_Detail_Form_Customlist_MyData(String Name, String Birth, String Profile, String Pk,String Duty){
        this.Name = Name;
        this.Profile = Profile;
        this.Birth = Birth;
        this.Pk = Pk;
        this.Duty = Duty;
    }
    public String getName() {
        return Name;
    }
    public String getProfile(){return Profile;}
    public String getBirth(){
        return Birth;
    }
    public String getPk() {
        return Pk;
    }
    public String getDuty(){
        return Duty;
    }

}
