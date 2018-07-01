package mdakh.filemanager.Layouts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import mdakh.filemanager.R;
import mdakh.filemanager.adapters.MyRecyclerDownAdapter;

import static android.content.Context.MODE_PRIVATE;


public class Layout_Show_by {

    private LinearLayout grid;
    private LinearLayout linear;
    private LinearLayout main;
    private LinearLayout space1;
    private LinearLayout space2;
    private static RadioButton R_grid;
    private static RadioButton R_linear;
    private TextView T_grid;
    private TextView T_linear;
    private final AlertDialog alert;

    private static boolean b;

    LinearLayout.LayoutParams p_space=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1f);
    LinearLayout.LayoutParams wrap=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    private static SharedPreferences.Editor editor;
    private static SharedPreferences prefs ;

    public Layout_Show_by(final Context context, final MyRecyclerDownAdapter myRecyclerDownAdapter, final RecyclerView my_recycler){
        editor = context.getSharedPreferences("Sort_Setting", MODE_PRIVATE).edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        alert = builder.create();

        space1= new LinearLayout(context);

        space1.setLayoutParams(p_space);
        space2= new LinearLayout(context);
        space2.setLayoutParams(p_space);
        grid =new LinearLayout(context);
        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!R_grid.isChecked())
                    R_grid.setChecked(true);
                alert.cancel();
            }
        });
        R_grid = new RadioButton(context);

        T_grid = new TextView(context);
        grid.setOrientation(LinearLayout.HORIZONTAL);
        T_grid.setText(context.getString(R.string.grid));
        T_grid.setTextSize(22);
        grid.setLayoutParams(wrap);
        grid.addView(T_grid);
        grid.addView(space1);
        grid.addView(R_grid);
        T_linear = new TextView(context);
        linear = new LinearLayout(context);
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!R_linear.isChecked())
                    R_linear.setChecked(true);
                alert.cancel();
            }
        });
        R_linear = new RadioButton(context);
        T_linear.setText(context.getString(R.string.list));
        T_linear.setTextSize(22);
        linear.setLayoutParams(wrap);
        linear.setOrientation(LinearLayout.HORIZONTAL);
        linear.addView(T_linear);
        linear.addView(space2);
        linear.addView(R_linear);
        main = new LinearLayout(context);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setLayoutParams(wrap);
        main.addView(grid);
        main.addView(linear);

        if (MyRecyclerDownAdapter.layout==MyRecyclerDownAdapter.linear)
            R_linear.setChecked(true);
        else
            R_grid.setChecked(true);
        R_grid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    R_linear.setChecked(false);
                    myRecyclerDownAdapter.Linear_HORIZONTAL_View(context,my_recycler);
                    editor.putBoolean("linear",false);
                    editor.apply();
                }
            }
        });
        R_linear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    R_grid.setChecked(false);
                    myRecyclerDownAdapter.Linear_Vertical_View(context,my_recycler);
                    editor.putBoolean("linear",true);
                    editor.apply();
                }
            }
        });

        alert.setView(main);



    }

    public void show(){
        alert.show();
    }


    public static void read(Context context){
        prefs = context.getSharedPreferences("Sort_Setting", MODE_PRIVATE);
        b = prefs.getBoolean("linear", true);
        if (b){
            R_linear.setChecked(true);
        }
        else
            R_grid.setChecked(true);
    }


    public View return_view(){
        return main;
    }
}
