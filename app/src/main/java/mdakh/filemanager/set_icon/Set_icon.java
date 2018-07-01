package mdakh.filemanager.set_icon;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;


import mdakh.filemanager.R;
import mdakh.filemanager.adapters.MyRecyclerDownAdapter;

public class Set_icon {


    public Set_icon(String type, final MyRecyclerDownAdapter.myViewHolder holder, final int position, Context context){

        try {
            if (type.equals(null)) {
                holder.icon.setImageResource(R.drawable.ic_file_o);
            }
            else{ if (type.equals("eBook File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_book);
            } else if (type.equals("Web File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_internet_explorer);
            } else if (type.equals("Vector Image File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_image);
                new image_loader(holder,position,context);
            } else if (type.equals("Text File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_file_text_o);
            } else if (type.equals("System File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_gears);
            } else if (type.equals("Spreadsheet File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_sheet_4);
            } else if (type.equals("Settings File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_setting);
            } else if (type.equals("Raster Image File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_image);
                new image_loader(holder,position,context);
            } else if (type.equals("Plugin File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_plug);
            } else if (type.equals("Page Layout File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_page_1);
            } else if (type.equals("Misc File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_file_o);
            } else if (type.equals("Game File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_gamepad);
            } else if (type.equals("GIS File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_file_o);
            } else if (type.equals("Font File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_font);
            } else if (type.equals("Executable File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_gear);
                if (MyRecyclerDownAdapter.Files.get(position).getPath().endsWith(".apk")) {
                    String filePath = MyRecyclerDownAdapter.Files.get(position).getPath();
                    PackageInfo packageInfo = context.getPackageManager().getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
                    if (packageInfo != null) {
                        ApplicationInfo appInfo = packageInfo.applicationInfo;
                        if (Build.VERSION.SDK_INT >= 8) {
                            appInfo.sourceDir = filePath;
                            appInfo.publicSourceDir = filePath;
                        }
                        final Drawable icon = appInfo.loadIcon(context.getPackageManager());
                        holder.icon.setImageDrawable(icon);
                    }
                }
            } else if (type.equals("Encoded File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_file_zip_o);
            } else if (type.equals("Disk Image File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_disc);
            } else if (type.equals("Developer File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_file_code_o);
            } else if (type.equals("Database File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_database);
            } else if (type.equals("Data File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_data);
            } else if (type.equals("Compressed File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_compress);
            } else if (type.equals("Camera Raw File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_raw);
            } else if (type.equals("CAD File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_interface);
            } else if (type.equals("Backup File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_backup);
            } else if (type.equals("Audio File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_music);
                try {
                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                    mmr.setDataSource(MyRecyclerDownAdapter.Files.get(position).getAbsolutePath());
                    byte[] data = mmr.getEmbeddedPicture();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    holder.icon.setImageBitmap(bitmap);
                } catch (Exception e) {
                    holder.icon.setImageResource(R.drawable.ic_music);
                }
            } else if (type.equals("3D Image File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_image_3d);
            } else if (type.equals("Video File Formats")) {
                holder.icon.setImageResource(R.drawable.ic_file_video_o);
                try {
                    holder.icon.setImageBitmap(ThumbnailUtils.createVideoThumbnail(MyRecyclerDownAdapter.Files.get(position).getAbsolutePath(),
                            MediaStore.Video.Thumbnails.MINI_KIND));
                } catch (Exception e) {
                    holder.icon.setImageResource(R.drawable.ic_file_video_o);
                }
            } else {
                holder.icon.setImageResource(R.drawable.ic_file_o);
            }}
        } catch (Exception e) {
            holder.icon.setImageResource(R.drawable.ic_file_o);
        }
    }
}
