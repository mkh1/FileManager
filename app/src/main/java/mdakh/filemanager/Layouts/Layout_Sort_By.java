package mdakh.filemanager.Layouts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import mdakh.filemanager.R;
import mdakh.filemanager.adapters.MyRecyclerDownAdapter;
import mdakh.filemanager.operations.Sort;

public class Layout_Sort_By {

    private RadioGroup first;
    private RadioButton first_folder;
    private RadioButton first_file;
    private RadioButton first_no;
    private RadioGroup sort;
    private RadioButton sort_name;
    private RadioButton sort_date;
    private RadioButton sort_no;
    private CheckBox reverce;

    private LinearLayout s1;
    private LinearLayout s2;
    private LinearLayout s3;
    private LinearLayout s4;
    private LinearLayout sort_reverce;
    private LinearLayout main;


    LinearLayout.LayoutParams p_space=new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1f);
    LinearLayout.LayoutParams p1=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    public Layout_Sort_By(Context context,final MyRecyclerDownAdapter myRecyclerDownAdapter){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final AlertDialog alert = builder.create();

        int s_first = Sort.getFirst();
        int s_sort = Sort.getSort();

        first=new RadioGroup(context);
        first_file=new RadioButton(context);
        first_folder=new RadioButton(context);
        first_no=new RadioButton(context);
        sort=new RadioGroup(context);
        sort_date=new RadioButton(context);
        sort_name=new RadioButton(context);
        sort_no=new RadioButton(context);

        reverce=new CheckBox(context);
        main=new LinearLayout(context);

        s1=new LinearLayout(context);
        s2=new LinearLayout(context);
        s3=new LinearLayout(context);
        s4=new LinearLayout(context);
        sort_reverce=new LinearLayout(context);

        first.setLayoutParams(p1);
        sort.setLayoutParams(p1);
        sort_reverce.setLayoutParams(p1);
        main.setLayoutParams(p1);
        s1.setLayoutParams(p_space);
        s2.setLayoutParams(p_space);
        s3.setLayoutParams(p_space);
        s4.setLayoutParams(p_space);

        first.setOrientation(LinearLayout.HORIZONTAL);
        sort.setOrientation(LinearLayout.VERTICAL);
        sort_reverce.setOrientation(LinearLayout.HORIZONTAL);
        main.setOrientation(LinearLayout.VERTICAL);

        first.addView(first_file);
        first.addView(s1);
        first.addView(first_folder);
        first.addView(s2);
        first.addView(first_no);
        sort_reverce.addView(reverce);
        sort_reverce.addView(sort);
        sort.addView(sort_name);
        sort.addView(sort_date);
        sort.addView(sort_no);
        main.addView(first);
        main.addView(sort_reverce);


        first_folder.setText(context.getString(R.string.first_folders));
        first_file.setText(context.getString(R.string.first_files));
        first_no.setText(context.getString(R.string.first_no));
        sort_name.setText(context.getString(R.string.sort_name));
        sort_date.setText(context.getString(R.string.sort_date));
        sort_no.setText(context.getString(R.string.sort_no));
        reverce.setText(context.getString(R.string.reverce));

        switch (s_first){
            case 0 :
                first_no.setChecked(true);
                break;
            case 1:
                first_folder.setChecked(true);
                break;
            case 2:
                first_file.setChecked(true);
                break;
        }
        switch (s_sort){
            case 0 :
                sort_name.setChecked(true);
                reverce.setChecked(false);
                break;
            case 1:
                sort_name.setChecked(true);
                reverce.setChecked(true);
                break;
            case 2:
                sort_date.setChecked(true);
                reverce.setChecked(false);
                break;
            case 3:
                sort_date.setChecked(true);
                reverce.setChecked(true);

                break;
            case 4:
                sort_no.setChecked(true);
                reverce.setChecked(false);
                break;
        }

        builder.setView(main);

        builder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (first_no.isChecked()){
                    Sort.no_Sort_first();
                }
                else if (first_file.isChecked()){
                    Sort.first_file();
                }
                else if (first_folder.isChecked()){
                    Sort.first_folder();
                }

                if (reverce.isChecked()){
                    if (sort_name.isChecked()){
                        Sort.name_r();
                    }
                    else if (sort_date.isChecked()){
                        Sort.date_r();
                    }
                    else if ((sort_no.isChecked())) {
                        Sort.Sort_no();
                    }
                }
                else {
                    if (sort_name.isChecked()){
                        Sort.name_a();
                    }
                    else if (sort_date.isChecked()){
                        Sort.date_a();
                    }
                    else if ((sort_no.isChecked())) {
                        Sort.Sort_no();
                    }
                }
                Sort.get_sort_setting();
                myRecyclerDownAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        builder.show();




    }

}
