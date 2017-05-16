package trophy.projetc2.OutCourt;

import android.app.Activity;

/**
 * Created by 박효근 on 2017-05-07.
 */

public class OutCourt_CourtInfo_Focus_MyData {
    private String OutCourt_Content_Pk;
    private String OutCourt_Pk;
    private String Content_User_Pk;
    private String Date;
    private String OutCourt_Content;
    private String User_Profile;
    private String User_Name;
    private String Now_Date;
    private Activity activity;
    private String User_Pk;
    public OutCourt_CourtInfo_Focus_MyData(String OutCourt_Content_Pk, String OutCourt_Pk,String Content_User_Pk, String Date, String OutCourt_Content, String User_Profile, String User_Name, String Now_Date, Activity activity,String User_Pk){
        this.OutCourt_Content_Pk = OutCourt_Content_Pk;
        this.OutCourt_Pk = OutCourt_Pk;
        this.Content_User_Pk = Content_User_Pk;
        this.Date = Date;
        this.OutCourt_Content = OutCourt_Content;
        this.User_Profile = User_Profile;
        this.User_Name = User_Name;
        this.Now_Date = Now_Date;
        this.activity = activity;
        this.User_Pk = User_Pk;
    }
    public String getOutCourt_Content_Pk(){return OutCourt_Content_Pk;}
    public String getOutCourt_Pk(){return OutCourt_Pk;}
    public String getContent_User_Pk(){return Content_User_Pk;}
    public String getDate(){return Date;}
    public String getOutCourt_Content(){return OutCourt_Content;}
    public String getUser_Profile(){return User_Profile;}
    public String getUser_Name(){return User_Name;}
    public String getNow_Date(){return Now_Date;}
    public Activity getActivity(){return activity;}
    public String getUser_Pk(){return User_Pk;}
}
