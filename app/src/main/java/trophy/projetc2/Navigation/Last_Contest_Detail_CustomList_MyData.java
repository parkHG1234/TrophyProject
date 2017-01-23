package trophy.projetc2.Navigation;


/**
 * Created by 박효근 on 2016-12-02.
 */

public class Last_Contest_Detail_CustomList_MyData {
    private String Pk;
    private String Image;
    private String Image1;
    private String Image2;
    private String Image3;
    private String Award;
    private String cnt;


    public Last_Contest_Detail_CustomList_MyData(String Pk,String Image, String Image1, String Image2, String Image3, String Award, String cnt){
        this.Pk = Pk;
        this.Award = Award;
        this.Image = Image;
        this.Image1 = Image1;
        this.Image2 = Image2;
        this.Image3 = Image3;
        this.cnt = cnt;
    }
    public String getPk() {
        return Pk;
    }
    public String getImage(){ return Image; }
    public String getImage1(){ return Image1; }
    public String getImage2(){ return Image2; }
    public String getImage3(){ return Image3; }
    public String getAward(){
        return Award;
    }
    public String getCnt(){
        return cnt;
    }

}
