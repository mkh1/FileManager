package mdakh.filemanager.models;

import android.support.annotation.NonNull;

import java.net.URI;

/**
 * Created by mdakh on 2/16/2018.
 */

public class File extends java.io.File{

    private boolean check;



    public File(@NonNull String pathname) {
        super(pathname);
    }

    public File(String parent, @NonNull String child) {
        super(parent, child);
    }

    public File(java.io.File parent, @NonNull String child) {
        super(parent, child);
    }

    public File(@NonNull URI uri) {
        super(uri);
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    @Override
    public File[] listFiles(){
        java.io.File[] file=super.listFiles();
        File[] files=new File[file.length];
        for (int i=0;i<file.length;i++){
            files[i]=new File(file[i].getPath());
        }
        return files;
    }

}
