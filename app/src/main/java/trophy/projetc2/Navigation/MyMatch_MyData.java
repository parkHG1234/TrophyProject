package trophy.projetc2.Navigation;

import android.app.Activity;

/**
 * Created by 박효근 on 2017-04-17.
 */

public class MyMatch_MyData{
        private String Match_Pk;
        private String Emblem;
        private String TeamName;
        private String Title;
        private String MatchTime;
        private String MatchPlace;
        private String Time;
        private String Status;
        private android.app.Activity Activity;
        private String User_Pk;
        public MyMatch_MyData(String Match_Pk,String Emblem, String TeamName, String Title, String MatchTime, String MatchPlace, String Time, String Status, Activity Activity, String User_Pk){
            this.Match_Pk = Match_Pk;
            this.Emblem = Emblem;
            this.TeamName = TeamName;
            this.Title = Title;
            this.MatchTime = MatchTime;
            this.MatchPlace = MatchPlace;
            this.Time = Time;
            this.Status = Status;
            this.Activity = Activity;
            this.User_Pk = User_Pk;
        }
        public String getEmblem() {
            return Emblem;
        }
        public String getTeamName() {
            return TeamName;
        }
        public String getTitle(){return Title;}
        public String getMatchTime(){
            return MatchTime;
        }
        public String getMatchPlace(){
            return MatchPlace;
        }
        public String getTime(){
            return Time;
        }
        public String getMatch_Pk(){
            return Match_Pk;
        }
        public String  getStatus(){
            return Status;
        }
        public Activity getActivity(){
            return Activity;
        }
        public String getUser_Pk(){
            return User_Pk;
        }
}
