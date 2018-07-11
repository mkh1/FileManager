package mdakh.filemanager.set_icon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import mdakh.filemanager.R;
import mdakh.filemanager.adapters.MyRecyclerDownAdapter;

public class image_loader {


    protected AbsListView listView;

    protected boolean pauseOnScroll = false;
    protected boolean pauseOnFling = false;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    ImageLoader imageLoader = ImageLoader.getInstance();

    public image_loader(final MyRecyclerDownAdapter.myViewHolder holder, int position, Context context){
        try {


            BitmapFactory.Options resample = new BitmapFactory.Options();
            resample.inSampleSize=5000;
            resample.inScreenDensity=100;



            DisplayImageOptions options;
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.ic_loading)
                    .showImageForEmptyUri(R.drawable.ic_empty_box)
                    .showImageOnFail(R.drawable.ic_publish_fail_24)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .decodingOptions(resample)
                    .bitmapConfig(Bitmap.Config.ARGB_8888)
                    .resetViewBeforeLoading(false)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .build();
            imageLoader.displayImage("file://"+ MyRecyclerDownAdapter.Files.get(position).getAbsolutePath(),holder.icon,options,animateFirstListener);

            applyScrollListener();
        }
        catch (Exception e){
        }
    }



    private void applyScrollListener() {
        listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), pauseOnScroll, pauseOnFling));
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }




}
