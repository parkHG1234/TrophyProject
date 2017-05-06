package trophy.projetc2.Navigation;

import android.app.Activity;

/**
 * Created by 박효근 on 2017-05-02.
 */

public class OutCourt_CourtInfo_MyData {
    private Activity Activity;
    private String CourtName;
    private String CourtAddress;
    private String TodayWrite;
    private String User_Pk;
    public OutCourt_CourtInfo_MyData(String CourtName, String CourtAddress,String TodayWrite, Activity activity, String User_Pk){
        this.CourtName = CourtName;
        this.CourtAddress = CourtAddress;
        this.TodayWrite = TodayWrite;
        this.Activity = activity;
        this.User_Pk = User_Pk;
    }
    public Activity getActivity(){return  Activity;}
    public String getCourtName(){return CourtName;}
    public String getCourtAddress(){return CourtAddress;}
    public String getTodayWrite(){return TodayWrite;}
    public String getUser_Pk(){return User_Pk;}
}