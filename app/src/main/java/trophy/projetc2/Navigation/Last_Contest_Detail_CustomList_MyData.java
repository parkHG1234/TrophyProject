package trophy.projetc2.Navigation;


/**
 * Created by 박효근 on 2016-12-02.
 */

public class Last_Contest_Detail_CustomList_MyData {
    private String Pk;
    private String Image;
    private String Award;

    public Last_Contest_Detail_CustomList_MyData(String Pk, String Image, String Award){
        this.Pk = Pk;
        this.Award = Award;
        this.Image = Image;
    }
    public String getPk() {
        return Pk;
    }
    public String getImage(){ return Image; }
    public String getAward(){
        return Award;
    }

}
