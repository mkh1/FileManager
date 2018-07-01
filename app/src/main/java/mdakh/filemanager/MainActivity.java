package mdakh.filemanager;




import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.os.StatFs;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import mdakh.filemanager.Database.DataBaseHelper;
import mdakh.filemanager.Database.DataBaseHelperForType;
import mdakh.filemanager.Layouts.Layout_Show_by;
import mdakh.filemanager.Layouts.Layout_Sort_By;
import mdakh.filemanager.Layouts.Nav_with_progress;
import mdakh.filemanager.Layouts.Nav_without_progress;
import mdakh.filemanager.Layouts.accept_checks;
import mdakh.filemanager.adapters.MyRecyclerDownAdapter;
import mdakh.filemanager.models.File;
import mdakh.filemanager.models.ListFormat;
import mdakh.filemanager.operations.Delete;
import mdakh.filemanager.operations.Search;
import mdakh.filemanager.operations.Sort;
import mdakh.filemanager.samples.Button_sample1;

public class MainActivity extends AppCompatActivity
{
    public static Thread refresh;
    public static Thread_change_value_Files thread_change_value_files;
    private String m_Text = "";
    public static RecyclerView my_recycler_down;
    private LinearLayout tag_address;
    public static LinearLayout linear_main;
    public static accept_checks accept_checks;
    private DataBaseHelper db;
    private static Toolbar toolbar;
    public static boolean menuActive=true;
    public static MyRecyclerDownAdapter myRecyclerDownAdapter;
    private LinearLayout Internal;
    public static NavigationDrawerFragment drawerFragment;
    public static ArrayList<File> Files=new ArrayList<>(10000);
    public static int sleep=150;
    private int j=1;
    public static int a=0;
    public static int lvl_j=1;
    public int insertitem;
    private boolean notify_refresh=false;
    public static MenuItem paste;
    public static int height,width;
    private String setsearch="";
    private SearchView searchView;
    private Button button;
    private Layout_Show_by layout_show_by;
    private Button_sample1 button_sample1;
    List<ListFormat> myformat=new ArrayList<>();
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private ImageView img;



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
                img.setImageURI(selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void getgalerry(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //config image loader
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);


        int h=36000;
        tag_address=(LinearLayout)findViewById(R.id.tag_address);

        getDelegate().applyDayNight();
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);

        toolbar=(Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        drawerFragment=(NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setupDrawe(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout),toolbar);
        //Internal=(LinearLayout)findViewById(R.id.Internal);

        thread_change_value_files=new Thread_change_value_Files();

        final Sort sort=new Sort(getApplicationContext());

        refresh=new Thread(new Runnable() {
            @Override
            public void run() {
                MyRecyclerDownAdapter.Files.clear();
                MainActivity.a=0;
                while (true){

                    try {
                        if (!MyRecyclerDownAdapter.Files.get(0).getName().equals(MainActivity.Files.get(0).getName())&&!MyRecyclerDownAdapter.Files.get(0).getPath().equals(MainActivity.Files.get(0).getPath())) {
                            a=0;
                            lvl_j=1;
                            notify_refresh=true;
                            //if (Files.size() == MyRecyclerDownAdapter.Files.size())
                            Files.clear();
                        }
                        else {

                            notify_refresh = false;
                        }
                    }
                    catch (Exception e){
                        a=0;
                        lvl_j=1;
                        notify_refresh=true;
                    }



                    if (!Status.Status_Files.isEqual()&&!MyRecyclerDownAdapter.Files.isEmpty()){
                        j=MyRecyclerDownAdapter.Files.size();
                        if (j>lvl_j*10){
                            j=lvl_j*10;
                            lvl_j++;
                            if (j>=MyRecyclerDownAdapter.Files.size()) {
                                j = MyRecyclerDownAdapter.Files.size();
                            }
                        }

                        for (int i=MainActivity.a;i<j;i++){
                            Files.add(MyRecyclerDownAdapter.Files.get(i));
                            insertitem=i;

                            if (i==j-1) {
                                try {
                                    Sort.get_sort_setting();
                                } catch (Exception e) {

                                }
                            }

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    myRecyclerDownAdapter.notifyDataSetChanged();
                                }
                            });


                        }






                        MainActivity.a=j;


                    }
                    if (notify_refresh&&!MyRecyclerDownAdapter.Files.isEmpty()){
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                MyRecyclerDownAdapter.mkh();
                            }
                        });
                        notify_refresh=false;
                    }


                    if (Status.get_Main_list)
                    {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                myRecyclerDownAdapter.notifyDataSetChanged();
                                myRecyclerDownAdapter.Internalmemory();
                            }
                        });
                        Status.get_Main_list=false;
                    }
                    if (sleep<200)
                        sleep*=3;



                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }



                }

            }
        });

        thread_change_value_files.start();
        refresh.start();
        my_recycler_down=(RecyclerView)findViewById(R.id.my_recycler_down);

        myRecyclerDownAdapter=new MyRecyclerDownAdapter(this , my_recycler_down,tag_address);



        linear_main=(LinearLayout)findViewById(R.id.linearmain);
        accept_checks=new accept_checks(this,linear_main);
        mdakh.filemanager.Layouts.accept_checks.hide();


        layout_show_by=new Layout_Show_by(this,myRecyclerDownAdapter,my_recycler_down);
        layout_show_by.read(this);


        Nav_with_progress a1,a2;
        a1=new Nav_with_progress(this,MyRecyclerDownAdapter.address_Internalmemory);
        a2=new Nav_with_progress(this,MyRecyclerDownAdapter.address_Externalmemory);
        Nav_without_progress b1,b2,b3,b4,b5,b6;
        b1=new Nav_without_progress(this,MyRecyclerDownAdapter.address_root,getResources().getDrawable(R.drawable.ic_root_directory),true,1);
        b2=new Nav_without_progress(this,MyRecyclerDownAdapter.address_photo,getResources().getDrawable(R.drawable.ic_photo_camera),false,1);
        b3=new Nav_without_progress(this,MyRecyclerDownAdapter.address_picture,getResources().getDrawable(R.drawable.ic_picture),true,2);
        b4=new Nav_without_progress(this,MyRecyclerDownAdapter.address_music,getResources().getDrawable(R.drawable.ic_music_player),false,2);
        b5=new Nav_without_progress(this,MyRecyclerDownAdapter.address_download,getResources().getDrawable(R.drawable.ic_download),true,3);
        b6=new Nav_without_progress(this,MyRecyclerDownAdapter.address_movies,getResources().getDrawable(R.drawable.ic_video_player),false,3);

    }

    @Override
    protected void onResume() {

        /*if (myRecyclerDownAdapter.Files.isEmpty())
            myRecyclerDownAdapter.setAddress(myRecyclerDownAdapter.getAddress());*/
        /*Internal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRecyclerDownAdapter.setAddress(MyRecyclerDownAdapter.address_Internalmemory);
                drawerFragment.setDrawerClose();
            }
        });*/
        super.onResume();
    }

    @Override
    public void onBackPressed() {


        sleep=200;
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            searchView.setIconified(true);
        }
        else {
            myRecyclerDownAdapter.back();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        paste=menu.findItem(R.id.paste);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(menuActive){
            getMenuInflater().inflate(R.menu.menu_main, menu);
            MenuItem item_search=menu.findItem(R.id.search);
            searchView=(SearchView)item_search.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    sleep=200;
                    if (!setsearch.equals(query)) {
                        if (!query.isEmpty()) {
                            Search.target = query;
                            MyRecyclerDownAdapter.Files.clear();
                            Files.clear();
                            myRecyclerDownAdapter.Search(query);
                        } else {
                            Search.target = query;
                            myRecyclerDownAdapter.setAddress(myRecyclerDownAdapter.getAddress());
                        }
                        setsearch=query;
                    }

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    sleep=200;
                    if(!setsearch.equals(newText))
                    {
                        if (!newText.isEmpty()) {
                            Search.target = newText;
                            MyRecyclerDownAdapter.Files.clear();
                            Files.clear();
                            myRecyclerDownAdapter.Search(newText);
                        }
                        else {
                            Search.target = newText;
                            myRecyclerDownAdapter.setAddress(myRecyclerDownAdapter.getAddress());
                        }
                        setsearch=newText;
                    }

                    return false;
                }
            });
        }
        else {
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);


        int id=item.getItemId();

        if(id == R.id.delete){
            if (!mdakh.filemanager.Layouts.accept_checks.isShow()) {
                mdakh.filemanager.Layouts.accept_checks.show();
                Status.checkbox = true;
            }
            else {
                mdakh.filemanager.Layouts.accept_checks.hide();
                Status.checkbox = false;
            }
            mdakh.filemanager.Layouts.accept_checks.Status.delete();

            myRecyclerDownAdapter.notifyDataSetChanged();
            return true;
        }
        else if(id == R.id.move){
            if (!mdakh.filemanager.Layouts.accept_checks.isShow()) {
                mdakh.filemanager.Layouts.accept_checks.show();
                Status.checkbox = true;
            }
            else {
                mdakh.filemanager.Layouts.accept_checks.hide();
                Status.checkbox = false;
            }
            mdakh.filemanager.Layouts.accept_checks.Status.move();
            myRecyclerDownAdapter.notifyDataSetChanged();

            return true;
        }
        else if(id==R.id.copy){

            if (!mdakh.filemanager.Layouts.accept_checks.isShow()) {
                mdakh.filemanager.Layouts.accept_checks.show();
                Status.checkbox = true;
            }
            else {
                mdakh.filemanager.Layouts.accept_checks.hide();
                Status.checkbox = false;
            }
            mdakh.filemanager.Layouts.accept_checks.Status.copy();
            myRecyclerDownAdapter.notifyDataSetChanged();

            return true;
        }
        else if (id==R.id.compress){
            if (!mdakh.filemanager.Layouts.accept_checks.isShow()) {
                mdakh.filemanager.Layouts.accept_checks.show();
                Status.checkbox = true;
            }
            else {
                mdakh.filemanager.Layouts.accept_checks.hide();
                Status.checkbox = false;
            }
            mdakh.filemanager.Layouts.accept_checks.Status.compress();
            myRecyclerDownAdapter.notifyDataSetChanged();
            return true;
        }
        else if (id==R.id.newfolder){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.newfolder));

            final EditText input = new EditText(this);

            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
            builder.setView(input);

            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    m_Text = input.getText().toString();
                    java.io.File file=new java.io.File(myRecyclerDownAdapter.getAddress()+"/"+m_Text);
                    boolean b=file.mkdirs();
                    if (!b){
                        Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                    }
                    myRecyclerDownAdapter.setAddress(myRecyclerDownAdapter.getAddress());
                }
            });
            builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();

            return true;
        }
        else if (id==R.id.paste){
            if (mdakh.filemanager.Layouts.accept_checks.Status.isCopy()){
                mdakh.filemanager.Layouts.accept_checks.copy.copy(myRecyclerDownAdapter.getAddress());
                myRecyclerDownAdapter.notifyDataSetChanged();
            }
            else if (mdakh.filemanager.Layouts.accept_checks.Status.isMove()){
                mdakh.filemanager.Layouts.accept_checks.copy.copy(myRecyclerDownAdapter.getAddress());
                for (int i = 0; i< mdakh.filemanager.Layouts.accept_checks.copy.address.length; i++) {
                    if (mdakh.filemanager.Layouts.accept_checks.copy.ch[i]) {
                        Delete delete = new Delete(mdakh.filemanager.Layouts.accept_checks.copy.address[i]);
                        delete.start();
                    }
                }
            }
            paste.setEnabled(false);
            return true;
        }
        else if(id == R.id.show_by){


            layout_show_by.show();


            return true;
        }
        else if(id == R.id.sort_by){

            Layout_Sort_By layout_show_by=new Layout_Sort_By(this,myRecyclerDownAdapter);


            return true;
        }
        return super.onOptionsItemSelected(item);

    }







}
