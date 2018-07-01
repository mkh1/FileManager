package mdakh.filemanager.operations;
import android.content.Context;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import mdakh.filemanager.adapters.MyRecyclerDownAdapter;
import mdakh.filemanager.models.File;
import static android.content.Context.MODE_PRIVATE;
public class Sort {
    private static SharedPreferences.Editor editor;
    private static SharedPreferences prefs ;
    private static Context context;
    private static int first;
    private static int sort;
    public static int getFirst() {
        first = prefs.getInt("Sort_first", 0);
        return first;
    }
    public static int getSort() {
        sort = prefs.getInt("Sort",4);
        return sort;
    }
    public static void get_sort_setting(){
        prefs = context.getSharedPreferences("Sort_Setting", MODE_PRIVATE);
        first = prefs.getInt("Sort_first", 0);
        sort = prefs.getInt("Sort",4);
        switch (sort){
            case 0 :
                Sort_name_a();
                break;
            case 1:
                Sort_name_r();
                break;
            case 2:
                Sort_date_a();
                break;
            case 3:
                Sort_date_r();
                break;
            case 4:
                Sort_no();
                break;
        }
        switch (first){
            case 0 :
                no_Sort_first();
                break;
            case 1:
                Sort_first_folder();
                break;
            case 2:
                Sort_first_file();
                break;
        }
    }
    public Sort(Context context){
        Sort.context=context;
    }
    public static void no_Sort_first(){
        editor = context.getSharedPreferences("Sort_Setting", MODE_PRIVATE).edit();
        editor.putInt("Sort_first",0);
        editor.apply();
    }
    public static void first_folder(){
        editor = context.getSharedPreferences("Sort_Setting", MODE_PRIVATE).edit();
        editor.putInt("Sort_first",1);
        editor.apply();
    }
    public static void first_file(){
        editor = context.getSharedPreferences("Sort_Setting", MODE_PRIVATE).edit();
        editor.putInt("Sort_first",2);
        editor.apply();
    }
    private static void Sort_first_folder(){
        editor = context.getSharedPreferences("Sort_Setting", MODE_PRIVATE).edit();
        int a=MyRecyclerDownAdapter.Files.size();
        int b=a;
        int y=0;
        for (int i=a-1;i>=0;i--){
            if (!MyRecyclerDownAdapter.Files.get(i).isDirectory()&&y>0){
                for (int j=i;j<b-1;j++){
                    change_location(j,j+1);
                }
                b--;
                y--;
            }
            else {
                y++;
            }
        }
        editor.putInt("Sort_first",1);
        editor.apply();
    }
    private static void Sort_first_file(){
        editor = context.getSharedPreferences("Sort_Setting", MODE_PRIVATE).edit();
        int a=MyRecyclerDownAdapter.Files.size();
        int b=a;
        int y=0;
        for (int i=a-1;i>=0;i--){
            if (MyRecyclerDownAdapter.Files.get(i).isDirectory()){
                for (int j=i;j<b-1;j++) {
                    change_location(j, j + 1);
                }
                    b--;
                    y--;
                }
            else {
                    y++;
                }
        }
        editor.putInt("Sort_first",2);
        editor.apply();
    }
    private static void Sort_name_a(){
        editor = context.getSharedPreferences("Sort_Setting", MODE_PRIVATE).edit();
        int a=MyRecyclerDownAdapter.Files.size();
        for (int i=0;i<a;i++){
            for (int j=i;j<a;j++){
                int b=MyRecyclerDownAdapter.Files.get(i).getName().compareTo(MyRecyclerDownAdapter.Files.get(j).getName());
                if (b>0)
                    change_location(i,j);
            }
        }
        editor.putInt("Sort",0);
        editor.apply();
    }
    public static void name_a(){
        editor = context.getSharedPreferences("Sort_Setting", MODE_PRIVATE).edit();
        editor.putInt("Sort",0);
        editor.apply();
    }
    public static void name_r(){
        editor = context.getSharedPreferences("Sort_Setting", MODE_PRIVATE).edit();
        editor.putInt("Sort",1);
        editor.apply();
    }
    public static void date_a(){
        editor = context.getSharedPreferences("Sort_Setting", MODE_PRIVATE).edit();
        editor.putInt("Sort",2);
        editor.apply();
    }
    public static void date_r(){
        editor = context.getSharedPreferences("Sort_Setting", MODE_PRIVATE).edit();
        editor.putInt("Sort",3);
        editor.apply();
    }
    private static void Sort_name_r(){
        editor = context.getSharedPreferences("Sort_Setting", MODE_PRIVATE).edit();
        int a=MyRecyclerDownAdapter.Files.size();
        for (int i=0;i<a;i++){
            for (int j=i;j<a;j++){
                int b=MyRecyclerDownAdapter.Files.get(i).getName().compareTo(MyRecyclerDownAdapter.Files.get(j).getName());
                if (b<0)
                    change_location(i,j);
            }
        }
        editor.putInt("Sort",1);
        editor.apply();
    }
    private static void Sort_date_a(){
        editor = context.getSharedPreferences("Sort_Setting", MODE_PRIVATE).edit();
        int a=MyRecyclerDownAdapter.Files.size();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        for (int i=0;i<a;i++){
            for (int j=i;j<a;j++){
                int b=simpleDateFormat.format(MyRecyclerDownAdapter.Files.get(i).lastModified()).compareTo(simpleDateFormat.format(MyRecyclerDownAdapter.Files.get(j).lastModified()));
                if (b>0)
                    change_location(i,j);
            }
        }
        editor.putInt("Sort",2);
        editor.apply();
    }
    private static void Sort_date_r(){
        editor = context.getSharedPreferences("Sort_Setting", MODE_PRIVATE).edit();
        int a=MyRecyclerDownAdapter.Files.size();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        for (int i=0;i<a;i++){
            for (int j=i;j<a;j++){
                int b=simpleDateFormat.format(MyRecyclerDownAdapter.Files.get(i).lastModified()).compareTo(simpleDateFormat.format(MyRecyclerDownAdapter.Files.get(j).lastModified()));
                if (b<0)
                    change_location(i,j);
            }
        }
        editor.putInt("Sort",3);
        editor.apply();
    }
    public static void Sort_no(){
        editor = context.getSharedPreferences("Sort_Setting", MODE_PRIVATE).edit();
        editor.putInt("Sort",4);
        editor.apply();
    }
    private static void change_location(int a,int b){
        File c= MyRecyclerDownAdapter.Files.get(a);
        MyRecyclerDownAdapter.Files.set(a,MyRecyclerDownAdapter.Files.get(b));
        MyRecyclerDownAdapter.Files.set(b,c);
    }
}
