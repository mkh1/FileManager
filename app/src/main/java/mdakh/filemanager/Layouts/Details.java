package mdakh.filemanager.Layouts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Text;

import mdakh.filemanager.R;
import mdakh.filemanager.Status;
import mdakh.filemanager.adapters.MyRecyclerDownAdapter;

public class Details {


    LinearLayout.LayoutParams p_space=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1f);
    LinearLayout.LayoutParams p1=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


    public Details(final Context context , final int position){

        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.alertDialog);



        final AlertDialog alert = builder.create();

        LinearLayout linearLayout=new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(p1);
        linearLayout.setBackgroundColor(context.getResources().getColor(R.color.drawer_objects));
        if (Status.RTL){
            linearLayout.setGravity(Gravity.RIGHT);
        }
        else {
            linearLayout.setGravity(Gravity.LEFT);
        }


        builder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        TextView title=new TextView(context);
        title.setText(context.getResources().getString(R.string.details));
        title.setTextSize((float) ((context.getResources().getDimension(R.dimen.text_Layout))*1.2));
        linearLayout.addView(title);
        int i= (int) context.getResources().getDimension(R.dimen.padding_text);
        linearLayout.setPadding(i,i,i,i*2);

        TextView name=new TextView(context);
        String s=context.getResources().getString(R.string.name);
        if (Status.RTL){
            s= MyRecyclerDownAdapter.Files.get(position).getName()+" : "+s;
            name.setGravity(Gravity.RIGHT);
        }
        else {
            s= s+" : "+MyRecyclerDownAdapter.Files.get(position).getName();
            name.setGravity(Gravity.LEFT);
        }
        name.setText(s);
        //name.setPadding(i,i,i,i);
        linearLayout.addView(name);



        TextView size=new TextView(context);
        s=context.getResources().getString(R.string.size);
        float d=0;
        String ds="";
        if (MyRecyclerDownAdapter.Files.get(position).isDirectory()){
            try {
                d= FileUtils.sizeOfDirectory(MyRecyclerDownAdapter.Files.get(position));
                if (d < 1024)
                    ds=(String.format("%.2f", d) + " " + context.getResources().getString(R.string.B));
                else if ((d = d / 1024) < 1024)
                    ds=(String.format("%.2f", d) + " " + context.getResources().getString(R.string.KB));
                else if ((d = d / 1024) < 1024)
                    ds=(String.format("%.2f", d) + " " + context.getResources().getString(R.string.MB));
                else if ((d = d / 1024) < 1024)
                    ds=(String.format("%.2f", d) + " " + context.getResources().getString(R.string.GB));
                else if ((d = d / 1024) < 1024)
                    ds=(String.format("%.2f", d) + " " + context.getResources().getString(R.string.TB));
            } catch (Exception e) {
                ds=(Long.toString(0) + " " + context.getResources().getString(R.string.B));
            }
        }
        else {
            try {
                d = MyRecyclerDownAdapter.Files.get(position).length();
                if (d < 1024)
                    ds=(String.format("%.2f", d) + " " + context.getResources().getString(R.string.B));
                else if ((d = d / 1024) < 1024)
                    ds=(String.format("%.2f", d) + " " + context.getResources().getString(R.string.KB));
                else if ((d = d / 1024) < 1024)
                    ds=(String.format("%.2f", d) + " " + context.getResources().getString(R.string.MB));
                else if ((d = d / 1024) < 1024)
                    ds=(String.format("%.2f", d) + " " + context.getResources().getString(R.string.GB));
                else if ((d = d / 1024) < 1024)
                    ds=(String.format("%.2f", d) + " " + context.getResources().getString(R.string.TB));
            } catch (Exception e) {
                ds=(Long.toString(0) + " " + context.getResources().getString(R.string.B));
            }
        }
        if (Status.RTL){
            s= s+" : "+ds;
            size.setGravity(Gravity.RIGHT);
        }
        else {
            s= ds+" : "+s;
            size.setGravity(Gravity.LEFT);
        }
        size.setText(s);
        linearLayout.addView(size);




        TextView type=new TextView(context);
        boolean more_ch=false;
        s="";
        try {
            if (!MyRecyclerDownAdapter.Files.get(position).isDirectory()) {
                String s1 = MyRecyclerDownAdapter.Files.get(position).getName();
                int p = s1.length();
                p--;
                String check_dot = s1.charAt(p) + "";
                while (!check_dot.equals(".") && p > 0) {
                    p--;
                    check_dot = s1.charAt(p) + "";
                }
                p++;
                String extention = s1.substring(p, MyRecyclerDownAdapter.Files.get(position).getName().length());


                if (!MyRecyclerDownAdapter.dataBaseHelperForType.getList(extention).FileType.isEmpty()) {
                    s=(MyRecyclerDownAdapter.dataBaseHelperForType.getList(extention).FileType);
                    more_ch=true;
                } else
                    s="not found";
            }
            else {
                s="Directory";
            }
        }
        catch (Exception e){
        }
        if (Status.RTL){
            s= s+" : "+context.getResources().getString(R.string.type);
            type.setGravity(Gravity.RIGHT);
        }
        else {
            s= context.getResources().getString(R.string.type)+" : "+s;
            type.setGravity(Gravity.LEFT);
        }
        type.setText(s);
        linearLayout.addView(type);







        if (more_ch){
            TextView more=new TextView(context);
            s="";
            try {
                if (!MyRecyclerDownAdapter.Files.get(position).isDirectory()) {
                    String s1 = MyRecyclerDownAdapter.Files.get(position).getName();
                    int p = s1.length();
                    p--;
                    String check_dot = s1.charAt(p) + "";
                    while (!check_dot.equals(".") && p > 0) {
                        p--;
                        check_dot = s1.charAt(p) + "";
                    }
                    p++;
                    String extention = s1.substring(p, MyRecyclerDownAdapter.Files.get(position).getName().length());


                    if (!MyRecyclerDownAdapter.dataBaseHelperForType.getList(extention).Link.isEmpty()) {
                        s=(MyRecyclerDownAdapter.dataBaseHelperForType.getList(extention).Link);
                    } else
                        s="not found";
                }
                else {
                    s="Directory";
                }
            }
            catch (Exception e){
            }
            if (Status.RTL){
                s= s+" : "+context.getResources().getString(R.string.more);
                more.setGravity(Gravity.RIGHT);
            }
            else {
                s= context.getResources().getString(R.string.more)+" : "+s;
                more.setGravity(Gravity.LEFT);
            }
            more.setText(s);
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s1 = MyRecyclerDownAdapter.Files.get(position).getName();
                    int p = s1.length();
                    p--;
                    String check_dot = s1.charAt(p) + "";
                    while (!check_dot.equals(".") && p > 0) {
                        p--;
                        check_dot = s1.charAt(p) + "";
                    }
                    p++;
                    String extention = s1.substring(p, MyRecyclerDownAdapter.Files.get(position).getName().length());
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MyRecyclerDownAdapter.dataBaseHelperForType.getList(extention).Link));
                    context.startActivity(browserIntent);
                }
            });
            linearLayout.addView(more);
        }




        builder.setView(linearLayout);

        Dialog dialog = builder.create();
        //dialog.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);
        //dialog.getWindow().getDecorView().getBackground().setColorFilter((new LightingColorFilter(0xff000000, context.getResources().getColor(R.color.drawer_objects))));
        dialog.show();

    }




}
