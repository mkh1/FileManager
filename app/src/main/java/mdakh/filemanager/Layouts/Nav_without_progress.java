package mdakh.filemanager.Layouts;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Locale;

import mdakh.filemanager.MainActivity;
import mdakh.filemanager.NavigationDrawerFragment;
import mdakh.filemanager.R;

public class Nav_without_progress {

    Context context;

    public static LinearLayout l1,l2,l3;

    private static int z=0;
    public Nav_without_progress(Context context, final String path, Drawable icon_path, boolean start_layout,int l_number){
        this.context=context;
        if (!path.isEmpty()) {
            l1=(LinearLayout)NavigationDrawerFragment.view.findViewById(R.id.line1);
            l2=(LinearLayout)NavigationDrawerFragment.view.findViewById(R.id.line2);
            l3=(LinearLayout)NavigationDrawerFragment.view.findViewById(R.id.line3);
            LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            l1.setLayoutParams(p2);
            l1.setOrientation(LinearLayout.HORIZONTAL);
            l2.setLayoutParams(p2);
            l2.setOrientation(LinearLayout.HORIZONTAL);
            l3.setLayoutParams(p2);
            l3.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams p3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams p4 = new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.size_icon), LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout internal = new LinearLayout(context);
            int width=l1.getMeasuredWidth();
            LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(0, (int) context.getResources().getDimension(R.dimen.height_storeg_line),1f);
            if (start_layout) {
                String language = getKeyboardLanguage();
                if (isRTL(new Locale(language)))
                    p1.setMargins(10, 10, 0, 0);
                else
                    p1.setMargins(0, 10, 10, 0);
            }
            else
                p1.setMargins(0, 10, 0, 0);
            internal.setLayoutParams(p1);
            internal.setBackgroundColor(context.getResources().getColor(R.color.drawer_objects));
            internal.setOrientation(LinearLayout.VERTICAL);
            internal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.myRecyclerDownAdapter.setAddress(path);
                    MainActivity.drawerFragment.setDrawerClose();
                }
            });


            LinearLayout line1 = new LinearLayout(context);
            line1.setLayoutParams(p3);
            line1.setOrientation(LinearLayout.HORIZONTAL);
            line1.setPadding(10, 0, 10, 0);
            line1.setGravity(Gravity.CENTER);
            internal.addView(line1);
            internal.setGravity(Gravity.CENTER);
            ImageView icon = new ImageView(context);
            icon.setImageDrawable(icon_path);
            icon.setLayoutParams(p4);
            line1.addView(icon);

            if (l_number==1){
                l1.addView(internal);
            }
            else if (l_number==2){
                l2.addView(internal);
            }
            else {
                l3.addView(internal);
            }


        }
    }

    private String getKeyboardLanguage() {
        InputMethodManager inputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        InputMethodSubtype inputMethodSubtype = inputMethodManager.getCurrentInputMethodSubtype();
        return inputMethodSubtype.getLocale();
    }


    private boolean isRTL(Locale locale) {
        final int directionality = Character.getDirectionality(locale.getDisplayName().charAt(0));
        return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
                directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC;
    }

}
