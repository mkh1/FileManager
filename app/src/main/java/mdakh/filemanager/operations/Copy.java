package mdakh.filemanager.operations;

import android.content.Context;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import mdakh.filemanager.Status;
import mdakh.filemanager.adapters.MyRecyclerDownAdapter;

public class Copy {

    private Context context;

    public String[] address;
    private String[] name;
    public boolean[] ch;

    public Copy(String src){
        this.address=new String[1];
        this.address[0]=src;
    }

    public Copy(Context context){
        int a=0;
        this.context=context;
        for (int i = 0; i< MyRecyclerDownAdapter.Files.size(); i++){
            if (MyRecyclerDownAdapter.Files.get(i).isCheck()) {
                a++;
            }
        }
        address=new String[a];
        name=new String[a];
        a=0;
        for (int i=0;i< MyRecyclerDownAdapter.Files.size();i++){
            if (MyRecyclerDownAdapter.Files.get(i).isCheck()) {
                address[a]=MyRecyclerDownAdapter.Files.get(i).getAbsolutePath();
                name[a]=MyRecyclerDownAdapter.Files.get(i).getName();
                a++;
            }
        }
    }

    public void copy(String dst){
        ch=new boolean[address.length];
        for (int i=0;i<address.length;i++){
            int a=1;
            while (new File(dst+"/"+this.name[i]).exists()){
                if (!(new File(dst+"/"+this.name[i]+a).exists()))
                    this.name[i]=this.name[i]+a;
                else a++;
            }
            ch[i]=copy(new File(this.address[i]),new File(dst+"/"+this.name[i]),new File(dst));
        }
    }

    private boolean copy(File src,File dst,File dir) {
        if (src.isDirectory()){
            if (!new java.io.File(dir.getAbsolutePath()+"/"+src.getName()).exists()) {
                java.io.File file = new java.io.File(dir.getAbsolutePath()+"/"+src.getName());
                file.mkdirs();
            }
            File[] files=src.listFiles();
            for (int i=0;i<files.length;i++){
                copy(files[i],new File(dir.getAbsolutePath()+"/"+src.getName()+"/"+files[i].getName()),new File(dir.getAbsolutePath()+"/"+src.getName()));
            }
        }
        else {
            try {
                FileUtils.copyFile(src, dst);
                Status.get_Main_list = true;
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }
}
