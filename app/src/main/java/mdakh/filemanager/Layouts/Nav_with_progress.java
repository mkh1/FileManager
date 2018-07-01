package mdakh.filemanager.Layouts;

import android.content.Context;
import android.graphics.Color;
import android.os.StatFs;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import mdakh.filemanager.MainActivity;
import mdakh.filemanager.NavigationDrawerFragment;
import mdakh.filemanager.R;
import mdakh.filemanager.adapters.MyRecyclerDownAdapter;

public class Nav_with_progress {

    Context context;

    public Nav_with_progress(Context context, final String path){
        this.context=context;
        if (!path.isEmpty()) {
            LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.height_storeg_line));
            p1.setMargins(0,10,0,0);
            LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.height_progress));
            LinearLayout.LayoutParams p3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams p4 = new LinearLayout.LayoutParams((int) context.getResources().getDimension(R.dimen.size_icon), LinearLayout.LayoutParams.MATCH_PARENT);


            LinearLayout main = (LinearLayout) NavigationDrawerFragment.view.findViewById(R.id.main);
            LinearLayout internal = new LinearLayout(context);
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
            NumberProgressBar size_internal = new NumberProgressBar(context);
            size_internal.setLayoutParams(p2);
            size_internal.setMax(100);
            float size = (get_Free_size_number(path) * 100) / get_Max_size_number(path);
            size = 100 - size;
            if (size <= 80) {
                size_internal.setReachedBarColor(context.getResources().getColor(R.color.drawer_size_rached_normal));
            } else {
                size_internal.setReachedBarColor(context.getResources().getColor(R.color.drawer_size_rached_full));
            }
            size_internal.setProgress((int) size);
            size_internal.setPadding(20, 0, 20, 0);
            size_internal.setReachedBarHeight(15);

            internal.addView(size_internal);
            LinearLayout line1 = new LinearLayout(context);
            line1.setLayoutParams(p3);
            line1.setOrientation(LinearLayout.HORIZONTAL);
            line1.setPadding(10, 0, 10, 0);
            line1.setGravity(Gravity.CENTER_VERTICAL);
            internal.addView(line1);
            ImageView icon = new ImageView(context);
            icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_phone));
            icon.setLayoutParams(p4);
            line1.addView(icon);
            TextView textView = new TextView(context);
            String s = get_Free_size(path) + " " +
                    context.getResources().getString(R.string.drawer_free_size1) + " " +
                    get_Max_size(path) + " " +
                    context.getResources().getString(R.string.drawer_free_size2);
            textView.setText(s);
            line1.addView(textView);

            main.addView(internal);
        }
    }

    public float get_Max_size_number(String path){
        StatFs stat = new StatFs(path);
        float bytesAvailable;
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bytesAvailable = stat.getBlockSizeLong() * stat.getBlockCountLong();
        }
        else {
            bytesAvailable = (long)stat.getBlockSize() * (long)stat.getBlockCount();
        }
        return bytesAvailable;
    }
    public float get_Free_size_number(String path){
        StatFs stat = new StatFs(path);
        float bytesfree;
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bytesfree = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        }
        else {
            bytesfree = (long)stat.getBlockSize() * (long)stat.getAvailableBlocks();
        }
        return bytesfree;
    }
    public String get_Max_size(String path){
        StatFs stat = new StatFs(path);
        float bytesAvailable;
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bytesAvailable = stat.getBlockSizeLong() * stat.getBlockCountLong();
        }
        else {
            bytesAvailable = (long)stat.getBlockSize() * (long)stat.getBlockCount();
        }
        if (bytesAvailable<1024)
            return (String.format("%.2f",bytesAvailable)+" "+context.getResources().getString(R.string.B));
        else if ((bytesAvailable=bytesAvailable/1024)<1024)
            return (String.format("%.2f",bytesAvailable)+" "+context.getResources().getString(R.string.KB));
        else if ((bytesAvailable=bytesAvailable/1024)<1024)
            return (String.format("%.2f",bytesAvailable)+" "+context.getResources().getString(R.string.MB));
        else if ((bytesAvailable=bytesAvailable/1024)<1024)
            return (String.format("%.2f",bytesAvailable)+" "+context.getResources().getString(R.string.GB));
        else if ((bytesAvailable=bytesAvailable/1024)<1024)
            return (String.format("%.2f",bytesAvailable)+" "+context.getResources().getString(R.string.TB));
        return "null";
    }
    public String get_Free_size(String path){
        StatFs stat = new StatFs(path);
        float bytesfree;
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bytesfree = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        }
        else {
            bytesfree = (long)stat.getBlockSize() * (long)stat.getAvailableBlocks();
        }
        if (bytesfree<1024)
            return (String.format("%.2f",bytesfree)+" "+context.getResources().getString(R.string.B));
        else if ((bytesfree=bytesfree/1024)<1024)
            return (String.format("%.2f",bytesfree)+" "+context.getResources().getString(R.string.KB));
        else if ((bytesfree=bytesfree/1024)<1024)
            return (String.format("%.2f",bytesfree)+" "+context.getResources().getString(R.string.MB));
        else if ((bytesfree=bytesfree/1024)<1024)
            return (String.format("%.2f",bytesfree)+" "+context.getResources().getString(R.string.GB));
        else if ((bytesfree=bytesfree/1024)<1024)
            return (String.format("%.2f",bytesfree)+" "+context.getResources().getString(R.string.TB));
        return "null";
    }

}
