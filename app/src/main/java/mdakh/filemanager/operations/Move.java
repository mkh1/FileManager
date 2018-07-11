package mdakh.filemanager.operations;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Move {

    ArrayList<String> paths=new ArrayList<>();
    private float size=0;


    private void moveFile(String inputPath, String inputFile, String outputPath) {




        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File (outputPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file
            out.flush();
            out.close();
            out = null;

            // delete the original file
            new File(inputPath + inputFile).delete();


        }

        catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }


    //ready for paths
    private void end_node(String path){

        File file=new File(path);

        if (!file.isDirectory()){
            paths.add(file.getAbsolutePath());
            size+=file.length();
            return;
        }
        else{
            File[] files=file.listFiles();
            if (files.length==0){
                paths.add(file.getAbsolutePath());
            }
            else {
                for (int i=0;i<files.length;i++){
                    end_node(files[i].getAbsolutePath());
                }
            }
        }

    }


}
