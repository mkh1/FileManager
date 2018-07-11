package mdakh.filemanager.Layouts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import mdakh.filemanager.MainActivity;
import mdakh.filemanager.R;
import mdakh.filemanager.adapters.MyRecyclerDownAdapter;
import mdakh.filemanager.operations.Copy;
import mdakh.filemanager.operations.Delete;

public class menu_single {

    private final AlertDialog alert;

    LinearLayout.LayoutParams p1=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    LinearLayout.LayoutParams p2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    public menu_single(final Context context, final int position){

        p2.setMargins(0,0,0,2);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        alert = builder.create();


        LinearLayout l_main=new LinearLayout(context);
        l_main.setLayoutParams(p1);
        l_main.setOrientation(LinearLayout.VERTICAL);


        LinearLayout l_name=new LinearLayout(context);
        l_name.setLayoutParams(p2);
        l_name.setGravity(Gravity.CENTER);
        l_name.setOrientation(LinearLayout.VERTICAL);
        l_name.setBackgroundColor(context.getResources().getColor(R.color.drawer_objects));
        TextView t_name=new TextView(context);
        t_name.setPadding(20,20,20,90);
        t_name.setTextSize(context.getResources().getDimension(R.dimen.text_Layout));
        t_name.setText(MyRecyclerDownAdapter.Files.get(position).getName());
        t_name.setGravity(Gravity.CENTER);
        l_name.addView(t_name);

        LinearLayout l_rename=new LinearLayout(context);
        l_rename.setLayoutParams(p2);
        l_rename.setGravity(Gravity.CENTER_VERTICAL);
        l_rename.setOrientation(LinearLayout.VERTICAL);
        l_rename.setBackgroundColor(context.getResources().getColor(R.color.drawer_objects));
        TextView t_rename=new TextView(context);
        t_rename.setPadding(20,20,20,20);
        t_rename.setTextSize(context.getResources().getDimension(R.dimen.text_Layout));
        t_rename.setText(context.getResources().getString(R.string.rename));
        l_rename.addView(t_rename);
        l_rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getResources().getString(R.string.rename));
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
                builder.setView(input);
                builder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                        java.io.File file_f=new java.io.File(MyRecyclerDownAdapter.Files.get(position).getParent()+"/"+MyRecyclerDownAdapter.Files.get(position).getName());
                        if (!MyRecyclerDownAdapter.Files.get(position).isDirectory()){
                            String s = MyRecyclerDownAdapter.Files.get(position).getName();
                            int p = s.length();
                            p--;
                            String check_dot = s.charAt(p) + "";
                            while (!check_dot.equals(".") && p > 0) {
                                p--;
                                check_dot = s.charAt(p) + "";
                            }
                            p++;
                            String extention = s.substring(p, MyRecyclerDownAdapter.Files.get(position).getName().length());
                            m_Text=m_Text+"."+extention;
                        }
                        java.io.File file_n=new java.io.File(MyRecyclerDownAdapter.Files.get(position).getParent()+"/"+m_Text);
                        boolean b=file_f.renameTo(file_n);
                        if (!b){
                            Toast.makeText(context.getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                        }
                        MainActivity.myRecyclerDownAdapter.setAddress(MainActivity.myRecyclerDownAdapter.getAddress());
                    }
                });
                builder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                alert.cancel();
            }
        });

        LinearLayout l_remove=new LinearLayout(context);
        l_remove.setLayoutParams(p2);
        l_remove.setGravity(Gravity.CENTER_VERTICAL);
        l_remove.setOrientation(LinearLayout.VERTICAL);
        l_remove.setBackgroundColor(context.getResources().getColor(R.color.drawer_objects));
        TextView t_remove=new TextView(context);
        t_remove.setPadding(20,20,20,20);
        t_remove.setTextSize(context.getResources().getDimension(R.dimen.text_Layout));
        t_remove.setText(context.getResources().getString(R.string.delete));
        l_remove.addView(t_remove);
        l_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRecyclerDownAdapter.Files.get(position).setCheck(true);
                Delete delete=new Delete();
                delete.run();
                alert.cancel();
            }
        });

        LinearLayout l_move=new LinearLayout(context);
        l_move.setLayoutParams(p2);
        l_move.setGravity(Gravity.CENTER_VERTICAL);
        l_move.setOrientation(LinearLayout.VERTICAL);
        l_move.setBackgroundColor(context.getResources().getColor(R.color.drawer_objects));
        TextView t_move=new TextView(context);
        t_move.setPadding(20,20,20,20);
        t_move.setTextSize(context.getResources().getDimension(R.dimen.text_Layout));
        t_move.setText(context.getResources().getString(R.string.move));
        l_move.addView(t_move);
        l_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRecyclerDownAdapter.Files.get(position).setCheck(true);
                accept_checks.copy=new Copy(context);
                MainActivity.paste.setEnabled(true);
                mdakh.filemanager.Layouts.accept_checks.Status.move();
                alert.cancel();
            }
        });

        LinearLayout l_copy=new LinearLayout(context);
        l_copy.setLayoutParams(p2);
        l_copy.setGravity(Gravity.CENTER_VERTICAL);
        l_copy.setOrientation(LinearLayout.VERTICAL);
        l_copy.setBackgroundColor(context.getResources().getColor(R.color.drawer_objects));
        TextView t_copy=new TextView(context);
        t_copy.setPadding(20,20,20,20);
        t_copy.setTextSize(context.getResources().getDimension(R.dimen.text_Layout));
        t_copy.setText(context.getResources().getString(R.string.copy));
        l_copy.addView(t_copy);
        l_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRecyclerDownAdapter.Files.get(position).setCheck(true);
                accept_checks.copy=new Copy(context);
                MainActivity.paste.setEnabled(true);
                mdakh.filemanager.Layouts.accept_checks.Status.copy();
                alert.cancel();
            }
        });

        LinearLayout l_details=new LinearLayout(context);
        l_details.setLayoutParams(p2);
        l_details.setGravity(Gravity.CENTER_VERTICAL);
        l_details.setOrientation(LinearLayout.VERTICAL);
        l_details.setBackgroundColor(context.getResources().getColor(R.color.drawer_objects));
        TextView t_details=new TextView(context);
        t_details.setPadding(20,20,20,20);
        t_details.setTextSize(context.getResources().getDimension(R.dimen.text_Layout));
        t_details.setText(context.getResources().getString(R.string.details));
        l_details.addView(t_details);
        l_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Details details=new Details(context,position);
                alert.cancel();
            }
        });




        l_main.addView(l_name);
        l_main.addView(l_rename);
        l_main.addView(l_remove);
        l_main.addView(l_move);
        l_main.addView(l_copy);
        l_main.addView(l_details);




        alert.setView(l_main);
        alert.show();

    }

}
