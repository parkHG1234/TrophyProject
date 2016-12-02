package trophy.projetc2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import static trophy.projetc2.R.id.MemberShip_Spinner_Si;

/**
 * Created by 박지훈 on 2016-12-01.
 */

public class Get_Spinner_Si extends BaseAdapter{

    Context context;
    ArrayAdapter<CharSequence> adspin2;
    public Get_Spinner_Si(Context c) {
        this.context = c;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }


    public ArrayAdapter<CharSequence> getAdapter(int position){
            if(position==-0) {
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_seoul, android.R.layout.simple_spinner_item);
                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }else if(position==1){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_incheon, android.R.layout.simple_spinner_item);
                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }else if(position==2){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_gwangju, android.R.layout.simple_spinner_item);
                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }else if(position==3){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_DaeGu, android.R.layout.simple_spinner_item);
                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }else if(position==4){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Ulsan, android.R.layout.simple_spinner_item);
                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }else if(position==5){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_DaeJeon, android.R.layout.simple_spinner_item);
                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }else if(position==6){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Busan, android.R.layout.simple_spinner_item);
                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }else if(position==7){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Gangwondo, android.R.layout.simple_spinner_item);
                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }else if(position==8){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Gyeonggido, android.R.layout.simple_spinner_item);
                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }else if(position==9){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Chungcheongbukdo, android.R.layout.simple_spinner_item);
                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }else if(position==10){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Chungcheongnamdo, android.R.layout.simple_spinner_item);
                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }else if(position==11){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Jeolabukdo, android.R.layout.simple_spinner_item);
                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }else if(position==12){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Jeolanamdo, android.R.layout.simple_spinner_item);
                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }else if(position==13){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Gyeongsangbukdo, android.R.layout.simple_spinner_item);
                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }else if(position==14){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Gyeongsangnamdo, android.R.layout.simple_spinner_item);
                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }else if(position==15){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Jejudo, android.R.layout.simple_spinner_item);
                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }
        return adspin2;
    }
}
