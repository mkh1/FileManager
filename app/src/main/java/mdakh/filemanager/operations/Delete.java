package mdakh.filemanager.operations;


import java.io.File;

import mdakh.filemanager.Status;
import mdakh.filemanager.adapters.MyRecyclerDownAdapter;

public class Delete extends Thread {

    private String[] address;
    private boolean[] deleted;

    public boolean[] getDeleted() {
        return deleted;
    }
    public boolean getDeleted(int item){
        return deleted[item];
    }

    public Delete(String[] address){
        this.address=address;
        this.deleted=new boolean[address.length];
    }

    public Delete(String address){
        this.address=new String[1];
        this.address[0]=address;
        this.deleted=new boolean[1];
    }

    public Delete(){
        int a=0;
        for (int i=0;i< MyRecyclerDownAdapter.Files.size();i++){
            if (MyRecyclerDownAdapter.Files.get(i).isCheck()) {
                a++;
            }
        }
        address=new String[a];
        a=0;
        for (int i=0;i< MyRecyclerDownAdapter.Files.size();i++){
            if (MyRecyclerDownAdapter.Files.get(i).isCheck()) {
                address[a]=MyRecyclerDownAdapter.Files.get(i).getAbsolutePath();
                a++;
                MyRecyclerDownAdapter.Files.get(i).setCheck(false);
            }
        }

    }


    @Override
    public void run() {
        super.run();
        try {
            for (int i = 0; i < address.length; i++) {
                File file = new File(address[i]);
                deleted[i] = deleteRecursive(file);
            }
        }
        catch (Exception e){

        }
        finally {
            Status.get_Main_list=true;
        }
    }

    private boolean deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        return fileOrDirectory.delete();
    }

}
