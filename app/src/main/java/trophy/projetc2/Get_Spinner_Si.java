package trophy.projetc2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.Toast;


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
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_seoul, R.layout.spinner_style);
                adspin2.setDropDownViewResource(R.layout.spinner_style);
            }else if(position==1){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_incheon, R.layout.spinner_style);
                adspin2.setDropDownViewResource(R.layout.spinner_style);
            }else if(position==2){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_gwangju, R.layout.spinner_style);
                adspin2.setDropDownViewResource(R.layout.spinner_style);
            }else if(position==3){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_DaeGu,R.layout.spinner_style);
                adspin2.setDropDownViewResource(R.layout.spinner_style);
            }else if(position==4){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Ulsan, R.layout.spinner_style);
                adspin2.setDropDownViewResource(R.layout.spinner_style);
            }else if(position==5){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_DaeJeon, R.layout.spinner_style);
                adspin2.setDropDownViewResource(R.layout.spinner_style);
            }else if(position==6){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Busan, R.layout.spinner_style);
                adspin2.setDropDownViewResource(R.layout.spinner_style);
            }else if(position==7){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Gangwondo, R.layout.spinner_style);
                adspin2.setDropDownViewResource(R.layout.spinner_style);
            }else if(position==8){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Gyeonggido, R.layout.spinner_style);
                adspin2.setDropDownViewResource(R.layout.spinner_style);
            }else if(position==9){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Chungcheongbukdo, R.layout.spinner_style);
                adspin2.setDropDownViewResource(R.layout.spinner_style);
            }else if(position==10){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Chungcheongnamdo, R.layout.spinner_style);
                adspin2.setDropDownViewResource(R.layout.spinner_style);
            }else if(position==11){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Jeolabukdo, R.layout.spinner_style);
                adspin2.setDropDownViewResource(R.layout.spinner_style);
            }else if(position==12){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Jeolanamdo, R.layout.spinner_style);
                adspin2.setDropDownViewResource(R.layout.spinner_style);
            }else if(position==13){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Gyeongsangbukdo, R.layout.spinner_style);
                adspin2.setDropDownViewResource(R.layout.spinner_style);
            }else if(position==14){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Gyeongsangnamdo, R.layout.spinner_style);
                adspin2.setDropDownViewResource(R.layout.spinner_style);
            }else if(position==15){
                adspin2 = ArrayAdapter.createFromResource(context, R.array.spinner_do_Jejudo,R.layout.spinner_style);
                adspin2.setDropDownViewResource(R.layout.spinner_style);
            }
        return adspin2;
    }
}
