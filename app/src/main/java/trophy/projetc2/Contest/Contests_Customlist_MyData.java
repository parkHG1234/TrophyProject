package trophy.projetc2.Contest;

import android.app.Activity;

/**
 * Created by KimIkJoong on 2016-11-08.
 */

public class Contests_Customlist_MyData {
    String Contest_Pk;
    String Contest_Title;
    String Contest_Image;
    String Contest_currentNum;
    String Contest_maxNum;
    String Contest_Payment;
    String Contest_Host;
    String Contest_Management;
    String Support;
    String ContestDate;
    String RecruitStartDate;
    String RecruitFinishDate;
    String DetailInfo;
    Activity Activity;
    public Contests_Customlist_MyData(String contest_Pk, String contest_Title, String contest_Image, String contest_currentNum, String contest_maxNum, String contest_Payment, String contest_Host, String contest_Management, String support, String contestDate, String recruitStartDate, String recruitFinishDate, String detailInfo,Activity activity) {
        Contest_Pk = contest_Pk;
        Contest_Title = contest_Title;
        Contest_Image = contest_Image;
        Contest_currentNum = contest_currentNum;
        Contest_maxNum = contest_maxNum;
        Contest_Payment = contest_Payment;
        Contest_Host = contest_Host;
        Contest_Management = contest_Management;
        Support = support;
        ContestDate = contestDate;
        RecruitStartDate = recruitStartDate;
        RecruitFinishDate = recruitFinishDate;
        DetailInfo = detailInfo;
        Activity = activity;
    }
    public Activity getActivity(){
        return Activity;
    }
    public String getContest_Pk() {
        return Contest_Pk;
    }

    public String getContest_Title() {
        return Contest_Title;
    }

    public String getContest_Image() {
        return Contest_Image;
    }

    public String getContest_currentNum() {
        return Contest_currentNum;
    }

    public String getContest_maxNum() {
        return Contest_maxNum;
    }

    public String getContest_Payment() {
        return Contest_Payment;
    }

    public String getContest_Host() {
        return Contest_Host;
    }

    public String getContest_Management() {
        return Contest_Management;
    }

    public String getSupport() {
        return Support;
    }

    public String getContestDate() {
        return ContestDate;
    }

    public String getRecruitStartDate() {
        return RecruitStartDate;
    }

    public String getRecruitFinishDate() {
        return RecruitFinishDate;
    }

    public String getDetailInfo() {
        return DetailInfo;
    }

    //setter


    public void setContest_Pk(String contest_Pk) {
        Contest_Pk = contest_Pk;
    }

    public void setContest_Title(String contest_Title) {
        Contest_Title = contest_Title;
    }

    public void setContest_Image(String contest_Image) {
        Contest_Image = contest_Image;
    }

    public void setContest_currentNum(String contest_currentNum) {
        Contest_currentNum = contest_currentNum;
    }

    public void setContest_maxNum(String contest_maxNum) {
        Contest_maxNum = contest_maxNum;
    }

    public void setContest_Payment(String contest_Payment) {
        Contest_Payment = contest_Payment;
    }

    public void setContest_Host(String contest_Host) {
        Contest_Host = contest_Host;
    }

    public void setContest_Management(String contest_Management) {
        Contest_Management = contest_Management;
    }

    public void setSupport(String support) {
        Support = support;
    }

    public void setContestDate(String contestDate) {
        ContestDate = contestDate;
    }

    public void setRecruitStartDate(String recruitStartDate) {
        RecruitStartDate = recruitStartDate;
    }

    public void setRecruitFinishDate(String recruitFinishDate) {
        RecruitFinishDate = recruitFinishDate;
    }

    public void setDetailInfo(String detailInfo) {
        DetailInfo = detailInfo;
    }
}
