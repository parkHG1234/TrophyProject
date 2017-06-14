package trophy.projetc2.OutCourt;

import android.app.Activity;

/**
 * Created by 박효근 on 2017-06-10.
 */

public class OutCourt_CourtInfo_Focus_Comment_MyData {
    private String OutCourt_Content_Comment_Pk;
    private String OutCourt_Content_Pk;
    private String User_Pk;
    private String Date;
    private String Memo;
    private Activity activity;
    private String MyUser_Pk;
    public OutCourt_CourtInfo_Focus_Comment_MyData(String OutCourt_Content_Comment_Pk, String OutCourt_Content_Pk,String User_Pk, String Date, String Memo, Activity activity,String MyUser_Pk){
        this.OutCourt_Content_Comment_Pk = OutCourt_Content_Comment_Pk;
        this.OutCourt_Content_Pk = OutCourt_Content_Pk;
        this.User_Pk = User_Pk;
        this.Date = Date;
        this.Memo = Memo;
        this.activity = activity;
        this.MyUser_Pk = MyUser_Pk;
    }
    public String getOutCourt_Content_Comment_Pk(){return OutCourt_Content_Comment_Pk;}
    public String getOutCourt_Content_Pk(){return OutCourt_Content_Pk;}
    public String getDate(){return Date;}
    public String getMemo(){return Memo;}
    public Activity getActivity(){return activity;}
    public String getUser_Pk(){return User_Pk;}
    public String getMyUser_Pk(){return MyUser_Pk;}
}
