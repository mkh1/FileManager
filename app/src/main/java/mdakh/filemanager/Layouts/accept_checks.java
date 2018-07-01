package mdakh.filemanager.Layouts;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import mdakh.filemanager.MainActivity;
import mdakh.filemanager.R;
import mdakh.filemanager.adapters.MyRecyclerDownAdapter;
import mdakh.filemanager.operations.Copy;
import mdakh.filemanager.operations.Delete;

/**
 * Created by mdakh on 5/18/2018.
 */

public class accept_checks {

    private static Context context;
    private static Button ok;
    private static Button cancel;
    private static boolean show;
    public static Copy copy;


    public accept_checks(final Context context, LinearLayout linearLayout){
        this.context=context;
        LinearLayout row=new LinearLayout(context);
        LinearLayout.LayoutParams layouthorizon=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams layoutweight=new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1);
        LinearLayout.LayoutParams layoutdefault=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(layouthorizon);
        row.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout weight=new LinearLayout(context);
        weight.setLayoutParams(layoutweight);
        this.ok=new Button(context);
        ok.setLayoutParams(layoutdefault);
        ok.setText(context.getResources().getString(R.string.ok));
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
                if (Status.isDelete()){
                    Delete delete=new Delete();
                    delete.start();
                }
                else if (Status.isCopy()){
                    copy=new Copy(context);
                }
                else if (Status.isMove()){
                    copy=new Copy(context);

                }
                MainActivity.paste.setEnabled(true);
            }
        });
        this.cancel=new Button(context);
        cancel.setLayoutParams(layoutdefault);
        cancel.setText(context.getResources().getString(R.string.cancel));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        row.addView(ok);
        row.addView(weight);
        row.addView(cancel);
        linearLayout.addView(row);
    }

    public void setText(String text){
        this.ok.setText(text);
    }


    public static boolean isShow(){
        return show;
    }



    public static void hide(){
        show=false;
        ok.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
        mdakh.filemanager.Status.checkbox=false;
        MainActivity.myRecyclerDownAdapter.notifyDataSetChanged();
    }

    public static void show(){
        for (int i=0;i< MyRecyclerDownAdapter.Files.size();i++){
            MyRecyclerDownAdapter.Files.get(i).setCheck(false);
        }
        show=true;
        ok.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);
    }


    public static class Status{



        public static void delete(){
            ok.setText(context.getResources().getString(R.string.delete));
        }

        public static void move(){
            ok.setText(context.getResources().getString(R.string.move));
        }

        public static void copy(){
            ok.setText(context.getResources().getString(R.string.copy));
        }

        public static void compress(){
            ok.setText(context.getResources().getString(R.string.compress));
        }

        public static boolean isDelete(){
            if (ok.getText().equals(context.getResources().getString(R.string.delete)))
                return true;
            else return false;
        }
        public static boolean isMove(){
            if (ok.getText().equals(context.getResources().getString(R.string.move)))
                return true;
            else return false;
        }
        public static boolean isCopy(){
            if (ok.getText().equals(context.getResources().getString(R.string.copy)))
                return true;
            else return false;
        }
        public static boolean isCompress(){
            if (ok.getText().equals(context.getResources().getString(R.string.compress)))
                return true;
            else return false;
        }



    }




}
