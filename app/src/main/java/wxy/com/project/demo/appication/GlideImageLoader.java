package wxy.com.project.demo.appication;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.loader.ImageLoader;

import wxy.com.project.R;

import static wxy.com.project.demo.appication.BaseApplication.baseApplication;

/**
 * Created by jiajun.wang on 2018/1/2.
 */

public class GlideImageLoader implements ImageLoader {


    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
       Glide.with(activity)
               .load(path)
               .asBitmap()
               .placeholder(R.mipmap.default_image)
               .error(R.mipmap.default_image)
               .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
        Glide.get(baseApplication).clearDiskCache();
    }
}
