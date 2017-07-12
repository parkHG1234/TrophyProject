package trophy.projetc2.Match;

import android.app.Activity;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by 박효근 on 2017-04-17.
 */

public class MyMatch_MyData{
        private String Match_Pk;
        private String Emblem;
        private String TeamName;
        private String Title;
        private String StartTime;
        private String MatchPlace;
        private String Time;
        private String Status;
        private android.app.Activity Activity;
        private String User_Pk;
        private String JoinerCount;
        private String FinishTime;
        private String MatchDate;
        private String DateStatus;
        public MyMatch_MyData(String Match_Pk,String Emblem, String TeamName, String Title, String StartTime, String MatchPlace, String Time, String Status, Activity Activity, String User_Pk,String JoinerCount,String FinishTime,String MatchDate){
            this.Match_Pk = Match_Pk;
            this.Emblem = Emblem;
            this.TeamName = TeamName;
            this.Title = Title;
            this.StartTime = StartTime;
            this.MatchPlace = MatchPlace;
            this.Time = Time;
            this.Status = Status;
            this.Activity = Activity;
            this.User_Pk = User_Pk;
            this.JoinerCount = JoinerCount;
            this.FinishTime = FinishTime;
            this.MatchDate = MatchDate;
            this.DateStatus = DateStatus;
        }
        public String getEmblem() {
            return Emblem;
        }
        public String getTeamName() {
            return TeamName;
        }
        public String getTitle(){return Title;}
        public String getStartTime(){
            return StartTime;
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
        public String getJoinerCount(){
            return JoinerCount;
        }
        public String getFinishTime(){return FinishTime;}
        public String getMatchDate(){return MatchDate;}

}
