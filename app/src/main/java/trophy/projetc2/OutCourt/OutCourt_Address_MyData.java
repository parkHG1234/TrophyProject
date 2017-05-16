package trophy.projetc2.OutCourt;

import android.app.Activity;
import android.location.Address;

/**
 * Created by 박효근 on 2017-05-02.
 */

public class OutCourt_Address_MyData {
    private String User_Pk;
    private Activity activity;
    private String Address_Do;
    private String Address_Si;
    public OutCourt_Address_MyData(Activity activity, String User_Pk, String Address_Do,String Address_Si){
        this.activity = activity;
        this.User_Pk = User_Pk;
        this.Address_Do = Address_Do;
        this.Address_Si = Address_Si;
    }
    public String getUser_Pk(){return User_Pk;}
    public String getAddress(){return Address_Do;}
    public String getAddress_Si(){return Address_Si;}
    public Activity getActivity(){return activity;}
}


