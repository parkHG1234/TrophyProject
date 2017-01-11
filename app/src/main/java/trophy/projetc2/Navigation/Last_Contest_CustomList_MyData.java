package trophy.projetc2.Navigation;


/**
 * Created by 박효근 on 2016-12-02.
 */

public class Last_Contest_CustomList_MyData {
    private String Pk;
    private String Title;
    private String Image;

    public Last_Contest_CustomList_MyData(String Pk, String Title, String Image){
        this.Pk = Pk;
        this.Title = Title;
        this.Image = Image;
    }
    public String getPk() {
        return Pk;
    }
    public String getTitle(){
        return Title;
    }
    public String getImage(){ return Image; }

}
