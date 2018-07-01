package mdakh.filemanager.Layouts;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import mdakh.filemanager.MainActivity;

/**
 * Created by mdakh on 3/6/2018.
 */

public class Tag_Address {

    private LinearLayout tag_address;
    private Context context;
    private ArrayList<TextView> textViews;
    private LinearLayout.LayoutParams layout;
    public static ArrayList<String> addresslink;


    ArrayList<String> addresses;

    public Tag_Address(Context context , LinearLayout tag_address){
        textViews=new ArrayList<>(10);
        this.tag_address=tag_address;
        this.context=context;
    }




    public void refresh_tag_address(String address){
        addresses=new ArrayList<>();
        addresslink=new ArrayList<>();
        int m1=address.length()-1;

        int n1=0;
        int j=0;

        for(int i=0;i<address.length();i++){
            if ((address.charAt(i)=='/')){

                if (i!=0) {
                    m1=i;
                    addresses.add(address.substring(n1,m1));
                    String w="";
                    w=w+address.substring(n1,m1);
                    if (j!=0)
                        w=addresslink.get(j)+w;
                    n1=m1;
                    addresslink.add(w);
                    j++;
                }
                else {
                    addresses.add("/");
                    addresslink.add("/");
                    //j++;
                }



            }
            if (i==address.length()-1&&i!=0){

                String w="";
                addresses.add(address.substring(n1,address.length()));
                w=w+address.substring(n1,address.length());
                if (j!=0)
                    w=addresslink.get(j-1)+w;
                addresslink.add(w);
                j++;
            }
        }

        int p=addresses.size();
        textViews.clear();
        tag_address.removeAllViews();
        for (int i=0;i<p;i++){
            textViews.add(new TextView(context));
            textViews.get(i).setId(i);
            textViews.get(i).setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
            textViews.get(i).setText(addresses.get(i));
            layout=new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
            textViews.get(i).setLayoutParams(layout);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if(context.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                for (int i=p-1;i>=0;i--) {
                    tag_address.addView(textViews.get(i));
                }
            }
            else {
                for (int i = 0; i < p; i++) {
                    tag_address.addView(textViews.get(i));
                }
            }
        }
        else {
            for (int i = 0; i < p; i++) {
                tag_address.addView(textViews.get(i));
            }
        }

        for (int i1=0;i1<p;i1++){
            final int finalI = i1;
            textViews.get(i1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.myRecyclerDownAdapter.setAddress(Tag_Address.addresslink.get(finalI));
                }
            });
        }
    }

    private String getaddress(int i){
        String s="";
        for (int j=0;j<i;j++){
            s+=addresses.get(j);
        }
        return s;
    }
}
