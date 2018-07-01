package mdakh.filemanager.samples;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import mdakh.filemanager.R;
import mdakh.filemanager.adapters.MyRecyclerDownAdapter;

/**
 * Created by mdakh on 3/2/2018.
 */

@SuppressLint("AppCompatCustomView")
public class Button_sample1 extends Button implements DialogInterface.OnClickListener {

    private LinearLayout tag_address;
    private MyRecyclerDownAdapter myRecyclerDownAdapter;
    private String Address;
    private Context context;
    private int i;


    //setters and getters



    public void setContext(Context context) {
        this.context = context;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public MyRecyclerDownAdapter getMy_recycler_down() {
        return myRecyclerDownAdapter;
    }

    public void setmyRecyclerDownAdapter(MyRecyclerDownAdapter myRecyclerDownAdapter) {
        this.myRecyclerDownAdapter = myRecyclerDownAdapter;
    }




    //constructors
    public Button_sample1(Context context) {
        super(context);
        this.context=context;
        //setmyRecyclerDownAdapter(myRecyclerDownAdapter);
    }

    public Button_sample1(Context context , AttributeSet attrs ) {
        super(context , attrs);

        this.context=context;
        i=0;
        //tag_address=(LinearLayout)context.findViewById(R.id.tag_address);
        //this.setBackgroundColor(getResources().getColor(R.color.address_background));
        //this.setHighlightColor(getResources().getColor(R.color.address_text));
        //this.setTextColor(R.color.address_text);
        //setmyRecyclerDownAdapter(myRecyclerDownAdapter);
    }






    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        i++;
        //myRecyclerDownAdapter.setAddress(this.Address);
        super.setOnClickListener(l);

    }




    @Override
    public void onClick(DialogInterface dialog, int which) {
        i++;
        this.setText("jj  "+i);
    }
}
