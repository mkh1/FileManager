package mdakh.filemanager.set_icon;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import mdakh.filemanager.R;
import mdakh.filemanager.adapters.MyRecyclerDownAdapter;
public class Thread_set_icon extends Thread{
    private final MyRecyclerDownAdapter.myViewHolder holder;
    private final int position;
    private Context context;
    private Bitmap finaly=null;
    public Thread_set_icon(final MyRecyclerDownAdapter.myViewHolder holder, final int position, Context context){
        this.holder=holder;
        this.position=position;
        this.context=context;
    }
    @Override
    public void run() {
        try {
            super.run();
            finaly=getbyte(MyRecyclerDownAdapter.Files.get(position).getPath());
            if (finaly!=null) {
                holder.icon.post(new Runnable() {
                    @Override
                    public void run() {
                        holder.icon.setImageBitmap(finaly);
                    }
                });
            }
            else {
                holder.icon.post(new Runnable() {
                    @Override
                    public void run() {
                        holder.icon.setImageResource(R.drawable.ic_image);
                    }
                });
            }
        }
        catch (Exception e){
            holder.icon.post(new Runnable() {
                @Override
                public void run() {
                    holder.icon.setImageResource(R.drawable.ic_image);
                }
            });
        }
    }
    private Bitmap getbyte(String path) {
        FileInputStream buffer = null;
        try {
            BitmapFactory.Options option = new BitmapFactory.Options();
            //option.inJustDecodeBounds = true;
            if (new File(path).length()>1048576)
                option.inSampleSize = 20;
            else if (new File(path).length()>20480)
                option.inSampleSize =5;
            else
                option.inSampleSize =0;
            buffer = new FileInputStream(new File(path));
            byte[] a = new byte[(int) new File(path).length()];
            buffer.read(a);
            Bitmap bitmap=BitmapFactory.decodeByteArray(a,0,a.length,option);
            return bitmap;
        }
        catch (IOException e) {
            holder.icon.post(new Runnable() {
                @Override
                public void run() {
                    holder.icon.setImageResource(R.drawable.ic_image);
                }
            });
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                buffer.close();
            }
            catch (Exception e){
            }
        }
    }
}
