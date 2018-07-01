package mdakh.filemanager.operations;


import java.util.ArrayList;
import java.util.PriorityQueue;


import mdakh.filemanager.MainActivity;
import mdakh.filemanager.adapters.MyRecyclerDownAdapter;
import mdakh.filemanager.models.File;

/**
 * Created by mdakh on 3/28/2018.
 */

public class Search extends Thread {

    private boolean Start=false;
    public static String target;
    private String address;
    private PriorityQueue<String> folders;
    public static boolean finish;

    public boolean isFinish() {
        return finish;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Search(){

    }

    public void setSearch(String target , String address){
        folders=new PriorityQueue<String>();
        setTarget(target);
        setAddress(address);
    }

    @Override
    public void run() {
        super.run();
        this.finish=false;
        this.Start=false;
        searchbfs(address,this.target);
    }


    private void searchbfs(String address,String target){

        File file=new File(address);
        File[] filesa = (File[]) file.listFiles();


        for (int i=0;i<filesa.length;i++){
            if(filesa[i].isDirectory()){
                this.folders.add(filesa[i].getPath());
            }
            if(filesa[i].getName().toLowerCase().contains(this.target.toLowerCase())){
                if (!Start) {
                    MainActivity.myRecyclerDownAdapter.Files.clear();
                    MainActivity.Files.clear();
                    MyRecyclerDownAdapter.Files.clear();
                }
                if (this.target.equals(target)&&!this.target.isEmpty()) {
                    MainActivity.myRecyclerDownAdapter.Files.add(filesa[i]);

                }
                Start = true;

            }
        }
        if (!folders.isEmpty()&&this.target.equals(target)&&!this.target.isEmpty())
            searchbfs(folders.poll(),target);
        else
            this.finish=true;

    }


}
