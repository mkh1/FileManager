package mdakh.filemanager;

import java.io.IOException;

import mdakh.filemanager.adapters.MyRecyclerDownAdapter;
import mdakh.filemanager.models.File;

public class Thread_change_value_Files extends Thread{

    private boolean Status_process=false;
    private boolean Status_a=false;
    private File file0=null;

    @Override
    public void run() {
        super.run();


        /*while (true){

            this.Status_process=false;

            int size_i=MyRecyclerDownAdapter.Files.size();
            //if (MyRecyclerDownAdapter.Files.size()>=MainActivity.Files.size()) {
                for (int i = 0; i < size_i; i++) {

                    try {
                        if (!MyRecyclerDownAdapter.Files.get(i).getName().equals(MainActivity.Files.get(i).getName())) {
                            Status.Status_Files.Equal(false);
                            if (i==0) {
                                MainActivity.a = 0;
                                MainActivity.lvl_j=1;

                            }
                            //if (MyRecyclerDownAdapter.Files.size()==MainActivity.Files.size())
                                //MainActivity.Files.clear();
                            this.Status_process=true;
                            break;

                        }
                    }
                    catch (Exception e1){
                        Status.Status_Files.Equal(false);
                        //MyRecyclerDownAdapter.mkh();
                        this.Status_process=true;
                        e1.printStackTrace();
                        break;
                    }
                }
            }
            else
                if (MyRecyclerDownAdapter.Files.size()<MainActivity.Files.size()){
                //MainActivity.a=0;
                MainActivity.a = 0;
                MainActivity.lvl_j=1;
                Status.Status_Files.Equal(false);
                MainActivity.Files.clear();
                this.Status_process=true;
            }
            else {
                Status.Status_Files.Equal(false);
                this.Status_process=true;
            }



            if (!this.Status_process) {
                Status.Status_Files.Equal(true);
                this.Status_process=false;
            }


            try {
                if (MyRecyclerDownAdapter.Files.get(0).getName().equals(MainActivity.Files.get(0).getName())&&!this.Status_process){
                    MainActivity.a = 0;
                    MainActivity.lvl_j=1;
                }
            }
            catch (Exception e){

            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }*/


    }
}
