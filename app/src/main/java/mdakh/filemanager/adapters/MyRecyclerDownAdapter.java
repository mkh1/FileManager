package mdakh.filemanager.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mdakh.filemanager.Database.DataBaseHelperForType;
import mdakh.filemanager.Layouts.menu_single;
import mdakh.filemanager.MainActivity;
import mdakh.filemanager.R;
import mdakh.filemanager.Status;
import mdakh.filemanager.Layouts.Tag_Address;
import mdakh.filemanager.models.File;
import mdakh.filemanager.operations.Search;
import mdakh.filemanager.set_icon.Set_icon;

/**
 * Created by mdakh on 2/16/2018.
 */

public class MyRecyclerDownAdapter extends RecyclerView.Adapter<MyRecyclerDownAdapter.myViewHolder> {

    public Context context;
    public static ArrayList<File> Files=new ArrayList<>(10000);
    private static RecyclerView my_recycler_down;
    private static String address=null;
    private Tag_Address tag_address;
    public Search search=new Search();
    public static DataBaseHelperForType dataBaseHelperForType;

    public static final  int linear =R.layout.my_recycler_down_items_check;
    public static final int grid =R.layout.my_recycler_down_items_grid;
    public static int layout;
    public static int row=3;



    private boolean holderchecked=true;
    private int isample=0;

    Thread[] thread_set_icons1=new Thread[200];
    public static int thread_icon_cunter=0;
    private int thread_icon_cunter1=0;


    public static final String address_Internalmemory=Environment.getExternalStorageDirectory().getPath().toString();
    public static String address_Externalmemory;
    public static String address_root="/";
    public static String address_photo=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
    public static String address_picture=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
    public static String address_music=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).toString();
    public static String address_download=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
    public static String address_movies=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).toString();





    public MyRecyclerDownAdapter() {

    }

    public String getAddress(){
        return this.address;
    }

    public RecyclerView getRecyclerView() {
        return my_recycler_down;
    }

    public void setRecyclerView(RecyclerView my_recycler_down) {
        this.my_recycler_down = my_recycler_down;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }





    public MyRecyclerDownAdapter(final Context context , RecyclerView my_recycler_down , LinearLayout Layout_address){
        setContext(context);
        this.dataBaseHelperForType=new DataBaseHelperForType(context);
        List<String> storageDirectories=getsdcard();
        if (storageDirectories.get(0).equals(""))
            address_Externalmemory="";
        else {
            address_Externalmemory=storageDirectories.get(0);
        }


        setRecyclerView(my_recycler_down);
        this.tag_address = new Tag_Address(context, Layout_address);
        setContext(context);



        Linear_Vertical_View(context, my_recycler_down);

        //if (address==null)
        Internalmemory();




        //setAddress("storage");



    }




    public List<String> getsdcard() {
        List<String> storageDirectories=new ArrayList<>();
        String rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            List<String> results = new ArrayList<String>();
            java.io.File[] externalDirs = this.context.getExternalFilesDirs(null);
            for (java.io.File file : externalDirs) {
                String path = null;
                try {
                    path = file.getPath().split("/Android")[0];
                } catch (Exception e) {
                    e.printStackTrace();
                    path = null;
                }
                if (path != null) {
                    if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Environment.isExternalStorageRemovable(file))|| rawSecondaryStoragesStr != null && rawSecondaryStoragesStr.contains(path)) {
                        results.add(path);
                    }
                }
            }

            if (results.isEmpty())
                storageDirectories.add("");
            else
                storageDirectories = results;


        } else {
            final List<String> rv = new ArrayList<>();

            if (!TextUtils.isEmpty(rawSecondaryStoragesStr)) {
                final String[] rawSecondaryStorages = rawSecondaryStoragesStr.split(File.pathSeparator);
                Collections.addAll(rv, rawSecondaryStorages);
            }

            if (rv.isEmpty())
                storageDirectories.add("");
            else
                storageDirectories = rv;

        }
        return storageDirectories;
    }




    //get Location Files
    public void Externalmemory(){
        setAddress(MyRecyclerDownAdapter.address_Externalmemory);
    }

    public void Internalmemory(){
        setAddress(MyRecyclerDownAdapter.address_Internalmemory);
    }





    public void setAddress(String address){
        try {
            File file=new File(address);
            File[] filesa = file.listFiles();

                this.address=address;
                MyRecyclerDownAdapter.Files.clear();
                notifyDataSetChanged();

                tag_address.refresh_tag_address(getAddress());
            if (filesa[0]!=null) {
                for (int i1 = 0; i1 < filesa.length; i1++) {
                    this.Files.add(filesa[i1]);
                }
            }

        }
        catch (Exception e){
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED)
                    setAddress(getAddress());
                else
                    ((Activity) context).onAttachedToWindow();

            } else {
                // Permission has already been granted
                //errrrrrrrrror big
            }

            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED)
                    setAddress(getAddress());
                else
                    ((Activity) context).onAttachedToWindow();

            } else {
                // Permission has already been granted
                //errrrrrrrrror big
            }
        }
    }

    public void back(){
        int m=this.address.length()-1;
        for(int i=this.address.length()-1;i>=0;i--){
           if (this.address.charAt(i)=='/'){
               m=i;
               break;
           }
        }
        if (this.address.substring(0,m).equals("")) {
            setAddress("/");
        }
        else {
            setAddress(this.address.substring(0, m));
        }
    }


    //operations
    public void Search(String text){

        Files.clear();
        tag_address.refresh_tag_address("Search : "+text);
        MainActivity.sleep=20;
        search.setSearch(text,this.getAddress());
        search.start();
    }

    public void Delete(String name){
        File file = new File(getAddress()+"/"+name);
        file.delete();
    }




    //Mode view
    public void Linear_Vertical_View(Context context , RecyclerView my_recycler){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context , LinearLayoutManager.VERTICAL ,false);
        my_recycler.setLayoutManager(linearLayoutManager);
        my_recycler.setAdapter(this);
        my_recycler.setItemAnimator(new DefaultItemAnimator());
        layout=linear;
    }


    public void Linear_HORIZONTAL_View(Context context , RecyclerView my_recycler){
        GridLayoutManager linearLayoutManager=new GridLayoutManager(context ,row, GridLayoutManager.VERTICAL ,false);
        my_recycler.setLayoutManager(linearLayoutManager);
        my_recycler.setAdapter(this);
        my_recycler.setItemAnimator(new DefaultItemAnimator());
        layout=grid;
    }

    public void Linear_StaggeredGridLayoutManager(Context context , RecyclerView my_recycler){
        GridLayoutManager linearLayoutManager=new GridLayoutManager(context ,row, StaggeredGridLayoutManager.VERTICAL ,false);
        my_recycler.setLayoutManager(linearLayoutManager);
        my_recycler.setAdapter(this);
        my_recycler.setItemAnimator(new DefaultItemAnimator());
        layout=grid;
    }






    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(layout,parent,false);

        return new myViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {

        if (position==0){
            holder.show();
            holder.hide();
        }

        holder.name.setText(Files.get(position).getName());

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(context.getString(R.string.DateFormat));
        holder.date.setText(simpleDateFormat.format(Files.get(position).lastModified()));
        holder.icon.setImageResource(R.drawable.ic_folder);
        try {
            if (Files.get(position).isDirectory()) {
                java.io.File[] a = Files.get(position).listFiles();
                int i = a.length;
                if (i <= 1)
                    holder.size.setText(Long.toString(i) + " " + context.getResources().getString(R.string.item));
                else
                    holder.size.setText(Long.toString(i) + " " + context.getResources().getString(R.string.items));
                if (i == 0) {
                    holder.icon.setImageResource(R.drawable.ic_folder_clear);
                }
            } else {
                try {
                    float length = Files.get(position).length();
                    if (length < 1024)
                        holder.size.setText(String.format("%.2f", length) + " " + context.getResources().getString(R.string.B));
                    else if ((length = length / 1024) < 1024)
                        holder.size.setText(String.format("%.2f", length) + " " + context.getResources().getString(R.string.KB));
                    else if ((length = length / 1024) < 1024)
                        holder.size.setText(String.format("%.2f", length) + " " + context.getResources().getString(R.string.MB));
                    else if ((length = length / 1024) < 1024)
                        holder.size.setText(String.format("%.2f", length) + " " + context.getResources().getString(R.string.GB));
                    else if ((length = length / 1024) < 1024)
                        holder.size.setText(String.format("%.2f", length) + " " + context.getResources().getString(R.string.TB));
                } catch (Exception e) {
                    holder.size.setText(Long.toString(0) + " " + context.getResources().getString(R.string.B));
                }
            }
        }
        catch (Exception e){

        }
        if (!Files.get(position).isDirectory()) {


            String type = null;
            try {
                String s = Files.get(position).getName();
                int p = s.length();
                p--;
                String check_dot = s.charAt(p) + "";
                while (!check_dot.equals(".") && p > 0) {
                    p--;
                    check_dot = s.charAt(p) + "";
                }
                p++;
                String extention = s.substring(p, Files.get(position).getName().length());


                if (!dataBaseHelperForType.getList(extention).FileType.isEmpty()) {
                    type = dataBaseHelperForType.getList(extention).FileType;
                } else
                    type = null;
            }
            catch (Exception e){

            }



            Set_icon set_icon =new Set_icon(type,holder,position,context);


        }




        if (Status.checkbox==true) {
            holder.show();
        }
        else {
            holder.hide();
        }
        int width = my_recycler_down.getMeasuredWidth();
        int height = my_recycler_down.getMeasuredHeight();



        if (layout==grid)
            holder.setWidth((int) ((width-(row*7))/row));


        holder.checkBox.setChecked(Files.get(position).isCheck());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.sleep=10;
                if (!Files.get(position).isDirectory()&&!Status.checkbox)
                {
                    String s = Files.get(position).getName();
                    int p = s.length();
                    p--;
                    String check_dot = s.charAt(p) + "";
                    while (!check_dot.equals(".") && p > 0) {
                        p--;
                        check_dot = s.charAt(p) + "";
                    }
                    p++;
                    String extention = s.substring(p, Files.get(position).getName().length());
                    try {
                        String mime_type=getMimeType(extention);
                        if (!mime_type.isEmpty()){
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.setDataAndType(Uri.parse("file://" + Files.get(position).getAbsolutePath()), mime_type);
                            context.startActivity(Intent.createChooser(intent, "Select File"));
                        }

                    }
                    catch (Exception e){

                    }

                }
                else {
                    if (Status.checkbox) {
                        if (holderchecked) {
                            notifyItemChanged(position);
                            if (!holder.checkBox.isChecked()) {

                                Files.get(position).setCheck(true);
                            } else {

                                Files.get(position).setCheck(false);
                            }
                        } else
                            holderchecked = true;
                        notifyItemChanged(position);
                    } else
                        setAddress(Files.get(position).getPath());
                }

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Files.get(position).setCheck(false);
                menu_single menu=new menu_single(context,position);
                return false;
            }
        });
        holder.checkBox.setClickable(false);

    }


    public String getMimeType(String extension) {
        String type=null;
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }



    @Override
    public int getItemCount() {
        return Files.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static void mkh(){
        my_recycler_down.smoothScrollToPosition(0);
    }



    public class myViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        private TextView size;
        private TextView date;
        public ImageView icon;
        public CheckBox checkBox;
        private LinearLayout Linear_image;

        public myViewHolder(View itemView) {
            super(itemView);

            name=(TextView)itemView.findViewById(R.id.name);
            size=(TextView)itemView.findViewById(R.id.size);
            date=(TextView)itemView.findViewById(R.id.date);
            icon=(ImageView)itemView.findViewById(R.id.icon);
            checkBox=(CheckBox)itemView.findViewById(R.id.checkbox);
            Linear_image=(LinearLayout)itemView.findViewById(R.id.Linear_image);


        }
        public void hide(){
            checkBox.setVisibility(View.GONE);
        }
        public void show(){
            checkBox.setVisibility(View.VISIBLE);
        }

        public void setWidth(int i){
            ViewGroup.LayoutParams params = icon.getLayoutParams();
            if (checkBox.getVisibility()==View.VISIBLE)
            i-= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,30,context.getResources().getDisplayMetrics());
            params.height = i;
            params.width = i;
            icon.setLayoutParams(params);

        }
    }

}
