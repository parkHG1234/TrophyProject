package trophy.projetc2.OutCourt;

import android.app.Activity;

/**
 * Created by 박효근 on 2017-05-02.
 */

public class OutCourt_CourtInfo_MyData {
    private Activity Activity;
    private String CourtName;
    private String CourtAddress;
    private String Today_Content;
    private String User_Pk;
    private String Court_Pk;
    private String Image;
    public OutCourt_CourtInfo_MyData(String CourtName, String CourtAddress,String Today_Content, Activity activity, String User_Pk, String Court_Pk,String Image){
        this.CourtName = CourtName;
        this.CourtAddress = CourtAddress;
        this.Today_Content = Today_Content;
        this.Activity = activity;
        this.User_Pk = User_Pk;
        this.Court_Pk = Court_Pk;
        this.Image = Image;
    }
    public Activity getActivity(){return  Activity;}
    public String getCourtName(){return CourtName;}
    public String getCourtAddress(){return CourtAddress;}
    public String getTodayWrite(){return Today_Content;}
    public String getUser_Pk(){return User_Pk;}
    public String getCourt_Pk(){return Court_Pk;}
    public String getImage(){return Image;}
}